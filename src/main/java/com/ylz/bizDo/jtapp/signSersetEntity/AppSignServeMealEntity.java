package com.ylz.bizDo.jtapp.signSersetEntity;

import com.ylz.packaccede.allDo.SysDao;
import com.ylz.packcommon.common.bizDo.SpringHelper;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zzl on 2018/3/20.
 */
public class AppSignServeMealEntity {
    private String id;//服务包主键
    private String title;//服务包名称
    private String value;//服务包值
    private String groupId;//组合id
    private List<AppSignServeGroupEntity> groupList;//组合信息

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public List<AppSignServeGroupEntity> getGroupList() {
        return groupList;
    }

    public void setGroupList(String groupList) {
        List<AppSignServeGroupEntity> listG = null;
        if(StringUtils.isNotBlank(this.getGroupId())){
            SysDao dao = (SysDao) SpringHelper.getBean("sysDao");
            Map<String,Object> map = new HashMap<>();
            map.put("groupIds",this.getGroupId().split(";"));
            String sql = "SELECT ID id," +
                    "SERG_VALUE value," +
                    "SERG_OBJECT_ID objectId," +
                    "SERG_PK_ID pkId," +
                    "'' obEntiry," +
                    "'' pkList " +
                    "FROM APP_SERVE_GROUP WHERE 1=1 AND ID IN (:groupIds)";
            listG = dao.getServiceDo().findSqlMapRVo(sql,map,AppSignServeGroupEntity.class);
        }
        this.groupList = listG;
    }
}
