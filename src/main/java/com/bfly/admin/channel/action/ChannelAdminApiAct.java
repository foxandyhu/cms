package com.bfly.admin.channel.action;

import com.bfly.cms.channel.entity.Channel;
import com.bfly.cms.channel.entity.ChannelExt;
import com.bfly.cms.channel.entity.ChannelTxt;
import com.bfly.cms.channel.service.ChannelMng;
import com.bfly.cms.content.entity.CmsModel;
import com.bfly.cms.content.service.CmsModelMng;
import com.bfly.cms.logs.service.CmsLogMng;
import com.bfly.cms.siteconfig.entity.CmsSite;
import com.bfly.cms.siteconfig.service.CmsSiteMng;
import com.bfly.common.util.ChineseCharToEn;
import com.bfly.common.util.StrUtils;
import com.bfly.common.web.RequestUtils;
import com.bfly.common.web.ResponseUtils;
import com.bfly.core.Constants;
import com.bfly.core.annotation.SignValidate;
import com.bfly.core.base.action.BaseAdminController;
import com.bfly.core.exception.ApiException;
import com.bfly.core.web.ApiResponse;
import com.bfly.core.web.ApiValidate;
import com.bfly.core.web.ResponseCode;
import com.bfly.core.web.WebErrors;
import org.apache.commons.lang.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

/**
 * 栏目Controller
 *
 * @author andy_hulibo@163.com
 * @date 2018/11/26 14:36
 */
@Controller("adminChannelApiAct")
@RequestMapping(value = "/api/admin")
public class ChannelAdminApiAct extends BaseAdminController {

    private static final Logger log = LoggerFactory.getLogger(ChannelAdminApiAct.class);


    @RequestMapping("/channel/select")
    public void channelSelect(Boolean hasContentOnly, Integer excludeId, HttpServletRequest request, HttpServletResponse response) {
        if (hasContentOnly == null) {
            hasContentOnly = true;
        }
        List<Channel> topList = channelMng.getTopList(hasContentOnly);
        JSONArray jsonArray = new JSONArray();
        if (topList != null && topList.size() > 0) {
            int j = 0;
            for (int i = 0; i < topList.size(); i++) {
                JSONObject json = createSelectJson(topList.get(i), excludeId);
                if (json != null) {
                    jsonArray.put(j, json);
                } else {
                    j--;
                }
                j++;
            }
        }
        String body = jsonArray.toString();
        ApiResponse apiResponse = ApiResponse.getSuccess(body);
        ResponseUtils.renderApiJson(response, request, apiResponse);
    }

