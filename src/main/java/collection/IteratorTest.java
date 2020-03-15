package collection;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * foreach 遍历集合时不要对集合元素进行增删
 * 反编译查看, foreach 在编译后实际是使用 Iterator 进行遍历的
 * <p>
 * ArrayList.Itr 部分源码:
 * <p>
 * boolean hasNext() {
 * return cursor != size;
 * }
 * <p>
 * E next() {
 * checkForComodification();
 * int i = cursor;
 * if (i >= size)
 * throw new NoSuchElementException();
 * Object[] elementData = ArrayList.this.elementData;
 * if (i >= elementData.length)
 * throw new ConcurrentModificationException();
 * cursor = i + 1;
 * return (E) elementData[lastRet = i];
 * }
 *
 * @author 孙继峰
 * @date 2019/06/24
 */
public class IteratorTest {
    /**
     * 编译正常, 执行正常, 但只是一个巧合
     * <p>
     * 原因: ArrayList.Itr 内部维护了一个初始值为 0 的游标 cursor
     * <p>
     * 初始
     * cursor = 0
     * ArrayList -> one     two     three
     * <p>
     * 第一次调用 next
     * cursor       = 1
     * ArrayList -> one     two     three
     * <p>
     * 第二次调用 next
     * cursor               = 2
     * ArrayList -> one     two     three
     * <p>
     * "two".equals("two") == true 移除 "two"
     * cursor               = 2
     * ArrayList -> one     three
     * <p>
     * cursor == size, 遍历结束, 最终没有机会调用 next 方法
     * 所以就没有 ConcurrentModificationException
     */
    @Test
    public void iteratorTest1() {
        List<String> list = new ArrayList<>();
        list.add("one");
        list.add("two");
        list.add("three");
        for (String s : list) {
            if ("two".equals(s)) {
                list.remove(s);
            }
        }
        System.out.println(list);
    }

    /**
     * 编译正常, 执行抛 ConcurrentModificationException
     * <p>
     * 原因: ArrayList.Itr 内部维护了一个初始值为 0 的游标 cursor
     * <p>
     * 初始
     * cursor = 0
     * ArrayList -> one     two     three
     * <p>
     * 第一次调用 next
     * cursor       = 1
     * ArrayList -> one     two     three
     * <p>
     * 第二次调用 next
     * cursor               = 2
     * ArrayList -> one     two     three
     * <p>
     * 第三次调用 next
     * cursor                        = 3
     * ArrayList -> one     two     three
     * <p>
     * "three".equals("three") == true 移除 "three"
     * cursor                        = 3
     * ArrayList -> one     two
     * <p>
     * cursor != size, 遍历继续
     * cursor >= elementData.length 抛出 ConcurrentModificationException
     */
    @Test
    public void iteratorTest2() {
        List<String> list = new ArrayList<>();
        list.add("one");
        list.add("two");
        list.add("three");
        for (String s : list) {
            if ("three".equals(s)) {
                list.remove(s);
            }
        }
        System.out.println(list);
    }

    /**
     * 反编译查看, foreach 在编译后实际是使用 Iterator 进行遍历的
     */
    @Test
    public void decompileIteratorTest1() {
        ArrayList arrayList = new ArrayList();
        arrayList.add("one");
        arrayList.add("two");
        arrayList.add("three");
        Iterator iterator = arrayList.iterator();

        while (iterator.hasNext()) {
            String temp = (String) iterator.next();
            if ("two".equals(temp)) {
                arrayList.remove(temp);
            }
        }
        System.out.println(arrayList);
    }
}