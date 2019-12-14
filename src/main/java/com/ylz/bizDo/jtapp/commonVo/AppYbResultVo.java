package com.ylz.bizDo.jtapp.commonVo;

import java.math.BigDecimal;

/**
 * 医保对接新增返回的结果
 * Created by WangCheng on 2019/03/07.
 */
public class AppYbResultVo {

    private String flag;//返回信息码
    private String cause;//操作结果
    private String usr;//用户名
    private String pwd;//密码
    private String signFromDate;//开始时间
    private String signEndDate;//截止时间

    private String akc190; //医保签约流水号
    private String aae071; //医保冲销流水号
    private String aae072; //医保收费流水号
    private String bke050; //机构收费业务流水号
    private String aac003; //姓名
    private String aac002; //身份证号
    private String aaz501; //医疗证号/社保卡号
    private String aab034; //参保人所属分中心名称
    private String aab001; //参保单位/乡村组
    private String akc227; //签约服务费
    private String bkc045; //基金支付金额
    private String bkc041; //账户支付金额
    private String bkc040; //个人自付金额
    private String bkc058; //公卫支付金额
    private String ake059; //实际收费时间
    private String bkc006;   //签约医生姓名
    private String bke595;   //签约机构
    private String bke598;   //开始时间
    private String bke599;   //截止时间


    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getCause() {
        return cause;
    }

    public void setCause(String cause) {
        this.cause = cause;
    }

    public String getUsr() {
        return usr;
    }

    public void setUsr(String usr) {
        this.usr = usr;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getSignFromDate() {
        return signFromDate;
    }

    public void setSignFromDate(String signFromDate) {
        this.signFromDate = signFromDate;
    }

    public String getSignEndDate() {
        return signEndDate;
    }

    public void setSignEndDate(String signEndDate) {
        this.signEndDate = signEndDate;
    }

    public String getAkc190() {
        return akc190;
    }

    public void setAkc190(String akc190) {
        this.akc190 = akc190;
    }

    public String getAae071() {
        return aae071;
    }

    public void setAae071(String aae071) {
        this.aae071 = aae071;
    }

    public String getAae072() {
        return aae072;
    }

    public void setAae072(String aae072) {
        this.aae072 = aae072;
    }

    public String getBke050() {
        return bke050;
    }

    public void setBke050(String bke050) {
        this.bke050 = bke050;
    }

    public String getAac003() {
        return aac003;
    }

    public void setAac003(String aac003) {
        this.aac003 = aac003;
    }

    public String getAac002() {
        return aac002;
    }

    public void setAac002(String aac002) {
        this.aac002 = aac002;
    }

    public String getAaz501() {
        return aaz501;
    }

    public void setAaz501(String aaz501) {
        this.aaz501 = aaz501;
    }

    public String getAab034() {
        return aab034;
    }

    public void setAab034(String aab034) {
        this.aab034 = aab034;
    }

    public String getAab001() {
        return aab001;
    }

    public void setAab001(String aab001) {
        this.aab001 = aab001;
    }

    public String getAkc227() {
        return akc227;
    }

    public void setAkc227(String akc227) {
        this.akc227 = akc227;
    }

    public String getBkc045() {
        return bkc045;
    }

    public void setBkc045(String bkc045) {
        this.bkc045 = bkc045;
    }

    public String getBkc041() {
        return bkc041;
    }

    public void setBkc041(String bkc041) {
        this.bkc041 = bkc041;
    }

    public String getBkc040() {
        return bkc040;
    }

    public void setBkc040(String bkc040) {
        this.bkc040 = bkc040;
    }

    public String getBkc058() {
        return bkc058;
    }

    public void setBkc058(String bkc058) {
        this.bkc058 = bkc058;
    }

    public String getAke059() {
        return ake059;
    }

    public void setAke059(String ake059) {
        this.ake059 = ake059;
    }

    public String getBkc006() {
        return bkc006;
    }

    public void setBkc006(String bkc006) {
        this.bkc006 = bkc006;
    }

    public String getBke595() {
        return bke595;
    }

    public void setBke595(String bke595) {
        this.bke595 = bke595;
    }

    public String getBke598() {
        return bke598;
    }

    public void setBke598(String bke598) {
        this.bke598 = bke598;
    }

    public String getBke599() {
        return bke599;
    }

    public void setBke599(String bke599) {
        this.bke599 = bke599;
    }
}