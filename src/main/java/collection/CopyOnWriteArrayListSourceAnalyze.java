package collection;

import java.util.Arrays;

/**
 * 读效率高, 写效率低
 *
 * @author 孙继峰
 * @date 2019/06/09
 */
public class CopyOnWriteArrayListSourceAnalyze {
    /**
     * 写时复制, 在 add 方法完成之前好之前访问的还是旧的
     */
    public boolean add(E e) {
        synchronized (lock) {
            Object[] es = getArray();
            int len = es.length;
            // 扩容时容量加 1
            es = Arrays.copyOf(es, len + 1);
            es[len] = e;
            setArray(es);
            return true;
        }
    }
}
