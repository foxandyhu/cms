package com.bfly.cms.content.service.impl;

import com.bfly.cms.content.entity.ContentType;
import com.bfly.cms.content.service.IContentTypeService;
import com.bfly.core.base.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author andy_hulibo@163.com
 * @date 2018/12/19 9:52
 */
@Service
@Transactional(propagation= Propagation.SUPPORTS, rollbackFor = Exception.class)
public class ContentTypeServiceImpl extends BaseServiceImpl<ContentType, Integer> implements IContentTypeService {
}
