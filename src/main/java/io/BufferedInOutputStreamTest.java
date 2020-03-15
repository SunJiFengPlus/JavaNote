package io;

import org.junit.jupiter.api.Test;

import java.io.*;

public class BufferedInOutputStreamTest {
    /**
     * 实现非文本文件复制
     * 缓冲流实现文本的复制比FileInputOutputStream实现的时间要短
     * 发会经常使用到缓冲流，不用 FileWriterReader  FileInputOutputStream
     */
    @Test
    public void Duplicate() {
        File file1 = new File("D:/workspace/idea/study/Java/src/io/desktop.jpg");
        File file2 = new File("D:/workspace/idea//study/Java/src/io/desktop2.jpg");

        try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file1));
             BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file2))) {

            byte[] b = new byte[1024];
            while (bis.read(b) != -1) {
                // 缓冲流写出
                bos.write(b);
                // 刷新此缓冲的输出流。这迫使所有缓冲的输出字节被写出到底层输出流中
                // 使用 try with resource 会自动调用 BufferedOutputStream.close()
                // 而 close() 内会调用 flush(), 不需要手动调用
                /// bos.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
