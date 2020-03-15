package concurrent;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author 孙继峰
 * @date 2019/07/19
 */
public class ThreadPoolExecutorSourceAnalyze {

    /**
     * 高 3 位保存线程状态, 剩下 29 位保存线程数
     */
    private final AtomicInteger ctl = new AtomicInteger(ctlOf(RUNNING, 0));
    private static final int COUNT_BITS = Integer.SIZE - 3;

    /**
     * 掩码, PS 中的遮罩
     */
    private static final int COUNT_MASK = (1 << COUNT_BITS) - 1;

    /**
     *
     */
    private static final int RUNNING = -1 << COUNT_BITS;
    /**
     *
     */
    private static final int SHUTDOWN = 0 << COUNT_BITS;
    /**
     *
     */
    private static final int STOP = 1 << COUNT_BITS;
    /**
     *
     */
    private static final int TIDYING = 2 << COUNT_BITS;
    /**
     *
     */
    private static final int TERMINATED = 3 << COUNT_BITS;

    /**
     * 仅得到线程池状态的高 3 位, 其他都置零
     */
    private static int runStateOf(int c) {
        return c & ~COUNT_MASK;
    }

    /**
     * 得到线程数量的低 29 位, 其他置零
     */
    private static int workerCountOf(int c) {
        return c & COUNT_MASK;
    }

    /**
     * 将线程池状态与线程池中线程数量合并
     */
    private static int ctlOf(int rs, int wc) {
        return rs | wc;
    }

    public void execute(Runnable command) {
        if (command == null)
            throw new NullPointerException();

        int c = ctl.get();
        /*如果当前的任务数小于等于设置的核心线程大小，那么调用addWorker直接执行该任务*/
        if (workerCountOf(c) < corePoolSize) {
            if (addWorker(command, true))
                return;
            c = ctl.get();
        }
        /*如果当前的任务数大于设置的核心线程大小，而且当前的线程池状态时运行状态，那么向阻塞队列中添加任务*/
        if (isRunning(c) && workQueue.offer(command)) {
            int recheck = ctl.get();
            if (!isRunning(recheck) && remove(command))
                reject(command);
            else if (workerCountOf(recheck) == 0)
                addWorker(null, false);
        }
        /*如果向队列中添加失败，那么就新开启一个线程来执行该任务*/
        else if (!addWorker(command, false))
            reject(command);
    }
}
