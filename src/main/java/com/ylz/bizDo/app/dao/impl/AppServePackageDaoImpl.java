package com.ylz.bizDo.app.dao.impl;

import com.ylz.bizDo.app.dao.AppServePackageDao;
import com.ylz.bizDo.app.po.AppServeGroup;
import com.ylz.bizDo.app.po.AppServePackage;
import com.ylz.bizDo.app.vo.AppServePackageQvo;
import com.ylz.packaccede.allDo.SysDao;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/** 服务包
 * Created by zzl on 2017/8/11.
 */
@Service("appServePackageDao")
@Transactional(rollbackForClassName={"Exception"})
public class AppServePackageDaoImpl implements AppServePackageDao {
    @Autowired
    private SysDao sysDao;
    /**
     * 根据条件查询数据列表
     * @param qvo
     * @return
     * @throws Exception
     */
    @Override
    public List<AppServePackage> findList(AppServePackageQvo qvo) throws Exception {
        Map<String,Object> map = new HashMap<String,Object>();
        String sql = "SELECT * FROM APP_SERVE_PACKAGE WHERE 1=1";
        if(StringUtils.isNotBlank(qvo.getPkName())){
            map.put("pkName","%"+qvo.getPkName()+"%");
            sql += " AND SERPK_NAME LIKE :pkName";
        }
        if("2".equals(qvo.getType())){
            map.put("areaCode",qvo.getAreaCode());
            map.put("hospId",qvo.getHospId());
            map.put("level","0");
            map.put("openState","1");
            //对于市级医院来说查询自己+系统开放的
            if(StringUtils.isBlank(qvo.getAreaCode())){//市权限
                sql += " AND ( SERPK_DEPT_ID =:hospId OR ( SERPK_STATE =:openState AND ID IN (SELECT a.OPEN_SER_ID FROM APP_OPEN_OBJECT a WHERE a.OPEN_HOSP_ID =:hospId )))";
            }else{//医院权限查询自己的+市的+系统开放的
                sql += " AND ( SERPK_DEPT_ID =:hospId OR SERPK_AREA_CODE =:areaCode OR (SERPK_STATE =:openState AND ID IN (SELECT a.OPEN_SER_ID FROM APP_OPEN_OBJECT a WHERE a.OPEN_HOSP_ID =:hospId )))";
            }
        }
//        map.put("SERPK_LEVE","999");
//        sql += " AND SERPK_LEVE = :SERPK_LEVE ";
        sql += " ORDER BY SERPK_CREATE_TIME DESC ";
        List<AppServePackage> ls = sysDao.getServiceDo().findSqlMap(sql,map,AppServePackage.class,qvo);
        return ls;
    }

    /**
     * 组合查询服务内容
     * @param roleType
     * @param hospId
     * @param areaCode
     * @param type
     * @return
     * @throws Exception
     */
    @Override
    public List<AppServePackage> findListByType(String roleType,String hospId,String areaCode,String type) throws Exception {
        Map<String,Object> map = new HashMap<String,Object>();
        String sql = "SELECT * FROM APP_SERVE_PACKAGE WHERE 1=1";
        if(StringUtils.isNotBlank(type)){
            map.put("typeO",type);
            sql += " AND SERPK_BASE_TYPE =:typeO";
        }
        if("2".equals(roleType)){
            map.put("areaCode",areaCode);
            map.put("hospId",hospId);
            map.put("level","0");
            map.put("openState","1");
            //对于市级医院来说查询自己+系统开放的
            if(StringUtils.isBlank(areaCode)){//市权限
                sql += " AND ( SERPK_DEPT_ID =:hospId OR ( SERPK_STATE =:openState AND ID IN (SELECT a.OPEN_SER_ID FROM APP_OPEN_OBJECT a WHERE a.OPEN_HOSP_ID =:hospId )))";
            }else{//医院权限查询自己的+市的+系统开放的
                sql += " AND ( SERPK_DEPT_ID =:hospId OR SERPK_AREA_CODE =:areaCode OR (SERPK_STATE =:openState AND ID IN (SELECT a.OPEN_SER_ID FROM APP_OPEN_OBJECT a WHERE a.OPEN_HOSP_ID =:hospId )))";
            }
        }
        sql += " ORDER BY SERPK_VALUE ";
        List<AppServePackage> ls = sysDao.getServiceDo().findSqlMap(sql,map,AppServePackage.class);
        return ls;
    }

