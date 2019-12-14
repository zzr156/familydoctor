package com.ylz.bizDo.app.dao;

import com.ylz.bizDo.app.po.AppReferralTable;
import com.ylz.bizDo.jtapp.commonVo.AppCommQvo;
import com.ylz.bizDo.jtapp.drEntity.AppDrReferralEntity;
import com.ylz.bizDo.jtapp.drEntity.AppReferralPatientEntity;
import com.ylz.bizDo.jtapp.drEntity.AppUpHospEntity;
import com.ylz.bizDo.jtapp.drEntity.ReferralInfo;
import com.ylz.bizDo.jtapp.drVo.AppDrReferralQvo;
import com.ylz.bizDo.jtapp.drVo.AppUpHospQvo;
import com.ylz.bizDo.mangecount.vo.ResidentVo;

import java.util.List;

/**
 * Created by zzl on 2017/12/11.
 */
public interface AppReferralTableDao {
    /**
     * 提交转诊信息
     * @param qvo
     * @return
     * @throws Exception
     */
    public AppReferralTable subReferral(AppDrReferralQvo qvo) throws Exception;

    /**
     * 康复回转
     * @param qvo
     * @return
     * @throws Exception
     */
    public AppReferralTable rehabilitation(AppDrReferralQvo qvo) throws Exception;

    /**
     * 查看转诊信息
     * @param qvo
     * @return
     * @throws Exception
     */
    public AppDrReferralEntity findOneByReferral(AppDrReferralQvo qvo) throws Exception;

    /**
     * 查询转诊记录列表
     * @param qvo
     * @return
     * @throws Exception
     */
    public List<AppDrReferralEntity> findReferralList(AppDrReferralQvo qvo) throws Exception;

    /**
     * 同意、拒绝、终止、回转
     * @param qvo
     * @return
     * @throws Exception
     */
    public AppReferralTable makeReferral(AppDrReferralQvo qvo) throws Exception;

    /**
     * 查询审核转诊记录
     * @param qvo
     * @return
     * @throws Exception
     */
    public List<AppDrReferralEntity> findShReferral(AppDrReferralQvo qvo) throws Exception;

    /**
     * 查询人员列表
     * @param qvo
     * @return
     * @throws Exception
     */
    public List<AppReferralPatientEntity> findPeople(AppDrReferralQvo qvo) throws Exception;

    /**
     * 查询上级医院列表
     * @param qvo
     * @return
     * @throws Exception
     */
    public List<AppUpHospEntity> findHosp(AppUpHospQvo qvo) throws Exception;

    /**
     * 查询转诊居民列表
     * @param qvo
     * @return
     * @throws Exception
     */
    public List<AppDrReferralEntity> findReferralPatient(AppCommQvo qvo) throws Exception;

    /**
     * 保存基卫转诊信息
     * @param qvo
     * @return
     * @throws Exception
     */
    public String addReferral(AppDrReferralQvo qvo) throws Exception;

    /**
     * 根据机构id查询转诊居民列表
     * @param qvo
     * @return
     * @throws Exception
     */
    public List<ReferralInfo> findByHospId(ResidentVo qvo) throws Exception;

    /**
     * 根据上级机构id查询转诊居民列表
     * @param qvo
     * @return
     * @throws Exception
     */
    public List<ReferralInfo> findUpHospt(AppCommQvo qvo) throws Exception ;
}
