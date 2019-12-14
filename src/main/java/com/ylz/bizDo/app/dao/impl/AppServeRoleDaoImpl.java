package com.ylz.bizDo.app.dao.impl;

import com.ylz.bizDo.app.dao.AppServeRoleDao;
import com.ylz.bizDo.app.entity.AppServeManageEntity;
import com.ylz.bizDo.app.po.AppServeRole;
import com.ylz.bizDo.app.vo.AppServeManageQvo;
import com.ylz.bizDo.app.vo.AppServeRoleQvo;
import com.ylz.packaccede.allDo.SysDao;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by asus on 2017/11/02.
 */
@Service("appServeRoleDao")
@Transactional(rollbackForClassName={"Exception"})
public class AppServeRoleDaoImpl implements AppServeRoleDao {

    @Autowired
    private SysDao sysDao;

    /**
     * 分页查询
     * @param qvo
     * @return
     */
    @Override
    public List<AppServeRole> findList(AppServeRoleQvo qvo) throws Exception{
        Map<String,Object> map = new HashMap<String,Object>();
        String sql = "SELECT * FROM APP_SERVE_ROLE  as a WHERE 1=1  ";
        if(StringUtils.isNotBlank(qvo.getServeType())){
            if(qvo.getServeType().equals("1")){//区域
                sql += " AND a.SERVE_ROLE_HOSP_ID IS NOT NULL ";
            }
            if(qvo.getServeType().equals("2")){//医院
                sql += " AND a.SERVE_ROLE_HOSP_ID IS NOT NULL ";
            }
        }
        sql += " ORDER BY a.SERVE_ROLE_AREA_CODE  ";
        return sysDao.getServiceDo().findSqlMap(sql, map, AppServeRole.class, qvo);
    }


    /**
     * 删除
     * @param id
     */
    @Override
    public void delForm(String id) throws Exception{
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("SON_SERVE_ROLE_ID",id);
        String sql = "DELETE FROM APP_SERVE_ROLE_SON WHERE SON_SERVE_ROLE_ID = :SON_SERVE_ROLE_ID";
        this.sysDao.getServiceDo().update(sql,map);
    }

    /**
     * 查询服务类型频次
     * @param qvo
     * @return
     */
    @Override
    public List<AppServeManageEntity> findManageEntity(AppServeManageQvo qvo) throws Exception{
        Map<String,Object> map = new HashMap<String,Object>();
        String sql ="SELECT\n" +
                "\tmax(s.SON_SERVE_SET_NUM) setNum,\n" +
                "\tmax(s.SON_SERVE_SET_SPACE) serSpace,\n" +
                "\tst.SER_TITLE serTitle,\n" +
                "\tst.SER_VALUE serValue\n" +
                "FROM\n" +
                "\tAPP_SERVE_ROLE c\n" +
                "INNER JOIN APP_SERVE_ROLE_SON s ON c.ID = s.SON_SERVE_ROLE_ID\n" +
                "INNER JOIN APP_SERVE_SETTING st ON st.ID = s.SON_SERVE_ID\n" +
                "WHERE \n" +
                "\t1=1 ";
        if(StringUtils.isNotBlank(qvo.getResult())){
            String[] resultString = qvo.getResult().split(";");
            map.put("SER_OBJECT_VALUE",resultString);
            sql += " AND st.SER_OBJECT_VALUE in :SER_OBJECT_VALUE ";
        }
        if(StringUtils.isNotBlank(qvo.getHospId())){
            map.put("SERVE_ROLE_HOSP_ID",qvo.getHospId());
            sql += " AND c.SERVE_ROLE_HOSP_ID = :SERVE_ROLE_HOSP_ID ";
        }
        sql += " GROUP BY st.SER_VALUE ORDER BY st.SER_OBJECT_VALUE,st.SER_VALUE";

        List<AppServeManageEntity> ls = sysDao.getServiceDo().findSqlMapRVo(sql,map,AppServeManageEntity.class);
        if(ls != null && ls.size() >0){
            return ls;
        }else{
            sql ="SELECT\n" +
                    "\tmax(s.SON_SERVE_SET_NUM) setNum,\n" +
                    "\tmax(s.SON_SERVE_SET_SPACE) serSpace,\n" +
                    "\tst.SER_TITLE serTitle,\n" +
                    "\tst.SER_VALUE serValue\n" +
                    "FROM\n" +
                    "\tAPP_SERVE_ROLE c\n" +
                    "INNER JOIN APP_SERVE_ROLE_SON s ON c.ID = s.SON_SERVE_ROLE_ID\n" +
                    "INNER JOIN APP_SERVE_SETTING st ON st.ID = s.SON_SERVE_ID\n" +
                    "WHERE \n" +
                    "\t1=1 ";
            if(StringUtils.isNotBlank(qvo.getResult())){
                String[] resultString = qvo.getResult().split(";");
                map.put("SER_OBJECT_VALUE",resultString);
                sql += " AND st.SER_OBJECT_VALUE in :SER_OBJECT_VALUE ";
            }
            if(StringUtils.isNotBlank(qvo.getAreaCode())){
                map.put("SERVE_ROLE_AREA_CODE",qvo.getAreaCode());
                sql += " AND c.SERVE_ROLE_AREA_CODE = :SERVE_ROLE_AREA_CODE ";
            }
            sql += " GROUP BY st.SER_VALUE ORDER BY st.SER_OBJECT_VALUE,st.SER_VALUE";
            ls = sysDao.getServiceDo().findSqlMapRVo(sql,map,AppServeManageEntity.class);
            if(ls != null && ls.size()>0){
                return  ls;
            }
            return  null;
        }
    }

