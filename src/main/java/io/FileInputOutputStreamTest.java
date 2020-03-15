package io;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;


public class FileInputOutputStreamTest {

    /**
     * FileInputStream 与 FileOutputStream 都是以字节的形式向文件读取、写入，
     * gbk 中中文占两个字节，UTF-8 中中文占三个字节，所以中文不行
     * 可以实现非文本文件的复制
     */
    @Test
    public void inputStreamTest1() {
        File file = new File("D:/workspace/idea/study/Java/src/exception/TryWithResourceTest.jad");
        try (FileInputStream fis = new FileInputStream(file)) {
            // read()读取文件的一个字节
            int a = fis.read();
            // 从此输入流中读取一个数据字节。返回下一个数据字节；如果已到达文件末尾，则返回 -1
            for (; a != -1; a = fis.read()) {
                System.out.println((char) a);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 按字节数组读取
     */
    @Test
    public void inputStreamTest2() {
        File file = new File("D:/workspace/idea/study/Java/src/exception/TryWithResourceTest.jad");
        try (FileInputStream fis = new FileInputStream(file)) {
            byte[] b = new byte[1024];
            while (fis.read(b) != -1) {
                System.out.println(new String(b));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 按照字节写入
     */
    public void outputStreamTest1() {

        File file = new File("F:\\Java Documents\\test\\src\\io\\FileOutputStream.txt");
        // 输出到哪个文件，当文件不存在时创建或抽象路径为目录时，抛出FileNotFoundException，若文件存在则将原有文件内容覆盖
        try (FileOutputStream fos = new FileOutputStream(file)) {
            fos.write("higkln".getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 实现文件的复制粘贴
     */
    public static void fileDuplicateTest() {
        File file1 = new File("F:\\Java Documents\\test\\src\\io\\图片.png");
        File file2 = new File("F:\\Java Documents\\test\\src\\io\\图片(1).png");
        try (FileInputStream fis = new FileInputStream(file1);
             FileOutputStream fos = new FileOutputStream(file2)) {
            byte[] b = new byte[(int) file1.length()];
            fis.read(b);
            fos.write(b);
        } catch (IOException e) {

        }
    }
}
