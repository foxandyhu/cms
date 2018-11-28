package com.bfly.cms.user.service;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.mail.MessagingException;

import com.bfly.cms.system.entity.EmailSender;
import com.bfly.cms.system.service.MessageTemplate;
import com.bfly.common.page.Pagination;
import com.bfly.cms.siteconfig.entity.CmsSite;
import com.bfly.cms.user.entity.CmsUser;
import com.bfly.cms.user.entity.CmsUserExt;

public interface CmsUserMng {
	 Pagination getPage(String username, String email, Integer siteId,
			Integer groupId, Integer statu, Boolean admin, Integer rank,
			String realName,Integer roleId,Boolean allChannel,
			Boolean allControlChannel,int pageNo,
			int pageSize);
	
	 List<CmsUser> getList(String username, String email, Integer siteId,
			Integer groupId,Integer statu, Boolean admin, Integer rank);

	 List<CmsUser> getAdminList(Integer siteId, Boolean allChannel,
			Integer statu, Integer rank);
	
	
	 Pagination getAdminsByRoleId(Integer roleId, int pageNo, int pageSize);

	 CmsUser findById(Integer id);

	 CmsUser findByUsername(String username);

	 CmsUser registerMember(String username, String email,
			String password, String ip, Integer groupId,Integer grain, boolean disabled,CmsUserExt userExt,Map<String,String>attr);
	
	 CmsUser registerMember(String username, String email,
			String password, String ip, Integer groupId, boolean disabled,CmsUserExt userExt,Map<String,String>attr, Boolean activation , EmailSender sender, MessageTemplate msgTpl)throws UnsupportedEncodingException, MessagingException ;

	 void updateLoginInfo(Integer userId, String ip,Date loginTime,String sessionId);

	 void updateUploadSize(Integer userId, Integer size);
	
	 void updateUser(CmsUser user);

	 void updatePwdEmail(Integer id, String password, String email);

	 boolean isPasswordValid(Integer id, String password);

	 CmsUser saveAdmin(String username, String email, String password,
			String ip, boolean viewOnly, boolean selfAdmin, int rank,
			Integer groupId, Integer[] roleIds,Integer[] channelIds, Integer[] siteIds,
			Byte[] steps, Boolean[] allChannels, CmsUserExt userExt);

	 CmsUser updateAdmin(CmsUser bean, CmsUserExt ext, String password,
			Integer groupId,Integer[] roleIds,Integer[] channelIds, Integer[] siteIds,
			Byte[] steps, Boolean[] allChannels);

	 CmsUser updateAdmin(CmsUser bean, CmsUserExt ext, String password,
			Integer groupId,Integer[] roleIds, Integer[] channelIds, Integer siteId,
			Byte step, Boolean allChannel);

	 CmsUser updateMember(Integer id, String email, String password,
			Boolean isDisabled, CmsUserExt ext, Integer groupId,Integer grain,Map<String,String>attr);
	
	 CmsUser updateMember(Integer id, String email, String password,Integer groupId,String realname,String mobile,Boolean sex);
	
	 CmsUser updateUserConllection(CmsUser user,Integer cid,Integer operate);

	 void addSiteToUser(CmsUser user, CmsSite site, Byte checkStep);

	 CmsUser deleteById(Integer id);

	 CmsUser[] deleteByIds(Integer[] ids);

	 boolean usernameNotExist(String username);
	
	 boolean usernameNotExistInMember(String username);

	 boolean emailNotExist(String email);

}