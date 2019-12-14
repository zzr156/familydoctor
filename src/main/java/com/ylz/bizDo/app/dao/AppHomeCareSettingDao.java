package com.ylz.bizDo.app.dao;

import com.ylz.bizDo.jtapp.drEntity.AppManageCardEntity;
import com.ylz.bizDo.jtapp.drVo.AppManageCardQvo;

import java.util.List;

/**
 * Created by asus on 2017/7/6.
 */
public interface AppHomeCareSettingDao {
    public List<AppManageCardEntity> findByTeamId(AppManageCardQvo qvo)  throws Exception;
}
