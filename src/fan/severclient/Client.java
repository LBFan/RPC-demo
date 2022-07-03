package fan.severclient;

import com.alibaba.fastjson.JSONObject;
import fan.severclient.handler.ReadWriteHandle;
import fan.severclient.register.RegisterUtil;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.*;

/**
 * @author : PF_23
 * @Description : TODO
 * @date : 2022/7/3 21:05.
 */

public class Client {

    public static void main(String[] args) {
        while (true) {
            Scanner scanner = new Scanner(System.in);
            int n = scanner.nextInt();
            CountDownLatch countDownLatch = new CountDownLatch(n);
            // 客户端建立连接，发送协议，然后可以选择多次发送请求：头和体，并接受服务器返回的结果
            try {

                Socket socket = new Socket("127.0.0.1", 8899);
                long start = System.currentTimeMillis();
                System.out.println("start time:" + start);
                // 输入流
                InputStream is = socket.getInputStream();

                BufferedInputStream bis = new BufferedInputStream(is);

                DataInputStream dis = new DataInputStream(bis);

                //输出流

                OutputStream os = socket.getOutputStream();

                BufferedOutputStream bos = new BufferedOutputStream(os);

                DataOutputStream dos = new DataOutputStream(bos);


                // 发送协议
                Option option = new Option(1864, CodeTypeConst.JSON_TYPE);
                dos.writeUTF(JSONObject.toJSONString(option));

                // 多线程发送和接受请求
                ExecutorService service = new ThreadPoolExecutor(10, 50, 10L, TimeUnit.MICROSECONDS, new LinkedBlockingDeque<>(100));
                // 获取处理器
                ReadWriteHandle readWriteHandle = RegisterUtil.getFactory(option.getCodeType()).getInstance(dos, dis);

                for (int i = 0; i < n; i++) {
                    int finalI = i;
                    //service.submit(() -> {
                    Header header = new Header("pf.method", finalI, "");
                    Body body = new Body();
                    body.setPayload("this is body:" + finalI);
                    try {
                        readWriteHandle.writeObject(header, body);
                    } catch (Exception e) {
                        System.out.println("第" + finalI + "次请求发送失败,原因：" + e.getMessage());
                    }
                    countDownLatch.countDown();

                    //});

                    // 接受响应
                    try {
                        Header respHeader = readWriteHandle.readHeader();
                        System.out.println("第" + finalI + "次接收到服务器的响应头信息：" + respHeader.toString());
                        Body respBody = readWriteHandle.readBody();
                        System.out.println("第" + finalI + "次接收到服务器的响应体信息：" + respBody.toString());
                    } catch (Exception e) {
                        System.out.println("第" + finalI + "次获取响应信息失败,原因：" + e.getMessage());
                    }
                }

                countDownLatch.await();
                service.shutdown();
                System.out.println("发送" + n + "个请求，并且获取响应信息总计耗时：" + (System.currentTimeMillis() - start) + "ms");

                close(socket, is, bis, dis, os, bos, dos);

            } catch (Exception e) {

            }
        }
    }

    private static void close(Socket accept, InputStream is, BufferedInputStream bis, DataInputStream dis, OutputStream os, BufferedOutputStream bos, DataOutputStream dos) throws IOException {
        if (dos != null) {
            dos.close();
        }
        if (bos != null) {
            bos.close();
        }
        if (os != null) {
            os.close();
        }

        if (dis != null) {
            dis.close();
        }
        if (bis != null) {
            bis.close();
        }
        if (is != null) {
            is.close();
        }
        if (accept != null) {
            accept.close();
        }
    }
}

