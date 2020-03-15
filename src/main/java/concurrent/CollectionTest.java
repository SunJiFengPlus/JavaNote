package concurrent;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;

public class CollectionTest {

    public static void main(String[] args) {
        /**
         *  List -> CopyOnWriteArrayList
         *  Set  -> CopyOnWriteArraySet 
         *  Map  -> ConcurrentHashMap
         *
         *  CopyOnWriteArrayList 写时复制容器, 其内维护着一个 Object[], 当向容器中加入一个元素时
         *  会将原有的 Object[n] 拷贝到一个新创建的 Object[n+1], 设置将要插入的元素插入新数组的尾部
         *  以新数组替换原内部维护的 Object[], 读写分离的思想, 读写用不同的容器
         *  并发读时不需要加锁, 写时加锁
         */
        List<String> list = new CopyOnWriteArrayList<>();
        for (int i = 0; i < 30; i++) {
            new Thread(() -> {
                list.add(UUID.randomUUID().toString().substring(0, 4));
                System.out.println(list);
            }, String.valueOf(i)).start();
        }
    }
}