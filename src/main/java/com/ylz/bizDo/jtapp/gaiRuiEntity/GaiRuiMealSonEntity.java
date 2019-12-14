package com.ylz.bizDo.jtapp.gaiRuiEntity;

import com.ylz.bizDo.app.po.AppServePackage;
import com.ylz.packaccede.allDo.SysDao;
import com.ylz.packcommon.common.bizDo.SpringHelper;
import org.apache.commons.lang.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zzl on 2019/3/25.
 */
public class GaiRuiMealSonEntity {
    private String groupId;//组合id
    private String objectName;//服务人群名称
    private String pkName;//服务内容名称

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getObjectName() {
        return objectName;
    }

    public void setObjectName(String objectName) {
        this.objectName = objectName;
    }

    public String getPkName() {
        return pkName;
    }

    public void setPkName(String pkName) {
        String result = null;
        if(StringUtils.isNotBlank(pkName)){
            String[] strs = pkName.split(";");
            SysDao dao = (SysDao) SpringHelper.getBean("sysDao");
            Map<String,Object> map = new HashMap<>();
            map.put("ID",strs);
            String sql = "SELECT * FROM APP_SERVE_PACKAGE WHERE ID IN (:ID)";
            List<AppServePackage> ll = dao.getServiceDo().findSqlMap(sql,map,AppServePackage.class);
            if(ll!=null && ll.size()>0){
                for(AppServePackage v : ll){
                    if(StringUtils.isNotBlank(result)){
                        result += "," +v.getSerpkName();
                    }else{
                        if(StringUtils.isBlank(v.getSerpkName())) {
                            result = v.getSerpkName()+",";
                        }else{
                            result = v.getSerpkName();
                        }
                    }
                }
            }
        }
        this.pkName = result;
    }
}
