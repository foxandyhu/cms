package com.bfly.admin.resource.action;

import com.bfly.core.annotation.SignValidate;
import com.bfly.core.web.ApiResponse;
import com.bfly.core.web.ApiValidate;
import com.bfly.core.Constants;
import com.bfly.core.web.ResponseCode;
import com.bfly.cms.content.entity.CmsModel;
import com.bfly.cms.content.service.CmsModelMng;
import com.bfly.cms.resource.service.CmsResourceMng;
import com.bfly.cms.siteconfig.entity.CmsSite;
import com.bfly.cms.siteconfig.service.CmsSiteMng;
import com.bfly.common.util.Zipper;
import com.bfly.common.util.Zipper.FileEntry;
import com.bfly.common.web.ResponseUtils;
import com.bfly.cms.resource.service.impl.FileTplImpl;
import com.bfly.cms.resource.service.Tpl;
import com.bfly.cms.resource.service.TplManager;
import com.bfly.core.web.WebErrors;
import com.bfly.core.web.util.CmsUtils;
import com.bfly.core.web.util.CoreUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Locale;

import static com.bfly.core.Constants.TPLDIR_INDEX;
import static com.bfly.core.Constants.TPL_BASE;

/**
 * 模板Controller
 *
 * @author andy_hulibo@163.com
 * @date 2018/11/26 11:42
 */
@Controller
@RequestMapping(value = "/api/admin")
public class CmsTemplateApiAct {

    private static final Logger log = LoggerFactory.getLogger(CmsTemplateApiAct.class);

    /**
     * 所有内容模型
     *
     * @author andy_hulibo@163.com
     * @date 2018/11/26 11:45
     */
    @RequestMapping("/tpl/select_content_model")
    public void selectContentModel(HttpServletRequest request, HttpServletResponse response) {
        CmsSite site = CmsUtils.getSite(request);
        JSONArray jsonArray = new JSONArray();
        //查询所有模型
        List<CmsModel> models = modelMng.getList(true, true);
        int index = 0;
        for (CmsModel model : models) {
            JSONObject json = new JSONObject();
            if (model.getId() != null) {
                json.put("id", model.getId());
            } else {
                json.put("id", "");
            }
            if (StringUtils.isNotBlank(model.getName())) {
                json.put("name", model.getName());
            } else {
                json.put("name", "");
            }
            JSONArray contentArray = new JSONArray();
            List<String> content = getTplContent(site, model, null);
            for (int i = 0; i < content.size(); i++) {
                contentArray.put(i, content.get(i));
            }
            json.put("contentTpl", contentArray);
            JSONArray mobileContentArray = new JSONArray();
            List<String> mobileTplContent = getMobileTplContent(site, model, null);
            for (int i = 0; i < mobileTplContent.size(); i++) {
                mobileContentArray.put(i, mobileTplContent.get(i));
            }
            json.put("mobileContentTpl", mobileContentArray);
            jsonArray.put(index, json);
            index++;
        }
        String body = jsonArray.toString();
        String message = Constants.API_MESSAGE_SUCCESS;
        String code = ResponseCode.API_CODE_CALL_SUCCESS;
        ApiResponse apiResponse = new ApiResponse(request, body, message, code);
        ResponseUtils.renderApiJson(response, request, apiResponse);
    }


    /**
     * 模板列表
     *
     * @author andy_hulibo@163.com
     * @date 2018/11/26 11:45
     */
    @RequestMapping("/tpl/list")
    public void getTpl(HttpServletResponse response, HttpServletRequest request) {
        CmsSite site = CmsUtils.getSite(request);
        List<String> indexTplList = getTplIndex(site, null);
        JSONArray jsonArray = new JSONArray();
        if (indexTplList != null) {
            for (int i = 0; i < indexTplList.size(); i++) {
                jsonArray.put(i, indexTplList.get(i));
            }
        }
        String body = jsonArray.toString();
        String message = Constants.API_MESSAGE_SUCCESS;
        String code = ResponseCode.API_CODE_CALL_SUCCESS;
        ApiResponse apiResponse = new ApiResponse(request, body, message, code);
        ResponseUtils.renderApiJson(response, request, apiResponse);
    }

