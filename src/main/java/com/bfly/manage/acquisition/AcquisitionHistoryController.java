package com.bfly.manage.acquisition;

import com.bfly.cms.acquisition.service.IAcquisitionHistoryService;
import com.bfly.common.ContextUtil;
import com.bfly.common.DataConvertUtils;
import com.bfly.common.ResponseUtil;
import com.bfly.common.page.Pager;
import com.bfly.core.base.action.BaseManageController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 采集器历史记录Controller
 *
 * @author andy_hulibo@163.com
 * @date 2018/12/12 16:31
 */
@RestController
@RequestMapping(value = "/manage/acquisition/history")
public class AcquisitionHistoryController extends BaseManageController {

    @Autowired
    private IAcquisitionHistoryService acquisitionHistoryService;

    /**
     * 采集历史记录列表
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/12 16:32
     */
    @GetMapping("/list")
    public void listAcquisitionHistory(HttpServletRequest request, HttpServletResponse response) {
        ContextUtil.initPager(request);
        Pager pager = acquisitionHistoryService.getPage(null);
        ResponseUtil.writeJson(response, pager);
    }

    /**
     * 删除采集历史记录
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/12 16:33
     */
    @PostMapping(value = "/del")
    public void removeAcquisitionHistory(HttpServletRequest request, HttpServletResponse response) {
        String historyIdStr = request.getParameter("ids");
        Integer[] historyIds = DataConvertUtils.convertToIntegerArray(historyIdStr.split(","));
        acquisitionHistoryService.remove(historyIds);
        ResponseUtil.writeJson(response, "");
    }
}
