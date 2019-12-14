package com.ylz.bizDo.jtapp.patientVo;

import com.ylz.packcommon.common.CommConditionVo;

/**查询儿童体检信息条件
 * Created by zzl on 2017/6/22.
 */
public class AppChildHealthQvo extends CommConditionVo {
    private String birthDay;//出生年月
    private String userId;//用户id
    private String childUserId;//儿童主键
    private String childUserName;//儿童姓名
    private String jan;//满月
    private String mar;//三月
    private String jun;//6月
    private String aug;//8月
    private String dec;//12月
    private String etMounth;//18月
    private String tfMounth;//24月
    private String ttyMounth;//30月
    private String threeYear;//3年
    private String fourYear;//4年
    private String fiveYear;//5年
    private String sexYear;//6年

    public String getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(String birthDay) {
        this.birthDay = birthDay;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getJan() {
        return jan;
    }

    public void setJan(String jan) {
        this.jan = jan;
    }

    public String getMar() {
        return mar;
    }

    public void setMar(String mar) {
        this.mar = mar;
    }

    public String getJun() {
        return jun;
    }

    public void setJun(String jun) {
        this.jun = jun;
    }

    public String getAug() {
        return aug;
    }

    public void setAug(String aug) {
        this.aug = aug;
    }

    public String getDec() {
        return dec;
    }

    public void setDec(String dec) {
        this.dec = dec;
    }

    public String getEtMounth() {
        return etMounth;
    }

    public void setEtMounth(String etMounth) {
        this.etMounth = etMounth;
    }

    public String getTfMounth() {
        return tfMounth;
    }

    public void setTfMounth(String tfMounth) {
        this.tfMounth = tfMounth;
    }

    public String getTtyMounth() {
        return ttyMounth;
    }

    public void setTtyMounth(String ttyMounth) {
        this.ttyMounth = ttyMounth;
    }

    public String getThreeYear() {
        return threeYear;
    }

    public void setThreeYear(String threeYear) {
        this.threeYear = threeYear;
    }

    public String getFourYear() {
        return fourYear;
    }

    public void setFourYear(String fourYear) {
        this.fourYear = fourYear;
    }

    public String getFiveYear() {
        return fiveYear;
    }

    public void setFiveYear(String fiveYear) {
        this.fiveYear = fiveYear;
    }

    public String getSexYear() {
        return sexYear;
    }

    public void setSexYear(String sexYear) {
        this.sexYear = sexYear;
    }

    public String getChildUserId() {
        return childUserId;
    }

    public void setChildUserId(String childUserId) {
        this.childUserId = childUserId;
    }

    public String getChildUserName() {
        return childUserName;
    }

    public void setChildUserName(String childUserName) {
        this.childUserName = childUserName;
    }
}
