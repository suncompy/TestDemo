package com.khy.utils.utils.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.Security;
import java.util.Base64;


/**
 * @author khy
 * AES 对称加密 算法
 */
public class AesUtils {

    private static Logger logger = LoggerFactory.getLogger(AesUtils.class);

    private static String charset = "UTF-8";
    private static int offset = 5;
    private static int len = 16;
    private static String transformation = "AES/CBC/PKCS7Padding";
    private static String algorithm = "AES";

    private static boolean initialized = false;

    private static org.bouncycastle.jce.provider.BouncyCastleProvider bouncyCastleProvider = null;

    public static synchronized org.bouncycastle.jce.provider.BouncyCastleProvider getInstance() {
        if (bouncyCastleProvider == null) {
            bouncyCastleProvider = new org.bouncycastle.jce.provider.BouncyCastleProvider();
        }
        return bouncyCastleProvider;
    }

    public static void initialize() {
        if (initialized) return;
        Security.addProvider(getInstance());
        initialized = true;
    }


    /**
     * 加密
     *
     * @param content
     * @return
     */
    public static String encrypt(String content, String key, String IV) {
        return encrypt(content, key, IV, len, offset);
    }

    /**
     * 解密
     *
     * @param content
     * @return
     */
    public static String decrypt(String content, String key, String IV) {
        return decrypt(content, key, IV, len, offset);
    }

    /**
     * 加密
     *
     * @return
     */
    public static String encrypt(String content, String key, String IV, int len, int offset) {
        try {
            initialize();
            SecretKeySpec skey = new SecretKeySpec(key.getBytes(), algorithm);
            IvParameterSpec iv = new IvParameterSpec(IV.getBytes(), offset, len);
            Cipher cipher = Cipher.getInstance(transformation, "BC");
            byte[] byteContent = content.getBytes(charset);
            cipher.init(Cipher.ENCRYPT_MODE, skey, iv);
            byte[] result = cipher.doFinal(byteContent);
            return Base64.getEncoder().encodeToString(result);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return null;
    }

    /**
     * AES（256）解密
     *
     * @return 解密之后
     * @throws Exception
     */
    public static String decrypt(String content, String key, String IV, int len, int offset) {
        try {
            initialize();
            SecretKeySpec skey = new SecretKeySpec(key.getBytes(), algorithm);
            IvParameterSpec iv = new IvParameterSpec(IV.getBytes(), offset, len);
            Cipher cipher = Cipher.getInstance(transformation, "BC");
            cipher.init(Cipher.DECRYPT_MODE, skey, iv);
            byte[] result = cipher.doFinal(Base64.getDecoder().decode(content));
            return new String(result, charset);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return null;
    }

    public static void main(String[] args) throws Exception {
        System.out.println("34918115696275563155931516818217".length());
        String key = "34918115696275563155931516818217";
        String s = "hello world";
        // 加密
        System.out.println("加密前：" + s);
        String encryptResultStr = encrypt(s, key, key);
        System.out.println("加密后：" + encryptResultStr);
        // 解密
        System.out.println("解密后：" + decrypt(encryptResultStr, key, key));
    }
}
