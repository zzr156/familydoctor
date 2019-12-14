package com.ylz.bizDo.app.dao;

import com.ylz.bizDo.app.po.AppPatientUser;
import com.ylz.bizDo.jtapp.commonEntity.AppDrEvaluationCountEntity;
import com.ylz.bizDo.jtapp.commonEntity.AppDrEvaluationDrEntity;
import com.ylz.bizDo.jtapp.commonEntity.AppDrEvaluationEntity;
import com.ylz.bizDo.jtapp.commonEntity.AppDrEvaluationViewEntity;
import com.ylz.bizDo.jtapp.commonVo.AppDrEvaluationQvo;

import java.util.List;

/**
 * Created by asus on 2017/07/23.
 */
public interface AppDrEvaluationDao {
    public List<AppDrEvaluationEntity> findEvaluationPatient(String id)throws Exception;

    public List<AppDrEvaluationViewEntity> findEvaluationPatientView(AppDrEvaluationQvo qvo)throws Exception;

    public List<AppDrEvaluationViewEntity> findEvaluationPatientView(String drId)throws Exception;

    public AppDrEvaluationDrEntity findEvaluationDr(String drId)throws Exception;

    public List<AppDrEvaluationCountEntity> findEvaluationCount(AppDrEvaluationQvo qvo)throws Exception;

    public String appAddEvaluation(AppDrEvaluationQvo vo, AppPatientUser user) throws Exception;
}
