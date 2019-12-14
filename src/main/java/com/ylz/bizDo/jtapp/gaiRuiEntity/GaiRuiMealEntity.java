package com.ylz.bizDo.jtapp.gaiRuiEntity;

import com.ylz.packaccede.allDo.SysDao;
import com.ylz.packcommon.common.bizDo.SpringHelper;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zzl on 2019/3/25.
 */
public class GaiRuiMealEntity {
    private String serveId;//套餐主键
    private String serveName;//套餐名称
    private String fee;//费用
    private String serveValue;//服务包编码
    private String groupId;//组合主键
    private List<GaiRuiMealSonEntity> groupList;//组合列表

    public String getServeId() {
        return serveId;
    }

    public void setServeId(String serveId) {
        this.serveId = serveId;
    }

    public String getServeName() {
        return serveName;
    }

    public void setServeName(String serveName) {
        this.serveName = serveName;
    }

    public String getFee() {
        return fee;
    }

    public void setFee(String fee) {
        this.fee = fee;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public List<GaiRuiMealSonEntity> getGroupList() {
        return groupList;
    }

    public void setGroupList(String groupList) {
        List<GaiRuiMealSonEntity> list = new ArrayList<>();
        if(StringUtils.isNotBlank(this.getGroupId())){
            String[] strs = this.getGroupId().split(";");
            SysDao dao = (SysDao) SpringHelper.getBean("sysDao");
            Map<String,Object> map = new HashMap<>();
            for(String str:strs){
                map.put("groupId",str);
                String sql = "SELECT " +
                        "ID groupId," +
                        "SERG_PK_ID pkName," +
                        "SERG_OBJECT_TITLE objectName " +
                        "FROM APP_SERVE_GROUP WHERE 1=1 " +
                        "AND ID = :groupId";
                List<GaiRuiMealSonEntity> ll = dao.getServiceDo().findSqlMapRVo(sql,map,GaiRuiMealSonEntity.class);
                if(ll!=null && ll.size()>0){
                    list.add(ll.get(0));
                }
            }

        }
        this.groupList = list;
    }

    public String getServeValue() {
        return serveValue;
    }

    public void setServeValue(String serveValue) {
        this.serveValue = serveValue;
    }
}
