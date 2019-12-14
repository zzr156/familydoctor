package com.ylz.bizDo.plan.bizDo;

import com.ylz.bizDo.plan.Entity.*;
import com.ylz.bizDo.plan.po.AppFollowPlan;
import com.ylz.bizDo.plan.po.AppMentalVisitTable;
import com.ylz.bizDo.plan.po.AppTBFollowVisitTable;
import com.ylz.bizDo.plan.vo.AppFollwCommQvo;
import com.ylz.bizDo.plan.vo.AppGeneralQvo;
import com.ylz.bizDo.plan.vo.AppSaveFollowQvo;

import java.util.List;

/** 保存随访记录
 * Created by zzl on 2017/7/24.
 */
public interface AppSaveFollowTableDao  {
    /**
     * 保存高血压随访记录
     * @param qvo
     * @return
     * @throws Exception
     */
    public AppSaveFollowQvo saveHdBloo(AppSaveFollowQvo qvo) throws Exception;


    /**
     * 个人基本信息 根据随访id 获取个人基本信息
     * @param qvo id 随访id
     * @return
     * @throws Exception
     */
    public AppFollowPatienBasic findFollBasic(AppFollwCommQvo qvo) throws Exception;

    /**
     * 根据随访id或患者id 查询随访记录
     * @param qvo id 随访id patientId 患者id
     * @return
     */
    public List<AppFollowPlan> findFollLosg(AppFollwCommQvo qvo) throws Exception;

    /**
     * 最后随访诊断单获取 根据患者id和服务人群类型 LableValue
     * @param qvo id patientId 患者id persGroup 服务人群类型
     * @return
     * @throws Exception
     */
    public Object findMaxPersGroup(AppFollwCommQvo qvo) throws Exception;


    /**
     * 保存糖尿病随访记录
     * @param qvo
     * @return
     * @throws Exception
     */
    public AppSaveFollowQvo saveDiabetes(AppSaveFollowQvo qvo) throws Exception;

    /**
     * 保存新生儿随访记录
     * @param qvo
     * @return
     * @throws Exception
     */
    public AppSaveFollowQvo saveNewChild(AppSaveFollowQvo qvo) throws Exception;

    /**
     * 根据id查询高血压数据
     * @param id
     * @return
     * @throws Exception
     */
    public AppHdBlooPressureTableEntity findHd(String id) throws Exception;

    /**
     * 查询糖尿病数据
     * @param id
     * @return
     * @throws Exception
     */
    public AppDiabetesTableEntity findDiabetes(String id) throws Exception;

    /**
     * 保存产后访视记录表
     * @param qvo
     * @return
     * @throws Exception
     */
    public AppSaveFollowQvo savePostPar(AppSaveFollowQvo qvo) throws Exception;

    /**
     * 查询新生儿家庭访视记录
     * @param id
     * @return
     * @throws Exception
     */
    public AppNewChildrenTableEntity findNewChild(String id) throws  Exception;

    /**
     * 查询产后访视记录
     * @param id
     * @return
     * @throws Exception
     */
    public AppPostpartumVisitEntity findPostPar(String id) throws Exception;

    /**
     * 保存通用随访信息
     * @param qvo
     * @return
     * @throws Exception
     */
    public AppGeneralQvo saveGeneral(AppGeneralQvo qvo) throws Exception;

    /**
     * 查询通用随访记录表
     * @param id
     * @return
     * @throws Exception
     */
    public AppGeneralEntity findTy(String id) throws Exception;

    /**
     * 保存精神病随访
     * @param qvo
     * @return
     * @throws Exception
     */
    public AppSaveFollowQvo saveMentalVisit(AppSaveFollowQvo qvo) throws Exception;

    /**
     * 查询严重精神病随访信息
     * @param id
     * @return
     * @throws Exception
     */
    public AppMentalVisitEntity findMentalVisit(String id) throws Exception;

    /**
     * 保存肺结核第一次入户随访信息
     * @param qvo
     * @return
     * @throws Exception
     */
    public AppSaveFollowQvo saveFTBVisit(AppSaveFollowQvo qvo) throws Exception;

    /**
     * 查询结核病第一次入户随访信息
     * @param id
     * @return
     * @throws Exception
     */
    public AppFirstTBFollowVisitEntity  findFtbVisit(String id) throws Exception;

    /**
     * 保存结核病随访信息
     * @param qvo
     * @return
     * @throws Exception
     */
    public AppSaveFollowQvo saveTBVisit(AppSaveFollowQvo qvo) throws Exception;

    /**
     * 查询结核病随访信息
     * @param id
     * @return
     * @throws Exception
     */
    public AppTBFollowVisitEntity findTbVisit(String id) throws Exception;

}
