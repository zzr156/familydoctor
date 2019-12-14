package com.ylz.bizDo.jtapp.commonEntity;

import com.ylz.packaccede.allDo.SysDao;
import com.ylz.packcommon.common.bizDo.SpringHelper;
import org.apache.commons.lang.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by zzl on 2018/9/18.
 */
public class AppManageTeamEntity {
    private String teamId;//团队id
    private int teamMemCount = 0;//团队成员数

    public String getTeamId() {
        return teamId;
    }

    public void setTeamId(String teamId) {
        this.teamId = teamId;
    }

    public int getTeamMemCount() {
        return teamMemCount;
    }
//查询团队成员数
    public void setTeamMemCount(String teamMemCount) {
        int mCount = 0;
        if(StringUtils.isNotBlank(this.getTeamId())){
            SysDao dao = (SysDao) SpringHelper.getBean("sysDao");
            Map<String,Object> map = new HashMap<>();
            map.put("teamId",this.getTeamId());
            map.put("STATE","1");
            String sql = "SELECT\n" +
                    "\tcount(1)\n" +
                    "FROM\n" +
                    "\tapp_dr_user a\n" +
                    "INNER JOIN app_team_member b ON a.ID = b.MEM_DR_ID\n" +
                    "WHERE b.MEM_TEAMID = :teamId\n" +
                    "AND a.DR_STATE = :STATE ";
            mCount = dao.getServiceDo().findCount(sql,map);
        }
        this.teamMemCount = mCount;
    }

}
