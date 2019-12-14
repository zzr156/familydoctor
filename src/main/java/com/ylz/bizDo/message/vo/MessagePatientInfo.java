package com.ylz.bizDo.message.vo;

import com.ylz.packcommon.common.util.SericuryUtil;
import org.apache.commons.lang.StringUtils;

/**
 * Created by hzk on 2017/7/4.
 * 消息患者列表
 */
public class MessagePatientInfo {
    private String userId;//
    private String id;//患者id
    private String patientName;//患者姓名
    private String patientAge;//患者年龄
    private String patientGender;//患者性别
    private String patientImageurl;//患者头像
    private String labelTitle;//疾病标签
    private String labelColor;//标签颜色

    public String getId() {
        return id;
    }

    public void setId(String id) {
        if(StringUtils.isNotBlank(id)){
            try {
                SericuryUtil p = new SericuryUtil();
                this.id = p.encrypt(id);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getPatientAge() {
        return patientAge;
    }

    public void setPatientAge(String patientAge) {
        this.patientAge = patientAge;
    }

    public String getPatientGender() {
        return patientGender;
    }

    public void setPatientGender(String patientGender) {
        this.patientGender = patientGender;
    }

    public String getPatientImageurl() {
        return patientImageurl;
    }

    public void setPatientImageurl(String patientImageurl) {
        this.patientImageurl = patientImageurl;
    }

    public String getLabelTitle() {
        return labelTitle;
    }

    public void setLabelTitle(String labelTitle) {
        this.labelTitle = labelTitle;
    }

    public String getLabelColor() {
        return labelColor;
    }

    public void setLabelColor(String labelColor) {
        this.labelColor = labelColor;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
