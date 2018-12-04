package com.bfly.cms.channel.service;

import com.bfly.cms.channel.entity.Channel;
import com.bfly.cms.channel.entity.ChannelExt;
import com.bfly.cms.channel.entity.ChannelTxt;
import com.bfly.common.page.Pagination;

import java.util.List;
import java.util.Map;

/**
 * 栏目管理接口
 *
 * @author andy_hulibo@163.com
 * @date 2018/11/26 14:24
 */
public interface ChannelMng {

    /**
     * 获得顶级栏目
     *
     * @param hasContentOnly 是否只获得有内容的栏目
     */
    List<Channel> getTopList(boolean hasContentOnly);

    List<Channel> getTopListByRigth(boolean hasContentOnly);

    List<Channel> getTopListForTag(boolean hasContentOnly);

    Pagination getTopPageForTag(boolean hasContentOnly, int pageNo, int pageSize);

    List<Channel> getChildList(Integer parentId, boolean hasContentOnly);

    List<Channel> getChildListByRight(Integer parentId, boolean hasContentOnly);

    List<Channel> getChildListForTag(Integer parentId, boolean hasContentOnly);

    List<Channel> getBottomList(boolean hasContentOnly);

    Pagination getChildPageForTag(Integer parentId, boolean hasContentOnly, int pageNo, int pageSize);

    Channel findByPath(String path);

    Channel findByPathForTag(String path);

    Channel findById(Integer id);

    Channel save(Channel bean, ChannelExt ext, ChannelTxt txt, Integer[] viewGroupIds, Integer[] contriGroupIds, Integer[] userIds, Integer parentId, Integer modelId, Integer[] modelIds, String[] tpls, String[] mtpls, boolean isCopy);

    Channel copy(Integer cid, String solution, String mobileSolution,  Map<String, String> pathMap);

    Channel update(Channel bean, ChannelExt ext, ChannelTxt txt, Integer[] viewGroupIds, Integer[] contriGroupIds, Integer[] userIds, Integer parentId, Map<String, String> attr, Integer modelId, Integer[] modelIds, String[] tpls, String[] mtpls);

    Channel deleteById(Integer id);

    Channel[] deleteByIds(Integer[] ids);

    Channel[] updatePriority(Integer[] ids, Integer[] priority);

    String checkDelete(Integer id);
}