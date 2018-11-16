package com.jeecms.cms.entity.main;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class ContentRewardFix implements Serializable {
    private static final long serialVersionUID = 1L;

    @Column(name = "reward_fix")
    private Double fixVal;


    public Double getFixVal() {
        return fixVal;
    }

    public void setFixVal(Double fixVal) {
        this.fixVal = fixVal;
    }

}