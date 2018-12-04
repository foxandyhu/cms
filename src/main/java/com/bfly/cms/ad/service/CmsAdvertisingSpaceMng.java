package com.bfly.cms.ad.service;

import java.util.List;

import com.bfly.cms.ad.entity.CmsAdvertisingSpace;

/**
 *
 * @author andy_hulibo@163.com
 * @date 2018/12/4 11:24
 */
public interface CmsAdvertisingSpaceMng {

	 List<CmsAdvertisingSpace> getList();

	 CmsAdvertisingSpace findById(Integer id);

	 CmsAdvertisingSpace save(CmsAdvertisingSpace bean);

	 CmsAdvertisingSpace update(CmsAdvertisingSpace bean);

	 CmsAdvertisingSpace deleteById(Integer id);

	 CmsAdvertisingSpace[] deleteByIds(Integer[] ids);
}