package nio;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;

/**
 * 选择器(Selector): 是 SelectableChannel 的多路复用器, 用于监控 SelectableChannel 的 IO 状况
 * <p>
 * java.nio.channels.Channel 接口：
 * |--SelectableChannel
 * |--SocketChannel
 * |--ServerSocketChannel
 * |--DatagramChannel
 * |--Pipe.SinkChannel
 * |--Pipe.SourceChannel
 * <p>
 * FileChannel 不能切换为非阻塞模式
 */
public class BlockingNIOTest {

    /**
     * 客户端
     */
    @Test
    public void testCliennt() {
        SocketChannel socketChannel = null;
        FileChannel inChannel = null;
        try {
            // 获取通道
            socketChannel = SocketChannel.open(new InetSocketAddress("127.0.0.1", 9898));
            // 分配缓冲区
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            // 读取本地文件发送到服务端
            inChannel = FileChannel.open(Paths.get("1.jpg"), StandardOpenOption.READ);
            while (inChannel.read(buffer) != -1) {
                buffer.flip();
                socketChannel.write(buffer);
                buffer.clear();
            }

            // 通知服务端数据已经传输完成
            socketChannel.shutdownOutput();

            // 接收服务端的反馈
            while (socketChannel.read(buffer) != -1) {
                buffer.flip();
                System.out.println(Arrays.toString(buffer.array()));
                buffer.clear();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            IOUtil.realse(socketChannel, inChannel);
        }
    }

    /**
     * 服务端
     */
    @Test
    public void testServer() {
        ServerSocketChannel serverSocketChannel = null;
        FileChannel outChannel = null;
        SocketChannel socketChannel = null;
        try {
            // 获取通道
            serverSocketChannel = ServerSocketChannel.open();
            // 绑定端口号
            serverSocketChannel.bind(new InetSocketAddress(9898));
            // 获取客户端连接的通道
            socketChannel = serverSocketChannel.accept();

            outChannel = FileChannel.open(Paths.get("2.jpg"), StandardOpenOption.CREATE,
                    StandardOpenOption.WRITE);
            ByteBuffer buffer = ByteBuffer.allocate(1024);

            // 接收客户端数据保存到本地
            while (socketChannel.read(buffer) != -1) {
                buffer.flip();
                outChannel.write(buffer);
                buffer.clear();
            }

            // 向客户端返回信息
            buffer.put("服务端以接收".getBytes(StandardCharsets.UTF_8));
            buffer.flip();
            socketChannel.write(buffer);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            IOUtil.realse(serverSocketChannel, outChannel, socketChannel);
        }
    }
}
