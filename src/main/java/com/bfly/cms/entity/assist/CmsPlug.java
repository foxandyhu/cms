package com.bfly.cms.entity.assist;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 插件
 *
 * @author andy_hulibo@163.com
 * @date 2018/11/16 17:42
 */
@Entity
@Table(name = "jc_plug")
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

    @Id
    @Column(name = "plug_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 插件名称
     */
    @Column(name = "name")
    private String name;

    /**
     * 文件路径
     */
    @Column(name = "path")
    private String path;

    /**
     * 描述
     */
    @Column(name = "description")
    private String description;

    /**
     * 作者
     */
    @Column(name = "author")
    private String author;

    /**
     * 上传时间
     */
    @Column(name = "upload_time")
    private Date uploadTime;

    /**
     * 安装时间
     */
    @Column(name = "install_time")
    private Date installTime;

    /**
     * 卸载时间
     */
    @Column(name = "uninstall_time")
    private Date uninstallTime;

    /**
     * 包含文件是否冲突
     */
    @Column(name = "file_conflict")
    private Boolean fileConflict;

    /**
     * 使用状态(0未使用,1使用中)
     */
    @Column(name = "is_used")
    private Boolean used;

    /**
     * 插件权限（,分隔各个权限配置）
     */
    @Column(name = "plug_perms")
    private String plugPerms;

    /**
     * 是否修复类插件(0 新功能插件 1修复类)
     */
    @Column(name = "plug_repair")
    private boolean plugRepair;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }


    public String getDescription() {
        return description;
    }


    public void setDescription(String description) {
        this.description = description;
    }


    public String getAuthor() {
        return author;
    }


    public void setAuthor(String author) {
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


    public String getPlugPerms() {
        return plugPerms;
    }


    public void setPlugPerms(String plugPerms) {
        this.plugPerms = plugPerms;
    }

    public boolean getPlugRepair() {
        return plugRepair;
    }

    public void setPlugRepair(boolean plugRepair) {
        this.plugRepair = plugRepair;
    }


}