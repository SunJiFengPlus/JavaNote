package java8;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.Consumer;

public class LambdaTest1 {

    /*
     * 左侧: 表达式的参数列表, 接口中方法的参数列表
     * 右侧: 表达式中要执行的语句, 对接口中方法的实现
     */

    /**
     * 无参数无返回值: () -> System.out.println("do something");
     */
    @Test
    public void test1() {
        new Runnable() {
            @Override
            public void run() {
                System.out.println("old");
            }
        }.run();

        Runnable r = () -> System.out.println("new");
        r.run();
    }

    /**
     * 一个参数无返回值: (asd) -> System.out.println(asd);
     */
    @Test
    public void test2() {
        Consumer<String> con = (asd) -> System.out.println(asd);
        con.accept("1");
    }

    /**
     * 两个以上参数, 有返回值, 多条语句
     */
    @Test
    public void test3() {
        Comparator<Integer> com = (x, y) -> {
            System.out.println("函数式接口");
            return Integer.compare(x, y);
        };
    }

    /**
     * 两个以上参数, 有返回值, 一条语句, 大括号与 return 可以省略
     */
    @Test
    public void test4() {
        Comparator<Integer> com = (x, y) -> Integer.compare(x, y);
    }

    /**
     * java8 参数数据类型可以不写, JVM 可以通过上下文推断出数据类型
     * 通过接口方法接收的参数类型推断出数据类型
     */
    @Test
    public void test5() {
        Comparator<Integer> com = (Integer x, Integer y) -> Integer.compare(x, y);
    }

    List<Employee> emps = Arrays.asList(
            new Employee(101, "张三", 18, 9999.99),
            new Employee(102, "李四", 59, 6666.66),
            new Employee(103, "王五", 28, 3333.33),
            new Employee(104, "赵六", 8, 7777.77),
            new Employee(105, "田七", 38, 5555.55)
    );

    public List<Employee> filterEmployee(List<Employee> emps, MyPredicate<Employee> mp) {
        List<Employee> list = new ArrayList<>();

        for (Employee employee : emps) {
            if (mp.test(employee)) {
                list.add(employee);
            }
        }
        return list;
    }

    /**
     * 列出所有年龄小于等于 35 的员工
     */
    @Test
    public void test6() {
        List<Employee> list = filterEmployee(emps, (e) -> e.getAge() <= 35);
        list.forEach(System.out::println);
    }

}
