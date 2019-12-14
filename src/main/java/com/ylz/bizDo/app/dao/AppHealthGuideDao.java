package com.ylz.bizDo.app.dao;

import com.ylz.bizDo.jtapp.commonEntity.AppHealthGuideEntity;
import com.ylz.bizDo.jtapp.commonVo.AppHealthGuideQvo;

import java.util.List;

/**
 * Created by zzl on 2017/6/20.
 */
public interface AppHealthGuideDao  {
    //根据病人id查询指导数据
    public List<AppHealthGuideEntity> findByPId(AppHealthGuideQvo qvo) throws Exception;


}
