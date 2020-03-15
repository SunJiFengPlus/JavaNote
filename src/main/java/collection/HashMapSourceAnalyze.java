package collection;

import javax.swing.tree.TreeNode;
import java.util.HashMap;

/**
 * table: 储存所有数据的数组
 * slot: 哈希槽, 即 table[i] 的位置
 * bucket: 哈希桶, table[i] 上所有元素形成的链表或红黑树
 * <p>
 * Object 类定义中对 hashCode 和 equals 要求如下:
 * 1.如果两个对象 equal, 则 hashCode 也必须相同
 * 2.任何时候重写 equals, 都必须同时重写 hashCode
 * <p>
 * Object.hashCode: 内存地址
 * Integer.hashCode: int 值
 * String.hashCode(byte[] value):
 * int result = 0;
 * for (byte v : value) {
 * // v & 0xff 保持补码一致性
 * result = 31 * h + (v & 0xff);
 * }
 * HashMapMethod.hash(Object key):
 * key.hashCode() ^ (key.hashCode() >>> 16)
 *
 * @author 孙继峰
 * @date 2019/06/05
 */
public class HashMapSourceAnalyze {
    public static void main(String[] args) {

    }

    /**
     * 默认的初始容量 16
     */
    static final int DEFAULT_INITIAL_CAPACITY = 1 << 4;

    /**
     * map的最大容量
     */
    static final int MAXIMUM_CAPACITY = 1 << 30;

    /**
     * 默认负载因子
     */
    static final float DEFAULT_LOAD_FACTOR = 0.75f;

    /**
     * 链表长度大于8时，转换为红黑树结构
     */
    static final int TREEIFY_THRESHOLD = 8;

    /**
     * 树还原链表阈值
     */
    static final int UNTREEIFY_THRESHOLD = 6;

    /**
     * map某个桶在由链表转为树之前还要满足键值对数量大于 64 才会发生转换
     * 目的是为了避免 resizing（扩容） 和 treeification（链表转树结构）之间的冲突
     */
    static final int MIN_TREEIFY_CAPACITY = 64;

    /**
     * tableSizeFor(10) = 16
     * tableSizeFor(20) = 32
     * tableSizeFor(40) = 64
     */
    static final int tableSizeFor(int cap) {
        int n = -1 >>> Integer.numberOfLeadingZeros(cap - 1);
        return (n < 0) ? 1 : (n >= MAXIMUM_CAPACITY) ? MAXIMUM_CAPACITY : n + 1;
    }

    /**
     * @param hash         key 的哈希值
     * @param key          key
     * @param value        value
     * @param onlyIfAbsent 如果为 true, 不改变以存在的 value
     * @param evict        如果为 false, table 处于创建模式
     * @return previous value, or null if none
     */
    final V putVal(int hash, K key, V value, boolean onlyIfAbsent, boolean evict) {
        Node<K, V>[] tab;
        Node<K, V> p;
        // n: table 长度, i: 哈希槽
        int n, i;
        // 初始化
        if ((tab = table) == null || (n = tab.length) == 0)
            n = (tab = resize()).length;
        /*
         * (n - 1) & hash: 计算哈希槽, 之前版本的 jdk 是以 indexFor 函数计算的
         * table 中目标目标哈希槽的桶中是空的, 也就是后面没有挂载链表, 没有数据
         * 则直接创建 node 对象，挂载数据
         */
        if ((p = tab[i = (n - 1) & hash]) == null) {
            tab[i] = newNode(hash, key, value, null);
        }
        // table 中目标哈希槽的桶中有节点时, 进行链表或红黑树的插入
        else {
            Node<K, V> e;
            K k;
            // 插入元素的 key 与桶中第一个节点的 key 相同, 在之后做处理
            if (p.hash == hash && ((k = p.key) == key || (key != null && key.equals(k))))
                e = p;
                // 桶中是红黑树结构时, 加入到红黑树中
            else if (p instanceof TreeNode)
                e = ((TreeNode<K, V>) p).putTreeVal(this, tab, hash, key, value);
                // 桶中是链表结构时
            else {
                for (int binCount = 0; ; ++binCount) {
                    if ((e = p.next) == null) {
                        // 加入链表中
                        p.next = newNode(hash, key, value, null);
                        // 桶中节点个数满足阈值, 转化成红黑树
                        if (binCount >= TREEIFY_THRESHOLD - 1)
                            treeifyBin(tab, hash);
                        break;
                    }
                    // 插入元素的 key 与桶中元素 key 相同, 在之后做处理
                    if (e.hash == hash && ((k = e.key) == key || (key != null && key.equals(k))))
                        break;
                    p = e;
                }
            }
            // key 相同时更新 value
            if (e != null) {
                V oldValue = e.value;
                if (!onlyIfAbsent || oldValue == null)
                    e.value = value;
                afterNodeAccess(e);
                return oldValue;
            }
        }
        ++modCount;
        // 超过阈值，则扩充 map 大小
        if (++size > threshold)
            resize();
        afterNodeInsertion(evict);
        return null;
    }

