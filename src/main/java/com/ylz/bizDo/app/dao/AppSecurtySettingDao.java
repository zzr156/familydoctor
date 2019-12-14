package com.ylz.bizDo.app.dao;

import com.ylz.bizDo.app.po.AppSecurtySetting;
import com.ylz.bizDo.jtapp.patientVo.AppSecurtyQvo;

import java.util.List;

/**
 * 安全设置
 */
public interface AppSecurtySettingDao {

    /**
     * 查询安全设置数据
     * @param qvo
     * @return
     */
    public List<AppSecurtySetting> findListQvo(AppSecurtyQvo qvo) throws Exception;

    /**
     * 添加默认禁用安全设置
     * @param drPatientId
     * @param type
     * @param state
     * @return
     */
    public AppSecurtySetting addSecurty(String drPatientId, String type, String state) throws Exception;

    /**
     * 根据用户和类型查询
     * @param drPatientId
     * @param type
     * @return
     */
    public String findByUserTypeId(String drPatientId, String type) throws Exception;
}
