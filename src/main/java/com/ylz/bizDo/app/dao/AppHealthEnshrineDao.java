package com.ylz.bizDo.app.dao;

import com.ylz.bizDo.app.po.AppHealthEnshrine;
import com.ylz.bizDo.jtapp.commonEntity.AppEnshrineHealthEntity;
import com.ylz.bizDo.jtapp.commonEntity.AppHealthEntiry;
import com.ylz.bizDo.jtapp.commonVo.AppEnshrineHealthQvo;
import com.ylz.bizDo.jtapp.commonVo.AppHealthQvo;
import com.ylz.bizDo.jtapp.drVo.AppDrQvo;
import com.ylz.bizDo.jtapp.patientVo.AppPatientHealthQvo;

import java.util.List;

/**
 * Created by zzl on 2017/6/22.
 */
public interface AppHealthEnshrineDao {
    //收藏功能
    public String saveEnshrine(AppEnshrineHealthQvo qvo) throws Exception;

    //医生收藏健康教育模板
    public void enshrineHD(AppEnshrineHealthQvo qvo) throws Exception;
    //查询收藏的健康教育
    public List<AppHealthEntiry> findList(AppDrQvo qvo) throws Exception;
    //个人收藏健康教育
    public void enshrineHDP(AppPatientHealthQvo qvo) throws Exception;

    /**
     * 收藏
     * @param qvo
     * @return
     * @throws Exception
     */
    public AppHealthEnshrine saveHealth(AppHealthQvo qvo) throws Exception;

    /**
     * 查询收藏列表
     * @param qvo
     * @return
     * @throws Exception
     */
    public List<AppEnshrineHealthEntity> findEnshrineList(AppHealthQvo qvo) throws Exception;

    /**
     * 查看健康教育详细内容
     * @param id
     * @return
     * @throws Exception
     */
    public AppEnshrineHealthEntity findEnshrine(String id) throws Exception;

    /**
     * 查询是否已收藏教育
     * @param id
     * @param userId
     * @return
     * @throws Exception
     */
    public AppHealthEnshrine find(String id,String userId) throws Exception;

    public AppHealthEnshrine findByQvo(AppHealthQvo qvo) throws Exception;

}
