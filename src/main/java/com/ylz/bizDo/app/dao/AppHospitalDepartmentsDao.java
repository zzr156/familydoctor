package com.ylz.bizDo.app.dao;

import com.ylz.bizDo.jtapp.commonEntity.AppFindDeptEntity;
import com.ylz.bizDo.jtapp.commonVo.AppFindDeptQvo;

import java.util.List;

/**
 * Created by zzl on 2017/12/13.
 */
public interface AppHospitalDepartmentsDao {
    /**
     * 查询科室
     * @param qvo
     * @return
     * @throws Exception
     */
    public List<AppFindDeptEntity> findDeptList(AppFindDeptQvo qvo) throws Exception;
    public List<AppFindDeptEntity> findDeptList(String hospId) throws Exception;
}
