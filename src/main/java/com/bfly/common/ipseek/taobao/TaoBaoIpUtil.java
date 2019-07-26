package com.bfly.common.ipseek.taobao;

import com.alibaba.fastjson.JSONObject;
import com.bfly.common.NetUtil;
import com.bfly.common.ipseek.IPLocation;
import com.bfly.common.json.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 淘宝IP工具类
 * 访问频率需小于1qps
 * http://ip.taobao.com/instructions.html
 *
 * @author andy_hulibo@163.com
 * @date 2019/7/26 13:26
 */
public class TaoBaoIpUtil {

    private static Logger logger = LoggerFactory.getLogger(TaoBaoIpUtil.class);

    /**
     * 淘宝ip查询地址
     *
     * @date 2019/7/26 13:27
     */
    private static final String URL = "http://ip.taobao.com/service/getIpInfo.php?ip=";

    /**
     * 根据IP得到地址对象 获取失败返回null
     *
     * @author andy_hulibo@163.com
     * @date 2019/7/26 13:28
     */
    public static IPLocation getLocation(String ip) {
        try {
            String url = TaoBaoIpUtil.URL.concat(ip);
            String dataStr = NetUtil.getHttpResponseData(url, "", "GET", false, NetUtil.ContentTypeEnum.JSON);
            JSONObject json = JsonUtil.parse(dataStr, JSONObject.class);
            String code = "code";

            //成功
            if (json != null && json.getInteger(code) == 0) {
                json = json.getJSONObject("data");
                IPLocation location = new IPLocation();
                location.setCountry(json.getString("country"));
                location.setArea(json.getString("region"));
                return location;
            }
            logger.warn("淘宝解析IP失败!");
        } catch (Exception e) {
            logger.error("淘宝解析IP出错", e);
        }
        return null;
    }
}
