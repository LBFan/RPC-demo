package fan.severclient;

import com.alibaba.fastjson.JSONObject;
import fan.severclient.factory.ReadWriteFactory;
import fan.severclient.handler.ReadWriteHandle;
import fan.severclient.register.RegisterUtil;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author : PF_23
 * @Description : TODO
 * @date : 2022/6/29 23:37.
 */

public class Server implements Runnable {

    Socket socket;

    public Server(Socket socket) {
        this.socket = socket;
    }

    public static void main(String[] args) throws IOException {
        //Scanner scanner = new Scanner(System.in);
        //int n = scanner.nextInt();

        ServerSocket serverSocket;
        try {
            serverSocket = new ServerSocket(8899);
        } catch (IOException e) {
            System.out.println("监听端口异常" + e.getMessage());
            return;
        }
        while (true) {
            Socket sock = serverSocket.accept();
            System.out.println("*********连接建立成功**************");
            new Thread(new Server(sock)).start();
            //handle(serverSocket);
        }
    }


    private static void handle(ServerSocket serverSocket) {
        try {
            Socket accept = serverSocket.accept();
            System.out.println("*********连接建立成功**************");
            long start = System.currentTimeMillis();
            System.out.println("start time:" + start);
            // 输入流
            InputStream is = accept.getInputStream();

            BufferedInputStream bis = new BufferedInputStream(is);

            DataInputStream dis = new DataInputStream(bis);

            //输出流

            OutputStream os = accept.getOutputStream();

            BufferedOutputStream bos = new BufferedOutputStream(os);

            DataOutputStream dos = new DataOutputStream(bos);

            String optionStr;
            Option option;

            // 一个连接只发一次option
            try {
                // read option
                optionStr = dis.readUTF();
                System.out.println("option content:" + optionStr);
                option = JSONObject.parseObject(optionStr, Option.class);
            } catch (Exception e) {
                System.out.println("读取option连接数据出现问题，被动关闭");
                close(accept, is, bis, dis, os, bos, dos);
                return;
            }
            //协议校验
            boolean checkOption = checkOption(option);
            if (!checkOption) {
                System.out.println("抱歉, 协议校验位通过，只能关闭！！！");
                close(accept, is, bis, dis, os, bos, dos);
                return;
            }
            // 获取处理器
            ReadWriteHandle readWriteHandle = RegisterUtil.getFactory(option.getCodeType()).getInstance(dos, dis);

            for (; ; ) {
                Header header;
                Body body;
                try {
                    header = readWriteHandle.readHeader();
                    body = readWriteHandle.readBody();

                } catch (Exception e) {
                    System.out.println("读取数据异常，" + e.getMessage());
                    break;
                }
                try {
                    header.setSeq(header.getSeq() + 101);
                    body.setPayload("resp " + body.getPayload());
                    readWriteHandle.writeObject(header, body);
                } catch (Exception e) {
                    System.out.println("发送数据异常，" + e.getMessage());
                    break;
                }

            }

            close(accept, is, bis, dis, os, bos, dos);
            System.out.println("cost too much time:" + (System.currentTimeMillis() - start));
            System.out.println("***********本次连接关闭*********");
        } catch (Exception e) {
            System.out.println("ghjksa" + e.getMessage());
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


    private static boolean checkOption(Option option) {
        if (option == null) {
            System.out.println("未获取到协议内容，关闭连接");
            return false;
        }

        if (!(option.getCodeType().equals(CodeTypeConst.JSON_TYPE) || option.getCodeType().equals(CodeTypeConst.GOB_TYPE))) {
            System.out.println("当前仅支持json格式或者gob格式");
            return false;
        }

        return true;
    }


    /**
     * 自定义编码解码器
     *
     * @param codeType
     * @param readWriteFactory
     * @param dos
     * @param dis
     * @return
     * @throws Exception
     */
    public boolean addFactory(String codeType, ReadWriteFactory readWriteFactory, DataOutputStream dos, DataInputStream dis) throws Exception {
        boolean addFactory = RegisterUtil.addFactory("application/gson", readWriteFactory, dos, dis);
        return addFactory;
    }

    @Override
    public void run() {
        try {
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

            String optionStr;
            Option option;

            // 一个连接只发一次option
            try {
                // read option
                optionStr = dis.readUTF();
                System.out.println("option content:" + optionStr);
                option = JSONObject.parseObject(optionStr, Option.class);
            } catch (Exception e) {
                System.out.println("读取option连接数据出现问题，被动关闭");
                close(socket, is, bis, dis, os, bos, dos);
                return;
            }
            //协议校验
            boolean checkOption = checkOption(option);
            if (!checkOption) {
                System.out.println("抱歉, 协议校验位通过，只能关闭！！！");
                close(socket, is, bis, dis, os, bos, dos);
                return;
            }
            // 获取处理器
            ReadWriteHandle readWriteHandle = RegisterUtil.getFactory(option.getCodeType()).getInstance(dos, dis);

            for (; ; ) {
                Header header;
                Body body;
                try {
                    header = readWriteHandle.readHeader();
                    body = readWriteHandle.readBody();

                } catch (Exception e) {
                    System.out.println("读取数据异常，" + e.getMessage());
                    break;
                }
                try {
                    header.setSeq(header.getSeq());
                    body.setPayload("resp " + body.getPayload());
                    readWriteHandle.writeObject(header, body);
                } catch (Exception e) {
                    System.out.println("发送数据异常，" + e.getMessage());
                    break;
                }

            }

            close(socket, is, bis, dis, os, bos, dos);
            System.out.println("cost too much time:" + (System.currentTimeMillis() - start));
            System.out.println("***********本次连接关闭*********");
        } catch (Exception e) {
            System.out.println("ghjksa" + e.getMessage());
        }
    }
}

