package io;

import java.io.File;
import java.io.IOException;
import java.util.Date;

public class FileTest {

    public static void main(String[] args) throws IOException {
        /**
         * 单斜杠是转义字符，需要改成双斜杠.
         * File对象的指向可以为文件或路径
         * 相对路径是test工程
         */
        File file1 = new File("F:\\Java Documents\\test\\src\\io\\RandomFile.txt");
        File file2 = new File("F:\\Java Documents\\test\\src\\io\\NothingnessFile.txt");
        File file3 = new File("F:\\Java Documents\\test\\src\\io");
        File file5 = new File("src\\io\\Nothingness\\Nothingness\\Nothingness");

/*		if(!file2.exists())
		{
			file2.createNewFile();	//当文件不存在时创建文件
			file2.mkdir();			//当目录不存在时创建目录
		}else
		{
			file2.delete();			//删除此抽象路径名表示的文件或目录。如果此路径名表示一个目录，则该目录必须为空才能删除。
        							//当且仅当成功删除文件或目录时，返回 true；否则返回 false 
		}
*/
        /*file1的toString方法,返回file1的所在目录，该字符串就是 getPath() 方法返回的字符串。*/
        System.out.println(file1.getPath());

        /*访问父目录路径*/
        System.out.println(file1.getParent());

        /*此抽象路径名表示的文件或目录的名称；如果路径名的名称序列为空，则返回空字符串*/
        System.out.println(file1.getName());

        System.out.println(file1.getAbsolutePath());

        /*绝对抽象路径名，它与此抽象路径名表示相同的文件或目录*/
        System.out.println(file1.getAbsoluteFile());

        /*重新命名此抽象路径名表示的文件,重命名成功返回true,否则false(两边需要同为文件或目录)*/
//		System.out.println(file1.renameTo(file2));

        /*文件或目录是否存在,存在返回true,否则false*/
        System.out.println("存在" + file1.exists());

        /*是否是目录，是目录返回true，不是或目录不存在返回false*/
        System.out.println("目录" + file1.isDirectory());

        /*是否是文件，返回true false*/
        System.out.println("文件" + file1.isFile());

        /*返回此抽象路径名指定的文件存在且可被应用程序读取时,返回 true,否则false*/
        System.out.println("可读" + file1.canRead());

        /*此抽象路径名表示的文件且允许应用程序对该文件进行写入时,返回 true否则false*/
        System.out.println("可写" + file1.canWrite());

        /*返回此抽象路径名表示的文件最后一次被修改的时间。用与时间点（1970 年 1 月 1 日，00:00:00 GMT）之间的毫秒数表示。*/
        System.out.println(new Date(file1.lastModified()));

        /*返回此抽象路径名表示的文件的长度，以字节为单位*/
        System.out.println("长度" + file1.length());

        /*当文件不存在时创建文件,创建成功返回true,否则false*/
        System.out.println("创建" + file2.createNewFile());

        /*删除此抽象路径名表示的文件或目录。如果此路径名表示一个目录，则该目录必须为空才能删除。*/
        System.out.println("删除" + file2.delete());

        /*当目录不存在时创建目录，只能在上层目录存在的情况下，创建完成返回true否则false*/
        System.out.println("创建目录" + file3.mkdir());

        /*可创建多级目录，可以创建个上级目录不存在的目录，创建一个不存在的目录的子目录的子目录等*/
        System.out.println("创建多级目录" + file5.mkdirs());

        /*返回目录下的文件以及目录列表,分别以File数组于String数组返回*/
        File[] files = file3.listFiles();
        String[] filenames = file3.list();
        for (String filename : filenames) {
            System.out.println(filename);
        }
    }
}
