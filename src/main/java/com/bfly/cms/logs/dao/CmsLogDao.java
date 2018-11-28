package com.bfly.cms.logs.dao;

import com.bfly.cms.logs.entity.CmsLog;
import com.bfly.common.page.Pagination;

import java.util.Date;

/**
 * 操纵日志数据接口
 *
 * @author andy_hulibo@163.com
 * @date 2018/11/25 18:36
 */
public interface CmsLogDao {

    /**
     * 获取操作日志
     *
     * @param userId   用户ID
     * @param pageNo   页码
     * @param pageSize 每页数据
     * @param ip       操作IP
     * @param category 操作类别
     * @param siteId   站点
     * @param title    标题
     * @return 分页对象
     * @author andy_hulibo@163.com
     * @date 2018/11/25 18:28
     */
    Pagination getPage(Integer category, Integer siteId, Integer userId,
                       String title, String ip, int pageNo, int pageSize);

    /**
     * 操作日志详情
     *
     * @param id 日志ID
     * @return 操作日志
     * @author andy_hulibo@163.com
     * @date 2018/11/25 18:29
     */
    CmsLog findById(Integer id);

    /**
     * 新增操纵日志
     *
     * @param bean 操作日志
     * @return 操作日志
     * @author andy_hulibo@163.com
     * @date 2018/11/25 18:31
     */
    CmsLog save(CmsLog bean);

    /**
     * 删除操作日志
     *
     * @param id 日志ID
     * @return 操作日志
     * @author andy_hulibo@163.com
     * @date 2018/11/25 18:31
     */
    CmsLog deleteById(Integer id);

    /**
     * 批量删除操作日志
     *
     * @param category 日志类型
     * @param siteId   站点
     * @param before   日期
     * @return 操作日志影响条数
     * @author andy_hulibo@163.com
     * @date 2018/11/25 18:31
     */
    int deleteBatch(Integer category, Integer siteId, Date before);
}