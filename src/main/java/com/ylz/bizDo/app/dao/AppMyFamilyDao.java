package com.ylz.bizDo.app.dao;

import com.ylz.bizDo.app.po.AppMyFamily;
import com.ylz.bizDo.app.po.AppPatientUser;
import com.ylz.bizDo.jtapp.patientEntity.AppMyFamilyAgeEntity;
import com.ylz.bizDo.jtapp.patientEntity.AppMyFamilyEntity;
import com.ylz.bizDo.jtapp.patientVo.AppMyFamilyRegisterVo;

import java.util.List;

/**
 * 我的家庭
 */
public interface AppMyFamilyDao {
    public List<AppMyFamilyEntity> findByOnly(String id) throws Exception;
    public List<AppMyFamilyAgeEntity> findByOnlyAge(String id) throws Exception;

    public AppMyFamily findByBdPatientIdNo(String userIdNo,String userId) throws Exception;

    public String addRegisterMyFamily(AppPatientUser vo, AppMyFamilyRegisterVo qvo) throws Exception;

    public AppMyFamily findByFamilyUserId(String id, String userId) throws Exception;

    public List<AppMyFamily> findBdMyFamily(String userId,String state) throws Exception;
}
