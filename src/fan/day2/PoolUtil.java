package fan.day2;

import java.util.concurrent.*;

/**
 * @author : PF_23
 * @Description : 客户端线程池工具类
 * @date : 2022/7/9 15:23.
 */

public class PoolUtil {
    private static volatile ExecutorService service;

    /**
     * 双重校验单例线程池
     *
     * @return
     */
    public static synchronized ExecutorService getInstance() {
        if (service == null) {
            synchronized (PoolUtil.class) {
                if (service == null) {
                    service = new ThreadPoolExecutor(10, 20, 10, TimeUnit.SECONDS, new LinkedBlockingDeque<>());
                }
            }
        }
        return service;
    }

}

