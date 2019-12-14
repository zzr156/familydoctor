package com.ylz.bizDo.register.entity;

import com.ylz.packaccede.allDo.SysDao;
import com.ylz.packcommon.common.bizDo.SpringHelper;
import org.apache.commons.lang.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RegisterGovEntity {
    private String sersmJjId;//
    private String eagGovValue;//
    private List<RegisterGovTileEntity> govList;//
    private String sersmPkValue;//服务内容值

    public String getSersmPkValue() {
        return sersmPkValue;
    }

    public void setSersmPkValue(String sersmPkValue) {
        this.sersmPkValue = sersmPkValue;
    }

    public String getSersmJjId() {
        return sersmJjId;
    }

    public void setSersmJjId(String sersmJjId) {
        this.sersmJjId = sersmJjId;
    }

    public String getEagGovValue() {
        return eagGovValue;
    }

    public void setEagGovValue(String eagGovValue) {
        if(StringUtils.isNotBlank(this.getSersmJjId())){
            SysDao dao = (SysDao) SpringHelper.getBean("sysDao");
            Map<String,Object> map = new HashMap<String,Object>();
            String[] sersmJjId = this.getSersmJjId().split(";");
            map.put("ID",sersmJjId);
            String sql = "SELECT\n" +
                    "\tEAG_GOV_VALUE govValue\n" +
                    "FROM\n" +
                    "\tAPP_ECON_AND_GOV t\n" +
                    "WHERE\n" +
                    "\tt.ID IN (:ID)";
            List<Map> ls = dao.getServiceDo().findSqlMap(sql,map);
            String result = null;
            if(ls != null && ls.size() >0){
                for(Map maps : ls){
                    if(maps.get("govValue") != null){
                        if(StringUtils.isNotBlank(result)){
                            result += ";" + maps.get("govValue").toString();
                        }else{
                            result = maps.get("govValue").toString();
                        }
                    }
                }
            }
            if(StringUtils.isNotBlank(result)) {
                eagGovValue = result;
            }
        }
        this.eagGovValue = eagGovValue;
    }

    public List<RegisterGovTileEntity> getGovList() {
        return govList;
    }

    public void setGovList(String govList) {
        if(StringUtils.isNotBlank(this.getEagGovValue())){
            SysDao dao = (SysDao) SpringHelper.getBean("sysDao");
            Map<String,Object> map = new HashMap<String,Object>();
            String[] eagGovValue = this.getEagGovValue().split(";");
            map.put("GOV_VALUE",eagGovValue);
            String sql = "SELECT t.GOV_TITLE govTitle,t.GOV_MONEY govMoney,t.GOV_HC_PROJECT_ID govHcProjectId FROM APP_SERVE_GOV t where GOV_VALUE in (:GOV_VALUE)";
            List<RegisterGovTileEntity> ls = dao.getServiceDo().findSqlMapRVo(sql,map,RegisterGovTileEntity.class);
            this.govList = ls;
        }
    }
}
