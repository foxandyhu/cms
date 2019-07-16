package com.bfly.core.base.action;

import com.bfly.common.FileUtil;
import com.bfly.common.ResponseData;
import com.bfly.common.ResponseUtil;
import com.bfly.core.Constants;
import com.bfly.core.config.ResourceConfig;
import com.bfly.core.security.ActionModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;


/**
 * 文件上传操作Action
 *
 * @author 胡礼波
 * 2013-8-19 下午4:32:39
 */
@RestController("FileUploadController")
@RequestMapping(value = "/manage/file")
public class FileUploadController extends BaseManageController {

    @Autowired
    private ResourceConfig resourceConfig;

    /**
     * 公共的文件上传
     *
     * @return 返回上传文件的文件名
     * @author 胡礼波
     * 2013-8-19 下午4:36:01
     */
    @PostMapping(value = "/upload")
    @ActionModel(value = "文件上传")
    public void uploadImage(@RequestPart MultipartFile file, HttpServletResponse response) throws Exception {
        String targetPath = resourceConfig.getTempDir();
        String fileName = FileUtil.getRandomName() + FileUtil.getFileSuffix(file.getOriginalFilename())+Constants.TEMP_RESOURCE_SUFFIX;
        targetPath = targetPath + File.separator + fileName;
        boolean result = FileUtil.writeFile(file.getInputStream(), targetPath);
        if (!result) {
            throw new RuntimeException("文件上传失败!");
        }
        ResponseUtil.writeJson(response, ResponseData.getSuccess(fileName));
    }
}
