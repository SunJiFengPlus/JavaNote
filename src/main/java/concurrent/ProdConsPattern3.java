package concurrent;

import java.util.concurrent.BlockingQueue;

/**
 * @author 孙继峰
 * @since 2020/3/8
 */
public class ProdConsPattern3<E> {

    private BlockingQueue<E> queue;

    public ProdConsPattern3(BlockingQueue<E> queue) {
        this.queue = queue;
    }

    public void product(E e) throws InterruptedException {
        queue.put(e);
    }

    public E consume() throws InterruptedException {
        return queue.take();
    }
}
