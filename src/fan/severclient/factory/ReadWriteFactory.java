package fan.severclient.factory;

import fan.severclient.handler.ReadWriteHandle;

import java.io.DataInputStream;
import java.io.DataOutputStream;

/**
 * @author : PF_23
 * @Description : 读写类就简单工厂
 * @date : 2022/7/2 14:33.
 */

public interface ReadWriteFactory {

    ReadWriteHandle getInstance(DataOutputStream dos, DataInputStream dis);

}