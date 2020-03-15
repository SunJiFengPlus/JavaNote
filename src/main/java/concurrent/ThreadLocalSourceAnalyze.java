package concurrent;

import java.lang.ref.WeakReference;

/**
 * @author 孙继峰
 * @date 2019/07/08
 */
public class ThreadLocalSourceAnalyze {

    /**
     * Thread 中定义的属性
     */
    ThreadLocal.ThreadLocalMap threadLocals = null;

    /**
     * Entry 在 ThreadLocalMap 中的定义
     * 继承了弱引用, 将在下一次 GC 中被回收
     */
    static class Entry extends WeakReference<ThreadLocal<?>> {
        /**
         * The value associated with this ThreadLocal.
         */
        Object value;

        Entry(ThreadLocal<?> k, Object v) {
            super(k);
            value = v;
        }
    }

    /**
     * ThreadLocal.set(T value)
     * 实质上是 ThreadLocal 把对象装进 Thread 的 threadLocals 的 Entry 数组中
     * ThreadLocal 其实不存储对象
     */
    public void set(T value) {
        Thread t = Thread.currentThread();
        ThreadLocal.ThreadLocalMap map = getMap(t);
        if (map != null) {
            map.set(this, value);
        } else {
            createMap(t, value);
        }
    }

    /**
     * ThreadLocalMap 采用线性探测法来解决哈希冲突, 可以把 ThreadLocal.table 看作一个环形数组
     * 如果哈希出的 index 一样, key 不一样, 将会判断 table[index + 1] 是否合适,
     * 如果何时就使用, 不合适就 table[index + 2], 直到哈希槽中为空
     */
    private void set(ThreadLocal<?> key, Object value) {
        Entry[] tab = table;
        int len = tab.length;
        // 以 ThreadLocal 对象算出 index
        int i = key.threadLocalHashCode & (len - 1);

        // 根据获取到的索引，进行线性探测, 直到没有哈希冲突
        for (Entry e = tab[i]; e != null; e = tab[i = nextIndex(i, len)]) {
            ThreadLocal<?> k = e.get();
            // 线性探测过程发现桶中 key 相同, 则覆盖 value
            if (k == key) {
                e.value = value;
                return;
            }

            // 线性探测过程中发现已经被回收了 key 的 entry, 执行替换
            if (k == null) {
                replaceStaleEntry(key, value, i);
                return;
            }
        }

        // 线性探测结束, 找到 table 中的空位置, 赋值
        tab[i] = new Entry(key, value);
        int sz = ++size;
        // 清除那些 entry.get()==null，也就是 table[index] != null && table[index].get()==null
        // 之前提到过，这种数据key关联的对象已经被回收，所以这个Entry(table[index])可以被置null。
        // 如果没有清除任何 entry, 并且当前使用量达到了负载因子所定义，那么进行 rehash()
        if (!cleanSomeSlots(i, sz) && sz >= threshold)
            rehash();
    }

    /**
     * 替换掉被回收的 key 的 entry
     *
     * @param value     新的 value
     * @param staleSlot 要被替换的 table 索引值
     */
    private void replaceStaleEntry(ThreadLocal<?> key, Object value,
                                   int staleSlot) {
        Entry[] tab = table;
        int len = tab.length;
        Entry e;

        // 一次清理一段被回收 key 的 entry
        // 向前找出一个连续且可回收的 entry 段
        int slotToExpunge = staleSlot;
        for (int i = prevIndex(staleSlot, len); (e = tab[i]) != null; i = prevIndex(i, len))
            if (e.get() == null)
                slotToExpunge = i;

        // 向后找出一个连续且可回收的 entry 段
        for (int i = nextIndex(staleSlot, len); (e = tab[i]) != null; i = nextIndex(i, len)) {
            ThreadLocal<?> k = e.get();

            // 如果向后找到了 key, 替换
            if (k == key) {
                e.value = value;

                tab[i] = tab[staleSlot];
                tab[staleSlot] = e;

                // Start expunge at preceding stale entry if it exists
                if (slotToExpunge == staleSlot)
                    slotToExpunge = i;
                cleanSomeSlots(expungeStaleEntry(slotToExpunge), len);
                return;
            }

            // If we didn't find stale entry on backward scan, the
            // first stale entry seen while scanning for key is the
            // first still present in the run.
            if (k == null && slotToExpunge == staleSlot)
                slotToExpunge = i;
        }
    }
