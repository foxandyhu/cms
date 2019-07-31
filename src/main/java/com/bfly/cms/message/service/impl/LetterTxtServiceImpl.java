package com.bfly.cms.message.service.impl;

import com.bfly.cms.message.entity.LetterTxt;
import com.bfly.cms.message.service.ILetterTxtService;
import com.bfly.core.base.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author andy_hulibo@163.com
 * @date 2019/7/31 16:56
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, rollbackFor = Exception.class)
public class LetterTxtServiceImpl extends BaseServiceImpl<LetterTxt, Integer> implements ILetterTxtService {
}
