package com.bfly.cms.content.service.impl;

import com.bfly.cms.content.dao.IModelItemDao;
import com.bfly.cms.content.entity.Model;
import com.bfly.cms.content.entity.ModelItem;
import com.bfly.cms.content.service.IModelItemService;
import com.bfly.cms.content.service.IModelService;
import com.bfly.core.base.service.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;

/**
 * @author andy_hulibo@163.com
 * @date 2018/12/19 10:31
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, rollbackFor = Exception.class)
public class ModelItemServiceImpl extends BaseServiceImpl<ModelItem, Integer> implements IModelItemService {

    @Autowired
    private IModelItemDao itemDao;
    @Autowired
    private IModelService modelService;

    @Override
    public List<ModelItem> getModelItems(int modelId) {
        return itemDao.getModelItems(modelId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean save(ModelItem modelItem) {
        Model model = modelService.get(modelItem.getModelId());
        Assert.notNull(model, "未指定模型项!");

        List<ModelItem> list = ModelItem.getSysModelItem();
        for (ModelItem item : list) {
            if (modelItem.getField().equalsIgnoreCase(item.getField())) {
                throw new RuntimeException("字段名称为系统保留名称!");
            }
        }

        ModelItem item = itemDao.getModelItem(modelItem.getModelId(), modelItem.getField());
        Assert.isTrue(item == null, "字段名称已存在!");

        int maxSeq = itemDao.getMaxSeq(model.getId());

        modelItem.setSeq(++maxSeq);
        modelItem.setCustom(true);
        modelItem.setEnabled(true);
        return super.save(modelItem);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean edit(ModelItem modelItem) {
        Model model = modelService.get(modelItem.getModelId());
        Assert.notNull(model, "未指定模型项!");

        ModelItem item = itemDao.getOne(modelItem.getId());
        Assert.notNull(item, "不存在的模型项!");
        modelItem.setField(item.getField());
        modelItem.setSeq(item.getSeq());
        return super.edit(modelItem);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void bindModel(int modelId, Integer... itemIds) {
        Model model = modelService.get(modelId);
        Assert.notNull(model, "未指定模型项!");

        List<ModelItem> list = ModelItem.getSysModelItem();
        if (itemIds != null) {
            for (Integer itemId : itemIds) {
                for (ModelItem item : list) {
                    if (item.getId() == itemId) {

                        ModelItem modelItem = itemDao.getModelItem(modelId, item.getField());
                        Assert.isTrue(modelItem == null, "该模型项已绑定!");

                        item.setModelId(modelId);
                        item.setId(0);
                        super.save(item);
                        break;
                    }
                }
            }
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void sortModelItem(int upItemId, int downItemId) {
        ModelItem upItem = get(upItemId);
        Assert.notNull(upItem, "不存在的模型项!");

        ModelItem downItem = get(downItemId);
        Assert.notNull(downItem, "不存在的模型项!");

        int downSeq = downItem.getSeq();

        itemDao.editModelItemSeq(downItemId, upItem.getSeq());
        itemDao.editModelItemSeq(upItemId, downSeq);

    }
}
