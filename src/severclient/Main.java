package severclient;

import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author : PF_23
 * @Description : TODO
 * @date : 2022/6/30 00:05.
 */

public class Main {
    public static void main(String[] args) throws Exception {

        // 同时包含服务器和客户端
        //服务器
        ServerSocket serverSocket = new ServerSocket(8899);

        // 客户端
        Socket s = new Socket("127.0.0.1", 8899);

        // 输出流
        Socket socket = serverSocket.accept();
        System.out.println("server start.time mils:" + System.currentTimeMillis());
        System.out.println("clinet:" + InetAddress.getLocalHost() + "已连接服务器");
        OutputStream sos = socket.getOutputStream();
        ObjectOutputStream soos = new ObjectOutputStream(sos);
        //输入流
        InputStream sis = socket.getInputStream();
        ObjectInputStream sois = new ObjectInputStream(sis);


        // 输出流
        OutputStream os = s.getOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(os);
        //输入流
        InputStream is = s.getInputStream();
        ObjectInputStream ois = new ObjectInputStream(is);


        for (int i = 0; i < 5; i++) {
            // 1. 客户端写消息
            Option option = new Option("pfan.test", System.currentTimeMillis(), i, "json");
            oos.writeObject(option);
            oos.flush();

            //2. 服务器读消息，处理消息，写消息
            //2.1 读消息
            Option message = (Option) sois.readObject();
            System.out.println("服务端第" + i + "次接收到客户端发送的数据:" + message);

            // 2.2 简单处理消息并写处理后的消息
            message.setSeq(message.getSeq() + 5);
            message.setMagicNumber(message.getMagicNumber() + 5);
            soos.writeObject(message);
            soos.flush();

            // 获取服务端返回的结果
            Option optionRes = (Option) ois.readObject();
            System.out.println("客户端第" + i + "次请求的返回结果：");
            System.out.println(optionRes);
        }
        sois.close();
        soos.close();
        ois.close();
        oos.close();
        s.close();
        serverSocket.close();

    }
}

