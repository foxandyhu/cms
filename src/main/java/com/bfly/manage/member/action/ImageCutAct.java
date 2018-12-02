package com.bfly.manage.member.action;

import com.bfly.cms.resource.service.DbFileMng;
import com.bfly.cms.siteconfig.entity.CmsSite;
import com.bfly.cms.siteconfig.entity.Ftp;
import com.bfly.cms.system.entity.MemberConfig;
import com.bfly.common.image.ImageScale;
import com.bfly.common.upload.FileRepository;
import com.bfly.core.base.action.RenderController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.File;

/**
 * 图片裁剪Controller
 *
 * @author andy_hulibo@163.com
 * @date 2018/11/30 17:10
 */
@Controller
public class ImageCutAct extends RenderController {

    private static final Logger log = LoggerFactory.getLogger(ImageCutAct.class);

    /**
     * 错误信息参数
     */
    public static final String ERROR = "error";

    /**
     * 校验
     *
     * @author andy_hulibo@163.com
     * @date 2018/11/30 17:01
     */
    private String check(ModelMap model) {
        MemberConfig mcfg = getSite().getConfig().getMemberConfig();
        // 没有开启会员功能
        if (!mcfg.isMemberOn()) {
            return renderMessagePage(model, "member.memberClose");
        }
        if (getUser() == null) {
            return renderLoginPage(model);
        }
        return null;
    }

    @RequestMapping("/member/v_image_area_select.html")
    public String imageAreaSelect(String uploadBase, String imgSrcPath, Integer zoomWidth, Integer zoomHeight, Integer uploadNum, ModelMap model) {
        model.addAttribute("uploadBase", uploadBase);
        model.addAttribute("imgSrcPath", imgSrcPath);
        model.addAttribute("zoomWidth", zoomWidth);
        model.addAttribute("zoomHeight", zoomHeight);
        model.addAttribute("uploadNum", uploadNum);
        String result = check(model);
        if (result != null) {
            return result;
        }
        return renderPage("member/image_area_select.html", model);
    }

    @RequestMapping("/member/o_image_cut.html")
    public String imageCut(String imgSrcPath, Integer imgTop, Integer imgLeft, Integer imgWidth, Integer imgHeight, Integer reMinWidth, Integer reMinHeight, Float imgScale, Integer uploadNum, ModelMap model) {
        CmsSite site = getSite();
        try {
            if (imgWidth > 0) {
                if (site.getConfig().getUploadToDb()) {
                    String dbFilePath = site.getConfig().getDbFileUri();
                    imgSrcPath = imgSrcPath.substring(dbFilePath.length());
                    File file = dbFileMng.retrieve(imgSrcPath);
                    imageScale.resizeFix(file, file, reMinWidth, reMinHeight, getLen(imgTop, imgScale), getLen(imgLeft, imgScale), getLen(imgWidth, imgScale), getLen(imgHeight, imgScale));
                    dbFileMng.restore(imgSrcPath, file);
                } else if (site.getUploadFtp() != null) {
                    Ftp ftp = site.getUploadFtp();
                    String ftpUrl = ftp.getUrl();
                    imgSrcPath = imgSrcPath.substring(ftpUrl.length());
                    String fileName = imgSrcPath.substring(imgSrcPath.lastIndexOf("/"));
                    File file = ftp.retrieve(imgSrcPath, fileName);
                    imageScale.resizeFix(file, file, reMinWidth, reMinHeight, getLen(imgTop, imgScale), getLen(imgLeft, imgScale), getLen(imgWidth, imgScale), getLen(imgHeight, imgScale));
                    ftp.restore(imgSrcPath, file);
                } else {
                    String ctx = getRequest().getContextPath();
                    imgSrcPath = imgSrcPath.substring(ctx.length());
                    File file = fileRepository.retrieve(imgSrcPath);
                    imageScale.resizeFix(file, file, reMinWidth, reMinHeight, getLen(imgTop, imgScale), getLen(imgLeft, imgScale), getLen(imgWidth, imgScale), getLen(imgHeight, imgScale));
                }
            } else {
                if (site.getConfig().getUploadToDb()) {
                    String dbFilePath = site.getConfig().getDbFileUri();
                    imgSrcPath = imgSrcPath.substring(dbFilePath.length());
                    File file = dbFileMng.retrieve(imgSrcPath);
                    imageScale.resizeFix(file, file, reMinWidth, reMinHeight);
                    dbFileMng.restore(imgSrcPath, file);
                } else if (site.getUploadFtp() != null) {
                    Ftp ftp = site.getUploadFtp();
                    String ftpUrl = ftp.getUrl();
                    imgSrcPath = imgSrcPath.substring(ftpUrl.length());
                    String fileName = imgSrcPath.substring(imgSrcPath.lastIndexOf("/"));
                    File file = ftp.retrieve(imgSrcPath, fileName);
                    imageScale.resizeFix(file, file, reMinWidth, reMinHeight);
                    ftp.restore(imgSrcPath, file);
                } else {
                    String ctx = getRequest().getContextPath();
                    imgSrcPath = imgSrcPath.substring(ctx.length());
                    File file = fileRepository.retrieve(imgSrcPath);
                    imageScale.resizeFix(file, file, reMinWidth, reMinHeight);
                }
            }
            model.addAttribute("uploadNum", uploadNum);
        } catch (Exception e) {
            log.error("cut image error", e);
            model.addAttribute(ERROR, e.getMessage());
        }
        return renderPage("member/image_cuted.html", model);
    }

    private int getLen(int len, float imgScale) {
        return Math.round(len / imgScale);
    }

    private ImageScale imageScale;

    private FileRepository fileRepository;
    private DbFileMng dbFileMng;

    @Autowired
    public void setImageScale(ImageScale imageScale) {
        this.imageScale = imageScale;
    }

    @Autowired
    public void setFileRepository(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }

    @Autowired
    public void setDbFileMng(DbFileMng dbFileMng) {
        this.dbFileMng = dbFileMng;
    }

}
