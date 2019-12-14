package com.ylz.bizDo.app.dao;

import com.ylz.bizDo.app.po.AppPersonDrug;
import com.ylz.bizDo.jtapp.commonEntity.AppDrugEntity;
import com.ylz.bizDo.jtapp.commonVo.AppDrugVo;

import java.util.List;
import java.util.Map;

/**
 * 家庭药箱
 */
public interface AppDrugDao {
    /**
     * 根据药品名称搜索药品
     * @param drugName
     * @param hospId
     * @return
     */
    public List<AppDrugEntity> findByDrugName(String drugName,String hospId) throws Exception;

    /**
     * 查找个人药品库常用药品
     * @param vo
     * @return
     */
    public List<AppDrugEntity> findPersonDrug(AppDrugVo vo) throws Exception;

    /**
     * 查询个人药品库中是否已存在该药品
     * @param patientId
     * @param drugId
     * @return
     */
    public List<AppPersonDrug> findUniqueDrug(String patientId, String drugId) throws Exception;

}
