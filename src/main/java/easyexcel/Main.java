package easyexcel;

import com.alibaba.excel.EasyExcel;

import java.util.ArrayList;

/**
 * @author 孙继峰
 * @since 2020/4/7
 */
public class Main {
    public static DemoDataListener listener = new DemoDataListener();
    public static void main(String[] args) {
        String fileName1 = "/Users/admin/Desktop/1.xls";
        String fileName2 = "/Users/admin/Desktop/2.xls";
        EasyExcel.read(fileName1, Model.class, listener).sheet().doRead();
        EasyExcel.read(fileName2, Model.class, listener).sheet().doRead();

        String fileName = "/Users/admin/Desktop/res.xls";
        EasyExcel.write(fileName, Model.class).sheet("1").doWrite(new ArrayList<>(listener.getSet()));
    }
}
