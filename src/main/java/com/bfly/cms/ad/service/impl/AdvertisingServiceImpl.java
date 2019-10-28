package com.bfly.cms.ad.service.impl;

import com.bfly.cms.ad.entity.Advertising;
import com.bfly.cms.ad.service.IAdvertisingService;
import com.bfly.core.base.service.impl.BaseServiceImpl;
import com.bfly.core.config.ResourceConfig;
import com.bfly.core.enums.AdvertisingType;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

/**
 * @author andy_hulibo@163.com
 * @date 2018/12/11 17:09
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, rollbackFor = Exception.class)
public class AdvertisingServiceImpl extends BaseServiceImpl<Advertising, Integer> implements IAdvertisingService {

    @Override
    @Cacheable(value = "beanCache", key = "'ad_'+#integer")
    public Advertising get(Integer integer) {
        return super.get(integer);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean save(Advertising advertising) {
        checkAdvertising(advertising);
        if (advertising.getType() == AdvertisingType.PIC.getId()) {
            String imgPath = advertising.getAttr().get("pic_url");
            Assert.hasLength(imgPath, "广告图片未上传!");

            String img = ResourceConfig.getUploadTempFileToDestDirForRelativePath(imgPath, ResourceConfig.getAdvertisingDir());
            if (img != null) {
                advertising.getAttr().put("pic_url", img);
            }
        }
        return super.save(advertising);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @Cacheable(value = "beanCache", key = "'ad_'+#advertising.id")
    public boolean edit(Advertising advertising) {
        checkAdvertising(advertising);

        Advertising ad = get(advertising.getId());
        Assert.notNull(ad, "广告信息不存在!");

        if (advertising.getType() == AdvertisingType.PIC.getId()) {
            String imgPath = advertising.getAttr().get("pic_url");
            Assert.hasLength(imgPath, "广告图片未上传!");

            String img = ResourceConfig.getUploadTempFileToDestDirForRelativePath(imgPath, ResourceConfig.getAdvertisingDir());
            //修改了广告
            if (img != null) {
                advertising.getAttr().put("pic_url", img);
            } else {
                //没有修改广告
                advertising.getAttr().put("pic_url", ad.getAttr().get("pic_url"));
            }
        } else {
            advertising.getAttr().put("pic_url", ad.getAttr().get("pic_url"));
        }
        return super.edit(advertising);
    }


    /**
     * 对广告信息校验
     *
     * @author andy_hulibo@163.com
     * @date 2019/7/18 10:42
     */
    private void checkAdvertising(Advertising advertising) {
        Assert.isTrue(advertising.getStartTime() != null, "开始时间不能为空!");
        Assert.isTrue(advertising.getEndTime() != null, "结束时间不能为空!");
        AdvertisingType type = AdvertisingType.getType(advertising.getType());
        switch (type) {
            case PIC:
                Assert.hasLength(advertising.getAttr().get("pic_url"), "图片地址不能为空!");
                break;
            case TEXT:
                Assert.hasLength(advertising.getAttr().get("text_title"), "广告文字不能为空!");
                break;
            case CODE:
                Assert.hasLength(advertising.getAttr().get("code_content"), "代码内容不能为空!");
                break;
            default:
                throw new RuntimeException("广告类型错误!");
        }
    }
}
