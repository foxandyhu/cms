package com.bfly.admin.content.action;

import com.bfly.cms.channel.entity.Channel;
import com.bfly.cms.channel.service.ChannelMng;
import com.bfly.cms.content.entity.*;
import com.bfly.cms.content.entity.Content.CheckResultStatus;
import com.bfly.cms.content.entity.Content.ContentStatus;
import com.bfly.cms.content.entity.ContentRecord.ContentOperateType;
import com.bfly.cms.content.service.CmsModelMng;
import com.bfly.cms.content.service.CmsTopicMng;
import com.bfly.cms.content.service.ContentMng;
import com.bfly.cms.content.service.ContentTypeMng;
import com.bfly.cms.logs.service.CmsLogMng;
import com.bfly.cms.resource.service.CmsFileMng;
import com.bfly.cms.resource.service.ImageSvc;
import com.bfly.cms.siteconfig.entity.CmsSite;
import com.bfly.cms.siteconfig.entity.Ftp;
import com.bfly.cms.siteconfig.service.CmsSiteMng;
import com.bfly.cms.siteconfig.service.FtpMng;
import com.bfly.cms.staticpage.ContentStatusChangeThread;
import com.bfly.cms.staticpage.FtpDeleteThread;
import com.bfly.cms.user.entity.CmsAdmin;
import com.bfly.cms.user.entity.CmsUser;
import com.bfly.cms.user.service.CmsUserMng;
import com.bfly.cms.weixin.entity.Weixin;
import com.bfly.cms.weixin.service.WeiXinSvc;
import com.bfly.common.image.ImageUtils;
import com.bfly.common.page.Pagination;
import com.bfly.common.util.DateUtils;
import com.bfly.common.util.StrUtils;
import com.bfly.common.web.RequestUtils;
import com.bfly.common.web.ResponseUtils;
import com.bfly.common.web.springmvc.MessageResolver;
import com.bfly.common.web.springmvc.RealPathResolver;
import com.bfly.core.Constants;
import com.bfly.core.annotation.SignValidate;
import com.bfly.core.base.action.BaseAdminController;
import com.bfly.core.exception.ApiException;
import com.bfly.core.web.ApiResponse;
import com.bfly.core.web.ApiValidate;
import com.bfly.core.web.ResponseCode;
import com.bfly.core.web.WebErrors;
import com.bfly.core.web.util.CmsUtils;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.json.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 系统内容Controller
 *
 * @author andy_hulibo@163.com
 * @date 2018/11/26 14:04
 */
@Controller("adminContentApiAct")
@RequestMapping(value = "/api/admin")
public class ContentAdminApiAct extends BaseAdminController {

    private static final Logger log = LoggerFactory.getLogger(ContentAdminApiAct.class);

    /**
     * 树状内容栏目
     *
     * @author andy_hulibo@163.com
     * @date 2018/11/26 14:05
     */
    @RequestMapping("/content/tree")
    public void tree(Boolean hasContent, Integer https, HttpServletRequest request, HttpServletResponse response) {
        if (hasContent == null) {
            hasContent = true;
        }
        if (https == null) {
            https = Constants.URL_HTTP;
        }
        List<Channel> list = channelMng.getTopList(hasContent);
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
     * @param queryTitle     查询条件-标题
     * @param queryShare     查询条件-共享内容
     * @param queryStatus    查询条件-内容状态(
     *                       本站内容all 投稿contribute
     *                       草稿draft 待审prepared 已审passed 终审checked
     *                       退回rejected 归档pigeonhole)
     * @param queryTypeId    查询条件-类型(普通1 图文2 焦点3 头条4)
     * @param queryTopLevel  查询条件-固顶
     * @param queryRecommend 查询条件-推荐
     * @param queryOrderBy   查询条件-排序 0-23
     * @param cid            查询条件-板块编号
     * @param pageNo
     * @param pageSize
     * @param format         返回完整数据(等于0或为空时)
     * @param https
     * @param hasCollect
     * @param request
     * @param response
     * @author andy_hulibo@163.com
     * @date 2018/11/26 14:05
     * 内容列表
     */
    @RequestMapping("/content/list")
    public void list(String queryTitle, Integer queryShare, String queryStatus, Integer queryTypeId, Boolean queryTopLevel, Boolean queryRecommend, Integer queryOrderBy, Integer cid, Integer pageNo, Integer pageSize, Integer format, Integer https, Boolean hasCollect, Boolean txtImgWhole, Boolean trimHtml, HttpServletRequest request, HttpServletResponse response) {
        queryTitle = StringUtils.trim(queryTitle);
        String queryInputUsername = RequestUtils.getQueryParam(request, "queryInputUsername");
        queryInputUsername = StringUtils.trim(queryInputUsername);
        if (format == null) {
            format = 0;
        }
        if (https == null) {
            https = Constants.URL_HTTP;
        }
        if (hasCollect == null) {
            hasCollect = false;
        }
        if (pageNo == null) {
            pageNo = 1;
        }
        if (pageSize == null) {
            pageSize = 10;
        }
        if (queryTopLevel == null) {
            queryTopLevel = false;
        }
        if (queryRecommend == null) {
            queryRecommend = false;
        }
        if (queryOrderBy == null) {
            queryOrderBy = 4;
        }
        if (queryShare == null) {
            queryShare = 0;
        }
        if (cid != null && cid.equals(0)) {
            cid = null;
        }
        if (txtImgWhole == null) {
            txtImgWhole = false;
        }
        if (trimHtml == null) {
            trimHtml = false;
        }
        ContentStatus contentStatus;
        if (!StringUtils.isBlank(queryStatus)) {
            contentStatus = ContentStatus.valueOf(queryStatus);
        } else {
            contentStatus = ContentStatus.all;
        }
        Integer queryInputUserId;
        if (!StringUtils.isBlank(queryInputUsername)) {
            CmsUser u = cmsUserMng.findByUsername(queryInputUsername);
            if (u != null) {
                queryInputUserId = u.getId();
            } else {
                queryInputUserId = null;
            }
        } else {
            queryInputUserId = 0;
        }
        Pagination page = manager.getPageByRight(queryShare, queryTitle, queryTypeId, getAdmin().getId(), queryInputUserId, queryTopLevel, queryRecommend, contentStatus, getAdmin().getCheckStep(), cid, getAdmin().getId(), queryOrderBy, pageNo, pageSize);
        List<Content> list = (List<Content>) page.getList();
        JSONArray jsonArray = new JSONArray();
        if (list != null && list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                jsonArray.put(i, list.get(i).convertToJson(format, https, hasCollect, true, txtImgWhole, trimHtml));
            }
        }
        String body = jsonArray.toString();
        ApiResponse apiResponse = ApiResponse.getSuccess(body);
        ResponseUtils.renderApiJson(response, request, apiResponse);
    }

