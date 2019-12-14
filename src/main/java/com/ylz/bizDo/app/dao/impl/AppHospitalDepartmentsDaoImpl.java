package com.ylz.bizDo.app.dao.impl;

import com.ylz.bizDo.app.dao.AppHospitalDepartmentsDao;
import com.ylz.bizDo.jtapp.commonEntity.AppFindDeptEntity;
import com.ylz.bizDo.jtapp.commonVo.AppFindDeptQvo;
import com.ylz.packaccede.allDo.SysDao;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zzl on 2017/12/13.
 */
@Service("appHospitalDepartmentsDao")
@Transactional(rollbackForClassName={"Exception"})
public class AppHospitalDepartmentsDaoImpl implements AppHospitalDepartmentsDao {
    @Autowired
    private SysDao sysDao;

    /**
     * 查询科室
     * @param qvo
     * @return
     * @throws Exception
     */
    @Override
    public List<AppFindDeptEntity> findDeptList(AppFindDeptQvo qvo) throws Exception {
        Map<String,Object> map = new HashMap<>();
        String sql = " SELECT ID id," +
                "HD_HOSP_ID hospId," +
                "HD_SECTION_NUMBER deptCode," +
                "HD_SECTION_NAME deptName," +
                "(SELECT a.HOSP_NAME FROM APP_HOSP_DEPT a WHERE a.ID = HD_HOSP_ID) hospName " +
                "FROM APP_HOSPITAL_DEPARTMENTS WHERE 1=1 ";
        if(StringUtils.isNotBlank(qvo.getDeptName())){
            map.put("HD_SECTION_NAME",qvo.getDeptName()+"%");
            sql +=" AND HD_SECTION_NAME LIKE :HD_SECTION_NAME";
        }
        map.put("HD_HOSP_ID",qvo.getHospId());
        sql +=" AND HD_HOSP_ID=:HD_HOSP_ID";
        List<AppFindDeptEntity> list = sysDao.getServiceDo().findSqlMapRVo(sql,map,AppFindDeptEntity.class);
        return list;
    }

    @Override
    public List<AppFindDeptEntity> findDeptList(String hospId) throws Exception {
        Map<String,Object> map = new HashMap<>();
        String sql = " SELECT ID id," +
                "HD_HOSP_ID hospId," +
                "HD_SECTION_NUMBER deptCode," +
                "HD_SECTION_NAME deptName," +
                "(SELECT a.HOSP_NAME FROM APP_HOSP_DEPT a WHERE a.ID = HD_HOSP_ID) hospName " +
                "FROM APP_HOSPITAL_DEPARTMENTS WHERE 1=1 ";
        map.put("HD_HOSP_ID",hospId);
        sql +=" AND HD_HOSP_ID=:HD_HOSP_ID";
        List<AppFindDeptEntity> list = sysDao.getServiceDo().findSqlMapRVo(sql,map,AppFindDeptEntity.class);
        return list;
    }
}
