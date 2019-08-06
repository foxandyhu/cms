package com.bfly.manage.resource;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bfly.common.FileUtil;
import com.bfly.common.ResponseData;
import com.bfly.common.ResponseUtil;
import com.bfly.common.StringUtil;
import com.bfly.core.base.action.BaseManageController;
import com.bfly.core.config.ResourceConfig;
import com.bfly.core.enums.SysError;
import com.bfly.core.exception.WsResponseException;
import com.bfly.core.security.ActionModel;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.Map;

/**
 * 系统模板Controller
 *
 * @author andy_hulibo@163.com
 * @date 2019/8/6 12:37
 */
@RestController
@RequestMapping(value = "/manage/template")
public class TemplateController extends BaseManageController {

    /**
     * 模板列表
     *
     * @author andy_hulibo@163.com
     * @date 2019/8/6 12:37
     */
    @GetMapping(value = "/list")
    @ActionModel(value = "系统模板列表", need = false)
    public void listTemplate(HttpServletResponse response) {
        String target = getRequest().getParameter("target");
        String root = ResourceConfig.getTemplatePath();
        root = Paths.get(root, target).toAbsolutePath().toString();
        File file = new File(root);
        if (!file.exists()) {
            throw new RuntimeException("文件地址不存在!");
        }
        JSONArray array = new JSONArray();
        if (file.exists()) {
            File[] files = file.listFiles();
            JSONObject json;
            for (File f : files) {
                json = new JSONObject();
                json.put("name", f.getName());
                json.put("date", new Date(f.lastModified()));
                json.put("size", StringUtil.getHumanSize(f.length()));
                json.put("isDir", f.isDirectory());

                //相对资源根目录的相对路径
                String relativePath = ResourceConfig.getRelativePathForTemplate(f.getPath());
                json.put("path", relativePath);
                if (f.isDirectory()) {
                    json.put("children", new String[]{});
                } else {
                    json.put("link", ResourceConfig.getServer() + relativePath);
                }
                array.add(json);
            }
        }
        ResponseUtil.writeJson(response, ResponseData.getSuccess(array));
    }

    /**
     * 检查路径是否合法
     *
     * @author andy_hulibo@163.com
     * @date 2019/7/22 19:13
     */
    private void checkPath(String path) {
        Path p = Paths.get(ResourceConfig.getTemplatePath(), path);
        File file = p.toFile();
        if (!file.exists() || !file.isDirectory()) {
            throw new RuntimeException("文件夹路径不存在!");
        }
    }

    /**
     * 创建目录
     *
     * @author andy_hulibo@163.com
     * @date 2019/7/22 19:01
     */
    @PostMapping("/mkdir")
    @ActionModel(value = "新建文件夹!")
    public void mkdir(HttpServletResponse response) {
        String path = getRequest().getParameter("path");
        String name = getRequest().getParameter("name");
        checkPath(path);
        File file = Paths.get(ResourceConfig.getTemplatePath(), path, name).toFile();
        if (file.exists()) {
            throw new RuntimeException("目标文件夹已存在!");
        }
        file.mkdirs();
        ResponseUtil.writeJson(response, ResponseData.getSuccess(""));
    }

    /**
     * 删除文件
     *
     * @author andy_hulibo@163.com
     * @date 2019/7/22 19:15
     */
    @PostMapping("/del")
    @ActionModel(value = "删除文件!")
    public void delTemplate(HttpServletResponse response, @RequestBody String... paths) {
        for (String path : paths) {
            String fullPath = ResourceConfig.getTemplatePath() + path;
            File file = new File(fullPath);
            File root = new File(ResourceConfig.getTemplatePath());
            if (file.exists()) {
                if (file.getPath().equals(root.getPath())) {
                    throw new RuntimeException("不能删除根目录!");
                }
                FileUtil.deleteFiles(fullPath);
            } else {
                throw new RuntimeException("目标文件或文件夹不存在!");
            }
        }
        ResponseUtil.writeJson(response, ResponseData.getSuccess(""));
    }

    /**
     * 上传文件
     *
     * @author andy_hulibo@163.com
     * @date 2019/7/22 19:26
     */
    @PostMapping(value = "/upload")
    @ActionModel("上传文件")
    public void uploadFile(@RequestPart MultipartFile file, MultipartHttpServletRequest request, HttpServletResponse response) throws Exception {
        String path = request.getParameter("path");
        String fullPath = ResourceConfig.getTemplatePath() + path;
        if (path == null || !FileUtil.checkExist(fullPath)) {
            throw new RuntimeException("目标文件夹不存在!");
        }

        Assert.isTrue(file.getOriginalFilename().endsWith(".html"), "模板文件必须以.html结尾!");

        fullPath = fullPath + File.separator + file.getOriginalFilename();
        boolean result = FileUtil.writeFile(file.getInputStream(), fullPath);
        if (!result) {
            throw new RuntimeException("文件上传失败!");
        }
        ResponseUtil.writeJson(response, ResponseData.getSuccess(""));
    }

    /**
     * 查看模板
     *
     * @author andy_hulibo@163.com
     * @date 2019/8/6 14:28
     */
    @GetMapping(value = "/detail")
    public void getTemplate(HttpServletResponse response) {
        String path = getRequest().getParameter("path");
        path = ResourceConfig.getTemplatePath() + path;
        if (!FileUtil.checkExist(path)) {
            throw new RuntimeException("文件不存在!");
        }

        Assert.isTrue(path.endsWith(".html"), "模板文件必须以.html结尾!");

        StringBuilder sb = new StringBuilder();
        try (FileReader reader = new FileReader(new File(path))) {
            try (BufferedReader bufferedReader = new BufferedReader(reader)) {
                String content;
                while ((content = bufferedReader.readLine()) != null) {
                    sb.append(content);
                }
            }
        } catch (Exception e) {
            throw new WsResponseException(SysError.ERROR, e.getMessage());
        }
        ResponseUtil.writeJson(response, ResponseData.getSuccess(sb));
    }

    /**
     * 修改文件
     *
     * @author andy_hulibo@163.com
     * @date 2019/7/22 19:15
     */
    @PostMapping("/edit")
    @ActionModel(value = "修改文件!")
    public void editTemplate(HttpServletResponse response, @RequestBody Map<String, String> params) {
        String path = params.get("path");
        String content = params.get("content");

        path = ResourceConfig.getTemplatePath() + path;
        if (!FileUtil.checkExist(path)) {
            throw new RuntimeException("文件不存在!");
        }

        Assert.isTrue(path.endsWith(".html"), "模板文件必须以.html结尾!");

        File file = new File(path);
        try (FileWriter writer = new FileWriter(file)) {
            writer.write(content);
        } catch (Exception ex) {
            throw new RuntimeException("修改文件出错!");
        }
        ResponseUtil.writeJson(response, ResponseData.getSuccess(""));
    }
}
