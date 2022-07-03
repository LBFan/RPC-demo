package fan.severclient.register;

import fan.severclient.factory.ReadWriteFactory;
import fan.severclient.factory.impl.GsonReadWriteFactoryImpl;
import fan.severclient.factory.impl.JsonReadWriteFactoryImpl;
import fan.severclient.handler.ReadWriteHandle;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author : PF_23
 * @Description : 工厂类注册器
 * @date : 2022/7/2 14:11.
 */

public class RegisterUtil {

    /**
     *  注册器的职责：注入系统中实现的已有的工厂，根据编解码类型获取对应的读写类工厂，用户自定义增加新编解码类型工厂
     */
    private static Map<String, ReadWriteFactory> readWriteFactoryMap;

    // 后期可以改为动态注入
    static {
        readWriteFactoryMap = new ConcurrentHashMap<>();
        readWriteFactoryMap.put("application/json", new JsonReadWriteFactoryImpl());
        readWriteFactoryMap.put("application/gson", new GsonReadWriteFactoryImpl());
    }

    public static ReadWriteFactory getFactory(String codeType) {
        if (codeType == null || codeType.equals("")) {
            return null;
        }
        for (Map.Entry<String, ReadWriteFactory> entry : readWriteFactoryMap.entrySet()) {
            if (entry.getKey().equals(codeType)) {
                return entry.getValue();
            }
        }
        return null;
    }

    public static boolean addFactory(String codeType, ReadWriteFactory readWriteFactory, DataOutputStream dos, DataInputStream dis) throws Exception {
        // 先判断是否存在当前类型的工厂,
        // 获取类的全路径，判断是否存在相同的读写工厂
        for (Map.Entry<String, ReadWriteFactory> entry : readWriteFactoryMap.entrySet()) {
            if (entry.equals(codeType)) {
                throw new Exception("当前类型的工厂已存在，请重新定义新的工厂类型");
            }
            String clazz = entry.getValue().getClass().getSimpleName();

            if (clazz.equals(entry.getValue().getInstance(dos, dis).getClass().getSimpleName())) {
                throw new Exception("当前工厂已存在，请重新定义新的工厂");
            }
        }

        readWriteFactoryMap.put(codeType, readWriteFactory);
        return true;
    }
}

