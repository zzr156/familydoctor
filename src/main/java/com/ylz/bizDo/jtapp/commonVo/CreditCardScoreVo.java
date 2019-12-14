package com.ylz.bizDo.jtapp.commonVo;

/**
 * Created by zzl on 2018/12/12.
 */
public class CreditCardScoreVo {
    private String orgId;//机构id
    private String drId;//医生编号
    private String idNo;//身份证号
    private String yxsksj;//有效刷卡时间 格式YYYY-MM-DD HH:mm:ss
    private String sbkh;//社保卡号
    private String urlType;

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getDrId() {
        return drId;
    }

    public void setDrId(String drId) {
        this.drId = drId;
    }

    public String getIdNo() {
        return idNo;
    }

    public void setIdNo(String idNo) {
        this.idNo = idNo;
    }

    public String getYxsksj() {
        return yxsksj;
    }

    public void setYxsksj(String yxsksj) {
        this.yxsksj = yxsksj;
    }

    public String getSbkh() {
        return sbkh;
    }

    public void setSbkh(String sbkh) {
        this.sbkh = sbkh;
    }

    public String getUrlType() {
        return urlType;
    }

    public void setUrlType(String urlType) {
        this.urlType = urlType;
    }
}
