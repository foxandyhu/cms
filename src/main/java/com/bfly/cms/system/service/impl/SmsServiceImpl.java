package com.bfly.cms.system.service.impl;

import com.bfly.cms.system.entity.Sms;
import com.bfly.cms.system.service.ISmsService;
import com.bfly.core.base.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author andy_hulibo@163.com
 * @date 2018/12/18 11:31
 */
@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class SmsServiceImpl extends BaseServiceImpl<Sms, Integer> implements ISmsService {
}