    /**
     * 内容分页
     *
     * @author andy_hulibo@163.com
     * @date 2018/11/26 14:05
     */
    @RequestMapping("/content/page")
    public void page(String queryTitle, Integer queryShare, String queryStatus, Integer queryTypeId, Boolean queryTopLevel, Boolean queryRecommend, Integer queryOrderBy, Integer cid, Integer pageNo, Integer pageSize, HttpServletRequest request, HttpServletResponse response) {
        queryTitle = StringUtils.trim(queryTitle);
        String queryInputUsername = RequestUtils.getQueryParam(request, "queryInputUsername");
        queryInputUsername = StringUtils.trim(queryInputUsername);
        if (pageNo == null) {
            pageNo = 1;
        }
        if (pageSize == null) {
            pageSize = 10;
        }
        if (queryTopLevel == null) {
            queryTopLevel = false;
        }
        if (queryRecommend == null) {
            queryRecommend = false;
        }
        if (queryOrderBy == null) {
            queryOrderBy = 4;
        }
        if (queryShare == null) {
            queryShare = 0;
        }
        ContentStatus contentStatus;
        if (!StringUtils.isBlank(queryStatus)) {
            contentStatus = ContentStatus.valueOf(queryStatus);
        } else {
            contentStatus = ContentStatus.all;
        }
        Integer queryInputUserId;
        if (!StringUtils.isBlank(queryInputUsername)) {
            CmsUser u = cmsUserMng.findByUsername(queryInputUsername);
            if (u != null) {
                queryInputUserId = u.getId();
            } else {
                queryInputUserId = null;
            }
        } else {
            queryInputUserId = 0;
        }
        Pagination p = manager.getPageCountByRight(queryShare, queryTitle, queryTypeId, getAdmin().getId(), queryInputUserId, queryTopLevel, queryRecommend, contentStatus, getAdmin().getCheckStep(), cid, getAdmin().getId(), queryOrderBy, pageNo, pageSize);
        JSONObject json = new JSONObject();
        json.put("pageNo", p.getPageNo());
        json.put("pageSize", p.getPageSize());
        json.put("totalCount", p.getTotalCount());
        json.put("totalPage", p.getTotalPage());
        json.put("firstPage", p.isFirstPage());
        json.put("lastPage", p.isLastPage());
        json.put("prePage", p.getPrePage());
        json.put("nextPage", p.getNextPage());
        String body = json.toString();
        ApiResponse apiResponse = ApiResponse.getSuccess(body);
        ResponseUtils.renderApiJson(response, request, apiResponse);
    }

    /**
     * 内容详情
     *
     * @author andy_hulibo@163.com
     * @date 2018/11/26 14:06
     */
    @RequestMapping("/content/get")
    public void get(Integer id, Integer format, Integer https, Boolean hasCollect, Boolean txtImgWhole, Boolean trimHtml, HttpServletRequest request, HttpServletResponse response) {
        if (id == null) {
            throw new ApiException("参数错误", ResponseCode.API_CODE_PARAM_ERROR);
        }
        if (format == null) {
            format = 0;
        }
        if (https == null) {
            https = Constants.URL_HTTP;
        }
        if (hasCollect == null) {
            hasCollect = false;
        }
        if (txtImgWhole == null) {
            txtImgWhole = false;
        }
        if (trimHtml == null) {
            trimHtml = false;
        }
        Content bean;
        if (id.equals(0)) {
            bean = new Content();
            if (bean.getSite() == null) {
                bean.setSite(CmsUtils.getSite(request));
            }
        } else {
            bean = manager.findById(id);
        }
        if (bean == null) {
            throw new ApiException("参数错误", ResponseCode.API_CODE_PARAM_ERROR);
        }
        bean.init();
        String body = bean.convertToJson(format, https, hasCollect, false, txtImgWhole, trimHtml).toString();
        ApiResponse apiResponse = ApiResponse.getSuccess(body);
        ResponseUtils.renderApiJson(response, request, apiResponse);
    }

    /**
     * 查看内容
     *
     * @author andy_hulibo@163.com
     * @date 2018/11/26 14:07
     */
    @RequestMapping("/content/view")
    public void view(Integer id, HttpServletRequest request, HttpServletResponse response) {
        WebErrors errors = WebErrors.create(request);
        errors = ApiValidate.validateRequiredParams(request, errors, id);
        if (errors.hasErrors()) {
            throw new ApiException("缺少参数", ResponseCode.API_CODE_PARAM_REQUIRED);
        }
        Content bean = manager.findById(id);
        if (bean == null) {
            throw new ApiException("参数错误", ResponseCode.API_CODE_NOT_FOUND);
        }
        String body = createViewJson(bean).toString();
        ApiResponse apiResponse = ApiResponse.getSuccess(body);
        ResponseUtils.renderApiJson(response, request, apiResponse);
    }

    /**
     * 发表内容
     *
     * @author andy_hulibo@163.com
     * @date 2018/11/26 14:07
     */
    @SignValidate
    @RequestMapping("/content/save")
    public void save(Content bean, ContentExt ext, ContentTxt txt, Boolean copyimg, String channelIds, String topicIds, String viewGroupIds, String attachmentPaths, String attachmentNames, String picPaths, String picDescs, Integer channelId, Integer typeId, String tagStr, Boolean draft, Integer cid, Integer modelId, Short charge, Double chargeAmount, Boolean rewardPattern, Double rewardRandomMin, Double rewardRandomMax, String rewardFix, HttpServletRequest request, HttpServletResponse response) {
        WebErrors errors = WebErrors.create(request);
        errors = ApiValidate.validateRequiredParams(request, errors, ext.getTitle(), typeId, modelId, channelId);
        if (errors.hasErrors()) {
            throw new ApiException("缺少参数", ResponseCode.API_CODE_PARAM_REQUIRED);
        }
        errors = validateSave(channelId, modelId, typeId, request);
        // 加上模板前缀
        if (errors.hasErrors()) {
            throw new ApiException("错误", ResponseCode.API_CODE_CALL_FAIL);
        }
        CmsSite site = siteMng.getSite();
        bean.setSite(site);
        String tplPath = site.getTplPath();
        if (!StringUtils.isBlank(ext.getTplContent())) {
            ext.setTplContent(tplPath + ext.getTplContent());
        }
        if (!StringUtils.isBlank(ext.getTplMobileContent())) {
            ext.setTplMobileContent(tplPath + ext.getTplMobileContent());
        }
        bean.setAttr(RequestUtils.getRequestMap(request, "attr_"));
        String[] tagArr = StrUtils.splitAndTrim(tagStr, ",", MessageResolver.getMessage(request, "content.tagStr.split"));
        if (txt != null && copyimg != null && copyimg) {
            txt = copyContentTxtImg(txt, site);
        }
        Integer[] channelArr = StrUtils.getInts(channelIds);
        Integer[] topicArr = StrUtils.getInts(topicIds);
        Integer[] viewGroupArr = StrUtils.getInts(viewGroupIds);
        String[] pathArr = getStringArr(attachmentPaths);
        String[] nameArr = getStringArr(attachmentNames);
        String[] filenameArr = nameArr;
        String[] picPathArr = getStringArr(picPaths);
        int picLength = 0;
        if (picPathArr != null) {
            picLength = picPathArr.length;
        }
        String[] picDesArr = getPicDescStringArr(picDescs, picLength);
        Double[] rewardArr = getDoubleArr(rewardFix);
        bean.init();
        if (modelId != null) {
            bean.setModel(modelMng.findById(modelId));
        }
        bean = manager.save(bean, ext, txt, channelArr, topicArr, viewGroupArr, tagArr, pathArr, nameArr, filenameArr, picPathArr, picDesArr, channelId, typeId, draft, false, charge, chargeAmount, rewardPattern, rewardRandomMin, rewardRandomMax, rewardArr, getAdmin(), false);
        fileMng.updateFileByPaths(pathArr, picPathArr, ext.getMediaPath(), ext.getTitleImg(), ext.getTypeImg(), ext.getContentImg(), true, bean);
        log.info("save Content id={}", bean.getId());
        cmsLogMng.operating(request, "content.log.save", "id=" + bean.getId() + ";title=" + bean.getTitle());
        afterContentStatusChange(bean, null, ContentStatusChangeThread.OPERATE_ADD);
        String body = easyJson(bean).toString();
        ApiResponse apiResponse = ApiResponse.getSuccess(body);
        ResponseUtils.renderApiJson(response, request, apiResponse);
    }

