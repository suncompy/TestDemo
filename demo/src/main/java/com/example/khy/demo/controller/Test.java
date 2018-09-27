package com.example.khy.demo.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Test {

    public static void main(String[] args) throws IOException {

        Map<String, Object> wordDataMap = new HashMap<String, Object>();// 存储报表全部数据
        Map<String, Object> parametersMap = new HashMap<String, Object>();// 存储报表中不循环的数据


        List<Map<String, Object>> table1 = new ArrayList<Map<String, Object>>();
        Map<String, Object> map1 = new HashMap<>();
        map1.put("lenderName", "张三");
        map1.put("lenderCardNum", "1651351456165413");
        map1.put("certificateType", "身份证");

        Map<String, Object> map2 = new HashMap<>();
        map2.put("lenderName", "李四");
        map2.put("lenderCardNum", "45");
        map2.put("certificateType", "45445@qq.com");

        Map<String, Object> map3 = new HashMap<>();
        map3.put("lenderName", "Tom");
        map3.put("lenderCardNum", "34");
        map3.put("certificateType", "6767@qq.com");

        table1.add(map1);
        table1.add(map2);
        table1.add(map3);


        List<Map<String, Object>> table2 = new ArrayList<Map<String, Object>>();
        Map<String, Object> map4 = new HashMap<>();
        map4.put("lenderName", "tom");
        map4.put("amount", "sd1234");
        map4.put("bidMonth", "上海");
        map4.put("bidRate", "上海");
        map4.put("start", "上海");
        map4.put("end", "上海");
        map4.put("day", "上海");
        map4.put("total", "上海");

        Map<String, Object> map5 = new HashMap<>();
        map5.put("lenderName", "seven");
        map5.put("amount", "sd15678");
        map5.put("bidMonth", "北京");
        map5.put("bidRate", "北京");
        map5.put("start", "北京");
        map5.put("end", "北京");
        map5.put("day", "北京");
        map5.put("total", "北京");

        Map<String, Object> map6 = new HashMap<>();
        map6.put("lenderName", "lisa");
        map6.put("amount", "sd9078");
        map6.put("bidMonth", "广州");
        map6.put("bidRate", "广州");
        map6.put("start", "广州");
        map6.put("end", "广州");
        map6.put("day", "广州");
        map6.put("total", "广州");

        table2.add(map4);
        table2.add(map5);
        table2.add(map6);
        table2.add(map6);
        table2.add(map6);
        table2.add(map6);
        table2.add(map6);
        table2.add(map6);
        table2.add(map6);
        table2.add(map6);
        table2.add(map6);
        table2.add(map6);
        table2.add(map6);
        table2.add(map6);
        table2.add(map6);
        table2.add(map6);
        table2.add(map6);
        table2.add(map6);
        table2.add(map6);
        table2.add(map6);
        table2.add(map6);
        table2.add(map6);
        table2.add(map6);
        table2.add(map6);
        table2.add(map6);
        table2.add(map6);
        table2.add(map6);
        table2.add(map6);
        table2.add(map6);
        table2.add(map6);
        table2.add(map6);
        table2.add(map6);
        table2.add(map6);
        table2.add(map6);
        table2.add(map6);
        table2.add(map6);
        table2.add(map6);
        table2.add(map6);
        table2.add(map6);
        table2.add(map6);
        table2.add(map6);
        table2.add(map6);
        table2.add(map6);
        table2.add(map6);
        table2.add(map6);
        table2.add(map6);
        table2.add(map6);
        table2.add(map6);
        table2.add(map6);
        table2.add(map6);
        table2.add(map6);
        table2.add(map6);
        table2.add(map6);
        table2.add(map6);
        table2.add(map6);
        table2.add(map6);


        parametersMap.put("contractId", "hd254f2saf4245jla543sf43jl");
        parametersMap.put("borrowerName", "李强");
        parametersMap.put("borrowerCardNum", "510256632585420232");
        parametersMap.put("address", "四川省成都市");
        parametersMap.put("certificateType", "身份证");


        wordDataMap.put("table1", table1);
        wordDataMap.put("table2", table2);
        wordDataMap.put("parametersMap", parametersMap);
        File file = new File("E:\\test\\借款协议.docx");//改成你本地文件所在目录


        // 读取word模板
        FileInputStream fileInputStream = new FileInputStream(file);
        WordTemplate template = new WordTemplate(fileInputStream);

        // 替换数据
        template.replaceDocument(wordDataMap);


        //生成文件
        File outputFile = new File("E:\\test\\输出.docx");//改成你本地文件所在目录
        FileOutputStream fos = new FileOutputStream(outputFile);
        template.getDocument().write(fos);
        System.out.println("success");
    }

}
