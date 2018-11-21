package com.bfly.core.manager.impl;

import com.bfly.cms.manager.assist.CmsResourceMng;
import com.bfly.common.hibernate4.Updater;
import com.bfly.core.dao.CmsSiteDao;
import com.bfly.core.entity.CmsSite;
import com.bfly.core.entity.CmsSiteCompany;
import com.bfly.core.entity.CmsUser;
import com.bfly.core.manager.*;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class CmsSiteMngImpl implements CmsSiteMng {
    private static final Logger log = LoggerFactory
            .getLogger(CmsSiteMngImpl.class);

    @Override
    @Transactional(readOnly = true)
    public List<CmsSite> getList() {
        return dao.getList(false);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CmsSite> getListFromCache() {
        return dao.getList(true);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean hasRepeatByProperty(String property) {
        return (getList().size() - dao.getCountByProperty(property)) > 0 ? true : false;
    }

    @Override
    @Transactional(readOnly = true)
    public CmsSite findByDomain(String domain) {
        return dao.findByDomain(domain);
    }

    @Override
    @Transactional(readOnly = true)
    public CmsSite findById(Integer id) {
        CmsSite entity = dao.findById(id);
        return entity;
    }

    @Override
    public CmsSite update(CmsSite site) {
        Updater<CmsSite> updater = new Updater<CmsSite>(site);
        site = dao.updateByUpdater(updater);
        return site;
    }

    @Override
    public CmsSite save(CmsSite currSite, CmsUser currUser, CmsSite bean,
                        Integer uploadFtpId, Integer syncPageFtpId) throws IOException {
        if (uploadFtpId != null) {
            bean.setUploadFtp(ftpMng.findById(uploadFtpId));
        }
        if (syncPageFtpId != null) {
            bean.setSyncPageFtp(ftpMng.findById(syncPageFtpId));
        }
        bean.getConfig().getId();
        bean.init();
        dao.save(bean);
        // 复制本站模板
        cmsResourceMng.copyTplAndRes(currSite, bean);
        // 处理管理员
        cmsUserMng.addSiteToUser(currUser, bean, bean.getFinalStep());
        //保存站点相关公司信息
        CmsSiteCompany company = new CmsSiteCompany();
        company.setName(bean.getName());
        siteCompanyMng.save(bean, company);
        return bean;
    }

    @Override
    public CmsSite update(CmsSite bean, Integer uploadFtpId, Integer syncPageFtpId, Integer ossId) {
        CmsSite entity = findById(bean.getId());
        if (uploadFtpId != null) {
            entity.setUploadFtp(ftpMng.findById(uploadFtpId));
        } else {
            entity.setUploadFtp(null);
        }
        if (syncPageFtpId != null) {
            entity.setSyncPageFtp(ftpMng.findById(syncPageFtpId));
        } else {
            entity.setSyncPageFtp(null);
        }
        if (ossId != null) {
            entity.setUploadOss(ossMng.findById(ossId));
        } else {
            entity.setUploadOss(null);
        }
        Updater<CmsSite> updater = new Updater<CmsSite>(bean);
        entity = dao.updateByUpdater(updater);
        return entity;
    }

    @Override
    public void updateTplSolution(Integer siteId, String solution, String mobileSol) {
        CmsSite site = findById(siteId);
        if (StringUtils.isNotBlank(solution)) {
            site.setTplSolution(solution);
        }
        if (StringUtils.isNotBlank(mobileSol)) {
            site.setTplMobileSolution(mobileSol);
        }
    }

    @Override
    public void updateAttr(Integer siteId, Map<String, String> attr) {
        CmsSite site = findById(siteId);
        site.getAttr().putAll(attr);
    }

    @Override
    public void updateAttr(Integer siteId, Map<String, String>... attrs) {
        CmsSite site = findById(siteId);
        for (Map<String, String> m : attrs) {
            site.getAttr().putAll(m);
        }
    }

    @Override
    public CmsSite deleteById(Integer id) {
        // 删除用户、站点关联
        cmsUserSiteMng.deleteBySiteId(id);
        CmsSite bean = dao.findById(id);
        dao.deleteById(id);
        log.info("delete site " + id);
        // 删除模板
        /*
		try {
			cmsResourceMng.delTplAndRes(bean);
		} catch (IOException e) {
			log.error("delete Template and Resource fail!", e);
		}
		*/
        return bean;
    }

    @Override
    public CmsSite[] deleteByIds(Integer[] ids) {
        CmsSite[] beans = new CmsSite[ids.length];
        for (int i = 0, len = ids.length; i < len; i++) {
            beans[i] = deleteById(ids[i]);
        }
        return beans;
    }

    @Autowired
    private CmsUserMng cmsUserMng;
    @Autowired
    private CmsUserSiteMng cmsUserSiteMng;
    @Autowired
    private CmsResourceMng cmsResourceMng;
    @Autowired
    private FtpMng ftpMng;
    @Autowired
    private CmsSiteDao dao;
    @Autowired
    private CmsOssMng ossMng;
    @Autowired
    private CmsSiteCompanyMng siteCompanyMng;
}