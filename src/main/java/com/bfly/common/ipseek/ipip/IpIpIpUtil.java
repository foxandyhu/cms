package com.bfly.common.ipseek.ipip;

import com.bfly.common.NetUtil;
import com.bfly.common.ipseek.IPLocation;
import com.bfly.common.json.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * IPIP ip查询工具类
 * 限速每天1000次
 * https://www.ipip.net/support/api.html
 *
 * @author andy_hulibo@163.com
 * @date 2019/7/26 15:12
 */
public class IpIpIpUtil {

    private static Logger logger = LoggerFactory.getLogger(IpIpIpUtil.class);

    /**
     * IPIP ip查询地址
     *
     * @date 2019/7/26 13:27
     */
    private static final String URL = "http://freeapi.ipip.net/";

    /**
     * 根据IP得到地址对象 获取失败返回null
     *
     * @author andy_hulibo@163.com
     * @date 2019/7/26 13:28
     */
    public static IPLocation getLocation(String ip) {
        try {
            String url = IpIpIpUtil.URL.concat(ip);
            String dataStr = NetUtil.getHttpResponseData(url, "", "GET", false, NetUtil.ContentTypeEnum.JSON);
            String[] data = JsonUtil.parse(dataStr, String[].class);

            //成功
            if (data != null && data.length == 5) {
                IPLocation location = new IPLocation();
                location.setCountry(data[0]);
                location.setArea(data[1]);
                return location;
            }
            logger.warn("IPIP解析IP失败!");
        } catch (Exception e) {
            logger.error("IPIP解析IP出错", e);
        }
        return null;
    }
}
