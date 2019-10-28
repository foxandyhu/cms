package com.bfly.cms.content.service.impl;

import com.bfly.cms.content.dao.IModelDao;
import com.bfly.cms.content.entity.Model;
import com.bfly.cms.content.service.IModelService;
import com.bfly.common.FileUtil;
import com.bfly.core.base.service.impl.BaseServiceImpl;
import com.bfly.core.config.ResourceConfig;
import com.bfly.core.context.ServletRequestThreadLocal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.servlet.http.HttpServletRequest;
import java.io.File;

/**
 * @author andy_hulibo@163.com
 * @date 2018/12/19 10:31
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, rollbackFor = Exception.class)
public class ModelServiceImpl extends BaseServiceImpl<Model, Integer> implements IModelService {

    @Autowired
    private IModelDao modelDao;

    /**
     * 创建文件夹目录
     *
     * @author andy_hulibo@163.com
     * @date 2019/8/5 19:30
     */
    private void createTplDir(String dirName) {
        HttpServletRequest request = ServletRequestThreadLocal.get();
        String path = request.getServletContext().getRealPath("");
        path = path + File.separator + ResourceConfig.getTemplateRelativePath() + dirName;
        FileUtil.mkdir(path);

        String pcPath = path + File.separator + "pc";
        FileUtil.mkdir(pcPath);

        String mobilePath = path + File.separator + "mobile";
        FileUtil.mkdir(mobilePath);
    }

    @Override
    @Cacheable(value = "beanCache", key = "'model_'+#integer")
    public Model get(Integer integer) {
        return super.get(integer);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value = "beanCache", key = "'model_'+#model.id")
    public boolean edit(Model model) {
        Model m = get(model.getId());
        Assert.notNull(m, "不存在的模型信息!");
        model.setSeq(m.getSeq());
        createTplDir(m.getTplDir());
        return super.edit(model);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean save(Model model) {
        int maxSeq = modelDao.getMaxSeq();
        model.setSeq(++maxSeq);
        createTplDir(model.getTplDir());
        return super.save(model);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @Caching(evict ={@CacheEvict(value = "beanCache", key = "'model_'+#upItemId"),@CacheEvict(value = "beanCache", key = "'model_'+#downItemId")} )
    public void sortModel(int upItemId, int downItemId) {
        Model upItem = get(upItemId);
        Assert.notNull(upItem, "不存在的模型!");

        Model downItem = get(downItemId);
        Assert.notNull(downItem, "不存在的模型!");

        int downSeq = downItem.getSeq();

        modelDao.editModelSeq(downItemId, upItem.getSeq());
        modelDao.editModelSeq(upItemId, downSeq);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value = "beanCache", key = "'model_'+#modelId")
    public boolean editModelEnabled(int modelId, boolean enabled) {
        return modelDao.editModelEnabled(modelId, enabled) > 0;
    }
}