    /**
     * 修改内容
     *
     * @author andy_hulibo@163.com
     * @date 2018/11/26 14:07
     */
    @SignValidate
    @RequestMapping("/content/update")
    public void update(Content bean, ContentExt ext, String tagStr, ContentTxt txt, Integer modelId, Boolean copyimg, String channelIds, String topicIds, String viewGroupIds, String attachmentPaths, String attachmentNames, String picPaths, String picDescs, Integer channelId, Integer typeId, Boolean draft, String oldattachmentPaths, String oldpicPaths, String oldTitleImg, String oldContentImg, String oldTypeImg, Short charge, Double chargeAmount, Boolean rewardPattern, Double rewardRandomMin, Double rewardRandomMax, String rewardFix, HttpServletRequest request, HttpServletResponse response) {
        WebErrors errors = validateUpdate(bean.getId(), request);
        if (errors.hasErrors()) {
            throw new ApiException("参数错误", ResponseCode.API_CODE_PARAM_ERROR);
        }
        // 加上模板前缀
        CmsSite site = siteMng.getSite();
        CmsAdmin admin = getAdmin();
        String tplPath = site.getTplPath();
        if (!StringUtils.isBlank(ext.getTplContent())) {
            ext.setTplContent(tplPath + ext.getTplContent());
        }
        if (!StringUtils.isBlank(ext.getTplMobileContent())) {
            ext.setTplMobileContent(tplPath + ext.getTplMobileContent());
        }
        String[] tagArr = StrUtils.splitAndTrim(tagStr, ",", MessageResolver.getMessage(request, "content.tagStr.split"));
        Map<String, String> attr = RequestUtils.getRequestMap(request, "attr_");
        if (txt != null && copyimg != null && copyimg) {
            txt = copyContentTxtImg(txt, site);
        }
        List<Map<String, Object>> list = manager.preChange(manager.findById(bean.getId()));
        Integer[] channelArr = StrUtils.getInts(channelIds);
        Integer[] topicArr = StrUtils.getInts(topicIds);
        Integer[] viewGroupArr = StrUtils.getInts(viewGroupIds);
        String[] pathArr = getStringArr(attachmentPaths);
        String[] nameArr = getStringArr(attachmentNames);
        String[] picPathArr = getStringArr(picPaths);
        int picLength = 0;
        if (picPathArr != null) {
            picLength = picPathArr.length;
        }
        String[] picDesArr = getPicDescStringArr(picDescs, picLength);
        String[] oldattachmentPathArr = getStringArr(oldattachmentPaths);
        String[] oldpicPathArr = getStringArr(oldpicPaths);
        Double[] rewardArr = getDoubleArr(rewardFix);
        if (modelId != null) {
            bean.setModel(modelMng.findById(modelId));
        }
        bean = manager.update(bean, ext, txt, tagArr, channelArr, topicArr, viewGroupArr, pathArr, nameArr, nameArr, picPathArr, picDesArr, attr, channelId, typeId, draft, charge, chargeAmount, rewardPattern, rewardRandomMin, rewardRandomMax, rewardArr, admin, false);
        afterContentStatusChange(bean, list, ContentStatusChangeThread.OPERATE_UPDATE);
        //处理之前的附件有效性
        fileMng.updateFileByPaths(oldattachmentPathArr, oldpicPathArr, null, oldTitleImg, oldTypeImg, oldContentImg, false, bean);
        //处理更新后的附件有效性
        fileMng.updateFileByPaths(pathArr, picPathArr, ext.getMediaPath(), ext.getTitleImg(), ext.getTypeImg(), ext.getContentImg(), true, bean);
        log.info("update Content id={}.", bean.getId());
        cmsLogMng.operating(request, "content.log.update", "id=" + bean.getId()
                + ";title=" + bean.getTitle());
        String body = easyJson(bean).toString();
        ApiResponse apiResponse = ApiResponse.getSuccess(body);
        ResponseUtils.renderApiJson(response, request, apiResponse);
    }

    /**
     * 删除内容
     *
     * @author andy_hulibo@163.com
     * @date 2018/11/26 14:08
     */
    @SignValidate
    @RequestMapping("/content/delete")
    public void delete(String ids, HttpServletRequest request, HttpServletResponse response) {
        WebErrors errors = WebErrors.create(request);
        CmsSite site = siteMng.getSite();
        errors = ApiValidate.validateRequiredParams(request, errors, ids);
        if (errors.hasErrors()) {
            throw new ApiException("缺少参数", ResponseCode.API_CODE_PARAM_REQUIRED);
        }
        Integer[] idArr = StrUtils.getInts(ids);
        errors = validateDelete(idArr, request);
        if (errors.hasErrors()) {
            throw new ApiException("参数错误", ResponseCode.API_CODE_PARAM_ERROR);
        }
        Content[] beans;
        // 是否开启回收站
        if (site.getResycleOn()) {
            Map<Integer, List<Map<String, Object>>> map = new HashMap<>(5);
            for (Integer id : idArr) {
                List<Map<String, Object>> list = manager.preChange(manager.findById(id));
                map.put(id, list);
            }
            beans = manager.cycle(getAdmin(), idArr);
            for (Content bean : beans) {
                afterContentStatusChange(bean, map.get(bean.getId()), ContentStatusChangeThread.OPERATE_UPDATE);
                log.info("delete to cycle, Content id={}", bean.getId());
            }
        } else {
            Map<Integer, List<Map<String, Object>>> map = new HashMap<>(5);
            for (Integer id : idArr) {
                Content c = manager.findById(id);
                //处理附件
                manager.updateFileByContent(c, false);
                List<Map<String, Object>> list = manager.preChange(manager.findById(c.getId()));
                map.put(id, list);
            }
            beans = manager.deleteByIds(idArr);

            //静态页与ftp删除
            String real;
            String ftpPath = "";
            String pcFtpPath = "";
            Ftp syncPageFtp;
            syncPageFtp = site.getSyncPageFtp();
            if (syncPageFtp != null) {
                syncPageFtp = ftpMng.findById(syncPageFtp.getId());
            }
            for (Content bean : beans) {
                //删除静态页
                int totalPage = bean.getPageCount();
                for (int pageNo = 1; pageNo <= totalPage; pageNo++) {
                    //判断是否手机模板
                    if (site.getMobileStaticSync()) {
                        real = realPathResolver.get(bean.getMobileStaticFilename(pageNo));
                    } else {
                        real = realPathResolver.get(bean.getStaticFilename(pageNo));
                    }
                    File f = new File(real);
                    if (f.exists()) {
                        f.delete();
                    }
                    deleteStatic(site, bean, pageNo, ftpPath, pcFtpPath, syncPageFtp);
                }
                log.info("delete Content id={}", bean.getId());
                afterContentStatusChange(bean, map.get(bean.getId()), ContentStatusChangeThread.OPERATE_DEL);
                cmsLogMng.operating(request, "content.log.delete", "id=" + bean.getId() + ";title=" + bean.getTitle());
            }
        }
        ApiResponse apiResponse = ApiResponse.getSuccess();
        ResponseUtils.renderApiJson(response, request, apiResponse);
    }

