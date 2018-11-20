package com.jeecms.core.manager.impl;

import com.jeecms.cms.entity.main.Channel;
import com.jeecms.cms.manager.main.ChannelMng;
import com.jeecms.cms.manager.main.ContentMng;
import com.jeecms.common.email.EmailSender;
import com.jeecms.common.email.MessageTemplate;
import com.jeecms.common.hibernate4.Updater;
import com.jeecms.common.page.Pagination;
import com.jeecms.core.dao.CmsUserDao;
import com.jeecms.core.entity.*;
import com.jeecms.core.manager.*;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
@Transactional
public class CmsUserMngImpl implements CmsUserMng {
    @Override
    @Transactional(readOnly = true)
    public Pagination getPage(String username, String email, Integer siteId,
                              Integer groupId, Integer statu, Boolean admin, Integer rank,
                              String realName, Integer roleId, Boolean allChannel,
                              Boolean allControlChannel, int pageNo,
                              int pageSize) {
        Pagination page = dao.getPage(username, email, siteId, groupId,
                statu, admin, rank, realName, roleId, allChannel,
                allControlChannel, pageNo, pageSize);
        return page;
    }

    @Override
    @Transactional(readOnly = true)
    public List<CmsUser> getList(String username, String email, Integer siteId,
                                 Integer groupId, Integer statu, Boolean admin, Integer rank) {
        List<CmsUser> list = dao.getList(username, email, siteId, groupId,
                statu, admin, rank);
        return list;
    }

    @Override
    @Transactional(readOnly = true)
    public List<CmsUser> getAdminList(Integer siteId, Boolean allChannel,
                                      Integer statu, Integer rank) {
        return dao.getAdminList(siteId, allChannel, statu, rank);
    }

    @Override
    @Transactional(readOnly = true)
    public Pagination getAdminsByRoleId(Integer roleId, int pageNo, int pageSize) {
        return dao.getAdminsByRoleId(roleId, pageNo, pageSize);
    }

    @Override
    @Transactional(readOnly = true)
    public CmsUser findById(Integer id) {
        CmsUser entity = dao.findById(id);
        return entity;
    }

    @Override
    @Transactional(readOnly = true)
    public CmsUser findByUsername(String username) {
        CmsUser entity = dao.findByUsername(username);
        return entity;
    }

    @Override
    public CmsUser registerMember(String username, String email,
                                  String password, String ip, Integer groupId, Integer grain, boolean uncheck, CmsUserExt userExt, Map<String, String> attr) {
        UnifiedUser unifiedUser = unifiedUserMng.save(username, email,
                password, ip);
        CmsUser user = new CmsUser();
        user.forMember(unifiedUser);
        user.setAttr(attr);
        if (uncheck) {
            user.setStatu(CmsUser.USER_STATU_CHECKING);
        } else {
            user.setStatu(CmsUser.USER_STATU_CHECKED);
        }
        CmsGroup group = null;
        if (groupId != null) {
            group = cmsGroupMng.findById(groupId);
        } else {
            group = cmsGroupMng.getRegDef();
        }
        if (group == null) {
            throw new RuntimeException(
                    "register default member group not found!");
        }
        user.setGroup(group);
        user.init();
        dao.save(user);
        cmsUserExtMng.save(userExt, user);
        return user;
    }


    @Override
    public CmsUser registerMember(String username, String email,
                                  String password, String ip, Integer groupId, boolean uncheck, CmsUserExt userExt, Map<String, String> attr,
                                  Boolean activation, EmailSender sender, MessageTemplate msgTpl) throws UnsupportedEncodingException, MessagingException {
        UnifiedUser unifiedUser = unifiedUserMng.save(username, email,
                password, ip, activation, sender, msgTpl);
        CmsUser user = new CmsUser();
        user.forMember(unifiedUser);
        user.setAttr(attr);
        if (uncheck) {
            user.setStatu(CmsUser.USER_STATU_CHECKING);
        } else {
            user.setStatu(CmsUser.USER_STATU_CHECKED);
        }
        CmsGroup group = null;
        if (groupId != null) {
            group = cmsGroupMng.findById(groupId);
        } else {
            group = cmsGroupMng.getRegDef();
        }
        if (group == null) {
            throw new RuntimeException(
                    "register default member group not found!");
        }
        user.setGroup(group);
        user.init();
        dao.save(user);
        cmsUserExtMng.save(userExt, user);
        return user;
    }

    @Override
    public void updateLoginInfo(Integer userId, String ip, Date loginTime, String sessionId) {
        CmsUser user = findById(userId);
        if (user != null) {
            user.setLoginCount(user.getLoginCount() + 1);
            if (StringUtils.isNotBlank(ip)) {
                user.setLastLoginIp(ip);
            }
            if (loginTime != null) {
                user.setLastLoginTime(loginTime);
            }
            user.setSessionId(sessionId);
        }
    }