    /**
     * 所有模板列表
     *
     * @author andy_hulibo@163.com
     * @date 2018/11/26 11:47
     */
    @RequestMapping("/tpl/model_list")
    public void modelList(Integer modelId, HttpServletRequest request, HttpServletResponse response) {
        String body = "\"\"";
        String message = Constants.API_MESSAGE_PARAM_REQUIRED;
        String code = ResponseCode.API_CODE_PARAM_REQUIRED;
        CmsSite site = CmsUtils.getSite(request);
        if (modelId != null) {
            CmsModel model = modelMng.findById(modelId);
            if (model == null) {
                message = Constants.API_MESSAGE_OBJECT_NOT_FOUND;
                code = ResponseCode.API_CODE_NOT_FOUND;
            } else {
                JSONObject json = new JSONObject();
                //栏目模板列表
                List<String> channelTplList = getTplChannel(site, model, null);
                json.put("channelTpl", strToJson(channelTplList));
                //栏目移动版模板列表
                List<String> channelMobileTplList = getMobileTplChannel(site, model, null);
                json.put("channelMobileTpl", strToJson(channelMobileTplList));
                //内容模板列表
                List<String> contentTplList = getTplContent(site, model, null);
                json.put("contentTpl", strToJson(contentTplList));
                //内容移动版模板列表
                List<String> contentMobileTplList = getMobileTplContent(site, model, null);
                json.put("contentMobileTpl", strToJson(contentMobileTplList));
                body = json.toString();
                message = Constants.API_MESSAGE_SUCCESS;
                code = ResponseCode.API_CODE_CALL_SUCCESS;
            }
        }
        ApiResponse apiResponse = new ApiResponse(request, body, message, code);
        ResponseUtils.renderApiJson(response, request, apiResponse);
    }

    private JSONArray strToJson(List<String> list) {
        JSONArray jsonArray = new JSONArray();
        if (list != null) {
            for (int i = 0; i < list.size(); i++) {
                jsonArray.put(i, list.get(i));
            }
        }
        return jsonArray;
    }

    /**
     * 树形结构模板
     *
     * @author andy_hulibo@163.com
     * @date 2018/11/26 11:51
     */
    @RequestMapping(value = "/template/tree")
    public void tree(HttpServletRequest request, HttpServletResponse response)
            throws JSONException {
        CmsSite site = CmsUtils.getSite(request);
        JSONArray jsonArray = new JSONArray();
        String code = ResponseCode.API_CODE_CALL_SUCCESS;
        String root = site.getTplPath();
        JSONObject result = new JSONObject();
        List<FileTplImpl> list = (List<FileTplImpl>) tplManager.getChild(root);
        if (list != null) {
            for (int i = 0; i < list.size(); i++) {
                jsonArray.put(i, list.get(i).convertToTreeJson(list.get(i)));
            }
        }
        result.put("resources", jsonArray);
        result.put("rootPath", root);
        String message = Constants.API_MESSAGE_SUCCESS;
        String body = result.toString();
        ApiResponse apiResponse = new ApiResponse(request, body, message, code);
        ResponseUtils.renderApiJson(response, request, apiResponse);
    }

    /**
     * 模板列表
     *
     * @author andy_hulibo@163.com
     * @date 2018/11/26 11:52
     */
    @RequestMapping(value = "/template/list")
    public void templateList(String root,
                             HttpServletRequest request, HttpServletResponse response)
            throws JSONException {
        CmsSite site = CmsUtils.getSite(request);
        JSONArray jsonArray = new JSONArray();
        String body = "\"\"";
        String message;
        String code;
        if (StringUtils.isBlank(root)) {
            root = site.getTplPath();
        }
        WebErrors errors = validateList(root, site.getTplPath(), request);
        if (errors.hasErrors()) {
            code = ResponseCode.API_CODE_PARAM_ERROR;
            message = Constants.API_MESSAGE_PARAM_ERROR;
        } else {
            List<FileTplImpl> list = (List<FileTplImpl>) tplManager.getChild(root);
            if (list != null && list.size() > 0) {
                for (int i = 0; i < list.size(); i++) {
                    jsonArray.put(i, list.get(i).convertToJson());
                }
            }
            message = Constants.API_MESSAGE_SUCCESS;
            code = ResponseCode.API_CODE_CALL_SUCCESS;
            body = jsonArray.toString();
        }
        ApiResponse apiResponse = new ApiResponse(request, body, message, code);
        ResponseUtils.renderApiJson(response, request, apiResponse);
    }

