package com.bfly.manage.content;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bfly.cms.content.entity.Model;
import com.bfly.cms.content.service.IModelService;
import com.bfly.common.ResponseData;
import com.bfly.common.ResponseUtil;
import com.bfly.common.json.JsonUtil;
import com.bfly.common.page.Pager;
import com.bfly.core.base.action.BaseManageController;
import com.bfly.core.context.PagerThreadLocal;
import com.bfly.core.security.ActionModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

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
    @ActionModel(value = "模型列表", need = false)
    public void listModel(HttpServletResponse response) {
        PagerThreadLocal.set(getRequest());

        Map<String, Sort.Direction> sortMap = new HashMap<>(1);
        sortMap.put("seq", Sort.Direction.ASC);
        Pager pager = modelService.getPage(null, null, sortMap);
        JSONObject json = JsonUtil.toJsonFilter(pager, "items");
        ResponseUtil.writeJson(response, ResponseData.getSuccess(json));
    }

    /**
     * 所有模型信息
     *
     * @author andy_hulibo@163.com
     * @date 2019/8/5 18:50
     */
    @GetMapping(value = "/all")
    @ActionModel(value = "所有模型信息", need = false)
    public void getAllModel(HttpServletResponse response) {
        Map<String, Sort.Direction> sortMap = new HashMap<>(1);
        sortMap.put("seq", Sort.Direction.ASC);

        Map<String, Object> exactMap = new HashMap<>(1);
        exactMap.put("enabled", true);

        List<Model> list = modelService.getList(exactMap, null, sortMap);
        JSONArray json = JsonUtil.toJsonFilterForArray(list, "items", "remark", "seq");
        ResponseUtil.writeJson(response, ResponseData.getSuccess(json));
    }

    /**
     * 添加模型
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/19 10:35
     */
    @PostMapping(value = "/add")
    @ActionModel("添加模型")
    public void addModel(@RequestBody @Valid Model model, BindingResult result, HttpServletResponse response) {
        validData(result);
        modelService.save(model);
        ResponseUtil.writeJson(response, ResponseData.getSuccess(""));
    }


    /**
     * 修改模型
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/19 10:36
     */
    @PostMapping(value = "/edit")
    @ActionModel("编辑模型")
    public void editModel(@RequestBody @Valid Model model, BindingResult result, HttpServletResponse response) {
        validData(result);
        modelService.edit(model);
        ResponseUtil.writeJson(response, ResponseData.getSuccess(""));
    }

    /**
     * 查看模型信息
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/19 10:36
     */
    @GetMapping(value = "/{modelId}")
    @ActionModel(value = "模型详情", need = false)
    public void viewModel(@PathVariable("modelId") int modelId, HttpServletResponse response) {
        Model model = modelService.get(modelId);
        JSONObject json = JsonUtil.toJsonFilter(model, "items");
        ResponseUtil.writeJson(response, ResponseData.getSuccess(json));
    }

    /**
     * 删除模型
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/19 10:37
     */
    @PostMapping(value = "/del")
    @ActionModel("删除模型")
    public void delMode(HttpServletResponse response, @RequestBody Integer... ids) {
        modelService.remove(ids);
        ResponseUtil.writeJson(response, ResponseData.getSuccess(""));
    }

    /**
     * 模型排序
     *
     * @author andy_hulibo@163.com
     * @date 2019/8/4 14:54
     */
    @GetMapping(value = "/sort/{upItemId}-{downItemId}")
    @ActionModel("模型排序")
    public void sortModelItem(HttpServletResponse response, @PathVariable("upItemId") int upItemId, @PathVariable("downItemId") int downItemId) {
        modelService.sortModel(upItemId, downItemId);
        ResponseUtil.writeJson(response, ResponseData.getSuccess(""));
    }

    /**
     * 更新模型状态
     *
     * @author andy_hulibo@163.com
     * @date 2019/8/4 20:02
     */
    @GetMapping(value = "/enabled/{modelId}-{enabled}")
    @ActionModel("更新模型状态")
    public void editEnable(HttpServletResponse response, @PathVariable("modelId") int modelId, @PathVariable("enabled") boolean enabled) {
        modelService.editModelEnabled(modelId, enabled);
        ResponseUtil.writeJson(response, ResponseData.getSuccess(""));
    }

    /**
     * 根据模型加载默认路径的模板信息
     * PC模板,手机模板
     *
     * @author andy_hulibo@163.com
     * @date 2019/8/5 19:13
     */
    @GetMapping(value = "/template/{modelId}")
    @ActionModel(value = "得到模型默认模板集合", need = false)
    public void getDefaultTpls(@PathVariable("modelId") int modelId, HttpServletResponse response) {
        Model model = modelService.get(modelId);
        String path = getRequest().getServletContext().getRealPath(model.getTplPath());

        JSONObject json = new JSONObject();
        String pcPath = path + File.separator + "pc";
        Object[] pcTpl = getTpl(pcPath);
        json.put("pc", pcTpl);

        String mobilePath = path + File.separator + "mobile";
        Object[] mobileTpl = getTpl(mobilePath);
        json.put("mobile", mobileTpl);
        ResponseUtil.writeJson(response, ResponseData.getSuccess(json));
    }

    /**
     * 读取目录模板文件名称
     *
     * @author andy_hulibo@163.com
     * @date 2019/8/5 19:50
     */
    private Object[] getTpl(String path) {
        File mobileFile = new File(path);
        if (mobileFile.exists()) {
            Stream<File> stream = Stream.of(mobileFile.listFiles());
            Stream<String> streamFileName = stream.filter(file -> !file.isDirectory()).map(file -> file.getName());
            return streamFileName.toArray();
        }
        return null;
    }
}
