package com.kaka.utils;

import com.kaka.constant.CodeType;
import java.util.LinkedHashMap;
import com.alibaba.fastjson.JSON;

/**
 * 控制层Json数据处理工具类
 *
 * @author mac
 * @date 2023/09/26
 */
public class JsonResult {

    // 定义常量，用于表示成功和失败的状态码
    private static final int DEFAULT_STATUS_SUCCESS = 0;
    private static final int DEFAULT_STATUS_FAIL = 1;

    // 定义默认的JSON键名称
    private static final String DEFAULT_STATUS_KEY = "status";
    private static final String DEFAULT_MESSAGE_KEY = "message";
    private static final String DEFAULT_DATA_KEY = "data";

    // 私有构造方法，防止实例化
    private JsonResult() {
    }

    // 创建表示成功的JsonData对象的静态方法
    public static JsonData success() {
        return build(DEFAULT_STATUS_KEY, DEFAULT_STATUS_SUCCESS);
    }

    // 创建表示成功的JsonData对象的静态方法，可指定状态码
    public static JsonData success(Object code) {
        return build(DEFAULT_STATUS_KEY, code);
    }

    // 创建表示成功的JsonData对象的静态方法，可指定状态码的键和值
    public static JsonData success(String codeKey, Object code) {
        return build(codeKey, code);
    }

    // 创建表示失败的JsonData对象的静态方法
    public static JsonData fail() {
        return build(DEFAULT_STATUS_KEY, DEFAULT_STATUS_FAIL);
    }

    // 创建表示失败的JsonData对象的静态方法，可指定状态码
    public static JsonData fail(Object code) {
        return build(DEFAULT_STATUS_KEY, code);
    }

    // 创建表示失败的JsonData对象的静态方法，可指定状态码的键和值
    public static JsonData fail(String codeKey, Object code) {
        return build(codeKey, code);
    }

    // 创建JsonData对象的内部方法，用于构建JSON响应
    private static JsonData build(String key, Object value) {
        JsonData jsonData = new JsonResult().new JsonData(3);
        // 如果值是CodeType枚举类型，将状态码和消息添加到JSON
        if (value instanceof CodeType) {
            jsonData.put(key, ((CodeType) value).getCode());
            jsonData.put(DEFAULT_MESSAGE_KEY, ((CodeType) value).getMessage());
        } else {
            // 否则，只添加指定的键和值
            jsonData.put(key, value);
        }

        return jsonData;
    }

    // 创建一个空的JsonData对象
    public static JsonData build() {
        return new JsonResult().new JsonData();
    }

    // 创建JsonData对象，基于DataMap对象的数据
    public static JsonData build(DataMap dataMap) {
        JsonData jsonData = new JsonResult().new JsonData(3);
        // 根据DataMap的数据构建JSON响应
        if (dataMap.getCode() != null) {
            jsonData.put(DEFAULT_STATUS_KEY, dataMap.getCode());
        } else {
            if (dataMap.getSuccess() != null) {
                if (dataMap.getSuccess()) {
                    jsonData.put(DEFAULT_STATUS_KEY, DEFAULT_STATUS_SUCCESS);
                } else {
                    jsonData.put(DEFAULT_STATUS_KEY, DEFAULT_STATUS_FAIL);
                }
            }
        }
        if (dataMap.getMessage() != null) {
            jsonData.put(DEFAULT_MESSAGE_KEY, dataMap.getMessage());
        }
        if (dataMap.getData() != null) {
            jsonData.put(DEFAULT_DATA_KEY, dataMap.getData());
        }
        return jsonData;
    }

    // 内部类，表示JSON数据的实体
    public class JsonData extends LinkedHashMap {

        private static final long serialVersionUID = 1L;

        // 无参构造函数
        private JsonData() {
        }

        // 有参构造函数，指定初始化容量
        private JsonData(int size) {
            super(size);
        }

        // 设置消息的方法，可指定消息键和消息内容
        public JsonData message(String messageKey, String message) {
            this.put(messageKey, message);
            return this;
        }

        // 设置消息的方法，只指定消息内容，使用默认的消息键
        public JsonData message(String message) {
            return message(DEFAULT_MESSAGE_KEY, message);
        }

        // 设置数据的方法，可指定数据键和数据内容
        public JsonData data(String dataKey, Object data) {
            this.put(dataKey, data);
            return this;
        }

        // 设置数据的方法，只指定数据内容，使用默认的数据键
        public JsonData data(Object data) {
            return data(DEFAULT_DATA_KEY, data);
        }

        // 添加键值对到JsonData对象
        public JsonData add(String key, Object value) {
            this.put(key, value);
            return this;
        }

        // 将JsonData对象转换为JSON字符串
        public String toJSON() {
            return JSON.toJSONString(this);
        }
    }
}
