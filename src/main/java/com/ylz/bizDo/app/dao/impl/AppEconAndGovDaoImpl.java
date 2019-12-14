package com.ylz.bizDo.app.dao.impl;

import com.ylz.bizDo.app.dao.AppEconAndGovDao;
import com.ylz.bizDo.app.po.AppEconAndGov;
import com.ylz.bizDo.app.po.AppServeSetmeal;
import com.ylz.bizDo.app.vo.AppEconAndGovQvo;
import com.ylz.packaccede.allDo.SysDao;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zzl on 2017/8/21.
 */
@Service("appEconAndGovDao")
@Transactional(rollbackForClassName={"Exception"})
public class AppEconAndGovDaoImpl implements AppEconAndGovDao {
    @Autowired
    private SysDao sysDao;

    @Override
    public List<AppEconAndGov> findList(AppEconAndGovQvo qvo) throws Exception{
        Map<String,Object> map = new HashMap<String,Object>();
        String sql = " SELECT * FROM APP_ECON_AND_GOV WHERE 1=1";
        if(StringUtils.isNotBlank(qvo.getEconTitle())){
            map.put("econTitle","%"+qvo.getEconTitle()+"%");
            sql += " AND EAG_ECON_TITLE LIKE :econTitle";
        }
        if(StringUtils.isNotBlank(qvo.getGovTitle())){
            map.put("govTitle","%"+qvo.getGovTitle()+"%");
            sql += " AND EAG_GOV_TITLE LIKE :govTitle";
        }
        if("2".equals(qvo.getRoleType())){
            map.put("areaCode",qvo.getAreaCode());
            map.put("hospId",qvo.getHospId());
            map.put("level","0");
            map.put("openState","1");
            //对于市级医院来说查询自己+系统开放的
            if(StringUtils.isBlank(qvo.getAreaCode())){//市权限
                sql += " AND ( EAG_DEPT_ID =:hospId OR ( EAG_OPEN_STATE =:openState AND ID IN (SELECT a.OPEN_SER_ID FROM APP_OPEN_OBJECT a WHERE a.OPEN_HOSP_ID =:hospId )))";
            }else{//医院权限查询自己的+市的+系统开放的
                sql += " AND ( EAG_DEPT_ID =:hospId OR EAG_AREA_CODE =:areaCode OR (EAG_OPEN_STATE =:openState AND ID IN (SELECT a.OPEN_SER_ID FROM APP_OPEN_OBJECT a WHERE a.OPEN_HOSP_ID =:hospId )))";
            }
        }

        List<AppEconAndGov> ls = sysDao.getServiceDo().findSqlMap(sql,map,AppEconAndGov.class,qvo);
        return ls;
    }

    @Override
    public List<AppEconAndGov> findByStr(String roleType, String areaCode, String hospId) throws Exception{
        Map<String,Object> map = new HashMap<String,Object>();
        String sql = " SELECT * FROM APP_ECON_AND_GOV WHERE 1=1";
        if("2".equals(roleType)){
            map.put("areaCode",areaCode);
            map.put("hospId",hospId);
            map.put("level","0");
            map.put("openState","1");
            //对于市级医院来说查询自己+系统开放的
            if(StringUtils.isBlank(areaCode)){//市权限
                sql += " AND ( EAG_DEPT_ID =:hospId OR ( EAG_OPEN_STATE =:openState AND ID IN (SELECT a.OPEN_SER_ID FROM APP_OPEN_OBJECT a WHERE a.OPEN_HOSP_ID =:hospId )))";
            }else{//医院权限查询自己的+市的+系统开放的
                sql += " AND ( EAG_DEPT_ID =:hospId OR EAG_AREA_CODE =:areaCode OR (EAG_OPEN_STATE =:openState AND ID IN (SELECT a.OPEN_SER_ID FROM APP_OPEN_OBJECT a WHERE a.OPEN_HOSP_ID =:hospId )))";
            }
        }
        List<AppEconAndGov> ls = sysDao.getServiceDo().findSqlMap(sql,map,AppEconAndGov.class);
        return ls;
    }

    @Override
    public Boolean isReferencedByMeal(AppEconAndGov pk) throws Exception{
        String sql="select t1.id " +
                "from APP_SERVE_SETMEAL t1 " +
                "where t1.sersm_jj_id like '%"+pk.getId()+"%'";
        Map<String,Object> map = new HashMap<>();
        List<AppServeSetmeal> ls=sysDao.getServiceDo().findSqlMapRVo(sql,map,AppServeSetmeal.class);
        if(ls.size()>0){
            return true;
        }
        return false;
    }
}