    /**
     * 新建文件夹
     *
     * @author andy_hulibo@163.com
     * @date 2018/11/26 11:53
     */
    @SignValidate
    @RequestMapping("/template/dir_save")
    public void createDir(String root, String dirName,
                          HttpServletRequest request, HttpServletResponse response) {
        CmsSite site = CmsUtils.getSite(request);
        String body = "\"\"";
        String message;
        String code;
        if (StringUtils.isBlank(root)) {
            root = site.getTplPath();
        }
        WebErrors errors = WebErrors.create(request);
        //验证公共非空参数
        errors = ApiValidate.validateRequiredParams(request, errors,
                root, dirName);
        if (!errors.hasErrors()) {
            errors = validateList(root, site.getTplPath(), request);
        }
        if (!errors.hasErrors()) {
            tplManager.save(root + "/" + dirName, null, true);
            message = Constants.API_MESSAGE_SUCCESS;
            code = ResponseCode.API_CODE_CALL_SUCCESS;
        } else {
            message = Constants.API_MESSAGE_PARAM_ERROR;
            code = ResponseCode.API_CODE_PARAM_ERROR;
        }
        ApiResponse apiResponse = new ApiResponse(request, body, message, code);
        ResponseUtils.renderApiJson(response, request, apiResponse);
    }

    /**
     * 模板详情
     *
     * @author andy_hulibo@163.com
     * @date 2018/11/26 11:54
     */
    @RequestMapping("/template/get")
    public void get(String name,
                    HttpServletRequest request, HttpServletResponse response) {
        CmsSite site = CmsUtils.getSite(request);
        String body = "";
        String message;
        String code;
        WebErrors errors = WebErrors.create(request);
        //验证公共非空参数
        errors = ApiValidate.validateRequiredParams(request, errors, name);
        if (!errors.hasErrors()) {
            errors = validateList(name, site.getTplPath(), request);
        }
        if (!errors.hasErrors()) {
            FileTplImpl tpl = (FileTplImpl) tplManager.get(name);
            message = Constants.G_API_MESSAGE_SUCCESS;
            code = ResponseCode.G_API_CODE_CALL_SUCCESS;
            body = tpl.getSource();
        } else {
            message = Constants.G_API_MESSAGE_PARAM_ERROR;
            code = ResponseCode.G_API_CODE_PARAM_ERROR;
        }
        JSONObject json = new JSONObject();
        json.put("body", body);
        json.put("message", message);
        json.put("code", code);
        ResponseUtils.renderJson(response, json.toString());
    }

    /**
     * 保存模板
     *
     * @author andy_hulibo@163.com
     * @date 2018/11/26 11:54
     */
    @SignValidate
    @RequestMapping("/template/save")
    public void save(String root, String filename, String source,
                     HttpServletRequest request, HttpServletResponse response) {
        String body = "\"\"";
        String message;
        String code;
        CmsSite site = CmsUtils.getSite(request);
        WebErrors errors = WebErrors.create(request);
        if (StringUtils.isBlank(root)) {
            root = site.getTplPath();
        }
        //验证公共非空参数
        errors = ApiValidate.validateRequiredParams(request, errors,
                root, filename, source);
        if (!errors.hasErrors()) {
            errors = validateList(root, site.getTplPath(), request);
        }
        if (!errors.hasErrors()) {
            String name = root + "/" + filename + Constants.TPL_SUFFIX;
            tplManager.save(name, source, false);
            message = Constants.API_MESSAGE_SUCCESS;
            code = ResponseCode.API_CODE_CALL_SUCCESS;
        } else {
            message = Constants.API_MESSAGE_PARAM_ERROR;
            code = ResponseCode.API_CODE_PARAM_ERROR;
        }
        ApiResponse apiResponse = new ApiResponse(request, body, message, code);
        ResponseUtils.renderApiJson(response, request, apiResponse);
    }

