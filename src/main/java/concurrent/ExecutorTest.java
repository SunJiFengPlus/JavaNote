package concurrent;

import java.util.Random;
import java.util.concurrent.*;

/**
 * 模拟银行业务, 有 5 个服务窗口, 15 个要办理业务的人
 * 第四种获得线程的方法: 线程池
 */
public class ExecutorTest {
    public static void main(String[] args) {
        testExecutor();
    }

    public static void testExecutor() {
        // 一个线程池中有 5 个线程
        // ExecutorService service = Executors.newFixedThreadPool(5);
        // 单例
        // ExecutorService service = Executors.newSingleThreadExecutor();
        // 根据任务量, 弹性调整线程池中的线程个数
        ExecutorService service = Executors.newCachedThreadPool();
        Future<Integer> future = null;

        try {
            // 模拟 15 个办理业务的客户
            for (int i = 0; i < 15; i++) {
                future = service.submit(() -> {
                    System.out.print(Thread.currentThread().getName() + " ");
                    // 模拟营业员给客户的回执
                    return new Random().nextInt(10);
                });
                System.out.println(future.get());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            service.shutdown();
        }
    }

    public static void testExecutors() {
        ScheduledExecutorService service = Executors.newScheduledThreadPool(5);
        ScheduledFuture<Integer> ft = null;

        try {
            // 模拟 15 个办理业务的客户
            for (int i = 0; i < 15; i++) {
                // 每 2 秒, 叫一个客户过来办理业务
                ft = service.schedule(() -> {
                    System.out.print(Thread.currentThread().getName() + " ");
                    // 模拟营业员给客户的回执
                    return new Random().nextInt(5);
                }, 2, TimeUnit.SECONDS);
                System.out.println(ft.get());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            service.shutdown();
        }
    }
}
