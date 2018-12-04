package com.bfly.cms.acquisition.service;

import com.bfly.cms.acquisition.entity.CmsAcquisitionTemp;

import java.util.List;

/**
 * @author andy_hulibo@163.com
 * @date 2018/12/4 10:05
 */
public interface CmsAcquisitionTempMng {

    List<CmsAcquisitionTemp> getList();

    CmsAcquisitionTemp findById(Integer id);

    CmsAcquisitionTemp save(CmsAcquisitionTemp bean);

    CmsAcquisitionTemp update(CmsAcquisitionTemp bean);

    CmsAcquisitionTemp deleteById(Integer id);

    CmsAcquisitionTemp[] deleteByIds(Integer[] ids);

    Integer getPercent();

    void clear();

    void clear(String channelUrl);
}