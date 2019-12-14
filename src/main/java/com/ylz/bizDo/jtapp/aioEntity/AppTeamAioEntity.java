package com.ylz.bizDo.jtapp.aioEntity;

import com.ylz.bizDo.app.po.AppTeamMember;
import com.ylz.bizDo.jtapp.drEntity.DrInfo;
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
public class AppTeamAioEntity {
    private String teamName;//团队名称
    private String teamId;//团队id
    private String num;//该团队签约数
    private String drId;//家庭医生id
    private String drName;//家庭医生姓名
    private List drList;//团队成员
    private String hospId;//医院id

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

    public void setDrList(String drList) throws Exception {
        List ls = new ArrayList();
        if(StringUtils.isNotBlank(this.getTeamId())){
            SysDao dao = (SysDao)SpringHelper.getBean("sysDao");
            List<AppDrInfoAioEntity> list = dao.getAppDrUserDao().findDrInfoByidList(this.getTeamId());
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

}
