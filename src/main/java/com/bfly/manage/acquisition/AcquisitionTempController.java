package com.bfly.manage.acquisition;

import com.bfly.cms.acquisition.service.IAcquisitionTempService;
import com.bfly.core.base.action.BaseManageController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 采集进度Controller
 *
 * @author andy_hulibo@163.com
 * @date 2018/12/12 16:31
 */
@RestController
@RequestMapping(value = "/manage/acquisition/proc")
public class AcquisitionTempController extends BaseManageController {

    @Autowired
    private IAcquisitionTempService acquisitionTempService;


}
