package com.ylz.bizDo.app.dao;

import com.ylz.bizDo.app.entity.AppArchivingCardCPEntiy;
import com.ylz.bizDo.app.entity.AppArchivingCardPeopeEntity;
import com.ylz.bizDo.app.entity.AppArchivingCheckEntity;
import com.ylz.bizDo.app.vo.AppArchivingCardPeopleQvo;

import java.util.List;
import java.util.Map;

/**
 * Created by zzl on 2018/11/8.
 */
public interface AppArchivingCheckDao {
    /**
     * 根据建档立卡花名册主键查询核查表
     * @param archivingId
     * @return
     * @throws Exception
     */
    public AppArchivingCheckEntity findByArchivingId(String archivingId,String signId) throws Exception;

    public  List<Map> findByQvo(AppArchivingCardPeopleQvo qvo) throws Exception;

    public List<AppArchivingCardCPEntiy> findPeopleList(AppArchivingCardPeopleQvo qvo) throws Exception;

}
