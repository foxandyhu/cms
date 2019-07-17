package com.bfly.cms.system.service;

import com.bfly.cms.system.entity.SmsRecord;
import com.bfly.core.base.service.IBaseService;

/**
 * 短信记录业务接口
 *
 * @author andy_hulibo@163.com
 * @date 2018/12/18 11:29
 */
public interface ISmsRecordService extends IBaseService<SmsRecord, Integer> {

    /**
     * 短信重发
     * @author andy_hulibo@163.com
     * @date 2019/7/17 13:02
     */
    boolean resend(int recordId);
}
