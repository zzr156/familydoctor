package com.ylz.bizDo.jtapp.commonEntity;

import com.ylz.bizDo.app.po.AppMyFamily;
import com.ylz.packaccede.allDo.SysDao;
import com.ylz.packcommon.common.bizDo.SpringHelper;
import org.apache.commons.lang.StringUtils;

/**
 * Created by asus on 2017-07-16.
 */
public class AppFamilyInfo {

    private String code;//身份证
    private String name;//名字
    private String tel;//电话
    private String relation;//昵称
    private String patientId;//患者主键

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getRelation() {
        return relation;
    }

    public void setRelation(String relation) {
        this.relation = relation;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public String getBooleanBund() throws Exception {
        if(StringUtils.isNotBlank(this.getPatientId()) && StringUtils.isNotBlank(this.getCode())){
            SysDao sysDao = (SysDao) SpringHelper.getBean("sysDao");
            AppMyFamily myFamily = sysDao.getAppMyFamilyDao().findByBdPatientIdNo(this.getCode(),this.getPatientId());
            if(myFamily != null){
                return "1";
            }
        }
        return "0";
    }
}
