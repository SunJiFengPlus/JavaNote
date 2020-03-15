package java8;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

/**
 * @author 孙继峰
 * @date 2019/06/02
 */
public class StreamTest1 {

    /**
     * 创建
     */
    @Test
    public void test1() {
        // 获取 Stream, Collection.stream() 或 Collection.parallelStream() 方法
        List<String> list = new ArrayList<>();
        Stream<String> stream1 = list.stream();

        // 获取 Stream, Arrays.stream() 方法
        Integer[] array = new Integer[5];
        Stream<Integer> stream2 = Arrays.stream(array);

        // 获取 Stream, Stream.of()
        Stream<String> stream3 = Stream.of("1", "2", "3", "4");

        // 获取无限流, 迭代
        Stream<Integer> stream4 = Stream.iterate(1, (t) -> t + 2);
        stream4.limit(5).forEach(System.out::println);

        // 获取无限流, 生成
        Stream<Double> stream5 = Stream.generate(() -> Math.random());
        stream5.limit(5).forEach(System.out::println);
    }
}
