package com.bfly.common.ipseek.ipapi;

import com.alibaba.fastjson.JSONObject;
import com.bfly.common.NetUtil;
import com.bfly.common.ipseek.IPLocation;
import com.bfly.common.json.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 国外免费
 * http://ip-api.com/
 *
 * @author andy_hulibo@163.com
 * @date 2019/7/26 15:29
 */
public class IpApiIpUtil {

    private static Logger logger = LoggerFactory.getLogger(IpApiIpUtil.class);

    /**
     * ip-api ip查询地址
     *
     * @date 2019/7/26 13:27
     */
    private static final String URL = "http://ip-api.com/json/";

    /**
     * 根据IP得到地址对象 获取失败返回null
     *
     * @author andy_hulibo@163.com
     * @date 2019/7/26 13:28
     */
    public static IPLocation getLocation(String ip) {
        try {
            String url = IpApiIpUtil.URL.concat(ip).concat("?lang=zh-CN");
            String dataStr = NetUtil.getHttpResponseData(url, "", "GET", false, NetUtil.ContentTypeEnum.JSON);
            JSONObject json = JsonUtil.parse(dataStr, JSONObject.class);

            //成功
            if (json != null && "success".equalsIgnoreCase(json.getString("status"))) {
                IPLocation location = new IPLocation();
                location.setCountry(json.getString("country"));
                location.setArea(json.getString("regionName"));
                return location;
            }
            logger.warn("ip-api解析IP失败!");
        } catch (Exception e) {
            logger.error("ip-api解析IP出错", e);
        }
        return null;
    }
}
