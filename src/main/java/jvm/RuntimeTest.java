package jvm;

/**
 * @author 孙继峰
 * @date 2019/08/14
 */
public class RuntimeTest {
    public static void main(String[] args) {
        // Java 虚拟机最多可使用的内存大小, 单位字节, -Xmx1024m 配置
        long maxMemory = Runtime.getRuntime().maxMemory();
        // 当前 Java 虚拟机总内存大小, 可能会扩容, -Xms2048m 配置
        // 推荐可使用与最大可使用一样大, 可减少扩容带来的性能消耗
        long totalMemory = Runtime.getRuntime().totalMemory();
        System.out.println("MaxMemory: " + maxMemory / 1024 / 1024 + "MB");
        System.out.println("TotalMemory: " + totalMemory / 1024 / 1024 + "MB");
    }
}
