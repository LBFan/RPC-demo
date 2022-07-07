package fan.day2;

import fan.severclient.Body;
import fan.severclient.Header;
import fan.severclient.Option;
import fan.severclient.handler.ReadWriteHandle;

import java.util.Map;

/**
 * @author : PF_23
 * @Description : TODO
 * @date : 2022/7/6 21:05.
 */

public class Client {

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
    Boolean shutdown;
    /**
     * server has told up to stop
     */
    Boolean closing;


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

    public void terminateCalls() throws Exception {
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
        synchronized (MU) {
            try {
                int seq = this.registerCall(call);
            } catch (Exception e) {
                System.out.println(e.getLocalizedMessage());
                return;
            }
            this.header.setServiceMethod(call.getServiceMethod());
            this.header.setSeq(seq);
            try {
                Boolean writeSuccess = this.codec.writeObject(this.header, call.getArgs());
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
            try {
                Header header = this.codec.readHeader();
                Call call = this.removeCall(header.getSeq());
                if (call != null) {
                    Body body = this.codec.readBody();
                }

                //
                call.done();
            } catch (Exception e) {
                System.out.println(e.getLocalizedMessage());
                break;
            }
        }

        // 发生异常，所以终止后续未发送的请求
        try {
            this.terminateCalls();
        } catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
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
}

