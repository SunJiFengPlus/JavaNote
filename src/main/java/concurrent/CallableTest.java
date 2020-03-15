package concurrent;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * 实现线程的方式
 * 1. 继承 Thread 类
 * 2. 实现 Runnable 接口
 * 3. 实现 Callable 接口, 通过 FetureTask 包装 Callable 接口来创建线程
 * <p>
 * 在主线程中需要执行比较耗时的操作时，但又不想阻塞主线程时，可以把这些作业交给Future对象在后台完成，
 * 当主线程将来需要时，就可以通过Future对象获得后台作业的计算结果或者执行状态。
 * <p>
 * 一般FutureTask多用于耗时的计算，主线程可以在完成自己的任务后，再去获取结果。
 * <p>
 * 仅在计算完成时才能检索结果；如果计算尚未完成，则阻塞 get 方法。一旦计算完成，
 * 就不能再重新开始或取消计算。get方法而获取结果只有在计算完成时获取，否则会一直阻塞直到任务转入完成状态，
 * 然后会返回结果或者抛出异常。
 * <p>
 * 只计算一次 get方法放到最后
 */
public class CallableTest {
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        // FutureTask 用于异步调用, 特点: 无论用 FeatureTask 启动多少个线程都只会运行一次, 结果可复用
        FutureTask<Integer> task = new FutureTask<>(() -> {
            System.out.println("invoke");
            return 200;
        });
        task.run();
        // get 方法要最后调用, 调用 get 方法会导致线程阻塞, 直至分支线程运行完返回结果
        System.out.println(task.get());
    }
}