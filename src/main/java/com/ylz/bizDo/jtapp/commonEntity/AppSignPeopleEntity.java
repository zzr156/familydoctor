package com.ylz.bizDo.jtapp.commonEntity;

import com.ylz.packcommon.common.comEnum.SignFormType;
import org.apache.commons.lang3.StringUtils;

/**
 * 姓名、身份证、档案号、是否签约、服务类型、签约行政区划、行政区划级别
 * Created by zzl on 2019/4/3.
 */
public class AppSignPeopleEntity {
    private String patientName;//姓名
    private String patientIdno;//身份证
    private String patientJmda;//居民档案号
    private String signState;//是否签约
    private String fwTitle;//服务类型
    private String signAreaCode;//签约行政区划
    private String areaLeve;//行政区划级别

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getPatientIdno() {
        return patientIdno;
    }

    public void setPatientIdno(String patientIdno) {
        this.patientIdno = patientIdno;
    }

    public String getPatientJmda() {
        return patientJmda;
    }

    public void setPatientJmda(String patientJmda) {
        this.patientJmda = patientJmda;
    }

    public String getSignState() {
        return signState;
    }

    public void setSignState(String signState) {
        if(SignFormType.YQY.getValue().equals(signState) || SignFormType.YUQY.getValue().equals(signState)){
            signState = "已签约";
        }else{
            signState = "未签约";
        }
        this.signState = signState;
    }

    public String getFwTitle() {
        return fwTitle;
    }

    public void setFwTitle(String fwTitle) {
        this.fwTitle = fwTitle;
    }

    public String getSignAreaCode() {
        return signAreaCode;
    }

    public void setSignAreaCode(String signAreaCode) {
        this.signAreaCode = signAreaCode;
    }

    public String getAreaLeve() {
        return areaLeve;
    }

    public void setAreaLeve(String areaLeve) {
        this.areaLeve = areaLeve;
    }
}
