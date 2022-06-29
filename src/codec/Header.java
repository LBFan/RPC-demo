package codec;

/**
 * @author : PF_23
 * @Description : TODO
 * @date : 2022/6/25 16:46.
 */

public class Header {
    /**
     * sequence number chosen by client:请求的序号,也可以认为是某个请求的 ID，用来区分不同的请求
     */
    private int seq;
    /**
     * format "Service.Method":服务名和方法名，通常与 Go 语言中的结构体和方法相映射
     */
    private String serviceMethod;
    /**
     * 错误信息，客户端置为空，服务端如果如果发生错误，将错误信息置于 Error 中
     */
    private String error;
}

