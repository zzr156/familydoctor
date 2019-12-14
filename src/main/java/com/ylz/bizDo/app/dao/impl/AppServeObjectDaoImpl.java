package com.ylz.bizDo.app.dao.impl;

import com.ylz.bizDo.app.dao.AppServeObjectDao;
import com.ylz.bizDo.app.po.AppServeObject;
import com.ylz.bizDo.app.vo.AppServeGroupQvo;
import com.ylz.bizDo.app.vo.AppServeObjectQvo;
import com.ylz.packaccede.allDo.SysDao;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by zzl on 2017/8/11.
 */
@Service("appServeObjectDao")
@Transactional(rollbackForClassName={"Exception"})
public class AppServeObjectDaoImpl implements AppServeObjectDao {
    @Autowired
    private SysDao sysDao;

    /**
     * 条件查询数据列表
     * @param qvo
     * @return
     * @throws Exception
     */
    @Override
    public List<AppServeObject> findList(AppServeObjectQvo qvo) throws Exception {
        Map<String,Object> map = new HashMap<String,Object>();
        String sql = "SELECT * FROM APP_SERVE_OBJECT WHERE 1=1";
        if(StringUtils.isNotBlank(qvo.getSerName())){
            map.put("serName","%"+qvo.getSerName()+"%");
            sql += " AND SERO_NAME LIKE :serName";
        }
        if("2".equals(qvo.getType())){
            map.put("areaCode",qvo.getAreaCode());
            map.put("hospId",qvo.getHospId());
            map.put("level","0");
            map.put("openState","1");
            //对于市级医院来说查询自己+系统开放的
            if(StringUtils.isBlank(qvo.getAreaCode())){//市权限
                sql += " AND ( SERO_DEPT_ID =:hospId OR ( SERO_OPEN_STATE =:openState AND ID IN (SELECT a.OPEN_SER_ID FROM APP_OPEN_OBJECT a WHERE a.OPEN_HOSP_ID =:hospId )))";
            }else{//医院权限查询自己的+市的+系统开放的
                sql += " AND ( SERO_DEPT_ID =:hospId OR SERO_AREA_CODE =:areaCode OR (SERO_OPEN_STATE =:openState AND ID IN (SELECT a.OPEN_SER_ID FROM APP_OPEN_OBJECT a WHERE a.OPEN_HOSP_ID =:hospId )))";
            }
        }
        sql += " ORDER BY SERO_CREATE_TIME DESC ";
        List<AppServeObject> ls = sysDao.getServiceDo().findSqlMap(sql,map,AppServeObject.class,qvo);
        return ls;
    }

    @Override
    public List<AppServeObject> findAllList() throws Exception {
        Map<String,Object> map = new HashMap<String,Object>();
        String sql = "SELECT * FROM APP_SERVE_OBJECT WHERE 1=1";
        sql += " ORDER BY SERO_CREATE_TIME DESC ";
        List<AppServeObject> ls = sysDao.getServiceDo().findSqlMap(sql,map,AppServeObject.class);
        return ls;
    }

    /**
     * 组合查询服务人群
     * @param roleType
     * @param hospId
     * @param areaCode
     * @return
     * @throws Exception
     */
    @Override
    public List<AppServeObject> findByPeople(String roleType, String hospId, String areaCode) throws Exception {
        Map<String,Object> map = new HashMap<String,Object>();
        String sql = "SELECT * FROM APP_SERVE_OBJECT WHERE 1=1";
        if("2".equals(roleType)){
            map.put("areaCode",areaCode);
            map.put("hospId",hospId);
            map.put("level","0");
            map.put("openState","1");
            //对于市级医院来说查询自己+系统开放的
            if(StringUtils.isBlank(areaCode)){//市权限
                sql += " AND ( SERO_DEPT_ID =:hospId OR ( SERO_OPEN_STATE =:openState AND ID IN (SELECT a.OPEN_SER_ID FROM APP_OPEN_OBJECT a WHERE a.OPEN_HOSP_ID =:hospId )))";
            }else{//医院权限查询自己的+市的+系统开放的
                sql += " AND ( SERO_DEPT_ID =:hospId OR SERO_AREA_CODE =:areaCode OR (SERO_OPEN_STATE =:openState AND ID IN (SELECT a.OPEN_SER_ID FROM APP_OPEN_OBJECT a WHERE a.OPEN_HOSP_ID =:hospId )))";
            }
        }
        sql += " ORDER BY SERO_VALUE ";
        List<AppServeObject> ls = sysDao.getServiceDo().findSqlMap(sql,map,AppServeObject.class);
        return ls;
    }

