package com.bfly.cms.content.service.impl;

import com.bfly.cms.content.entity.Content;
import com.bfly.cms.content.service.IContentService;
import com.bfly.core.base.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author andy_hulibo@163.com
 * @date 2019/8/1 13:45
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, rollbackFor = Exception.class)
public class ContentServiceImpl extends BaseServiceImpl<Content, Integer> implements IContentService {
}
