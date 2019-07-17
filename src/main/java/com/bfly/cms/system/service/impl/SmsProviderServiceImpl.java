package com.bfly.cms.system.service.impl;

import com.bfly.cms.system.entity.SmsProvider;
import com.bfly.cms.system.service.ISmsProviderService;
import com.bfly.core.base.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author andy_hulibo@163.com
 * @date 2018/12/18 11:31
 */
@Service
@Transactional(propagation= Propagation.SUPPORTS, rollbackFor = Exception.class)
public class SmsProviderServiceImpl extends BaseServiceImpl<SmsProvider, Integer> implements ISmsProviderService {
}
