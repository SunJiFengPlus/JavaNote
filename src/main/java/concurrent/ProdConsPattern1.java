package concurrent;

/**
 * 生产消费者模式
 *
 * @author 孙继峰
 * @since 2019/12/27
 */
public class ProdConsPattern1 {
    /*
     * 生产者-消费者模式
     * 两个线程, 操作初始值为 0 的变量, 一个线程对变量加 1, 一个线程对变量减 1, 交替 10 轮
     */
    public static void main(String[] args) {
        ShareData sd = new ShareData();

        // 生产者
        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                try {
                    sd.increament();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "A").start();

        // 消费者
        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                try {
                    sd.decreament();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "B").start();
    }

    static class ShareData {
        private Integer num = 0;

        public synchronized void increament() throws InterruptedException {
            // 判断, 多线程中的判断只能用循环, 以防止虚假唤醒
            while (num.equals(1)) {
                this.wait();
            }
            // 操作
            num++;
            System.out.println(Thread.currentThread().getName() + "执行了加 1");
            // 通知
            this.notifyAll();
        }

        public synchronized void decreament() throws InterruptedException {
            while (num.equals(0)) {
                this.wait();
            }
            num--;
            System.out.println(Thread.currentThread().getName() + "执行了减 1");
            this.notifyAll();
        }
    }
}
