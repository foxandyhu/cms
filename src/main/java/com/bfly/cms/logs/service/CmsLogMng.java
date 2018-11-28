package com.bfly.cms.logs.service;

import com.bfly.cms.logs.entity.CmsLog;
import com.bfly.common.page.Pagination;
import com.bfly.cms.user.entity.CmsUser;

import javax.servlet.http.HttpServletRequest;

/**
 * 操纵日志业务接口
 *
 * @author andy_hulibo@163.com
 * @date 2018/11/25 18:28
 */
public interface CmsLogMng {

    /**
     * 获取操作日志
     *
     * @param username 用户名
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
    Pagination getPage(Integer category, Integer siteId,
                       String username, String title, String ip, int pageNo, int pageSize);

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
     * 记录日志
     *
     * @param title   标题
     * @param request HttpServletRequest
     * @param content 内容
     * @return 操作日志
     * @author andy_hulibo@163.com
     * @date 2018/11/25 18:29
     */
    CmsLog operating(HttpServletRequest request, String title,
                     String content);

    /**
     * 记录登录失败日志
     *
     * @param content 内容
     * @param request HttpServletRequest
     * @return 操作日志
     * @author andy_hulibo@163.com
     * @date 2018/11/25 18:30
     */
    CmsLog loginFailure(HttpServletRequest request, String content);

    /**
     * 记录登录成功日志
     *
     * @param user    操作者
     * @param request HttpServletRequest
     * @return 操作日志
     * @author andy_hulibo@163.com
     * @date 2018/11/25 18:30
     */
    CmsLog loginSuccess(HttpServletRequest request, CmsUser user);

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
     * @param ids 日志ID
     * @return 操作日志
     * @author andy_hulibo@163.com
     * @date 2018/11/25 18:31
     */
    CmsLog[] deleteByIds(Integer[] ids);

    /**
     * 批量删除操作日志
     *
     * @param category 日志类型
     * @param siteId   站点
     * @param days     天数
     * @return 操作日志影响条数
     * @author andy_hulibo@163.com
     * @date 2018/11/25 18:31
     */
    int deleteBatch(Integer category, Integer siteId, Integer days);
}