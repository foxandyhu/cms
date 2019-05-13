package com.bfly.cms.system.service.impl;

import com.bfly.cms.system.entity.SysWaterMark;
import com.bfly.cms.system.service.ISysWaterMarkService;
import com.bfly.core.base.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author andy_hulibo@163.com
 * @date 2018/12/20 9:31
 */
@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class SysWaterMarkServiceImpl extends BaseServiceImpl<SysWaterMark, Integer> implements ISysWaterMarkService {


    @Override
    public SysWaterMark getWaterMark() {
        List<SysWaterMark> list = getList();
        return (list != null && !list.isEmpty()) ? list.get(0) : null;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean edit(SysWaterMark sysWaterMark) {
        SysWaterMark waterMark = getWaterMark();
        //不存在则新增 存在则修改
        if (waterMark == null) {
            return super.save(sysWaterMark);
        }
        sysWaterMark.setId(waterMark.getId());
        return super.edit(sysWaterMark);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean save(SysWaterMark waterMark) {
        return edit(waterMark);
    }
}
