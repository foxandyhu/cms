package com.bfly.cms.friendlink.dao;

import java.util.List;

import com.bfly.cms.friendlink.entity.CmsFriendlinkCtg;
import com.bfly.common.hibernate4.Updater;

public interface CmsFriendlinkCtgDao {
    List<CmsFriendlinkCtg> getList(Integer siteId);

    CmsFriendlinkCtg findById(Integer id);

    CmsFriendlinkCtg save(CmsFriendlinkCtg bean);

    CmsFriendlinkCtg updateByUpdater(Updater<CmsFriendlinkCtg> updater);

    CmsFriendlinkCtg deleteById(Integer id);
}