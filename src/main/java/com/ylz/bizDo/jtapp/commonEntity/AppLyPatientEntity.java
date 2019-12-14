package com.ylz.bizDo.jtapp.commonEntity;

import com.ylz.bizDo.app.po.AppPatientUser;
import com.ylz.packaccede.allDo.SysDao;
import com.ylz.packaccede.util.CardUtil;
import com.ylz.packcommon.common.bizDo.SpringHelper;
import com.ylz.packcommon.common.comEnum.PerType;
import com.ylz.packcommon.common.util.AgeUtil;
import com.ylz.packcommon.common.util.ExtendDate;
import com.ylz.packcommon.common.util.ExtendDateUtil;
import org.apache.commons.lang.StringUtils;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * 履约人员列表返回实体
 * Created by zzl on 2017/12/29.
 */
public class AppLyPatientEntity {
    //姓名，性别，年龄，出生日期，疾病类型,联系电话
    private String patientId;//患者id
    private String patientName;//姓名
    private String patientIdno;//身份证
    private String patientSex;//性别
    private String patientBirthday;//出生日期
    private String patientAge;//年龄
    private String patientTel;//联系电话
    private String labelColor;//疾病颜色
    private String labelTitle;//疾病名称(高血压或糖尿病)
    private String perType;//履约类型

    public String getPatientIdno() {
        return patientIdno;
    }

    public void setPatientIdno(String patientIdno) {
        this.patientIdno = patientIdno;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getPatientSex() {
        return patientSex;
    }

    public void setPatientSex(String patientSex) {
        this.patientSex = patientSex;
    }

    public String getPatientBirthday() {
        return patientBirthday;
    }

    public void setPatientBirthday(java.sql.Date patientBirthday) {
        if(patientBirthday!=null){
            this.patientBirthday = ExtendDate.getYMD(patientBirthday);
        }else{
            this.patientBirthday = "";
        }
    }

    public String getPatientAge() {
        return patientAge;
    }

    public void setPatientAge(String patientAge) throws Exception {
        if(PerType.ETJKTJ.getValue().equals(this.getPerType())||PerType.ETZYYJKZD.getValue().equals(this.getPerType())){
            if(StringUtils.isNotBlank(this.getPatientBirthday())){
                patientAge = ExtendDateUtil.getMyl(ExtendDate.getYMD(Calendar.getInstance()),this.getPatientBirthday());
                patientAge +="月龄";
            }
        }else{
            if(StringUtils.isNotBlank(this.getPatientIdno())){
                Map<String,Object> idNos = new HashMap<String,Object>();
                if(StringUtils.isNotBlank(this.getPatientIdno())){
                    if(this.getPatientIdno().length() == 18){
                        idNos = CardUtil.getCarInfo(this.getPatientIdno());
                    }else{
                        idNos = CardUtil.getCarInfo15W(this.getPatientIdno());
                    }
                    String birthday = idNos.get("birthday").toString();
                    String age = AgeUtil.getAgeYear(ExtendDate.getCalendar(birthday));
                    patientAge = age;
                }
            }
        }
        this.patientAge = patientAge;
    }

    public String getPatientTel() {
        return patientTel;
    }

    public void setPatientTel(String patientTel) {
        this.patientTel = patientTel;
    }

    public String getLabelTitle() {
        return labelTitle;
    }

    public void setLabelTitle(String labelTitle) {
        this.labelTitle = labelTitle;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public String getLabelColor() {
        return labelColor;
    }

    public void setLabelColor(String labelColor) {
        this.labelColor = labelColor;
    }

    public String getPerType() {
        return perType;
    }

    public void setPerType(String perType) {
        this.perType = perType;
    }
}
