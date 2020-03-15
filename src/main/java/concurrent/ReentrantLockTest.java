package concurrent;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockTest {

    /*
     * 三个售票员卖出 30 张票
     *
     * 线程操作资源类 高内聚低耦合
     */
    public static void main(String[] args) {
        Ticket ticket = new Ticket();

        // 1.8 以后的写法
        // Java 多线程的调度取决于 CPU 与操作系统, 所以不一定是线程 A 先启动
        new Thread(() -> {
            for (int i = 0; i < 40; i++) {
                ticket.sale();
            }
        }, "AA").start();

        new Thread(() -> {
            for (int i = 0; i < 40; i++) {
                ticket.sale();
            }
        }, "BB").start();

        new Thread(() -> {
            for (int i = 0; i < 40; i++) {
                ticket.sale();
            }
        }, "CC").start();
    }

    // 资源类
    static class Ticket {
        private Integer num = 30;
        // 可复入锁
        private Lock lock = new ReentrantLock();

        public void sale() {
            lock.lock();
            try {
                if (num > 0) {
                    System.out.println(Thread.currentThread().getName() + " 卖出第" + (num--) + "张票, 还剩" + num + "张票");
                }
            } finally {
                lock.unlock();
            }
        }
    }

    static class ReLock {
        public static void main(String[] args) {
            ReentrantLock lock = new ReentrantLock();
            /*
             * ReentrantLock 锁或者不锁的状态都取决于它的 state 属性
             * 非公平锁的 lock() 会调用 acquire(1); 把 state 置为 1
             * 当同一个线程调用 lock() 两次, state 会变为 2
             */
            lock.lock();
            lock.lock();
            try {
                System.out.println("do something");
            } finally {
                lock.unlock();
            }
        }
    }
}