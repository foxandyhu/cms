package com.bfly.cms.system.service.impl;

import com.bfly.cms.system.entity.OssInfo;
import com.bfly.cms.system.service.IOssInfoService;
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
public class OssInfoServiceImpl extends BaseServiceImpl<OssInfo, Integer> implements IOssInfoService {
}
