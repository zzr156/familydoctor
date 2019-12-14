package com.ylz.bizDo.plan.bizDo;

import com.ylz.bizDo.jtapp.patientEntity.AppPatientEntity;
import com.ylz.bizDo.jtapp.patientVo.AppPatientQvo;
import com.ylz.bizDo.plan.Entity.AppFollowPlanEntity;
import com.ylz.bizDo.plan.Entity.AppFollowPlanTjEntjty;
import com.ylz.bizDo.plan.Entity.AppFollowPlanTxEntity;
import com.ylz.bizDo.plan.po.AppDiabetesTable;
import com.ylz.bizDo.plan.po.AppFollowPlan;
import com.ylz.bizDo.plan.po.AppHdBlooPressureTable;
import com.ylz.bizDo.plan.vo.AppBasicDiseaseQvo;
import com.ylz.bizDo.plan.vo.AppBasicHdBlooQvo;
import com.ylz.bizDo.plan.vo.AppFollowPlanQvo;
import com.ylz.packcommon.common.exception.DaoException;

import java.util.List;

/**
 * Created by Administrator on 2017-05-12.
 */
public interface AppFollowPlanDao {
    public List<AppFollowPlanTjEntjty> findTjPlan(AppFollowPlanQvo qvo) throws Exception;

    public List<AppFollowPlan> findPlan(AppFollowPlanQvo qvo) throws Exception;

    public AppFollowPlan findByType(String type, String patientId) throws Exception;

    List<AppFollowPlan> findByTxState(String state) throws Exception;

    public AppFollowPlan findById(String id) throws Exception;

    public boolean findByIdAndTime(String patientId, String time, String type) throws Exception;

    //根据患者身份证查所有面访记录
    public  List<AppFollowPlan> findByIdNo(String userIdNo) throws Exception;

    //根据医生id查所有已完成随访，为上传基卫的数据信息
    public  List<AppFollowPlan> findByDoctorId(String doctorId) throws Exception;

    //根据患者身份证查询未完成随访记录
    public AppFollowPlan findByIdCard(String type, String patientId) throws Exception;
    //添加随访计划
    public boolean saveFollowPlan(AppFollowPlanEntity qvo) throws Exception;

    public List<AppPatientEntity> findList(AppPatientQvo qvo) throws Exception;


    public List<AppFollowPlan> findPlanYearList(AppFollowPlanQvo qvo) throws Exception;

    /**
     * 查询随访提醒数据
     * @return
     * @throws Exception
     */
    public List<AppFollowPlan> findAllPlan()  throws Exception;

    /**
     * 查询当天随访提醒
     * @return
     */
    public List<AppFollowPlanTxEntity> findAllDayPlan(String type) throws Exception;

    /**
     * 查询随访提醒数据
     * @return
     * @throws Exception
     */
    public List<AppFollowPlan> findAllPlan(String userId) throws Exception;
    /**
     * 查询当天随访提醒
     * @return
     */
    public List<AppFollowPlanTxEntity> findAllDayPlan(String type,String userId) throws Exception;

    /**
     * 保存基卫糖尿病随访数据
     * @param qvo
     * @return
     * @throws Exception
     */
    public String addBasicDiabetes(AppBasicDiseaseQvo qvo) throws Exception;

    /**
     * 根据随访编号查询糖尿病随访信息
     * @param code
     */
    public AppDiabetesTable findDisByCode(String code) throws Exception;

    public List<AppFollowPlan> findPlanById(String userId) throws Exception;

    /**
     * 保存基卫高血压数据
     * @param qvo
     * @return
     * @throws Exception
     */
    public String addBasicHdBloo(AppBasicHdBlooQvo qvo) throws Exception;
    /**
     * 根据随访编号查询糖尿病随访信息
     * @param code
     */
    public AppHdBlooPressureTable findHdByCode(String code) throws Exception;

}
