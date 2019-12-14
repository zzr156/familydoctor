package com.ylz.bizDo.app.dao;

import com.ylz.bizDo.jtapp.commonEntity.AppTcmGuideEntity;
import com.ylz.bizDo.jtapp.commonVo.AppTcmGuideQvo;
import com.ylz.bizDo.jtapp.drEntity.AppDrMeddleEntity;
import com.ylz.bizDo.jtapp.drEntity.AppGuideModelEntity;
import com.ylz.bizDo.jtapp.drVo.AppGuideTemplateQvo;
import com.ylz.packcommon.common.exception.DaoException;

import java.util.List;

/**
 * Created by zzl on 2017/6/20.
 */
public interface AppGuideTemplateDao {
    /**
     * 查找健康模板
     * @param qvo
     * @return
     * @throws DaoException
     */
    public List<AppGuideModelEntity> findByQvo(AppGuideTemplateQvo qvo) throws Exception;

    /**
     * 公共添加健康指导模板
     * @param qvo
     * @throws Exception
     */
    public void addHealthGuide(AppGuideTemplateQvo qvo) throws Exception;

    /**
     *修改健康指导模板
     * @param qvo
     * @throws Exception
     */
    public AppDrMeddleEntity modifyGuide(AppGuideTemplateQvo qvo) throws Exception;

    /**
     * 添加中医药体质辨识指导
     * @param qvo
     * @return
     * @throws Exception
     */
    public AppTcmGuideEntity addTcmGuide(AppTcmGuideQvo qvo) throws Exception;

    /**
     * 查询中医药体质辨识保健指导列表
     * @param qvo
     * @return
     * @throws Exception
     */
    public List<AppTcmGuideEntity>  findZyyGuide(AppTcmGuideQvo qvo) throws Exception;

    /**
     * 修改中医药保健指导模板
     * @param qvo
     * @return
     * @throws Exception
     */
    public AppTcmGuideEntity modifyTcmGuide(AppTcmGuideQvo qvo) throws Exception;

    public List<AppTcmGuideEntity> addTcmGuide1(AppTcmGuideQvo qvo) throws Exception;
}
