package io;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class FileReaderWriterTest {
    /**
     * 以字符的方式写入读出，可以汉字
     * 可以实现文本的复制
     */
    public static void FileReaderTest() {

        File file1 = new File("F:\\学习资源\\JAVA\\Java.txt");
        FileReader fr = null;

        try {
            fr = new FileReader(file1);
            char[] c = new char[(int) file1.length()];
            fr.read(c);
            System.out.println(c);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fr != null) {
                try {
                    fr.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    /*
     * 		实现文本文件的复制
     * 		但是中文会乱码
     * 		用后面的可以解决
     */
    public static void FileDuplicate() {

        File file1 = new File("F:\\学习资源\\JAVA\\Java.txt");
        File file2 = new File("F:\\Java Documents\\test\\src\\io\\FileWriter.txt");

        FileWriter fw = null;
        FileReader fr = null;

        try {
            fr = new FileReader(file1);                    //输入流文件一定要存在
            fw = new FileWriter(file2);                    //输出流文件可以不存在，但必须是可创建状态
            char[] c = new char[(int) file1.length()];
            fr.read(c);
            System.out.println(c);                        //控制台输出中文正常
            fw.write(c);                                //输出到文件后中文乱码

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fr != null) {
                try {
                    fr.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fw != null) {
                try {
                    fw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
