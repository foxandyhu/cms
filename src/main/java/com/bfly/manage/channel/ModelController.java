package com.bfly.manage.channel;

import com.bfly.cms.channel.entity.Model;
import com.bfly.cms.channel.service.IModelService;
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
 * 模型管理Controller
 *
 * @author andy_hulibo@163.com
 * @date 2018/12/19 10:32
 */
@RestController
@RequestMapping(value = "/manage/model")
public class ModelController extends BaseManageController {

    @Autowired
    private IModelService modelService;

    /**
     * 模型列表
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/19 10:33
     */
    @GetMapping(value = "/list")
    public void listModel(HttpServletResponse response) {
        PagerThreadLocal.set(getRequest());
        Pager pager = modelService.getPage(null);
        ResponseUtil.writeJson(response, pager);
    }

    /**
     * 添加模型
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/19 10:35
     */
    @PostMapping(value = "/add")
    public void addModel(@Valid Model model, BindingResult result, HttpServletResponse response) {
        validData(result);
        modelService.save(model);
        ResponseUtil.writeJson(response, "");
    }


    /**
     * 修改模型
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/19 10:36
     */
    @PostMapping(value = "/edit")
    public void editModel(@Valid Model model,BindingResult result, HttpServletResponse response) {
        validData(result);
        modelService.edit(model);
        ResponseUtil.writeJson(response, "");
    }

    /**
     * 查看模型信息
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/19 10:36
     */
    @GetMapping(value = "/{modelId}")
    public void viewModel(@PathVariable("modeId") int modelId, HttpServletResponse response) {
        Model model = modelService.get(modelId);
        ResponseUtil.writeJson(response, model);
    }

    /**
     * 删除模型
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/19 10:37
     */
    @PostMapping(value = "/del")
    public void delMode(HttpServletResponse response) {
        String idStr = getRequest().getParameter("ids");
        Integer[] ids = DataConvertUtils.convertToIntegerArray(idStr.split(","));
        modelService.remove(ids);
        ResponseUtil.writeJson(response, "");
    }
}
