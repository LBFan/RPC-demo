package fan.severclient.handler;

import fan.severclient.Body;
import fan.severclient.Header;

/**
 * @author : PF_23
 * @Description : 读写请求接口
 * @date : 2022/7/2 14:15.
 */

public interface ReadWriteHandle {
    /**
     * 读头信息
     *
     * @return
     * @throws Exception
     */
    Header readHeader() throws Exception;

    /**
     * 读结构体
     *
     * @return
     * @throws Exception
     */
    Body readBody() throws Exception;

    /**
     * 写结构头，结构体信息
     *
     * @param header
     * @param body
     * @throws Exception
     */
    Boolean writeObject(Header header, Body body) throws Exception;

    void close();

}