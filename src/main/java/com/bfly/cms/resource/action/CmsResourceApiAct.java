package com.bfly.cms.resource.action;

import com.bfly.core.annotation.SignValidate;
import com.bfly.core.web.ApiResponse;
import com.bfly.core.web.ApiValidate;
import com.bfly.core.Constants;
import com.bfly.core.web.ResponseCode;
import com.bfly.cms.resource.entity.FileWrap;
import com.bfly.cms.resource.service.CmsResourceMng;
import com.bfly.cms.resource.service.Tpl;
import com.bfly.cms.resource.service.TplManager;
import com.bfly.cms.siteconfig.entity.CmsSite;
import com.bfly.common.web.ResponseUtils;
import com.bfly.core.web.WebErrors;
import com.bfly.core.web.util.CmsUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Locale;

/**
 * 资源Controller
 *
 * @author andy_hulibo@163.com
 * @date 2018/11/26 13:05
 */
@Controller
@RequestMapping(value = "/api/admin")
public class CmsResourceApiAct {

    /**
     * 树状资源集合
     *
     * @author andy_hulibo@163.com
     * @date 2018/11/26 13:05
     */
    @RequestMapping(value = "/resource/tree")
    public void tree(HttpServletRequest request, HttpServletResponse response)
            throws JSONException {
        CmsSite site = CmsUtils.getSite(request);
        JSONArray jsonArray = new JSONArray();
        String code = ResponseCode.API_CODE_CALL_SUCCESS;
        String root = site.getResPath();
        JSONObject result = new JSONObject();
        List<FileWrap> list = resourceMng.listFile(root, true);
        if (list != null && list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                jsonArray.put(i, list.get(i).convertToTreeJson(list.get(i)));
            }
        }
        String message = Constants.API_MESSAGE_SUCCESS;
        result.put("resources", jsonArray);
        result.put("rootPath", site.getResPath());
        String body = result.toString();
        ApiResponse apiResponse = new ApiResponse(request, body, message, code);
        ResponseUtils.renderApiJson(response, request, apiResponse);
    }

    /**
     * @author andy_hulibo@163.com
     * @date 2018/11/26 13:06
     * 资源列表
     */
    @RequestMapping(value = "/resource/list")
    public void resourceList(String root,
                             HttpServletRequest request, HttpServletResponse response)
            throws JSONException {
        CmsSite site = CmsUtils.getSite(request);
        JSONArray jsonArray = new JSONArray();
        String body = "\"\"";
        String message;
        String code;
        if (StringUtils.isBlank(root)) {
            root = site.getResPath();
        }
        WebErrors errors = validateList(root, site.getResPath(), request);
        if (errors.hasErrors()) {
            code = ResponseCode.API_CODE_PARAM_ERROR;
            message = Constants.API_MESSAGE_PARAM_ERROR;
        } else {
            List<FileWrap> list = resourceMng.listFile(root, false);
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
     * @date 2018/11/26 13:07
     */
    @SignValidate
    @RequestMapping("/resource/dir_save")
    public void createDir(String root, String dirName,
                          HttpServletRequest request, HttpServletResponse response) {
        CmsSite site = CmsUtils.getSite(request);
        String body = "\"\"";
        String message;
        String code;
        if (StringUtils.isBlank(root)) {
            root = site.getResPath();
        }
        WebErrors errors = WebErrors.create(request);
        //验证公共非空参数
        errors = ApiValidate.validateRequiredParams(request, errors, root, dirName);
        if (!errors.hasErrors()) {
            errors = validateList(root, site.getResPath(), request);
        }
        if (!errors.hasErrors()) {
            resourceMng.createDir(root, dirName);
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
     * 资源详情
     * @author andy_hulibo@163.com
     * @date 2018/11/26 13:07
     */
    @RequestMapping("/resource/get")
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
            errors = validateList(name, site.getResPath(), request);
        }
        if (!errors.hasErrors()) {
            String source = "";
            try {
                source = resourceMng.readFile(name);
            } catch (IOException e) {
            }
            message = Constants.G_API_MESSAGE_SUCCESS;
            code = ResponseCode.G_API_CODE_CALL_SUCCESS;
            body = source;
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
     * 保存资源
     * @author andy_hulibo@163.com
     * @date 2018/11/26 13:08
     */
    @SignValidate
    @RequestMapping("/resource/save")
    public void save(String root, String filename, String source,
                     HttpServletRequest request, HttpServletResponse response) {
        String body = "\"\"";
        String message;
        String code;
        CmsSite site = CmsUtils.getSite(request);
        WebErrors errors = WebErrors.create(request);
        if (StringUtils.isBlank(root)) {
            root = site.getResPath();
        }
        //验证公共非空参数
        errors = ApiValidate.validateRequiredParams(request, errors,
                root, filename, source);
        if (!errors.hasErrors()) {
            errors = validateList(root, site.getResPath(), request);
        }
        if (!errors.hasErrors()) {
            try {
                resourceMng.createFile(request, root, filename, source);
            } catch (IOException e) {
            }
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
     * 更新资源
     * @author andy_hulibo@163.com
     * @date 2018/11/26 13:08
     */
    @SignValidate
    @RequestMapping("/resource/update")
    public void update(String filename, String source,
                       HttpServletRequest request, HttpServletResponse response) {
        String body = "\"\"";
        String message;
        String code;
        CmsSite site = CmsUtils.getSite(request);
        WebErrors errors = WebErrors.create(request);
        //验证公共非空参数
        errors = ApiValidate.validateRequiredParams(request, errors,
                filename, source);
        if (!errors.hasErrors()) {
            errors = validateList(filename, site.getResPath(), request);
        }
        if (!errors.hasErrors()) {
            try {
                resourceMng.updateFile(filename, source);
            } catch (IOException e) {
                e.printStackTrace();
            }
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
     * 删除资源
     * @author andy_hulibo@163.com
     * @date 2018/11/26 13:09
     */
    @SignValidate
    @RequestMapping("/resource/delete")
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
                errors = validatePath(n, site.getResPath(), errors);
                if (errors.hasErrors()) {
                    break;
                }
            }
        }
        if (!errors.hasErrors()) {
            try {
                resourceMng.delete(nameArray);
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
     * 资源重命名
     * @author andy_hulibo@163.com
     * @date 2018/11/26 13:09
     */
    @RequestMapping("/resource/rename")
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
            errors = validateRename(origName, distName, site.getResPath(), request);
        }
        if (!errors.hasErrors()) {
            resourceMng.rename(origName, distName);
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
     * 上传资源
     * @author andy_hulibo@163.com
     * @date 2018/11/26 13:09
     */
    @RequestMapping("/resource/upload")
    public void upload(String root,
                       @RequestParam(value = "uploadFile", required = false) MultipartFile file,
                       HttpServletRequest request, HttpServletResponse response) {
        String body = "\"\"";
        String message;
        String code;
        CmsSite site = CmsUtils.getSite(request);
        WebErrors errors = WebErrors.create(request);
        //验证公共非空参数
        errors = ApiValidate.validateRequiredParams(request, errors, root, file);
        if (!errors.hasErrors()) {
            errors = validateUpload(root, site.getResPath(), file, request);
        }
        if (!errors.hasErrors()) {
            try {
                String origName = file.getOriginalFilename();
                String ext = FilenameUtils.getExtension(origName).toLowerCase(
                        Locale.ENGLISH);
                resourceMng.saveFile(request, root, file);
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
        } else {
            message = Constants.API_MESSAGE_PARAM_ERROR;
            code = ResponseCode.API_CODE_PARAM_ERROR;
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

    @Autowired
    private TplManager tplManager;
    @Autowired
    private CmsResourceMng resourceMng;
}
