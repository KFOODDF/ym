package com.kaka.utils;

import com.kaka.constant.CodeType;
import java.util.LinkedHashMap;

/**
 * 业务层数据封装工具类
 * DataMap 是一个工具类，继承自 LinkedHashMap，用于业务层数据的封装。
 * 它提供了一种统一的方式来封装返回给前端或其他调用者的数据，
 * 包括响应的状态码、消息、成功或失败的状态以及实际的数据内容。
 */
public class DataMap<T> extends LinkedHashMap {

    private static final long serialVersionUID = 1L;

    private Integer code;       // 响应状态码
    private String message;     // 响应消息
    private Boolean success;    // 响应成功状态标志

    private T data;             // 响应数据

    // 私有构造方法，防止实例化
    private DataMap() {
    }

    // 创建表示成功的DataMap对象的静态方法
    public static <T> DataMap<T> success() {
        DataMap<T> dataMap = new DataMap<T>();
        dataMap.success = true;
        dataMap.code = CodeType.SUCCESS_STATUS.getCode(); // 默认成功状态码
        return dataMap;
    }


    // 创建表示成功的DataMap对象的静态方法，可指定状态码
    public static <T> DataMap<T> success(Object code) {
        DataMap<T> dataMap = new DataMap<T>();
        dataMap.success = true;
        if (code instanceof CodeType) {
            dataMap.code = ((CodeType) code).getCode();    // 如果传递的是CodeType枚举，获取其状态码
            dataMap.message = ((CodeType) code).getMessage(); // 获取枚举的消息
        } else if (code instanceof Integer) {
            dataMap.code = (Integer) code;                   // 如果传递的是整数，直接使用该整数作为状态码
        }
        return dataMap;
    }

    // 创建表示失败的DataMap对象的静态方法
    public static <T> DataMap<T> fail() {
        DataMap<T> dataMap = new DataMap<T>();
        dataMap.success = false; // 标记为失败
        return dataMap;
    }

    // 创建表示失败的DataMap对象的静态方法，可指定失败状态码或消息
    public static <T> DataMap<T> fail(T code) {
        DataMap<T> dataMap = new DataMap<T>();
        dataMap.success = false; // 标记为失败
        if (code instanceof CodeType) {
            dataMap.code = ((CodeType) code).getCode();    // 如果传递的是CodeType枚举，获取其状态码
            dataMap.message = ((CodeType) code).getMessage(); // 获取枚举的消息
        } else {
            dataMap.code = (Integer) code;                   // 如果传递的是整数，直接使用该整数作为状态码
        }
        return dataMap;
    }

    // 设置响应消息的方法，可传递消息内容或CodeType枚举
    public DataMap message(Object message) {
        if (message instanceof CodeType) {
            this.message = ((CodeType) message).getMessage(); // 如果传递的是CodeType枚举，获取其消息
        } else {
            this.message = (String) message;                   // 否则，直接使用传递的消息内容
        }
        return this;
    }

    // 获取响应数据
    public T getData() {
        return data;
    }

    // 设置响应数据
    public DataMap<T> setData(T data) {
        this.data = data;
        return this;
    }

    // 判断响应是否成功
    public Boolean isSuccess() {
        return this.success;
    }

    // 判断响应是否失败
    public Boolean isFail() {
        return !this.success;
    }

    // 获取响应状态码
    public Integer getCode() {
        return code;
    }

    // 设置响应状态码
    public void setCode(Integer code) {
        this.code = code;
    }

    // 获取响应消息
    public String getMessage() {
        return message;
    }

    // 设置响应消息
    public void setMessage(String message) {
        this.message = message;
    }

    // 获取响应成功状态标志
    public Boolean getSuccess() {
        return success;
    }

    // 设置响应成功状态标志
    public void setSuccess(Boolean success) {
        this.success = success;
    }
}
