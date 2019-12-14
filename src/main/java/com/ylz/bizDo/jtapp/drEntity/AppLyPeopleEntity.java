package com.ylz.bizDo.jtapp.drEntity;

import com.ylz.packaccede.util.CardUtil;
import com.ylz.packcommon.common.util.AgeUtil;
import com.ylz.packcommon.common.util.ExtendDate;
import org.apache.commons.lang.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * 查询履约人员列表
 * Created by zzl on 2017/11/14.
 */
public class AppLyPeopleEntity {
    private String patientId;//患者id
    private String patientName;//患者姓名
    private String imgUrl;//患者头像
    private String sex;//患者性别
    private String age;//患者年龄
    private String patientIdno;//身份证号

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

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) throws Exception {
        Map<String,Object> idNos = new HashMap<String,Object>();
        try {
            if(StringUtils.isNotBlank(this.getPatientIdno())){
                if(this.getPatientIdno().length() == 18){
                    idNos = CardUtil.getCarInfo(this.getPatientIdno());
                }else{
                    idNos = CardUtil.getCarInfo15W(this.getPatientIdno());
                }
                String birthday = idNos.get("birthday").toString();
                age = AgeUtil.getAgeYear(ExtendDate.getCalendar(birthday));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        this.age = age;
    }
    public String getPatientIdno() {
        return patientIdno;
    }

    public void setPatientIdno(String patientIdno) {
        this.patientIdno = patientIdno;
    }

}


