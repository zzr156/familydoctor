package com.ylz.bizDo.jtapp.drEntity;

import com.ylz.packaccede.allDo.SysDao;
import com.ylz.packcommon.common.bizDo.SpringHelper;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zzl on 2017/12/29.
 */
public class AppServeEntity {
    private String serveId;//服务套餐id
    private String serveName;//服务套餐名称
    private String groupId;//组合id
    private String fee;//费用
    private String signFormId;//签约单
    private List<AppServeGroupEntity> groupList;//组合集合

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

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public List<AppServeGroupEntity> getGroupList() {
        return groupList;
    }

    public String getFee() {
        return fee;
    }

    public void setFee(String fee) {
        this.fee = fee;
    }

    public String getSignFormId() {
        return signFormId;
    }

    public void setSignFormId(String signFormId) {
        this.signFormId = signFormId;
    }

    public void setGroupList(String groupList) {
        List<AppServeGroupEntity> list = new ArrayList<>();
        if(StringUtils.isNotBlank(this.getGroupId())){
            String[] strs = this.getGroupId().split(";");
            SysDao dao = (SysDao) SpringHelper.getBean("sysDao");
            Map<String,Object> map = new HashMap<>();
            for(String str:strs){
                map.put("groupId",str);
                String sql = "SELECT " +
                        "ID groupId," +
                        "SERG_PK_ID pkRemark,"+
                        "SERG_PK_ID pkImageName,"+
                        "SERG_PK_ID pkName," +
                        "SERG_OBJECT_ID objectState,"+
                        "SERG_OBJECT_VALUE objectValue,"+
                        "SERG_OBJECT_TITLE objectName " +
                        "FROM APP_SERVE_GROUP WHERE 1=1 " +
                        "AND ID = :groupId";
                List<AppServeGroupEntity> ll = dao.getServiceDo().findSqlMapRVo(sql,map,AppServeGroupEntity.class);
                if(ll!=null && ll.size()>0){
                    list.add(ll.get(0));
                }
            }
//            if(StringUtils.isNotBlank(this.getSignFormId())){
//                map.put("LABEL_SIGN_ID",this.getSignFormId());
//                String sql = "SELECT GROUP_CONCAT(LABEL_VALUE) LABEL_VALUE FROM APP_LABEL_GROUP where LABEL_SIGN_ID = :LABEL_SIGN_ID";
//                List<Map> ls = dao.getServiceDo().findSqlMap(sql,map);
//                if(ls != null && ls.size() >0){
//                    if(ls.get(0).get("LABEL_VALUE") != null){
//                        String result = ls.get(0).get("LABEL_VALUE").toString();
//                        String[] results = result.split(",");
//                        if(list != null && list.size()>0){
//                            for(int i=0;i<list.size();i++){
//                                if(StringUtils.isNotBlank(list.get(i).getObjectState()) && list.get(i).getObjectState().equals("1")){
//                                    if(results != null && results.length >0){
//                                        boolean flag = false;
//                                        for(String s : results){
//                                            if(list.get(i).getObjectValue().equals(s)){
//                                                flag = true;
//                                                break;
//                                            }
//                                        }
//                                        if(!flag){
//                                            list.remove(i);
//                                            i--;
//                                        }
//                                    }
//                                }
//                            }
//                        }
//                    }
//                }
//            }

        }
        this.groupList = list;
    }
}
