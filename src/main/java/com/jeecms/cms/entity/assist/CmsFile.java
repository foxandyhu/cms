package com.jeecms.cms.entity.assist;

import com.jeecms.cms.entity.main.Content;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;


public class CmsFile implements Serializable {
    private static final long serialVersionUID = 1L;

    // primary key
    private String filePath;

    // fields
    private String fileName;
    private boolean fileIsvalid;

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