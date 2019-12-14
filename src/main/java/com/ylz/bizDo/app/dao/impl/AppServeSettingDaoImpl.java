package com.ylz.bizDo.app.dao.impl;

import com.ylz.bizDo.app.dao.AppServeSettingDao;
import com.ylz.bizDo.app.entity.AppServeRoleEntity;
import com.ylz.bizDo.app.po.AppServeSetting;
import com.ylz.bizDo.app.vo.AppServeSettingQvo;
import com.ylz.packaccede.allDo.SysDao;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zzl on 2017/7/21.
 */
@Service("appServeSettingDao")
@Transactional(rollbackForClassName={"Exception"})
public class AppServeSettingDaoImpl implements AppServeSettingDao {
    @Autowired
    private SysDao sysDao;

    @Override
    public List<AppServeSetting> findList(AppServeSettingQvo qvo) throws Exception{
        Map<String,Object> map = new HashMap<String,Object>();
        String sql = " SELECT * FROM APP_SERVE_SETTING a WHERE 1=1";
        if(StringUtils.isNotBlank(qvo.getResident())){
            map.put("resident","%"+qvo.getResident()+"%");
            sql += " AND a.SER_OBJECT_TITLE LIKE :resident";
        }
        if(StringUtils.isNotBlank(qvo.getServeType())){
            map.put("serveType","%"+qvo.getServeType()+"%");
            sql += " AND a.SER_TITLE LIKE :serveType";
        }
        sql += " ORDER BY a.SER_OBJECT_VALUE ";
        List<AppServeSetting> ls = sysDao.getServiceDo().findSqlMap(sql,map,AppServeSetting.class,qvo);
        return ls;
    }

    @Override
    public AppServeSetting findBySer(String reValue, String serValue) throws Exception{
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("reValue",reValue);
        map.put("serValue",serValue);
        String sql = " SELECT * FROM APP_SERVE_SETTING a WHERE 1=1";
        sql += " AND a.SER_OBJECT_VALUE = :reValue";
        sql += " AND a.SER_VALUE =:serValue";
        List<AppServeSetting> ls = sysDao.getServiceDo().findSqlMap(sql,map,AppServeSetting.class);
        if(ls!=null && ls.size()>0){
            return ls.get(0);
        }
        return null;
    }
    
    /**
     * 查询
     * @return
     */
    @Override
    public List<AppServeRoleEntity> findCmmServe(String serveId) throws Exception{
        Map<String,Object> map = new HashMap<String,Object>();
        String sql = " SELECT\n" +
                "\ta.ID serSettingId,\n"+
                "\tc.ID serRoleSonId,\n"+
                "\tSER_OBJECT_TITLE serObjectTitle,\n" +
                "\tSER_TITLE serTitle,\n" +
                "\tc.SON_SERVE_SET_NUM setNum,\n" +
                "\tc.SON_SERVE_SET_SPACE setSpace,\n" +
                "\tc.SON_SERVE_SET_SPACE_TYPE setSpaceType\n" +
                "FROM\n" +
                "\tAPP_SERVE_SETTING a\n" +
                "LEFT JOIN APP_SERVE_ROLE_SON c ON a.ID = c.SON_SERVE_ID\n" +
                "WHERE\n" +
                "\t1 = 1";
        if(StringUtils.isNotBlank(serveId)){
            map.put("SON_SERVE_ROLE_ID",serveId);
            sql += " AND c.SON_SERVE_ROLE_ID = :SON_SERVE_ROLE_ID ";
        }
        sql += " ORDER BY a.SER_OBJECT_VALUE,a.SER_VALUE ";
        List<AppServeRoleEntity> ls = sysDao.getServiceDo().findSqlMapRVo(sql,map,AppServeRoleEntity.class);
        if(ls != null && ls.size() >0) {
            return ls;
        }else{
            map = new HashMap<String,Object>();
            sql = " SELECT\n" +
                    "\ta.ID serSettingId,\n"+
                    "\t'' serRoleSonId,\n"+
                    "\tSER_OBJECT_TITLE serObjectTitle,\n" +
                    "\tSER_TITLE serTitle,\n" +
                    "\t'' setNum,\n" +
                    "\t'' setSpace,\n" +
                    "\t'' setSpaceType\n" +
                    "FROM\n" +
                    "\tAPP_SERVE_SETTING a\n" +
                    "WHERE\n" +
                    "\t1 = 1";
            sql += " ORDER BY a.SER_OBJECT_VALUE,a.SER_VALUE ";
            ls = sysDao.getServiceDo().findSqlMapRVo(sql,map,AppServeRoleEntity.class);
            return ls;
        }
    }
}
