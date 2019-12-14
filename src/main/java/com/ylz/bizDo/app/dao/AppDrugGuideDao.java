package com.ylz.bizDo.app.dao;

import com.ylz.bizDo.app.po.AppDrUser;
import com.ylz.bizDo.app.po.AppDrugGuide;
import com.ylz.bizDo.app.po.AppDrugWarning;
import com.ylz.bizDo.jtapp.commonVo.AppDrugVo;
import com.ylz.bizDo.jtapp.drEntity.AppDrDrugGuideEntity;
import com.ylz.bizDo.jtapp.patientEntity.AppDrugGuideBatchEntity;
import com.ylz.bizDo.jtapp.patientEntity.AppDrugGuideEntity;
import com.ylz.bizDo.jtapp.patientEntity.AppDrugReminderEntity;

import java.util.List;

/**
 *
 */
public interface AppDrugGuideDao {

    public List<AppDrugGuide> findGuidByTime() throws Exception;
    //通过患者id查找用药指导
    public List<AppDrugGuideEntity> findByPid(AppDrugVo vo) throws Exception;
    //查找用户的用药提醒设置
    public List<AppDrugWarning> findDrugWarn(String drId, String drugId) throws Exception;
    //查找用户通用用药提醒设置
    public List<AppDrugWarning> findDrugCommonWarn(String drId) throws Exception;


    public AppDrugWarning setDrugWarn(String drId,String drugId,String warningNum) throws Exception;

    public AppDrugWarning setCommonDrugWarn(String drId,String warnNum) throws Exception;


    public void addGuide(AppDrUser user,AppDrugVo vo) throws Exception;

    /**
     * 通过用户id查询用药提醒的药品
     * @param vo patientId
     * @return
     */
    public List<AppDrugReminderEntity> findByDrugUnique(AppDrugVo vo) throws Exception;

    /**
     * 通过用户id查询
     * @param vo
     * @return
     */
    public List<AppDrugGuideBatchEntity> findByBatch(AppDrugVo vo) throws Exception;

    public List<AppDrDrugGuideEntity>  findByDrOrPatient(String drId, String patiendId) throws Exception;

    /**
     * 药品存量预警
     * @param usreId
     * @return
     */
    public List<AppDrugGuide> findGuidByTime(String usreId) throws Exception;
}