    @Override
    public AppServeManageEntity findManageByOne(String type,String result, String hospId, String areaCode) throws Exception{
        Map<String,Object> map = new HashMap<String,Object>();
        String sql ="SELECT\n" +
                "\tmax(s.SON_SERVE_SET_NUM) setNum,\n" +
                "\tmax(s.SON_SERVE_SET_SPACE) serSpace,\n" +
                "\tst.SER_TITLE serTitle,\n" +
                "\tst.SER_VALUE serValue\n" +
                "FROM\n" +
                "\tAPP_SERVE_ROLE c\n" +
                "INNER JOIN APP_SERVE_ROLE_SON s ON c.ID = s.SON_SERVE_ROLE_ID\n" +
                "INNER JOIN APP_SERVE_SETTING st ON st.ID = s.SON_SERVE_ID\n" +
                "WHERE \n" +
                "\t1=1 ";
        if(StringUtils.isNotBlank(result)){
            String[] resultString = result.split(",");
            map.put("SER_OBJECT_VALUE",resultString);
            sql += " AND st.SER_OBJECT_VALUE in :SER_OBJECT_VALUE ";
        }
        if(StringUtils.isNotBlank(hospId)){
            map.put("SERVE_ROLE_HOSP_ID",hospId);
            sql += " AND c.SERVE_ROLE_HOSP_ID = :SERVE_ROLE_HOSP_ID ";
        }
        map.put("serValue",type);
        sql += " AND st.SER_VALUE=:serValue";
        sql += " GROUP BY st.SER_VALUE ORDER BY st.SER_OBJECT_VALUE,st.SER_VALUE";
        List<AppServeManageEntity> ls = sysDao.getServiceDo().findSqlMapRVo(sql,map,AppServeManageEntity.class);
        if(ls != null && ls.size() >0){
            return ls.get(0);
        }else{
            sql ="SELECT\n" +
                    "\tmax(s.SON_SERVE_SET_NUM) setNum,\n" +
                    "\tmax(s.SON_SERVE_SET_SPACE) serSpace,\n" +
                    "\tst.SER_TITLE serTitle,\n" +
                    "\tst.SER_VALUE serValue\n" +
                    "FROM\n" +
                    "\tAPP_SERVE_ROLE c\n" +
                    "INNER JOIN APP_SERVE_ROLE_SON s ON c.ID = s.SON_SERVE_ROLE_ID\n" +
                    "INNER JOIN APP_SERVE_SETTING st ON st.ID = s.SON_SERVE_ID\n" +
                    "WHERE \n" +
                    "\t1=1 ";
            if(StringUtils.isNotBlank(result)){
                String[] resultString = result.split(",");
                map.put("SER_OBJECT_VALUE",resultString);
                sql += " AND st.SER_OBJECT_VALUE in :SER_OBJECT_VALUE ";
            }
            if(StringUtils.isNotBlank(areaCode)){
                map.put("SERVE_ROLE_AREA_CODE",areaCode);
                sql += " AND c.SERVE_ROLE_AREA_CODE = :SERVE_ROLE_AREA_CODE ";
            }
            map.put("serValue",type);
            sql += " AND st.SER_VALUE=:serValue";
            sql += " GROUP BY st.SER_VALUE ORDER BY st.SER_OBJECT_VALUE,st.SER_VALUE";
            ls = sysDao.getServiceDo().findSqlMapRVo(sql,map,AppServeManageEntity.class);
            if(ls != null && ls.size()>0){
                return  ls.get(0);
            }
            return  null;
        }
    }
}
