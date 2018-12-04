package com.bfly.cms.user.service.impl;

import com.bfly.cms.user.dao.CmsAdminDao;
import com.bfly.cms.user.entity.CmsAdmin;
import com.bfly.cms.user.service.CmsAdminService;
import com.bfly.cms.user.service.PwdEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author andy_hulibo@163.com
 * @date 2018/12/3 16:05
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class CmsAdminServiceImpl implements CmsAdminService {

    @Autowired
    private CmsAdminDao adminDao;
    @Autowired
    private PwdEncoder pwdEncoder;

    @Override
    @Transactional(readOnly = true, rollbackFor = Exception.class)
    public CmsAdmin findByUsername(String username) {
        return adminDao.findByUsername(username);
    }

    @Override
    @Transactional(readOnly = true, rollbackFor = Exception.class)
    public CmsAdmin findById(Integer id) {
        return adminDao.findById(id);
    }

    @Override
    @Transactional(readOnly = true, rollbackFor = Exception.class)
    public boolean isPasswordValid(Integer id, String password) {
        CmsAdmin user = findById(id);
        return pwdEncoder.isPasswordValid(user.getPassword(), password);
    }

    @Override
    public void updateLoginInfo(CmsAdmin admin) {
        adminDao.updateLoginInfo(admin);
    }
}