package com.bfly.cms.member.service.impl;

import com.bfly.cms.member.entity.Member;
import com.bfly.cms.member.service.IMemberService;
import com.bfly.core.base.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 
 * @author andy_hulibo@163.com
 * @date 2018/12/7 17:39
 */
@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class MemberServiceImpl extends BaseServiceImpl<Member, Integer> implements IMemberService {
}
