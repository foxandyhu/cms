package com.bfly.cms.ad.service.impl;

import com.bfly.cms.ad.entity.Advertising;
import com.bfly.cms.ad.service.IAdvertisingService;
import com.bfly.core.base.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author andy_hulibo@163.com
 * @date 2018/12/11 17:09
 */
@Service
@Transactional(propagation= Propagation.SUPPORTS, rollbackFor = Exception.class)
public class AdvertisingServiceImpl extends BaseServiceImpl<Advertising, Integer> implements IAdvertisingService {
}