    @Override
    public void updateUploadSize(Integer userId, Integer size) {
        CmsUser user = findById(userId);
        user.setUploadTotal(user.getUploadTotal() + size);
        if (user.getUploadDate() != null) {
            if (CmsUser.isToday(user.getUploadDate())) {
                size += user.getUploadSize();
            }
        }
        user.setUploadDate(new java.sql.Date(System.currentTimeMillis()));
        user.setUploadSize(size);
    }

    @Override
    public void updateUser(CmsUser user) {
        Updater<CmsUser> updater = new Updater<CmsUser>(user);
        dao.updateByUpdater(updater);
    }

    @Override
    public boolean isPasswordValid(Integer id, String password) {
        return unifiedUserMng.isPasswordValid(id, password);
    }

    @Override
    public void updatePwdEmail(Integer id, String password, String email) {
        CmsUser user = findById(id);
        if (!StringUtils.isBlank(email)) {
            user.setEmail(email);
        } else {
            user.setEmail(null);
        }
        unifiedUserMng.update(id, password, email);
    }

    @Override
    public CmsUser saveAdmin(String username, String email, String password,
                             String ip, boolean viewOnly, boolean selfAdmin, int rank,
                             Integer groupId, Integer[] roleIds, Integer[] channelIds, Integer[] siteIds,
                             Byte[] steps, Boolean[] allChannels, CmsUserExt userExt) {
        UnifiedUser unifiedUser = unifiedUserMng.save(username, email,
                password, ip);
        CmsUser user = new CmsUser();
        user.forAdmin(unifiedUser, viewOnly, selfAdmin, rank);
        CmsGroup group = null;
        if (groupId != null) {
            group = cmsGroupMng.findById(groupId);
        } else {
            group = cmsGroupMng.getRegDef();
        }
        if (group == null) {
            throw new RuntimeException(
                    "register default member group not setted!");
        }
        /*
		if (department == null) {
			throw new RuntimeException(
					"register default admin department not setted!");
		}
		*/
        user.setGroup(group);
        user.init();
        dao.save(user);
        cmsUserExtMng.save(userExt, user);
        if (roleIds != null) {
            for (Integer rid : roleIds) {
                user.addToRoles(cmsRoleMng.findById(rid));
            }
        }
        if (channelIds != null) {
            Channel channel;
            for (Integer cid : channelIds) {
                channel = channelMng.findById(cid);
                channel.addToUsers(user);
                while (channel.getParent() != null) {
                    channel = channelMng.findById(channel.getParentId());
                    channel.addToUsers(user);
                }
            }
        }
        if (siteIds != null) {
            CmsSite site;
            for (int i = 0, len = siteIds.length; i < len; i++) {
                site = cmsSiteMng.findById(siteIds[i]);
                cmsUserSiteMng.save(site, user, steps[i], allChannels[i]);
            }
        }
        return user;
    }

    @Override
    public void addSiteToUser(CmsUser user, CmsSite site, Byte checkStep) {
        cmsUserSiteMng.save(site, user, checkStep, true);
    }

    @Override
    public CmsUser updateAdmin(CmsUser bean, CmsUserExt ext, String password,
                               Integer groupId, Integer[] roleIds, Integer[] channelIds, Integer siteId,
                               Byte step, Boolean allChannel) {
        CmsUser user = updateAdmin(bean, ext, password, groupId, roleIds, channelIds);
        // 更新所属站点
        cmsUserSiteMng.updateByUser(user, siteId, step, allChannel);
        return user;
    }

    @Override
    public CmsUser updateAdmin(CmsUser bean, CmsUserExt ext, String password,
                               Integer groupId, Integer[] roleIds, Integer[] channelIds, Integer[] siteIds,
                               Byte[] steps, Boolean[] allChannels) {
        CmsUser user = updateAdmin(bean, ext, password, groupId, roleIds, channelIds);
        // 更新所属站点
        cmsUserSiteMng.updateByUser(user, siteIds, steps, allChannels);
        return user;
    }

