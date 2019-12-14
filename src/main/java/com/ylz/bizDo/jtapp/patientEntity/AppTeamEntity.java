package com.ylz.bizDo.jtapp.patientEntity;

import com.ylz.packaccede.allDo.SysDao;
import com.ylz.packcommon.common.bizDo.SpringHelper;
import com.ylz.packcommon.common.comEnum.SignFormType;
import org.apache.commons.lang.StringUtils;

import java.util.*;

/**
 * Created by zzl on 2017/6/17.
 */
public class AppTeamEntity {
    private String teamName;//团队名称
    private String teamId;//团队id
    private String num;//该团队签约数
    private String drId;//家庭医生id
    private String drName;//家庭医生姓名
    private List drList;//团队成员
    private String hospId;//医院id
    private String toplimit;//是否上限

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public void setTeamId(String teamId) {
        this.teamId = teamId;
    }

    public void setNum(String num) {
        SysDao dao =  (SysDao) SpringHelper.getBean("sysDao");
        if(StringUtils.isNotBlank(this.getTeamId())){
            Map<String,Object> map = new HashMap<String,Object>();
            map.put("teamId",this.getTeamId());
            String[] signStates = new String[]{SignFormType.YQY.getValue(),SignFormType.YUQY.getValue()};
            map.put("signState",signStates);
            String sql = "select count(*) from APP_SIGN_FORM WHERE sign_team_id =:teamId AND sign_state IN (:signState) " +
                    "AND SIGN_FROM_DATE<=SYSDATE() AND SIGN_TO_DATE>SYSDATE() ";
            num=String.valueOf( dao.getServiceDo().findSqlObject(sql, map));
        }
        this.num = num;
    }

    public void setDrId(String drId) {
        this.drId = drId;
    }

    public void setDrName(String drName) {
        this.drName = drName;
    }

    public String getTeamName() {
        return teamName;
    }

    public String getTeamId() {
        return teamId;
    }

    public String getNum() {
        return num;
    }

    public String getDrId() {
        return drId;
    }

    public String getDrName() {
        return drName;
    }

    public List getDrList() {
        return drList;
    }

    public void setDrList(String drList) {
        List ls = new ArrayList();
        if(StringUtils.isNotBlank(this.getTeamId())){
            SysDao dao = (SysDao)SpringHelper.getBean("sysDao");
            Map<String,Object> map = new HashMap<>();
            map.put("teamId",this.getTeamId());
            String sql = "SELECT\n" +
                    "\ta.ID id,\n" +
                    "\ta.MEM_TEAMID memTeamid,\n" +
                    "\ta.MEM_DR_ID memDrId,\n" +
                    "\ta.MEM_DR_NAME memDrName,\n" +
                    "\ta.MEM_WORK_TYPE memWorkType,\n" +
                    "\ta.MEM_TEAM_NAME memTeamName,\n" +
                    "\ta.MEM_STATE memState,\n" +
                    "\ta.DR_ROLE drRole,\n" +
                    "\ta.DR_TEL drTel,\n" +
                    "\tb.DR_GENDER drGender \n" +
                    "FROM\n" +
                    "\tAPP_TEAM_MEMBER a INNER JOIN app_dr_user b ON a.MEM_DR_ID = b.ID\n" +
                    "WHERE a.MEM_TEAMID = :teamId";
            List<AppTeamMemEntity> list = dao.getServiceDo().findSqlMapRVo(sql,map,AppTeamMemEntity.class);
            ls = list;
        }
        this.drList = ls;
    }

    public String getHospId() {
        return hospId;
    }

    public void setHospId(String hospId) {
        this.hospId = hospId;
    }

    public String getToplimit() {
        return toplimit;
    }

    public void setToplimit(String toplimit) throws Exception {
        this.toplimit = toplimit;
    }
}
