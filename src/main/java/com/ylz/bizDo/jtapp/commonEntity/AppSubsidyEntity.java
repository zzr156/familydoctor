package com.ylz.bizDo.jtapp.commonEntity;

import com.ylz.bizDo.app.po.AppLabelManage;
import com.ylz.packaccede.allDo.SysDao;
import com.ylz.packcommon.common.bizDo.SpringHelper;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 补贴情况返回对象
 * Created by zzl on 2019/2/13.
 */
public class AppSubsidyEntity {
    private String id;//主键
    private String jjTitle;//经济类型
    private String jjValue;//经济类型值
    private List<AppSubsidySonEntity> sonEntity;//补贴类型情况
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getJjTitle() {
        return jjTitle;
    }

    public void setJjTitle(String jjTitle) throws Exception {
        if(StringUtils.isNotBlank(jjTitle)){
            SysDao dao = (SysDao)SpringHelper.getBean("sysDao");
            AppLabelManage manage = dao.getAppLabelManageDao().findLabelByValue("4",jjTitle);
            if(manage != null){
                jjTitle = manage.getLabelTitle();
            }
        }
        this.jjTitle = jjTitle;
    }

    public String getJjValue() {
        return jjValue;
    }

    public void setJjValue(String jjValue) {
        this.jjValue = jjValue;
    }

    public List<AppSubsidySonEntity> getSonEntity() {
        return sonEntity;
    }

    public void setSonEntity(String sonEntity) {
        List<AppSubsidySonEntity> list = null;
        if(StringUtils.isNotBlank(sonEntity)){
            SysDao dao = (SysDao) SpringHelper.getBean("sysDao");
            Map<String,Object> map = new HashMap<>();
            map.put("btIds",sonEntity.split(";"));
            String sql = "select gov_title title,gov_money money from app_serve_gov where id IN (:btIds)";
            list = dao.getServiceDo().findSqlMapRVo(sql,map,AppSubsidySonEntity.class);
        }
        this.sonEntity = list;
    }
}
