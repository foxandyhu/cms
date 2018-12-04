package com.bfly.cms.ad.dao;

import com.bfly.cms.ad.entity.CmsAdvertisingSpace;
import com.bfly.common.hibernate4.Updater;

import java.util.List;

/**
 * @author andy_hulibo@163.com
 * @date 2018/12/4 11:24
 */
public interface CmsAdvertisingSpaceDao {

    List<CmsAdvertisingSpace> getList();

    CmsAdvertisingSpace findById(Integer id);

    CmsAdvertisingSpace save(CmsAdvertisingSpace bean);

    CmsAdvertisingSpace updateByUpdater(Updater<CmsAdvertisingSpace> updater);

    CmsAdvertisingSpace deleteById(Integer id);
}