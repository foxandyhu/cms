package com.bfly.cms.entity.main;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class ContentPicture implements Serializable {
    private static final long serialVersionUID = 1L;

    @Column(name = "img_path")
    private String imgPath;

    @Column(name = "description")
    private String description;


    public String getImgPath() {
        return imgPath;
    }


    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


}