    /**
     * 审核
     *
     * @author andy_hulibo@163.com
     * @date 2018/11/26 14:15
     */
    @SignValidate
    @RequestMapping("/content/check")
    public void check(String ids, HttpServletRequest request, HttpServletResponse response) {
        WebErrors errors = WebErrors.create(request);
        errors = ApiValidate.validateRequiredParams(request, errors, ids);
        if (errors.hasErrors()) {
            throw new ApiException("缺少参数", ResponseCode.API_CODE_PARAM_REQUIRED);
        }
        Integer[] idArr = StrUtils.getInts(ids);
        errors = validateContent(errors, idArr, request);
        if (errors.hasErrors()) {
            throw new ApiException("参数错误", ResponseCode.API_CODE_PARAM_ERROR);
        }
        Map<Integer, List<Map<String, Object>>> map = new HashMap<>(5);
        for (Integer id : idArr) {
            List<Map<String, Object>> list = manager.preChange(manager.findById(id));
            map.put(id, list);
        }
        Content[] beans = manager.check(idArr, getAdmin());
        boolean ckFlag = true;
        for (Content bean : beans) {
            if (bean.getCheckResult() != CheckResultStatus.nopass) {
                afterContentStatusChange(bean, map.get(bean.getId()),
                        ContentStatusChangeThread.OPERATE_UPDATE);
                log.info("check Content id={}", bean.getId());
            } else {
                ckFlag = false;
                log.info("check Content id={} is nopass", bean.getId());
            }
        }
        if (!ckFlag) {
            //未审核成功统一返回 审核失败
            throw new ApiException("审核失败", ResponseCode.API_CODE_CHECK_ERROR);
        }
        String body = "{\"result\":" + true + "}";
        ApiResponse apiResponse = ApiResponse.getSuccess(body);
        ResponseUtils.renderApiJson(response, request, apiResponse);
    }

    /**
     * 生成静态页
     *
     * @author andy_hulibo@163.com
     * @date 2018/11/26 14:15
     */
    @SignValidate
    @RequestMapping("/content/static")
    public void contentStatic(String ids, HttpServletRequest request, HttpServletResponse response) {
        WebErrors errors = WebErrors.create(request);
        errors = ApiValidate.validateRequiredParams(request, errors, ids);
        if (errors.hasErrors()) {
            throw new ApiException("缺少参数", ResponseCode.API_CODE_PARAM_REQUIRED);
        }
        Integer[] idArr = StrUtils.getInts(ids);
        errors = validateContent(errors, idArr, request);
        if (errors.hasErrors()) {
            throw new ApiException("参数错误", ResponseCode.API_CODE_CALL_FAIL);
        }
        try {
            Content[] beans = manager.contentStatic(getAdmin(), idArr);
            for (Content bean : beans) {
                log.info("static Content id={}", bean.getId());
            }
        } catch (Exception e) {
            throw new ApiException("系统异常", ResponseCode.API_CODE_CALL_FAIL);
        }
        String body = "{\"result\":" + true + "}";
        ApiResponse apiResponse = ApiResponse.getSuccess(body);
        ResponseUtils.renderApiJson(response, request, apiResponse);
    }

    /**
     * 退回
     *
     * @author andy_hulibo@163.com
     * @date 2018/11/26 14:08
     */
    @SignValidate
    @RequestMapping("/content/reject")
    public void reject(String ids, String rejectOpinion, Byte rejectStep, HttpServletRequest request, HttpServletResponse response) {
        WebErrors errors = WebErrors.create(request);
        errors = ApiValidate.validateRequiredParams(request, errors, ids);
        if (errors.hasErrors()) {
            throw new ApiException("缺少参数", ResponseCode.API_CODE_PARAM_REQUIRED);
        }
        Integer[] idArr = StrUtils.getInts(ids);
        errors = validateContent(errors, idArr, request);
        if (errors.hasErrors()) {
            throw new ApiException("参数错误", ResponseCode.API_CODE_CALL_FAIL);
        }
        Map<Integer, List<Map<String, Object>>> map = new HashMap<>(5);
        for (Integer id : idArr) {
            List<Map<String, Object>> list = manager.preChange(manager.findById(id));
            map.put(id, list);
        }
        if (rejectStep == null) {
            rejectStep = getAdmin().getCheckStep();
        }
        Content[] beans = manager.reject(idArr, getAdmin(), rejectStep, rejectOpinion);
        for (Content bean : beans) {
            afterContentStatusChange(bean, map.get(bean.getId()), ContentStatusChangeThread.OPERATE_UPDATE);
            log.info("reject Content id={}", bean.getId());
        }
        String body = "{\"result\":" + true + "}";
        ApiResponse apiResponse = ApiResponse.getSuccess(body);
        ResponseUtils.renderApiJson(response, request, apiResponse);
    }

    /**
     * 提交
     *
     * @author andy_hulibo@163.com
     * @date 2018/11/26 14:09
     */
    @SignValidate
    @RequestMapping("/content/submit")
    public void submit(String ids, HttpServletRequest request, HttpServletResponse response) {
        WebErrors errors = WebErrors.create(request);
        errors = ApiValidate.validateRequiredParams(request, errors, ids);
        if (errors.hasErrors()) {
            throw new ApiException("缺少参数", ResponseCode.API_CODE_PARAM_REQUIRED);
        }
        Integer[] idArr = StrUtils.getInts(ids);
        errors = validateContent(errors, idArr, request);
        if (errors.hasErrors()) {
            throw new ApiException("参数错误", ResponseCode.API_CODE_CALL_FAIL);
        }
        try {
            Content[] beans = manager.submit(idArr, getAdmin());
            for (Content bean : beans) {
                log.info("submit Content id={}", bean.getId());
            }
        } catch (Exception e) {
            log.error("提交出错", e);
            throw new ApiException("提交出错", ResponseCode.API_CODE_CALL_FAIL);
        }
        String body = "{\"result\":" + true + "}";
        ApiResponse apiResponse = ApiResponse.getSuccess(body);
        ResponseUtils.renderApiJson(response, request, apiResponse);
    }

