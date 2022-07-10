package fan.day2;

import fan.severclient.Body;
import fan.severclient.CodeTypeConst;
import fan.severclient.Option;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

/**
 * @author : PF_23
 * @Description : 主函数，启动客户端建立连接，并且发送请求，接受服务器的响应；服务器接受请求，处理请求并返回响应
 * @date : 2022/7/9 09:58.
 */

public class Main {

    /**
     * 解析并返回协议
     *
     * @param option
     * @return
     * @throws Exception
     */
    public static Option parseOptions(Option option) throws Exception {
        if (option == null) {
            throw new Exception("option is null");
        }

        if (option.getCodeType() == null || option.getCodeType().equals("")) {
            option.setCodeType(CodeTypeConst.JSON_TYPE);
        }

        return option;
    }

    /**
     * 请求建立连接：地址，协议
     *
     * @return
     * @throws Exception
     */
    public static Client dail(String ip, int port, Option option, int n) {
        Option op;
        Socket socket = null;
        InputStream is = null;
        BufferedInputStream bis = null;
        DataInputStream dis = null;

        OutputStream os = null;
        BufferedOutputStream bos = null;
        DataOutputStream dos = null;
        try {
            op = parseOptions(option);
            socket = new Socket(ip, port);

            //输入流
            is = socket.getInputStream();
            bis = new BufferedInputStream(is);
            dis = new DataInputStream(bis);

            //输出流
            os = socket.getOutputStream();
            bos = new BufferedOutputStream(os);
            dos = new DataOutputStream(bos);
        } catch (Exception e) {
            System.out.println("client请求建立连接失败");
            try {
                //this.close();
                //关闭当前的所有流
                close(socket, is, bis, dis, os, bos, dos);
            } catch (Exception exception) {

            } finally {
                return null;
            }
        }
        return new Client(op, dis, dos, n);
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

    public static void main(String[] args) {
        System.out.println("系统默认编码：" + System.getProperty("file.encoding"));
        String ip = "127.0.0.1";
        int port = 8899;
        int n = new Scanner(System.in).nextInt();
        Option option = new Option(1001, CodeTypeConst.JSON_TYPE);
        Client client = dail(ip, port, option, n);
        String serviceMethod = "fan.pp";

        for (int i = 0; i < n; i++) {
            int finalI = i;
            PoolUtil.getInstance().submit(() -> {
                Body body = new Body();
                body.setPayload("yiyayayaya: " + finalI);
                try {
                    client.call(serviceMethod, body);
                    //client.notify();
                } catch (Exception e) {
                    e.printStackTrace();
                    return;
                }
            });

        }

        try {
            client.count.await();
            client.close();
            PoolUtil.getInstance().shutdownNow();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}

