package com.ylz.bizDo.smjq.smEntity;

import com.ylz.bizDo.app.po.AppDrUser;
import com.ylz.bizDo.app.po.AppHospDept;
import com.ylz.bizDo.app.po.AppTeam;
import com.ylz.packaccede.allDo.SysDao;
import com.ylz.packcommon.common.bizDo.SpringHelper;
import org.apache.commons.lang3.StringUtils;

/**
 * Created by zzl on 2018/8/17.
 */
public class AppModifyPeopleListEntity {
//    机构、团队、医生、居民姓名、性别、年龄、变化记录、变化时间。
    private String orgId;//机构id
    private String orgName;//机构名称
    private String drId;//医生id
    private String drName;//医生姓名
    private String teamId;//团队id
    private String teamName;//团队名称
    private String patientId;//居民id
    private String patientName;//居民姓名
    private String patientAge;//年龄
    private String patientGender;//性别

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
        if(StringUtils.isNotBlank(this.getOrgId())){
            SysDao dao = (SysDao) SpringHelper.getBean("sysDao");
            AppHospDept dept = (AppHospDept)dao.getServiceDo().find(AppHospDept.class,this.getOrgId());
            if(dept != null){
                orgName = dept.getHospName();
            }
        }
        this.orgName = orgName;

    }

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
        if(StringUtils.isNotBlank(this.getDrId())){
            SysDao dao = (SysDao) SpringHelper.getBean("sysDao");
            AppDrUser drUser = (AppDrUser)dao.getServiceDo().find(AppDrUser.class,this.getDrId());
            if(drUser != null){
                drName = drUser.getDrName();
            }
        }
        this.drName = drName;
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

    public String getTeamId() {
        return teamId;
    }

    public void setTeamId(String teamId) {
        this.teamId = teamId;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        if(StringUtils.isNotBlank(this.getTeamId())){
            SysDao dao = (SysDao)SpringHelper.getBean("sysDao");
            AppTeam team = (AppTeam) dao.getServiceDo().find(AppTeam.class,this.getTeamId());
            if(team != null){
                teamName = team.getTeamName();
            }
        }
        this.teamName = teamName;
    }
}
