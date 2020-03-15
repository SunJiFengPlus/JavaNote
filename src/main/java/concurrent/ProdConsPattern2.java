package concurrent;

import java.util.Objects;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static concurrent.ProdConsPattern2.Content.*;

/**
 * @author 孙继峰
 * @since 2020/3/6
 */
public class ProdConsPattern2 {

    /**
     * 线程之间顺序调用, 以 ABC 的顺序启动线程, 实现:
     * A 打印 1 次, B 打印 2 次, C 打印 3 次
     * 来3轮
     */
    public static void main(String[] args) {
        PrintController printController = new PrintController();
        new Thread(() -> {
            for (int i = 0; i < 3; i++) {
                printController.print(1, A);
            }
        }).start();
        new Thread(() -> {
            for (int i = 0; i < 3; i++) {
                printController.print(2, B);
            }
        }).start();
        new Thread(() -> {
            for (int i = 0; i < 3; i++) {
                printController.print(3, C);
            }
        }).start();
    }

    static class PrintController {
        /*
         *  一把锁, 三个钥匙
         */
        Lock lock = new ReentrantLock();
        Condition aCondition = lock.newCondition();
        Condition bCondition = lock.newCondition();
        Condition cCondition = lock.newCondition();
        Content flag = A;

        public void print(int times, Content content) {
            if (Objects.equals(A, content)) {
                lock.lock();
                // 判断
                while (flag != A) {
                    try {
                        aCondition.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                // 干活
                try {
                    for (int i = 0; i < times; i++) {
                        System.out.println(A);
                    }
                } finally {
                    // 通知
                    flag = B;
                    bCondition.signal();
                    lock.unlock();
                }
            } else if (B == content) {
                lock.lock();
                // 判断
                while (flag != B) {
                    try {
                        bCondition.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                // 干活
                try {
                    for (int i = 0; i < times; i++) {
                        System.out.println(B);
                    }
                } finally {
                    // 通知
                    flag = C;
                    cCondition.signal();
                    lock.unlock();
                }
            } else {
                lock.lock();
                // 判断
                while (flag != C) {
                    try {
                        cCondition.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                // 干活
                try {
                    for (int i = 0; i < times; i++) {
                        System.out.println(C);
                    }
                } finally {
                    // 通知
                    flag = A;
                    aCondition.signal();
                    lock.unlock();
                }
            }
        }
    }

    enum Content {
        A, B, C
    }

}