    /**
     * 更新模板
     *
     * @author andy_hulibo@163.com
     * @date 2018/11/26 11:55
     */
    @SignValidate
    @RequestMapping("/template/update")
    public void update(String filename, String source,
                       HttpServletRequest request, HttpServletResponse response) {
        String body = "\"\"";
        String message;
        String code;
        CmsSite site = CmsUtils.getSite(request);
        WebErrors errors = WebErrors.create(request);
        String root = site.getTplPath();
        //验证公共非空参数
        errors = ApiValidate.validateRequiredParams(request, errors,
                filename, source);
        if (!errors.hasErrors()) {
            errors = validateList(root, site.getTplPath(), request);
        }
        if (!errors.hasErrors()) {
            tplManager.update(filename, source);
            message = Constants.API_MESSAGE_SUCCESS;
            code = ResponseCode.API_CODE_CALL_SUCCESS;
        } else {
            message = Constants.API_MESSAGE_PARAM_ERROR;
            code = ResponseCode.API_CODE_PARAM_ERROR;
            log.error(errors.getErrors().get(0));
        }
        ApiResponse apiResponse = new ApiResponse(request, body, message, code);
        ResponseUtils.renderApiJson(response, request, apiResponse);
    }

    /**
     * 删除模板
     *
     * @author andy_hulibo@163.com
     * @date 2018/11/26 11:55
     */
    @SignValidate
    @RequestMapping("/template/delete")
    public void delete(String names,
                       HttpServletRequest request, HttpServletResponse response) {
        String body = "\"\"";
        String message;
        String code;
        CmsSite site = CmsUtils.getSite(request);
        WebErrors errors = WebErrors.create(request);
        //验证公共非空参数
        errors = ApiValidate.validateRequiredParams(request, errors, names);
        String[] nameArray = null;
        if (!errors.hasErrors()) {
            nameArray = names.split(Constants.API_ARRAY_SPLIT_STR);
            for (String n : nameArray) {
                errors = validatePath(n, site.getTplPath(), errors);
                if (errors.hasErrors()) {
                    break;
                }
            }
        }
        if (!errors.hasErrors()) {
            try {
                if (nameArray != null) {
                    tplManager.delete(nameArray);
                }
                message = Constants.API_MESSAGE_SUCCESS;
                code = ResponseCode.API_CODE_CALL_SUCCESS;
            } catch (Exception e) {
                message = Constants.API_MESSAGE_DELETE_ERROR;
                code = ResponseCode.API_CODE_DELETE_ERROR;
            }
        } else {
            message = Constants.API_MESSAGE_PARAM_ERROR;
            code = ResponseCode.API_CODE_PARAM_ERROR;
        }
        ApiResponse apiResponse = new ApiResponse(request, body, message, code);
        ResponseUtils.renderApiJson(response, request, apiResponse);
    }

    /**
     * 模板重新命名
     *
     * @author andy_hulibo@163.com
     * @date 2018/11/26 11:55
     */
    @SignValidate
    @RequestMapping("/template/rename")
    public void rename(String origName, String distName,
                       HttpServletRequest request, HttpServletResponse response) {
        String body = "\"\"";
        String message;
        String code;
        CmsSite site = CmsUtils.getSite(request);
        WebErrors errors = WebErrors.create(request);
        //验证公共非空参数
        errors = ApiValidate.validateRequiredParams(request, errors, origName, distName);
        if (!errors.hasErrors()) {
            errors = validateRename(origName, distName, site.getTplPath(), request);
        }
        if (!errors.hasErrors()) {
            tplManager.rename(origName, distName);
            message = Constants.API_MESSAGE_SUCCESS;
            code = ResponseCode.API_CODE_CALL_SUCCESS;
        } else {
            message = Constants.API_MESSAGE_PARAM_ERROR;
            code = ResponseCode.API_CODE_PARAM_ERROR;
        }
        ApiResponse apiResponse = new ApiResponse(request, body, message, code);
        ResponseUtils.renderApiJson(response, request, apiResponse);
    }

