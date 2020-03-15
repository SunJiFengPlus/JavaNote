package relfect;

public class ReflectionTest2 {
    /*
     *  Class 的实例只创建一次
     *  如何获取 Class 的实例
     *  1 调用运行时类本身的 class 属性
     *  2 通过运行时类的对象获取
     *  3 通过 Class 的静态方法获取
     *  4 通过类加载器
     */
    public void asd() throws ClassNotFoundException {
        Class clazz1 = Person.class;//1

        Person p = new Person();
        Class clazz2 = p.getClass();//2

        String className = "reflect.ReflectoinTest2";
        Class clazz3 = Class.forName(className);//3

        ClassLoader classLoader = this.getClass().getClassLoader();
        Class clazz4 = classLoader.loadClass(className);//4
    }
}
