package com.bfly.cms.channel.service;

import java.util.List;
import java.util.Map;

import com.bfly.cms.channel.entity.Channel;
import com.bfly.cms.channel.entity.ChannelExt;
import com.bfly.cms.channel.entity.ChannelTxt;
import com.bfly.common.page.Pagination;

/**
 * 栏目管理接口
 * @author andy_hulibo@163.com
 * @date 2018/11/26 14:24
 */
public interface ChannelMng {
	/**
	 * 获得顶级栏目
	 * 
	 * @param siteId
	 *            站点ID
	 * @param hasContentOnly
	 *            是否只获得有内容的栏目
	 * @return
	 */
	 List<Channel> getTopList(Integer siteId, boolean hasContentOnly);

	 List<Channel> getTopListByRigth(Integer userId, Integer siteId,boolean hasContentOnly);

	 List<Channel> getTopListForTag(Integer siteId, boolean hasContentOnly);

	 Pagination getTopPageForTag(Integer siteId, boolean hasContentOnly,int pageNo, int pageSize);

	 List<Channel> getChildList(Integer parentId, boolean hasContentOnly);

	 List<Channel> getChildListByRight(Integer userId, Integer siteId,Integer parentId, boolean hasContentOnly);

	 List<Channel> getChildListForTag(Integer parentId,boolean hasContentOnly);
	
	 List<Channel> getBottomList(Integer siteId,boolean hasContentOnly);

	 Pagination getChildPageForTag(Integer parentId,boolean hasContentOnly, int pageNo, int pageSize);

	
	 Channel findByPath(String path, Integer siteId);

	 Channel findByPathForTag(String path, Integer siteId);

	 Channel findById(Integer id);

	 Channel save(Channel bean, ChannelExt ext, ChannelTxt txt,
			Integer[] viewGroupIds, Integer[] contriGroupIds,
			Integer[] userIds, Integer siteId, Integer parentId,
			Integer modelId,Integer[]modelIds,
			String[] tpls,String[] mtpls,boolean isCopy);
	
	 Channel copy(Integer cid,String solution, String mobileSolution, Integer siteId, Map<String, String> pathMap);
	

	 Channel update(Channel bean, ChannelExt ext, ChannelTxt txt,
			Integer[] viewGroupIds, Integer[] contriGroupIds,
			Integer[] userIds, Integer parentId, Map<String, String> attr, Integer modelId,
			Integer[]modelIds,String[] tpls,String[] mtpls);

	 Channel deleteById(Integer id);

	 Channel[] deleteByIds(Integer[] ids);

	 Channel[] updatePriority(Integer[] ids, Integer[] priority);
	
	 String checkDelete(Integer id);
}