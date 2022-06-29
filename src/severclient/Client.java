package severclient;

import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * @author : PF_23
 * @Description : TODO
 * @date : 2022/6/29 23:38.
 */

public class Client {
    // 发送连接，向连接里发送二进制数据，接收响应，关闭连接

    public static void main(String[] args) throws Exception{
        Socket s = new Socket("127.0.0.1", 8890);
        // 输出流
        OutputStream os = s.getOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(os);

        //输入流
        InputStream is = s.getInputStream();
        ObjectInputStream ois = new ObjectInputStream(is);
        for (int i = 0; i < 5; i++) {
            Option option = new Option("pfan.test", System.currentTimeMillis(), 5, "json");
            oos.writeObject(option);
            oos.flush();

            // 获取服务端返回的结果
            Option optionRes = (Option) ois.readObject();
            System.out.println("第" + i + "次请求的返回结果：");
            System.out.println(optionRes);
        }
        ois.close();
        is.close();
        oos.close();
        os.close();
        s.close();
    }

}

