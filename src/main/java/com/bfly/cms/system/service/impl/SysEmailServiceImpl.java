package com.bfly.cms.system.service.impl;

import com.bfly.cms.system.entity.SysEmail;
import com.bfly.cms.system.service.ISysEmailService;
import com.bfly.core.base.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author andy_hulibo@163.com
 * @date 2018/12/14 11:57
 */
@Service
@Transactional(propagation= Propagation.SUPPORTS, rollbackFor = Exception.class)
public class SysEmailServiceImpl extends BaseServiceImpl<SysEmail, Integer> implements ISysEmailService {
}
