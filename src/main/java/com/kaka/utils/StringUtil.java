package com.kaka.utils;

/**
 * 作者：IT枫斗者
 * 日期：2021/11/18 15:42
 * 描述：这是一个字符常量工具类，用于定义和存储系统中使用的一些字符串常量。
 * 这是一个字符常量工具类，主要用于定义和存储系统中使用的一些字符串常量，
 * 如空字符串、用户名的最大长度、文章点赞的标志以及访客标识等。
 * 这些常量在系统的其他部分中可能会被频繁使用，所以将它们定义在一个工具类中可以提高代码的可读性和维护性。
 */
public class StringUtil {

    // 空字符串常量
    public static final String BLANK = "";

    // 用户名的最大长度常量
    public static final int USERNAME_MAX_LENGTH = 35;

    /**
     * 文章点赞的标志常量。
     * 用于标识文章是否被点赞。
     */
    public static final String ARTICLE_THUMBS_UP = "articleThumbsUp";

    // 访客标识常量
    // 用于标识某个用户为访客。
    public static final String VISITOR = "visitor";
}
