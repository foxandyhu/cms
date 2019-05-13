package com.bfly.common;

import com.bfly.core.Constants;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Map;


/**
 * 基于网络操作工具类
 *
 * @author 胡礼波
 * 2014-4-3 下午03:46:38
 */
public class NetUtil {

    /**
     * 获得Http连接
     *
     * @param httpUrl
     * @return
     * @throws Exception
     * @author 胡礼波
     * 2014-4-3 下午04:41:47
     */
    private static HttpURLConnection getHttpConnection(String httpUrl) throws Exception {
        URL url = new URL(httpUrl);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        return conn;
    }

    /**
     * 获取http请求响应的数据
     *
     * @param httpUrl       请求地址
     * @param params        请求参数
     * @param requestMethod 请求方式 GET POST等
     * @param isHtml        是否返回HTML格式的字符串
     * @return
     * @author 胡礼波
     * 2014-4-3 下午03:47:38
     */
    public static String getHttpResponseData(String httpUrl, Map<String, Object> params, String requestMethod, boolean isHtml, ContentTypeEnum type) throws Exception {
        return getHttpResponseData(httpUrl, getParmasStr(params), requestMethod, isHtml, type);
    }

    /**
     * 获取http请求响应的数据
     *
     * @param httpUrl       请求地址
     * @param dataStr       数据
     * @param requestMethod 请求方式 GET POST等
     * @param isHtml        是否返回HTML格式的字符串
     * @return
     * @author 胡礼波
     * 2014-4-3 下午03:47:38
     */
    public static String getHttpResponseData(String httpUrl, String dataStr, String requestMethod, boolean isHtml, ContentTypeEnum type) throws Exception {
        HttpURLConnection conn = getHttpConnection(httpUrl);
        conn.setRequestMethod(requestMethod);
        conn.setDoInput(true);
        conn.setDoOutput(true);
        switch (type) {
            case JSON:
                conn.setRequestProperty("Content-Type", "application/json; charset=" + Constants.ENCODEING_UTF8);
                break;
            case XML:
                conn.setRequestProperty("Content-Type", "text/xml; charset=" + Constants.ENCODEING_UTF8);
                break;
            case TEXT:
                conn.setRequestProperty("Content-Type", "text/plain; charset=" + Constants.ENCODEING_UTF8);
                break;
            default:
                break;
        }
        if (dataStr != null) {
            OutputStreamWriter out = new OutputStreamWriter(conn.getOutputStream(), Charset.forName(Constants.ENCODEING_UTF8));
            out.write(dataStr);
            out.flush();
            out.close();
        }

        BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), Charset.forName(Constants.ENCODEING_UTF8)));
        StringBuilder sb = new StringBuilder();
        String data;
        while ((data = reader.readLine()) != null) {
            sb.append(data);
            if (isHtml) {
                sb.append("\r\n");
            }
        }
        reader.close();
        conn.disconnect();
        return sb.toString();
    }

    /**
     * 得到参数字符串
     *
     * @param map
     * @return
     * @author 胡礼波
     * 2014-4-3 下午04:12:55
     */
    public static String getParmasStr(Map<String, Object> map) {
        StringBuilder sb = new StringBuilder();
        for (String key : map.keySet()) {
            sb.append(key + "=" + map.get(key));
            sb.append("&");
        }
        return sb.deleteCharAt(sb.lastIndexOf("&")).toString();
    }

    /**
     * 获得IP地址
     *
     * @return
     * @author 胡礼波
     * 2014-9-22 下午2:57:55
     */
    public static String getInetAddressIp(SocketAddress address) {
        if (address instanceof InetSocketAddress) {
            InetSocketAddress inetAddress = (InetSocketAddress) address;
            return inetAddress.getAddress().getHostAddress();
        }
        return null;
    }

    /**
     * 内容类型枚举
     *
     * @author 胡礼波-Andy
     * @2015年4月30日下午3:30:28
     */
    public static enum ContentTypeEnum {
        HTML, XML, JSON, TEXT;
    }
}
