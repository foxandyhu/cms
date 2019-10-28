package com.bfly.cms.job.service.impl;

import com.bfly.cms.job.entity.JobResume;
import com.bfly.cms.job.service.IJobResumeService;
import com.bfly.core.base.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author andy_hulibo@163.com
 * @date 2018/12/12 14:32
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, rollbackFor = Exception.class)
public class JobResumeServiceImpl extends BaseServiceImpl<JobResume, Integer> implements IJobResumeService {
}
