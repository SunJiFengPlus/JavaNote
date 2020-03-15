package java8;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author 孙继峰
 * @date 2019/06/02
 */
public class StreamTest2 {

    /**
     * filter 接收 lambda 表达式, 从流中删除某些元素
     */
    @Test
    public void test2() {
        // 中间操作, 不会执行任何操作
        Stream<Employee> stream = emps.stream().filter((e) -> {
            System.out.println("中间操作");
            return e.getAge() > 35;
        });
        // 终止操作, 一次性执行全部内容, 惰性求值
        stream.forEach(System.out::println);
    }

    /**
     * limit(n) 截断流, 使元素数量不超过 n
     */
    @Test
    public void test3() {
        Stream<Employee> stream = emps.stream()
                .filter((e) -> e.getAge() > 35)
                // 迭代时找到足够数量的满足条件的元素时, 会终止迭代
                .limit(1);
        stream.forEach(System.out::println);
    }

    /**
     * skip(n) 扔掉前 n 个元素, 若不足 n 个, 则返回空流
     */
    @Test
    public void test4() {
        Stream<Employee> stream = emps.stream()
                .skip(2);
        stream.forEach(System.out::println);
    }

    /**
     * distinct 去重, 去重根据 hashCode 与 equals 方法进行判定
     */
    @Test
    public void test5() {
        Stream<Employee> stream = emps.stream()
                .distinct();
        stream.forEach(System.out::println);
    }

    /**
     * map 类比数据库操作，只选择一个表中的某一列
     */
    @Test
    public void test6() {
        List<String> list1 = emps.stream()
                .map(Employee::getName)
                .collect(Collectors.toList());

        List<String> list2 = emps.stream()
                .map((Employee e) -> e.getName())
                .collect(Collectors.toList());

        List<String> list3 = emps.stream()
                .map(e -> e.getName())
                .collect(Collectors.toList());
    }

    private List<Employee> emps = Arrays.asList(
            new Employee(101, "张三", 18, 9999.99),
            new Employee(102, "李四", 59, 6666.66),
            new Employee(103, "王五", 28, 3333.33),
            new Employee(104, "赵六", 8, 7777.77),
            new Employee(105, "田七", 38, 5555.55),
            new Employee(105, "田七", 38, 5555.55)
    );
}
