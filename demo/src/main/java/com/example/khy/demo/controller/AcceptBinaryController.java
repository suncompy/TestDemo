package com.example.khy.demo.controller;

import com.alibaba.fastjson.JSON;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;

@Controller
public class AcceptBinaryController {

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

    @RequestMapping(value = "/fdd", method = RequestMethod.POST)
    public void doPostFdd(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //response.sendRedirect("https://www.baidu.com");
        System.out.println(request.getQueryString());
        System.out.println(JSON.toJSONString(request.getParameterMap()));

        //{"name":["khy"],"transaction_id":["b123"],"timestamp":["20180921201407"],"result_code":["3000"],"msg_digest":["QjU0RUYxNjlEQjJCQzVFMDhBNzJCMzgyMjk4NDQ5MEQwMTZENjc4OA=="],"download_url":["https://testapi.fadada.com:8443/api//getdocs.action?app_id=401542&timestamp=20180921201214&v=2.0&msg_digest=NEZEN0VFNzg5MUFEMjc3QkM0NDdFOUY2RUM4M0JCRTYyMENEQ0REQQ==&send_app_id=&transaction_id=b123"],"viewpdf_url":["https://testapi.fadada.com:8443/api//viewdocs.action?app_id=401542&timestamp=20180921201214&v=2.0&msg_digest=NEZEN0VFNzg5MUFEMjc3QkM0NDdFOUY2RUM4M0JCRTYyMENEQ0REQQ==&send_app_id=&transaction_id=b123"],"result_desc":["签署成功"]}
    }

    public static void main(String[] args) {
        System.out.println(LocalDateTime.now().getYear());
        System.out.println(LocalDateTime.now().getMonth().getValue());
        System.out.println(LocalDateTime.now().getDayOfMonth());
    }
}
