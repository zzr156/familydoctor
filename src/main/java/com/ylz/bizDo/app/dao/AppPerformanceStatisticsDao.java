package com.ylz.bizDo.app.dao;

import com.ylz.bizDo.app.entity.AppServeManagePerformanceEntity;
import com.ylz.bizDo.app.vo.AppServeManageQvo;
import com.ylz.bizDo.app.vo.PerformanceDataQvo;
import com.ylz.bizDo.jtapp.commonVo.AppCommQvo;

import java.util.List;
import java.util.Map;

/**
 * 履约
 */
public interface AppPerformanceStatisticsDao {

    /**
     * 添加履约数据
     * @param qvo
     */
    public void addPerformanceData(PerformanceDataQvo qvo,String sermeal,String fwType) throws Exception;

    /**
     * 统计个人履约数据
     * @param qvo
     * @return
     */
    public Map<String,Object> findAppSingPerformance(AppCommQvo qvo) throws Exception;

    /**
     * 健康教育(履约)
     * @param yearMonth 年月
     * @param year 年
     * @param historyYear 历史
     * @param patientIdNo 患者身份证
     * @param drId 医生主键
     * @param hospId 医院主键
     * @param areaCode 行政区划
     * @return
     */
    public int getHealthEducation(String yearMonth, String year,String historyYear,String patientIdNo,String drId,String hospId,String areaCode) throws Exception;

    /**
     * 健康指导(履约)
     * @param yearMonth 年月
     * @param year 年
     * @param historyYear 历史
     * @param patientIdNo 患者身份证
     * @param drId 医生主键
     * @param hospId 医院主键
     * @param areaCode 行政区划
     * @return
     */
    public int getHealthGuidance(String yearMonth, String year,String historyYear,String patientIdNo,String drId,String hospId,String areaCode) throws Exception;


    /**
     * 健康体检(履约)
     * @param yearMonth 年月
     * @param year 年
     * @param historyYear 历史
     * @param patientIdNo 患者身份证
     * @param drId 医生主键
     * @param hospId 医院主键
     * @param areaCode 行政区划
     * @return
     */
    public int getPhysicalExamination(String yearMonth, String year,String historyYear,String patientIdNo,String drId,String hospId,String areaCode) throws Exception;

    /**
     * 中医体质辨识(履约)
     * @param yearMonth 年月
     * @param year 年
     * @param historyYear 历史
     * @param patientIdNo 患者身份证
     * @param drId 医生主键
     * @param hospId 医院主键
     * @param areaCode 行政区划
     * @return
     */
    public int getConstitutionIdentification(String yearMonth, String year, String historyYear, String patientIdNo, String drId,String hospId,String areaCode) throws Exception;


    /**
     * 慢病用药指导(履约)
     * @param yearMonth 年月
     * @param year 年
     * @param historyYear 历史
     * @param patientIdNo 患者身份证
     * @param drId 医生主键
     * @param hospId 医院主键
     * @param areaCode 行政区划
     * @return
     */
    public int getMedicationGuidance(String yearMonth, String year, String historyYear, String patientIdNo, String drId,String hospId,String areaCode) throws Exception;

    /**
     * 随访工作(履约)
     * @param yearMonth 年月
     * @param year 年
     * @param historyYear 历史
     * @param patientIdNo 患者身份证
     * @param followType 类型 新生儿访视,儿童体检,高血压,糖尿病,神经病,结核病
     * @param drId 医生主键
     * @param hospId 医院主键
     * @param areaCode 行政区划
     * @return
     */
    public int getRegularFollowup(String yearMonth, String year, String historyYear,String followType, String patientIdNo, String drId,String hospId,String areaCode) throws Exception;


    /**
     * 中医药健康指导（0-36月龄）履约
     * @param yearMonth 年月
     * @param year 年
     * @param historyYear 历史
     * @param patientIdNo 患者身份证
     * @param drId 医生主键
     * @param hospId 医院主键
     * @param areaCode 行政区划
     * @return
     */
    public int getChineseGuidance(String yearMonth, String year, String historyYear, String patientIdNo, String drId,String hospId,String areaCode) throws Exception;


    /**
     * 健康咨询(履约)
     * @param yearMonth 年月
     * @param year 年
     * @param historyYear 历史
     * @param patientIdNo 患者身份证
     * @param drId 医生主键
     * @param hospId 医院主键
     * @param areaCode 行政区划
     * @return
     */
    public int getHealthConsultation(String yearMonth, String year,String historyYear,String patientIdNo,String drId,String hospId,String areaCode) throws Exception;

    /**
     * 产后访视(履约)
     * @param yearMonth 年月
     * @param year 年
     * @param historyYear 历史
     * @param patientIdNo 患者身份证
     * @param drId 医生主键
     * @param hospId 医院主键
     * @param areaCode 行政区划
     * @return
     */
    public int getPostpartumVisit(String yearMonth, String year,String historyYear,String patientIdNo,String drId,String hospId,String areaCode) throws Exception;


    /**
     * 孕期保健服务(履约)
     * @param yearMonth 年月
     * @param year 年
     * @param historyYear 历史
     * @param patientIdNo 患者身份证
     * @param drId 医生主键
     * @param hospId 医院主键
     * @param areaCode 行政区划
     * @return
     */
    public int getPrenatalCare(String yearMonth, String year,String historyYear,String patientIdNo,String drId,String hospId,String areaCode) throws Exception;


    /**
     * 管理端履约情况
     * @param qvo
     * @return
     */
    public Map<String, Object> findAppSingPerformanceManage(AppCommQvo qvo) throws Exception;
    /**
     * 医生履约情况
     * @param qvo
     * @return
     */
    public Map<String,Object> findAppSingPerformanceDr(AppCommQvo qvo) throws Exception;

    /**
     * 应完成情况履约(医生,医院,省市区)
     * @param manageQvo
     * @return
     */
    public List<AppServeManagePerformanceEntity> getManagePerformanceCompleted(AppServeManageQvo manageQvo) throws Exception;

	/**
	 * <p>
	 * 管理端履约统计(新)
	 * </p>
	 * <p>
	 * 按服务包进行统计
	 * </p>
	 * 
	 * @version 2018-05-07
	 * @author lyy
	 * @param qvo
	 * @return
	 */
	public Map<String, Object> findNewAppSingPerformanceManage(AppCommQvo qvo) throws Exception;
	
    /**
     * 统计个人履约数据
     * @param qvo
     * @return
     */
    public Map<String,Object> findNewAppSingPerformance(AppCommQvo qvo) throws Exception;

    /**
     * 医生履约情况（新）
     * 
     * @param qvo
     * @return
     */
    public Map<String,Object> findNewAppSingPerformanceDr(AppCommQvo qvo) throws Exception;

    /**
     * 添加履约数据（新）
     * @param vo
     */
    public void addPerformance(PerformanceDataQvo vo) throws Exception;
    
    /**
     * 公共服务(履约)已完成
     * 
     * @author lyy
     * @param year 年
     * @param drId 医生主键
     * @param hospId 医院主键
     * @param areaCode 行政区划
     * @return
     */
    public int getPublicService(String year, String drId,String hospId,String areaCode) throws Exception;
}
