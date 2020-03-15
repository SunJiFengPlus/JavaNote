package nio;

import org.junit.jupiter.api.Test;

import java.nio.ByteBuffer;

/**
 * 缓冲区: 负责数据的存取, 实质上是数组, 用于储存不同类型的数据
 * 根据不同的数据类型提供了不同的缓冲区(boolean 类型没有)
 * <p>
 * 缓冲区核心属性
 * capacity: 缓冲区中最大存储数据容量, 一旦声明不能改变
 * limit: 第一个不应该读写数据的索引, limit 以后的数据不能进行读写
 * position: 下一个要读取或写入的数据的索引
 * mark: 标记, 记录当前 position 的索引, 可以通过 reset() 恢复到 mark 的位置
 * 其遵循 0 <= mark <= position <= limit <= capacity
 */
public class BufferTest {
    @Test
    public void test1() {
        // 分配缓冲区
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        System.out.println(buffer + " allocate()");

        // 存数据
        buffer.put("asdfg".getBytes());
        System.out.println(buffer + " put()");

        // 切换到读数据模式
        buffer.flip();
        System.out.println(buffer + " flip()");

        // 读数据
        byte[] bytes = new byte[buffer.limit()];
        buffer.get(bytes);
        System.out.println(new String(bytes));
        System.out.println(buffer + " get()");

        // 使 position 复位, 可重复读
        buffer.rewind();
        System.out.println(buffer + " rewind()");

        // 清空缓冲区, 清空后缓冲区中的数据依然存在
        buffer.clear();
        System.out.println(buffer + " clear()");
    }

    @Test
    public void test2() {
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        buffer.put("asdfg".getBytes());
        buffer.flip();
        byte[] bytes = new byte[buffer.limit()];
        // 从缓冲区中读两个字节
        buffer.get(bytes, 0, 2);
        System.out.println(buffer + " get1");
        // 记录当前 position 位置
        buffer.mark();
        // 从缓冲区中再读两个字节
        buffer.get(bytes, 2, 2);
        System.out.println(buffer + " get2");
        // position 恢复到 mark 的位置
        buffer.reset();
        System.out.println(buffer + " reset()");

        // position 与 limit 之间是否有数据
        if (buffer.hasRemaining()) {
            // position 与 limit 之间有几个数据
            System.out.println(buffer.remaining());
        }
    }

    /**
     * 非直接缓冲区: 通过 allocate() 方法分配缓冲区, 缓冲区建立在 JVM 的内存中
     * 直接缓冲区: 通拓 allocateDirect() 方法分配缓冲区, 缓冲区建立在物理内存中, 可以提高效率
     */
    @Test
    public void test3() {
        // allocateDirect() 只有 ByteBuffer 支持
        ByteBuffer buffer = ByteBuffer.allocateDirect(1024);
        // 是否为直接缓冲区
        System.out.println(buffer.isDirect());
    }
}