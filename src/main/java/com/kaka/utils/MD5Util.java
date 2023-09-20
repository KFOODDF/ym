package com.kaka.utils;

import java.security.MessageDigest;

/**
 * MD5加密工具类
 *
 * @author IT枫斗者
 * @Date 2022/6/5 19:58
 */
public class MD5Util {

    // MD5加密的盐值
    private static final String SALT = "tamboo";

    /**
     * 对输入的密码进行MD5加密
     *
     * @param password 要加密的密码
     * @return 加密后的密码字符串
     */
    public String encode(String password) {
        // 将值追加到密码后面
        password = password + SALT;

        MessageDigest md5 = null;
        try {
            // 获取MD5加密算法的实例
            md5 = MessageDigest.getInstance("MD5");
        } catch (Exception e) {
            // 抛出运行时异常，如果无法获取MD5实例
            throw new RuntimeException(e);
        }

        char[] charArray = password.toCharArray();
        byte[] byteArray = new byte[charArray.length];

        for (int i = 0; i < charArray.length; i++) {
            byteArray[i] = (byte) charArray[i];
        }

        // 计算MD5摘要
        byte[] md5Bytes = md5.digest(byteArray);

        // 将摘要转换成十六进制字符串
        StringBuffer hexValue = new StringBuffer();
        for (byte md5Byte : md5Bytes) {
            int val = ((int) md5Byte) & 0xff;
            if (val < 16) {
                hexValue.append("0");
            }
            hexValue.append(Integer.toHexString(val));
        }

        return hexValue.toString();
    }
}
