package com.ylz.bizDo.app.dao;

import com.ylz.bizDo.jtapp.commonEntity.AppGluCountEntity;
import com.ylz.bizDo.jtapp.commonEntity.AppGluEntity;
import com.ylz.bizDo.jtapp.commonVo.AppUserBloodgluVo;
import com.ylz.bizDo.jtapp.commonVo.DevUserBloodgluVo;
import com.ylz.bizDo.smjq.smEntity.AppSmPeopleBasicEntity;
import com.ylz.bizDo.smjq.smVo.AppSmPeopleBasicVo;

import java.util.List;

/**
 * Created by zzl on 2017/6/14.
 */
public interface AppUserBloodgluDao  {
    public List<AppGluEntity> findById(AppUserBloodgluVo qvo) throws Exception;

    public String findNewGxt(String userId) throws Exception;

    public void appBloodglu(AppUserBloodgluVo vo) throws Exception;

    public void addlfBlu(DevUserBloodgluVo xt) throws Exception;

    //血糖统计
    public AppGluCountEntity findCount(AppUserBloodgluVo vo) throws Exception;

    public List<AppGluEntity> findLook(String userId, String bgstate, String period) throws Exception;

    public List<AppGluEntity> findAll(String userId) throws Exception;

    /**
     * 查询血糖最新数据
     * @param patientId
     * @return
     */
    public AppGluEntity findLatest(String patientId) throws Exception;
    /**
     * 查询血糖最新数据(随访使用)
     * @param patientId
     * @return
     */
    public AppGluEntity findLatestT(String patientId) throws Exception;

    /**
     * 三明查询居民血糖数据
     * @param qvo
     * @return
     * @throws Exception
     */
    public AppSmPeopleBasicEntity findPeopleBasic(AppSmPeopleBasicVo qvo) throws Exception;
}
