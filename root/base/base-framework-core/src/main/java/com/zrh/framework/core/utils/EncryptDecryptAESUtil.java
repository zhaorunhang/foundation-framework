package com.zrh.framework.core.utils;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.SecureRandom;
import java.util.Base64;

/**
 * @PACKAGE_NAME: com.zrh.framework.core.utils
 * @NAME: ZRH
 * @USER: ZRH13
 * @DATE: 2025/10/27
 * @TIME: 18:56
 * @Description:
 */
public class EncryptDecryptAESUtil {
    private static final String ALGORITHM = "AES";
    private static final String MODE = "CBC";
    private static final String PADDING = "PKCS5Padding";

    // 密钥 (16 字节)
    private static final String SECRET_KEY = "1234567890123456";

    // 生成随机初始化向量 (IV)
    private static String generateIV() {
        SecureRandom random = new SecureRandom();
        byte[] iv = new byte[16]; // AES块大小为16字节
        random.nextBytes(iv);
        return Base64.getEncoder().encodeToString(iv);
    }

    // AES 加密方法
    private static String encrypt(String data, String secretKey, String iv) throws Exception {
        SecretKeySpec key = new SecretKeySpec(secretKey.getBytes(), ALGORITHM);
        IvParameterSpec ivSpec = new IvParameterSpec(Base64.getDecoder().decode(iv));
        Cipher cipher = Cipher.getInstance(ALGORITHM + "/" + MODE + "/" + PADDING);
        cipher.init(Cipher.ENCRYPT_MODE, key, ivSpec);
        byte[] encrypted = cipher.doFinal(data.getBytes());
        return Base64.getEncoder().encodeToString(encrypted);
    }

    // AES 解密方法
    private static String decrypt(String encryptedData, String secretKey, String iv) throws Exception {
        SecretKeySpec key = new SecretKeySpec(secretKey.getBytes(), ALGORITHM);
        IvParameterSpec ivSpec = new IvParameterSpec(Base64.getDecoder().decode(iv));
        Cipher cipher = Cipher.getInstance(ALGORITHM + "/" + MODE + "/" + PADDING);
        cipher.init(Cipher.DECRYPT_MODE, key, ivSpec);
        byte[] decrypted = cipher.doFinal(Base64.getDecoder().decode(encryptedData));
        return new String(decrypted);
    }
}
