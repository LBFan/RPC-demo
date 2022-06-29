package codec;

/**
 * @author : PF_23
 * @Description : TODO
 * @date : 2022/6/25 17:04.
 */

public interface Codec {

    /**
     * 读请求头
     *
     * @param header header
     * @throws Exception exception
     */
    void readHeader(Header header) throws Exception;

    /**
     * 读请求体
     *
     * @param o json和gob类格式
     * @throws Exception
     */
    void readBody(Object o) throws Exception;

    /**
     * 写请求头和请求体
     *
     * @param header
     * @param o
     * @throws Exception
     */
    void write(Header header, Object o) throws Exception;

}