package com.ylz.bizDo.app.dao;

import com.ylz.bizDo.app.vo.AppThreeBloodPressureDataVo;
import com.ylz.bizDo.jtapp.commonEntity.AppPressureCountEntity;
import com.ylz.bizDo.jtapp.commonEntity.AppPressureEntity;
import com.ylz.bizDo.jtapp.commonVo.AppUserBloodpressureVo;
import com.ylz.bizDo.smjq.smEntity.AppSmPeopleBasicEntity;
import com.ylz.bizDo.smjq.smVo.AppSmPeopleBasicVo;

import java.util.List;

/**
 * Created by zzl on 2017/6/14.
 */
public interface AppUserBloodpressureDao  {
    public List<AppPressureEntity> findById(AppUserBloodpressureVo qvo) throws Exception;

    public void appPressure(AppUserBloodpressureVo vo) throws Exception;

    public void addlfPressure(AppUserBloodpressureVo gxy) throws Exception;
    //统计血压数据
    public AppPressureCountEntity findCount(AppUserBloodpressureVo vo) throws Exception;

    public List<AppPressureEntity> findLook(String userId,String period) throws Exception;

    /**
     * 查询血压最新数据
     * @param patientId
     * @return
     */
    public AppPressureEntity findLatest(String patientId) throws Exception;

    public void addThreeBloodPressureData(AppThreeBloodPressureDataVo qvo) throws Exception;

    /**
     * 三明查询居民血压数据
     * @param qvo
     * @return
     * @throws Exception
     */
    public AppSmPeopleBasicEntity findPeopleBasic(AppSmPeopleBasicVo qvo) throws Exception;


}
