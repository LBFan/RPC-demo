package severclient;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author : PF_23
 * @Description : TODO
 * @date : 2022/6/29 23:37.
 */

public class Server {

    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(8890);
            while (true) {
                Socket socket = serverSocket.accept();
                // 输出流
                OutputStream os = socket.getOutputStream();
                ObjectOutputStream oos = new ObjectOutputStream(os);

                //输入流
                InputStream is = socket.getInputStream();
                ObjectInputStream ois = new ObjectInputStream(is);
                byte[] buffer = new byte[1024];
                //while (ois.readObject() != null) {
                for (int i = 0; i < 10; i++) {
                    Lock lock = new ReentrantLock();
                    try {
                        lock.lock();
                        System.out.println("server start.time mils:" + System.currentTimeMillis());
                        System.out.println("clinet:" + InetAddress.getLocalHost() + "已连接服务器");

                        Option2 message = (Option2) ois.readObject();
                        System.out.println("服务端接收到客户端发送的数据:" + message);

                        // 简单处理客户端发送的数据
                        message.setSeq(message.getSeq() + 5);
                        message.setMagicNumber(message.getMagicNumber() + 5);
                        oos.writeObject(message);
                        oos.flush();
                    } catch (Exception e) {
                        System.out.println("读取连接数据出现问题，被动关闭");
                        break;
                    } finally {
                        lock.unlock();
                    }

                }
                ois.close();
                is.close();
                oos.close();
                os.close();
                socket.close();
            }
        } catch (Exception e) {

        }
    }
}

