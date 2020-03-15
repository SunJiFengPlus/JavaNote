package concurrent;

import java.util.Random;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/*
 *  Semaphore: 信号灯
 *  6 个车抢 3 个停车位
 */
public class SemaphoreTest {
    public static void main(String[] args) {
        // 模拟 3 个车位
        Semaphore semaphore = new Semaphore(3);

        // 模拟 6 个车
        for (int i = 0; i < 6; i++) {
            new Thread(() -> {
                try {
                    semaphore.acquire();
                    System.out.println(Thread.currentThread().getName() + "抢到了车位");
                    TimeUnit.SECONDS.sleep(new Random().nextInt(7));
                    System.out.println(Thread.currentThread().getName() + "离开了");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    semaphore.release();
                }
            }, String.valueOf(i)).start();
        }
    }
}
