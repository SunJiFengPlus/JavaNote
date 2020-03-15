package concurrent;


/**
 * @author 孙继峰
 * @date 2019/12/20
 */
public class ThreadTest {

    private static ThreadTest threadTest = new ThreadTest();

    public static void main(String[] args) throws Exception {
        threadTest.joinTest();
    }
    /**
     * 调用此方法的线程 wait（main线程）, 直到调用此方法的线程对象（subThread）所在的线程执行完毕后被唤醒。
     * https://juejin.im/post/5b3054c66fb9a00e4d53ef75
     */
    public void joinTest() throws Exception {
        Thread subThread = new Thread(() -> {
            try {
                Thread.sleep(1000);
                System.out.println("subThread");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        subThread.start();
        subThread.join();
        System.out.println("mainThread");
    }
}