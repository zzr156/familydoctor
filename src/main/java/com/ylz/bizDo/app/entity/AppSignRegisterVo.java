package com.ylz.bizDo.app.entity;

/**
 * Created by lenovo on 2018/1/4.
 */
public class AppSignRegisterVo {


    private String id;
    private String flowNo;//流水号
    private String name;//姓名
    private String sfzh;//身份证号
    private String id00;//医疗证号/社保卡号
    private String cbdw;//参保单位/乡村组
    private String signMoney;//签约服务费
    private String jjzfe;//基金支付金额
    private String grzfe;//个人自付金额
    private String gwzfe;//公卫支付金额


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFlowNo() {
        return flowNo;
    }

    public void setFlowNo(String flowNo) {
        this.flowNo = flowNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSfzh() {
        return sfzh;
    }

    public void setSfzh(String sfzh) {
        this.sfzh = sfzh;
    }

    public String getId00() {
        return id00;
    }

    public void setId00(String id00) {
        this.id00 = id00;
    }

    public String getCbdw() {
        return cbdw;
    }

    public void setCbdw(String cbdw) {
        this.cbdw = cbdw;
    }

    public String getSignMoney() {
        return signMoney;
    }

    public void setSignMoney(String signMoney) {
        this.signMoney = signMoney;
    }

    public String getJjzfe() {
        return jjzfe;
    }

    public void setJjzfe(String jjzfe) {
        this.jjzfe = jjzfe;
    }

    public String getGrzfe() {
        return grzfe;
    }

    public void setGrzfe(String grzfe) {
        this.grzfe = grzfe;
    }

    public String getGwzfe() {
        return gwzfe;
    }

    public void setGwzfe(String gwzfe) {
        this.gwzfe = gwzfe;
    }
}
