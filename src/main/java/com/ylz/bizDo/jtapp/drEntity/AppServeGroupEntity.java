package com.ylz.bizDo.jtapp.drEntity;

import com.ylz.bizDo.app.po.AppServeObject;
import com.ylz.bizDo.app.po.AppServePackage;
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
public class AppServeGroupEntity {
    private String groupId;//组合id
    private String objectState;//是否设为基本公共卫生服务人群
    private String objectName;//服务对象名称
    private String objectValue;//服务对象值
    private String pkName;//服务内容名称
    private String pkRemark;//服务内容
    private String pkImageName;//图片名称

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


    public String getPkRemark() {
        return pkRemark;
    }

    public void setPkRemark(String pkRemark) {
        String result = null;
        if(StringUtils.isNotBlank(pkRemark)){
            String[] strs = pkRemark.split(";");
            SysDao dao = (SysDao) SpringHelper.getBean("sysDao");
            Map<String,Object> map = new HashMap<>();
                map.put("ID",strs);
                String sql = "SELECT * FROM APP_SERVE_PACKAGE WHERE ID IN (:ID)";
                List<AppServePackage> ll = dao.getServiceDo().findSqlMap(sql,map,AppServePackage.class);
                if(ll!=null && ll.size()>0){
                    for(AppServePackage v : ll){
                        if(StringUtils.isNotBlank(v.getSerpkRemark())){
                            if(StringUtils.isNotBlank(result)){
                                result += "," +v.getSerpkRemark();
                            }else{
                                result = v.getSerpkRemark();
                            }
                        }
                    }
                }
        }
        this.pkRemark = result;
    }

    public String getObjectState() {
        return objectState;
    }

    public void setObjectState(String objectState) {
        String result = null;
        if(StringUtils.isNotBlank(objectState)){
            String[] strs = objectState.split(";");
            SysDao dao = (SysDao) SpringHelper.getBean("sysDao");
            Map<String,Object> map = new HashMap<>();
            map.put("ID",strs);
            String sql = "SELECT * FROM APP_SERVE_OBJECT WHERE ID IN (:ID)";
            List<AppServeObject> ll = dao.getServiceDo().findSqlMap(sql,map,AppServeObject.class);
            if(ll!=null && ll.size()>0){
                for(AppServeObject v : ll){
                    if(StringUtils.isNotBlank(result)){
                        result += "," +v.getSeroState();
                    }else{
                        if(StringUtils.isBlank(v.getSeroState())) {
                            result = v.getSeroState()+",";
                        }else{
                            result = v.getSeroState();
                        }
                    }
                }
            }
        }
        this.objectState = result;
    }

    public String getObjectValue() {
        return objectValue;
    }

    public void setObjectValue(String objectValue) {
        this.objectValue = objectValue;
    }

    public String getPkImageName() {
        return pkImageName;
    }

    public void setPkImageName(String pkImageName) throws Exception {
        String result = null;
        if(StringUtils.isNotBlank(pkImageName)){
            String[] strs = pkImageName.split(";");
            SysDao dao = (SysDao) SpringHelper.getBean("sysDao");
            Map<String,Object> map = new HashMap<>();
            map.put("ID",strs);
            String sql = "SELECT * FROM APP_SERVE_PACKAGE WHERE ID IN (:ID)";
            List<AppServePackage> ll = dao.getServiceDo().findSqlMap(sql,map,AppServePackage.class);
            if(ll!=null && ll.size()>0){
                for(AppServePackage v : ll){
                    if(StringUtils.isNotBlank(result)){
                        result += "," +v.getSerImageName();
                    }else{
                        if(StringUtils.isBlank(v.getSerImageName())) {
                            result = v.getSerImageName()+",";
                        }else{
                            result = v.getSerImageName();
                        }
                    }
                }
            }
        }
        this.pkImageName = result;
    }
}
