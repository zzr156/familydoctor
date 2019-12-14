package com.ylz.bizDo.jtapp.commonVo.booldSugr;

/**
 * Created by asus on 2018/05/10.
 */
public class AppMsgdata {
    private String code;//数据唯一标识
    private String devicesn;//设备唯一标识
    private String usercode;//用户标识
    private String openid;//微信用户openid
    private String result;//血糖测试结果
    private String unit;//单位
    private String testtime;//测试时间
    private String aimstatus;//
    private String createtime;
    private String foodstatus;//血糖测试时间段标志位foodstatus : 0餐前 1餐后 2随机 ，具体按（金智/亲智）设备按钮点击来确定


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDevicesn() {
        return devicesn;
    }

    public void setDevicesn(String devicesn) {
        this.devicesn = devicesn;
    }

    public String getUsercode() {
        return usercode;
    }

    public void setUsercode(String usercode) {
        this.usercode = usercode;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getTesttime() {
        return testtime;
    }

    public void setTesttime(String testtime) {
        this.testtime = testtime;
    }

    public String getAimstatus() {
        return aimstatus;
    }

    public void setAimstatus(String aimstatus) {
        this.aimstatus = aimstatus;
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    public String getFoodstatus() {
        return foodstatus;
    }

    public void setFoodstatus(String foodstatus) {
        this.foodstatus = foodstatus;
    }
}
