package com.ylz.bizDo.jtapp.ysChangeEntity;

import com.ylz.bizDo.app.po.AppTeam;
import com.ylz.packaccede.allDo.SysDao;
import com.ylz.packcommon.common.bizDo.SpringHelper;
import com.ylz.packcommon.common.comEnum.ChangeState;
import com.ylz.packcommon.common.comEnum.LabelManageType;
import com.ylz.packcommon.common.comEnum.SignFormType;
import org.apache.commons.lang.StringUtils;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zzl on 2017/9/4.
 */
public class YsChangeCountEntity {
    private String typeValue;//类型值
    private String typeName;//类型名称
    private String count;//人数
    private List<YsChangePeopleEntity> peopleList;
    private String drId;
    private String teamId;
    private String type;//类型

    public String getTypeValue() {
        return typeValue;
    }

    public void setTypeValue(String typeValue) {
        this.typeValue = typeValue;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getCount() {
        return count;
    }

    public void setCount(BigInteger count)
    {
        if(count == null){
            this.count = "0";
        }else{
            this.count = String.valueOf(count);
        }
    }

    public List<YsChangePeopleEntity> getPeopleList() {
        return peopleList;
    }

    public String getDrId() {
        return drId;
    }

    public void setDrId(String drId) {
        this.drId = drId;
    }

    public String getTeamId() {
        return teamId;
    }

    public void setTeamId(String teamId) {
        this.teamId = teamId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setPeopleList(String peopleList) {
        List<YsChangePeopleEntity> list = new ArrayList<YsChangePeopleEntity>();
        SysDao dao = (SysDao) SpringHelper.getBean("sysDao");
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("drId",this.getDrId());
        String[] signStates = new String[]{SignFormType.YQY.getValue(),SignFormType.YUQY.getValue()};
        map.put("state", signStates);
        map.put("value",this.getTypeValue());
        String[] strs = new String[]{ChangeState.SQBG.getValue(),ChangeState.TYBG.getValue()};
        map.put("changeState", strs);
        String sql = "";
        if(LabelManageType.JKQK.getValue().equals(this.getType())){
            sql = "SELECT e.ID id," +
                    "e.PATIENT_NAME name," +
                    "e.PATIENT_IMAGEURL imageUrl," +
                    "e.PATIENT_GENDER sex  " +
                    "FROM " +
                    "APP_PATIENT_USER e " +
                    "INNER JOIN APP_SIGN_FORM f ON e.ID = f.SIGN_PATIENT_ID " +
                    "WHERE " +
                    "f.SIGN_STATE IN (:state) " +
                    " AND f.SIGN_HEALTH_GROUP =:value " +
                    "AND f.SIGN_CHANGE_STATE != '2' ";
            if(StringUtils.isNotBlank(this.getTeamId())){
                map.put("teamId",this.getTeamId());
                sql += " AND f.SIGN_TEAM_ID =:teamId ";
                AppTeam team = (AppTeam)dao.getServiceDo().find(AppTeam.class,this.getTeamId());
                if(team!=null){
                    if(!this.getDrId().equals(team.getTeamDrId())){//非本团队队长带医生限制查询
                        sql +=  " AND f.SIGN_DR_ID =:drId ";
                    }
                }
            }

        }else if(LabelManageType.FWRQ.getValue().equals(this.getType())){
            sql ="SELECT " +
                    "e.ID id," +
                    "e.PATIENT_NAME name," +
                    "e.PATIENT_IMAGEURL imageUrl," +
                    "e.PATIENT_GENDER sex  " +
                    "FROM " +
                    "APP_PATIENT_USER e " +
                    "INNER JOIN APP_SIGN_FORM f ON e.ID = f.SIGN_PATIENT_ID " +
                    "WHERE " +
                    "f.SIGN_STATE IN (:state) " +
                    "AND f.ID IN (" +
                    "SELECT " +
                    "LABEL_SIGN_ID " +
                    "FROM " +
                    "APP_LABEL_GROUP " +
                    "WHERE " +
                    "LABEL_VALUE = :value ) " +
                    "AND f.SIGN_CHANGE_STATE != '2' ";
            if(StringUtils.isNotBlank(this.getTeamId())){
                map.put("teamId",this.getTeamId());
                sql += " AND f.SIGN_TEAM_ID = :teamId ";
                AppTeam team = (AppTeam)dao.getServiceDo().find(AppTeam.class,this.getTeamId());
                if(team!=null){
                    if(!this.getDrId().equals(team.getTeamDrId())){//非本团队队长带医生限制查询
                        sql +=  " AND f.SIGN_DR_ID =:drId ";
                    }
                }
            }
        }else if(LabelManageType.JBLX.getValue().equals(this.getType())){
            sql ="SELECT " +
                    "e.ID id," +
                    "e.PATIENT_NAME name," +
                    "e.PATIENT_IMAGEURL imageUrl," +
                    "e.PATIENT_GENDER sex  " +
                    "FROM " +
                    "APP_PATIENT_USER e " +
                    "INNER JOIN APP_SIGN_FORM f ON e.ID = f.SIGN_PATIENT_ID " +
                    "WHERE " +
                    "f.SIGN_STATE IN (:state) " +
                    "AND f.ID IN (" +
                    "SELECT " +
                    "LABEL_SIGN_ID " +
                    "FROM " +
                    "APP_LABEL_DISEASE " +
                    "WHERE " +
                    "LABEL_VALUE = :value ) " +
                    "AND f.SIGN_CHANGE_STATE != '2' ";
            if(StringUtils.isNotBlank(this.getTeamId())){
                map.put("teamId",this.getTeamId());
                sql += " AND f.SIGN_TEAM_ID = :teamId ";
                AppTeam team = (AppTeam)dao.getServiceDo().find(AppTeam.class,this.getTeamId());
                if(team!=null){
                    if(!this.getDrId().equals(team.getTeamDrId())){//非本团队队长带医生限制查询
                        sql +=  " AND f.SIGN_DR_ID =:drId ";
                    }
                }
            }
        }else if(LabelManageType.JJLX.getValue().equals(this.getType())){
            sql ="SELECT " +
                    "e.ID id," +
                    "e.PATIENT_NAME name," +
                    "e.PATIENT_IMAGEURL imageUrl," +
                    "e.PATIENT_GENDER sex  " +
                    "FROM " +
                    "APP_PATIENT_USER e " +
                    "INNER JOIN APP_SIGN_FORM f ON e.ID = f.SIGN_PATIENT_ID " +
                    "WHERE " +
                    "f.SIGN_STATE IN (:state) " +
                    "AND f.ID IN (" +
                    "SELECT " +
                    "LABEL_SIGN_ID " +
                    "FROM " +
                    "APP_LABEL_ECONOMICS " +
                    "WHERE " +
                    "LABEL_VALUE = :value ) " +
                    "AND f.SIGN_CHANGE_STATE != '2' ";
            if(StringUtils.isNotBlank(this.getTeamId())){
                map.put("teamId",this.getTeamId());
                sql += " AND f.SIGN_TEAM_ID = :teamId ";
                AppTeam team = (AppTeam)dao.getServiceDo().find(AppTeam.class,this.getTeamId());
                if(team!=null){
                    if(!this.getDrId().equals(team.getTeamDrId())){//非本团队队长带医生限制查询
                        sql +=  " AND f.SIGN_DR_ID =:drId ";
                    }
                }
            }
        }
        sql += " AND f.SIGN_FROM_DATE<=SYSDATE() AND f.SIGN_TO_DATE>=SYSDATE() ";
        list = dao.getServiceDo().findSqlMapRVo(sql,map,YsChangePeopleEntity.class);
        this.peopleList = list;
    }
}
