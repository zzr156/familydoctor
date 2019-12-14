package com.ylz.bizDo.jtapp.drEntity;

import com.ylz.bizDo.app.po.AppHospDept;
import com.ylz.packaccede.allDo.SysDao;
import com.ylz.packcommon.common.bizDo.SpringHelper;
import com.ylz.packcommon.common.comEnum.FollowPlanState;
import org.apache.commons.lang.StringUtils;

import java.util.*;

/**
 * Created by hzk on 2017/6/16.
 * 医生信息
 */
public class DrInfo {
    private String id;
    private String drName;//医生名称
    private String drImageurl;//医生头像
    private String teamState;//队长或成员
    private String teamWorkType;//成员工作类型 1..健康管理师,2.专科医生,3.全科医生,4.医技人员,5.公卫医师,6.社区护士
    private String drHospName;//医院名称
    private String drGood;////医生擅长
    private String drIntro;//医生简介

    private String zxCount;//咨询数
    private String sfCount;//随访数
    private String dyyCount;//代预约
    private String jkjyCount;//健康教育
    private String jkzdCount;//健康指导

    private String drLabel;//评价标签
    private String teamId;//团队id
    private String hospId;//医院id
    private List<AppServeEntity> serveList;

    public void setId(String id) {
        this.id = id;
    }

    public void setDrName(String drName) {
        this.drName = drName;
    }

    public void setDrImageurl(String drImageurl) {
        this.drImageurl = drImageurl;
    }

    public void setTeamState(String teamState) {
        this.teamState = teamState;
    }

    public void setTeamWorkType(String teamWorkType) {
        this.teamWorkType = teamWorkType;
    }

    public void setDrHospName(String drHospName) {
        this.drHospName = drHospName;
    }

    public void setDrGood(String drGood) {
        this.drGood = drGood;
    }

    public void setDrIntro(String drIntro) {
        this.drIntro = drIntro;
    }

    public void setZxCount(String zxCount) {
        if(StringUtils.isNotBlank(this.getId())){
            SysDao dao =  (SysDao) SpringHelper.getBean("sysDao");
            Map<String,Object> map = new HashMap<String,Object>();
            map.put("id",this.getId());
            String sql ="SELECT COUNT(*) FROM APP_CONSULT_RECORD d WHERE 1=1 ";
            String date = String.valueOf(Calendar.getInstance().get(Calendar.YEAR));
            map.put("startDate",date+"-01-01 00:00:00");
            map.put("endDate",date+"-12-31 23:59:59");
            map.put("teamId",this.getTeamId());
            sql += " AND d.CONREC_TEAM_ID =:teamId";
            sql += " AND d.CONREC_INITIATE_TIME >=:startDate";
            sql += " AND d.CONREC_INITIATE_TIME <=:endDate";
            zxCount = String.valueOf(dao.getServiceDo().gteSqlCount(sql,map));
        }
        this.zxCount = zxCount;
    }

    public void setSfCount(String sfCount) {
        if(StringUtils.isNotBlank(this.getId())){
            SysDao dao =  (SysDao) SpringHelper.getBean("sysDao");
            Map<String,Object> map = new HashMap<String,Object>();
            map.put("id",this.getId());
            String[] fllowState = new String[]{FollowPlanState.YIWANCHENG.getValue(),FollowPlanState.SHIFANG.getValue(),FollowPlanState.SIWANG.getValue()};
            map.put("state", fllowState);
            String sql ="SELECT COUNT(*) FROM APP_FOLLOW_PLAN WHERE SF_FOLLOW_DOCTORID=:id AND SF_FOLLOW_STATE IN :state";
            String date = String.valueOf(Calendar.getInstance().get(Calendar.YEAR));
            map.put("startDate",date+"-01-01 00:00:00");
            map.put("endDate",date+"-12-31 23:59:59");
            map.put("teamId",this.getTeamId());
            sql += " AND SF_TEAM_ID =:teamId";
            sql += " AND  SF_FOLLOW_DATE BETWEEN :startDate AND :endDate ";
            sfCount = String.valueOf(dao.getServiceDo().gteSqlCount(sql,map));
        }
        this.sfCount = sfCount;
    }

    public void setDyyCount(String dyyCount) {
        /*if(StringUtils.isNotBlank(this.getId())){
            SysDao dao =  (SysDao) SpringHelper.getBean("sysDao");
            Map<String,Object> map = new HashMap<String,Object>();
            map.put("id",this.getId());
            map.put("way", SignFormWay.DBYS.getValue());
            String sql ="SELECT COUNT(*) FROM APP_SIGN_FORM e JOIN APP_SIGN_BATCH f ON e.SIGN_BATCH_ID = f.ID WHERE\n" +
                    "\tf.BATCH_CREATE_PERSID =:id  AND e.SIGN_WAY =:way";
            dyyCount = String.valueOf(dao.getServiceDo().gteSqlCount(sql,map));
        }*/
        dyyCount = "0";
        this.dyyCount = dyyCount;
    }

