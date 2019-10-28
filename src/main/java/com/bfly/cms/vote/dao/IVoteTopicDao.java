package com.bfly.cms.vote.dao;

import com.bfly.cms.vote.entity.VoteTopic;
import com.bfly.core.base.dao.IBaseDao;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * @author andy_hulibo@163.com
 * @date 2018/12/12 15:05
 */
public interface IVoteTopicDao extends IBaseDao<VoteTopic, Integer> {

    /**
     * 得到子主题回复数量
     *
     * @param voteSubTopicId
     * @return
     * @author andy_hulibo@163.com
     * @date 2019/9/11 13:13
     */
    @Query(value = "select count(1) from vote_reply where sub_topic_id=:voteSubTopicId", nativeQuery = true)
    long getVoteReplyCount(@Param("voteSubTopicId") int voteSubTopicId);

    /**
     * 投票选项
     *
     * @param voteTopicId 主题ID
     * @param itemId      选项ID
     * @param subTopicId  自主体ID
     * @return
     * @author andy_hulibo@163.com
     * @date 2019/9/11 20:28
     */
    @Modifying
    @Query(value = "update vote_item set vote_count=vote_count+1 where id=:itemId and sub_topic_id=(select id from vote_subtopic where id=:subTopicId and vote_topic_id=:voteTopicId)", nativeQuery = true)
    int voteSubmitItem(@Param("voteTopicId") int voteTopicId, @Param("subTopicId") int subTopicId, @Param("itemId") int itemId);

    /**
     * 投票回复选项
     *
     * @param subTopicId 自主体ID
     * @param text       回复内容
     * @return
     * @author andy_hulibo@163.com
     * @date 2019/9/11 20:31
     */
    @Modifying
    @Query(value = "insert into vote_reply(sub_topic_id,reply) values(:subTopicId,:text)", nativeQuery = true)
    int voteSubmitReply(@Param("subTopicId") int subTopicId, @Param("text") String text);

    /**
     * 投票量增长
     *
     * @param voteTopicId
     * @return
     * @author andy_hulibo@163.com
     * @date 2019/9/12 0:04
     */
    @Modifying
    @Query("update VoteTopic set totalCount=totalCount+1 where id=:voteTopicId")
    int incrementVoteTotal(@Param("voteTopicId") int voteTopicId);
}
