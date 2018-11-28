package com.bfly.cms.acquisition.dao;

import com.bfly.cms.acquisition.entity.CmsAcquisitionReplace;
import com.bfly.common.hibernate4.Updater;

import java.util.List;

/**
 * 采集内容关键词替换接口
 *
 * @author Administrator
 */
public interface CmsAcquisitionReplaceDao {

    CmsAcquisitionReplace save(CmsAcquisitionReplace bean);

    CmsAcquisitionReplace updateByUpdater(Updater<CmsAcquisitionReplace> updater);

    List<CmsAcquisitionReplace> getList(Integer acquisitionId);
}
