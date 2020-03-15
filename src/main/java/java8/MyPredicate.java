package java8;

/**
 * java8 表达式需要函数式接口支持, 接口中只有一个方法的接口是函数接口
 * 添加 @FunctionalInterface 注解可以使编译器帮助检查是否为函数接口
 */
@FunctionalInterface
public interface MyPredicate<T> {

    public boolean test(T t);
}
