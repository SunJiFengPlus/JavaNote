package io;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class InputStreamReaderOutputStreamWriterTest {
    /**
     * 转换流
     * 解决中文乱码
     */
    public static void Duplicate() {

        File file1 = new File("src\\io\\FileWriter.txt");
        File file2 = new File("src\\io\\FileWriter2.txt");
        FileInputStream fis = null;
        FileOutputStream fos = null;
        InputStreamReader isr = null;
        OutputStreamWriter osw = null;
        BufferedReader br = null;
        BufferedWriter bw = null;
        String str = null;

        try {
            // 解码过程，将字节转为字符
            fis = new FileInputStream(file1);
            // 使用GBK的编码方式解码
            isr = new InputStreamReader(fis, "GBK");
            // InputStreamReader是Reader类的子类
            br = new BufferedReader(isr);

            // 编码过程
            fos = new FileOutputStream(file2);
            osw = new OutputStreamWriter(fos, StandardCharsets.UTF_8);
            bw = new BufferedWriter(osw);

            for (; (str = br.readLine()) != null; ) {
                System.out.println(str);
                bw.write(str);
                bw.newLine();
                bw.flush();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
