package com.bfly.cms.sms.dao;

import com.bfly.cms.sms.entity.CmsSmsRecord;
import com.bfly.common.hibernate4.Updater;
import com.bfly.common.page.Pagination;

import java.util.Date;
import java.util.List;

/**
 * 短信记录业务接口
 *
 * @author andy_hulibo@163.com
 * @date 2018/11/25 16:42
 */
public interface CmsSmsRecordDao {

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
     * @param updater 短信记录
     * @return 短信记录
     * @author andy_hulibo@163.com
     * @date 2018/11/25 16:39
     */
    CmsSmsRecord updateByUpdater(Updater<CmsSmsRecord> updater);

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
     * 根据手机号码查询短信记录
     *
     * @param phone     手机号码
     * @param endTime   结束时间
     * @param startTime 开始时间
     * @return 短信记录集合
     * @author andy_hulibo@163.com
     * @date 2018/11/25 16:38
     */
    List<CmsSmsRecord> findByPhone(String phone, Date startTime, Date endTime);
}
