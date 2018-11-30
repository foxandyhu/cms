package com.bfly.cms.member.action;

import com.bfly.cms.resource.service.DbFileMng;
import com.bfly.cms.siteconfig.entity.CmsOss;
import com.bfly.cms.siteconfig.entity.CmsSite;
import com.bfly.cms.siteconfig.entity.Ftp;
import com.bfly.cms.system.entity.MarkConfig;
import com.bfly.cms.system.entity.MemberConfig;
import com.bfly.cms.user.entity.CmsUser;
import com.bfly.common.image.ImageScale;
import com.bfly.common.image.ImageUtils;
import com.bfly.common.upload.FileRepository;
import com.bfly.common.web.springmvc.RealPathResolver;
import com.bfly.core.base.action.RenderController;
import com.bfly.core.web.WebCoreErrors;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Locale;

/**
 * 图片上传Controller
 *
 * @author andy_hulibo@163.com
 * @date 2018/11/30 16:54
 */
@Controller
public class ImageUploadAct extends RenderController {
    private static final Logger log = LoggerFactory.getLogger(ImageUploadAct.class);

    /**
     * 用户头像路径
     */
    private static final String USER_IMG_PATH = "/user/images";

    /**
     * 错误信息参数
     */
    public static final String ERROR = "error";

    @RequestMapping("/member/o_upload_image.html")
    public String execute(String filename, Integer uploadNum, Boolean mark, @RequestParam(value = "uploadFile", required = false) MultipartFile file, ModelMap model) {
        WebCoreErrors errors = validate(filename, file);
        CmsSite site = getSite();
        CmsUser user = getUser();
        MemberConfig mcfg = site.getConfig().getMemberConfig();
        if (!mcfg.isMemberOn()) {
            return renderMessagePage(model, "member.memberClose");
        }
        if (user == null) {
            return renderLoginPage(model);
        }
        if (errors.hasErrors()) {
            model.addAttribute(ERROR, errors.getErrors().get(0));
            return renderPage("member/iframe_upload.html", model);
        }
        MarkConfig conf = site.getConfig().getMarkConfig();
        if (mark == null) {
            mark = conf.getOn();
        }
        String origName = file.getOriginalFilename();
        String ext = FilenameUtils.getExtension(origName).toLowerCase(Locale.ENGLISH);
        try {
            String fileUrl;
            if (site.getConfig().getUploadToDb()) {
                String dbFilePath = site.getConfig().getDbFileUri();
                if (!StringUtils.isBlank(filename)) {
                    filename = filename.substring(dbFilePath.length());
                    if (mark) {
                        File tempFile = mark(file, conf);
                        fileUrl = dbFileMng.storeByFilename(filename, new FileInputStream(tempFile));
                        tempFile.delete();
                    } else {
                        fileUrl = dbFileMng.storeByFilename(filename, file.getInputStream());
                    }
                } else {
                    if (mark) {
                        File tempFile = mark(file, conf);
                        fileUrl = dbFileMng.storeByExt(site.getUploadPath(), ext, new FileInputStream(tempFile));
                        tempFile.delete();
                    } else {
                        fileUrl = dbFileMng.storeByExt(site.getUploadPath(), ext, file.getInputStream());
                    }
                    // 加上访问地址
                    fileUrl = getRequest().getContextPath() + dbFilePath + fileUrl;
                }
            } else if (site.getUploadFtp() != null) {
                Ftp ftp = site.getUploadFtp();
                String ftpUrl = ftp.getUrl();
                if (!StringUtils.isBlank(filename)) {
                    filename = filename.substring(ftpUrl.length());
                    if (mark) {
                        File tempFile = mark(file, conf);
                        fileUrl = ftp.storeByFilename(filename, new FileInputStream(tempFile));
                        tempFile.delete();
                    } else {
                        fileUrl = ftp.storeByFilename(filename, file.getInputStream());
                    }
                } else {
                    if (mark) {
                        File tempFile = mark(file, conf);
                        fileUrl = ftp.storeByExt(site.getUploadPath(), ext, new FileInputStream(tempFile));
                        tempFile.delete();
                    } else {
                        fileUrl = ftp.storeByExt(site.getUploadPath(), ext, file.getInputStream());
                    }
                    // 加上url前缀
                    fileUrl = ftpUrl + fileUrl;
                }
            } else if (site.getUploadOss() != null) {
                CmsOss oss = site.getUploadOss();
                if (mark) {
                    File tempFile = mark(file, conf);
                    fileUrl = oss.storeByExt(site.getUploadPath(), ext, new FileInputStream(tempFile));
                    tempFile.delete();
                } else {
                    fileUrl = oss.storeByExt(site.getUploadPath(), ext, file.getInputStream());
                }
            } else if (site.getUploadOss() != null) {
                CmsOss oss = site.getUploadOss();
                if (mark) {
                    File tempFile = mark(file, conf);
                    fileUrl = oss.storeByExt(site.getUploadPath(), ext, new FileInputStream(tempFile));
                    tempFile.delete();
                } else {
                    fileUrl = oss.storeByExt(site.getUploadPath(), ext, file.getInputStream());
                }
            } else {
                String ctx = getRequest().getContextPath();
                if (!StringUtils.isBlank(filename)) {
                    if (StringUtils.isNotBlank(ctx)) {
                        filename = filename.substring(ctx.length());
                    }
                    if (mark) {
                        File tempFile = mark(file, conf);
                        fileUrl = fileRepository.storeByFilename(filename, tempFile);
                        tempFile.delete();
                    } else {
                        fileUrl = fileRepository.storeByFilename(filename, file);
                    }
                } else {
                    if (mark) {
                        File tempFile = mark(file, conf);
                        fileUrl = fileRepository.storeByExt(USER_IMG_PATH, ext, tempFile);
                        tempFile.delete();
                    } else {
                        fileUrl = fileRepository.storeByExt(USER_IMG_PATH, ext, file);
                    }
                    // 加上部署路径
                    fileUrl = ctx + fileUrl;
                }
            }
            model.addAttribute("uploadPath", fileUrl);
            model.addAttribute("uploadNum", uploadNum);
        } catch (Exception e) {
            model.addAttribute(ERROR, e.getMessage());
            log.error("upload file error!", e);
        }
        return renderPage("member/iframe_upload.html", model);
    }

