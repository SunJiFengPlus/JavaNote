package concurrent;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.concurrent.*;

/**
 * ThreadLocal 用于保存线程上下文信息，在任意需要的地方可以获取
 *
 * @author 孙继峰
 * @date 2019/06/25
 */
public class ThreadLocalTest {

    private static ThreadLocal<Integer> threadLocal = new ThreadLocal<>();

    private static final int FREQUENCY = 100;
    private static final int THREAD_POOL_SIZE = 2;

    private static ThreadFactory namedThreadFactory = new ThreadFactoryBuilder().setNameFormat("threadLocal-test-%d").build();
    private static ExecutorService threadPool = new ThreadPoolExecutor(THREAD_POOL_SIZE, THREAD_POOL_SIZE,
            0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>(1024),
            namedThreadFactory, new ThreadPoolExecutor.AbortPolicy());

    /**
     * 结论: ThreadLocal 中 set 进去的值, 线程之间不共享
     */
    public static void main(String[] args) {
        threadPool.execute(() -> {
            for (int i = 0; i < FREQUENCY; i++) {
                threadLocal.set(i);
                System.out.println(Thread.currentThread().getName() + ": " + threadLocal.get());
            }
        });

        threadPool.execute(() -> {
            for (int i = 0; i < FREQUENCY; i++) {
                System.out.println(Thread.currentThread().getName() + ": " + threadLocal.get());
            }
        });
    }
}