    /**
     * 获得模板方案
     * @author andy_hulibo@163.com
     * @date 2018/11/26 11:56
     */
    @RequestMapping(value = "/template/getSolutions")
    public void getSolutions(HttpServletRequest request, HttpServletResponse response)
            throws JSONException {
        CmsSite site = CmsUtils.getSite(request);
        JSONArray jsonArray = new JSONArray();
        String[] solutions = resourceMng.getSolutions(site.getTplPath());
        if (solutions != null) {
            for (int i = 0; i < solutions.length; i++) {
                jsonArray.put(i, solutions[i]);
            }
        }
        String message = Constants.API_MESSAGE_SUCCESS;
        String code = ResponseCode.API_CODE_CALL_SUCCESS;
        String body = jsonArray.toString();
        ApiResponse apiResponse = new ApiResponse(request, body, message, code);
        ResponseUtils.renderApiJson(response, request, apiResponse);
    }

    /**
     * 更新模板方案
     * @author andy_hulibo@163.com
     * @date 2018/11/26 11:57
     */
    @SignValidate
    @RequestMapping("/template/solutionupdate")
    public void setTempate(String solution, String mobile,
                           HttpServletRequest request, HttpServletResponse response) {
        String body = "\"\"";
        String message;
        String code;
        CmsSite site = CmsUtils.getSite(request);
        WebErrors errors = WebErrors.create(request);
        //验证公共非空参数
        errors = ApiValidate.validateRequiredParams(request, errors, solution);
        if (!errors.hasErrors()) {
            cmsSiteMng.updateTplSolution(site.getId(), solution, mobile);
            message = Constants.API_MESSAGE_SUCCESS;
            code = ResponseCode.API_CODE_CALL_SUCCESS;
        } else {
            message = Constants.API_MESSAGE_PARAM_ERROR;
            code = ResponseCode.API_CODE_PARAM_ERROR;
        }
        ApiResponse apiResponse = new ApiResponse(request, body, message, code);
        ResponseUtils.renderApiJson(response, request, apiResponse);
    }

    /**
     * 模板导出
     * @author andy_hulibo@163.com
     * @date 2018/11/26 11:59
     */
    @RequestMapping("/template/exportTpl")
    public void tplExport(String solution,
                          HttpServletRequest request, HttpServletResponse response) {
        String body = "\"\"";
        String message = Constants.API_MESSAGE_PARAM_REQUIRED;
        String code = ResponseCode.API_CODE_PARAM_REQUIRED;
        CmsSite site = CmsUtils.getSite(request);
        WebErrors errors = WebErrors.create(request);
        //验证公共非空参数
        errors = ApiValidate.validateRequiredParams(request, errors, solution);
        if (!errors.hasErrors()) {
            List<FileEntry> fileEntrys = resourceMng.export(site, solution);
            response.setContentType("application/x-download;charset=UTF-8");
            response.addHeader("Content-disposition", "filename=template-"
                    + solution + ".zip");
            try {
                // 模板一般都在windows下编辑，所以默认编码为GBK
                Zipper.zip(response.getOutputStream(), fileEntrys, "GBK");
            } catch (IOException e) {
                log.error("export template error!", e);
            }
            message = Constants.API_MESSAGE_SUCCESS;
            code = ResponseCode.API_CODE_CALL_SUCCESS;
        }
        ApiResponse apiResponse = new ApiResponse(request, body, message, code);
        ResponseUtils.renderApiJson(response, request, apiResponse);
    }

