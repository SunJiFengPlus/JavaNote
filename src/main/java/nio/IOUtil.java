package nio;

import java.io.Closeable;
import java.io.IOException;

/**
 * 文件操作工具类
 *
 * @author 孙继峰
 * @date 2018/12/24
 */
public class IOUtil {

    /**
     * 关闭实现了 java.io.Closeable 了接口的 Stream, Channel, Buffer
     *
     * @param closeOption 实现 java.io.Closeable 了接口的对象
     */
    public static void realse(Closeable... closeOption) {
        for (Closeable closeable : closeOption) {
            if (closeable != null) {
                try {
                    closeable.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
