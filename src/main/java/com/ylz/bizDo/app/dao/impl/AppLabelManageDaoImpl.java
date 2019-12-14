package com.ylz.bizDo.app.dao.impl;

import com.ylz.bizDo.app.dao.AppLabelManageDao;
import com.ylz.bizDo.app.po.AppLabelManage;
import com.ylz.bizDo.app.vo.AppLabelManageQvo;
import com.ylz.bizDo.jtapp.commonEntity.AppMeddleEntity;
import com.ylz.bizDo.jtapp.gaiRuiEntity.GaiRuiLabelEntity;
import com.ylz.packaccede.allDo.SysDao;
import com.ylz.packcommon.common.exception.DaoException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("appLabelManageDao")
@Transactional(rollbackForClassName={"Exception"})
public class AppLabelManageDaoImpl implements AppLabelManageDao {
    @Autowired
    private SysDao sysDao;

    @Override
    public List<AppLabelManage> findListQvo(AppLabelManageQvo qvo) throws Exception {
        Map<String,Object> map = new HashMap<String,Object>();
        String sql = "SELECT * FROM APP_LABEL_MANAGE  as a WHERE 1=1 ";
        return sysDao.getServiceDo().findSqlMap(sql, map, AppLabelManage.class, qvo);
    }

    public AppLabelManage findLabelByValue(String type, String value) throws Exception{
        HashMap map=new HashMap();
        map.put("LABEL_TYPE",type);
        map.put("LABEL_VALUE",value);
        String sql="SELECT * FROM APP_LABEL_MANAGE a where a.LABEL_TYPE=:LABEL_TYPE and a.LABEL_VALUE=:LABEL_VALUE";
        List<AppLabelManage> ls=sysDao.getServiceDo().findCachSqlMap(sql, map, AppLabelManage.class);
        if(ls!=null&&ls.size()>0){
            return ls.get(0);
        }
        return null;
    }

    @Override
    public List<AppLabelManage> findByType(String value) throws Exception{
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("type",value);
        String sql = "SELECT * FROM APP_LABEL_MANAGE a where a.LABEL_TYPE=:type ";
        sql +=" ORDER BY LABEL_VALUE ";
        List<AppLabelManage> ls=sysDao.getServiceDo().findCachSqlMap(sql, map, AppLabelManage.class);
        return ls;
    }

    @Override
    public List<AppMeddleEntity> findByMeddle(String value) throws Exception{
        HashMap map=new HashMap();
        map.put("type",value);
        String sql="SELECT a.ID id,a.LABEL_TITLE title,a.LABEL_VALUE value,'' state FROM APP_LABEL_MANAGE a WHERE 1=1 AND a.LABEL_TYPE=:type ";
        sql += " ORDER BY a.LABEL_VALUE ";
        List<AppMeddleEntity> ls=sysDao.getServiceDo().findSqlMapRVo(sql, map, AppMeddleEntity.class);
        return ls;
    }

    @Override
    public List<AppLabelManage> findList() throws Exception {
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("LABEL_TYPE","2");
        String sql = "SELECT * FROM APP_LABEL_MANAGE  as a WHERE 1=1 and a.LABEL_TYPE = :LABEL_TYPE ";
        return sysDao.getServiceDo().findSqlMap(sql, map, AppLabelManage.class);
    }

    @Override
    public List<GaiRuiLabelEntity> findLabelByGroup(String group) throws Exception {
        Map<String,Object> map = new HashMap<>();
        map.put("labelType",group);
        String sql = "SELECT ID id," +
                "LABEL_VALUE labelValue," +
                "LABEL_TITLE labelTitle " +
                "FROM APP_LABEL_MANAGE " +
                "WHERE LABEL_TYPE =:labelType ";
        List<GaiRuiLabelEntity> list = sysDao.getServiceDo().findSqlMapRVo(sql,map,GaiRuiLabelEntity.class);
        return list;
    }
}