    private WebCoreErrors validate(String filename, MultipartFile file) {
        WebCoreErrors errors = WebCoreErrors.create(getRequest());
        if (file == null) {
            errors.addErrorCode("imageupload.error.noFileToUpload");
            return errors;
        }
        if (StringUtils.isBlank(filename)) {
            filename = file.getOriginalFilename();
        }
        String ext = FilenameUtils.getExtension(filename);
        if (filename != null && (filename.contains("/") || filename.contains("\\") || filename.indexOf("\0") != -1)) {
            errors.addErrorCode("upload.error.filename", filename);
            return errors;
        }
        if (!ImageUtils.isValidImageExt(ext)) {
            errors.addErrorCode("imageupload.error.notSupportExt", ext);
            return errors;
        }
        try {
            if (!ImageUtils.isImage(file.getInputStream())) {
                errors.addErrorCode("imageupload.error.notImage", ext);
                return errors;
            }
        } catch (IOException e) {
            log.error("image upload error", e);
            errors.addErrorCode("imageupload.error.ioError", ext);
            return errors;
        }
        return errors;
    }

    private File mark(MultipartFile file, MarkConfig conf) throws Exception {
        String path = System.getProperty("java.io.tmpdir");
        File tempFile = new File(path, String.valueOf(System
                .currentTimeMillis()));
        file.transferTo(tempFile);
        boolean imgMark = !StringUtils.isBlank(conf.getImagePath());
        if (imgMark) {
            File markImg = new File(realPathResolver.get(conf.getImagePath()));
            imageScale.imageMark(tempFile, tempFile, conf.getMinWidth(), conf
                    .getMinHeight(), conf.getPos(), conf.getOffsetX(), conf
                    .getOffsetY(), markImg);
        } else {
            imageScale.imageMark(tempFile, tempFile, conf.getMinWidth(), conf
                    .getMinHeight(), conf.getPos(), conf.getOffsetX(), conf
                    .getOffsetY(), conf.getContent(), Color.decode(conf
                    .getColor()), conf.getSize(), conf.getAlpha());
        }
        return tempFile;
    }

    @Autowired
    private FileRepository fileRepository;
    @Autowired
    private DbFileMng dbFileMng;
    @Autowired
    private ImageScale imageScale;
    @Autowired
    private RealPathResolver realPathResolver;
}