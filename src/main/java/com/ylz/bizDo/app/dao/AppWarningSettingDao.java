package com.ylz.bizDo.app.dao;

import com.ylz.bizDo.app.po.AppWarningSetting;

import java.util.List;

/**
 * Created by zzl on 2017/6/14.
 */
public interface AppWarningSettingDao {

    public List<AppWarningSetting> findSetting(String userId,String type) throws Exception;
}
