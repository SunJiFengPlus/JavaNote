package nio;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 * 分散读取(Scatter Reads): 将通道中的数据分散到多个缓冲区中
 * 聚集写入(Gather Writes): 将多个缓冲区的数据聚集到一个通道中
 */
public class ScatterGatherTest {
    @Test
    public void test1() {
        FileChannel inChannel = null;
        FileChannel outChannel = null;
        ByteBuffer[] buffers = {ByteBuffer.allocate(1024), ByteBuffer.allocate(1024)};
        try {
///            inChannel = FileChannel.open(Paths.get("F:/学习资源/01-尚硅谷Java技术之java基础/java.txt"), StandardOpenOption.READ);
            inChannel = FileChannel.open(Paths.get("1.txt"), StandardOpenOption.READ);
            // 分散读取
            inChannel.read(buffers);
            // 切换到读数据模式
            buffers[0].flip();
            buffers[1].flip();
            // 聚集写入 
            outChannel = FileChannel.open(Paths.get("2.txt"), StandardOpenOption.WRITE, StandardOpenOption.CREATE);
            outChannel.write(buffers);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }
}
