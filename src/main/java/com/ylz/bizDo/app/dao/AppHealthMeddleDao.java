package com.ylz.bizDo.app.dao;

import com.ylz.bizDo.jtapp.commonEntity.AppNewTcmLookEntity;
import com.ylz.bizDo.jtapp.commonEntity.AppTcmLookEntity;
import com.ylz.bizDo.jtapp.commonVo.AppTcmGuideQvo;
import com.ylz.bizDo.jtapp.drEntity.AppDrMeddleEntity;
import com.ylz.bizDo.jtapp.drVo.AppDrQvo;
import com.ylz.bizDo.jtapp.drVo.AppHealthMeddleQvo;
import com.ylz.packcommon.common.exception.DaoException;

import java.util.List;

/**
 * Created by zzl on 2017/6/29.
 */
public interface AppHealthMeddleDao {

    public void saveToPatient(AppHealthMeddleQvo qvo) throws Exception;

    //查看医生发的指导内容
    public AppDrMeddleEntity findById(String id) throws Exception;
    //查看模板指导内容
    public AppDrMeddleEntity findByIdModdl(String id) throws Exception;
    //查询指导模板列表
    public List<AppDrMeddleEntity> findByQvo(AppDrQvo qvo) throws Exception;

    /**
     * 查询中医药体质辨识指导记录
     * @param qvo
     * @return
     * @throws Exception
     */
    public List<AppTcmLookEntity> findByTcm(AppTcmGuideQvo qvo) throws Exception;
    public List<AppNewTcmLookEntity> findByTcmGuide(AppTcmGuideQvo qvo) throws Exception;

}
