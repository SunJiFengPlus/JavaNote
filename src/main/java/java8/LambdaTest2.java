package java8;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * @author 孙继峰
 * @date 2019/06/01
 */
public class LambdaTest2 {
    /**
     * Consumer<T>: 消费型接口
     * void accept(T t);
     */
    @Test
    public void test1() {
        consume(1000, (money) -> System.out.println("大保健消费 " + money + "元"));
    }

    private void consume(int money, Consumer<Integer> consumer) {
        // java8.LambdaTest2$$Lambda$36/0x00000001000a3040
        System.out.println(consumer.getClass().getName());
        consumer.accept(money);
    }

    /**
     * Supplier<T>: 供给型接口
     * T get();
     */
    @Test
    public void test2() {
        // 生成 num 个随机数
        Random random = new Random();
        List<Integer> list = getList(30, () -> random.nextInt(100));
        getList(20, () -> random.nextInt(100));
        System.out.println(list);
    }

    /**
     * 生成 num 个整数
     */
    private List<Integer> getList(int num, Supplier<Integer> supplier) {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < num; i++) {
            list.add(supplier.get());
        }
        return list;
    }

    /**
     * Function<T,R>: 函数型接口
     * R apply(T t);
     */
    @Test
    public void test3() {
        // 获取字符串长度
        int length = handleString("qwe", (t) -> t.length());
        System.out.println(length);
    }

    /**
     * 处理字符串
     */
    private int handleString(String string, Function<String, Integer> function) {
        return function.apply(string);
    }

    /**
     * Predicate<T>: 断言型接口
     * boolean test(T t);
     */
    @Test
    public void test4() {
        List<String> list = Arrays.asList("1", "2", "3", "123", "456", "789");
        // 过滤出长度大于等于 3 的字符串
        List<String> res = filterString(list, (t) -> t.length() >= 3);
        System.out.println(res);
    }

    /**
     * 过滤字符串集合
     */
    private List<String> filterString(List<String> stringList, Predicate<String> predicate) {
        List<String> res = new ArrayList<>();
        for (String str : stringList) {
            if (predicate.test(str)) {
                res.add(str);
            }
        }
        return res;
    }

    /**
     *  其他函数式接口
     *
     *  BiFunction<T,U,R>
     *      R apply(T t, U u);
     *
     *  UnaryOperator<T>
     *      T apply(T t);
     *
     *  BinaryOperator<T>
     *      T applay(T t1, T t2);
     *
     *  BiConsumer<T,U>
     *      void accept(T t, U u);
     */
}
