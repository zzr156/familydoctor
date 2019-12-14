package com.ylz.bizDo.app.dao.impl;

import com.ylz.bizDo.app.dao.AppServeGroupDao;
import com.ylz.bizDo.app.po.AppServeGroup;
import com.ylz.bizDo.app.po.AppServeObject;
import com.ylz.bizDo.app.po.AppServeSetmeal;
import com.ylz.bizDo.app.vo.AppServeGroupQvo;
import com.ylz.bizDo.app.vo.AppServePackageQvo;
import com.ylz.bizDo.app.vo.AppServeSetmealQvo;
import com.ylz.packaccede.allDo.SysDao;
import com.ylz.packcommon.common.comEnum.CommonGroupCxType;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by zzl on 2017/8/13.
 */
@Service("appServeGroupDao")
@Transactional(rollbackForClassName={"Exception"})
public class AppServeGroupDaoImpl implements AppServeGroupDao{
    @Autowired
    private SysDao sysDao;


    @Override
    public List<AppServeGroup> findList(AppServeGroupQvo qvo) throws Exception {
        Map<String,Object> map = new HashMap<String,Object>();
        String sql = "SELECT * FROM APP_SERVE_GROUP WHERE 1=1 ";
        if(StringUtils.isNotBlank(qvo.getContent())&&StringUtils.isNotBlank(qvo.getType())){
            map.put("content","%"+qvo.getContent()+"%");
            if(CommonGroupCxType.FWNR.getValue().equals(qvo.getType())){
                sql += " AND SERG_PK_TITLE LIKE :content";
            }
            if(CommonGroupCxType.FWRQ.getValue().equals(qvo.getType())){
                sql += " AND SERG_OBJECT_TITLE LIKE :content";
            }
        }
        if("2".equals(qvo.getRoleType())){
            map.put("areaCode",qvo.getAreaCode());
            map.put("hospId",qvo.getHospId());
            map.put("level","0");
            map.put("openState","1");
            //对于市级医院来说查询自己+系统开放的
            if(StringUtils.isBlank(qvo.getAreaCode())){//市权限
                sql += " AND ( SERG_DEPT_ID =:hospId OR ( SERG_OPEN_STATE =:openState AND ID IN (SELECT a.OPEN_SER_ID FROM APP_OPEN_OBJECT a WHERE a.OPEN_HOSP_ID =:hospId )))";
            }else{//医院权限查询自己的+市的+系统开放的
                sql += " AND ( SERG_DEPT_ID =:hospId OR SERG_AREA_CODE =:areaCode OR (SERG_OPEN_STATE =:openState AND ID IN (SELECT a.OPEN_SER_ID FROM APP_OPEN_OBJECT a WHERE a.OPEN_HOSP_ID =:hospId )))";
            }
        }
        sql += " ORDER BY SERG_CREATE_TIME DESC";
        List<AppServeGroup> ls = sysDao.getServiceDo().findSqlMap(sql,map,AppServeGroup.class,qvo);
        return ls;
    }

    /**
     * 无分页条件查询
     * @param qvo
     * @return
     * @throws Exception
     */
    @Override
    public List<AppServeGroup> findAllList(AppServeSetmealQvo qvo) throws Exception {
        Map<String,Object> map = new HashMap<String,Object>();
        String sql = "SELECT * FROM APP_SERVE_GROUP WHERE 1=1";
        List<AppServeGroup> ls = sysDao.getServiceDo().findSqlMap(sql,map,AppServeGroup.class);
        return ls;
    }

    /**
     * 套餐查询组合
     * @param roleType
     * @param hospId
     * @param areaCode
     * @return
     * @throws Exception
     */
    @Override
    public List<AppServeGroup> findByGroup(String roleType, String hospId, String areaCode) throws Exception {
        Map<String,Object> map = new HashMap<String,Object>();
        String sql = "SELECT * FROM APP_SERVE_GROUP WHERE 1=1 ";

        if("2".equals(roleType)){
            map.put("areaCode",areaCode);
            map.put("hospId",hospId);
            map.put("level","0");
            map.put("openState","1");
            //对于市级医院来说查询自己+系统开放的
            if(StringUtils.isBlank(areaCode)){//市权限
                sql += " AND ( SERG_DEPT_ID =:hospId OR ( SERG_OPEN_STATE =:openState AND ID IN (SELECT a.OPEN_SER_ID FROM APP_OPEN_OBJECT a WHERE a.OPEN_HOSP_ID =:hospId )))";
            }else{//医院权限查询自己的+市的+系统开放的
                sql += " AND ( SERG_DEPT_ID =:hospId OR SERG_AREA_CODE =:areaCode OR (SERG_OPEN_STATE =:openState AND ID IN (SELECT a.OPEN_SER_ID FROM APP_OPEN_OBJECT a WHERE a.OPEN_HOSP_ID =:hospId )))";
            }
        }
        sql += " ORDER BY SERG_VALUE";
        List<AppServeGroup> ls = sysDao.getServiceDo().findSqlMap(sql,map,AppServeGroup.class);
        return ls;
    }

    /**
     * 查询最新编号
     * @return
     */
    @Override
    public String findCode() throws Exception{
        Map<String,Object> map = new HashMap<>();
        String sql = "SELECT SERG_VALUE FROM APP_SERVE_GROUP WHERE SERG_VALUE regexp '^[0-9]+$' ORDER BY SERG_CREATE_TIME DESC limit 1";
        List<Map> list = sysDao.getServiceDo().findSqlMap(sql,map);
        if(list !=null && list.size()>0){
            return list.get(0).get("SERG_VALUE").toString();
        }
        return null;
    }

