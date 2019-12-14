package com.ylz.bizDo.pay.vo;

/**
 * Created by asus on 2017/07/25.
 */
public class PayAppVo {
    private String signId;//签约单主键
    private String orderNo;//订单号
    private String patientId;//患者主键

    public String getSignId() {
        return signId;
    }

    public void setSignId(String signId) {
        this.signId = signId;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }
}