    public void setJkjyCount(String jkjyCount) {
        if(StringUtils.isNotBlank(this.getId())){
            SysDao dao =  (SysDao) SpringHelper.getBean("sysDao");
            Map<String,Object> map = new HashMap<String,Object>();
            map.put("id",this.getId());
            String date = String.valueOf(Calendar.getInstance().get(Calendar.YEAR));
            map.put("startDate",date+"-01-01 00:00:00");
            map.put("endDate",date+"-12-31 23:59:59");
            String sql ="SELECT COUNT(*) FROM APP_USER_HEALTHED g WHERE g.HED_DR_ID=:id";
            map.put("teamId",this.getTeamId());
//            sql += " AND g.HED_TEAM_ID =:teamId";
            sql += " AND g.HED_CREATE_TIME >=:startDate";
            sql += " AND g.HED_CREATE_TIME <=:endDate";
            jkjyCount = String.valueOf(dao.getServiceDo().gteSqlCount(sql,map));
        }
        this.jkjyCount = jkjyCount;
    }

    public void setJkzdCount(String jkzdCount) {
        if(StringUtils.isNotBlank(this.getId())){
            SysDao dao =  (SysDao) SpringHelper.getBean("sysDao");
            Map<String,Object> map = new HashMap<String,Object>();
            map.put("id",this.getId());
            String sql ="SELECT COUNT(*) FROM APP_HEALTH_MEDDLE h WHERE h.HM_DR_ID=:id";

            String date = String.valueOf(Calendar.getInstance().get(Calendar.YEAR));
            map.put("startDate",date+"-01-01 00:00:00");
            map.put("endDate",date+"-12-31 23:59:59");
            map.put("teamId",this.getTeamId());
            sql += " AND h.HM_TEAM_ID =:teamId";
            sql += " AND h.HM_CREATE_TIME >=:startDate";
            sql += " AND h.HM_CREATE_TIME <=:endDate";

            jkzdCount = String.valueOf(dao.getServiceDo().gteSqlCount(sql,map));
        }
        this.jkzdCount = jkzdCount;
    }

    public void setDrLabel(String drLabel) {
        this.drLabel = drLabel;
    }

    //

    public String getId() {
        return id;
    }

    public String getDrName() {
        return drName;
    }

    public String getDrImageurl() {
        return drImageurl;
    }

    public String getTeamState() {
        return teamState;
    }

    public String getTeamWorkType() {
        return teamWorkType;
    }

    public String getDrHospName() {
        return drHospName;
    }

    public String getDrGood() {
        return drGood;
    }

    public String getDrIntro() {
        return drIntro;
    }

    public String getZxCount() {
        return zxCount;
    }

    public String getSfCount() {
        return sfCount;
    }

    public String getDyyCount() {
        return dyyCount;
    }

    public String getJkjyCount() {
        return jkjyCount;
    }

    public String getJkzdCount() {
        return jkzdCount;
    }

    public String getDrLabel() {
        return drLabel;
    }

    public String getTeamId() {
        return teamId;
    }

    public void setTeamId(String teamId) {
        this.teamId = teamId;
    }

    public String getHospId() {
        return hospId;
    }

    public void setHospId(String hospId) {
        this.hospId = hospId;
    }

    public List<AppServeEntity> getServeList() {
        return serveList;
    }

    public void setServeList(String serveList) {
        List<AppServeEntity> list = new ArrayList<>();
        if(StringUtils.isNotBlank(this.getHospId())){
            SysDao dao = (SysDao)SpringHelper.getBean("sysDao");
            AppHospDept dept = (AppHospDept)dao.getServiceDo().find(AppHospDept.class,this.getHospId());
            if(dept!=null){
                Map<String,Object> map = new HashMap<>();
                map.put("hospId",this.getHospId());
                map.put("sj",dept.getHospAreaCode().substring(0,2)+"0000000000");
                map.put("city",dept.getHospAreaCode().substring(0,4)+"00000000");
                map.put("street",dept.getHospAreaCode().substring(0,6)+"000000");
                map.put("area",dept.getHospAreaCode().substring(0,9)+"000");
                map.put("areaCode",dept.getHospAreaCode());
                map.put("level","0");
                map.put("openState","1");
                String sql = " SELECT " +
                        "a.ID serveId," +
                        "a.SERSM_NAME serveName," +
                        "a.SERSM_GROUP_ID groupId," +
                        "a.SERSM_TOTAL_FEE fee," +
                        "'' signFormId,"+
                        "'' groupList " +
                        "FROM APP_SERVE_SETMEAL a " +
                        "WHERE 1=1 ";

                sql += " AND (a.SERSM_CREATE_DEPT =:hospId OR (a.SERSM_OPEN_STATE =:openState " +
                        "AND (find_in_set(:sj, a.SERSM_OPEN_AREA) OR find_in_set(:city, a.SERSM_OPEN_AREA) OR find_in_set(:street, a.SERSM_OPEN_AREA)" +
                        " OR find_in_set(:area, a.SERSM_OPEN_AREA) OR find_in_set(:areaCode, a.SERSM_OPEN_AREA))))";
                list = dao.getServiceDo().findSqlMapRVo(sql,map,AppServeEntity.class);
            }


        }
        this.serveList = list;
    }
}
