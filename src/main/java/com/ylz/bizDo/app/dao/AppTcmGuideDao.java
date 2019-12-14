package com.ylz.bizDo.app.dao;

import com.ylz.bizDo.jtapp.commonEntity.AppNewUserTcmGuideEntity;
import com.ylz.bizDo.jtapp.commonVo.*;

import java.util.List;

/**
 * Created by zzl on 2017/8/29.
 */
public interface AppTcmGuideDao {
    /**
     * 根据条件查询中医药保健指导
     * @param qvo
     * @return
     * @throws Exception
     */
    public List<AppNewTcmGuideQvo> findGuideMod(AppNewTcmQvo qvo) throws Exception;

    /**
     * 保存或修改中医药保健指导
     * @param qvo
     * @return
     * @throws Exception
     */
    public List<AppNewTcmGuideQvo> saveTcmGuide(AppTcmListGuideQvo qvo) throws Exception;

    /**
     * 发送中医药保健指导
     * @param qvo
     * @return
     * @throws Exception
     */
    public String  fsGuide(AppNewTcmVo qvo) throws Exception;

    /**
     * 查询中医药保健指导记录
     * @param qvo
     * @return
     * @throws Exception
     */
    public List<AppNewUserTcmGuideEntity>  findGuideList(AppUserTcmGuideQvo qvo) throws Exception;
}
