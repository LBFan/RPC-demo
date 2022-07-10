package fan.severclient.handler.impl;

import com.alibaba.fastjson.JSONObject;
import fan.severclient.Body;
import fan.severclient.Header;
import fan.severclient.handler.ReadWriteHandle;

import java.io.DataInputStream;
import java.io.DataOutputStream;

/**
 * @author : PF_23
 * @Description : json实现类：读取连接信息
 * @date : 2022/7/2 14:19.
 */

public class JsonReadWriteHandleImpl implements ReadWriteHandle {
    private DataOutputStream dos;
    private DataInputStream dis;

    public JsonReadWriteHandleImpl(DataOutputStream dos, DataInputStream dis) {
        this.dos = dos;
        this.dis = dis;
    }

    @Override
    public Header readHeader() throws Exception {
        Header header;
        try {
            String headerStr = dis.readUTF();
            System.out.println("header content:" + headerStr);
            header = JSONObject.parseObject(headerStr, Header.class);
        } catch (Exception e) {
            System.out.println("读取头信息异常，" + e.getMessage());
            throw new Exception(e.getMessage());
        }
        return header;
    }

    @Override
    public Body readBody() throws Exception {
        Body body;
        try {
            String bodyStr = dis.readUTF();
            System.out.println("body content:" + bodyStr);
            body = JSONObject.parseObject(bodyStr, Body.class);
        } catch (Exception e) {
            System.out.println("读取结构体信息异常，" + e.getMessage());
            throw new Exception(e.getMessage());
        }
        return body;
    }

    @Override
    public Boolean writeObject(Header header, Body body) throws Exception {
        try {
            dos.writeUTF(JSONObject.toJSONString(header));
            dos.writeUTF(JSONObject.toJSONString(body));
            dos.flush();
        } catch (Exception e) {
            System.out.println("写结构体信息异常，" + e.getMessage());
            throw new Exception(e.getMessage());
        }

        return true;
    }

    @Override
    public void close() {
        if (dis != null) {
            try {
                dis.close();
            } catch(Exception e) {

            }
        }
        if (dos != null) {
            try {
                dos.close();
            } catch(Exception e) {

            }
        }
    }
}

