package com.bfly.cms.entity.main;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;

/**
 * @author Tom
 */
@Embeddable
public class ChannelModel implements Serializable {

    @Column(name = "tpl_content")
    private String tplContent;

    @Column(name = "tpl_mobile_content")
    private String tplMoibleContent;

    @ManyToOne
    @JoinColumn(name = "model_id")
    private CmsModel model;


    public String getTplContent() {
        return tplContent;
    }

    public void setTplContent(String tplContent) {
        this.tplContent = tplContent;
    }

    public String getTplMoibleContent() {
        return tplMoibleContent;
    }

    public void setTplMoibleContent(String tplMoibleContent) {
        this.tplMoibleContent = tplMoibleContent;
    }

    public CmsModel getModel() {
        return model;
    }

    public void setModel(CmsModel model) {
        this.model = model;
    }
}
