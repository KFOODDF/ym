package com.kaka.utils;
import com.kaka.config.HWClientConstants;
import com.obs.services.ObsClient;
import com.obs.services.model.HeaderResponse;
import com.obs.services.model.ObjectMetadata;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.net.URL;
import java.util.Date;
import java.util.UUID;

/**
 * @author: IT枫斗者
 * @Date: 2018/6/10 17:45
 * Describe: 华为云ObS连接
 */
@Slf4j
public class HwYunOBSClientUtil {

    private static String ENDPOINT;

    private static String AK;

    private static String SK;

    private static String BACKET_NAME;

    private static String FOLDER;

    //初始化属性
    static {
        ENDPOINT = HWClientConstants.ENDPOINT;
        AK = HWClientConstants.AK;
        SK = HWClientConstants.SK;
        BACKET_NAME = HWClientConstants.BACKET_NAME;
        FOLDER = HWClientConstants.FOLDER;
    }

    /**
     * 获得华为云OSS客户端对象
     *
     * @return ossClient
     */
    public static ObsClient getOBSClient() {
        return new ObsClient(AK, SK, ENDPOINT);
    }

    /**
     * 创建存储空间
     *
     * @param ossClient  OSS连接
     * @param bucketName 存储空间
     * @return
     */
//    public static String createBucketName(OSSClient ossClient, String bucketName) {
//        //存储空间
//        final String bucketNames = bucketName;
//        if (!ossClient.doesBucketExist(bucketName)) {
//            //创建存储空间
//            Bucket bucket = ossClient.createBucket(bucketName);
//            log.info("创建存储空间成功");
//            return bucket.getName();
//        }
//        return bucketNames;
//    }

    /**
     * 删除存储空间buckName
     *
     * @param ossClient  oss对象
     * @param bucketName 存储空间
     */
//    public static void deleteBucket(OSSClient ossClient, String bucketName) {
//        ossClient.deleteBucket(bucketName);
//        log.info("删除" + bucketName + "Bucket成功");
//    }


    /**
     * 创建模拟文件夹
     *
     * @param ossClient  oss连接
     * @param bucketName 存储空间
     * @param folder     模拟文件夹名如"qj_nanjing/"
     * @return 文件夹名
     */
//    public static String createFolder(OSSClient ossClient, String bucketName, String folder) {
//        //文件夹名
//        final String keySuffixWithSlash = folder;
//        //判断文件夹是否存在，不存在则创建
//        if (!ossClient.doesObjectExist(bucketName, keySuffixWithSlash)) {
//            //创建文件夹
//            ossClient.putObject(bucketName, keySuffixWithSlash, new ByteArrayInputStream(new byte[0]));
//            log.info("创建文件夹成功");
//            //得到文件夹名
//            OSSObject object = ossClient.getObject(bucketName, keySuffixWithSlash);
//            String fileDir = object.getKey();
//            return fileDir;
//        }
//        return keySuffixWithSlash;
//    }

    /**
     * 根据key删除OSS服务器上的文件
     *
     * @param ossClient  oss连接
     * @param bucketName 存储空间
     * @param folder     模拟文件夹名 如"qj_nanjing/"
     * @param key        Bucket下的文件的路径名+文件名 如："upload/cake.jpg"
     */
//    public static void deleteFile(OSSClient ossClient, String bucketName, String folder, String key) {
//        ossClient.deleteObject(bucketName, folder + key);
//        log.info("删除" + bucketName + "下的文件" + folder + key + "成功");
//    }

