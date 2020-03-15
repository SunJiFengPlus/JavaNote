package relfect;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class ReflectionTest1 {

    /**
     * 获取 Class 实例之后可以获取运行时类的完整结构(属性 方法 构造器  内部类 父类 所在包 异常 注释)
     * 调用指定结构(属性 方法 构造器)
     */
    public static void main(String[] args) throws Exception {

        Class<Person> clazz = Person.class;

        // 1 创建运行时类 Person 类的对象
        Person p = clazz.newInstance();
        Person p2 = clazz.newInstance();
        System.out.println(p);

        // 2 通过反射调用运行时类的指定属性
        // 当 Person 类的 name 属性为 public 时可以使用 get 方法来获取值
        Field field1 = clazz.getField("name");
        // 通过 set 方法来设置值
        field1.set(p2, "ASD");
        field1.set(p, "ASD");
        System.out.println(p2 + "************");

        Field f2 = clazz.getDeclaredField("age");
        f2.setAccessible(true);
        f2.set(p, 20);
        System.out.println(p);

        // 3 通过反射调用运行时类指定的方法 调用运行时类的无参数的方法
        Method m1 = clazz.getMethod("ssss");
        m1.invoke(p);

        // getMethod 的第一个参数为 String 类型的方法名, 后面的参数为该方法的参数类型的 class 对象
        Method m2 = clazz.getMethod("show", String.class);
        // invoke(调用方法的对象,用于方法调用的参数)
        m2.invoke(p, "东北");
    }
}
