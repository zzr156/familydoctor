package com.ylz.bizDo.app.dao;

import com.ylz.bizDo.jtapp.commonEntity.AppHealthEntiry;
import com.ylz.bizDo.jtapp.commonVo.AppHealthQvo;
import com.ylz.bizDo.jtapp.commonVo.AppHealthUserQvo;
import com.ylz.bizDo.jtapp.drEntity.AppDrHealthEntity;
import com.ylz.packcommon.common.exception.DaoException;

import java.util.List;

/**
 * Created by zzl on 2017/6/21.
 */
public interface AppUserHealthEDDao  {

    //保存患者健康教育
    public String save(AppHealthUserQvo qvo) throws Exception;

    //查询医生发给患者健康教育列表
    public List<AppDrHealthEntity> findList(String drId) throws Exception;
    //查询健康教育具体内容
    public AppHealthEntiry findByOne(AppHealthQvo qvo) throws Exception;

}
