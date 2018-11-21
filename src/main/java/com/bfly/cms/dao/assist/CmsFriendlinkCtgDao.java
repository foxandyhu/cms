package com.bfly.cms.dao.assist;

import java.util.List;

import com.bfly.cms.entity.assist.CmsFriendlinkCtg;
import com.bfly.common.hibernate4.Updater;

public interface CmsFriendlinkCtgDao {
    List<CmsFriendlinkCtg> getList(Integer siteId);

    int countBySiteId(Integer siteId);

    CmsFriendlinkCtg findById(Integer id);

    CmsFriendlinkCtg save(CmsFriendlinkCtg bean);

    CmsFriendlinkCtg updateByUpdater(Updater<CmsFriendlinkCtg> updater);

    CmsFriendlinkCtg deleteById(Integer id);
}