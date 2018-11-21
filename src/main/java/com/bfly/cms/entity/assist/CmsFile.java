package com.bfly.cms.entity.assist;

import com.bfly.cms.entity.main.Content;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "jc_file")
public class CmsFile implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "file_path")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String filePath;

    /**
     * 文件名字
     */
    @Column(name = "file_name")
    private String fileName;

    /**
     * 是否有效
     */
    @Column(name = "file_isvalid")
    private boolean fileIsvalid;

    /**
     * 所属内容
     */
    @ManyToOne
    @JoinColumn(name = "content_id")
    private Content content;

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public boolean isFileIsvalid() {
        return fileIsvalid;
    }


    public void setFileIsvalid(boolean fileIsvalid) {
        this.fileIsvalid = fileIsvalid;
    }


    public Content getContent() {
        return content;
    }

    public void setContent(Content content) {
        this.content = content;
    }


}