package com.ylz.bizDo.jtapp.commonVo;

import com.ylz.packcommon.common.CommConditionVo;

/**
 * Created by asus on 2017/07/23.
 */
public class AppDrEvaluationQvo extends CommConditionVo{
    private String drId;//医生主键
    private String professionalAbility;//专业能力;
    private String recoverySpeed;//回复速度
    private String serviceAttitude;//服务态度
    private String content;//评价内容
    private String anonymous;//是否匿名
    private String type;//1--好评,2--中评,3--差评
    private String methodType;//类型 1健康咨询,2服务类型,3建档,4签约,5随访
    private String evaluationUrl;//评价上传图片
    private String patientIdNo;//身份证


    public String getDrId() {
        return drId;
    }

    public void setDrId(String drId) {
        this.drId = drId;
    }

    public String getProfessionalAbility() {
        return professionalAbility;
    }

    public void setProfessionalAbility(String professionalAbility) {
        this.professionalAbility = professionalAbility;
    }

    public String getServiceAttitude() {
        return serviceAttitude;
    }

    public void setServiceAttitude(String serviceAttitude) {
        this.serviceAttitude = serviceAttitude;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAnonymous() {
        return anonymous;
    }

    public void setAnonymous(String anonymous) {
        this.anonymous = anonymous;
    }

    public String getRecoverySpeed() {
        return recoverySpeed;
    }

    public void setRecoverySpeed(String recoverySpeed) {
        this.recoverySpeed = recoverySpeed;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMethodType() {
        return methodType;
    }

    public void setMethodType(String methodType) {
        this.methodType = methodType;
    }

    public String getEvaluationUrl() {
        return evaluationUrl;
    }

    public void setEvaluationUrl(String evaluationUrl) {
        this.evaluationUrl = evaluationUrl;
    }

    public String getPatientIdNo() {
        return patientIdNo;
    }

    public void setPatientIdNo(String patientIdNo) {
        this.patientIdNo = patientIdNo;
    }
}
