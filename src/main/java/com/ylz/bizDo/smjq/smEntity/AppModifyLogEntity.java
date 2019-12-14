package com.ylz.bizDo.smjq.smEntity;

import com.ylz.bizDo.app.po.AppHospDept;
import com.ylz.bizDo.app.po.AppPatientUser;
import com.ylz.packaccede.allDo.SysDao;
import com.ylz.packcommon.common.bizDo.SpringHelper;
import com.ylz.packcommon.common.util.ExtendDate;
import org.apache.commons.lang3.StringUtils;

import java.sql.Timestamp;

/**
 * 修改日志返回数据
 * Created by zzl on 2018/8/16.
 */
public class AppModifyLogEntity {
    private String patientId;//居民id
    private String patientName;//居民姓名
    private String patientGender;//居民性别（1男 2女）
    private String drId;//医生id
    private String drName;//医生姓名
    private String orgId;//机构id
    private String orgName;//机构名
    private String upTime;//修改时间
    private String oldHbpValue;//高血压旧值
    private String newHbpValue;//高血压新值
    private String oldDmValue;//糖尿病旧值
    private String newDmValue;//糖尿病新值

    public String getDrId() {
        return drId;
    }

    public void setDrId(String drId) {
        this.drId = drId;
    }

    public String getDrName() {
        return drName;
    }

    public void setDrName(String drName) {
        this.drName = drName;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        String deptName = "";
        if(StringUtils.isNotBlank(this.getOrgId())){
            SysDao dao = (SysDao) SpringHelper.getBean("sysDao");
            AppHospDept dept = (AppHospDept)dao.getServiceDo().find(AppHospDept.class,this.getOrgId());
            if(dept != null){
                deptName = dept.getHospName();
            }
        }
        this.orgName = deptName;
    }

    public String getUpTime() {
        return upTime;
    }

    public void setUpTime(Timestamp upTime) {
        if(upTime != null){
            this.upTime = ExtendDate.getYMD_h_m(upTime);
        }else{
            this.upTime = "";
        }
    }

    public String getOldHbpValue() {
        return oldHbpValue;
    }

    public void setOldHbpValue(String oldHbpValue) {
        this.oldHbpValue = oldHbpValue;
    }

    public String getNewHbpValue() {
        return newHbpValue;
    }

    public void setNewHbpValue(String newHbpValue) {
        this.newHbpValue = newHbpValue;
    }

    public String getOldDmValue() {
        return oldDmValue;
    }

    public void setOldDmValue(String oldDmValue) {
        this.oldDmValue = oldDmValue;
    }

    public String getNewDmValue() {
        return newDmValue;
    }

    public void setNewDmValue(String newDmValue) {
        this.newDmValue = newDmValue;
    }

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
        if(StringUtils.isNotBlank(this.getPatientId())){
            SysDao dao = (SysDao)SpringHelper.getBean("sysDao");
            AppPatientUser user = (AppPatientUser)dao.getServiceDo().find(AppPatientUser.class,this.getPatientId());
            if(user != null){
                this.patientName = user.getPatientName();
            }
        }else{
            this.patientName = "";
        }
    }

    public String getPatientGender() {
        return patientGender;
    }

    public void setPatientGender(String patientGender) {
        if(StringUtils.isNotBlank(this.getPatientId())){
            SysDao dao = (SysDao)SpringHelper.getBean("sysDao");
            AppPatientUser user = (AppPatientUser)dao.getServiceDo().find(AppPatientUser.class,this.getPatientId());
            if(user != null){
                this.patientGender = user.getPatientGender();
            }
        }else{
            this.patientGender = "";
        }
    }
}
