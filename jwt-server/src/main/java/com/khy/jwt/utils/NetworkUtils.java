package com.khy.jwt.utils;

import eu.bitwalker.useragentutils.Browser;
import eu.bitwalker.useragentutils.UserAgent;
import eu.bitwalker.useragentutils.Version;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NetworkUtils {
    /**
     * Logger for this class
     */
    private static Logger logger = LoggerFactory.getLogger(NetworkUtils.class);

    /**
     * 获取请求主机IP地址,如果通过代理进来，则透过防火墙获取真实IP地址;
     *
     * @param request
     * @return
     * @throws IOException
     */
    public final static String getIpAddress(HttpServletRequest request) throws IOException {
        // 获取请求主机IP地址,如果通过代理进来，则透过防火墙获取真实IP地址

        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("Proxy-Client-IP");
            }
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("WL-Proxy-Client-IP");
            }
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("HTTP_CLIENT_IP");
            }
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("HTTP_X_FORWARDED_FOR");
            }
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getRemoteAddr();
            }
        } else if (ip.length() > 15) {
            String[] ips = ip.split(",");
            for (int index = 0; index < ips.length; index++) {
                String strIp = (String) ips[index];
                if (!("unknown".equalsIgnoreCase(strIp))) {
                    ip = strIp;
                    break;
                }
            }
        }
        return ip;
    }

    /**
     * Java通过浏览器请求头（UserAgent）获取手机机型
     * http://energykey.iteye.com/blog/2118491
     * @param userAgent
     * @return
     */
    public static String getModelFromDevice(String userAgent){
        Pattern pattern = Pattern.compile(";\\s?(\\S*?\\s?\\S*?)\\s?(Build)?/");
        Matcher matcher = pattern.matcher(userAgent);
        String model = null;
        if (matcher.find()) {
            model = matcher.group(1).trim();
            System.out.println("通过userAgent解析出机型：" + model);
        }
        return model;
    }

    /**
     * java从后台获取浏览器名称及版本号
     * @param request
     * @return
     */
    public String getVersionOfBrowser(HttpServletRequest request){
        String info = "";
        try {
            //获取浏览器信息
            Browser browser = UserAgent.parseUserAgentString(request.getHeader("User-Agent")).getBrowser();
            //获取浏览器版本号
            Version version = browser.getVersion(request.getHeader("User-Agent"));
            info = browser.getName() + "/" + version.getVersion();
        }catch (Exception e){
            logger.error("从user-agent中获取版本失败，user-agent: {}", request.getHeader("User-Agent"));
        }
        return info;
    }

    public static void main(String[] args) {
        String s = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/66.0.3359.181 Safari/537.36";
        //s = "Meizu MX2 M040  Android 4.1     Baidu 4.1   Mozilla/5.0 (Linux; U; Android 4.1.1; zh-cn; M040 Build/JRO03H) AppleWebKit/534.24 (KHTML, like Gecko) Version/4.0 Mobile Safari/534.24 T5/2.0 baidubrowser/4.2.4.0 (Baidu; P1 4.1.1)";
        //获取浏览器信息
        Browser browser = UserAgent.parseUserAgentString(s).getBrowser();
        //获取浏览器版本号
        Version version = browser.getVersion(s);
        String info = browser.getName() + "/" + version.getVersion();
        System.out.println(info);
    }
}
