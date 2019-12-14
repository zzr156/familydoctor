package com.ylz.bizDo.app.dao;

import com.ylz.bizDo.app.po.AppLabelDisease;
import com.ylz.bizDo.app.po.AppLabelEconomics;
import com.ylz.bizDo.app.po.AppLabelGroup;
import com.ylz.bizDo.app.po.AppPatientUser;
import com.ylz.bizDo.app.vo.AppLabelManageQvo;
import com.ylz.bizDo.jtapp.commonEntity.AppDiseaseLabelEntity;

import java.util.List;

/**
 * 居民标签分组
 */
public interface AppLabelGroupDao {

    /**
     * 根据签约主键和标签类型查询患者标签
     * @param signId
     * @param type
     * @return
     */
    public List<AppLabelGroup> findBySignGroup(String signId, String type) throws Exception;


    /**
     * 添加标签数据
     * @param value
     * @param signId
     * @param teamId
     * @param areaCode
     * @param type
     */
    public void addLabel(String[] value,String signId,String teamId,String areaCode,String type) throws Exception;

    /**
     * 根据签约主键和标签类型查询患者经济人口标签
     */
    public List<AppLabelEconomics> findBySignEconomics(String signId, String type) throws Exception;

    /**
     * 根据签约主键和标签类型查询患者疾病类型标签
     */
    public List<AppLabelDisease> findBySignDisease(String signId, String type) throws Exception;


    /**
     * 判断患者经济类型
     * @param signId
     * @param value
     * @return
     */
    public List<AppLabelEconomics> findBySignEcon(String signId, String value) throws Exception;

    /**
     * 查询个人服务人群
     * @param formId
     * @return
     */
    public String findFwValue(String formId) throws Exception;

    /**
     * 查询个人经济类型
     * @param formId
     * @return
     */
    public String findJjValue(String formId) throws Exception;

    /**
     * 查询高血压、糖尿病人群
     * @param qvo
     * @return
     */
    public List<AppDiseaseLabelEntity>  findLabelGroupByQvo(AppLabelManageQvo qvo) throws Exception;

    /**
     * 通过签约单主键查询单个患者高血压和糖尿病情况
     * @param signId
     * @return
     */
    public AppDiseaseLabelEntity findLabelGroupByOne(String signId) throws Exception;

    /**
     * 根据签约单id和疾病类型查询患者是否有高血压和糖尿病疾病标签
     * @param value
     * @param signId
     * @return
     */
    public AppLabelDisease findDiseaseByOne(String value,String signId) throws Exception;

    /**
     * 根据居民服务人群添加疾病标签
     * @param user
     */
    public void addLabelDis(AppPatientUser user) throws Exception;

    /**
     * 根据签约单主键查询人口服务类型
     * WangCheng
     * @param signId
     * @return
     */
    public List<AppLabelGroup> listLabelGroup(String signId) throws Exception;

    /**
     * 根据签约单主键查询人口经济性质
     * WangCheng
     * @param signId
     * @return
     */
    public List<AppLabelEconomics> listLabelEconomics(String signId) throws Exception;

    /**
     * 签约单生成服务类型,经济类型,疾病类型
     * @param resultStr
     * @param type
     * @param signId
     */
    public void  addSignLabelData(String resultStr,String type,String signId)throws Exception;

    /**
     * 根据签约单id和服务类型查询服务标签
     * @param signId
     * @param value
     * @return
     * @throws Exception
     */
    public AppLabelGroup findGroupBySignAndValue(String signId,String value) throws Exception;
}
