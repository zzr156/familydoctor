package com.ylz.bizDo.jtapp.patientEntity;

import com.ylz.packaccede.allDo.SysDao;
import com.ylz.packcommon.common.bizDo.SpringHelper;
import com.ylz.packcommon.common.comEnum.SignFormType;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zzl on 2017/6/17.
 */
public class AppTeamMotoeEntity {
    private String orgId;//机构主键
    private String teamId;//团队id
    private String teamName;//团队名称
    private String teamTel;//团队联系电话
    private String teamServiceType;//服务类别（1-家签，2-居家养老，3-慢病管理）
    private String teamDiseaseType;//疾病类别
    private String teamServiceArea;//服务区域编码
    private String teamServiceAreaName;//服务区域名称
    private String src;//来源
    private List teamMembers;//团队成员

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
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
        this.teamName = teamName;
    }

    public String getTeamTel() {
        return teamTel;
    }

    public void setTeamTel(String teamTel) {
        this.teamTel = teamTel;
    }

    public String getTeamServiceType() {
        return teamServiceType;
    }

    public void setTeamServiceType(String teamServiceType) {
        this.teamServiceType = teamServiceType;
    }

    public String getTeamDiseaseType() {
        return teamDiseaseType;
    }

    public void setTeamDiseaseType(String teamDiseaseType) {
        this.teamDiseaseType = teamDiseaseType;
    }

    public String getTeamServiceArea() {
        return teamServiceArea;
    }

    public void setTeamServiceArea(String teamServiceArea) {
        this.teamServiceArea = teamServiceArea;
    }

    public String getTeamServiceAreaName() {
        return teamServiceAreaName;
    }

    public void setTeamServiceAreaName(String teamServiceAreaName) {
        this.teamServiceAreaName = teamServiceAreaName;
    }

    public List getTeamMembers() {
        return teamMembers;
    }

    public void setTeamMembers(String teamMembers) {
        List ls = new ArrayList();
        if(StringUtils.isNotBlank(this.getTeamId())){
            SysDao dao = (SysDao)SpringHelper.getBean("sysDao");
            Map<String,Object> map = new HashMap<>();
            map.put("teamId",this.getTeamId());
            String sql = "SELECT\n" +
//                    "\tSUBSTRING(a.MEM_DR_ID,4) drId,\n" +
                    "\ta.MEM_DR_ID drId,\n" +
                    "\ta.MEM_WORK_TYPE drWorkTitle,\n" +
                    "\tCASE a.MEM_STATE WHEN 0 THEN '1' ELSE '3' END teamRole,\n" +
                    "\ta.DR_ROLE drWorkType\n" +
                    "FROM\n" +
                    "\tAPP_TEAM_MEMBER a\n" +
                    " WHERE a.MEM_TEAMID = :teamId";
            List<AppTeamMemMotoeEntity> list = dao.getServiceDo().findSqlMapRVo(sql,map,AppTeamMemMotoeEntity.class);
            ls = list;
        }
        this.teamMembers = ls;
    }

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }
}
