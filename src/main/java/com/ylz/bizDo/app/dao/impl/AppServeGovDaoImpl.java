package com.ylz.bizDo.app.dao.impl;

import com.ylz.bizDo.app.dao.AppServeGovDao;
import com.ylz.bizDo.app.po.AppEconAndGov;
import com.ylz.bizDo.app.po.AppServeGov;
import com.ylz.bizDo.app.vo.AppServeGovQvo;
import com.ylz.packaccede.allDo.SysDao;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zzl on 2017/8/16.
 */
@Service("appServeGovDao")
@Transactional(rollbackForClassName={"Exception"})
public class AppServeGovDaoImpl implements AppServeGovDao {
    @Autowired
    private SysDao sysDao;
    /**
     * 初始查询
     * @param qvo
     * @return
     * @throws Exception
     */
    @Override
    public List<AppServeGov> findList(AppServeGovQvo qvo) throws Exception {
        Map<String,Object> map = new HashMap<String,Object>();
        String sql = "SELECT * FROM APP_SERVE_GOV WHERE 1=1";
        if(StringUtils.isNotBlank(qvo.getContent())){
            map.put("content","%"+qvo.getContent()+"%");
            sql += " AND GOV_TITLE LIKE :content";
        }
        if("2".equals(qvo.getRoleType())){
            map.put("areaCode",qvo.getAreaCode());
            map.put("hospId",qvo.getHospId());
            map.put("level","0");
            map.put("openState","1");
            //对于市级医院来说查询自己+系统开放的
            if(StringUtils.isBlank(qvo.getAreaCode())){//市权限
                sql += " AND ( GOV_DEPT_ID =:hospId OR ( GOV_OPEN_STATE =:openState AND ID IN (SELECT a.OPEN_SER_ID FROM APP_OPEN_OBJECT a WHERE a.OPEN_HOSP_ID =:hospId )))";
            }else{//医院权限查询自己的+市的+系统开放的
                sql += " AND ( GOV_DEPT_ID =:hospId OR GOV_AREA_CODE =:areaCode OR (GOV_OPEN_STATE =:openState AND ID IN (SELECT a.OPEN_SER_ID FROM APP_OPEN_OBJECT a WHERE a.OPEN_HOSP_ID =:hospId )))";
            }
        }
        sql += " ORDER BY GOV_CREATE_TIME DESC";
        List<AppServeGov> ls = sysDao.getServiceDo().findSqlMap(sql,map,AppServeGov.class,qvo);
        return ls;
    }

    @Override
    public List<AppServeGov> findAllList(String roleType,String areaCode,String hospId) throws Exception{
        Map<String,Object> map = new HashMap<String,Object>();
        String sql = "SELECT * FROM APP_SERVE_GOV WHERE 1=1";
        if("2".equals(roleType)){
            map.put("areaCode",areaCode);
            map.put("hospId",hospId);
            map.put("level","0");
            map.put("openState","1");
            //对于市级医院来说查询自己+系统开放的
            if(StringUtils.isBlank(areaCode)){//市权限
                sql += " AND ( GOV_DEPT_ID =:hospId OR ( GOV_OPEN_STATE =:openState AND ID IN (SELECT a.OPEN_SER_ID FROM APP_OPEN_OBJECT a WHERE a.OPEN_HOSP_ID =:hospId )))";
            }else{//医院权限查询自己的+市的+系统开放的
                sql += " AND ( GOV_DEPT_ID =:hospId OR GOV_AREA_CODE =:areaCode OR (GOV_OPEN_STATE =:openState AND ID IN (SELECT a.OPEN_SER_ID FROM APP_OPEN_OBJECT a WHERE a.OPEN_HOSP_ID =:hospId )))";
            }
        }else{
            sql += " AND GOV_AREA_CODE = '0'";
        }
        sql += " ORDER BY GOV_VALUE";
        List<AppServeGov> ls = sysDao.getServiceDo().findSqlMap(sql,map,AppServeGov.class);
        return ls;
    }

    @Override
    public List<AppServeGov> findByEcon(String[] strs) throws Exception {
        Map<String,Object> map = new HashMap<String,Object>();
        String sql = "SELECT * FROM APP_SERVE_GOV WHERE 1=1";
        map.put("strs",strs);
        sql += " AND ID IN :strs";
        List<AppServeGov> ls = sysDao.getServiceDo().findSqlMap(sql,map,AppServeGov.class);
        return ls;
    }

    /**
     * 查询最新编号
     * @return
     */
    @Override
    public String findCode() throws Exception{
        Map<String,Object> map = new HashMap<>();
        String sql = "SELECT GOV_VALUE FROM APP_SERVE_GOV WHERE GOV_VALUE regexp '^[0-9]+$' ORDER BY GOV_CREATE_TIME DESC  limit 1";
        List<Map> list = sysDao.getServiceDo().findSqlMap(sql,map);
        if(list!=null && list.size()>0){
            return list.get(0).get("GOV_VALUE").toString();
        }
        return null;
    }

    @Override
    public Boolean isReferencedByEG(AppServeGov pk) throws Exception{
        String sql="select t1.id " +
                "from APP_ECON_AND_GOV t1 " +
                "where t1.EAG_GOV_ID like '%"+pk.getId()+"%' " +
                "and t1.EAG_DEPT_ID=:deptId";
        Map<String,Object> map = new HashMap<>();
        map.put("deptId",pk.getGovDeptId());
        List<AppEconAndGov> ls=sysDao.getServiceDo().findSqlMapRVo(sql,map,AppEconAndGov.class);
        if(ls.size()>0){
            return true;
        }
        return false;
    }

    @Override
    public AppServeGov findByValue(String value) throws Exception{
        Map<String,Object> map = new HashMap<>();
        map.put("value",value);
        String sql = "SELECT * FROM APP_SERVE_GOV WHERE GOV_VALUE=:value";
        List<AppServeGov> list = sysDao.getServiceDo().findSqlMap(sql,map,AppServeGov.class);
        if(list!=null && list.size()>0){
            return list.get(0);
        }
        return null;
    }
}
