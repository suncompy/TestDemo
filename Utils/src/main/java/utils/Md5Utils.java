package utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Md5Utils {
    protected static char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
    protected static char hexDigitsUpper[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

    private static String bufferToHex(byte bytes[], int m, int n) {
        StringBuffer stringbuffer = new StringBuffer(2 * n);
        int k = m + n;
        for (int l = m; l < k; l++) {
            appendHexPair(bytes[l], stringbuffer);
        }
        return stringbuffer.toString();
    }

    private static void appendHexPair(byte bt, StringBuffer stringbuffer) {

        char c0 = hexDigits[(bt & 0xf0) >> 4];
        char c1 = hexDigits[bt & 0xf];
        stringbuffer.append(c0);
        stringbuffer.append(c1);
    }

    private static String bufferToHex(byte bytes[]) {
        return bufferToHex(bytes, 0, bytes.length);
    }

    /**
     * 字符串的md5加密
     *
     * @param input
     * @return
     */
    public static String stringMD5(String input) throws NoSuchAlgorithmException {
        // 获得MD5摘要算法的 MessageDigest 对象
        MessageDigest mdInst = MessageDigest.getInstance("MD5");
        // 使用指定的字节更新摘要
        mdInst.update(input.getBytes());
        // 获得密文
        byte[] md = mdInst.digest();
        // 字符数组转换成字符串返回
        return bufferToHex(md);
    }

    /**
     * @param args
     */
    public static void main(String[] args) throws NoSuchAlgorithmException {
        //测试字符串MD5加密
        //123456: e10adc3949ba59abbe56e057f20f883e
        //eastcom:  6997c46956185a7c4d452646fc9c69e2
        System.out.println(stringMD5("eastcomdsaf34wqfsdgdfsjtr6543t#$%^"));
        System.out.println("7ab7afd4589d1da2e44eea8649ffdd13".length());
        System.out.println(stringMD5("eastcomdsaf34wqfsdgdfsjtr6543t#$%^").equals("7ab7afd4589d1da2e44eea8649ffdd13"));
    }
}
