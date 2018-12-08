package com.bfly.cms.channel.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 * 栏目的模板配置
 *
 * @author andy_hulibo@163.com
 * @date 2018/12/5 17:47
 */
@Embeddable
public class ChannelTemplate implements Serializable {

    /**
     * PC端模板
     */
    @Column(name = "tpl_pc")
    private String tplPc;

    /**
     * 移动端模板
     */
    @Column(name = "tpl_mobile")
    private String tplMoible;

    public String getTplPc() {
        return tplPc;
    }

    public void setTplPc(String tplPc) {
        this.tplPc = tplPc;
    }

    public String getTplMoible() {
        return tplMoible;
    }

    public void setTplMoible(String tplMoible) {
        this.tplMoible = tplMoible;
    }
}
