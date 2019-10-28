package com.bfly.web.ad.directive;

import com.bfly.cms.ad.entity.Advertising;
import com.bfly.cms.ad.service.IAdvertisingService;
import com.bfly.core.base.action.BaseTemplateDirective;
import com.bfly.core.config.ResourceConfig;
import com.bfly.core.enums.AdvertisingType;
import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.Date;
import java.util.Map;

/**
 * 广告标签
 * 给定指定广告ID 显示广告
 *
 * @author andy_hulibo@163.com
 * @date 2019/8/30 20:21
 */
@Component("adDirective")
public class AdDirective extends BaseTemplateDirective implements TemplateDirectiveModel {

    @Autowired
    private IAdvertisingService advertisingService;

    @Override
    public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body) throws TemplateException, IOException {
        String adId = "adId";
        if (!params.containsKey(adId)) {
            return;
        }
        Integer id = getData(adId, params, Integer.class);
        if (id == null) {
            return;
        }
        Advertising advertising = advertisingService.get(id);
        if (advertising == null || !advertising.isEnabled() || advertising.getStartTime().after(new Date()) || advertising.getEndTime().before(new Date())) {
            //  禁用,过期的广告不展示
            return;
        }
        if (advertising.getType() == AdvertisingType.PIC.getId()) {
            String picUrl = "pic_url";
            if (advertising.getAttr().containsKey(picUrl) && StringUtils.hasLength(advertising.getAttr().get(picUrl))) {
                String url = advertising.getAttr().get(picUrl);
                if (StringUtils.hasLength(url)) {
                    String http="http://",https="https://";
                    if (!url.startsWith(http) && !url.startsWith(https)) {
                        advertising.getAttr().put(picUrl, ResourceConfig.getServer() + url);
                    }
                }
            }
        }

        env.setVariable("advertising", getObjectWrapper().wrap(advertising));
        body.render(env.getOut());
    }
}
