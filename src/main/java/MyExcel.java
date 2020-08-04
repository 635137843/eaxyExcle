import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.excel.read.metadata.ReadSheet;
import com.alibaba.excel.write.metadata.WriteSheet;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyExcel {

    public static void main(String[] args) {
        //simpleRead();
        simpleWrite();
    }

    public static void simpleRead() {
        // 有个很重要的点 DemoDataListener 不能被spring管理，要每次读取excel都要new,然后里面用到spring可以构造方法传进去
        // 写法1：
        String fileName = "D://hss123.xlsx";
        // 这里 需要指定读用哪个class去读，然后读取第一个sheet 文件流会自动关闭
        StringExcelListener listener = new StringExcelListener();
        /*EasyExcel.read(fileName, null, listener).sheet().doRead();
        System.out.println(listener.getDatas());*/

        // 写法2：
        ExcelReader excelReader = null;
        try {
            excelReader = EasyExcel.read(fileName, null, listener).build();
            ReadSheet readSheet = EasyExcel.readSheet(0).build();
            excelReader.read(readSheet);
            List<Map<Integer, String>> datas = listener.getDatas();
            Map<String, String> heads = listener.getHeads();
        } finally {
            if (excelReader != null) {
                // 这里千万别忘记关闭，读的时候会创建临时文件，到时磁盘会崩的
                excelReader.finish();
            }
        }
    }
    //读
    public static void simpleRead2() {
        FileInputStream fileInput;
        try {
            fileInput = new FileInputStream("D://公租房信息.xls");
            List<Object> read = EasyExcelFactory.read(fileInput, new Sheet(0,0));
            System.out.println(read);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }
    //写
    public static void simpleWrite() {
        File file = new File("D://hss123.xlsx");
        String name = file.getPath();
        ExcelWriter build = EasyExcel.write(name).build();
        if (file.exists()) {
            file.delete();
        }
        try {
            Map<String, Object> datas = getDatas();
            List<String> data1= (List<String>)datas.get("data");
            List<List<String>> data2 = (List<List<String>>)datas.get("data2");
            WriteSheet wr = EasyExcel.writerSheet("HelloWord1").head(data2).build();
            WriteSheet wr2 = EasyExcel.writerSheet("HelloWord2").build();
            build.write(data1, wr);
            build.write(data1, wr2);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            build.finish();
        }
    }

    private static Map<String,Object> getDatas() {
        Map<String,Object> list = new HashMap<>();
        List<List<String>> head = new ArrayList<>();
        List<List<String>> data1 = new ArrayList<>();
        List<String> list1 = new ArrayList<>();
        List<String> list2 = new ArrayList<>();
        List<String> list3 = new ArrayList<>();
        List<String> list4 = new ArrayList<>();
        List<String> list5 = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list1.add("123");
            list1.add("123");
            list1.add("123");
            list1.add("123");
            list1.add("123");
            data1.add(list1);
        }
        list2.add("你好1");
        list3.add("你好2");
        list4.add("你好3");
        list5.add("你好4");
        head.add(list2);
        head.add(list3);
        head.add(list4);
        head.add(list5);
        list.put("data",data1);
        list.put("data2",head);
        return list;
    }
}