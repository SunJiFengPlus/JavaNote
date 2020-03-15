package collection;

import java.util.concurrent.ConcurrentHashMap;

/**
 * ConcurrentHashMap 与 HashMap 的数据结构相同
 * <p>
 * 非阻塞同步算法与 CAS(Compare&Swap) 语义:
 * 我认为 V 的值应该为 a，如果是，那么将 V 的值更新为 b，否则不修改并告诉 V 的值实际为多少
 * <p>
 * Java8 后以 CAS 替换了分段锁
 *
 * @author 孙继峰
 * @date 2019/06/09
 */
public class ConcurrentHashMapSourceAnalyze {
    /**
     * 默认并发数
     */
    private static final int DEFAULT_CONCURRENCY_LEVEL = 16;
    transient volatile Node<K, V>[] table;
    /**
     * 仅在 resize 时为非空
     */
    private transient volatile Node<K, V>[] nextTable;
    /**
     * hash 值是-1，表示这是一个 forwardNode 节点
     */
    static final int MOVED = -1;
    /**
     * hash 值是-2  表示这时一个 TreeBin 节点
     */
    static final int TREEBIN = -2;
    /**
     * 该属性保存着整个哈希表中存储的所有的结点的个数总和，有点类似于 HashMap 的 size 属性
     */
    private transient volatile long baseCount;
    /**
     * 该属性有以下几种取值：
     * 负数代表正在进行初始化或扩容操作
     * -1 代表正在初始化
     * -N 表示有 N-1 个线程正在进行扩容操作
     * 正数或 0 代表 hash 表还没有被初始化，这个数值表示初始化或下一次进行扩容的阈值
     * 它的值始终是当前 ConcurrentHashMap 容量的 0.75 倍，这与 LOAD_FACTOR 是对应的
     */
    private transient volatile int sizeCtl;

    /**
     * 线程迁移 bin 的起始位置，CAS(transferIndex) 成功者可迁移 transferIndex 前置 stride 个 bin
     */
    private transient volatile int transferIndex;

    /**
     * 获得在 i 位置上的 Node 节点
     *
     * @param tab table
     * @param i   内存位置
     */
    static final <K, V> Node<K, V> tabAt(Node<K, V>[] tab, int i) {
        return (Node<K, V>) U.getObjectAcquire(tab, ((long) i << ASHIFT) + ABASE);
    }

    /**
     * CAS 设置 i 位置的值
     *
     * @param tab table
     * @param i   内存位置
     * @param c   预期原值
     * @param v   新值
     * @return success
     */
    static final <K, V> boolean casTabAt(Node<K, V>[] tab, int i, Node<K, V> c, Node<K, V> v) {
        return U.compareAndSetObject(tab, ((long) i << ASHIFT) + ABASE, c, v);
    }

    /**
     * 设置节点的值
     *
     * @param tab table
     * @param i   内存位置
     * @param v   新值
     */
    static final <K, V> void setTabAt(Node<K, V>[] tab, int i, Node<K, V> v) {
        U.putObjectRelease(tab, ((long) i << ASHIFT) + ABASE, v);
    }

    /**
     * 初始化 table, 只允许一个线程对表进行初始化，如果有其他线程进来了，那么会让其他线程交出 CPU 等待下次系统调度
     */
    private final Node<K, V>[] initTable() {
        Node<K, V>[] tab;
        int sc;
        while ((tab = table) == null || tab.length == 0) {
            // sizeCtl 小于零说明已经有线程正在进行初始化操作
            if ((sc = sizeCtl) < 0)
                // 放弃本次 CPU 时间, 与其他优先级相同的线程共同竞争 CPU
                Thread.yield();
                // CAS 将 sizeCtl 设置为 -1，代表抢到了锁
            else if (U.compareAndSetInt(this, SIZECTL, sc, -1)) {
                try {
                    if ((tab = table) == null || tab.length == 0) {
                        // sc 大于零表示: 已经初始化过了, 否则使用默认初始容量
                        int n = (sc > 0) ? sc : DEFAULT_CAPACITY;
                        @SuppressWarnings("unchecked")
                        Node<K, V>[] nt = (Node<K, V>[]) new Node<?, ?>[n];
                        table = tab = nt;
                        // 如果 n 是 16, sc 就是 12, 0.75*n
                        sc = n - (n >>> 2);
                    }
                } finally {
                    sizeCtl = sc;
                }
                break;
            }
        }
        return tab;
    }

    /**
     *
     */
    private final void treeifyBin(Node<K, V>[] tab, int index) {
        Node<K, V> b;
        int n;
        if (tab != null) {
            if ((n = tab.length) < MIN_TREEIFY_CAPACITY)
                tryPresize(n << 1);
            else if ((b = tabAt(tab, index)) != null && b.hash >= 0) {
                synchronized (b) {
                    if (tabAt(tab, index) == b) {
                        ConcurrentHashMap.TreeNode<K, V> hd = null, tl = null;
                        for (Node<K, V> e = b; e != null; e = e.next) {
                            ConcurrentHashMap.TreeNode<K, V> p =
                                    new ConcurrentHashMap.TreeNode<K, V>(e.hash, e.key, e.val, null, null);
                            if ((p.prev = tl) == null)
                                hd = p;
                            else
                                tl.next = p;
                            tl = p;
                        }
                        setTabAt(tab, index, new ConcurrentHashMap.TreeBin<K, V>(hd));
                    }
                }
            }
        }
    }
}