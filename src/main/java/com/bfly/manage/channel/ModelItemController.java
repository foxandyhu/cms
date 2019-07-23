package com.bfly.manage.channel;

import com.bfly.cms.channel.entity.ModelItem;
import com.bfly.cms.channel.service.IModelItemService;
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
 * 模型项管理Controller
 *
 * @author andy_hulibo@163.com
 * @date 2018/12/19 10:32
 */
@RestController
@RequestMapping(value = "/manage/model/item")
public class ModelItemController extends BaseManageController {

    @Autowired
    private IModelItemService modelItemService;

    /**
     * 模型项列表
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/19 10:33
     */
    @GetMapping(value = "/list")
    public void listModelItem(HttpServletResponse response) {
        PagerThreadLocal.set(getRequest());
        Pager pager = modelItemService.getPage(null);
        ResponseUtil.writeJson(response, pager);
    }

    /**
     * 添加模型项
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/19 10:35
     */
    @PostMapping(value = "/add")
    public void addModelItem(@Valid ModelItem modelItem, BindingResult result, HttpServletResponse response) {
        validData(result);
        modelItemService.save(modelItem);
        ResponseUtil.writeJson(response, "");
    }


    /**
     * 修改模型项
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/19 10:36
     */
    @PostMapping(value = "/edit")
    public void editModelItem(@Valid ModelItem modelItem,BindingResult result, HttpServletResponse response) {
        validData(result);
        modelItemService.edit(modelItem);
        ResponseUtil.writeJson(response, "");
    }

    /**
     * 查看模型项信息
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/19 10:36
     */
    @GetMapping(value = "/{modelItemId}")
    public void viewModelItem(@PathVariable("modelItemId") int modelItemId, HttpServletResponse response) {
        ModelItem modelItem = modelItemService.get(modelItemId);
        ResponseUtil.writeJson(response, modelItem);
    }

    /**
     * 删除模型项
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/19 10:37
     */
    @PostMapping(value = "/del")
    public void delModeItem(HttpServletResponse response) {
        String idStr = getRequest().getParameter("ids");
        Integer[] ids = DataConvertUtils.convertToIntegerArray(idStr.split(","));
        modelItemService.remove(ids);
        ResponseUtil.writeJson(response, "");
    }
}
