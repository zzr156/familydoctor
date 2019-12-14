package com.ylz.bizDo.app.dao;

import com.ylz.bizDo.app.po.AppTcmResult;
import com.ylz.bizDo.jtapp.basicHealthVo.AppBasicTcmAllQvo;
import com.ylz.bizDo.jtapp.commonEntity.AppTcmEntity;
import com.ylz.bizDo.jtapp.commonEntity.AppTcmListEntity;
import com.ylz.bizDo.jtapp.commonEntity.AppTcmPeopleEntity;
import com.ylz.bizDo.jtapp.commonEntity.AppTcmRecordEntity;
import com.ylz.bizDo.jtapp.commonVo.AppTcmGuideListQvo;
import com.ylz.bizDo.jtapp.commonVo.AppTcmGuideQvo;
import com.ylz.bizDo.jtapp.commonVo.AppTcmQvo;

import java.util.List;

/**
 * Created by zzl on 2017/8/4.
 */
public interface AppTcmSyndromeDao  {
    /**
     * 保存体质辨识
     * @param qvo
     * @return
     * @throws Exception
     */
    public List<AppTcmEntity> saveTcm(AppTcmQvo qvo) throws Exception;

    /**
     * 查询中医体质辨识记录列表
     * @param qvo
     * @return
     * @throws Exception
     */
    public AppTcmListEntity findList(AppTcmQvo qvo) throws Exception;

    /**
     * 发送中医药保健指导
     * @param qvo
     * @return
     * @throws Exception
     */
    public void fsGuide(AppTcmGuideQvo qvo) throws  Exception;

    /**
     * 查询中医体质辨识居民列表
     * @param qvo
     * @return
     * @throws Exception
     */
    public List<AppTcmPeopleEntity> findPeople(AppTcmQvo qvo) throws Exception;

    /**
     * 保存中医药体质辨识指导模板
     * @param qvo
     * @return
     * @throws Exception
     */
    public String saveTcmGuide(AppTcmGuideListQvo qvo) throws Exception;

    /**
     * 查询个人端中医药体质辨识记录
     * @param qvo
     * @return
     * @throws Exception
     */
    public AppTcmListEntity  findByPersonal(AppTcmQvo qvo) throws Exception;

    /**
     * 查询中医体质未上传到基卫记录（医生做的）
     * @return
     */
    public List<AppTcmRecordEntity> findListByState() throws Exception;

    /**
     * 根据记录id和类型查询
     * @param jlId
     * @param type
     * @return
     */
    public AppTcmResult findResult(String jlId,String type) throws Exception;

    /**
     * 保存基卫中医体质辨识
     * @param qvo
     * @return
     */
    public String savaBasicTcm(AppBasicTcmAllQvo qvo) throws Exception;
}
