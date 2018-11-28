package com.bfly.cms.channel.service.impl;

import com.bfly.cms.acquisition.service.CmsAcquisitionMng;
import com.bfly.cms.channel.entity.Channel;
import com.bfly.cms.channel.service.ChannelCountMng;
import com.bfly.cms.channel.service.ChannelExtMng;
import com.bfly.cms.channel.service.ChannelMng;
import com.bfly.cms.channel.service.ChannelTxtMng;
import com.bfly.cms.content.entity.CmsModel;
import com.bfly.cms.content.service.*;
import com.bfly.cms.channel.dao.ChannelDao;
import com.bfly.cms.channel.entity.ChannelCount;
import com.bfly.cms.channel.entity.ChannelExt;
import com.bfly.cms.channel.entity.ChannelTxt;
import com.bfly.cms.siteconfig.entity.CmsSite;
import com.bfly.cms.siteconfig.service.CmsSiteMng;
import com.bfly.common.hibernate4.Updater;
import com.bfly.common.page.Pagination;
import com.bfly.cms.user.entity.CmsGroup;
import com.bfly.cms.user.entity.CmsUser;
import com.bfly.cms.user.entity.CmsUserSite;
import com.bfly.cms.user.service.CmsGroupMng;
import com.bfly.cms.user.service.CmsUserMng;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author andy_hulibo@163.com
 * @date 2018/11/26 14:25
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class ChannelMngImpl implements ChannelMng {

    @Override
    @Transactional(readOnly = true, rollbackFor = Exception.class)
    public List<Channel> getTopList(Integer siteId, boolean hasContentOnly) {
        return dao.getTopList(siteId, hasContentOnly, false, true);
    }

    @Override
    @Transactional(readOnly = true, rollbackFor = Exception.class)
    public List<Channel> getTopListByRigth(Integer userId, Integer siteId,
                                           boolean hasContentOnly) {
        CmsUser user = cmsUserMng.findById(userId);
        CmsUserSite us = user.getUserSite(siteId);
        if (us.getAllChannel()) {
            return getTopList(siteId, hasContentOnly);
        } else {
            return dao.getTopListByRigth(userId, siteId, hasContentOnly);
        }
    }

    @Override
    @Transactional(readOnly = true, rollbackFor = Exception.class)
    public List<Channel> getTopListForTag(Integer siteId, boolean hasContentOnly) {
        return dao.getTopList(siteId, hasContentOnly, true, true);
    }

    @Override
    @Transactional(readOnly = true, rollbackFor = Exception.class)
    public Pagination getTopPageForTag(Integer siteId, boolean hasContentOnly,
                                       int pageNo, int pageSize) {
        return dao.getTopPage(siteId, hasContentOnly, false, true, pageNo,
                pageSize);
    }

    @Override
    @Transactional(readOnly = true, rollbackFor = Exception.class)
    public List<Channel> getChildList(Integer parentId, boolean hasContentOnly) {
        return dao.getChildList(parentId, hasContentOnly, false, true);
    }

    @Override
    @Transactional(readOnly = true, rollbackFor = Exception.class)
    public List<Channel> getChildListByRight(Integer userId, Integer siteId,
                                             Integer parentId, boolean hasContentOnly) {
        CmsUser user = cmsUserMng.findById(userId);
        CmsUserSite us = user.getUserSite(siteId);
        if (us.getAllChannel()) {
            return getChildList(parentId, hasContentOnly);
        } else {
            return dao.getChildListByRight(userId, parentId, hasContentOnly);
        }
    }

    @Override
    @Transactional(readOnly = true, rollbackFor = Exception.class)
    public List<Channel> getChildListForTag(Integer parentId,
                                            boolean hasContentOnly) {
        return dao.getChildList(parentId, hasContentOnly, true, true);
    }

    @Override
    @Transactional(readOnly = true, rollbackFor = Exception.class)
    public List<Channel> getBottomList(Integer siteId, boolean hasContentOnly) {
        return dao.getBottomList(siteId, hasContentOnly);
    }

    @Override
    @Transactional(readOnly = true, rollbackFor = Exception.class)
    public Pagination getChildPageForTag(Integer parentId,
                                         boolean hasContentOnly, int pageNo, int pageSize) {
        return dao.getChildPage(parentId, hasContentOnly, true, true, pageNo,
                pageSize);
    }

    @Override
    @Transactional(readOnly = true, rollbackFor = Exception.class)
    public Channel findById(Integer id) {
        Channel entity = dao.findById(id);
        return entity;
    }

    @Override
    @Transactional(readOnly = true, rollbackFor = Exception.class)
    public Channel findByPath(String path, Integer siteId) {
        return dao.findByPath(path, siteId, false);
    }

    @Override
    @Transactional(readOnly = true, rollbackFor = Exception.class)
    public Channel findByPathForTag(String path, Integer siteId) {
        return dao.findByPath(path, siteId, true);
    }

    @Override
    public Channel save(Channel bean, ChannelExt ext, ChannelTxt txt,
                        Integer[] viewGroupIds, Integer[] contriGroupIds,
                        Integer[] userIds, Integer siteId, Integer parentId,
                        Integer modelId, Integer[] modelIds,
                        String[] tpls, String[] mtpls, boolean isCopy) {
        if (parentId != null) {
            bean.setParent(findById(parentId));
        } else {
            bean.setParent(null);
        }
        CmsSite site = cmsSiteMng.findById(siteId);
        bean.setSite(site);
        CmsModel model = cmsModelMng.findById(modelId);
        bean.setModel(model);
        bean.setHasContent(model.getHasContent());
        bean.init();
        bean = dao.save(bean);
        channelExtMng.save(ext, bean);
        channelTxtMng.save(txt, bean);
        channelCountMng.save(new ChannelCount(), bean);
        CmsGroup g;
        if (viewGroupIds != null && viewGroupIds.length > 0) {
            for (Integer gid : viewGroupIds) {
                g = cmsGroupMng.findById(gid);
                bean.addToViewGroups(g);
            }
        }
        if (contriGroupIds != null && contriGroupIds.length > 0) {
            for (Integer gid : contriGroupIds) {
                g = cmsGroupMng.findById(gid);
                bean.addToContriGroups(g);
            }
        }
        if (modelIds != null && modelIds.length > 0) {
            for (int i = 0; i < modelIds.length; i++) {
                CmsModel m = cmsModelMng.findById(modelIds[i]);
                bean.addToChannelModels(m, tpls[i], mtpls[i]);
            }
        }
        CmsUser u;
        if (userIds != null && userIds.length > 0) {
            for (Integer uid : userIds) {
                u = cmsUserMng.findById(uid);
                bean.addToUsers(u);
            }
        }
        return bean;
    }

    @Override
    public Channel copy(Integer cid, String solution,
                        String mobileSolution, Integer siteId,
                        Map<String, String> pathMap) {
        Channel c = findById(cid);
        Channel channel = new Channel();
        if (c != null) {
            ChannelExt ext = new ChannelExt();
            ChannelTxt channelTxt = new ChannelTxt();
            channel = (Channel) c.clone();
            ext = (ChannelExt) c.getChannelExt().clone();
            if (c.getChannelTxt() != null) {
                channelTxt = (ChannelTxt) c.getChannelTxt().clone();
            }
            String path = c.getPath();
            //判断栏目路径是否重复，重复则创建新的栏目路径,需要存储对应关系，方便建立子栏目的对应关系结构
            String newpath = getChannelPath(path, siteId);
            pathMap.put(path, newpath);
            channel.setPath(newpath);
            Integer parentId = null;
            //有上层栏目，则新复制的栏目上层需要查找路径同上层栏目路径，新的栏目parent不是当前栏目的父栏目
            //新栏目的路径从pathMap中获取
            if (c.getParent() != null) {
                Channel newParent = findByPath(
                        pathMap.get(c.getParent().getPath()), siteId);
                if (newParent != null) {
                    parentId = newParent.getId();
                }
            }
            String oldSolution = c.getSite().getTplSolution();
            String oldMobileSolution = c.getSite().getTplMobileSolution();
            if (StringUtils.isNotBlank(ext.getTplChannel())
                    && StringUtils.isNotBlank(solution)) {
                ext.setTplChannel(ext.getTplChannel().replace(oldSolution, solution));
            }
            if (StringUtils.isNotBlank(ext.getTplContent())
                    && StringUtils.isNotBlank(solution)) {
                ext.setTplContent(ext.getTplContent().replace(oldSolution, solution));
            }
            if (StringUtils.isNotBlank(ext.getTplMobileChannel())
                    && StringUtils.isNotBlank(mobileSolution)) {
                ext.setTplMobileChannel(ext.getTplMobileChannel().replace(oldMobileSolution, mobileSolution));
            }
            channel.setChannelExt(ext);
            ext.setChannel(channel);
            channelTxt.setChannel(channel);
            CmsSite site = cmsSiteMng.findById(siteId);

            // 加上模板前缀
            String tplPath = site.getTplPath();
            String[] tpls = c.getModelTplStrs();
            String[] mtpls = c.getMobileModelTplStrs();
            if (tpls != null && tpls.length > 0) {
                for (int t = 0; t < tpls.length; t++) {
                    if (!StringUtils.isBlank(tpls[t])) {
                        tpls[t] = tplPath + tpls[t];
                        if (StringUtils.isNotBlank(solution)) {
                            tpls[t] = tpls[t].replace(oldSolution, solution);
                        }
                    }
                }
            }
            if (mtpls != null && mtpls.length > 0) {
                for (int t = 0; t < mtpls.length; t++) {
                    if (!StringUtils.isBlank(mtpls[t])) {
                        mtpls[t] = tplPath + mtpls[t];
                        if (StringUtils.isNotBlank(mobileSolution)) {
                            mtpls[t] = mtpls[t].replace(oldMobileSolution, mobileSolution);
                        }
                    }
                }
            }
            channel = save(channel, ext, channelTxt,
                    c.getViewGroupIds(), c.getContriGroupIds(),
                    c.getUserIds(), siteId, parentId, c.getModel().getId(),
                    c.getModelIntIds(), tpls, mtpls, true);
        }
        return channel;
    }

    @Override
    public Channel update(Channel bean, ChannelExt ext, ChannelTxt txt,
                          Integer[] viewGroupIds, Integer[] contriGroupIds,
                          Integer[] userIds, Integer parentId, Map<String, String> attr, Integer modelId,
                          Integer[] modelIds, String[] tpls, String[] mtpls) {
        // 更新主表
        Updater<Channel> updater = new Updater<>(bean);
        bean = dao.updateByUpdater(updater);
        // 更新父栏目
        Channel parent;
        if (parentId != null) {
            parent = findById(parentId);
        } else {
            parent = null;
        }
        if (modelId != null) {
            CmsModel model = cmsModelMng.findById(modelId);
            bean.setModel(model);
            bean.setHasContent(model.getHasContent());
        }
        bean.setParent(parent);
        // 更新扩展表
        channelExtMng.update(ext);
        // 更新文本表
        channelTxtMng.update(txt, bean);
        // 更新属性表
        Map<String, String> attrOrig = bean.getAttr();
        attrOrig.clear();
        attrOrig.putAll(attr);
        // 更新浏览会员组关联
        for (CmsGroup g : bean.getViewGroups()) {
            g.getViewChannels().remove(bean);
        }
        bean.getViewGroups().clear();
        if (viewGroupIds != null && viewGroupIds.length > 0) {
            CmsGroup g;
            for (Integer gid : viewGroupIds) {
                g = cmsGroupMng.findById(gid);
                bean.addToViewGroups(g);
            }
        }
        // 更新投稿会员组关联
        for (CmsGroup g : bean.getContriGroups()) {
            g.getContriChannels().remove(bean);
        }
        bean.getContriGroups().clear();
        if (contriGroupIds != null && contriGroupIds.length > 0) {
            CmsGroup g;
            for (Integer gid : contriGroupIds) {
                g = cmsGroupMng.findById(gid);
                bean.addToContriGroups(g);
            }
        }
        bean.getChannelModels().clear();
        if (modelIds != null && modelIds.length > 0) {
            for (int i = 0; i < modelIds.length; i++) {
                CmsModel m = cmsModelMng.findById(modelIds[i]);
                bean.addToChannelModels(m, tpls[i], mtpls[i]);
            }
        }
        // 更新管理员关联
        for (CmsUser u : bean.getUsers()) {
            u.getChannels().remove(bean);
        }
        bean.getUsers().clear();
        if (userIds != null && userIds.length > 0) {
            CmsUser u;
            for (Integer uid : userIds) {
                u = cmsUserMng.findById(uid);
                bean.addToUsers(u);
            }
        }
        return bean;
    }

    @Override
    public Channel deleteById(Integer id) {
        Channel entity = dao.findById(id);
        for (CmsGroup group : entity.getViewGroups()) {
            group.getViewChannels().remove(entity);
        }
        for (CmsGroup group : entity.getContriGroups()) {
            group.getContriChannels().remove(entity);
        }
        entity = dao.deleteById(id);
        return entity;
    }

    @Override
    public Channel[] deleteByIds(Integer[] ids) {
        Channel[] beans = new Channel[ids.length];
        for (int i = 0, len = ids.length; i < len; i++) {
            beans[i] = deleteById(ids[i]);
        }
        return beans;
    }

    @Override
    public String checkDelete(Integer id) {
        String msg = null;
        for (ChannelDeleteChecker checker : deleteCheckerList) {
            msg = checker.checkForChannelDelete(id);
            if (msg != null) {
                return msg;
            }
        }
        return msg;
    }

    @Override
    public Channel[] updatePriority(Integer[] ids, Integer[] priority) {
        int len = ids.length;
        Channel[] beans = new Channel[len];
        for (int i = 0; i < len; i++) {
            beans[i] = findById(ids[i]);
            beans[i].setPriority(priority[i]);
        }
        return beans;
    }

    private String getChannelPath(String path, Integer siteId) {
        Channel findChannel = findByPath(path, siteId);
        if (findChannel != null) {
            path = path + RandomUtils.nextInt();
            return getChannelPath(path, siteId);
        }
        return path;
    }

    private List<ChannelDeleteChecker> deleteCheckerList;

    @Autowired
    public void setDeleteCheckerList(ContentMng contentMng, CmsTopicMng cmsTopicMng, CmsAcquisitionMng cmsAcquisitionMng) {
        this.deleteCheckerList = new ArrayList<>();
        deleteCheckerList.add(contentMng);
        deleteCheckerList.add(cmsTopicMng);
        deleteCheckerList.add(cmsAcquisitionMng);
    }

    @Autowired
    private CmsSiteMng cmsSiteMng;
    @Autowired
    private CmsModelMng cmsModelMng;
    @Autowired
    private ChannelExtMng channelExtMng;
    @Autowired
    private ChannelTxtMng channelTxtMng;
    @Autowired
    private ChannelCountMng channelCountMng;
    @Autowired
    private CmsUserMng cmsUserMng;
    @Autowired
    private CmsGroupMng cmsGroupMng;
    @Autowired
    private ChannelDao dao;
}