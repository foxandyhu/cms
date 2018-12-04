package com.bfly.cms.comment.dao;

import com.bfly.cms.comment.entity.CmsGuestbookCtg;
import com.bfly.common.hibernate4.Updater;

import java.util.List;

/**
 * @author andy_hulibo@163.com
 * @date 2018/12/4 14:48
 */
public interface CmsGuestbookCtgDao {

    List<CmsGuestbookCtg> getList();

    CmsGuestbookCtg findById(Integer id);

    CmsGuestbookCtg save(CmsGuestbookCtg bean);

    CmsGuestbookCtg updateByUpdater(Updater<CmsGuestbookCtg> updater);

    CmsGuestbookCtg deleteById(Integer id);
}