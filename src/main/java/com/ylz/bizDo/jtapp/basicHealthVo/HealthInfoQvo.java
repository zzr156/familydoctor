package com.ylz.bizDo.jtapp.basicHealthVo;

/**
 * Created by zzl on 2017/7/21.
 */
public class HealthInfoQvo {
    private String idNo ;
    private String card;
    private String type;//1：门诊2：住院 3：体检4：妇幼

    public String getIdNo() {
        return idNo;
    }

    public void setIdNo(String idNo) {
        this.idNo = idNo;
    }

    public String getCard() {
        return card;
    }

    public void setCard(String card) {
        this.card = card;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
