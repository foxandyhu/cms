//package com.bfly.web.member.action;
//
//import java.awt.Color;
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.IOException;
//import java.util.Locale;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import com.bfly.cms.system.entity.Oss;
//import com.bfly.cms.member.entity.Member;
//import com.bfly.core.web.WebCoreErrors;
//import com.bfly.core.web.WebErrors;
//import org.apache.commons.io.FilenameUtils;
//import org.apache.commons.lang.StringUtils;
//import org.json.JSONObject;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.ModelMap;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.multipart.MultipartFile;
//
//import com.bfly.cms.resource.service.CmsFileMng;
//import com.bfly.common.image.ImageScale;
//import com.bfly.common.image.ImageUtils;
//import com.bfly.common.upload.FileRepository;
//import com.bfly.common.web.ResponseUtils;
//import com.bfly.common.web.springmvc.RealPathResolver;
//import com.bfly.cms.siteconfig.entity.Site;
//import com.bfly.cms.system.entity.Ftp;
//import com.bfly.cms.system.entity.MarkConfig;
//import com.bfly.cms.user.service.CmsUserMng;
//import com.bfly.cms.resource.service.DbFileMng;
//import com.bfly.core.web.util.CmsUtils;
//
///**
// * @author Tom
// */
//@Controller
//public class CommonUpload {
//
//	protected void swfPicsUpload(
//			String root,
//			Integer uploadNum,
//			@RequestParam(value = "Filedata", required = false) MultipartFile file,
//			HttpServletRequest request, HttpServletResponse response,
//			ModelMap model) throws Exception {
//		JSONObject data=new JSONObject();
//		WebCoreErrors errors = validateImage(file, request);
//		if (errors.hasErrors()) {
//			data.put("error", errors.getErrors().get(0));
//			ResponseUtils.renderJson(response, data.toString());
//		}else{
//			Site site = CmsUtils.getSite(request);
//			String ctx = request.getContextPath();
//			MarkConfig conf = site.getConfig().getMarkConfig();
//			Boolean mark = conf.getOn();
//			String origName = file.getOriginalFilename();
//			String ext = FilenameUtils.getExtension(origName).toLowerCase(
//					Locale.ENGLISH);
//			String fileUrl;
//			if (site.getUploadFtp() != null) {
//				Ftp ftp = site.getUploadFtp();
//				String ftpUrl = ftp.getUrl();
//				if (mark) {
//					File tempFile = mark(file, conf);
//					fileUrl = ftp.storeByExt(site.getUploadPath(), ext,
//							new FileInputStream(tempFile));
//					tempFile.delete();
//				} else {
//					fileUrl = ftp.storeByExt(site.getUploadPath(), ext, file
//							.getInputStream());
//				}
//				// 加上url前缀
//				fileUrl = ftpUrl + fileUrl;
//			}else if (site.getUploadOss() != null) {
//				Oss oss = site.getUploadOss();
//				if (mark) {
//					File tempFile = mark(file, conf);
//					fileUrl=oss.storeByExt(site.getUploadPath(), ext, new FileInputStream(tempFile));
//					tempFile.delete();
//				} else {
//					fileUrl = oss.storeByExt(site.getUploadPath(), ext, file.getInputStream());
//				}
//			} else {
//				if (mark) {
//					File tempFile = mark(file, conf);
//					fileUrl = fileRepository.storeByExt(site.getUploadPath(), ext,
//							tempFile);
//					tempFile.delete();
//				} else {
//					fileUrl = fileRepository.storeByExt(site.getUploadPath(), ext,
//							file);
//				}
//				// 加上部署路径
//				if(StringUtils.isNotBlank(ctx)){
//					fileUrl = ctx + fileUrl;
//				}
//			}
//			cmsUserMng.updateUploadSize(CmsUtils.getUserId(request), Integer.parseInt(String.valueOf(file.getSize()/1024)));
//			data.put("imgUrl", fileUrl);
//			ResponseUtils.renderText(response, data.toString());
//		}
//	}
//
//	protected void swfAttachsUpload(
//			String root,
//			Integer uploadNum,
//			@RequestParam(value = "Filedata", required = false) MultipartFile file,
//			HttpServletRequest request, HttpServletResponse response,
//			ModelMap model) throws Exception {
//		JSONObject data=new JSONObject();
//		WebCoreErrors errors = validateUpload( file, request);
//		if (errors.hasErrors()) {
//			data.put("error", errors.getErrors().get(0));
//			ResponseUtils.renderJson(response, data.toString());
//		}else{
//			Site site = CmsUtils.getSite(request);
//			String ctx = request.getContextPath();
//			String origName = file.getOriginalFilename();
//			String ext = FilenameUtils.getExtension(origName).toLowerCase(
//					Locale.ENGLISH);
//			// TODO 检查允许上传的后缀
//			String fileUrl="";
//			try {
//				if (site.getConfig().getUploadToDb()) {
//					String dbFilePath = site.getConfig().getDbFileUri();
//					fileUrl = dbFileMng.storeByExt(site.getUploadPath(), ext, file
//							.getInputStream());
//					// 加上访问地址
//					fileUrl = request.getContextPath() + dbFilePath + fileUrl;
//				} else if (site.getUploadFtp() != null) {
//					Ftp ftp = site.getUploadFtp();
//					String ftpUrl = ftp.getUrl();
//					fileUrl = ftp.storeByExt(site.getUploadPath(), ext, file
//							.getInputStream());
//					// 加上url前缀
//					fileUrl = ftpUrl + fileUrl;
//				}else if (site.getUploadOss() != null) {
//					Oss oss = site.getUploadOss();
//					fileUrl = oss.storeByExt(site.getUploadPath(), ext, file.getInputStream());
//				} else {
//					fileUrl = fileRepository.storeByExt(site.getUploadPath(), ext,
//							file);
//					// 加上部署路径
//					fileUrl = ctx + fileUrl;
//				}
//				cmsUserMng.updateUploadSize(CmsUtils.getUserId(request), Integer.parseInt(String.valueOf(file.getSize()/1024)));
//				fileMng.saveFileByPath(fileUrl, origName, false);
//				model.addAttribute("attachmentPath", fileUrl);
//			} catch (IllegalStateException e) {
//				model.addAttribute("error", e.getMessage());
//			} catch (IOException e) {
//				model.addAttribute("error", e.getMessage());
//			}
//			data.put("attachUrl", fileUrl);
//			data.put("attachName", origName);
//			ResponseUtils.renderJson(response, data.toString());
//		}
//	}
//	/**
//	 * 验证图片（图片有格式需求系统设定）
//	 * @param file
//	 * @param request
//	 * @return
//	 */
//	protected WebCoreErrors validateImage(MultipartFile file,
//										  HttpServletRequest request) {
//		Member user=CmsUtils.getMember(request);
//		WebCoreErrors errors = WebCoreErrors.create(request);
//		if (file == null) {
//			errors.addErrorCode("imageupload.error.noFileToUpload");
//			return errors;
//		}
//		String filename=file.getOriginalFilename();
//		if (StringUtils.isBlank(filename)) {
//			filename = file.getOriginalFilename();
//		}
//		if(filename!=null&&filename.indexOf("\0")!=-1){
//			errors.addErrorCode("upload.error.filename", filename);
//		}
//		String ext = FilenameUtils.getExtension(filename);
//		if (!ImageUtils.isValidImageExt(ext)) {
//			errors.addErrorCode("imageupload.error.notSupportExt", ext);
//			return errors;
//		}
//		try {
//			if (!ImageUtils.isImage(file.getInputStream())) {
//				errors.addErrorCode("imageupload.error.notImage", ext);
//				return errors;
//			}
//		} catch (IOException e) {
//			errors.addErrorCode("imageupload.error.ioError", ext);
//			return errors;
//		}
//		//验证文件大小
//		errors=validateFile(file, user, request);
//		return errors;
//	}
//
//	/**
//	 * 验证附件
//	 * @param file
//	 * @param request
//	 * @return
//	 */
//	protected WebErrors validateUpload(MultipartFile file,
//									   HttpServletRequest request) {
//		String origName = file.getOriginalFilename();
//		Member user=CmsUtils.getMember(request);
//		String ext = FilenameUtils.getExtension(origName).toLowerCase(
//				Locale.ENGLISH);
//		WebErrors errors = WebErrors.create(request);
//		if (errors.ifNull(file, "file", true)) {
//			return errors;
//		}
//		if(origName!=null&&origName.indexOf("\0")!=-1){
//			errors.addErrorCode("upload.error.filename", origName);
//		}
//		//非允许的后缀
//		if(!user.isAllowSuffix(ext)){
//			errors.addErrorCode("upload.error.invalidsuffix", ext);
//			return errors;
//		}
//		errors=validateFile(file, user, request);
//		return errors;
//	}
//
//	protected WebErrors validateFile(MultipartFile file, Member user,
//									 HttpServletRequest request) {
//		String origName = file.getOriginalFilename();
//		int fileSize = (int) (file.getSize() / 1024);
//		WebErrors errors = WebErrors.create(request);
//		if(origName!=null&&origName.indexOf("\0")!=-1){
//			errors.addErrorCode("upload.error.filename", origName);
//		}
//		//超过附件大小限制
//		if(!user.isAllowMaxFile((int)(file.getSize()/1024))){
//			errors.addErrorCode("upload.error.toolarge",origName,user.getGroup().getAllowUploadMaxFile());
//			return errors;
//		}
//		//超过每日上传限制
//		if (!user.isAllowPerDay(fileSize)) {
//			long laveSize=user.getGroup().getAllowUploadPerDay()-user.getUploadSize();
//			if(laveSize<0){
//				laveSize=0;
//			}
//			errors.addErrorCode("upload.error.dailylimit", laveSize);
//		}
//		return errors;
//	}
//
//	protected File mark(MultipartFile file, MarkConfig conf) throws Exception {
//		String path = System.getProperty("java.io.tmpdir");
//		File tempFile = new File(path, String.valueOf(System
//				.currentTimeMillis()));
//		file.transferTo(tempFile);
//		boolean imgMark = !StringUtils.isBlank(conf.getImagePath());
//		if (imgMark) {
//			File markImg = new File(realPathResolver.get(conf.getImagePath()));
//			imageScale.imageMark(tempFile, tempFile, conf.getMinWidth(), conf
//					.getMinHeight(), conf.getPos(), conf.getOffsetX(), conf
//					.getOffsetY(), markImg);
//		} else {
//			imageScale.imageMark(tempFile, tempFile, conf.getMinWidth(), conf
//					.getMinHeight(), conf.getPos(), conf.getOffsetX(), conf
//					.getOffsetY(), conf.getContent(), Color.decode(conf
//					.getColor()), conf.getSize(), conf.getAlpha());
//		}
//		return tempFile;
//	}
//	@Autowired
//	protected FileRepository fileRepository;
//	@Autowired
//	protected DbFileMng dbFileMng;
//	@Autowired
//	protected ImageScale imageScale;
//	@Autowired
//	protected RealPathResolver realPathResolver;
//	@Autowired
//	protected CmsFileMng fileMng;
//	@Autowired
//	protected CmsUserMng cmsUserMng;
//}