    /**
     * 模板导入
     * @author andy_hulibo@163.com
     * @date 2018/11/26 12:00
     */
    @SignValidate
    @RequestMapping("/template/importTpl")
    public void tplImport(
            @RequestParam(value = "uploadFile", required = false) MultipartFile file,
            HttpServletRequest request, HttpServletResponse response) {
        String body = "\"\"";
        String message = Constants.API_STATUS_FAIL;
        String code = ResponseCode.API_CODE_CALL_SUCCESS;
        CmsSite site = CmsUtils.getSite(request);
        WebErrors errors = validate(file, request);
        if (errors.hasErrors()) {
            code = ResponseCode.API_CODE_PARAM_ERROR;
        } else {
            //验证公共非空参数
            errors = ApiValidate.validateRequiredParams(request, errors, file);
            if (!errors.hasErrors()) {
                String origName = file.getOriginalFilename();
                String ext = FilenameUtils.getExtension(origName).toLowerCase(
                        Locale.ENGLISH);
                String filepath = "";
                try {
                    File tempFile = File.createTempFile("tplZip", "temp");
                    file.transferTo(tempFile);
                    resourceMng.imoport(tempFile, site);
                    tempFile.delete();
                    JSONObject json = new JSONObject();
                    json.put("ext", ext.toUpperCase());
                    json.put("size", file.getSize());
                    json.put("url", filepath);
                    json.put("name", file.getOriginalFilename());
                    body = json.toString();
                    message = Constants.API_MESSAGE_SUCCESS;
                } catch (Exception e) {
                    code = ResponseCode.API_CODE_UPLOAD_ERROR;
                }
            } else {
                code = ResponseCode.API_CODE_PARAM_REQUIRED;
            }
        }
        ApiResponse apiResponse = new ApiResponse(request, body, message, code);
        ResponseUtils.renderApiJson(response, request, apiResponse);
    }

    /**
     * 模板上传
     * @author andy_hulibo@163.com
     * @date 2018/11/26 12:00
     */
    @SignValidate
    @RequestMapping("/template/upload")
    public void upload(String root,
                       @RequestParam(value = "uploadFile", required = false) MultipartFile file,
                       HttpServletRequest request, HttpServletResponse response) {
        String body = "\"\"";
        String message = Constants.API_MESSAGE_PARAM_ERROR;
        String code = ResponseCode.API_CODE_PARAM_ERROR;
        CmsSite site = CmsUtils.getSite(request);
        WebErrors errors = WebErrors.create(request);
        //验证公共非空参数
        errors = ApiValidate.validateRequiredParams(request, errors, file);
        if (!errors.hasErrors()) {
            errors = validateUpload(root, site.getTplPath(), file, request);
        }
        if (!errors.hasErrors()) {
            try {
                String origName = file.getOriginalFilename();
                String ext = FilenameUtils.getExtension(origName).toLowerCase(
                        Locale.ENGLISH);
                tplManager.save(root, file);
                JSONObject json = new JSONObject();
                json.put("ext", ext.toUpperCase());
                json.put("size", file.getSize());
                json.put("url", root + "/" + origName);
                json.put("name", file.getOriginalFilename());
                body = json.toString();
                message = Constants.API_MESSAGE_SUCCESS;
                code = ResponseCode.API_CODE_CALL_SUCCESS;
            } catch (Exception e) {
                e.printStackTrace();
                message = Constants.API_MESSAGE_UPLOAD_ERROR;
                code = ResponseCode.API_CODE_UPLOAD_ERROR;
            }
        }
        ApiResponse apiResponse = new ApiResponse(request, body, message, code);
        ResponseUtils.renderApiJson(response, request, apiResponse);
    }

    private WebErrors validateUpload(String root, String tplPath,
                                     MultipartFile file,
                                     HttpServletRequest request) {
        WebErrors errors = WebErrors.create(request);
        if (file == null) {
            errors.addErrorString("error.noFileToUpload");
            return errors;
        }
        if (isUnValidName(root, root, tplPath, errors)) {
            errors.addErrorString("template.invalidParams");
        }
        String filename = file.getOriginalFilename();
        if (filename != null && (filename.contains("/") || filename.contains("\\") || filename.indexOf("\0") != -1)) {
            errors.addErrorString(Constants.API_MESSAGE_PARAM_ERROR);
            return errors;
        }
        return errors;
    }