    /**
     * 初始化或扩容 table, HashMap *2 扩容, 这样计算哈希槽更快
     *
     * @return table 数组
     */
    final Node<K, V>[] resize() {
        Node<K, V>[] oldTab = table;
        int oldCap = (oldTab == null) ? 0 : oldTab.length;
        int oldThr = threshold;
        int newCap, newThr = 0;
        if (oldCap > 0) {
            // 容量达到最大时不在扩容
            if (oldCap >= MAXIMUM_CAPACITY) {
                threshold = Integer.MAX_VALUE;
                return oldTab;
            } else if ((newCap = oldCap << 1) < MAXIMUM_CAPACITY && oldCap >= DEFAULT_INITIAL_CAPACITY)
                // 阈值翻倍
                newThr = oldThr << 1;
        }
        // initial capacity was placed in threshold
        else if (oldThr > 0)
            newCap = oldThr;
            // zero initial threshold signifies using defaults
        else {
            newCap = DEFAULT_INITIAL_CAPACITY;
            newThr = (int) (DEFAULT_LOAD_FACTOR * DEFAULT_INITIAL_CAPACITY);
        }
        if (newThr == 0) {
            float ft = (float) newCap * loadFactor;
            newThr = (newCap < MAXIMUM_CAPACITY && ft < (float) MAXIMUM_CAPACITY ?
                    (int) ft : Integer.MAX_VALUE);
        }
        threshold = newThr;
        @SuppressWarnings({"rawtypes", "unchecked"})
        Node<K, V>[] newTab = (Node<K, V>[]) new HashMap.Node[newCap];
        table = newTab;
        if (oldTab != null) {
            // 遍历旧 table, 将元素复制到新 table
            for (int j = 0; j < oldCap; ++j) {
                Node<K, V> e;
                if ((e = oldTab[j]) != null) {
                    oldTab[j] = null;
                    // 旧 table 桶中只有一个元素时, 直接赋值
                    if (e.next == null)
                        newTab[e.hash & (newCap - 1)] = e;
                        // 旧 table 桶中是红黑树结构, 修剪树
                    else if (e instanceof HashMap.TreeNode)
                        ((HashMap.TreeNode<K, V>) e).split(this, newTab, j, oldCap);
                        // 旧 table 桶中是链表结构, 真看不懂
                    else { // preserve order??? 链表前序遍历?
                        Node<K, V> loHead = null, loTail = null;
                        Node<K, V> hiHead = null, hiTail = null;
                        Node<K, V> next;
                        // 构建链表
                        do {
                            next = e.next;
                            if ((e.hash & oldCap) == 0) {
                                if (loTail == null)
                                    loHead = e;
                                else
                                    loTail.next = e;
                                loTail = e;
                            } else {
                                if (hiTail == null)
                                    hiHead = e;
                                else
                                    hiTail.next = e;
                                hiTail = e;
                            }
                        } while ((e = next) != null);
                        if (loTail != null) {
                            loTail.next = null;
                            newTab[j] = loHead;
                        }
                        if (hiTail != null) {
                            hiTail.next = null;
                            newTab[j + oldCap] = hiHead;
                        }
                    }
                }
            }
        }
        return newTab;
    }
}
