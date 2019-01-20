package com.bfly.cms.logs.service.impl;

import com.bfly.cms.logs.entity.SysLog;
import com.bfly.cms.logs.service.ISysLogService;
import com.bfly.core.base.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author andy_hulibo@163.com
 * @date 2018/12/10 16:34
 */
@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class SysLogServiceImpl extends BaseServiceImpl<SysLog, Integer> implements ISysLogService {

}
