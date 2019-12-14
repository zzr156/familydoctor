package com.ylz.bizDo.app.dao;

import com.ylz.bizDo.app.po.AppConsult;
import com.ylz.bizDo.jtapp.commonEntity.AppCousultEntity;
import com.ylz.bizDo.jtapp.commonVo.AppConsultQvo;
import com.ylz.bizDo.jtapp.drEntity.AppDrConsultEntity;
import com.ylz.packcommon.common.exception.DaoException;

import java.util.List;

/**
 * Created by zzl on 2017/6/23.
 */
public interface AppConsultDao  {
    //保存咨询
    public void saveCon(AppConsultQvo qvo) throws Exception;
    //医生咨询我的
    public List<AppDrConsultEntity> findByDr(AppConsultQvo qvo) throws Exception;
    //我发起的咨询
    //查询咨询记录
    public List<AppCousultEntity> findList(AppConsultQvo qvo) throws Exception;
    //我回复的
    public List<AppCousultEntity> findReply(AppConsultQvo qvo) throws Exception;
    //查询咨询信息
    public List<AppCousultEntity> findDetailed(AppConsultQvo qvo) throws Exception;
    //查询已完成的咨询AppDrConsultEntity
    public List<AppDrConsultEntity> findComplete(AppConsultQvo qvo) throws Exception;
    //查询个人端咨询 待回复、进行中、已完成
    public List<AppCousultEntity> findConList(AppConsultQvo qvo) throws Exception;

    /**
     * 根据创建者是医生，接收者是患者对同一件事查询当天是否有咨询数据
     * @param drId 医生id
     * @param conId 居民id
     * @param pid 事件id
     * @return
     */
    public List<AppConsult> findToDayCon(String drId,String pid,String conId) throws Exception;




}
