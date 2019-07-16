package com.bfly.cms.channel.service.impl;

import com.bfly.cms.channel.entity.Model;
import com.bfly.cms.channel.service.IModelService;
import com.bfly.core.base.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author andy_hulibo@163.com
 * @date 2018/12/19 10:31
 */
@Service
@Transactional(propagation= Propagation.SUPPORTS, rollbackFor = Exception.class)
public class ModelServiceImpl extends BaseServiceImpl<Model, Integer> implements IModelService {
}
