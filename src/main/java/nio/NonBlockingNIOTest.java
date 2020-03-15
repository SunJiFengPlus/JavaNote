package nio;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Iterator;

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
public class NonBlockingNIOTest {

    /**
     * 客户端
     */
    @Test
    public void test() {
        SocketChannel socketChannel = null;
        try {
            // 获取通道
            socketChannel = SocketChannel.open(new InetSocketAddress("127.0.0.1", 9898));
            // 切换为非阻塞模式
            socketChannel.configureBlocking(false);
            // 分配缓冲区
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            // 向服务端发送数据
            buffer.put(new Date().toString().getBytes(StandardCharsets.UTF_8));
            buffer.flip();
            socketChannel.write(buffer);
            buffer.clear();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            IOUtil.realse(socketChannel);
        }
    }

    /**
     * 服务端
     */
    @Test
    public void test1() {
        ServerSocketChannel serverSocketChannel;
        Selector selector;
        SocketChannel clientChannel;

        try {
            // 获取通道
            serverSocketChannel = ServerSocketChannel.open();
            // 切换为非阻塞模式
            serverSocketChannel.configureBlocking(false);
            // 绑定端口号
            serverSocketChannel.bind(new InetSocketAddress(9898));
            // 获取选择器
            selector = Selector.open();
            /*
             *  将通道注册到选择器中, 选择器开始监听通道的接收事件, 当准备就绪时获取连接
             *  param2: 监听事件类型, 1-读, 4-写, 8-连接, 16-接收, 多个状态使用位或运算符
             */
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
            // 轮询式获取选择上已经准备就绪的事件
            while (selector.select() > 0) {
                // 获取选择器中所有注册的状态(已就绪的监听事件)
                Iterator<SelectionKey> it = selector.selectedKeys().iterator();
                while (it.hasNext()) {
                    SelectionKey key = it.next();
                    // 判断是什么进入就绪状态
                    if (key.isAcceptable()) {
                        // 在此 if 块中的内容可以新开个线程来执行
                        // 若接收就绪, 获取客户端连接
                        clientChannel = serverSocketChannel.accept();
                        // 把客户端切换到非阻塞模式
                        clientChannel.configureBlocking(false);
                        // 将客户端通道注册到选择器中, 监听读就绪事件
                        clientChannel.register(selector, SelectionKey.OP_READ);
                    } else if (key.isReadable()) {
                        // 在此 if 块中的内容可以新开个线程来执行
                        // 获取当前选择器上“读就绪”状态的通道
                        SocketChannel sChannel = (SocketChannel) key.channel();
                        // 读取数据
                        ByteBuffer buf = ByteBuffer.allocate(1024);
                        int len;
                        while ((len = sChannel.read(buf)) > 0) {
                            buf.flip();
                            System.out.println(new String(buf.array(), 0, len, StandardCharsets.UTF_8));
                            buf.clear();
                        }
                    }
                    // 取消选择键 SelectionKey
                    it.remove();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}