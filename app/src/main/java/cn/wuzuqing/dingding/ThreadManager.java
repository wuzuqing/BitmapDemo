package cn.wuzuqing.dingding;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @Author: 吴祖清
 * @CreateDate: 2018/10/25 14:44
 * @Description: 线程池管理
 * @UpdateUser: 吴祖清
 * @UpdateDate: 2018/10/25 14:44
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class ThreadManager {

    private static ThreadManager INSTANCE = new ThreadManager();


    public static ThreadManager get() {
        return INSTANCE;
    }

    private ThreadPoolExecutor executor;

    private ThreadManager() {
        executor = new ThreadPoolExecutor(4,
                20, 3, TimeUnit.SECONDS,
                new LinkedBlockingQueue<Runnable>(6), handler);
    }

    /**
     * 执行线程
     *
     * @param runnable
     */
    public void execute(Runnable runnable) {
        executor.execute(runnable);
    }

    /**
     * 拒绝策略
     */
    private RejectedExecutionHandler handler = new RejectedExecutionHandler() {
        @Override
        public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
        }
    };
}
