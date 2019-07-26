package com.bfly.common.ipseek.ip2region;

import com.bfly.common.ipseek.IPLocation;
import org.lionsoul.ip2region.DataBlock;
import org.lionsoul.ip2region.DbConfig;
import org.lionsoul.ip2region.DbSearcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * ip2region.db 本地IP数据库
 *
 * @author andy_hulibo@163.com
 * @date 2019/7/26 16:32
 */
public class Ip2RegionUtil {

    private static Logger logger = LoggerFactory.getLogger(Ip2RegionUtil.class);

    /**
     * ip-api ip查询地址
     *
     * @date 2019/7/26 13:27
     */
    private static final String URL = "ip2region.db";
    private static DbSearcher searcher;

    static {
        try {
            String path = ClassLoader.getSystemResource(URL).toURI().getPath();
            DbConfig config = new DbConfig();
            searcher = new DbSearcher(config, path);
        } catch (Exception e) {
            logger.error("初始化IP库出错!", e);
        }
    }

    /**
     * 根据IP得到地址对象 获取失败返回null
     *
     * @author andy_hulibo@163.com
     * @date 2019/7/26 13:28
     */
    public static IPLocation getLocation(String ip) {
        String NoData = "0";
        try {
            DataBlock block = searcher.btreeSearch(ip);
            String result = block.getRegion();
            String[] data = result.split("\\|");
            IPLocation location = new IPLocation();
            if (data[0].equals(NoData) || data[2].equals(NoData)) {
                return null;
            }
            location.setCountry(data[0]);
            location.setArea(data[2]);
            return location;
        } catch (Exception e) {
            logger.error("ip2region解析IP出错", e);
        }
        return null;
    }
}
