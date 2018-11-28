package com.bfly.cms.siteconfig.service;

import com.bfly.cms.siteconfig.entity.CmsSite;
import com.bfly.cms.user.entity.CmsUser;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * 站点信息业务接口
 *
 * @author andy_hulibo@163.com
 * @date 2018/11/23 10:49
 */
public interface CmsSiteMng {

    /**
     * 获得所有站点信息
     *
     * @return 站点集合
     * @author andy_hulibo@163.com
     * @date 2018/11/23 11:01
     */
    List<CmsSite> getList();

    /**
     * 获得所有缓存站点信息
     *
     * @return 站点集合
     * @author andy_hulibo@163.com
     * @date 2018/11/23 11:01
     */
    List<CmsSite> getListFromCache();

    /**
     * 根据站点域名获得对应的站点
     *
     * @param domain 域名
     * @return 站点对象
     * @author andy_hulibo@163.com
     * @date 2018/11/23 11:13
     */
    CmsSite findByDomain(String domain);

    /**
     * 得到是否有重复的属性值
     *
     * @param property 属性名称
     * @return true 有重复值 false 没有
     * @author andy_hulibo@163.com
     * @date 2018/11/23 11:15
     */
    boolean hasRepeatByProperty(String property);

    /**
     * 根据站点信息ID获得站点信息对象
     *
     * @param id 站点ID
     * @return 站点对象
     * @author andy_hulibo@163.com
     * @date 2018/11/23 11:24
     */
    CmsSite findById(Integer id);

    /**
     * 保存站点信息
     *
     * @param currSite      当前站点对象
     * @param currUser      当前登录用户
     * @param bean          将要保存的站点对象
     * @param uploadFtpId   附件FTP
     * @param syncPageFtpId 静态页同步FTP
     * @return 站点对象
     * @throws IOException IO异常
     * @author andy_hulibo@163.com
     * @date 2018/11/23 11:26
     */
    CmsSite save(CmsSite currSite, CmsUser currUser, CmsSite bean,
                 Integer uploadFtpId, Integer syncPageFtpId) throws IOException;

    /**
     * 更新站点信息
     *
     * @param site 站点对象
     * @return 站点对象
     * @author andy_hulibo@163.com
     * @date 2018/11/23 11:35
     */
    CmsSite update(CmsSite site);

    /**
     * 更新站点信息
     *
     * @param bean          站点信息
     * @param uploadFtpId   附件FTP
     * @param syncPageFtpId 静态页同步FTP
     * @param ossId         云存储
     * @return 站点对象
     * @author andy_hulibo@163.com
     * @date 2018/11/23 11:43
     */
    CmsSite update(CmsSite bean, Integer uploadFtpId, Integer syncPageFtpId, Integer ossId);

    /**
     * 更新站点模板方案
     *
     * @param siteId    站点ID
     * @param solution  PC端模板
     * @param mobileSol 手机端模板
     * @author andy_hulibo@163.com
     * @date 2018/11/23 11:44
     */
    void updateTplSolution(Integer siteId, String solution, String mobileSol);

    /**
     * 更新站点属性
     *
     * @param siteId 站点ID
     * @param attr  更新的属性集合
     * @author andy_hulibo@163.com
     * @date 2018/11/23 11:47
     */
    void updateAttr(Integer siteId, Map<String, String>... attr);

    /**
     * 删除站点信息
     *
     * @param id 站点ID
     * @return 站点对象
     * @author andy_hulibo@163.com
     * @date 2018/11/23 11:47
     */
    CmsSite deleteById(Integer id);

    /**
     * 批量删除站点信息
     *
     * @param ids 站点ID集合
     * @return 站点集合对象
     * @author andy_hulibo@163.com
     * @date 2018/11/23 11:48
     */
    CmsSite[] deleteByIds(Integer[] ids);
}