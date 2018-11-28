package com.bfly.cms.resource.service;

import com.bfly.cms.content.entity.Content;
import com.bfly.cms.resource.entity.CmsFile;

import java.util.List;

/**
 * 附件管理业务接口
 *
 * @author andy_hulibo@163.com
 * @date 2018/11/26 14:51
 */
public interface CmsFileMng {
    List<CmsFile> getList(Boolean valid);

    CmsFile findById(Integer id);

    CmsFile findByPath(String path);

    CmsFile save(CmsFile bean);

    CmsFile update(CmsFile bean);

    CmsFile deleteById(Integer id);

    CmsFile deleteByPath(String path);

    void deleteByContentId(Integer contentId);

    void saveFileByPath(String filepath, String name, Boolean valid);

    void updateFileByPath(String path, Boolean valid, Content c);

    void updateFileByPaths(String[] attachmentPaths, String[] picPaths, String mediaPath,
                           String titleImg, String typeImg, String contentImg, Boolean valid, Content c);
}