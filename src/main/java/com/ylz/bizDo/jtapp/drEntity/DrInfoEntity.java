package com.ylz.bizDo.jtapp.drEntity;

import com.ylz.bizDo.app.po.AppHospDept;
import com.ylz.packaccede.allDo.SysDao;
import com.ylz.packcommon.common.bizDo.SpringHelper;
import com.ylz.packcommon.common.comEnum.FollowPlanState;
import org.apache.commons.lang.StringUtils;

import java.util.*;

/**
 * Created by hzk on 2017/6/16.
 * 医生信息(易惠)
 */
public class DrInfoEntity {
    private String id;
    private String drName;//医生名称
    private String drImageurl;//医生头像
    private String teamState;//队长或成员
    private String teamWorkType;//成员工作类型 1..健康管理师,2.专科医生,3.全科医生,4.医技人员,5.公卫医师,6.社区护士
    private String drHospName;//医院名称
    private String drGood;////医生擅长
    private String drIntro;//医生简介
    private String teamId;//团队id
    private String hospId;//医院id
    private List<AppServeEntity> serveList;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDrName() {
        return drName;
    }

    public void setDrName(String drName) {
        this.drName = drName;
    }

    public String getDrImageurl() {
        return drImageurl;
    }

    public void setDrImageurl(String drImageurl) {
        this.drImageurl = drImageurl;
    }

    public String getTeamState() {
        return teamState;
    }

    public void setTeamState(String teamState) {
        this.teamState = teamState;
    }

    public String getTeamWorkType() {
        return teamWorkType;
    }

    public void setTeamWorkType(String teamWorkType) {
        this.teamWorkType = teamWorkType;
    }

    public String getDrHospName() {
        return drHospName;
    }

    public void setDrHospName(String drHospName) {
        this.drHospName = drHospName;
    }

    public String getDrGood() {
        return drGood;
    }

    public void setDrGood(String drGood) {
        this.drGood = drGood;
    }

    public String getDrIntro() {
        return drIntro;
    }

    public void setDrIntro(String drIntro) {
        this.drIntro = drIntro;
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
