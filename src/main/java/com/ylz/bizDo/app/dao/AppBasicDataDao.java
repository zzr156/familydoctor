package com.ylz.bizDo.app.dao;

import com.ylz.bizDo.app.po.AppBasicData;

/**
 * Created by zzl on 2018/4/13.
 */
public interface AppBasicDataDao {
    /**
     * 保存调取基卫相关数据
     * @param userId 请求人主键
     * @param userName 请求人姓名
     * @param requestData 请求参数
     * @param patientId 居民主键
     * @param data 请求返回数据
     * @param methodsType 方法类型
     */
    public String saveBasicData(String userId,String userName,String requestData,String patientId,String data,String methodsType) throws Exception;

    /**
     * 查询数据
     * @param patientId 居民主键
     * @param methodsType 方法类型
     * @return
     */
    public AppBasicData findByPatientIdAndMethodsType(String patientId,String methodsType) throws Exception;

    public AppBasicData findById(String id) throws Exception;



}
