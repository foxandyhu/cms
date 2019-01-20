package com.bfly.cms.channel.service.impl;

import com.bfly.cms.channel.entity.ModelItem;
import com.bfly.cms.channel.service.IModelItemService;
import com.bfly.core.base.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author andy_hulibo@163.com
 * @date 2018/12/19 10:31
 */
@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class ModelItemServiceImpl extends BaseServiceImpl<ModelItem, Integer> implements IModelItemService {
}