    /**
     * 移动
     *
     * @author andy_hulibo@163.com
     * @date 2018/11/26 14:09
     */
    @SignValidate
    @RequestMapping("/content/move")
    public void move(String ids, Integer channelId, HttpServletResponse response, HttpServletRequest request) {
        WebErrors errors = WebErrors.create(request);
        errors = ApiValidate.validateRequiredParams(request, errors, ids, channelId);
        if (errors.hasErrors()) {
            throw new ApiException("缺少参数", ResponseCode.API_CODE_PARAM_REQUIRED);
        }
        Integer[] idArr = StrUtils.getInts(ids);
        errors = validateContent(errors, idArr, request);
        if (errors.hasErrors()) {
            throw new ApiException("参数错误", ResponseCode.API_CODE_CALL_FAIL);
        }
        Channel channel = channelMng.findById(channelId);
        if (channel == null) {
            throw new ApiException("参数错误", ResponseCode.API_CODE_NOT_FOUND);
        }
        for (Integer contentId : idArr) {
            Content bean = manager.findById(contentId);
            if (bean != null) {
                bean.removeSelfAddToChannels(channelMng.findById(channelId));
                bean.setChannel(channel);
                manager.update(getAdmin(), bean, ContentOperateType.move);
            }
        }
        String body = "{\"result\":" + true + "}";
        ApiResponse apiResponse = ApiResponse.getSuccess(body);
        ResponseUtils.renderApiJson(response, request, apiResponse);
    }

    /**
     * 复制
     *
     * @author andy_hulibo@163.com
     * @date 2018/11/26 14:09
     */
    @SignValidate
    @RequestMapping("/content/copy")
    public void copy(String ids, Integer channelId, HttpServletRequest request, HttpServletResponse response) {
        WebErrors errors = WebErrors.create(request);
        errors = ApiValidate.validateRequiredParams(request, errors, ids, channelId);
        if (errors.hasErrors()) {
            throw new ApiException("缺少参数", ResponseCode.API_CODE_PARAM_REQUIRED);
        }
        Channel channel = channelMng.findById(channelId);
        if (channel == null) {
            throw new ApiException("参数错误", ResponseCode.API_CODE_NOT_FOUND);
        }
        Integer[] idArr = StrUtils.getInts(ids);
        errors = validateContent(errors, idArr);
        if (errors.hasErrors()) {
            throw new ApiException("参数错误", ResponseCode.API_CODE_NOT_FOUND);
        }
        for (Integer contentId : idArr) {
            Content bean = manager.findById(contentId);
            ContentExt extCopy = new ContentExt();
            ContentTxt txtCopy = new ContentTxt();
            Content beanCopy = bean.cloneWithoutSet();
            beanCopy.setChannel(channel);
            boolean draft = false;
            if (bean.getStatus().equals(ContentCheck.DRAFT)) {
                draft = true;
            }
            BeanUtils.copyProperties(bean.getContentExt(), extCopy);
            if (bean.getContentTxt() != null) {
                BeanUtils.copyProperties(bean.getContentTxt(), txtCopy);
            }
            manager.save(beanCopy, extCopy, txtCopy, null, bean.getTopicIds(), bean.getViewGroupIds(), bean.getTagArray(), bean.getAttachmentPaths(), bean.getAttachmentNames(), bean.getAttachmentFileNames(), bean.getPicPaths(), bean.getPicDescs(), channelId, bean.getType().getId(), draft, false, bean.getChargeModel(), bean.getChargeAmount(), bean.getRewardPattern(), bean.getRewardRandomMin(), bean.getRewardRandomMax(), bean.getRewardFixValues(), getAdmin(), false);
            afterContentStatusChange(bean, null, ContentStatusChangeThread.OPERATE_ADD);
        }
        String body = "{\"result\":" + true + "}";
        ApiResponse apiResponse = ApiResponse.getSuccess(body);
        ResponseUtils.renderApiJson(response, request, apiResponse);
    }

    /**
     * 引用
     *
     * @author andy_hulibo@163.com
     * @date 2018/11/26 14:10
     */
    @SignValidate
    @RequestMapping("/content/refer")
    public void refer(String ids, Integer channelId, HttpServletRequest request, HttpServletResponse response) {
        WebErrors errors = WebErrors.create(request);
        errors = ApiValidate.validateRequiredParams(request, errors, ids, channelId);
        if (errors.hasErrors()) {
            throw new ApiException("缺少参数", ResponseCode.API_CODE_PARAM_REQUIRED);
        }
        Integer[] idArr = StrUtils.getInts(ids);
        errors = validateContent(errors, idArr);
        if (errors.hasErrors()) {
            throw new ApiException("参数错误", ResponseCode.API_CODE_PARAM_ERROR);
        }
        for (Integer contentId : idArr) {
            manager.updateByChannelIds(contentId, new Integer[]{channelId}, Content.CONTENT_CHANNEL_ADD);
        }
        String body = "{\"result\":" + true + "}";
        ApiResponse apiResponse = ApiResponse.getSuccess(body);
        ResponseUtils.renderApiJson(response, request, apiResponse);
    }

    /**
     * 保存固顶等级
     *
     * @author andy_hulibo@163.com
     * @date 2018/11/26 14:10
     */
    @SignValidate
    @RequestMapping("/content/priority")
    public void priority(String ids, String topLevel, HttpServletRequest request, HttpServletResponse response) {
        WebErrors errors = WebErrors.create(request);
        errors = ApiValidate.validateRequiredParams(request, errors, ids, topLevel);
        if (errors.hasErrors()) {
            throw new ApiException("缺少参数", ResponseCode.API_CODE_PARAM_REQUIRED);
        }
        Integer[] idArr = StrUtils.getInts(ids);
        errors = validateContent(errors, idArr, request);
        if (errors.hasErrors()) {
            throw new ApiException("参数错误", ResponseCode.API_CODE_PARAM_ERROR);
        }
        Byte[] bytes = splitToByte(topLevel);
        errors = validatePriority(errors, idArr, bytes);
        if (errors.hasErrors()) {
            throw new ApiException("参数错误", ResponseCode.API_CODE_PARAM_ERROR);
        }
        for (int i = 0; i < idArr.length; i++) {
            Content c = manager.findById(idArr[i]);
            c.setTopLevel(bytes[i]);
            manager.update(c);
        }
        log.info("update content priority.");
        String body = "{\"result\":" + true + "}";
        ApiResponse apiResponse = ApiResponse.getSuccess(body);
        ResponseUtils.renderApiJson(response, request, apiResponse);
    }

