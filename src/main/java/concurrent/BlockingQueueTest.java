package concurrent;

import org.junit.jupiter.api.Test;

import java.util.concurrent.SynchronousQueue;

/**
 * @author 孙继峰
 * @date 2019/12/24
 */
public class BlockingQueueTest {
    /**
     * 敏感方法
     * 1. add: 队列已满时抛异常
     * 2. remove: 队列为空时抛异常
     * 3. element: 队列为空时抛异常
     *
     * 非敏感方法
     * 1. offer: 队列已满是返回 false
     * 2. poll: 队列为空时返回 null
     * 3. peek: 队列为空时返回 null
     *
     * 阻塞方法
     * 1. put: 队列已满是阻塞
     * 2. take: 队列为空时阻塞
     *
     * 阻塞超时方法
     * 1. offer(E, long, TimeUnit): 队列已满时阻塞一段时间
     * 2. poll(long, TimeUnit): 队列为空时
     */
    public static void main(String[] args) {
    }

    /**
     * 没有容量, 不存储元素
     * 每次 put 方法都要等待一个 take, 每一个 take 都要有一个 put
     */
    @Test
    public void synchronousQueueTest() {
        SynchronousQueue<String> queue = new SynchronousQueue<>();
    }

}
