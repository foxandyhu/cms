package com.bfly.cms.content.service.impl;

import com.bfly.cms.content.dao.ISpecialTopicDao;
import com.bfly.cms.content.entity.SpecialTopic;
import com.bfly.cms.content.service.ISpecialTopicService;
import com.bfly.core.base.service.impl.BaseServiceImpl;
import com.bfly.core.config.ResourceConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.io.File;

/**
 * @author andy_hulibo@163.com
 * @date 2019/8/4 15:10
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, rollbackFor = Exception.class)
public class SpecialTopicServiceImpl extends BaseServiceImpl<SpecialTopic, Integer> implements ISpecialTopicService {

    @Autowired
    private ISpecialTopicDao topicDao;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean save(SpecialTopic topic) {
        int maxSeq = topicDao.getMaxSeq();
        topic.setSeq(++maxSeq);
        String titleImg = ResourceConfig.getUploadTempFileToDestDirForRelativePath(topic.getTitleImg(), ResourceConfig.getContentDir());
        topic.setTitleImg(titleImg);

        String contentImg = ResourceConfig.getUploadTempFileToDestDirForRelativePath(topic.getContentImg(), ResourceConfig.getContentDir());
        topic.setContentImg(contentImg);

        String path = ResourceConfig.getTemplatePath() + File.separator + "topic/pc";
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
        path = ResourceConfig.getTemplatePath() + File.separator + "topic/mobile";
        file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
        return super.save(topic);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean edit(SpecialTopic topic) {
        SpecialTopic st = get(topic.getId());
        Assert.notNull(st, "不存在的专题信息!");

        String titleImg = ResourceConfig.getUploadTempFileToDestDirForRelativePath(topic.getTitleImg(), ResourceConfig.getContentDir());
        if (!StringUtils.hasLength(titleImg)) {
            titleImg = st.getTitleImg();
        }
        topic.setTitleImg(titleImg);
        String contentImg = ResourceConfig.getUploadTempFileToDestDirForRelativePath(topic.getContentImg(), ResourceConfig.getContentDir());
        if (!StringUtils.hasLength(contentImg)) {
            contentImg = st.getContentImg();
        }
        topic.setContentImg(contentImg);

        topic.setSeq(st.getSeq());
        return super.edit(topic);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void sortSpecialTopic(int upId, int downId) {
        SpecialTopic upTopic = get(upId);
        Assert.notNull(upTopic, "不存在的专题信息!");

        SpecialTopic downTopic = get(downId);
        Assert.notNull(downTopic, "不存在的专题信息!");

        topicDao.editSpecialTopicSeq(upId, downTopic.getSeq());
        topicDao.editSpecialTopicSeq(downId, upTopic.getSeq());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int remove(Integer... ids) {
        for (Integer id : ids) {
            if (id != null) {
                topicDao.removeSpecialTopicContentShip(id);
            }
        }
        return super.remove(ids);
    }
}