    /**
     * 栏目列表API
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/4 13:57
     */
    @RequestMapping(value = "/channel/list")
    public void channelList(Integer https, Integer parentId, Boolean all, HttpServletRequest request, HttpServletResponse response) {
        if (https == null) {
            https = Constants.URL_HTTP;
        }
        if (all == null) {
            all = false;
        }
        List<Channel> list;
        if (parentId == null) {
            list = channelMng.getTopList(false);
        } else {
            list = channelMng.getChildList(parentId, false);
        }
        JSONArray jsonArray = new JSONArray();
        if (list != null && list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                jsonArray.put(i, list.get(i).convertToJson(https, all, false, null));
            }
        }
        String body = jsonArray.toString();
        ApiResponse apiResponse = ApiResponse.getSuccess(body);
        ResponseUtils.renderApiJson(response, request, apiResponse);
    }


    @RequestMapping(value = "/channel/tree")
    public void tree(Integer https, Integer parentId, HttpServletRequest request, HttpServletResponse response) {
        if (https == null) {
            https = Constants.URL_HTTP;
        }
        boolean isRoot = parentId == null || parentId == 0;
        List<Channel> list;
        if (isRoot) {
            list = channelMng.getTopList(false);
        } else {
            list = channelMng.getChildList(parentId, false);
        }
        JSONArray jsonArray = new JSONArray();
        if (list != null && list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                jsonArray.put(i, list.get(i).convertToJson(https, false, false, null));
            }
        }
        String body = jsonArray.toString();
        ApiResponse apiResponse = ApiResponse.getSuccess(body);
        ResponseUtils.renderApiJson(response, request, apiResponse);
    }

    /**
     * 获取栏目信息
     * id或者path
     *
     * @param id   栏目id
     * @param path 栏目路径
     */
    @RequestMapping(value = "/channel/get")
    public void channelGet(Integer https, Integer id, String path, HttpServletRequest request, HttpServletResponse response) {
        Channel channel;
        if (https == null) {
            https = Constants.URL_HTTP;
        }
        if (id != null) {
            if (id.equals(0)) {
                channel = new Channel();
            } else {
                channel = channelMng.findById(id);
            }
        } else {
            channel = channelMng.findByPathForTag(path);
        }
        if (channel == null) {
            throw new ApiException("参数错误", ResponseCode.API_CODE_PARAM_ERROR);
        }
        channel.init();
        List<CmsModel> modelList = modelMng.getList(false, true);
        JSONObject json = channel.convertToJson(https, false, true, modelList);
        String body = json.toString();
        ApiResponse apiResponse = ApiResponse.getSuccess(body);
        ResponseUtils.renderApiJson(response, request, apiResponse);
    }

    /**
     * 栏目保存接口
     *
     * @param parentId 父栏目ID 非必选
     * @param txt      栏目文本内容 非必选
     * @param modelId  模型ID 必选  新闻 1
     */
    @SignValidate
    @RequestMapping(value = "/channel/save")
    public void save(Integer parentId, Channel bean, ChannelExt ext, ChannelTxt txt, String viewGroupIds, String contriGroupIds, Integer modelId, String modelIds, String tpls, String mtpls, HttpServletRequest request, HttpServletResponse response) {
        WebErrors errors = WebErrors.create(request);
        //验证公共非空参数
        errors = ApiValidate.validateRequiredParams(request, errors, ext.getName(), bean.getPath(), modelId);
        Integer[] viewGroupIdArray = null;
        if (StringUtils.isNotBlank(viewGroupIds)) {
            viewGroupIdArray = StrUtils.getInts(viewGroupIds);
        }
        Integer[] contriGroupIdArray = null;
        if (StringUtils.isNotBlank(contriGroupIds)) {
            contriGroupIdArray = StrUtils.getInts(contriGroupIds);
        }
        Integer[] modelIdArray = null;
        if (StringUtils.isNotBlank(modelIds)) {
            modelIdArray = StrUtils.getInts(modelIds);
        }
        String[] tplArray = null;
        if (StringUtils.isNotBlank(tpls)) {
            tplArray = StrUtils.getStrArrayComplete(tpls);
        } else {
            if (modelIdArray != null && modelIdArray.length == 1) {
                tplArray = new String[1];
                tplArray[0] = "";
            }
        }
        String[] mtplArray = null;
        if (StringUtils.isNotBlank(mtpls)) {
            mtplArray = StrUtils.getStrArrayComplete(mtpls);
        } else {
            if (modelIdArray != null && modelIdArray.length == 1) {
                mtplArray = new String[1];
                mtplArray[0] = "";
            }
        }
        if (bean.getPriority() == null) {
            bean.setPriority(10);
        }
        if (bean.getDisplay() == null) {
            bean.setDisplay(true);
        }
        if (errors.hasErrors()) {
            throw new ApiException("缺少参数", ResponseCode.API_CODE_PARAM_REQUIRED);
        }
        if (validatePath(bean.getPath())) {
            throw new ApiException("参数错误", ResponseCode.API_CODE_PARAM_ERROR);
        }
        CmsSite site = siteMng.getSite();
        // 加上模板前缀
        String tplPath = site.getTplPath();
        if (!StringUtils.isBlank(ext.getTplChannel())) {
            ext.setTplChannel(tplPath + ext.getTplChannel());
        }
        if (!StringUtils.isBlank(ext.getTplContent())) {
            ext.setTplContent(tplPath + ext.getTplContent());
        }
        if (!StringUtils.isBlank(ext.getTplMobileChannel())) {
            ext.setTplMobileChannel(tplPath + ext.getTplMobileChannel());
        }
        if (tplArray != null && tplArray.length > 0) {
            for (int t = 0; t < tplArray.length; t++) {
                if (!StringUtils.isBlank(tplArray[t]) && !tplArray[t].startsWith(tplPath)) {
                    tplArray[t] = tplPath + tplArray[t];
                }
            }
        }
        if (mtplArray != null && mtplArray.length > 0) {
            for (int t = 0; t < mtplArray.length; t++) {
                if (!StringUtils.isBlank(mtplArray[t]) && !mtplArray[t].startsWith(tplPath)) {
                    mtplArray[t] = tplPath + mtplArray[t];
                }
            }
        }
        bean.setAttr(RequestUtils.getRequestMap(request, "attr_"));
        bean = channelMng.save(bean, ext, txt, viewGroupIdArray, contriGroupIdArray, null, parentId, modelId, modelIdArray, tplArray, mtplArray, false);
        log.info("save Channel id={}, name={}", bean.getId(), bean.getName());
        cmsLogMng.operating(request, "channel.log.save", "id=" + bean.getId() + ";title=" + bean.getTitle());
        String body = "{\"id\":" + "\"" + bean.getId() + "\"}";
        ApiResponse apiResponse = ApiResponse.getSuccess(body);
        ResponseUtils.renderApiJson(response, request, apiResponse);
    }

    /**
     * 栏目修改接口
     *
     * @param parentId 父栏目ID 非必选
     * @param txt      栏目文本内容 非必选
     * @param modelId  模型ID 非必选  新闻 1
     */
    @SignValidate
    @RequestMapping(value = "/channel/update")
    public void update(Integer parentId, Channel bean, ChannelExt ext, ChannelTxt txt, String viewGroupIds, String contriGroupIds, String modelIds, String tpls, String mtpls, Integer modelId, HttpServletRequest request, HttpServletResponse response) {
        WebErrors errors = validateUpdate(bean.getId(), request);
        //验证公共非空参数
        errors = ApiValidate.validateRequiredParams(request, errors, ext.getName(), bean.getPath(), modelId);
        Integer[] viewGroupIdArray = null;
        if (StringUtils.isNotBlank(viewGroupIds)) {
            viewGroupIdArray = StrUtils.getInts(viewGroupIds);
        }
        Integer[] contriGroupIdArray = null;
        if (StringUtils.isNotBlank(contriGroupIds)) {
            contriGroupIdArray = StrUtils.getInts(contriGroupIds);
        }
        Integer[] modelIdArray = null;
        if (StringUtils.isNotBlank(modelIds)) {
            modelIdArray = StrUtils.getInts(modelIds);
        }
        String[] tplArray = null;
        if (StringUtils.isNotBlank(tpls)) {
            tplArray = StrUtils.getStrArrayComplete(tpls);
        } else {
            if (modelIdArray != null && modelIdArray.length == 1) {
                tplArray = new String[1];
                tplArray[0] = "";
            }
        }
        String[] mtplArray = null;
        if (StringUtils.isNotBlank(mtpls)) {
            mtplArray = StrUtils.getStrArrayComplete(mtpls);
        } else {
            if (modelIdArray != null && modelIdArray.length == 1) {
                mtplArray = new String[1];
                mtplArray[0] = "";
            }
        }
        if (bean.getPriority() == null) {
            bean.setPriority(10);
        }
        if (bean.getDisplay() == null) {
            bean.setDisplay(true);
        }
        if (errors.hasErrors()) {
            throw new ApiException("缺少参数", ResponseCode.API_CODE_PARAM_REQUIRED);
        }
        if (validatePath(bean.getPath())) {
            throw new ApiException("参数错误", ResponseCode.API_CODE_PARAM_ERROR);
        }
        CmsSite site = siteMng.getSite();
        // 加上模板前缀
        String tplPath = site.getTplPath();
        if (!StringUtils.isBlank(ext.getTplChannel())) {
            ext.setTplChannel(tplPath + ext.getTplChannel());
        }
        if (!StringUtils.isBlank(ext.getTplContent())) {
            ext.setTplContent(tplPath + ext.getTplContent());
        }
        if (!StringUtils.isBlank(ext.getTplMobileChannel())) {
            ext.setTplMobileChannel(tplPath + ext.getTplMobileChannel());
        }
        if (tplArray != null && tplArray.length > 0) {
            for (int t = 0; t < tplArray.length; t++) {
                if (!StringUtils.isBlank(tplArray[t]) && !tplArray[t].startsWith(tplPath)) {
                    tplArray[t] = tplPath + tplArray[t];
                }
            }
        }
        if (mtplArray != null && mtplArray.length > 0) {
            for (int t = 0; t < mtplArray.length; t++) {
                if (!StringUtils.isBlank(mtplArray[t]) && !mtplArray[t].startsWith(tplPath)) {
                    mtplArray[t] = tplPath + mtplArray[t];
                }
            }
        }
        Map<String, String> attr = RequestUtils.getRequestMap(request, "attr_");
        bean = channelMng.update(bean, ext, txt, viewGroupIdArray, contriGroupIdArray, null, parentId, attr, modelId, modelIdArray, tplArray, mtplArray);
        log.info("update Channel id={}.", bean.getId());
        cmsLogMng.operating(request, "channel.log.update", "id=" + bean.getId() + ";name=" + bean.getName());
        String body = "{\"id\":" + "\"" + bean.getId() + "\"}";
        ApiResponse apiResponse = ApiResponse.getSuccess(body);
        ResponseUtils.renderApiJson(response, request, apiResponse);
    }

    @SignValidate
    @RequestMapping("/channel/delete")
    public void delete(String ids, HttpServletRequest request, HttpServletResponse response) {
        Integer[] idArray = null;
        WebErrors errors = WebErrors.create(request);
        if (StringUtils.isNotBlank(ids)) {
            idArray = StrUtils.getInts(ids);
        }
        errors = ApiValidate.validateRequiredParams(request, errors, ids);
        if (idArray == null || errors.hasErrors()) {
            throw new ApiException("缺少参数", ResponseCode.API_CODE_PARAM_REQUIRED);
        }
        errors = validateDelete(idArray, request);
        if (errors.hasErrors()) {
            throw new ApiException("参数错误", ResponseCode.API_CODE_PARAM_ERROR);
        }
        try {
            Channel[] beans = channelMng.deleteByIds(idArray);
            for (Channel bean : beans) {
                log.info("delete Channel id={}", bean.getId());
                cmsLogMng.operating(request, "channel.log.delete", "id=" + bean.getId() + ";title=" + bean.getTitle());
            }
        } catch (Exception e) {
            throw new ApiException("删除出错", ResponseCode.API_CODE_DELETE_ERROR);
        }
        ApiResponse apiResponse = ApiResponse.getSuccess();
        ResponseUtils.renderApiJson(response, request, apiResponse);
    }

    @SignValidate
    @RequestMapping(value = "/channel/copy")
    public void channelCopy(String ids, String solution, String mobileSolution, HttpServletRequest request, HttpServletResponse response) {
        WebErrors errors = WebErrors.create(request);
        Integer[] channelIds = null;
        if (StringUtils.isNotBlank(ids)) {
            channelIds = StrUtils.getInts(ids);
        }
        if (channelIds == null) {
            throw new ApiException("参数错误", ResponseCode.API_CODE_PARAM_ERROR);
        }
        errors = ApiValidate.validateRequiredParams(request, errors, ids);
        if (errors.hasErrors()) {
            throw new ApiException("缺少参数", ResponseCode.API_CODE_PARAM_REQUIRED);
        }
        Map<String, String> pathMap = new HashMap<>(5);
        for (Integer id : channelIds) {
            channelMng.copy(id, solution, mobileSolution, pathMap);
        }
        //临时存放新旧栏目路径对应关系
        pathMap.clear();
        ApiResponse apiResponse = ApiResponse.getSuccess();
        ResponseUtils.renderApiJson(response, request, apiResponse);
    }

    @SignValidate
    @RequestMapping("/channel/priority")
    public void priority(String ids, String prioritys, HttpServletRequest request, HttpServletResponse response) {
        WebErrors errors = WebErrors.create(request);
        Integer[] idInts = null, priorityInts = null;
        if (StringUtils.isNotBlank(ids)) {
            idInts = StrUtils.getInts(ids);
            priorityInts = StrUtils.getInts(prioritys);
        }
        if (idInts == null || priorityInts == null) {
            throw new ApiException("参数错误", ResponseCode.API_CODE_PARAM_ERROR);
        }
        errors = ApiValidate.validateRequiredParams(request, errors, ids, prioritys);
        if (errors.hasErrors()) {
            throw new ApiException("缺少参数", ResponseCode.API_CODE_PARAM_REQUIRED);
        }
        errors = validatePriority(idInts, priorityInts, request);
        if (errors.hasErrors()) {
            throw new ApiException("缺少参数", ResponseCode.API_CODE_PARAM_REQUIRED);
        }
        channelMng.updatePriority(idInts, priorityInts);
        ApiResponse apiResponse = ApiResponse.getSuccess();
        ResponseUtils.renderApiJson(response, request, apiResponse);
    }

    @RequestMapping(value = "/channel/create_path")
    public void createPath(String name, HttpServletRequest request, HttpServletResponse response) {
        WebErrors errors = WebErrors.create(request);
        //验证公共非空参数
        errors = ApiValidate.validateRequiredParams(request, errors, name);
        if (errors.hasErrors()) {
            throw new ApiException("缺少参数", ResponseCode.API_CODE_PARAM_REQUIRED);
        }
        String path;
        if (StringUtils.isBlank(name)) {
            path = "";
        } else {
            path = ChineseCharToEn.getAllFirstLetter(name);
        }
        String body = "\"" + path + "\"";
        ApiResponse apiResponse = ApiResponse.getSuccess(body);
        ResponseUtils.renderApiJson(response, request, apiResponse);
    }

    @RequestMapping(value = "/channel/v_check_path")
    public void checkPath(Integer id, String path, HttpServletRequest request, HttpServletResponse response) {
        WebErrors errors = WebErrors.create(request);
        errors = ApiValidate.validateRequiredParams(request, errors, path);
        if (errors.hasErrors()) {
            throw new ApiException("缺少参数", ResponseCode.API_CODE_PARAM_REQUIRED);
        }
        String pass;
        if (StringUtils.isBlank(path)) {
            pass = "false";
        } else {
            Channel c = channelMng.findByPath(path);
            if (c == null) {
                pass = "true";
            } else {
                if (id != null && c.getId().equals(id)) {
                    pass = "true";
                } else {
                    pass = "false";
                }
            }
        }
        String body = "\"" + pass + "\"";
        ApiResponse apiResponse = ApiResponse.getSuccess(body);
        ResponseUtils.renderApiJson(response, request, apiResponse);
    }

    private JSONObject createSelectJson(Channel channel, Integer excludeId) {
        JSONObject json = new JSONObject();
        if (channel != null) {
            if (excludeId == null || (excludeId != null && !channel.getId().equals(excludeId))) {
                if (channel.getId() != null) {
                    json.put("id", channel.getId());
                } else {
                    json.put("id", "");
                }
                if (StringUtils.isNotBlank(channel.getName())) {
                    json.put("name", channel.getName());
                } else {
                    json.put("name", "");
                }
                if (channel.getChild() != null && channel.getChild().size() > 0) {
                    json.put("hasChild", true);
                    Set<Channel> child = channel.getChild();
                    JSONArray jsonArray = new JSONArray();
                    int index = 0;
                    for (Channel c : child) {
                        JSONObject jsonObj = createSelectJson(c, excludeId);
                        if (jsonObj != null) {
                            if (jsonObj.has("hasChild")) {
                                jsonArray.put(index, jsonObj);
                                index++;
                            }
                        } else if (index > 0) {
                            index--;
                        }
                    }
                    json.put("child", jsonArray);
                } else {
                    json.put("hasChild", false);
                }
            } else {
                //清空对象，否则前端还是多个空对象
                json = null;
            }
        } else {
            json = null;
        }
        return json;
    }

    private WebErrors validateUpdate(Integer id, HttpServletRequest request) {
        WebErrors errors = WebErrors.create(request);
        if (vldExist(id, errors)) {
            return errors;
        }
        return errors;
    }

    private WebErrors validateDelete(Integer[] ids, HttpServletRequest request) {
        WebErrors errors = WebErrors.create(request);
        errors.ifEmpty(ids, "ids", false);
        for (Integer id : ids) {
            if (vldExist(id, errors)) {
                return errors;
            }
            // 检查是否可以删除
            String code = channelMng.checkDelete(id);
            if (code != null) {
                errors.addErrorString(code);
                return errors;
            }
        }
        return errors;
    }


    private WebErrors validatePriority(Integer[] wids, Integer[] priority, HttpServletRequest request) {
        WebErrors errors = WebErrors.create(request);
        if (errors.ifEmpty(wids, "wids", false)) {
            return errors;
        }
        if (errors.ifEmpty(priority, "priority", false)) {
            return errors;
        }
        if (wids.length != priority.length) {
            errors.addErrorString(Constants.API_MESSAGE_PARAM_ERROR);
            return errors;
        }
        for (int i = 0, len = wids.length; i < len; i++) {
            if (vldExist(wids[i], errors)) {
                return errors;
            }
            if (priority[i] == null) {
                priority[i] = 0;
            }
        }
        return errors;
    }

    private boolean vldExist(Integer id, WebErrors errors) {
        if (errors.ifNull(id, "id", false)) {
            return true;
        }
        Channel entity = channelMng.findById(id);
        if (errors.ifNotExist(entity, Channel.class, id, false)) {
            return true;
        }
        return false;
    }

    private boolean validatePath(String path) {
        String pattern = "^[a-zA-Z0-9]+$";
        boolean result = Pattern.matches(pattern, path);
        return result;
    }

    @Autowired
    private ChannelMng channelMng;
    @Autowired
    private CmsLogMng cmsLogMng;
    @Autowired
    private CmsModelMng modelMng;
    @Autowired
    private CmsSiteMng siteMng;
}