    private WebErrors validate(MultipartFile file,
                               HttpServletRequest request) {
        WebErrors errors = WebErrors.create(request);
        if (file == null) {
            errors.addErrorString("error.noFileToUpload");
            return errors;
        }
        String filename = file.getOriginalFilename();
        if (filename != null && (filename.contains("/") || filename.contains("\\") || filename.indexOf("\0") != -1)) {
            errors.addErrorString(Constants.API_MESSAGE_PARAM_ERROR);
            return errors;
        }
        return errors;
    }

    private WebErrors validateList(String name, String tplPath,
                                   HttpServletRequest request) {
        WebErrors errors = WebErrors.create(request);
        if (vldExist(name, errors)) {
            return errors;
        }
        if (isUnValidName(name, name, tplPath, errors)) {
            errors.addErrorString("template.invalidParams");
        }
        return errors;
    }

    private WebErrors validatePath(String name, String tplPath,
                                   WebErrors errors) {
        if (vldExist(name, errors)) {
            return errors;
        }
        if (isUnValidName(name, name, tplPath, errors)) {
            errors.addErrorString("template.invalidParams");
        }
        return errors;
    }

    private WebErrors validateRename(String name, String newName,
                                     String tplPath, HttpServletRequest request) {
        WebErrors errors = WebErrors.create(request);
        if (vldExist(name, errors)) {
            return errors;
        }
        if (isUnValidName(name, name, tplPath, errors)) {
            errors.addErrorString("template.invalidParams");
        }
        if (isUnValidName(newName, newName, tplPath, errors)) {
            errors.addErrorString("template.invalidParams");
        }
        return errors;
    }

    private boolean isUnValidName(String path, String name, String tplPath, WebErrors errors) {
        if (!path.startsWith(tplPath) || path.contains("../") || path.contains("..\\") || name.contains("..\\") || name.contains("../")) {
            return true;
        } else {
            return false;
        }
    }

    private boolean vldExist(String name, WebErrors errors) {
        String nameStr="name";
        if (errors.ifNull(name, nameStr, false)) {
            return true;
        }
        Tpl entity = tplManager.get(name);
        if (errors.ifNotExist(entity, Tpl.class, name, false)) {
            return true;
        }
        return false;
    }

    private List<String> getTplIndex(CmsSite site, String tpl) {
        String path = site.getPath();
        List<String> tplList = tplManager.getNameListByPrefix(site.getTplIndexPrefix(TPLDIR_INDEX));
        return CoreUtils.tplTrim(tplList, getTplPath(path), tpl);
    }

    private String getTplPath(String path) {
        return TPL_BASE + "/" + path;
    }

    private List<String> getTplContent(CmsSite site, CmsModel model, String tpl) {
        String sol = site.getSolutionPath();
        List<String> tplList = tplManager.getNameListByPrefix(model
                .getTplContent(sol, false));
        return CoreUtils.tplTrim(tplList, site.getTplPath(), tpl);
    }

    private List<String> getMobileTplContent(CmsSite site, CmsModel model, String tpl) {
        String sol = site.getMobileSolutionPath();
        List<String> tplList = tplManager.getNameListByPrefix(model
                .getTplContent(sol, false));
        return CoreUtils.tplTrim(tplList, site.getTplPath(), tpl);
    }

    private List<String> getTplChannel(CmsSite site, CmsModel model, String tpl) {
        String sol = site.getSolutionPath();
        List<String> tplList = tplManager.getNameListByPrefix(model.getTplChannel(sol, false));
        return CoreUtils.tplTrim(tplList, site.getTplPath(), tpl);
    }

    private List<String> getMobileTplChannel(CmsSite site, CmsModel model, String tpl) {
        String sol = site.getMobileSolutionPath();
        List<String> tplList = tplManager.getNameListByPrefix(model.getTplChannel(sol, false));
        return CoreUtils.tplTrim(tplList, site.getTplPath(), tpl);
    }

    @Autowired
    private CmsModelMng modelMng;
    @Autowired
    private TplManager tplManager;
    @Autowired
    private CmsResourceMng resourceMng;
    @Autowired
    private CmsSiteMng cmsSiteMng;
}
