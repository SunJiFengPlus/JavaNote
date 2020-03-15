package io;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * 对文件内容的访问类，可以读文件，可以写文件。
 * 可以从任意位置读取、写入、插入
 *
 * @author 孙继峰
 * @date 20190703
 */
public class RandomAccessFileTest {
    @Test
    public void test() {
        File file = new File("F:\\Java Documents\\test\\src\\io\\RandomFile.txt");
        byte[] buf = new byte[1024];
        // rw可读可写 r只读
        try (RandomAccessFile raf = new RandomAccessFile(file, "rw")) {
            // getFilePointer方法会返回此文件中指针的当前偏移量。
            System.out.println(raf.getFilePointer());
            // 一次性写一个字节
            raf.write('a');
            // 设置指针指到文件开头，seek()方法会从文件开头以字节为单位测量的偏移量位置，在该位置设置文件指针
            raf.seek(0);
            // 把文件中的数据读入到数组中
            while (raf.read(buf) != -1) {
                System.out.println(new String(buf));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}