    /**
     * 归档
     *
     * @author andy_hulibo@163.com
     * @date 2018/11/26 14:11
     */
    @SignValidate
    @RequestMapping("/content/pigeonhole")
    public void pigeonhole(String ids, HttpServletRequest request, HttpServletResponse response) {
        WebErrors errors = WebErrors.create(request);
        errors = ApiValidate.validateRequiredParams(request, errors, ids);
        if (errors.hasErrors()) {
            throw new ApiException("缺少参数", ResponseCode.API_CODE_PARAM_REQUIRED);
        }
        Integer[] idArr = StrUtils.getInts(ids);
        errors = validateContent(errors, idArr, request);
        if (errors.hasErrors()) {
            throw new ApiException("参数错误", ResponseCode.API_CODE_PARAM_ERROR);
        }
        for (Integer id : idArr) {
            Content c = manager.findById(id);
            List<Map<String, Object>> list = manager.preChange(c);
            c.setStatus(ContentCheck.PIGEONHOLE);
            afterContentStatusChange(c, list, ContentStatusChangeThread.OPERATE_UPDATE);
            manager.update(getAdmin(), c, ContentOperateType.pigeonhole);
        }
        log.info("update CmsFriendlink priority.");
        String body = "{\"result\":" + true + "}";
        ApiResponse apiResponse = ApiResponse.getSuccess(body);
        ResponseUtils.renderApiJson(response, request, apiResponse);
    }

    /**
     * 出档
     *
     * @author andy_hulibo@163.com
     * @date 2018/11/26 14:11
     */
    @SignValidate
    @RequestMapping("/content/unpigeonhole")
    public void unpigeonhole(String ids, HttpServletResponse response, HttpServletRequest request) {
        WebErrors errors = WebErrors.create(request);
        errors = ApiValidate.validateRequiredParams(request, errors, ids);
        if (errors.hasErrors()) {
            throw new ApiException("缺少参数", ResponseCode.API_CODE_PARAM_REQUIRED);
        }
        Integer[] idArr = StrUtils.getInts(ids);
        errors = validateContent(errors, idArr, request);
        if (errors.hasErrors()) {
            throw new ApiException("参数错误", ResponseCode.API_CODE_PARAM_ERROR);
        }
        for (Integer id : idArr) {
            Content c = manager.findById(id);
            List<Map<String, Object>> list = manager.preChange(c);
            c.setStatus(ContentCheck.CHECKED);
            manager.update(getAdmin(), c, ContentOperateType.reuse);
            afterContentStatusChange(c, list, ContentStatusChangeThread.OPERATE_UPDATE);
        }
        log.info("update CmsFriendlink priority.");
        String body = "{\"result\":" + true + "}";
        ApiResponse apiResponse = ApiResponse.getSuccess(body);
        ResponseUtils.renderApiJson(response, request, apiResponse);
    }

    /**
     * 推荐、取消推荐
     *
     * @author andy_hulibo@163.com
     * @date 2018/11/26 14:11
     */
    @SignValidate
    @RequestMapping("/content/recommend")
    public void recommend(String ids, Byte level, HttpServletRequest request, HttpServletResponse response) {
        WebErrors errors = WebErrors.create(request);
        errors = ApiValidate.validateRequiredParams(request, errors, ids, level);
        if (errors.hasErrors()) {
            throw new ApiException("缺少参数", ResponseCode.API_CODE_PARAM_REQUIRED);
        }
        Integer[] idArr = StrUtils.getInts(ids);
        errors = validateContent(errors, idArr, request);
        if (errors.hasErrors()) {
            throw new ApiException("参数错误", ResponseCode.API_CODE_PARAM_ERROR);
        }
        for (Integer id : idArr) {
            Content content = manager.findById(id);
            // 设置推荐
            if (level == -1) {
                content.setRecommend(false);
            } else {
                content.setRecommend(true);
            }
            if (level <= 0) {
                content.setRecommendLevel(new Byte("0"));
            } else {
                content.setRecommendLevel(level);
            }
            manager.update(content);
        }
        log.info("update CmsFriendlink recommend.");
        String body = "{\"result\":" + true + "}";
        ApiResponse apiResponse = ApiResponse.getSuccess(body);
        ResponseUtils.renderApiJson(response, request, apiResponse);
    }

    /**
     * 推送至主题
     *
     * @author andy_hulibo@163.com
     * @date 2018/11/26 14:12
     */
    @SignValidate
    @RequestMapping("/content/send_to_topic")
    public void sendToTopic(String ids, String topicIds, HttpServletRequest request, HttpServletResponse response) {
        WebErrors errors = WebErrors.create(request);
        errors = ApiValidate.validateRequiredParams(request, errors, ids, topicIds);
        if (errors.hasErrors()) {
            throw new ApiException("缺少参数", ResponseCode.API_CODE_PARAM_REQUIRED);
        }
        Integer[] idArr = StrUtils.getInts(ids);
        Integer[] topicArr = StrUtils.getInts(topicIds);
        errors = validateSendToTopic(errors, request, idArr, topicArr);
        if (errors.hasErrors()) {
            throw new ApiException("参数错误", ResponseCode.API_CODE_PARAM_ERROR);
        }
        for (Integer contentId : idArr) {
            manager.addContentToTopics(contentId, topicArr);
        }
        String body = "{\"result\":" + true + "}";
        ApiResponse apiResponse = ApiResponse.getSuccess(body);
        ResponseUtils.renderApiJson(response, request, apiResponse);
    }

    /**
     * 发送到微信
     *
     * @author andy_hulibo@163.com
     * @date 2018/11/26 14:12
     */
    @SignValidate
    @RequestMapping("/content/send_to_weixin")
    public void sendToWeixin(String ids, HttpServletRequest request, HttpServletResponse response) {
        WebErrors errors = WebErrors.create(request);
        Map<String, String> msg;
        JSONObject json = new JSONObject();
        errors = ApiValidate.validateRequiredParams(request, errors, ids);
        Integer wxCode;
        if (errors.hasErrors()) {
            throw new ApiException("缺少参数", ResponseCode.API_CODE_PARAM_REQUIRED);
        }
        Integer[] idArr = StrUtils.getInts(ids);
        if (errors.hasErrors() || idArr == null || idArr.length <= 0) {
            throw new ApiException("参数错误", ResponseCode.API_CODE_PARAM_ERROR);
        }
        Content[] beans = new Content[idArr.length];
        for (int i = 0; i < idArr.length; i++) {
            Integer contentId = idArr[i];
            beans[i] = manager.findById(contentId);
        }
        // 判断正文是否存在
        Boolean flag = true;
        for (Content bean : beans) {
            if (StringUtils.isBlank(bean.getTxt())) {
                flag = false;
                break;
            }
        }
        if (!flag) {
            throw new ApiException("内容为空", "202");
        }
        msg = weiXinSvc.sendTextToAllUser(beans);
        wxCode = Integer.parseInt(msg.get("status"));
        if (!wxCode.equals(Weixin.TENCENT_WX_SUCCESS)) {
            throw new ApiException("发送微信消息错误", "\"" + wxCode + "\"");
        }
        if (StringUtils.isNotBlank(msg.get("errmsg"))) {
            throw new ApiException(msg.get("errmsg"), "202");
        }
        //成功发送状态码无价值
        wxCode = null;
        json.put("wxCode", wxCode);
        json.put("result", true);
        String body = json.toString();
        ApiResponse apiResponse = ApiResponse.getSuccess(body);
        ResponseUtils.renderApiJson(response, request, apiResponse);
    }

