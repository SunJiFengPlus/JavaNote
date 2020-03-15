package concurrent;

import java.util.concurrent.CountDownLatch;

/*
 *  当自习室的人都走完后再锁门
 *  应用: 初级电商秒杀
 */
public class CountDownLatchTest {
    public static void main(String[] args) {

    }

    public static void closeDoor() throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(6);

        // 6 个上自习的同学, 每个人离开教室的时间不一样
        for (int i = 0; i < 6; i++) {
            new Thread(() -> {
                System.out.println(Thread.currentThread().getName());
                countDownLatch.countDown();
            }, String.valueOf(i + 1)).start();
        }

        // 等待 countDownLatch 计数器为 0, 此时 main 线程为阻塞状态
        countDownLatch.await();
        System.out.println("最后走的人锁门");
    }
}