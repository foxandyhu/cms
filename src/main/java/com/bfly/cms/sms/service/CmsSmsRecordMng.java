package com.bfly.cms.sms.service;

import com.bfly.cms.siteconfig.entity.CmsSite;
import com.bfly.cms.sms.entity.CmsSms;
import com.bfly.cms.sms.entity.CmsSmsRecord;
import com.bfly.common.page.Pagination;
import com.bfly.cms.user.entity.CmsUser;

import java.util.Date;
import java.util.List;

/**
 * 短信记录业务接口
 *
 * @author andy_hulibo@163.com
 * @date 2018/11/25 16:42
 */
public interface CmsSmsRecordMng {

    /**
     * 获得短信记录集合
     *
     * @param smsId         短信id
     * @param phone         手机号码
     * @param pageSize      每页数据
     * @param pageNo        页码
     * @param username      用户名
     * @param drawTimeBegin 开始时间
     * @param drawTimeEnd   结束时间
     * @param validateType  类型
     * @return 短信记录集合
     * @author andy_hulibo@163.com
     * @date 2018/11/25 16:40
     */
    Pagination getPage(Byte smsId, int pageNo, int pageSize, String phone, Integer validateType, String username, Date drawTimeBegin, Date drawTimeEnd);

    /**
     * 获得短信记录集合
     *
     * @param smsId 短信id
     * @return 短信记录集合
     * @author andy_hulibo@163.com
     * @date 2018/11/25 16:40
     */
    List<CmsSmsRecord> getList(Integer smsId);

    /**
     * 根据ID获得短信记录
     *
     * @param id 短信记录ID
     * @return 短信记录
     * @author andy_hulibo@163.com
     * @date 2018/11/25 16:40
     */
    CmsSmsRecord findById(Integer id);

    /**
     * 新增短信记录
     *
     * @param bean 短信记录
     * @return 短信记录
     * @author andy_hulibo@163.com
     * @date 2018/11/25 16:39
     */
    CmsSmsRecord save(CmsSmsRecord bean);

    /**
     * 更新短信记录
     *
     * @param bean 短信记录
     * @return 短信记录
     * @author andy_hulibo@163.com
     * @date 2018/11/25 16:39
     */
    CmsSmsRecord updateByUpdater(CmsSmsRecord bean);


    /**
     * 根据手机号码查询短信记录
     *
     * @param phone 手机号码
     * @return 短信记录集合
     * @author andy_hulibo@163.com
     * @date 2018/11/25 16:38
     */
    List<CmsSmsRecord> findByPhone(String phone);

    /**
     * 删除短信记录
     *
     * @param id 短信记录ID
     * @return 短信记录
     * @author andy_hulibo@163.com
     * @date 2018/11/25 16:38
     */
    CmsSmsRecord deleteById(Integer id);

    /**
     * 批量删除短信记录
     *
     * @param idArr 短信记录ID集合
     * @return 短信记录
     * @author andy_hulibo@163.com
     * @date 2018/11/25 16:38
     */
    CmsSmsRecord[] deleteByIds(Integer[] idArr);

    /**
     * 新增短信记录
     *
     * @param mobilePhone 手机号码
     * @param site        站点
     * @param sms         短信
     * @param smsSendType 发送类型
     * @param user        用户
     * @return 短信记录
     * @author andy_hulibo@163.com
     * @date 2018/11/25 16:37
     */
    CmsSmsRecord save(CmsSms sms, String mobilePhone, Integer smsSendType, CmsSite site, CmsUser user);
}