    /**
     * 回收站内容还原
     *
     * @author andy_hulibo@163.com
     * @date 2018/11/26 14:21
     */
    @SignValidate
    @RequestMapping("/content/cycle_recycle")
    public void cycleRecycle(String ids, HttpServletRequest request, HttpServletResponse response) {
        WebErrors errors = WebErrors.create(request);
        errors = ApiValidate.validateRequiredParams(request, errors, ids);
        if (errors.hasErrors()) {
            throw new ApiException("缺少参数", ResponseCode.API_CODE_PARAM_REQUIRED);
        }
        Integer[] idArr = StrUtils.getInts(ids);
        errors = validateContent(errors, idArr, request);
        if (errors.hasErrors()) {
            throw new ApiException("参数错误", ResponseCode.API_CODE_NOT_FOUND);
        }
        Map<Integer, List<Map<String, Object>>> map = new HashMap<>(5);
        for (Integer id : idArr) {
            List<Map<String, Object>> list = manager.preChange(manager.findById(id));
            map.put(id, list);
        }
        Content[] beans = manager.recycle(idArr);
        for (Content bean : beans) {
            afterContentStatusChange(bean, map.get(bean.getId()), ContentStatusChangeThread.OPERATE_UPDATE);
            log.info("delete Content id={}", bean.getId());
        }
        ApiResponse apiResponse = ApiResponse.getSuccess();
        ResponseUtils.renderApiJson(response, request, apiResponse);
    }

    /**
     * 删除回收站内容
     *
     * @author andy_hulibo@163.com
     * @date 2018/11/26 14:21
     */
    @SignValidate
    @RequestMapping("/content/cycle_delete")
    public void cycleDelete(String ids, HttpServletRequest request, HttpServletResponse response) {
        WebErrors errors = WebErrors.create(request);
        CmsSite site = siteMng.getSite();
        errors = ApiValidate.validateRequiredParams(request, errors, ids);
        if (errors.hasErrors()) {
            throw new ApiException("缺少参数", ResponseCode.API_CODE_PARAM_REQUIRED);
        }
        Integer[] idArr = StrUtils.getInts(ids);
        errors = validateContent(errors, idArr, request);
        if (errors.hasErrors()) {
            throw new ApiException("参数错误", ResponseCode.API_CODE_NOT_FOUND);
        }
        for (Integer id : idArr) {
            Content c = manager.findById(id);
            //处理附件
            manager.updateFileByContent(c, false);
        }
        Map<Integer, List<Map<String, Object>>> map = new HashMap<>(10);
        for (Integer id : idArr) {
            List<Map<String, Object>> list = manager.preChange(manager.findById(id));
            map.put(id, list);
        }
        Content[] beans = manager.deleteByIds(idArr);
        //静态页与ftp删除
        String real;
        String ftpPath = "";
        String pcFtpPath = "";
        Ftp syncPageFtp;
        syncPageFtp = site.getSyncPageFtp();
        if (syncPageFtp != null) {
            syncPageFtp = ftpMng.findById(syncPageFtp.getId());
        }
        //删除静态页
        int count = 0;
        for (Content bean : beans) {
            count += 1;
            //判断是否手机模板
            if (site.getMobileStaticSync()) {
                real = realPathResolver.get(bean.getMobileStaticFilename(count));
            } else {
                real = realPathResolver.get(bean.getStaticFilename(count));
            }
            File f = new File(real);
            if (f.exists()) {
                f.delete();
            }
            deleteStatic(site, bean, count, ftpPath, pcFtpPath, syncPageFtp);
            afterContentStatusChange(bean, map.get(bean.getId()), ContentStatusChangeThread.OPERATE_DEL);
            log.info("delete Content id={}", bean.getId());
        }
        ApiResponse apiResponse = ApiResponse.getSuccess();
        ResponseUtils.renderApiJson(response, request, apiResponse);
    }

    private WebErrors validateContent(WebErrors errors, Integer[] arr, HttpServletRequest request) {
        for (Integer id : arr) {
            vldExist(id, errors);
        }
        return errors;
    }

    private WebErrors validateContent(WebErrors errors, Integer[] arr) {
        if (arr != null) {
            for (int i = 0; i < arr.length; i++) {
                Content content = manager.findById(arr[i]);
                if (content == null) {
                    errors.addErrorString(Constants.API_MESSAGE_OBJECT_NOT_FOUND);
                    return errors;
                }
            }
        }
        return errors;
    }

    private WebErrors validateSendToTopic(WebErrors errors, HttpServletRequest request, Integer[] arr1, Integer[] arr2) {
        if (arr1 != null) {
            for (Integer id : arr1) {
                vldExist(id, errors);
            }
        }
        if (arr2 != null) {
            for (Integer topicId : arr2) {
                vldTopicExist(topicId, errors);
            }
        }
        return errors;
    }

    private boolean vldTopicExist(Integer topicId, WebErrors errors) {
        if (errors.ifNull(topicId, "topicId", false)) {
            return true;
        }
        CmsTopic entity = topicMng.findById(topicId);
        return errors.ifNotExist(entity, Content.class, topicId, false);
    }

    private WebErrors validatePriority(WebErrors errors, Integer[] arr1, Byte[] arr2) {
        if (arr1 != null && arr2 != null) {
            if (arr1.length != arr2.length) {
                errors.addError(Constants.API_MESSAGE_PARAM_ERROR);
                return errors;
            }
        }
        return errors;
    }

    private Byte[] splitToByte(String str) {
        if (StringUtils.isBlank(str)) {
            return null;
        }
        String[] split = str.split(",");
        Byte[] bytes = new Byte[split.length];
        for (int i = 0; i < split.length; i++) {
            bytes[i] = Byte.parseByte(split[i]);
        }
        return bytes;
    }

    private WebErrors validateDelete(Integer[] ids, HttpServletRequest request) {
        WebErrors errors = WebErrors.create(request);
        errors.ifEmpty(ids, "ids", false);
        if (ids != null && ids.length > 0) {
            for (Integer id : ids) {
                Content content = manager.findById(id);
                // 是否有审核后删除权限。
                if (!content.isHasDeleteRight()) {
                    errors.addErrorString("content.error.afterCheckDelete");
                    return errors;
                }
            }
        }
        return errors;
    }

    private WebErrors validateUpdate(Integer id, HttpServletRequest request) {
        WebErrors errors = WebErrors.create(request);
        if (vldExist(id, errors)) {
            return errors;
        }
        Content content = manager.findById(id);
        // 是否有审核后更新权限。
        if (!content.isHasUpdateRight()) {
            errors.addErrorString("content.error.afterCheckUpdate");
            return errors;
        }
        return errors;
    }

