package com.example.khy.demo.controller;

import com.artofsolving.jodconverter.DocumentConverter;
import com.artofsolving.jodconverter.openoffice.connection.OpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.connection.SocketOpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.converter.OpenOfficeDocumentConverter;

import java.io.File;
import java.io.IOException;

/**
 * 参考文档：https://www.cnblogs.com/wjqboke/articles/7646606.html
 *          https://www.cnblogs.com/morethink/p/8111120.html
 * 1. 开发过程中经常会使用java将office系列文档转换为PDF， 一般都使用微软提供的openoffice+jodconverter 实现转换文档。
 * 2. openoffice既有windows版本也有linux版。不用担心生产环境是linux系统。
 * 二、 准备
 * 1. 下载安装openoffice   http://www.openoffice.org/  （路径随意，因为不管怎么选路径，咱们想要的都在"C:/Program Files (x86)/OpenOffice 4/"，版本建议选最新的）
 * 2. 安装完事后 cmd  运行  cd C:\Program Files\OpenOffice.org 3\program>soffice -headless -accept="socket,host=127.0.0.1,port=8100;urp;" -nofirststartwizard
 * 查看是否安装成功，查看端口对应的pid
 * netstat -ano|findstr  8100
 * 查看pid对应的服务程序名
 * tasklist|findstr pid值
 * 也可以把这一步省略，放到java程序中调用服务，因为启动服务占用内存比较大，在java中可以在使用的时候调用，然后马上销毁。
 * 3.odconverter-2.2.2.zip 下载地址： http://sourceforge.net/projects/jodconverter/files/JODConverter/
 */
public class WordToPdf {
    public static int office2PDF(String sourceFile, String destFile) {
        //String OpenOffice_HOME = "D:/Program Files/OpenOffice.org 3";// 这里是OpenOffice的安装目录,C:\Program Files (x86)\OpenOffice 4
        String OpenOffice_HOME = "C:/Program Files (x86)/OpenOffice 4/";
        // 在我的项目中,为了便于拓展接口,没有直接写成这个样子,但是这样是尽对没题目的
        // 假如从文件中读取的URL地址最后一个字符不是 '\'，则添加'\'
        if (OpenOffice_HOME.charAt(OpenOffice_HOME.length() - 1) != '/') {
            OpenOffice_HOME += "/";
        }
        Process pro = null;
        OpenOfficeConnection connection = null;
        try {
            File inputFile = new File(sourceFile);
            if (!inputFile.exists()) {
                // 找不到源文件, 则返回false
                return -1;// 找不到源文件, 则返回-1
            }
            // 如果目标路径不存在, 则新建该路径
            File outputFile = new File(destFile);
            if (!outputFile.getParentFile().exists()) {
                outputFile.getParentFile().mkdirs();
            }
            //如果目标文件存在，则删除
            /*if (outputFile.exists()) {
                outputFile.delete();
            }*/
            // 启动OpenOffice的服务
            /*String command = OpenOffice_HOME
                    + "program\\soffice.exe -headless -accept=\"socket,host=127.0.0.1,port=8100;urp;StarOffice.ServiceManager\" -nofirststartwizard";
            pro = Runtime.getRuntime().exec(command);*/
            // connect to an OpenOffice.org instance running on port 8100
            connection = new SocketOpenOfficeConnection("127.0.0.1", 8100);
            //OpenOfficeConnection connection = new SocketOpenOfficeConnection(8100);
            connection.connect();

            // convert
            DocumentConverter converter = new OpenOfficeDocumentConverter(connection);
            converter.convert(inputFile, outputFile);
            //converter.convert(inputFile,xml,outputFile,pdf);

            // 封闭OpenOffice服务的进程
            //pro.destroy();
            return 0;
        } /*catch (FileNotFoundException e) {
            e.printStackTrace();
            return -1;
        }*/ catch (IOException e) {
            e.printStackTrace();
        } finally {
            // close the connection
            if(connection != null){
                connection.disconnect();
            }
            //pro.destroy();
        }

        return 1;
    }


    public static void main(String[] args) throws Exception {
        String sourceFile = "E:\\test\\输出.docx";
        String destFile = "E:\\aaaa\\aaaa\\pdfdest.pdf";
        int i = WordToPdf.office2PDF(sourceFile, destFile);
        System.out.println(i);
    }

}

