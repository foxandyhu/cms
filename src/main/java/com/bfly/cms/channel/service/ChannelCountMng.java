package com.bfly.cms.channel.service;

import com.bfly.cms.channel.entity.Channel;
import com.bfly.cms.channel.entity.ChannelCount;
import net.sf.ehcache.Ehcache;

/**
 * 栏目计数Manager接口
 * <p>
 * '栏目'数据存在则'栏目计数'数据必须存在。
 *
 * @author andy_hulibo@163.com
 * @date 2018/11/26 14:28
 */
public interface ChannelCountMng {

    int freshCacheToDB(Ehcache cache);

    ChannelCount findById(Integer id);

    ChannelCount save(ChannelCount count, Channel channel);

    ChannelCount update(ChannelCount bean);

    void afterSaveContent(Channel channel);

    void afterDelContent(Channel channel);
}