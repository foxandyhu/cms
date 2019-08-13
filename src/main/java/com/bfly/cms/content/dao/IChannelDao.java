package com.bfly.cms.content.dao;

import com.bfly.cms.content.entity.Channel;
import com.bfly.core.base.dao.IBaseDao;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;

/**
 * @author andy_hulibo@163.com
 * @date 2018/12/19 10:32
 */
public interface IChannelDao extends IBaseDao<Channel, Integer> {

    /**
     * 修改栏目排序
     *
     * @param channelId 栏目ID
     * @param seq       排序序号
     * @return 受影响行数
     * @author andy_hulibo@163.com
     * @date 2019/8/4 15:24
     */
    @Modifying
    @Query("update Channel set seq=:seq where id=:channelId")
    int editChannelSeq(@Param("channelId") int channelId, @Param("seq") int seq);

    /**
     * 获得最大的排序序号
     *
     * @return 返回最大的排序序号
     * @author andy_hulibo@163.com
     * @date 2019/8/4 20:46
     */
    @Query(value = "select IFNULL(max(seq),0) as seq from channel", nativeQuery = true)
    int getMaxSeq();

    /**
     * 解除栏目关系设置parentId为0
     *
     * @param channelParentId 解除关系parentId为channelParentId的数据
     * @return 受影响行数
     * @author andy_hulibo@163.com
     * @date 2019/8/5 21:47
     */
    @Modifying
    @Query("update Channel set parentId=0 where parentId=:channelParentId")
    int editChannelParent(@Param("channelParentId") int channelParentId);

    /**
     * 查询栏目所有相关信息
     * 包括所属模型名称,是否有内容
     * @return 栏目相关信息
     * @author andy_hulibo@163.com
     * @date 2019/8/7 14:58
     */
    @Query(value = "SELECT ch.id,ch.channel_name as name,ch.parent_id as parentId,m.has_content as hasContent,m.`name` as modelName,ch.model_id as modelId FROM channel as ch left join model as m on ch.model_id=m.id ORDER BY ch.seq ASC", nativeQuery = true)
    List<Map<String, Object>> getAllChannel();
}