    /**
     * 查询最新编号
     * @return
     */
    @Override
    public String findCode() throws Exception{
        Map<String,Object> map = new HashMap<>();
        String sql = "SELECT SERPK_VALUE  FROM APP_SERVE_PACKAGE WHERE SERPK_VALUE regexp '^[0-9]+$' ORDER BY SERPK_CREATE_TIME DESC limit 1";
        List<Map> list = sysDao.getServiceDo().findSqlMap(sql,map);
        if(list!=null && list.size()>0){
            return list.get(0).get("SERPK_VALUE").toString();
        }
        return null;
    }

    @Override
    public Boolean isReferencedByGroup(AppServePackage pk) throws Exception{
        String sql="select t1.id " +
                "from APP_SERVE_GROUP t1 " +
                "where t1.serg_pk_id like '%"+pk.getId()+"%' " +
                "and t1.SERG_DEPT_ID=:deptId" ;
        Map<String,Object> map = new HashMap<>();
        map.put("deptId",pk.getSerpkDeptId());
        List<AppServeGroup> ls=sysDao.getServiceDo().findSqlMapRVo(sql,map,AppServeGroup.class);
        if(ls.size()>0){
            return true;
        }
        return false;
    }

    @Override
    public AppServePackage findByValue(String value) throws Exception{
        Map<String,Object> map = new HashMap<>();
        map.put("value",value);
        String sql = "SELECT * FROM APP_SERVE_PACKAGE WHERE SERPK_VALUE = :value";
        List<AppServePackage> list = sysDao.getServiceDo().findSqlMap(sql,map,AppServePackage.class);
        if(list!=null && list.size()>0){
            return list.get(0);
        }
        return null;
    }

    @Override
    public List<AppServePackage> findListNQvo(AppServePackageQvo qvo) throws Exception{
        Map<String,Object> map = new HashMap<String,Object>();
        String sql = "SELECT * FROM APP_SERVE_PACKAGE WHERE 1=1";
//        if(StringUtils.isNotBlank(qvo.getPkName())){
//            map.put("pkName","%"+qvo.getPkName()+"%");
//            sql += " AND SERPK_NAME LIKE :pkName";
//        }
        if("2".equals(qvo.getType())){
            map.put("areaCode",qvo.getAreaCode());
            map.put("hospId",qvo.getHospId());
            map.put("level","0");
            map.put("openState","1");
            //对于市级医院来说查询自己+系统开放的
            if(StringUtils.isBlank(qvo.getAreaCode())){//市权限
                sql += " AND ( SERPK_DEPT_ID =:hospId OR ( SERPK_STATE =:openState AND ID IN (SELECT a.OPEN_SER_ID FROM APP_OPEN_OBJECT a WHERE a.OPEN_HOSP_ID =:hospId )))";
            }else{//医院权限查询自己的+市的+系统开放的
                sql += " AND ( SERPK_DEPT_ID =:hospId OR SERPK_AREA_CODE =:areaCode OR (SERPK_STATE =:openState AND ID IN (SELECT a.OPEN_SER_ID FROM APP_OPEN_OBJECT a WHERE a.OPEN_HOSP_ID =:hospId )))";
            }
        }
//        map.put("SERPK_LEVE","999");
//        sql += " AND SERPK_LEVE = :SERPK_LEVE ";
        sql += " ORDER BY SERPK_CREATE_TIME DESC ";
        List<AppServePackage> ls = sysDao.getServiceDo().findSqlMap(sql,map,AppServePackage.class);
        return ls;
    }

    @Override
    public List<AppServePackage> findPakege(String pkId, String fwType) throws Exception{
        Map<String,Object> map = new HashMap<>();
        map.put("pkIds",pkId.split(";"));
        map.put("fwType",fwType);
        String sql = "SELECT\n" +
                "\ta.*\n" +
                "FROM\n" +
                "\tapp_serve_package a\n" +
                "WHERE\n" +
                "\t1 = 1\n" +
                "AND a.SERPK_BASE_TYPE = '0'\n" +
                "AND a.SERPK_IMAGE_URL = :fwType\n" +
                "AND a.ID IN (:pkIds)";
        List<AppServePackage> list = sysDao.getServiceDo().findSqlMap(sql,map,AppServePackage.class);
        return list;
    }

    /**
     * 根据服务内容id查询集合
     * @param pkIds
     * @return
     */
    @Override
    public List<AppServePackage> findPakeges(String pkIds) throws Exception{
        Map<String,Object> map = new HashMap<>();
        if(StringUtils.isBlank(pkIds)){
            return null;
        }
        String sql = " SELECT * FROM APP_SERVE_PACKAGE WHERE 1=1 ";
        map.put("pkIds",pkIds.split(";"));
        sql += " AND ID IN (:pkIds) ";
        List<AppServePackage> list = sysDao.getServiceDo().findSqlMap(sql,map,AppServePackage.class);
        if(list != null && list.size()>0){
            return list;
        }
        return null;
    }
}
