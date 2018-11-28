package com.bfly.cms.user.dao;

import com.bfly.cms.user.entity.CmsRole;
import com.bfly.common.hibernate4.Updater;

import java.util.List;

public interface CmsRoleDao {
    List<CmsRole> getList(Integer level);

    CmsRole findById(Integer id);

    CmsRole save(CmsRole bean);

    CmsRole updateByUpdater(Updater<CmsRole> updater);

    CmsRole deleteById(Integer id);
}