package concurrent;

public class Helloworld {

    /**
     * 进程 - winworld.exe
     * 线程 - 容灾线程, 语法检查线程
     * <p>
     * 线程状态
     * NEW      - Thread t = new Thread();
     * RUNNABLE - t.start();
     * BLOCKED  - 阻塞
     * WAITING  - 不见不散
     * TIMED_WAITING - 过期不候
     * TERMINATED - 终结
     * <p>
     * t.wait() 时释放锁
     * t.sleep() 时不释放锁
     * <p>
     * 并发 - 小米手机 8 点开售, 多个线程访问同一个资源
     * 并行 - 泡面, 烧水的同时拆方便面包装、撕调料包
     */
    public static void main(String[] args) {

    }
}
