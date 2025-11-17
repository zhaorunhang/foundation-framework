package com.zrh.framework.core.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;

/**
 * @PACKAGE_NAME: com.zrh.framework.core.utils
 * @NAME: ZRH
 * @USER: ZRH13
 * @DATE: 2025/11/1
 * @TIME: 16:45
 * @Description: 密码加密工具类，使用 PBKDF2WithHmacSHA256 算法进行加密
 */
public class PasswordUtil {
    // 算法名称
    private static final String ALGORITHM = "PBKDF2WithHmacSHA256";
    // 迭代次数
    private static final int ITERATIONS = 10000;
    // 密钥长度
    private static final int KEY_LENGTH = 256;
    // 盐的长度
    private static final int SALT_LENGTH = 32;

    /**
     * 生成随机盐
     *
     * @return Base64编码的盐
     */
    public static String generateSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[SALT_LENGTH];
        random.nextBytes(salt);
        return Base64.getEncoder().encodeToString(salt);
    }

    /**
     * 使用盐对密码进行加密
     *
     * @param password 原始密码
     * @param salt     Base64编码的盐
     * @return Base64编码的密码哈希
     */
    public static String encryptPassword(String password, String salt) {
        try {
            byte[] saltBytes = Base64.getDecoder().decode(salt);
            PBEKeySpec spec = new PBEKeySpec(
                    password.toCharArray(),
                    saltBytes,
                    ITERATIONS,
                    KEY_LENGTH
            );

            SecretKeyFactory factory = SecretKeyFactory.getInstance(ALGORITHM);
            byte[] hash = factory.generateSecret(spec).getEncoded();

            return Base64.getEncoder().encodeToString(hash);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new RuntimeException("密码加密失败", e);
        }
    }

    /**
     * 验证密码是否正确
     *
     * @param password     待验证的密码
     * @param salt         数据库中存储的盐
     * @param passwordHash 数据库中存储的密码哈希
     * @return 密码是否匹配
     */
    public static boolean verifyPassword(String password, String salt, String passwordHash) {
        String newHash = encryptPassword(password, salt);
        return newHash.equals(passwordHash);
    }

    /**
     * 加密结果对象
     */
    @Getter
    @AllArgsConstructor
    public static class EncryptResult {
        private String passwordHash;
        private String salt;
    }

    public static String getPasswordStrengthMessage(String password) {
        if (password == null || password.isEmpty()) {
            return "密码不能为空";
        }

        if (password.length() < 8) {
            return "密码长度不能少于8位";
        }

        StringBuilder missing = new StringBuilder("密码必须包含至少三种字符类型，当前缺少：");

        if (!password.matches(".*[a-z].*")) {
            missing.append("小写字母、");
        }else

        if (!password.matches(".*[A-Z].*")) {
            missing.append("大写字母、");
        }

        if (!password.matches(".*[0-9].*")) {
            missing.append("数字、");
        }

        if (!password.matches(".*[^a-zA-Z0-9].*")) {
            missing.append("特殊字符、");
        }
        return missing.substring(0, missing.length() - 1);
    }

    /**
     * 对新密码进行加密（生成盐并加密）
     *
     * @param password 原始密码
     * @return 包含密码哈希和盐的结果对象
     */
    public static EncryptResult encrypt(String password) {
        String salt = generateSalt();
        String passwordHash = encryptPassword(password, salt);
        return new EncryptResult(passwordHash, salt);
    }


}
