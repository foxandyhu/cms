package com.bfly.cms.ad.service.impl;

import com.bfly.cms.ad.entity.AdvertisingSpace;
import com.bfly.cms.ad.service.IAdvertisingSpaceService;
import com.bfly.core.base.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author andy_hulibo@163.com
 * @date 2018/12/11 17:09
 */
@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class AdvertisingSpaceServiceImpl extends BaseServiceImpl<AdvertisingSpace, Integer> implements IAdvertisingSpaceService {
}
