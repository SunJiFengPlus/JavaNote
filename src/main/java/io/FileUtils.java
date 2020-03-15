package io;

import java.io.File;

public class FileUtils {

    /**
     * 用于遍历目录中、子目录中、子目录的子目录中等等的所有文件
     */
    public void listDirctory(File file) {
        // 判断目录是否存在
        if (!file.exists()) {
            throw new IllegalArgumentException("目录:" + file.getPath() + "不存在");
        } else if (!file.isDirectory()) {
            throw new IllegalArgumentException(file.getPath() + "不是目录");
        }

        //返回一个抽象路径名数组，这些路径名表示此抽象路径名表示的目录中的文件。
        File[] files = file.listFiles();

        for (File filename : files) {
            if (filename.isDirectory()) {
                //递归
                listDirctory(filename);
            } else {
                System.out.println(filename);
            }
        }
    }
}