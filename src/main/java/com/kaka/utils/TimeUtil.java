package com.kaka.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * @author: IT枫斗者
 * @Date: 2022/6/20 19:55
 * Describe: 时间工具类，提供一系列的时间格式化和解析方法
 */
public class TimeUtil {

    /**
     * 格式化当前日期到"年-月-日"格式的字符串
     *
     * @return 格式化后的日期字符串
     */
    public String getFormatDateForThree(){
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return now.format(format);
    }

    /**
     * 格式化当前日期和时间到"年-月-日 时:分:秒"格式的字符串
     *
     * @return 格式化后的日期时间字符串
     */
    public String getFormatDateForSix(){
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return now.format(format);
    }

    /**
     * 格式化当前日期和时间到"年-月-日 时:分"格式的字符串
     *
     * @return 格式化后的日期时间字符串
     */
    public String getFormatDateForFive(){
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return now.format(format);
    }

    /**
     * 将字符串日期解析成LocalDate对象（仅包含年月日）
     *
     * @param date 日期字符串，如"2018-06-21"
     * @return 解析后的LocalDate对象
     */
    public LocalDate getParseDateForThree(String date){
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return LocalDate.parse(date, format);
    }

    /**
     * 将字符串日期时间解析成LocalDate对象（仅包含年月日）
     *
     * @param date 日期时间字符串，如"2018-06-21 12:01:25"
     * @return 解析后的LocalDate对象
     */
    public LocalDate getParseDateForSix(String date){
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return LocalDate.parse(date, format);
    }

    /**
     * 获取当前时间的时间戳，单位是秒
     *
     * @return 当前时间的时间戳
     */
    public long getLongTime(){
        Date now = new Date();
        return now.getTime()/1000;
    }

    /**
     * 将包含年月的字符串转换成含有“年”和“月”汉字的字符串
     *
     * @param str 原字符串，如"2018-08"
     * @return 转换后的字符串，如"2018年8月"
     */
    public String timeWhippletreeToYear(String str){
        StringBuilder s = new StringBuilder();
        s.append(str.substring(0,4));
        s.append("年");
        s.append(str.substring(5,7));
        s.append("月");
        return String.valueOf(s);
    }

    /**
     * 将包含“年”和“月”汉字的字符串转换成年月中间用横杠连接的字符串
     *
     * @param str 原字符串，如"2018年07月"
     * @return 转换后的字符串，如"2018-07"
     */
    public String timeYearToWhippletree(String str){
        StringBuilder s = new StringBuilder();
        s.append(str.substring(0,4));
        s.append("-");
        s.append(str.substring(5,7));
        return String.valueOf(s);
    }

    /**
     * 将"年-月-日"格式的字符串解析成Date对象
     *
     * @param date 日期字符串
     * @return 解析后的Date对象，解析失败返回null
     */
    public Date stringToDateThree(String date){
        DateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return format1.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
}
