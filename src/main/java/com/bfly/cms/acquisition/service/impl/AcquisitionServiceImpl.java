package com.bfly.cms.acquisition.service.impl;

import com.bfly.cms.acquisition.entity.Acquisition;
import com.bfly.cms.acquisition.service.IAcquisitionService;
import com.bfly.core.base.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author andy_hulibo@163.com
 * @date 2018/12/12 16:30
 */
@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class AcquisitionServiceImpl extends BaseServiceImpl<Acquisition, Integer> implements IAcquisitionService {
}
