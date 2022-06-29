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
 * @date : 2022/6/29 23:37.
 */

public class Server {

    public static void main(String[] args) throws Exception {
        ServerSocket serverSocket = new ServerSocket(8890);
        while (true) {
            Socket socket = serverSocket.accept();
            // 输出流
            OutputStream os = socket.getOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(os);

            //输入流
            InputStream is = socket.getInputStream();
            ObjectInputStream ois = new ObjectInputStream(is);

            //while (ois.readObject() != null) {
            for (int i = 0; i < 5; i++) {
                System.out.println("server start.time mils:" + System.currentTimeMillis());
                System.out.println("clinet:" + InetAddress.getLocalHost() + "已连接服务器");


                Option message = (Option) ois.readObject();
                System.out.println("服务端接收到客户端发送的数据:" + message);

                // 简单处理客户端发送的数据
                message.setSeq(message.getSeq() + 5);
                message.setMagicNumber(message.getMagicNumber() + 5);
                oos.writeObject(message);
                oos.flush();
            }
            //}
            ois.close();
            is.close();
            oos.close();
            os.close();
            socket.close();
        }
    }
}

