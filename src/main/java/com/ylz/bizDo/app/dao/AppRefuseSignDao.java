package com.ylz.bizDo.app.dao;

import com.ylz.bizDo.app.po.AppRefuseSign;

/**
 * 拒绝签约
 */
public interface AppRefuseSignDao {
    //通过身份证号查找是否有拒签患者
    public  AppRefuseSign findByIdno(String patientIdno) throws Exception;
}
