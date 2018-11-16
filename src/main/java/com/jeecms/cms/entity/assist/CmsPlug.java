package com.jeecms.cms.entity.assist;

import java.io.Serializable;
import java.util.Date;


public class CmsPlug implements Serializable {
    private static final long serialVersionUID = 1L;

    public Boolean getUsed() {
        return isUsed();
    }

    public Boolean getFileConflict() {
        return isFileConflict();
    }

    public boolean getCanInstall() {
        //未使用 且(文件未冲突或者是修复类)
        if (!getUsed() && (!getFileConflict() || getPlugRepair())) {
            return true;
        } else {
            return false;
        }
    }

    public boolean getCanUnInstall() {
        //使用中的修复类插件和未使用的插件 不能卸载
        if ((getUsed() && getPlugRepair()) || !getUsed()) {
            return false;
        } else {
            return true;
        }
    }

    // primary key
    private java.lang.Integer id;

    // fields
    private java.lang.String name;
    private java.lang.String path;
    private java.lang.String description;
    private java.lang.String author;
    private Date uploadTime;
    private Date installTime;
    private Date uninstallTime;
    private Boolean fileConflict;
    private Boolean used;
    private java.lang.String plugPerms;
    private boolean plugRepair;


    public java.lang.Integer getId() {
        return id;
    }

    public void setId(java.lang.Integer id) {
        this.id = id;
    }

    public java.lang.String getName() {
        return name;
    }


    public void setName(java.lang.String name) {
        this.name = name;
    }

    public java.lang.String getPath() {
        return path;
    }

    public void setPath(java.lang.String path) {
        this.path = path;
    }


    public java.lang.String getDescription() {
        return description;
    }


    public void setDescription(java.lang.String description) {
        this.description = description;
    }


    public java.lang.String getAuthor() {
        return author;
    }


    public void setAuthor(java.lang.String author) {
        this.author = author;
    }


    public Date getUploadTime() {
        return uploadTime;
    }


    public void setUploadTime(Date uploadTime) {
        this.uploadTime = uploadTime;
    }

    public Date getInstallTime() {
        return installTime;
    }


    public void setInstallTime(Date installTime) {
        this.installTime = installTime;
    }

    public Date getUninstallTime() {
        return uninstallTime;
    }


    public void setUninstallTime(Date uninstallTime) {
        this.uninstallTime = uninstallTime;
    }


    public Boolean isFileConflict() {
        return fileConflict;
    }

    public void setFileConflict(boolean fileConflict) {
        this.fileConflict = fileConflict;
    }


    public Boolean isUsed() {
        return used;
    }


    public void setUsed(boolean used) {
        this.used = used;
    }


    public java.lang.String getPlugPerms() {
        return plugPerms;
    }


    public void setPlugPerms(java.lang.String plugPerms) {
        this.plugPerms = plugPerms;
    }

    public boolean getPlugRepair() {
        return plugRepair;
    }

    public void setPlugRepair(boolean plugRepair) {
        this.plugRepair = plugRepair;
    }


}