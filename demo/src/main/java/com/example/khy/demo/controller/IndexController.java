package com.example.khy.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Controller
public class IndexController {

    /**
     * 测试接收HttpClient Post 二进制/字节流/byte[]
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @RequestMapping("index")
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ServletInputStream servletInputStream = request.getInputStream();
        ByteArrayOutputStream out = new ByteArrayOutputStream ();
        byte[] b = new byte[1024];
        int i = 0;
        // inputStream 转 byte
        while((i = servletInputStream.read(b,0,1024))>0){
            out.write(b,0,i);
        }

        byte[] req = out.toByteArray();

        String str = new String(req,"UTF-8");
        System.out.println(str);
    }

}
