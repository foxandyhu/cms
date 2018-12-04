package com.bfly.cms.user.service.impl;

import com.bfly.cms.user.dao.CmsRoleDao;
import com.bfly.cms.user.entity.CmsAdmin;
import com.bfly.cms.user.entity.CmsRole;
import com.bfly.cms.user.service.CmsAdminService;
import com.bfly.cms.user.service.CmsRoleMng;
import com.bfly.common.hibernate4.Updater;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

/**
 *
 * @author andy_hulibo@163.com
 * @date 2018/12/3 22:13
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class CmsRoleMngImpl implements CmsRoleMng {

    @Override
    @Transactional(readOnly = true,rollbackFor = Exception.class)
    public List<CmsRole> getList(Integer level) {
        return dao.getList(level);
    }

    @Override
    @Transactional(readOnly = true,rollbackFor = Exception.class)
    public CmsRole findById(Integer id) {
        return dao.findById(id);
    }

    @Override
    public CmsRole save(CmsRole bean, Set<String> perms) {
        bean.setPerms(perms);
        dao.save(bean);
        return bean;
    }

    @Override
    public CmsRole update(CmsRole bean, Set<String> perms) {
        Updater<CmsRole> updater = new Updater<>(bean);
        bean = dao.updateByUpdater(updater);
        bean.setPerms(perms);
        return bean;
    }

    @Override
    public CmsRole deleteById(Integer id) {
        return dao.deleteById(id);
    }

    @Override
    public CmsRole[] deleteByIds(Integer[] ids) {
        CmsRole[] beans = new CmsRole[ids.length];
        for (int i = 0, len = ids.length; i < len; i++) {
            beans[i] = deleteById(ids[i]);
        }
        return beans;
    }

    @Override
    public void deleteMembers(CmsRole role, Integer[] userIds) {
        Updater<CmsRole> updater = new Updater<>(role);
        role = dao.updateByUpdater(updater);
        if (userIds != null) {
            CmsAdmin admin;
            for (Integer uid : userIds) {
                admin = adminService.findById(uid);
                role.delFromUsers(admin);
                admin.getRoles().remove(role);
            }
        }
    }

    @Autowired
    private CmsRoleDao dao;
    @Autowired
    private CmsAdminService adminService;
}