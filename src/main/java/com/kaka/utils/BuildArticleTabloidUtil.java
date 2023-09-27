package com.kaka.utils;

/**
 * 工具类：用于从markdown内容中生成文章的摘要。
 * 该类提供了一个方法，从文章的HTML内容中提取简短的摘要。
 *这是一个用于从markdown内容中生成文章摘要的工具类。
 * 主要功能是从文章的HTML内容中提取出一个简短的摘要，这个摘要是纯文本格式，最大长度为197个字符。
 * 在处理过程中，该方法会移除所有的HTML标签，并确保摘要的内容是连续的，没有被HTML标签打断。
 * BuildArticleTabloidUtil 工具类的设计目的就是为了从HTML格式的文章内容中生成摘要。
 * 所以，每当你需要从文章的HTML内容中提取摘要时，你都可以使用这个工具类。
 *
 * 只要你提供了合适的HTML格式的文章内容作为输入，
 * 这个工具类就可以帮助你生成相应的摘要。
 * 这样，无论是在文章发布、编辑还是其他与文章内容相关的场景中，都可以使用这个工具类来获取摘要。
 * @author: IT枫斗者
 * @Date: 2022/6/24 9:51
 */
public class BuildArticleTabloidUtil {

    /**
     * 从给定的HTML格式的文章内容中提取摘要。
     * 该方法处理HTML内容，移除所有HTML标签，并返回纯文本的摘要。
     *
     * @param htmlArticleComment 文章的HTML格式内容。
     * @return 文章的纯文本摘要，最大长度为197个字符。
     */
    public String buildArticleTabloid(String htmlArticleComment){

        // 使用正则表达式匹配所有的空白字符。
        String regex = "\\s+";
        // 移除字符串的前后空白字符。
        String str = htmlArticleComment.trim();
        // 将所有的空白字符替换为空字符串。
        String articleTabloid = str.replaceAll(regex,StringUtil.BLANK);

        // 查找HTML标签的开始和结束位置。
        int beginIndex = articleTabloid.indexOf("<");
        int endIndex = articleTabloid.indexOf(">");
        String myArticleTabloid = "";
        String nowStr = "";

        // 循环处理，直到没有HTML标签为止。
        while (beginIndex != -1){
            // 获取当前标签之前的文本内容。
            nowStr = articleTabloid.substring(0, beginIndex);
            // 如果文本内容超过197个字符，只截取前197个字符。
            if(nowStr.length() > 197){
                nowStr = nowStr.substring(0,197);
                myArticleTabloid += nowStr;
            } else {
                myArticleTabloid += nowStr;
            }

            // 移除已经处理过的部分。
            articleTabloid = articleTabloid.substring(endIndex + 1);
            beginIndex = articleTabloid.indexOf("<");

            // 如果摘要的长度小于197个字符，继续处理。
            if(myArticleTabloid.length() < 197){
                // 过滤掉<pre>标签中的代码块。
                if(articleTabloid.length() > 4){
                    if(articleTabloid.charAt(beginIndex) == '<' && articleTabloid.charAt(beginIndex+1) == 'p'  && articleTabloid.charAt(beginIndex+2) == 'r'  && articleTabloid.charAt(beginIndex+3) == 'e' ){
                        endIndex = articleTabloid.indexOf("</pre>");
                        endIndex = endIndex + 5;
                    } else {
                        endIndex = articleTabloid.indexOf(">");
                    }
                } else {
                    endIndex = articleTabloid.indexOf(">");
                }
            } else {
                break;
            }
        }

        // 如果摘要的长度超过197个字符，只截取前197个字符。
        if(myArticleTabloid.length() > 197){
            myArticleTabloid = myArticleTabloid.substring(0, 197);
        }

        return myArticleTabloid;
    }
}
