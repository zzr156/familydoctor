package com.ylz.bizDo.smjq.smEntity;

import com.ylz.packaccede.allDo.SysDao;
import com.ylz.packcommon.common.bizDo.SpringHelper;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zzl on 2018/7/24.
 */
public class AppSmLoginEntity {
    private String drName;//医生姓名
    private String drId;//医生主键
    private String orgId;//机构主键
    private String orgName;//机构名称
    List<AppSmTeamEntity> listTeam;//团队信息

    public String getDrName() {
        return drName;
    }

    public void setDrName(String drName) {
        this.drName = drName;
    }

    public String getDrId() {
        return drId;
    }

    public void setDrId(String drId) {
        this.drId = drId;
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
        this.orgName = orgName;
    }

    public List<AppSmTeamEntity> getListTeam() {
        return listTeam;
    }

    public void setListTeam(String listTeam) {
        List<AppSmTeamEntity> ll = new ArrayList<>();
        if(StringUtils.isNotBlank(this.getOrgId())){
            SysDao dao = (SysDao) SpringHelper.getBean("sysDao");
            Map<String,Object> map = new HashMap<>();
            map.put("orgId",this.getOrgId());
            String sql = "SELECT\n" +
                    "\tID teamId,\n" +
                    "\tTEAM_NAME teamName\n" +
                    "FROM\n" +
                    "\tapp_team\n" +
                    "WHERE\n" +
                    "\tTEAM_HOSP_ID =:orgId and TEAM_DEL_STATE='0' ";
            ll = dao.getServiceDo().findSqlMapRVo(sql,map,AppSmTeamEntity.class);
        }
        this.listTeam = ll;
    }
}
