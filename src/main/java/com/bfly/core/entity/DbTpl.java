package com.bfly.core.entity;

import com.bfly.common.web.Constants;
import com.bfly.core.tpl.Tpl;
import org.apache.commons.lang.StringUtils;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.util.Assert;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.bfly.common.web.Constants.SPT;

/**
 * 模板类
 *
 * @author andy_hulibo@163.com
 * @date 2018/11/15 22:54
 */
@Entity
@Table(name = "jo_template")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "beanCache")
public class DbTpl implements Tpl, Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 模板名称
     */
    @Id
    @Column(name = "tpl_name")
    private String id;

    /**
     * 模板内容
     */
    @Column(name = "tpl_source")
    private String source;

    /**
     * 最后修改时间
     */
    @Column(name = "last_modified")
    private long lastModified;

    /**
     * 是否目录
     */
    @Column(name = "is_directory")
    private boolean directory;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }


    @Override
    public long getLastModified() {
        return lastModified;
    }


    public void setLastModified(long lastModified) {
        this.lastModified = lastModified;
    }


    @Override
    public boolean isDirectory() {
        return directory;
    }

    public void setDirectory(boolean directory) {
        this.directory = directory;
    }


    /**
     * 获得文件夹或文件的所有父文件夹
     *
     * @param path
     * @return
     */
    public static String[] getParentDir(String path) {
        Assert.notNull(path);
        if (!path.startsWith(SPT)) {
            throw new IllegalArgumentException("path must start with /");
        }
        List<String> list = new ArrayList<String>();
        int index = path.indexOf(SPT, 1);
        while (index >= 0) {
            list.add(path.substring(0, index));
            index = path.indexOf(SPT, index + 1);
        }
        String[] arr = new String[list.size()];
        return list.toArray(arr);
    }

    @Override
    public String getName() {
        return getId();
    }

    @Override
    public String getPath() {
        String name = getId();
        return getId().substring(0, name.lastIndexOf("/"));
    }

    @Override
    public String getFilename() {
        String name = getId();
        if (!StringUtils.isBlank(name)) {
            int index = name.lastIndexOf(Constants.SPT);
            if (index != -1) {
                return name.substring(index + 1, name.length());
            }
        }
        return name;
    }

    @Override
    public long getLength() {
        if (isDirectory() || getSource() == null) {
            return 128;
        } else {
            // 一个英文字符占1个字节，而一个中文则占3-4字节，这里取折中一个字符1.5个字节
            return (long) (getSource().length() * 1.5);
        }
    }

    @Override
    public int getSize() {
        return (int) (getLength() / 1024) + 1;
    }

    @Override
    public Date getLastModifiedDate() {
        return new Timestamp(getLastModified());
    }

}