package com.ylz.bizDo.app.dao.impl;

import com.ylz.bizDo.app.dao.AppServeEconDao;
import com.ylz.bizDo.app.po.AppServeEcon;
import com.ylz.bizDo.app.po.AppServeSetmeal;
import com.ylz.bizDo.app.vo.AppServeEconQvo;
import com.ylz.bizDo.jtapp.commonEntity.AppSubsidyEntity;
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
@Service("appServeEconDao")
@Transactional(rollbackForClassName={"Exception"})
public class AppServeEconDaoImpl implements AppServeEconDao {
    @Autowired
    private SysDao sysDao;
    /**
     * 初始查询
     * @param qvo
     * @return
     */
    @Override
    public List<AppServeEcon> findList(AppServeEconQvo qvo) throws Exception{
        Map<String,Object> map = new HashMap<String,Object>();
        String sql = "SELECT * FROM APP_SERVE_ECON WHERE 1=1";
        if(StringUtils.isNotBlank(qvo.getContent())){
            map.put("content","%"+qvo.getContent()+"%");
            sql += " AND ECON_TITLE LIKE :content";
        }
        if("2".equals(qvo.getRoleType())){
            map.put("areaCode",qvo.getAreaCode());
            map.put("hospId",qvo.getHospId());
            map.put("level","0");
            map.put("openState","1");
            //对于市级医院来说查询自己+系统开放的
            if(StringUtils.isBlank(qvo.getAreaCode())){//市权限
                sql += " AND ( ECON_DEPT_ID =:hospId OR ( ECON_OPEN_STATE =:openState AND ID IN (SELECT a.OPEN_SER_ID FROM APP_OPEN_OBJECT a WHERE a.OPEN_HOSP_ID =:hospId )))";
            }else{//医院权限查询自己的+市的+系统开放的
                sql += " AND ( ECON_DEPT_ID =:hospId OR ECON_AREA_CODE =:areaCode OR (ECON_OPEN_STATE =:openState AND ID IN (SELECT a.OPEN_SER_ID FROM APP_OPEN_OBJECT a WHERE a.OPEN_HOSP_ID =:hospId )))";
            }
        }

        sql += " ORDER BY ECON_CREATE_TIME DESC";
        List<AppServeEcon> ls = sysDao.getServiceDo().findSqlMap(sql,map,AppServeEcon.class,qvo);
        return ls;
    }

    @Override
    public List<AppServeEcon> findAllList(String roleType,String areaCode,String hospId) throws Exception{
        Map<String,Object> map = new HashMap<String,Object>();
        String sql = "SELECT * FROM APP_SERVE_ECON WHERE 1=1";
        if("2".equals(roleType)){
            map.put("areaCode",areaCode);
            map.put("hospId",hospId);
            map.put("level","0");
            map.put("openState","1");
            //对于市级医院来说查询自己+系统开放的
            if(StringUtils.isBlank(areaCode)){//市权限
                sql += " AND ( ECON_DEPT_ID =:hospId OR ( ECON_OPEN_STATE =:openState AND ID IN (SELECT a.OPEN_SER_ID FROM APP_OPEN_OBJECT a WHERE a.OPEN_HOSP_ID =:hospId )))";
            }else{//医院权限查询自己的+市的+系统开放的
                sql += " AND ( ECON_DEPT_ID =:hospId OR ECON_AREA_CODE =:areaCode OR (ECON_OPEN_STATE =:openState AND ID IN (SELECT a.OPEN_SER_ID FROM APP_OPEN_OBJECT a WHERE a.OPEN_HOSP_ID =:hospId )))";
            }
        }else{
            sql += " AND ECON_AREA_CODE = '0'";
        }
        sql += " ORDER BY ECON_VALUE";
        List<AppServeEcon> ls = sysDao.getServiceDo().findSqlMap(sql,map,AppServeEcon.class);
        return ls;
    }

    /**
     * 查询最新编号
     * @return
     */
    @Override
    public String findCode() throws Exception{
        Map<String,Object> map = new HashMap<>();
        String sql = "SELECT ECON_VALUE  FROM APP_SERVE_ECON WHERE ECON_VALUE regexp '^[0-9]+$' ORDER BY ECON_CREATE_TIME DESC limit 1";
        List<Map> list = sysDao.getServiceDo().findSqlMap(sql,map);
        if(list!=null && list.size()>0){
            return list.get(0).get("ECON_VALUE").toString();
        }
        return null;
    }

    @Override
    public Boolean isReferencedByEG(AppServeEcon pk) throws Exception{
        String sql="select t1.id " +
                "from app_serve_econ t1,APP_ECON_AND_GOV t2 " +
                "where t1.econ_value=t2.eag_econ_value and t1.econ_value=:pkValue " +
                "and t1.ECON_DEPT_ID=:deptId";
        Map<String,Object> map = new HashMap<>();
        map.put("pkValue",pk.getEconValue());
        map.put("deptId",pk.getEconDeptId());
        List<AppServeEcon> ls=sysDao.getServiceDo().findSqlMapRVo(sql,map,AppServeEcon.class);
        if(ls.size()>0){
            return true;
        }
        return false;
    }

    @Override
    public AppServeEcon findByValue(String value) throws Exception{
        Map<String,Object> map = new HashMap<>();
        map.put("value",value);
        String sql = "SELECT * FROM APP_SERVE_ECON WHERE ECON_VALUE=:value";
        List<AppServeEcon> list = sysDao.getServiceDo().findSqlMap(sql,map,AppServeEcon.class);
        if(list!=null && list.size()>0){
            return list.get(0);
        }
        return null;
    }

    @Override
    public List<AppSubsidyEntity> findByJjType(String jjId, String mealId) throws Exception {
        Map<String,Object> map = new HashMap<>();
        map.put("jjIds",jjId.split(";"));
        //根据服务包主键查询服务包信息获取补贴主键
        AppServeSetmeal meal = (AppServeSetmeal)sysDao.getServiceDo().find(AppServeSetmeal.class,mealId);
        if(meal != null){
            String btId = meal.getSersmJjId();
            if(StringUtils.isNotBlank(btId)){
                String[] btIds = btId.split(";");
                map.put("btIds",btIds);
                String sql = "SELECT\n" +
                        "\ta.id id,\n" +
                        "\tb.ECON_LABEL_TYPE jjValue,\n" +
                        "\tb.ECON_LABEL_TYPE jjTitle,\n" +
                        "\ta.EAG_GOV_ID sonEntity\n" +
                        "FROM\n" +
                        "\tapp_econ_and_gov a\n" +
                        "INNER JOIN app_serve_econ b ON EAG_ECON_ID = b.ID\n" +
                        "WHERE \n" +
                        "b.ECON_FW_TYPE = '4'\n" +
                        "AND b.ECON_LABEL_TYPE IN (:jjIds)\n" +
                        "AND a.ID IN (:btIds)";
                List<AppSubsidyEntity> list = sysDao.getServiceDo().findSqlMapRVo(sql,map,AppSubsidyEntity.class);
                if(list != null && list.size()>0){
                    return list;
                }
            }
        }
        return null;
    }
}
