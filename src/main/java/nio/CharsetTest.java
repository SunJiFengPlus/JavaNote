package nio;

import org.junit.jupiter.api.Test;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.util.Arrays;

/**
 * 编码: 将字符串转换为字节数组
 * 解码: 将字节数组转换为字符串
 */
public class CharsetTest {
    @Test
    public void test() {
        // 3个字节解析成一个字符
        Charset charset = Charset.forName("UTF-8");
        // 获取编码器
        CharsetEncoder encoder = charset.newEncoder();
        // 获取解码器
        CharsetDecoder decoder = charset.newDecoder();
        CharBuffer charBuffer = CharBuffer.allocate(1024);
        charBuffer.put("中文中文中文");
        charBuffer.flip();
        try {
            // 编码
            ByteBuffer byteBuffer = encoder.encode(charBuffer);
            System.out.println(Arrays.toString(byteBuffer.array()));
            System.out.println(byteBuffer);
            // TODO: 为什么需要先 get 再解码才有效呢?
            byteBuffer.get();
            byteBuffer.get();
            byteBuffer.get();
            System.out.println(byteBuffer);
            // 解码
            byteBuffer.flip();
            System.out.println(byteBuffer);
            CharBuffer temp = decoder.decode(byteBuffer);
            System.out.println(temp.toString());
        } catch (CharacterCodingException e) {
            e.printStackTrace();
        }
    }
}