    private CmsUser updateAdmin(CmsUser bean, CmsUserExt ext, String password,
                                Integer groupId, Integer[] roleIds, Integer[] channelIds) {
        Updater<CmsUser> updater = new Updater<CmsUser>(bean);
        updater.include("email");
        CmsUser user = dao.updateByUpdater(updater);
        user.setGroup(cmsGroupMng.findById(groupId));
        cmsUserExtMng.update(ext, user);
        // 更新角色
        user.getRoles().clear();
        if (roleIds != null) {
            for (Integer rid : roleIds) {
                user.addToRoles(cmsRoleMng.findById(rid));
            }
        }
        // 更新栏目权限
        Set<Channel> channels = user.getChannels();
        // 清除
        for (Channel channel : channels) {
            channel.getUsers().remove(user);
        }
        user.getChannels().clear();
        // 添加
        if (channelIds != null) {
            Channel channel;
            for (Integer cid : channelIds) {
                channel = channelMng.findById(cid);
                channel.addToUsers(user);
                while (channel.getParent() != null) {
                    channel = channelMng.findById(channel.getParentId());
                    channel.addToUsers(user);
                }
            }
        }
        unifiedUserMng.update(bean.getId(), password, bean.getEmail());
        return user;
    }

    @Override
    public CmsUser updateMember(Integer id, String email, String password,
                                Boolean isDisabled, CmsUserExt ext, Integer groupId, Integer grain, Map<String, String> attr) {
        CmsUser entity = findById(id);
        entity.setEmail(email);
		/*
		if (!StringUtils.isBlank(email)) {
			entity.setEmail(email);
		}
		*/
        if (isDisabled != null) {
            if (isDisabled) {
                entity.setStatu(CmsUser.USER_STATU_DISABLED);
            } else {
                entity.setStatu(CmsUser.USER_STATU_CHECKED);
            }
        }
        if (groupId != null) {
            entity.setGroup(cmsGroupMng.findById(groupId));
        }
        // 更新属性表
        if (attr != null) {
            Map<String, String> attrOrig = entity.getAttr();
            attrOrig.clear();
            attrOrig.putAll(attr);
        }
        cmsUserExtMng.update(ext, entity);
        unifiedUserMng.update(id, password, email);
        return entity;
    }

    @Override
    public CmsUser updateMember(Integer id, String email, String password, Integer groupId, String realname, String mobile, Boolean sex) {
        CmsUser entity = findById(id);
        CmsUserExt ext = entity.getUserExt();
        if (!StringUtils.isBlank(email)) {
            entity.setEmail(email);
        }
        if (groupId != null) {
            entity.setGroup(cmsGroupMng.findById(groupId));
        }
        if (!StringUtils.isBlank(realname)) {
            ext.setRealname(realname);
        }
        if (!StringUtils.isBlank(mobile)) {
            ext.setMobile(mobile);
        }
        if (sex != null) {
            ext.setGender(sex);
        }
        cmsUserExtMng.update(ext, entity);
        unifiedUserMng.update(id, password, email);
        return entity;
    }

    @Override
    public CmsUser updateUserConllection(CmsUser user, Integer cid, Integer operate) {
        Updater<CmsUser> updater = new Updater<CmsUser>(user);
        user = dao.updateByUpdater(updater);
        if (operate.equals(1)) {
            user.addToCollection(contentMng.findById(cid));
        }// 取消收藏
        else if (operate.equals(0)) {
            user.delFromCollection(contentMng.findById(cid));
        }
        return user;
    }

    @Override
    public CmsUser deleteById(Integer id) {
        unifiedUserMng.deleteById(id);
        CmsUser bean = dao.deleteById(id);
        //删除收藏信息
        bean.clearCollection();
        Set<Channel> channels = bean.getChannels();
        // 清除
        for (Channel channel : channels) {
            channel.getUsers().remove(bean);
        }
        return bean;
    }

    @Override
    public CmsUser[] deleteByIds(Integer[] ids) {
        CmsUser[] beans = new CmsUser[ids.length];
        for (int i = 0, len = ids.length; i < len; i++) {

            beans[i] = deleteById(ids[i]);
        }
        return beans;
    }

    @Override
    public boolean usernameNotExist(String username) {
        return dao.countByUsername(username) <= 0;
    }

    @Override
    public boolean usernameNotExistInMember(String username) {
        return dao.countMemberByUsername(username) <= 0;
    }

    @Override
    public boolean emailNotExist(String email) {
        return dao.countByEmail(email) <= 0;
    }

    @Autowired
    private CmsUserSiteMng cmsUserSiteMng;
    @Autowired
    private CmsSiteMng cmsSiteMng;
    @Autowired
    private ChannelMng channelMng;
    @Autowired
    private CmsRoleMng cmsRoleMng;
    @Autowired
    private CmsGroupMng cmsGroupMng;
    @Autowired
    private UnifiedUserMng unifiedUserMng;
    @Autowired
    private CmsUserExtMng cmsUserExtMng;
    @Autowired
    private CmsUserDao dao;
    @Autowired
    private ContentMng contentMng;
}