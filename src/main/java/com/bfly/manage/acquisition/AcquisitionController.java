package com.bfly.manage.acquisition;

import com.bfly.cms.acquisition.entity.Acquisition;
import com.bfly.cms.acquisition.service.IAcquisitionService;
import com.bfly.common.ContextUtil;
import com.bfly.common.DataConvertUtils;
import com.bfly.common.ResponseUtil;
import com.bfly.common.page.Pager;
import com.bfly.core.base.action.BaseManageController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

/**
 * 采集器Controller
 *
 * @author andy_hulibo@163.com
 * @date 2018/12/12 16:31
 */
@RestController
@RequestMapping(value = "/manage/acquisition")
public class AcquisitionController extends BaseManageController {

    @Autowired
    private IAcquisitionService acquisitionService;

    /**
     * 采集器列表
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/12 16:32
     */
    @GetMapping("/list")
    public void listAcquisition(HttpServletRequest request, HttpServletResponse response) {
        ContextUtil.initPager(request);
        Pager pager = acquisitionService.getPage(null);
        ResponseUtil.writeJson(response, pager);
    }

    /**
     * 新增采集器
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/11 16:57
     */
    @PostMapping(value = "/add")
    public void addFriendLink(@Valid Acquisition acquisition, BindingResult result, HttpServletResponse response) {
        validData(result);
        acquisitionService.save(acquisition);
        ResponseUtil.writeJson(response, "");
    }

    /**
     * 编辑采集器
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/12 16:33
     */
    @PostMapping(value = "/edit")
    public void editAcquisition(@Valid Acquisition acquisition,BindingResult result, HttpServletResponse response) {
        validData(result);
        acquisitionService.edit(acquisition);
        ResponseUtil.writeJson(response, "");
    }

    /**
     * 获取友情链接基本信息
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/11 17:02
     */
    @GetMapping(value = "/{acquisitionId}")
    public void viewFriendLink(@PathVariable("acquisitionId") int acquisitionId, HttpServletResponse response) {
        Acquisition acquisition = acquisitionService.get(acquisitionId);
        ResponseUtil.writeJson(response, acquisition);
    }

    /**
     * 删除采集器
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/12 16:33
     */
    @PostMapping(value = "/del")
    public void removeAcquisition(HttpServletRequest request, HttpServletResponse response) {
        String acqIdStr = request.getParameter("ids");
        Integer[] acqIds = DataConvertUtils.convertToIntegerArray(acqIdStr.split(","));
        acquisitionService.remove(acqIds);
        ResponseUtil.writeJson(response, "");
    }

    /**
     * 开始一个采集器采集记录
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/12 16:39
     */
    @GetMapping(value = "/start")
    public void startAcquisition(HttpServletResponse response) {

    }

    /**
     * 暂停一个采集器采集记录
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/12 16:39
     */
    @GetMapping(value = "/pause")
    public void pauseAcquisition(HttpServletResponse response) {

    }

    /**
     * 停止一个采集器采集记录
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/12 16:39
     */
    @GetMapping(value = "/stop")
    public void stopAcquisition(HttpServletResponse response) {

    }
}
