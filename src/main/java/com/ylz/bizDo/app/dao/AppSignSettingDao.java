package com.ylz.bizDo.app.dao;

import com.ylz.bizDo.app.po.AppSignSetting;
import com.ylz.bizDo.jtapp.signSersetEntity.AppSignSettingEntity;

/**
 * Created by zzl on 2017/7/26.
 */
public interface AppSignSettingDao {
    /**
     * 保存签约管理设置
     * @param qvo
     * @throws Exception
     */
    public AppSignSetting saveSet(AppSignSetting qvo) throws Exception;

    /**
     * 查询医院保存的签约管理设置信息
     * @param hospId
     * @return
     * @throws Exception
     */
    public AppSignSettingEntity findByMeal(String hospId) throws Exception;

    /**
     * 修改签约管理设置
     * @param qvo
     * @return
     * @throws Exception
     */
    public AppSignSetting modifySign(AppSignSetting qvo) throws Exception;

    /**
     * 新增签约管理设置
     * @param qvo
     * @return
     * @throws Exception
     */
    public AppSignSetting saveSign(AppSignSetting qvo) throws Exception;



    /**
     * 查询市级保存的签约管理设置信息
     * @param areaCode
     * @return
     * @throws Exception
     */
    public AppSignSetting findByAreaCode(String areaCode) throws Exception;

}