    /**
     * 查询编号
     * @return
     */
    @Override
    public String findCode() throws Exception{
        Map<String,Object> map = new HashMap<>();
        String sql = "SELECT SERO_VALUE FROM APP_SERVE_OBJECT WHERE SERO_VALUE regexp '^[0-9]+$' ORDER BY SERO_CREATE_TIME DESC limit 1";
        List<Map> list = sysDao.getServiceDo().findSqlMap(sql,map);
        if(list !=null && list.size()>0){
            return list.get(0).get("SERO_VALUE").toString();
        }
        return null;
    }

    @Override
    public Boolean isReferencedByGroup(AppServeObject pk) throws Exception{
        String sql="select t1.id " +
                "from APP_SERVE_OBJECT t1,APP_SERVE_GROUP t2 " +
                "where t1.sero_value=t2.serg_value and t1.sero_value=:pkValue " +
                "and t1.SERO_DEPT_ID=:deptId";
        Map<String,Object> map = new HashMap<>();
        map.put("pkValue",pk.getSeroValue());
        map.put("deptId",pk.getSeroDeptId());
        List<AppServeObject> ls=sysDao.getServiceDo().findSqlMapRVo(sql,map,AppServeObject.class);
        if(ls.size()>0){
            return true;
        }
        return false;
    }

    @Override
    public List<AppServeObject> findByPeople(AppServeGroupQvo qvo) throws Exception{
        Map<String,Object> map = new HashMap<String,Object>();
        String sql = "SELECT * FROM APP_SERVE_OBJECT WHERE 1=1";
        if("2".equals(qvo.getType())){
            map.put("areaCode",qvo.getAreaCode());
            map.put("hospId",qvo.getHospId());
            map.put("level","0");
            map.put("openState","1");
            //对于市级医院来说查询自己+系统开放的
            if(StringUtils.isBlank(qvo.getAreaCode())){//市权限
                sql += " AND ( SERO_DEPT_ID =:hospId OR ( SERO_OPEN_STATE =:openState AND ID IN (SELECT a.OPEN_SER_ID FROM APP_OPEN_OBJECT a WHERE a.OPEN_HOSP_ID =:hospId )))";
            }else{//医院权限查询自己的+市的+系统开放的
                sql += " AND ( SERO_DEPT_ID =:hospId OR SERO_AREA_CODE =:areaCode OR (SERO_OPEN_STATE =:openState AND ID IN (SELECT a.OPEN_SER_ID FROM APP_OPEN_OBJECT a WHERE a.OPEN_HOSP_ID =:hospId )))";
            }
        }
        if(StringUtils.isNotBlank(qvo.getContent())){
            map.put("SERO_NAME","%"+qvo.getContent()+"%");
            sql += " AND SERO_NAME LIKE :SERO_NAME";
        }
        sql += " ORDER BY SERO_VALUE ";
        List<AppServeObject> ls = sysDao.getServiceDo().findSqlMap(sql,map,AppServeObject.class,qvo);
        return ls;
    }

    @Override
    public AppServeObject findByValue(String value) throws Exception{
        Map<String,Object> map = new HashMap<>();
        map.put("value",value);
        String sql = " SELECT * FROM APP_SERVE_OBJECT WHERE SERO_VALUE=:value";
        List<AppServeObject> list = sysDao.getServiceDo().findSqlMap(sql,map,AppServeObject.class);
        if(list!=null && list.size()>0){
            return list.get(0);
        }
        return null;
    }
}
