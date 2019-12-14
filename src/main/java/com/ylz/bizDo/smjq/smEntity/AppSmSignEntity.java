package com.ylz.bizDo.smjq.smEntity;

import com.ylz.bizDo.app.po.AppDrUser;
import com.ylz.bizDo.app.po.AppHospDept;
import com.ylz.bizDo.app.po.AppTeam;
import com.ylz.packaccede.allDo.SysDao;
import com.ylz.packcommon.common.bizDo.SpringHelper;
import org.apache.commons.lang.StringUtils;

/** 签约信息
 * Created by zzl on 2018/7/25.
 */
public class AppSmSignEntity {
    private String teamId;//团队id
    private String teamName;//团队名称
    private String orgId;//机构id
    private String orgName;//机构名称
    private String drId;//医生id
    private String drName;//医生姓名
    private String drTel;//医生电话

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
            SysDao dao = (SysDao) SpringHelper.getBean("sysDao");
            AppTeam team = (AppTeam)dao.getServiceDo().find(AppTeam.class,this.getTeamId());
            if(team != null){
                teamName = team.getTeamName();
            }
        }
        this.teamName = teamName;
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
            SysDao dao = (SysDao)SpringHelper.getBean("sysDao");
            AppDrUser drUser = (AppDrUser)dao.getServiceDo().find(AppDrUser.class,this.getDrId());
            if(drUser != null){
                drName = drUser.getDrName();
            }
        }
        this.drName = drName;
    }

    public String getDrTel() {
        return drTel;
    }

    public void setDrTel(String drTel) {
        if(StringUtils.isNotBlank(this.getDrId())){
            SysDao dao = (SysDao)SpringHelper.getBean("sysDao");
            AppDrUser drUser = (AppDrUser)dao.getServiceDo().find(AppDrUser.class,this.getDrId());
            if(drUser != null){
                drTel = drUser.getDrTel();
            }
        }
        this.drTel = drTel;
    }
}