    private void afterContentStatusChange(Content content,
                                          List<Map<String, Object>> list, Short operate) {
        ContentStatusChangeThread afterThread = new ContentStatusChangeThread(
                content, operate,
                manager.getListenerList(),
                list);
        afterThread.start();
    }

    private Double[] getDoubleArr(String str) {
        if (StringUtils.isBlank(str)) {
            return null;
        } else {
            String[] split = str.split(",");
            Double[] newArr = new Double[split.length];
            for (int i = 0; i < split.length; i++) {
                newArr[i] = Double.parseDouble(split[i]);
            }
            return newArr;
        }
    }

    private String[] getStringArr(String str) {
        if (StringUtils.isBlank(str)) {
            return null;
        } else {
            return str.split(",");
        }
    }

    private String[] getPicDescStringArr(String str, int length) {
        String[] desc = new String[length];
        if (StringUtils.isBlank(str)) {
            return desc;
        } else {
            return str.split(",", -1);
        }

    }

    private boolean vldExist(Integer id, WebErrors errors) {
        String idStr = "id";
        if (errors.ifNull(id, idStr, false)) {
            return true;
        }
        Content entity = manager.findById(id);
        if (errors.ifNotExist(entity, Content.class, id, false)) {
            return true;
        }
        return false;
    }

    private JSONObject createViewJson(Content bean) {
        JSONObject json = new JSONObject();
        if (bean.getId() != null) {
            json.put("id", bean.getId());
        } else {
            json.put("id", "");
        }
        if (bean.getSiteId() != null) {
            json.put("siteId", bean.getSiteId());
        } else {
            json.put("siteId", "");
        }
        if (StringUtils.isNotBlank(bean.getUrlDynamic())) {
            json.put("urlDynamic", bean.getUrlDynamic());
        } else {
            json.put("urlDynamic", "");
        }
        if (StringUtils.isNotBlank(bean.getTit())) {
            json.put("title", bean.getTit());
        } else {
            json.put("title", "");
        }
        if (bean.getReleaseDate() != null) {
            json.put("releaseDate", DateUtils.parseDateToTimeStr(bean.getReleaseDate()));
        } else {
            json.put("releaseDate", "");
        }
        if (StringUtils.isNotBlank(bean.getTxt())) {
            json.put("txt", bean.getTxt());
        } else {
            json.put("txt", "");
        }
        if (StringUtils.isNotBlank(bean.getOrigin())) {
            json.put("origin", bean.getOrigin());
        } else {
            json.put("origin", "");
        }
        if (bean.getAdmin() != null && StringUtils.isNotBlank(bean.getAdmin().getUsername())) {
            json.put("username", bean.getAdmin().getUsername());
        } else {
            json.put("username", "");
        }
        if (StringUtils.isNotBlank(bean.getAuthor())) {
            json.put("author", bean.getAuthor());
        } else {
            json.put("author", "");
        }
        if (bean.getViews() != null) {
            json.put("views", bean.getViews());
        } else {
            json.put("views", "");
        }
        return json;
    }

    private WebErrors validateSave(Integer channelId, Integer modelId, Integer typeId,
                                   HttpServletRequest request) {
        WebErrors errors = WebErrors.create(request);
        if (errors.ifNull(channelId, "channelId", false)) {
            return errors;
        }
        Channel channel = channelMng.findById(channelId);
        if (errors.ifNotExist(channel, Channel.class, channelId, false)) {
            return errors;
        }
        if (channel.getChild().size() > 0) {
            errors.addErrorString("content.error.notLeafChannel");
        }
        //所选发布内容模型不在栏目模型范围内
        if (modelId != null) {
            CmsModel m = modelMng.findById(modelId);
            if (errors.ifNotExist(m, CmsModel.class, modelId, false)) {
                return errors;
            }
            //默认没有配置的情况下modelIds为空 则允许添加
            if (channel.getModelIds().size() > 0 && !channel.getModelIds().contains(modelId.toString())) {
                errors.addErrorString("channel.modelError");
            }
        }
        if (typeId != null) {
            ContentType type = typeMng.findById(typeId);
            if (type == null) {
                //
                errors.addErrorString(Constants.API_MESSAGE_PARAM_ERROR);
            }
        }
        return errors;
    }

    private ContentTxt copyContentTxtImg(ContentTxt txt, CmsSite site) {
        if (StringUtils.isNotBlank(txt.getTxt())) {
            txt.setTxt(copyTxtHmtlImg(txt.getTxt(), site));
        }
        if (StringUtils.isNotBlank(txt.getTxt1())) {
            txt.setTxt1(copyTxtHmtlImg(txt.getTxt1(), site));
        }
        if (StringUtils.isNotBlank(txt.getTxt2())) {
            txt.setTxt2(copyTxtHmtlImg(txt.getTxt2(), site));
        }
        if (StringUtils.isNotBlank(txt.getTxt3())) {
            txt.setTxt3(copyTxtHmtlImg(txt.getTxt3(), site));
        }
        return txt;
    }

    private JSONObject easyJson(Content bean) {
        JSONObject json = new JSONObject();
        if (bean.getId() != null) {
            json.put("id", bean.getId());
        } else {
            json.put("id", "");
        }
        if (StringUtils.isNotBlank(bean.getUrl())) {
            json.put("url", bean.getUrl());
        } else {
            json.put("url", "");
        }
        return json;
    }

    private String copyTxtHmtlImg(String txtHtml, CmsSite site) {
        List<String> imgUrls = ImageUtils.getImageSrc(txtHtml);
        for (String img : imgUrls) {
            txtHtml = txtHtml.replace(img, imageSvc.crawlImg(img, site));
        }
        return txtHtml;
    }

    private void deleteStatic(CmsSite site, Content bean, Integer pageNo, String ftpPath,
                              String pcFtpPath, Ftp syncPageFtp) {

        if (syncPageFtp != null && bean.getChannel().getStaticChannel()) {
            if (site.getMobileStaticSync()) {
                ftpPath = bean.getSite().getSyncPageFtp().getPath();
                ftpPath += bean.getMobileStaticFilename(pageNo);
                new Thread(new FtpDeleteThread(syncPageFtp, ftpPath)).start();
            }
            pcFtpPath = bean.getSite().getSyncPageFtp().getPath();
            pcFtpPath += bean.getStaticFilename(pageNo);
            new Thread(new FtpDeleteThread(syncPageFtp, pcFtpPath)).start();
        }
    }

    @Autowired
    private ContentTypeMng typeMng;
    @Autowired
    private CmsModelMng modelMng;
    @Autowired
    private CmsTopicMng topicMng;
    @Autowired
    private ChannelMng channelMng;
    @Autowired
    private CmsUserMng cmsUserMng;
    @Autowired
    private CmsLogMng cmsLogMng;
    @Autowired
    private ContentMng manager;
    @Autowired
    private CmsFileMng fileMng;
    @Autowired
    private CmsSiteMng siteMng;
    @Autowired
    private ImageSvc imageSvc;
    @Autowired
    private WeiXinSvc weiXinSvc;
    @Autowired
    private RealPathResolver realPathResolver;
    @Autowired
    private FtpMng ftpMng;
}
