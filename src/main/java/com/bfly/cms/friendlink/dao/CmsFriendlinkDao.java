package com.bfly.cms.friendlink.dao;

import com.bfly.cms.friendlink.entity.CmsFriendlink;
import com.bfly.common.hibernate4.Updater;

import java.util.List;

public interface CmsFriendlinkDao {
    List<CmsFriendlink> getList(Integer siteId, Integer ctgId,
                                Boolean enabled);

    CmsFriendlink findById(Integer id);

    CmsFriendlink save(CmsFriendlink bean);

    CmsFriendlink updateByUpdater(Updater<CmsFriendlink> updater);

    CmsFriendlink deleteById(Integer id);
}