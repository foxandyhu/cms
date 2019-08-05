package com.bfly.manage.content;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bfly.cms.content.entity.ModelItem;
import com.bfly.cms.content.service.IModelItemService;
import com.bfly.common.ResponseData;
import com.bfly.common.ResponseUtil;
import com.bfly.core.base.action.BaseManageController;
import com.bfly.core.enums.DataType;
import com.bfly.core.security.ActionModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

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
     * 系统默认模型项列表
     *
     * @author andy_hulibo@163.com
     * @date 2019/8/3 20:36
     */
    @GetMapping(value = "/default")
    @ActionModel(value = "系统默认模型项集合", need = false)
    public void listSysModelItems(HttpServletResponse response) {
        List<ModelItem> list = ModelItem.getSysModelItem();
        ResponseUtil.writeJson(response, ResponseData.getSuccess(list));
    }

    /**
     * 模型项列表
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/19 10:33
     */
    @GetMapping(value = "/model-{modelId}")
    public void listModelItem(HttpServletResponse response, @PathVariable("modelId") int modelId) {
        List<ModelItem> list = modelItemService.getModelItems(modelId);
        ResponseUtil.writeJson(response, ResponseData.getSuccess(list));
    }

    /**
     * 添加模型项
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/19 10:35
     */
    @PostMapping(value = "/add")
    @ActionModel("添加模型项")
    public void addModelItem(@RequestBody @Valid ModelItem modelItem, BindingResult result, HttpServletResponse response) {
        validData(result);
        modelItemService.save(modelItem);
        ResponseUtil.writeJson(response, ResponseData.getSuccess(""));
    }

    /**
     * 绑定模型项
     *
     * @author andy_hulibo@163.com
     * @date 2019/8/4 14:12
     */
    @PostMapping(value = "/bind/{modelId}")
    @ActionModel("绑定模型项")
    public void bindModel(HttpServletResponse response, @PathVariable("modelId") int modelId, @RequestBody Integer... itemIds) {
        modelItemService.bindModel(modelId, itemIds);
        ResponseUtil.writeJson(response, ResponseData.getSuccess(""));
    }

    /**
     * 修改模型项
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/19 10:36
     */
    @PostMapping(value = "/edit")
    @ActionModel("编辑模型项")
    public void editModelItem(@RequestBody @Valid ModelItem modelItem, BindingResult result, HttpServletResponse response) {
        validData(result);
        modelItemService.edit(modelItem);
        ResponseUtil.writeJson(response, ResponseData.getSuccess(""));
    }

    /**
     * 查看模型项信息
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/19 10:36
     */
    @GetMapping(value = "/{modelItemId}")
    @ActionModel(value = "查看模型项", need = false)
    public void viewModelItem(@PathVariable("modelItemId") int modelItemId, HttpServletResponse response) {
        ModelItem modelItem = modelItemService.get(modelItemId);
        ResponseUtil.writeJson(response, ResponseData.getSuccess(modelItem));
    }

    /**
     * 删除模型项
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/19 10:37
     */
    @PostMapping(value = "/del")
    @ActionModel("删除模型项")
    public void delModeItem(HttpServletResponse response, @RequestBody Integer... ids) {
        modelItemService.remove(ids);
        ResponseUtil.writeJson(response, ResponseData.getSuccess(""));
    }

    /**
     * 模型项排序
     *
     * @author andy_hulibo@163.com
     * @date 2019/8/4 14:54
     */
    @GetMapping(value = "/sort/{upItemId}-{downItemId}")
    @ActionModel("模型项排序")
    public void sortModelItem(HttpServletResponse response, @PathVariable("upItemId") int upItemId, @PathVariable("downItemId") int downItemId) {
        modelItemService.sortModelItem(upItemId, downItemId);
        ResponseUtil.writeJson(response, ResponseData.getSuccess(""));
    }

    /**
     * 得到数据类型
     *
     * @author andy_hulibo@163.com
     * @date 2019/8/5 15:45
     */
    @GetMapping("/datatypes")
    @ActionModel(value = "得到数据类型", need = false)
    public void getDataTypes(HttpServletResponse response) {
        JSONArray array = new JSONArray();
        for (DataType type : DataType.values()) {
            JSONObject json = new JSONObject();
            json.put("id", type.getId());
            json.put("name", type.getName());
            array.add(json);
        }
        ResponseUtil.writeJson(response, ResponseData.getSuccess(array));
    }
}
