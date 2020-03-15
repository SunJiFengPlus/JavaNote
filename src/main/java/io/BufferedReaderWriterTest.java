package io;

import java.io.*;

public class BufferedReaderWriterTest {
    /**
     * BufferedReader Writer实现文本复制仍然会乱码
     */
    public static void Duplicate() {

        File file1 = new File("F:\\学习资源\\JAVA\\Java.txt");
        File file2 = new File("F:\\Java Documents\\test\\src\\io\\FileWriter.txt");
        BufferedReader br = null;
        BufferedWriter bw = null;
        FileReader fr = null;
        FileWriter fw = null;

        try {

            fr = new FileReader(file1);
            fw = new FileWriter(file2);
            br = new BufferedReader(fr);
            bw = new BufferedWriter(fw);

            //	char[] c = new char[(int) file1.length()];
            //	br.read(c);
            //	bw.write(c);

            String str = null;            //第二种方法
            for (; (str = br.readLine()) != null; ) {
                bw.write(str);
                bw.newLine();//换行
                bw.flush();
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (bw != null) {
                try {
                    bw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
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
