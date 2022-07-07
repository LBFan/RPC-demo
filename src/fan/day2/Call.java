package fan.day2;

import fan.severclient.Body;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.util.List;

/**
 * @author : PF_23
 * @Description : 承载一次RPC调用所需要的信息
 * 对于net/rpc来说，一个函数要能够被远程调用，需要满足：
 * 1.函数是课扩展的
 * 2.函数类型是可扩展的
 * 3.函数的参数均是可扩展的
 * 4.函数的第二个参数是个引用对象
 * 5.函数需要抛出异常（go语言中是返回错误信息）
 * @date : 2022/7/4 22:02.
 */

public class Call {
    /**
     * 服务名方法名
     */
    private String serviceMethod;
    /**
     * 序列号
     */
    private int seq;
    /**
     * 请求参数
     */
    private Body args;
    /**
     * 响应信息
     */
    private Object reply;

    private DataInputStream dis;

    public String getServiceMethod() {
        return serviceMethod;
    }

    public void setServiceMethod(String serviceMethod) {
        this.serviceMethod = serviceMethod;
    }

    public int getSeq() {
        return seq;
    }

    public void setSeq(int seq) {
        this.seq = seq;
    }

    public Body getArgs() {
        return args;
    }

    public void setArgs(Body args) {
        this.args = args;
    }

    public Object getReply() {
        return reply;
    }

    public void setReply(Object reply) {
        this.reply = reply;
    }

    public DataInputStream getDis() {
        return dis;
    }

    public void setDis(DataInputStream dis) {
        this.dis = dis;
    }

    public void close() {

    }

    public void done() {

    }
}