    /**
     * 上传图片至OBS
     *
     * @param obsClient  obs连接
     * @param file       上传文件
     * @param bucketName 存储空间
     * @param folder     模拟文件夹名 如"qj_nanjing/"
     * @return String 返回的唯一MD5数字签名
     */
    public static String uploadObject2ObS(ObsClient obsClient, File file, String bucketName, String folder) {
        String resultStr = null;
        try {
//            //以输入流的形式上传文件
            InputStream is = new FileInputStream(file);
//            //文件名
            String fileName = file.getName();
            //文件大小
            Long fileSize = file.length();
            //创建上传Object的Metadata
            ObjectMetadata metadata = new ObjectMetadata();
            //上传的文件的长度
            metadata.setContentLength((long) is.available());
            //指定该Object被下载时的网页的缓存行为
            metadata.setCacheControl("no-cache");
            //指定该Object下设置Header
           // metadata.setHeader("Pragma", "no-cache");
            //指定该Object被下载时的内容编码格式
            metadata.setContentEncoding("utf-8");
            //文件的MIME，定义文件的类型及网页编码，决定浏览器将以什么形式、什么编码读取文件。如果用户没有指定则根据Key或文件名的扩展名生成，
            //如果没有扩展名则填默认值application/octet-stream
            metadata.setContentType(getContentType(fileName));
            //指定该Object被下载时的名称（指示MINME用户代理如何显示附加的文件，打开或下载，及文件名称）
            metadata.setContentDisposition("filename/filesize=" + fileName + "/" + fileSize + "Byte.");
            //上传文件   (上传文件流的形式)
//            PutObjectResult putResult = obsClient.putObject(bucketName, folder + fileName, is, metadata);
//            //解析结果
//            resultStr = putResult.getETag();
//            File  filePath=    new File(file);
//            String contentType = getContentType(fileName);
            //文件后缀名
            String fileExtension = fileName.substring(fileName.lastIndexOf("."));

            //objcetName
            String objectname = UUID.randomUUID().toString().replace("-", "");

            HeaderResponse response = obsClient.putObject(bucketName, folder + objectname + fileExtension, file);  // localfile为待上传的本地文件路径，需要指定到具体的文件名
            //返回文件名称 ENDPOINT+":443"/UserHead%2
            resultStr = "https://javatiaozaowang.obs.cn-east-3.myhuaweicloud.com:443/UserHead%2F"+objectname + fileExtension;//objectname + fileExtension;
            System.out.println(response);
            //System.out.println(objectname);

        } catch (Exception e) {
            e.printStackTrace();
            log.error("上传华为云OSS服务器异常." + e.getMessage(), e);
        } finally {

            // 关闭ObsClient实例，如果是全局ObsClient实例，可以不在每个方法调用完成后关闭
            // ObsClient在调用ObsClient.close方法关闭后不能再次使用
            if (obsClient != null) {
                try {
                    obsClient.close();
                } catch (IOException e) {
                }
            }
        }
        return resultStr;
    }

    /**
     * 通过文件名判断并获取OSS服务文件上传时文件的contentType
     *
     * @param fileName 文件名
     * @return 文件的contentType
     */
    public static String getContentType(String fileName) {
        //文件的后缀名
        String fileExtension = fileName.substring(fileName.lastIndexOf("."));
        if (".bmp".equalsIgnoreCase(fileExtension)) {
            return "image/bmp";
        }
        if (".gif".equalsIgnoreCase(fileExtension)) {
            return "image/gif";
        }
        if (".jpeg".equalsIgnoreCase(fileExtension) || ".jpg".equalsIgnoreCase(fileExtension) || ".png".equalsIgnoreCase(fileExtension)) {
            return "image/jpeg";
        }
        if (".html".equalsIgnoreCase(fileExtension)) {
            return "text/html";
        }
        if (".txt".equalsIgnoreCase(fileExtension)) {
            return "text/plain";
        }
        if (".vsd".equalsIgnoreCase(fileExtension)) {
            return "application/vnd.visio";
        }
        if (".ppt".equalsIgnoreCase(fileExtension) || "pptx".equalsIgnoreCase(fileExtension)) {
            return "application/vnd.ms-powerpoint";
        }
        if (".doc".equalsIgnoreCase(fileExtension) || "docx".equalsIgnoreCase(fileExtension)) {
            return "application/msword";
        }
        if (".xml".equalsIgnoreCase(fileExtension)) {
            return "text/xml";
        }
        //默认返回类型
        return "image/jpeg";
    }

    /**
     * 获得url链接
     *
     * @param key
     * @return
     */
//    public static String getUrl(OSSClient ossClient, String key) {
//        // 设置URL过期时间为10年  3600l* 1000*24*365*10
//        Date expiration = new Date(System.currentTimeMillis() + 3600L * 1000 * 24 * 365 * 10);
//        // 生成URL
//        URL url = ossClient.generatePresignedUrl(HWClientConstants.BACKET_NAME, key, expiration);
//        if (url != null) {
//            return url.toString();
//        }
//        return null;
//    }

}
