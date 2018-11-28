package com.bfly.cms.friendlink.service;

import com.bfly.cms.friendlink.entity.CmsFriendlinkCtg;

import java.util.List;

public interface CmsFriendlinkCtgMng {
    List<CmsFriendlinkCtg> getList(Integer siteId);

    CmsFriendlinkCtg findById(Integer id);

    CmsFriendlinkCtg save(CmsFriendlinkCtg bean);

    CmsFriendlinkCtg update(CmsFriendlinkCtg bean);

    void updateFriendlinkCtgs(Integer[] ids, String[] names,
                              Integer[] priorities);

    CmsFriendlinkCtg deleteById(Integer id);

    CmsFriendlinkCtg[] deleteByIds(Integer[] ids);
}