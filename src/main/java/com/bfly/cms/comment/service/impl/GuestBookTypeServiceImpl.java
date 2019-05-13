package com.bfly.cms.comment.service.impl;

import com.bfly.cms.comment.entity.GuestBookType;
import com.bfly.cms.comment.service.IGuestBookTypeService;
import com.bfly.core.base.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author andy_hulibo@163.com
 * @date 2018/12/12 11:37
 */
@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class GuestBookTypeServiceImpl extends BaseServiceImpl<GuestBookType, Integer> implements IGuestBookTypeService {

}
