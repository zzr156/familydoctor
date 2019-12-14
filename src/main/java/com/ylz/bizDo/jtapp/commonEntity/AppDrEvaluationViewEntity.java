package com.ylz.bizDo.jtapp.commonEntity;

import com.ylz.bizDo.app.po.AppPatientUser;
import com.ylz.bizDo.cd.po.CdCode;
import com.ylz.packaccede.allDo.SysDao;
import com.ylz.packcommon.common.CodeGroupConstrats;
import com.ylz.packcommon.common.bizDo.SpringHelper;
import com.ylz.packcommon.common.util.ExtendDate;
import org.apache.commons.lang.StringUtils;

import java.sql.Timestamp;

/**
 * Created by asus on 2017/07/23.
 */
public class AppDrEvaluationViewEntity {
    private String patientId;//患者主键
    private String patientName;//患者姓名
    private String patientGender;//患者性别
    private String professionalAbility;//专业能力
    private String serviceAttitude;//服务态度
    private String recoverySpeed;//回复速度
    private String content;//结果
    private String date;//时间
    private String methodType;//方法类型

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getPatientGender() {
        return patientGender;
    }

    public void setPatientGender(String patientGender) {
        this.patientGender = patientGender;
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

    public String getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = ExtendDate.getYMD_h_m(date);
    }

    public String getStrPatientGender() throws Exception{
        if(StringUtils.isNotBlank(this.getPatientGender())){
            SysDao dao =  (SysDao) SpringHelper.getBean("sysDao");
            CdCode value = dao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_CODESEX, this.getPatientGender());
            if(value!=null) {
                return value.getCodeTitle();
            }
        }
        return "";
    }

    public String getStrPatientImageUrl(){
        if(StringUtils.isNotBlank(this.getPatientId())){
            SysDao dao =  (SysDao) SpringHelper.getBean("sysDao");
            AppPatientUser value = (AppPatientUser)dao.getServiceDo().find(AppPatientUser.class,this.getPatientId());
            if(value!=null) {
                if(StringUtils.isNotBlank(value.getPatientImageurl())){
                    return value.getPatientImageurl();
                }
            }

        }
        return "";
    }

    public String getRecoverySpeed() {
        return recoverySpeed;
    }

    public void setRecoverySpeed(String recoverySpeed) {
        this.recoverySpeed = recoverySpeed;
    }

    public String getMethodType() {
        return methodType;
    }

    public void setMethodType(String methodType) {
        this.methodType = methodType;
    }
}
