package com.bfly.manage.system;

import com.bfly.cms.system.entity.FtpInfo;
import com.bfly.cms.system.service.IFtpInfoService;
import com.bfly.core.context.ContextUtil;
import com.bfly.common.DataConvertUtils;
import com.bfly.common.ResponseUtil;
import com.bfly.common.page.Pager;
import com.bfly.core.base.action.BaseManageController;
import com.bfly.core.context.PagerThreadLocal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

/**
 * FTP配置Controller
 *
 * @author andy_hulibo@163.com
 * @date 2018/12/18 11:32
 */
@RestController
@RequestMapping(value = "/manage/ftp")
public class FtpInfoController extends BaseManageController {

    @Autowired
    private IFtpInfoService ftpInfoService;

    /**
     * FTP配置列表
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/18 11:32
     */
    @GetMapping(value = "/list")
    public void listFtpInfo(HttpServletResponse response) {
        PagerThreadLocal.set(getRequest());
        Pager pager = ftpInfoService.getPage(null);
        ResponseUtil.writeJson(response, pager);
    }

    /**
     * 添加FTP配置
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/18 11:34
     */
    @PostMapping(value = "/add")
    public void addFtpInfo(@Valid FtpInfo ftpInfo, BindingResult result, HttpServletResponse response) {
        validData(result);
        ftpInfoService.save(ftpInfo);
        ResponseUtil.writeJson(response, "");
    }

    /**
     * 编辑FTP配置
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/18 11:34
     */
    @PostMapping(value = "/edit")
    public void editFtpInfo(@Valid FtpInfo ftpInfo,BindingResult result, HttpServletResponse response) {
        validData(result);
        ftpInfoService.save(ftpInfo);
        ResponseUtil.writeJson(response, "");
    }

    /**
     * FTP配置详情
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/18 11:35
     */
    @GetMapping(value = "/{ftpInfoId}")
    public void viewFtpInfo(@PathVariable("ftpInfoId") int ftpInfoId, HttpServletResponse response) {
        FtpInfo ftpInfo = ftpInfoService.get(ftpInfoId);
        ResponseUtil.writeJson(response, ftpInfo);
    }

    /**
     * 删除FTP配置
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/18 11:36
     */
    @PostMapping(value = "/del")
    public void delFtpInfo(HttpServletResponse response) {
        String ftpInfoIdStr = getRequest().getParameter("ids");
        Integer[] ftpInfoIds = DataConvertUtils.convertToIntegerArray(ftpInfoIdStr.split(","));
        ftpInfoService.remove(ftpInfoIds);
        ResponseUtil.writeJson(response, "");
    }
}
