package com.bfly.admin.acquisition.action;

import com.bfly.cms.acquisition.entity.*;
import com.bfly.cms.acquisition.service.AcquisitionSvc;
import com.bfly.cms.acquisition.service.CmsAcquisitionHistoryMng;
import com.bfly.cms.acquisition.service.CmsAcquisitionMng;
import com.bfly.cms.acquisition.service.CmsAcquisitionTempMng;
import com.bfly.cms.logs.service.CmsLogMng;
import com.bfly.common.page.Pagination;
import com.bfly.common.util.StrUtils;
import com.bfly.common.web.ResponseUtils;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 采集管理Controller
 *
 * @author andy_hulibo@163.com
 * @date 2018/11/26 16:46
 */
@Controller
@RequestMapping(value = "/api/admin")
public class CmsAcquisitionApiAct extends BaseAdminController {
    private static final Logger log = LoggerFactory.getLogger(CmsAcquisitionApiAct.class);

    @RequestMapping("/acquisition/list")
    public void list(HttpServletRequest request, HttpServletResponse response) {
        List<CmsAcquisition> list = manager.getList();
        JSONArray jsonArray = new JSONArray();
        if (list != null && list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                jsonArray.put(i, list.get(i).convertToJson());
            }
        }
        String body = jsonArray.toString();
        ApiResponse apiResponse = ApiResponse.getSuccess(body);
        ResponseUtils.renderApiJson(response, request, apiResponse);
    }

    @RequestMapping("/acquisition/get")
    public void get(Integer id, HttpServletRequest request, HttpServletResponse response) {
        CmsAcquisition bean;
        if (id == null) {
            throw new ApiException("缺少参数", ResponseCode.API_CODE_PARAM_REQUIRED);
        }
        if (id.equals(0)) {
            bean = new CmsAcquisition();
        } else {
            bean = manager.findById(id);
        }
        if (bean == null) {
            throw new ApiException("参数错误", ResponseCode.API_CODE_NOT_FOUND);
        }
        bean.init();
        JSONArray replaces = new JSONArray();
        int i = 0;
        if (bean.getReplaceWords() != null) {
            for (CmsAcquisitionReplace replace : bean.getReplaceWords()) {
                replaces.put(i, replace.convertToJson());
                i++;
            }
        }
        JSONArray shields = new JSONArray();
        int j = 0;
        if (bean.getShields() != null) {
            for (CmsAcquisitionShield shield : bean.getShields()) {
                shields.put(j, shield.convertToJson());
                j++;
            }
        }
        JSONObject object = new JSONObject();
        object.put("acq", bean.convertToJson());
        object.put("replaces", replaces);
        object.put("shields", shields);
        String body = object.toString();
        ApiResponse apiResponse = ApiResponse.getSuccess(body);
        ResponseUtils.renderApiJson(response, request, apiResponse);
    }

    @SignValidate
    @RequestMapping("/acquisition/save")
    public void save(CmsAcquisition bean, Integer channelId, Integer typeId, HttpServletRequest request, HttpServletResponse response, String replaceArrs, String shieldArrs) {
        WebErrors errors = WebErrors.create(request);
        errors = ApiValidate.validateRequiredParams(request, errors, bean.getName(), channelId, typeId);
        if (errors.hasErrors()) {
            throw new ApiException("缺少参数", ResponseCode.API_CODE_PARAM_REQUIRED);
        }
        bean.init();
        String[] keywords = null;
        String[] replaceWords = null;
        String[] shieldStarts = null;
        String[] shieldEnds = null;
        //获取批量屏蔽数组
        List<String[]> shields = strForJson("shieldArrs", shieldArrs, shieldStarts, shieldEnds);
        shieldStarts = shields.get(0);
        shieldEnds = shields.get(1);
        //获取批量替换数组
        List<String[]> replaces = strForJson("replaceArrs", replaceArrs, keywords, replaceWords);
        keywords = replaces.get(0);
        replaceWords = replaces.get(1);
        bean = manager.save(bean, channelId, typeId, getAdmin().getId(), keywords, replaceWords, shieldStarts, shieldEnds);

        log.info("save CmsAcquisition id={}", bean.getId());

        cmsLogMng.operating(request, "cmsAcquisition.log.save", "id=" + bean.getId() + ";name=" + bean.getName());
        String body = "{\"id\":" + bean.getId() + "}";
        ApiResponse apiResponse = ApiResponse.getSuccess(body);
        ResponseUtils.renderApiJson(response, request, apiResponse);
    }

    @SignValidate
    @RequestMapping("/acquisition/update")
    public void update(CmsAcquisition bean, Integer channelId, Integer typeId, HttpServletResponse response, HttpServletRequest request, String replaceArrs, String shieldArrs) {
        WebErrors errors = WebErrors.create(request);
        errors = ApiValidate.validateRequiredParams(request, errors, bean.getId(), bean.getName(), channelId, typeId);
        if (errors.hasErrors()) {
            throw new ApiException("缺少参数", ResponseCode.API_CODE_PARAM_REQUIRED);
        }
        errors = validateUpdate(errors, bean.getId(), request);
        if (errors.hasErrors()) {
            throw new ApiException("参数错误", ResponseCode.API_CODE_PARAM_ERROR);
        }
        String[] keywords = null;
        String[] replaceWords = null;
        String[] shieldStarts = null;
        String[] shieldEnds = null;
        //获取批量屏蔽数组
        List<String[]> shields = strForJson("shieldArrs", shieldArrs, shieldStarts, shieldEnds);
        shieldStarts = shields.get(0);
        shieldEnds = shields.get(1);
        //获取批量替换数组
        List<String[]> replaces = strForJson("replaceArrs", replaceArrs, keywords, replaceWords);
        keywords = replaces.get(0);
        replaceWords = replaces.get(1);
        bean = manager.update(bean, channelId, typeId, keywords, replaceWords, shieldStarts, shieldEnds);

        log.info("update CmsAcquisition id={}.", bean.getId());

        cmsLogMng.operating(request, "cmsAcquisition.log.update", "id=" + bean.getId() + ";name=" + bean.getName());
        String body = "{\"id\":" + bean.getId() + "}";
        ApiResponse apiResponse = ApiResponse.getSuccess(body);
        ResponseUtils.renderApiJson(response, request, apiResponse);
    }

    @SignValidate
    @RequestMapping("/acquisition/delete")
    public void delete(String ids, HttpServletRequest request, HttpServletResponse response) {
        WebErrors errors = WebErrors.create(request);
        errors = ApiValidate.validateRequiredParams(request, errors, ids);
        if (errors.hasErrors()) {
            throw new ApiException("缺少参数", ResponseCode.API_CODE_PARAM_REQUIRED);
        }
        Integer[] idArr = StrUtils.getInts(ids);
        errors = validateArr(errors, idArr);
        if (errors.hasErrors()) {
            throw new ApiException("参数错误", ResponseCode.API_CODE_PARAM_ERROR);
        }
        try {
            CmsAcquisition[] beans = manager.deleteByIds(idArr);
            for (CmsAcquisition bean : beans) {
                log.info("delete CmsAcquisition id={}", bean.getId());
                cmsLogMng.operating(request, "cmsAcquisition.log.delete", "id=" + bean.getId() + ";name=" + bean.getName());
            }
        } catch (Exception e) {
            throw new ApiException("删除错误", ResponseCode.API_CODE_DELETE_ERROR);
        }
        ApiResponse apiResponse = ApiResponse.getSuccess();
        ResponseUtils.renderApiJson(response, request, apiResponse);
    }

    @SignValidate
    @RequestMapping("/acquisition/start")
    public void start(String ids, HttpServletRequest request, HttpServletResponse response) {
        WebErrors errors = WebErrors.create(request);
        errors = ApiValidate.validateRequiredParams(request, errors, ids);
        if (errors.hasErrors()) {
            throw new ApiException("缺少参数", ResponseCode.API_CODE_PARAM_REQUIRED);
        }
        Integer[] idArr = StrUtils.getInts(ids);
        errors = validateArr(errors, idArr);
        if (errors.hasErrors()) {
            throw new ApiException("参数错误", ResponseCode.API_CODE_PARAM_ERROR);
        }
        Integer queueNum = manager.hasStarted();
        if (queueNum == 0) {
            acquisitionSvc.start(idArr[0]);
        }
        manager.addToQueue(idArr, queueNum);
        log.info("start CmsAcquisition ids={}", Arrays.toString(idArr));
        ApiResponse apiResponse = ApiResponse.getSuccess();
        ResponseUtils.renderApiJson(response, request, apiResponse);
    }

    @SignValidate
    @RequestMapping("/acquisition/end")
    public void end(Integer id, HttpServletResponse response, HttpServletRequest request) {
        WebErrors errors = WebErrors.create(request);
        errors = ApiValidate.validateRequiredParams(request, errors, id);
        if (errors.hasErrors()) {
            throw new ApiException("缺少参数", ResponseCode.API_CODE_PARAM_REQUIRED);
        }
        errors = validateExist(errors, id);
        if (errors.hasErrors()) {
            throw new ApiException("参数错误", ResponseCode.API_CODE_PARAM_ERROR);
        }
        manager.end(id);
        CmsAcquisition acqu = manager.popAcquFromQueue();
        if (acqu != null) {
            acquisitionSvc.start(acqu.getId());
        }
        log.info("end CmsAcquisition id={}", id);
        String body = "{\"id\":" + id + "}";
        ApiResponse apiResponse = ApiResponse.getSuccess(body);
        ResponseUtils.renderApiJson(response, request, apiResponse);
    }

    @SignValidate
    @RequestMapping("/acquisition/pause")
    public void pause(Integer id, HttpServletRequest request, HttpServletResponse response) {
        WebErrors errors = WebErrors.create(request);
        errors = ApiValidate.validateRequiredParams(request, errors, id);
        if (errors.hasErrors()) {
            throw new ApiException("缺少参数", ResponseCode.API_CODE_PARAM_REQUIRED);
        }
        errors = validateExist(errors, id);
        if (errors.hasErrors()) {
            throw new ApiException("参数错误", ResponseCode.API_CODE_PARAM_ERROR);
        }
        manager.pause(id);
        log.info("pause CmsAcquisition id={}", id);
        String body = "{\"id\":" + id + "}";
        ApiResponse apiResponse = ApiResponse.getSuccess(body);
        ResponseUtils.renderApiJson(response, request, apiResponse);
    }

    @SignValidate
    @RequestMapping("/acquisition/cancel")
    public void cancel(Integer id, HttpServletRequest request, HttpServletResponse response) {
        WebErrors errors = WebErrors.create(request);
        errors = ApiValidate.validateRequiredParams(request, errors, id);
        if (errors.hasErrors()) {
            throw new ApiException("缺少参数", ResponseCode.API_CODE_PARAM_REQUIRED);
        }
        errors = validateExist(errors, id);
        if (errors.hasErrors()) {
            throw new ApiException("参数错误", ResponseCode.API_CODE_PARAM_ERROR);
        }
        manager.cancel(id);
        log.info("cancel CmsAcquisition id={}", id);
        String body = "{\"id\":" + id + "}";
        ApiResponse apiResponse = ApiResponse.getSuccess(body);
        ResponseUtils.renderApiJson(response, request, apiResponse);
    }

    /**
     * 采集进度
     *
     * @author andy_hulibo@163.com
     * @date 2018/11/26 16:55
     */
    @RequestMapping("/acquisition/progress_data")
    public void progressData(HttpServletRequest request, HttpServletResponse response) {
        CmsAcquisition bean = manager.getStarted();
        JSONObject json = new JSONObject();
        if (bean != null) {
            List<CmsAcquisitionTemp> list = cmsAcquisitionTempMng.getList();
            JSONArray jsonArray = new JSONArray();
            if (list != null) {
                for (int i = 0; i < list.size(); i++) {
                    jsonArray.put(i, createEasyJson(list.get(i)));
                }
            }
            Integer percent = cmsAcquisitionTempMng.getPercent();
            json.put("list", jsonArray);
            json.put("acquisition", createEasyAcq(bean));
            json.put("percent", percent);
            json.put("havaAcquisition", true);
        } else {
            json.put("havaAcquisition", false);
            cmsAcquisitionTempMng.clear();
        }
        ApiResponse apiResponse = ApiResponse.getSuccess(json.toString());
        ResponseUtils.renderApiJson(response, request, apiResponse);
    }

    private JSONObject createEasyAcq(CmsAcquisition bean) {
        JSONObject json = new JSONObject();
        if (bean != null) {
            if (StringUtils.isNotBlank(bean.getName())) {
                json.put("name", bean.getName());
            } else {
                json.put("name", "");
            }
            json.put("totalNum", bean.getTotalNum());
            if (bean.getCurrNum() != null) {
                json.put("currNum", bean.getCurrNum());
            } else {
                json.put("currNum", "");
            }
        }
        return json;
    }

    /**
     * 采集历史记录
     *
     * @author andy_hulibo@163.com
     * @date 2018/11/26 16:55
     */
    @RequestMapping("/acquisition/history")
    public void history(Integer acquId, Integer pageNo, Integer pageSize, HttpServletRequest request, HttpServletResponse response) {
        if (pageNo == null) {
            pageNo = 1;
        }
        if (pageSize == null) {
            pageSize = 10;
        }
        Pagination page = cmsAcquisitionHistoryMng.getPage(acquId, pageNo, pageSize);
        int totalCount = page.getTotalCount();
        List<CmsAcquisitionHistory> list = (List<CmsAcquisitionHistory>) page.getList();
        JSONArray jsonArray = new JSONArray();
        if (list != null) {
            for (int i = 0; i < list.size(); i++) {
                jsonArray.put(i, list.get(i).convertToJson());
            }
        }
        String body = jsonArray.toString() + ",\"totalCount\":" + totalCount;
        ApiResponse apiResponse = ApiResponse.getSuccess(body);
        ResponseUtils.renderApiJson(response, request, apiResponse);
    }

    @SignValidate
    @RequestMapping("/acquisition/history_delete")
    public void historyDelete(String ids, HttpServletRequest request, HttpServletResponse response) {
        WebErrors errors = WebErrors.create(request);
        Integer[] idArr = StrUtils.getInts(ids);
        errors = validateHistoryDelete(errors, idArr);
        if (errors.hasErrors()) {
            throw new ApiException("参数错误", ResponseCode.API_CODE_PARAM_ERROR);
        }
        try {
            CmsAcquisitionHistory[] beans = cmsAcquisitionHistoryMng.deleteByIds(idArr);
            for (CmsAcquisitionHistory bean : beans) {
                log.info("delete CmsAcquisitionHistory id={}", bean.getId());
                cmsLogMng.operating(request, "cmsAcquisitionHistory.log.delete", "id=" + bean.getId() + ";name=" + bean.getTitle());
            }
        } catch (Exception e) {
            throw new ApiException("删除错误", ResponseCode.API_CODE_DELETE_ERROR);
        }
        ApiResponse apiResponse = ApiResponse.getSuccess();
        ResponseUtils.renderApiJson(response, request, apiResponse);
    }

    private JSONObject createEasyJson(CmsAcquisitionTemp temp) {
        JSONObject json = new JSONObject();
        if (temp.getSeq() != null) {
            json.put("seq", temp.getSeq());
        } else {
            json.put("seq", "");
        }
        if (StringUtils.isNotBlank(temp.getContentUrl())) {
            json.put("contentUrl", temp.getContentUrl());
        } else {
            json.put("contentUrl", "");
        }
        if (StringUtils.isNotBlank(temp.getTitle())) {
            json.put("title", temp.getTitle());
        } else {
            json.put("title", "");
        }
        if (StringUtils.isNotBlank(temp.getDescription())) {
            json.put("description", temp.getDescription());
        } else {
            json.put("description", "");
        }
        return json;
    }

    private WebErrors validateHistoryDelete(WebErrors errors, Integer[] ids) {
        if (errors.ifEmpty(ids, "ids", false)) {
            return errors;
        }
        for (Integer id : ids) {
            vldHistoryExist(id, errors);
        }
        return errors;
    }

    private boolean vldHistoryExist(Integer id, WebErrors errors) {
        if (errors.ifNull(id, "id", false)) {
            return true;
        }
        CmsAcquisitionHistory entity = cmsAcquisitionHistoryMng.findById(id);
        return errors.ifNotExist(entity, CmsAcquisitionHistory.class, id, false);
    }

    private WebErrors validateExist(WebErrors errors, Integer id) {
        if (id != null) {
            vldExist(id, errors);
        }
        return errors;
    }

    private WebErrors validateArr(WebErrors errors, Integer[] ids) {
        if (errors.ifEmpty(ids, "ids", false)) {
            return errors;
        }
        for (Integer id : ids) {
            vldExist(id, errors);
        }
        return errors;
    }

    private WebErrors validateUpdate(WebErrors errors, Integer id, HttpServletRequest request) {
        vldExist(id, errors);
        return errors;
    }

    private boolean vldExist(Integer id, WebErrors errors) {
        if (errors.ifNull(id, "id", false)) {
            return true;
        }
        CmsAcquisition entity = manager.findById(id);
        if (errors.ifNotExist(entity, CmsAcquisition.class, id, false)) {
            return true;
        }
        return false;
    }

    private List<String[]> strForJson(String flag, String str, String[] start, String[] end) {
        List<String[]> list = new ArrayList<>();
        if (StringUtils.isNotBlank(str)) {
            net.sf.json.JSONArray array = net.sf.json.JSONArray.fromObject(str);
            start = new String[array.size()];
            end = new String[array.size()];
            for (int i = 0; i < array.size(); i++) {
                net.sf.json.JSONObject object = array.getJSONObject(i);
                if ("shieldArrs".equals(flag)) {
                    start[i] = object.getString("shieldStart");
                    end[i] = object.getString("shieldEnd");
                } else {
                    start[i] = object.getString("keyword");
                    end[i] = object.getString("replaceWord");
                }
            }
        }
        list.add(start);
        list.add(end);
        return list;
    }

    @Autowired
    private AcquisitionSvc acquisitionSvc;
    @Autowired
    private CmsLogMng cmsLogMng;
    @Autowired
    private CmsAcquisitionMng manager;
    @Autowired
    private CmsAcquisitionHistoryMng cmsAcquisitionHistoryMng;
    @Autowired
    private CmsAcquisitionTempMng cmsAcquisitionTempMng;
}
