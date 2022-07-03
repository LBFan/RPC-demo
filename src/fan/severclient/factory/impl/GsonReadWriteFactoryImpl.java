package fan.severclient.factory.impl;

import fan.severclient.factory.ReadWriteFactory;
import fan.severclient.handler.ReadWriteHandle;
import fan.severclient.handler.impl.GsonReadWriteHandleImpl;
import fan.severclient.handler.impl.JsonReadWriteHandleImpl;

import java.io.DataInputStream;
import java.io.DataOutputStream;

/**
 * @author : PF_23
 * @Description : TODO
 * @date : 2022/7/2 15:01.
 */

public class GsonReadWriteFactoryImpl implements ReadWriteFactory {
    @Override
    public ReadWriteHandle getInstance(DataOutputStream dos, DataInputStream dis) {
        return new GsonReadWriteHandleImpl(dos, dis);
    }
}

