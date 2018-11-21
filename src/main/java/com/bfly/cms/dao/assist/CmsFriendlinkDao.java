package com.bfly.cms.dao.assist;

import com.bfly.cms.entity.assist.CmsFriendlink;
import com.bfly.common.hibernate4.Updater;

import java.util.List;

public interface CmsFriendlinkDao {
    List<CmsFriendlink> getList(Integer siteId, Integer ctgId,
                                Boolean enabled);

    int countByCtgId(Integer ctgId);

    CmsFriendlink findById(Integer id);

    CmsFriendlink save(CmsFriendlink bean);

    CmsFriendlink updateByUpdater(Updater<CmsFriendlink> updater);

    CmsFriendlink deleteById(Integer id);
}