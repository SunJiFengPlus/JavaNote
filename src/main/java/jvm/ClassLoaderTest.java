package jvm;

/**
 * JVM 不直接与硬件交互
 * <p>
 * BootStrap Class Loader
 * |
 * Extension ClassLoader
 * |
 * Application ClassLoader
 * |
 * User-Defined ClassLoader
 * <p>
 * 启动类加载器 Bootstrap ClassLoader: 加载 %JAVA_HOME%/jre/lib 目录下核心库
 * 扩展类加载器 Extension ClassLoader: 加载 %JAVA_HOME%/jre/lib/ext 目录下扩展包
 * 应用程序类加载器 Application ClassLoader: 加载 classpath 上指定的类库
 * <p>
 * 双亲委派机制:
 * 1.当 Application ClassLoader 收到一个类加载请求时，他首先不会自己去尝试加载这个类，
 * 而是将这个请求委派给父类加载器 Extension ClassLoader 去完成。
 * 2.当 Extension ClassLoader 收到一个类加载请求时，他首先也不会自己去尝试加载这个类，
 * 而是将请求委派给父类加载器 Bootstrap ClassLoader 去完成。
 * 3.如果 Bootstrap ClassLoader 加载失败(在%JAVA_HOME%\lib中未找到所需类)，
 * 就会让Extension ClassLoader 尝试加载。
 * 4.如果 Extension ClassLoader 也加载失败，就会使用Application ClassLoader 加载。
 * 5.如果 Application ClassLoader 也加载失败，就会使用自定义加载器去尝试加载。
 * 6.如果均加载失败，就会抛出 ClassNotFoundException 异常。
 * <p>
 * java 的沙箱安全机制: 自己写的 String 类不会被加载
 */
public class ClassLoaderTest {
    public static void main(String[] args) {
        Object obj = new Object();
        // null
        System.out.println(obj.getClass().getClassLoader());

        ClassLoaderTest cl = new ClassLoaderTest();
        // AppClassLoader
        System.out.println(cl.getClass().getClassLoader());
        // ExtClassLoader
        System.out.println(cl.getClass().getClassLoader().getParent());
        // null
        System.out.println(cl.getClass().getClassLoader().getParent().getParent());
    }
}
