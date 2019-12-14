package com.ylz.bizDo.news.vo;

import com.ylz.packcommon.common.CommConditionVo;

/**
 * Created by zzl on 2017/9/14.
 */
public class NewsTableQqvo extends CommConditionVo {
    private String patientName;
    private String drId;//医生id
    private String areaCode;//区域code
    private String type;//1管理员 2医生用户
    private String showNum;

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getDrId() {
        return drId;
    }

    public void setDrId(String drId) {
        this.drId = drId;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getShowNum() {
        return showNum;
    }

    public void setShowNum(String showNum) {
        this.showNum = showNum;
    }
}
