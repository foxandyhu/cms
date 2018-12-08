package com.bfly.common;

import com.bfly.core.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * HttpServletResponse响应工具类
 *
 * @author 胡礼波
 * 2013-6-26 上午11:23:53
 */
public class ResponseUtil {

    private static Logger logger = LoggerFactory.getLogger(ResponseUtil.class);

    /**
     * 向客户端输出javascript脚本
     *
     * @param response
     * @param jsText
     * @author 胡礼波-andy
     * @2013-6-23下午3:49:10
     */
    public static void writeJavascript(HttpServletResponse response, String jsText) {
        writeData(response, jsText, "application/javascript");
    }

    /**
     * 输出文本字符串
     *
     * @param response
     * @param text
     * @author 胡礼波-andy
     * @2013-6-22下午8:33:35
     */
    public static void writeHtml(HttpServletResponse response, Object text) {
        writeData(response, String.valueOf(text), "text/html");
    }

    /**
     * 输出文本字符串
     *
     * @param response
     * @param text
     * @author 胡礼波-andy
     * @2013-6-22下午8:33:35
     */
    public static void writeText(HttpServletResponse response, Object text) {
        writeData(response, String.valueOf(text), "text/plain");
    }

    /**
     * 输出xml
     *
     * @param response
     * @param xml
     * @author 胡礼波-andy
     * @2013-6-22下午8:34:29
     */
    public static void writeXml(HttpServletResponse response, String xml) {
        writeData(response, xml, "application/xmlapplication/xml");
    }

    /**
     * 输出Json
     *
     * @param response
     * @param json
     * @author 胡礼波-andy
     * @2013-6-22下午8:35:08
     */
    public static void writeJson(HttpServletResponse response, String json) {
        writeData(response, json, "application/json");
    }

    /**
     * 输出数据 通常是Ajax调用
     *
     * @author 胡礼波
     * 2012-4-28 下午09:11:53
     */
    private static void writeData(HttpServletResponse response, String data, String contentType) {
        response.setCharacterEncoding(Constants.ENCODEING_UTF8);
        response.setContentType(contentType);
        try (PrintWriter out = response.getWriter()) {
            String dataStr = String.valueOf(data);
            out.write(dataStr);
            out.flush();
        } catch (Exception e) {
            logger.warn("数据流输入有错:" + e);
        }
    }

}
