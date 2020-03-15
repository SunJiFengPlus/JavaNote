package concurrent;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * 循环屏障: 一组线程到达屏障点(同步点)时被阻塞, 直到最后一个线程到达时, 屏障才会解除, 阻塞的线程才能继续
 * 反向的 CountDownLatch, 集齐 7 颗龙珠, 可召唤神龙
 * <p>
 * CountDownLatch 不可重用, CyclicBarrier 可重用
 */
public class CyclicBarrierTest {
    /*
     * 龙珠数量
     */
    private static final Integer DRAGON_BALL_NUM = 7;

    public static void main(String[] args) {
        // 参数一
        CyclicBarrier cyclicBarrier = new CyclicBarrier(DRAGON_BALL_NUM, () -> System.out.println("集齐 7 颗龙珠, 可召唤神龙"));

        for (int i = 0; i < DRAGON_BALL_NUM; i++) {
            final int temp = i;
            new Thread(() -> {
                System.out.println("已收集" + (temp + 1) + "颗龙珠");
                try {
                    cyclicBarrier.await();
                } catch (InterruptedException | BrokenBarrierException e) {
                    e.printStackTrace();
                }
            }, String.valueOf(temp)).start();
        }
    }
}
