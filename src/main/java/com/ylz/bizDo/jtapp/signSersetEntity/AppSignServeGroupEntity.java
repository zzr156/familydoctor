package com.ylz.bizDo.jtapp.signSersetEntity;

import com.ylz.packaccede.allDo.SysDao;
import com.ylz.packcommon.common.bizDo.SpringHelper;
import org.apache.commons.lang.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zzl on 2018/3/20.
 */
public class AppSignServeGroupEntity {
    private String id;//组合主键
    private String value;//组合值
    private String objectId;//服务对象主键
    private String pkId;//服务内容主键
    private AppSignServeObjectEntity obEntiry;//服务对象信息
    private List<AppSignServePkEntity> pkList;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public String getPkId() {
        return pkId;
    }

    public void setPkId(String pkId) {
        this.pkId = pkId;
    }

    public AppSignServeObjectEntity getObEntiry() {
        return obEntiry;
    }

    public void setObEntiry(String obEntiry) {
        AppSignServeObjectEntity ob = null;
        if(StringUtils.isNotBlank(this.getObjectId())){
            SysDao dao = (SysDao) SpringHelper.getBean("sysDao");
            Map<String,Object> map = new HashMap<>();
            map.put("obId",this.getObjectId());
            String sql = "SELECT ID id," +
                    "SERO_NAME title," +
                    "SERO_VALUE value," +
                    "SERO_FWTYPE fwType," +
                    "SERO_LABEL_TYPE labelType " +
                    "FROM APP_SERVE_OBJECT " +
                    "WHERE 1=1 AND ID = :obId ";
            List<AppSignServeObjectEntity> obs = dao.getServiceDo().findSqlMapRVo(sql,map,AppSignServeObjectEntity.class);
            if(obs!=null && obs.size()>0){
                ob = obs.get(0);
            }
        }
        this.obEntiry = ob;
    }

    public List<AppSignServePkEntity> getPkList() {
        return pkList;
    }

    public void setPkList(String pkList) {
        List<AppSignServePkEntity> listPk = null;
        if(StringUtils.isNotBlank(this.getPkId())){
            SysDao dao = (SysDao)SpringHelper.getBean("sysDao");
            Map<String,Object> map = new HashMap<>();
            map.put("pkIds",this.getPkId().split(";"));
            String sql = "SELECT ID id," +
                    "SERPK_NAME title," +
                    "SERPK_VALUE value," +
                    "SERPK_OPEN_STATE openState," +
                    "SERPK_TIME num " +
                    "FROM APP_SERVE_PACKAGE " +
                    "WHERE 1=1 AND ID IN(:pkIds)";

            listPk = dao.getServiceDo().findSqlMapRVo(sql,map,AppSignServePkEntity.class);
        }
        this.pkList = listPk;
    }
}
