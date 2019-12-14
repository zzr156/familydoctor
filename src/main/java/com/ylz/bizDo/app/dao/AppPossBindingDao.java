package com.ylz.bizDo.app.dao;

import com.ylz.bizDo.app.entity.AppSignServiceRecordEntity;
import com.ylz.bizDo.app.po.AppPossBinding;
import com.ylz.bizDo.app.po.AppSignServiceRecord;
import com.ylz.bizDo.jtapp.commonEntity.AppPossEntity;
import com.ylz.bizDo.jtapp.commonEntity.AppPossPatientEntity;
import com.ylz.bizDo.jtapp.commonVo.AppPossPatientVo;
import com.ylz.bizDo.jtapp.commonVo.AppPossQvo;

import java.util.List;

/**
 * Created by zzl on 2018/8/20.
 */
public interface AppPossBindingDao {
    /**
     * 根据poss机SN码查询绑定信息(返回托管对象)
     * @param qvo
     * @return
     * @throws Exception
     */
    public AppPossBinding findByPossSn(AppPossQvo qvo) throws Exception;

    /**
     * 根据血糖仪sn码验证该血糖仪是否已经绑定
     * @param gluSn
     * @return
     * @throws Exception
     */
    public boolean findByGluSn(String gluSn) throws Exception;

    /**
     * 根据血压计sn码验证该血糖仪是否已经绑定
     * @param sphSn
     * @return
     * @throws Exception
     */
    public boolean findBySphSn(String sphSn) throws Exception;

    /**
     * 根据条件查询漳浦手持机服务记录人员基础信息
     * @param qvo
     * @return
     * @throws Exception
     */
    public AppPossPatientEntity findInformationByPatient(AppPossQvo qvo) throws Exception;

    /**
     * 保存签约服务数据
     * @param qvo
     * @return
     * @throws Exception
     */
    public AppSignServiceRecord saveInformation(AppPossPatientVo qvo) throws Exception;

    /**
     * 根据条件查询签约服务记录列表
     * @param qvo
     * @return
     * @throws Exception
     */
    public List<AppPossEntity> findSignServeRecordList(AppPossQvo qvo) throws Exception;

    /**
     * 根据主键id查询数据记录
     * @param id
     * @return
     * @throws Exception
     */
    public AppSignServiceRecordEntity findSignServeRecord(String id) throws Exception;

}
