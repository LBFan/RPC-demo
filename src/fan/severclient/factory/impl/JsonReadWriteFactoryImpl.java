package fan.severclient.factory.impl;

import fan.severclient.factory.ReadWriteFactory;
import fan.severclient.handler.ReadWriteHandle;
import fan.severclient.handler.impl.JsonReadWriteHandleImpl;

import java.io.DataInputStream;
import java.io.DataOutputStream;

/**
 * @author : PF_23
 * @Description : json读写类工厂
 * @date : 2022/7/2 14:37.
 */

public class JsonReadWriteFactoryImpl implements ReadWriteFactory {
    @Override
    public ReadWriteHandle getInstance(DataOutputStream dos, DataInputStream dis) {
        return new JsonReadWriteHandleImpl(dos, dis);
    }
}

