package com.ylz.bizDo.Motoe.dao.vo;

/**
 * Created by cjw on 2018/6/5.
 * 基卫 死亡解约 发起家签请求 vo
 *
 */
public class SignsurrenderVo {


    private String ptIdNo;  // 身份证
    private String ptJmdah; // 居民档案号
    private String reason ; // 原因
    private String signDieDate; // 死亡时间
    private String signOperatorName; // 操作人
    public String getPtIdNo() {
        return ptIdNo;
    }

    public void setPtIdNo(String ptIdNo) {
        this.ptIdNo = ptIdNo;
    }

    public String getPtJmdah() {
        return ptJmdah;
    }

    public void setPtJmdah(String ptJmdah) {
        this.ptJmdah = ptJmdah;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getSignDieDate() {
        return signDieDate;
    }

    public void setSignDieDate(String signDieDate) {
        this.signDieDate = signDieDate;
    }

    public String getSignOperatorName() {
        return signOperatorName;
    }

    public void setSignOperatorName(String signOperatorName) {
        this.signOperatorName = signOperatorName;
    }
}