package com.bfly.cms.system.service;

import com.bfly.cms.system.entity.CmsConfig;
import com.bfly.cms.system.entity.CmsConfigAttr;
import com.bfly.cms.system.entity.MarkConfig;
import com.bfly.cms.system.entity.MemberConfig;

import java.util.Date;
import java.util.Map;

/**
 * 系统配置业务接口
 *
 * @author andy_hulibo@163.com
 * @date 2018/11/24 16:32
 */
public interface CmsConfigMng {

    /**
     * 获得系统配置信息
     *
     * @return 系统配置对象
     * @author andy_hulibo@163.com
     * @date 2018/11/24 16:32
     */
    CmsConfig get();

    /**
     * 获得内容查询缓存时间
     *
     * @return 分钟数
     * @author andy_hulibo@163.com
     * @date 2018/11/24 16:32
     */
    Integer getContentFreshMinute();

    /**
     * 更新计数器拷贝时间
     *
     * @param d 时间
     * @author andy_hulibo@163.com
     * @date 2018/11/24 16:36
     */
    void updateCountCopyTime(Date d);

    /**
     * 更新计数器清除时间
     *
     * @param d 时间
     * @author andy_hulibo@163.com
     * @date 2018/11/24 16:39
     */
    void updateCountClearTime(Date d);

    /**
     * 更新流量统计清除时间
     *
     * @param d 时间
     * @author andy_hulibo@163.com
     * @date 2018/11/24 16:39
     */
    void updateFlowClearTime(Date d);

    /**
     * 更新栏目发布内容计数器清除时间
     *
     * @param d 时间
     * @author andy_hulibo@163.com
     * @date 2018/11/24 16:40
     */
    void updateChannelCountClearTime(Date d);

    /**
     * 更新系统配置信息
     *
     * @param bean 系统配置
     * @return 系统配置
     * @author andy_hulibo@163.com
     * @date 2018/11/24 16:40
     */
    CmsConfig update(CmsConfig bean);

    /**
     * 更新水印配置信息
     *
     * @param mark 水印配置
     * @return 水印配置
     * @author andy_hulibo@163.com
     * @date 2018/11/24 16:41
     */
    MarkConfig updateMarkConfig(MarkConfig mark);

    /**
     * 更新会员配置
     *
     * @param memberConfig 会员配置
     * @author andy_hulibo@163.com
     * @date 2018/11/24 16:44
     */
    void updateMemberConfig(MemberConfig memberConfig);

    /**
     * 更新系统配置属性
     *
     * @param configAttr 系统配置属性
     * @author andy_hulibo@163.com
     * @date 2018/11/24 16:46
     */
    void updateConfigAttr(CmsConfigAttr configAttr);

    /**
     * 更新单点登录配置属性
     *
     * @param ssoAttr 属性
     * @author andy_hulibo@163.com
     * @date 2018/11/24 16:46
     */
    void updateSsoAttr(Map<String, String> ssoAttr);

    /**
     * 更新大赏配置属性
     *
     * @param fixAttr 属性
     * @author andy_hulibo@163.com
     * @date 2018/11/24 16:47
     */
    void updateRewardFixAttr(Map<String, String> fixAttr);
}