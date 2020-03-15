package concurrent;

import java.util.concurrent.locks.ReentrantReadWriteLock;

/*
 * 1 个线程写入, 100 个线程读取
 */
public class ReentrantReadWriteLockTest {
    public static void main(String[] args) throws InterruptedException {

        MyQueen queen = new MyQueen();

        new Thread(() -> queen.write(1024), "A").start();

        Thread.sleep(100);

        for (int i = 0; i < 100; i++) {
            new Thread(queen::read, String.valueOf(i)).start();
        }
    }

    static class MyQueen {
        private Integer obj;
        /**
         * 读-读 可以同时加锁
         * 读-写 不可以同时加锁
         * 写-写 不可以同时加锁
         */
        private ReentrantReadWriteLock rwLock = new ReentrantReadWriteLock();

        public void read() {
            try {
                rwLock.readLock().lock();
                System.out.println(Thread.currentThread().getName() + "-" + obj);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                rwLock.readLock().unlock();
            }

        }

        public void write(Integer i) {
            try {
                rwLock.writeLock().lock();
                this.obj = i;
                System.out.println(Thread.currentThread().getName() + "write" + i);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                rwLock.writeLock().unlock();
            }
        }
    }
}
