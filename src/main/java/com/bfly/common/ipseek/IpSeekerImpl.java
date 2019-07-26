package com.bfly.common.ipseek;

import com.bfly.common.ipseek.ip2region.Ip2RegionUtil;
import com.bfly.common.ipseek.ipapi.IpApiIpUtil;
import com.bfly.common.ipseek.ipip.IpIpIpUtil;
import com.bfly.common.ipseek.taobao.TaoBaoIpUtil;

/**
 * @author andy_hulibo@163.com
 * @date 2018/11/29 10:20
 */
public class IpSeekerImpl implements IPSeeker {

    @Override
    public IPLocation getIPLocation(String ip) {
        if (ip == null || ip.length() < 7) {
            return null;
        }
        IPLocation location = Ip2RegionUtil.getLocation(ip);
        if (location == null) {
            location = IpIpIpUtil.getLocation(ip);
        }
        if (location == null) {
            location = IpApiIpUtil.getLocation(ip);
        }
        if (location == null) {
            location = TaoBaoIpUtil.getLocation(ip);
        }
        return location;
    }
}
