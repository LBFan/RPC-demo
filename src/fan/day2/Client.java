package fan.day2;

import com.alibaba.fastjson.JSONObject;
import fan.severclient.Body;
import fan.severclient.Header;
import fan.severclient.Option;
import fan.severclient.handler.ReadWriteHandle;
import fan.severclient.register.RegisterUtil;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

/**
 * @author : PF_23
 * @Description : TODO
 * @date : 2022/7/6 21:05.
 */

public class Client {

    public CountDownLatch count;

    /**
     * 互斥锁
     */
    private final Long MU = -1L;

    /**
     * 互斥锁
     */
    private final Long SENDING = 1L;
    /**
     * 编解码器
     */
    ReadWriteHandle codec;
    /**
     * 协议
     */
    Option opt;
    /**
     * 头信息
     */
    Header header;
    /**
     * 序列号
     */
    int seq;
    /**
     * 待发送的消息体map
     */
    Map<Integer, Call> pending;
    /**
     * user has called close
     */
    boolean shutdown;
    /**
     * server has told up to stop
     */
    boolean closing;


    //ExecutorService service = new ThreadPoolExecutor(10, 50, 10, TimeUnit.SECONDS, new LinkedBlockingDeque<>(1000));

    /**
     * 构造方法： 主要是构造请求协议和通道（输入输出流）
     *
     * @param option
     * @param dis
     * @param dos
     */
    public Client(Option option, DataInputStream dis, DataOutputStream dos, int n) {
        // 获取处理器
        this.codec = RegisterUtil.getFactory(option.getCodeType()).getInstance(dos, dis);
        this.seq = 1;
        this.opt = option;
        this.pending = new HashMap<>();
        this.header = new Header();
        count = new CountDownLatch(n);
        // 发送请求协议
        try {
            dos.writeUTF(JSONObject.toJSONString(this.opt));
            System.out.println("client sent option:" + this.opt);
        } catch (IOException e) {
            System.out.println("请求协议发送失败");
            if (dis != null) {
                try {
                    dis.close();
                } catch (IOException ioException) {

                }
            }
            try {
                dos.close();
            } catch (IOException ioException) {

            }
        }

        try {
            PoolUtil.getInstance().submit(this::receive);
        } catch (Exception e) {
            System.out.println("接收中断，线程结束");
        }
    }

    public void close() throws Exception {
        synchronized (MU) {
            if (this.closing) {
                throw new Exception("connection is shut down");
            }
            codec.close();
        }
    }

    public Boolean isAvailable() {
        synchronized (MU) {
            return !this.shutdown && !this.closing;
        }
    }

    /**
     * 注册call消息体，并返回消息体序列号
     *
     * @param call
     * @return
     * @throws Exception
     */
    public int registerCall(Call call) throws Exception {
        synchronized (MU) {
            if (this.closing || this.shutdown) {
                throw new Exception("Connection is shutdown");
            }
            call.setSeq(this.seq);
            this.pending.put(call.getSeq(), call);
            this.seq++;
            return call.getSeq();
        }
    }


    public Call removeCall(int seq) {
        synchronized (MU) {
            Call call = this.pending.get(seq);
            delete(this.pending, seq);
            return call;
        }
    }

    /**
     * 移除序列号为seq的pending
     *
     * @param pending
     * @param seq
     */
    private void delete(Map<Integer, Call> pending, int seq) {
        pending.remove(seq);
    }

    public void terminateCalls() {
        synchronized (SENDING) {
            synchronized (MU) {
                this.shutdown = true;
                for (Map.Entry<Integer, Call> entry : this.pending.entrySet()) {
                    entry.getValue().close();
                }
            }
        }
    }

    public void send(Call call) {
        synchronized (SENDING) {
            int seqNow;
            try {
                seqNow = this.registerCall(call);
            } catch (Exception e) {
                System.out.println(e.getLocalizedMessage());
                return;
            }
            this.header.setServiceMethod(call.getServiceMethod());
            this.header.setSeq(seqNow);
            try {
                Boolean writeSuccess = this.codec.writeObject(this.header, call.getArgs());
                //System.out.println("client send header:" + this.header + "; body:" + call.getArgs() + "success");
            } catch (Exception e) {
                Call call1 = this.removeCall(seq);
                System.out.println(e.getLocalizedMessage());
                if (call1 != null) {
                    call1.done();
                }
                return;
            }
        }
    }

    public void receive() {
        for (; ; ) {
            if (count.getCount() == 0) {
                return;
            }
            try {
                Header header = this.codec.readHeader();
                //System.out.println("client receive header:" + header);
                Call call = this.removeCall(header.getSeq());
                if (call != null) {
                    Body body = this.codec.readBody();
                    //System.out.println("client receive body:" + body);
                }
            } catch (Exception e) {
                System.out.println(e);
                // 发生异常，所以终止后续未发送的请求
                try {
                    this.terminateCalls();
                } catch (Exception e1) {
                    System.out.println(e1.getLocalizedMessage());
                }
            } finally {
                count.countDown();
            }
        }
    }

    public Call goCall(String serviceMethod, Body args) {
        Call call = new Call();
        call.setSeq(seq);
        call.setServiceMethod(serviceMethod);
        call.setArgs(args);

        this.send(call);
        return call;
    }

    /**
     * waits for it to complete,or returns its error exception.
     *
     * @param serviceMethod
     * @param args
     * @throws Exception
     */
    public void call(String serviceMethod, Body args) throws Exception {
        this.goCall(serviceMethod, args);
    }
}

