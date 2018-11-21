package com.bfly.cms.dao.assist;

import com.bfly.cms.entity.assist.CmsKeyword;
import com.bfly.common.hibernate4.Updater;

import java.util.List;

public interface CmsKeywordDao {
    List<CmsKeyword> getList(Integer siteId, boolean onlyEnabled,
                             boolean cacheable);

    List<CmsKeyword> getListGlobal(boolean onlyEnabled, boolean cacheable);

    CmsKeyword findById(Integer id);

    CmsKeyword save(CmsKeyword bean);

    CmsKeyword updateByUpdater(Updater<CmsKeyword> updater);

    CmsKeyword deleteById(Integer id);
}