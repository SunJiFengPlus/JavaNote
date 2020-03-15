package java8;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author 孙继峰
 * @date 2019/07/02
 */
public class StreamTest3 {
    /**
     * flatMap可以把两个流合并成一个流进行操作
     * 对字符串数组 ["hello", "world"] 输出其去重后的字符序列
     */
    @Test
    public void test() {
        String[] strArr = new String[]{"hello", "world"};
        List<String> strList = Arrays.asList(strArr);
        strList.stream().map(s -> s.split(""))
                .flatMap(Arrays::stream)
                .distinct()
                .forEach(a -> System.out.print(a + " "));
    }

    /*
     * Stream 的其他方法
     * boolean allMatch(Predicate<? super T> predicate);
     * boolean anyMatch(Predicate<? super T> predicate);
     * boolean noneMatch(Predicate<? super T> predicate);
     * Optional<T> findFirst();
     * Optional<T> findAny();
     */

    /**
     * 对集合元素求和
     * <p>
     * T reduce(T identity, BinaryOperator<T> accumulator)
     * identity: 初始值
     * accumulator: 累加器
     */
    @Test
    public void sum() {
        List<Integer> number = Arrays.asList(3, 5, 8, 4, 2, 13);
        int sum = number.stream().reduce(0, Integer::sum);
        System.out.println(sum);
    }

    /**
     * fork join
     *
     * <U> U reduce(U identity, BiFunction<U, ? super T, U> accumulator, BinaryOperator<U> combiner);
     * identity: 初始值
     * accumulator: 累加器
     * combiner: 合并器 (join)
     */
    @Test
    public void forkJoin() {
        List<Integer> nums = new ArrayList<>();
        int s = 0;
        for (int i = 0; i < 200; i++) {
            nums.add(i);
            s = s + i;
        }

        int sum = nums.parallelStream().reduce(0, Integer::sum, Integer::sum);
        System.out.println(sum);
    }
}