    @Override
    public Boolean isReferencedByMeal(AppServeGroup pk) throws Exception{
        String sql="select t1.id " +
                "from APP_SERVE_SETMEAL t1 " +
                "where t1.SERSM_GROUP_ID like '%"+pk.getId()+"%' " +
                "and t1.SERSM_CREATE_DEPT=:deptId";
        Map<String,Object> map = new HashMap<>();
        map.put("deptId",pk.getSergDeptId());
        List<AppServeSetmeal> ls=sysDao.getServiceDo().findSqlMapRVo(sql,map,AppServeSetmeal.class);
        if(ls.size()>0){
            return true;
        }
        return false;
    }

    @Override
    public List<AppServeGroup> findByGroup(AppServeSetmealQvo qvo) throws Exception {
        Map<String,Object> map = new HashMap<String,Object>();
        String sql = "SELECT * FROM APP_SERVE_GROUP WHERE 1=1 ";

        if("2".equals(qvo.getRoleType())){
            map.put("areaCode",qvo.getAreaCode());
            map.put("hospId",qvo.getHospId());
            map.put("level","0");
            map.put("openState","1");
            //对于市级医院来说查询自己+系统开放的
            if(StringUtils.isBlank(qvo.getAreaCode())){//市权限
                sql += " AND ( SERG_DEPT_ID =:hospId OR ( SERG_OPEN_STATE =:openState AND ID IN (SELECT a.OPEN_SER_ID FROM APP_OPEN_OBJECT a WHERE a.OPEN_HOSP_ID =:hospId )))";
            }else{//医院权限查询自己的+市的+系统开放的
                sql += " AND ( SERG_DEPT_ID =:hospId OR SERG_AREA_CODE =:areaCode OR (SERG_OPEN_STATE =:openState AND ID IN (SELECT a.OPEN_SER_ID FROM APP_OPEN_OBJECT a WHERE a.OPEN_HOSP_ID =:hospId )))";
            }
        }
        if(StringUtils.isNotBlank(qvo.getFwrq())){
            map.put("fwrq","%"+qvo.getFwrq()+"%");
            sql += " AND SERG_OBJECT_TITLE LIKE :fwrq ";
        }
        if(StringUtils.isNotBlank(qvo.getFwnr())){
            map.put("fwnr","%"+qvo.getFwnr()+"%");
            sql += " AND SERG_PK_TITLE LIKE :fwnr ";
        }
        if(StringUtils.isNotBlank(qvo.getGroupId())){
            map.put("groupIds",qvo.getGroupId().split(";"));
            sql += " AND ID IN (:groupIds) ";
        }
        sql += " ORDER BY SERG_VALUE";
        List<AppServeGroup> ls = sysDao.getServiceDo().findSqlMap(sql,map,AppServeGroup.class,qvo);
        return ls;
    }

    @Override
    public AppServeGroup findByValue(String value) throws Exception{
        Map<String,Object> map = new HashMap<>();
        map.put("value",value);
        String sql = "SELECT * FROM APP_SERVE_GROUP WHERE SERG_VALUE=:value";
        List<AppServeGroup> list = sysDao.getServiceDo().findSqlMap(sql,map,AppServeGroup.class);
        if(list!=null&& list.size()>0){
            return list.get(0);
        }
        return null;
    }

    @Override
    public List<AppServeGroup> findGroups(String groupIds) throws Exception{
        Map<String,Object> map = new HashMap<>();
        String sql = "SELECT * FROM APP_SERVE_GROUP WHERE 1=1";
        if(StringUtils.isNotBlank(groupIds)){
            map.put("idss",groupIds.split(";"));
            sql += " AND id IN (:idss)";
        }else{
            return null;
        }
        List<AppServeGroup> list = sysDao.getServiceDo().findSqlMap(sql,map,AppServeGroup.class);
        return list;
    }

    @Override
    public List<AppServeObject> findListN(AppServePackageQvo qvo) throws Exception{
        Map<String,Object> map = new HashMap<String,Object>();
        if(StringUtils.isNotBlank(qvo.getPkId())){
            map.put("pkId","%"+qvo.getPkId()+"%");
            String sql = "SELECT\n" +
                    "\t*\n" +
                    "FROM\n" +
                    "\tAPP_SERVE_GROUP a\n" +
                    "INNER JOIN APP_SERVE_OBJECT b ON a.SERG_OBJECT_ID = b.ID\n" +
                    "WHERE\n" +
                    "\ta.SERG_PK_ID LIKE :pkId ";
            if("2".equals(qvo.getType())){
                map.put("areaCode",qvo.getAreaCode());
                map.put("hospId",qvo.getHospId());
                map.put("level","0");
                map.put("openState","1");
                //对于市级医院来说查询自己+系统开放的
                if(StringUtils.isBlank(qvo.getAreaCode())){//市权限
                    sql += " AND ( a.SERG_DEPT_ID =:hospId OR ( a.SERG_OPEN_STATE =:openState AND ID IN (SELECT c.OPEN_SER_ID FROM APP_OPEN_OBJECT c WHERE c.OPEN_HOSP_ID =:hospId )))";
                }else{//医院权限查询自己的+市的+系统开放的
                    sql += " AND ( a.SERG_DEPT_ID =:hospId OR a.SERG_AREA_CODE =:areaCode OR (a.SERG_OPEN_STATE =:openState AND ID IN (SELECT c.OPEN_SER_ID FROM APP_OPEN_OBJECT c WHERE c.OPEN_HOSP_ID =:hospId )))";
                }
            }
            sql += " GROUP BY b.SERO_FWTYPE,b.SERO_LABEL_TYPE ";
            List<AppServeObject> ls = sysDao.getServiceDo().findSqlMap(sql,map,AppServeObject.class);
            return ls;
        }
        return null;
    }
}
