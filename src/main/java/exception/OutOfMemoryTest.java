package exception;

import java.util.ArrayList;

/**
 * -Xms10m -Xmx10m -XX:+PrintGCDetails
 *
 * @author 孙继峰
 * @date 019/05/02
 */
public class OutOfMemoryTest {
    public static void main(String[] args) {
        ArrayList<byte[]> list = new ArrayList<>();

        while (true) {
            // 1MB
            byte[] bytes = new byte[1024 * 1024];
            list.add(bytes);
        }

    }
}
