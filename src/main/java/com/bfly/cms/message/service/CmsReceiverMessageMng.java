package com.bfly.cms.message.service;

import com.bfly.cms.message.entity.CmsReceiverMessage;
import com.bfly.common.page.Pagination;

import java.util.Date;
import java.util.List;

/**
 * 站内信收信业务接口
 *
 * @author andy_hulibo@163.com
 * @date 2018/11/25 18:54
 */
public interface CmsReceiverMessageMng {

    /**
     * 获得收信集合
     *
     * @param box            信箱
     * @param receiverUserId 接收者ID
     * @param status         状态
     * @param sendUserId     发送者ID
     * @param sendEndTime    发送结束时间
     * @param sendBeginTime  发送开始时间
     * @param title          标题
     * @param siteId         站点
     * @param cacheable      是否缓存
     * @param pageNo         页码
     * @param pageSize       每页数据
     * @return 分页对象
     * @author andy_hulibo@163.com
     * @date 2018/11/25 19:00
     */
    Pagination getPage(Integer siteId, Integer sendUserId,
                       Integer receiverUserId, String title, Date sendBeginTime,
                       Date sendEndTime, Boolean status, Integer box, Boolean cacheable,
                       int pageNo, int pageSize);

    /**
     * 获得收信集合
     *
     * @param first          索引
     * @param box            信箱
     * @param count          数量
     * @param receiverUserId 接收者ID
     * @param status         状态
     * @param sendUserId     发送者ID
     * @param sendEndTime    发送结束时间
     * @param sendBeginTime  发送开始时间
     * @param title          标题
     * @param siteId         站点
     * @param cacheable      是否缓存
     * @return 收信集合
     * @author andy_hulibo@163.com
     * @date 2018/11/25 19:00
     */
    List<CmsReceiverMessage> getList(Integer siteId, Integer sendUserId,
                                     Integer receiverUserId, String title, Date sendBeginTime,
                                     Date sendEndTime, Boolean status, Integer box, Boolean cacheable
            , Integer first, Integer count);

    /**
     * 根据ID和信箱获得收信
     *
     * @param messageId 收信ID
     * @param box       信箱
     * @return 收信
     * @author andy_hulibo@163.com
     * @date 2018/11/25 18:59
     */
    CmsReceiverMessage find(Integer messageId, Integer box);

    /**
     * 根据ID获得收信
     *
     * @param id 收信ID
     * @return 收信
     * @author andy_hulibo@163.com
     * @date 2018/11/25 18:59
     */
    CmsReceiverMessage findById(Integer id);

    /**
     * 新增收信
     *
     * @param bean 收信
     * @return 收信
     * @author andy_hulibo@163.com
     * @date 2018/11/25 18:59
     */
    CmsReceiverMessage save(CmsReceiverMessage bean);

    /**
     * 更新收信
     *
     * @param bean 收信
     * @return 收信
     * @author andy_hulibo@163.com
     * @date 2018/11/25 18:59
     */
    CmsReceiverMessage update(CmsReceiverMessage bean);

    /**
     * 删除站内信收信
     *
     * @param id 收信ID
     * @return 收信
     * @author andy_hulibo@163.com
     * @date 2018/11/25 18:58
     */
    CmsReceiverMessage deleteById(Integer id);

    /**
     * 删除站内信收信
     *
     * @param ids 收信ID集合
     * @return 收信
     * @author andy_hulibo@163.com
     * @date 2018/11/25 18:58
     */
    CmsReceiverMessage[] deleteByIds(Integer[] ids);
}