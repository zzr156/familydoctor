package com.ylz.bizDo.jtapp.basicHealthVo;

/**
 * Created by asus on 2017/10/31.
 */
public class ReferralQvo {

    private String idno;//身份证
    private String orgid;//转出医院
    private String urlType;//类型

    public String getIdno() {
        return idno;
    }

    public void setIdno(String idno) {
        this.idno = idno;
    }

    public String getOrgid() {
        return orgid;
    }

    public void setOrgid(String orgid) {
        this.orgid = orgid;
    }

    public String getUrlType() {
        return urlType;
    }

    public void setUrlType(String urlType) {
        this.urlType = urlType;
    }
}
