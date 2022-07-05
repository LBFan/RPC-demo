package fan.day2;

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
    private List<Object> args;
    /**
     * 响应信息
     */
    private Object reply;
}

