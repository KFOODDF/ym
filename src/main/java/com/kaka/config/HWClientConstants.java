package com.kaka.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author: IT枫斗者
 * @Date: 2018/6/9 19:45
 * Describe: 华为云连接密钥
 */
@Component
public class HWClientConstants {

    /**
     * 华为云OBS的外网域名
     */
    public static final String ENDPOINT = "https://obs.cn-east-3.myhuaweicloud.com";

    /**
     * 华为云短信API的密钥Access Key ID
     */
    public static  String APP_KEY_ID;
    /**
     *华为云短信API的密钥Access Key Secret
     */
    public static String APP_KEY_SECRET;

    /**
     * 华为云OBS AK
     */
    public static String AK;

    /**
     * 华为云OBS SK
     */
    public static String SK;

    /**
     * 华为云API的bucket名称
     * 在华为云上自己创建一个bucket
     */
    public static final String BACKET_NAME = "javatiaozaowang";

    /**
     * 华为云API的文件夹名称
     * 在华为云上自己创建一个文件夹，方便分类管理图片
     */
    public static final String FOLDER="UserHead/";

    @Value("${hwyun.appKey}")
    public void setAccessKeyId(String appKey) {
        APP_KEY_ID = appKey;
    }

    @Value("${hwyun.appSecret}")
    public void setAccessKeySecret(String appSecret) {
        APP_KEY_SECRET = appSecret;
    }

    @Value("${hwyun.ak}")
    public void setAy(String ak) {
        AK = ak;
    }

    @Value("${hwyun.sk}")
    public void setSk(String sk) {
        SK = sk;
    }
}
