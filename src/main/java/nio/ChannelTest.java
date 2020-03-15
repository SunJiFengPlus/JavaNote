package nio;

import org.junit.jupiter.api.Test;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileChannel.MapMode;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 * 通道: 负责源节点与目标节点的连接, 在 NIO 中负责缓冲区中数据的传输, Channel 本身不存储数据, 需要配合缓冲区进行传输
 */
public class ChannelTest {

    /**
     * 使用非直接缓冲区与通道完成文件的复制, 1.7 以前的写法, try catch 省略
     */
    @Test
    public void test1() {
        // 获取通道
        try (FileChannel inChannel = new FileInputStream("1.jpg").getChannel();
             FileChannel outChannel = new FileOutputStream("2.jpg").getChannel()) {

            // 分配缓冲区
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            // 将数据存入缓冲区
            while (inChannel.read(buffer) != -1) {
                // position 复位
                buffer.flip();
                // 将数据取出
                outChannel.write(buffer);
                // 清空缓冲区
                buffer.clear();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 使用直接缓冲区与通道完成文件复制, 1.7 以后的写法
     * <p>
     * 结果:
     * 1.文件复制完成后, 程序并没有结束, JVM 还继续占用系统资源, 直到下一次垃圾回收, 不能及时的释放资源
     * 2.执行速度快于非直接缓冲区, 但很消耗系统资源
     * <p>
     * 建议将直接缓冲区主要分配给那些易受基础系统的本机 I/O 操作影响的大型、持久的缓冲区。
     * 一般情况下，最好仅在直接缓冲区能在程序性能方面带来明显好处时分配它们。
     */
    @Test
    public void test2() {
        FileChannel inChannel = null;
        FileChannel outChannel = null;
        MappedByteBuffer inBuffer = null;
        MappedByteBuffer outBuffer = null;
        try {
            /*
             *  StandardOpenOption READ: 读; WRITE: 写; CREATE: 检查文件是否存在, 不存在则创建, 存在则覆盖
             *  CREATE_NEW: 当文件不存在时创建, 存在则报错
             */
            inChannel = FileChannel.open(Paths.get("1.jpg"), StandardOpenOption.READ);
            outChannel = FileChannel.open(Paths.get("3.jpg"), StandardOpenOption.READ,
                    StandardOpenOption.WRITE, StandardOpenOption.CREATE);
            // 内存映射文件, 与 allocatedDirect() 方法效果相同, 将缓冲区建立在物理内存中
            inBuffer = inChannel.map(MapMode.READ_ONLY, 0, inChannel.size());
            outBuffer = outChannel.map(MapMode.READ_WRITE, 0, inChannel.size());
        } catch (IOException e) {
            e.printStackTrace();
        }
        byte[] dst = new byte[inBuffer.limit()];
        inBuffer.get(dst);
        outBuffer.put(dst);

        IOUtil.realse(inChannel, outChannel);
    }

    /**
     * 通道之间的数据传输(直接缓冲区)
     */
    @Test
    public void test3() {
        FileChannel inChannel = null;
        FileChannel outChannel = null;

        try {
            inChannel = FileChannel.open(Paths.get("1.jpg"), StandardOpenOption.READ);
            outChannel = FileChannel.open(Paths.get("2.jpg"), StandardOpenOption.CREATE, StandardOpenOption.WRITE);

            inChannel.transferTo(0, inChannel.size(), outChannel);
//            outChannel.transferFrom(src, position, count)
        } catch (Exception e) {
            e.printStackTrace();
        }

        IOUtil.realse(inChannel, outChannel);
    }
}
