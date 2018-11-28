package com.bfly.cms.message.service;

import com.bfly.cms.message.entity.CmsMessage;
import com.bfly.common.page.Pagination;

import java.util.Date;
import java.util.List;

/**
 * 站内消息业务接口
 *
 * @author andy_hulibo@163.com
 * @date 2018/11/25 18:46
 */
public interface CmsMessageMng {

    /**
     * 获得站内消息
     *
     * @param siteId         站点
     * @param title          标题
     * @param pageSize       每页数据
     * @param pageNo         页码
     * @param cacheable      是否缓存
     * @param receiverUserId 接收者
     * @param sendBeginTime  发送开始时间
     * @param sendEndTime    发送结束时间
     * @param sendUserId     发送者
     * @param status         状态
     * @param box            信箱
     * @return 分页对象
     * @author andy_hulibo@163.com
     * @date 2018/11/25 18:47
     */
    Pagination getPage(Integer siteId, Integer sendUserId,
                       Integer receiverUserId, String title, Date sendBeginTime,
                       Date sendEndTime, Boolean status, Integer box, Boolean cacheable,
                       int pageNo, int pageSize);

    /**
     * 获得站内消息
     *
     * @param siteId         站点
     * @param title          标题
     * @param cacheable      是否缓存
     * @param receiverUserId 接收者
     * @param sendBeginTime  发送开始时间
     * @param sendEndTime    发送结束时间
     * @param sendUserId     发送者
     * @param status         状态
     * @param box            信箱
     * @param count          数量
     * @param first          索引
     * @return 消息集合
     * @author andy_hulibo@163.com
     * @date 2018/11/25 18:47
     */
    List<CmsMessage> getList(Integer siteId, Integer sendUserId,
                             Integer receiverUserId, String title, Date sendBeginTime,
                             Date sendEndTime, Boolean status, Integer box, Boolean cacheable,
                             Integer first, Integer count);

    /**
     * 根据消息ID获得消息信息
     *
     * @param id 消息ID
     * @return 消息对象
     * @author andy_hulibo@163.com
     * @date 2018/11/25 18:49
     */
    CmsMessage findById(Integer id);

    /**
     * 新增站内消息
     *
     * @param bean 消息对象
     * @return 消息对象
     * @author andy_hulibo@163.com
     * @date 2018/11/25 18:50
     */
    CmsMessage save(CmsMessage bean);

    /**
     * 修改站内消息
     *
     * @param bean 消息对象
     * @return 消息对象
     * @author andy_hulibo@163.com
     * @date 2018/11/25 18:50
     */
    CmsMessage update(CmsMessage bean);

    /**
     * 删除站内消息
     *
     * @param id 消息ID
     * @return 消息对象
     * @author andy_hulibo@163.com
     * @date 2018/11/25 18:50
     */
    CmsMessage deleteById(Integer id);

    /**
     * 删除站内消息
     *
     * @param ids 消息ID集合
     * @return 消息对象集合
     * @author andy_hulibo@163.com
     * @date 2018/11/25 18:50
     */
    CmsMessage[] deleteByIds(Integer[] ids);
}