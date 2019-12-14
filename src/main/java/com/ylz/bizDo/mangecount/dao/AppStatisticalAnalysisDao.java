package com.ylz.bizDo.mangecount.dao;

import com.ylz.bizDo.mangecount.vo.PerformanceVo;
import com.ylz.bizDo.mangecount.vo.ResidentVo;

import java.util.Map;

/**
 * 统计分析
 * Created by zzl on 2017/7/19.
 */
public interface AppStatisticalAnalysisDao {

    /**
     * 查询随访量、健康指导、健康教育、求助量、未缴费人数
     * @param qvo areaId 区域编码
     * @param qvo hospId 医院id
     * @param qvo teamId 团队id
     * @param qvo yearStart 开始时间（yyyy-MM)
     * @param qvo yearEnd 结束时间(yyyy-MM)
     * @return
     */
    public Map<String,Object> findCount(ResidentVo qvo) throws Exception;

    /**
     * 查询随访工作量
     * @param qvo areaId 区域编码
     * @param qvo hospId 医院id
     * @param qvo teamId 团队id
     * @param qvo yearStart 开始时间（yyyy-MM)
     * @param qvo yearEnd 结束时间(yyyy-MM)
     * @return
     */
    public Map<String,Object> findSfWorkPlan(ResidentVo qvo) throws Exception;

    /**
     * 遵从医嘱情况
     * @param qvo areaId 区域编码
     * @param qvo hospId 医院id
     * @param qvo teamId 团队id
     * @param qvo yearStart 开始时间（yyyy-MM)
     * @param qvo yearEnd 结束时间(yyyy-MM)
     * @return
     */
    public Map<String,Object> findFollowDoctor(ResidentVo qvo) throws Exception;

    /**
     * 拒转签情况
     * @param qvo areaId 区域编码
     * @param qvo hospId 医院id
     * @param qvo teamId 团队id
     * @param qvo yearStart 开始时间（yyyy-MM)
     * @param qvo yearEnd 结束时间(yyyy-MM)
     * @return
     */
    public Map<String,Object> findRefuseToSign(ResidentVo qvo) throws Exception;

    /**
     * 健康干预统计
     * @param qvo areaId 区域编码
     * @param qvo hospId 医院id
     * @param qvo teamId 团队id
     * @param qvo yearStart 开始时间（yyyy-MM)
     * @param qvo yearEnd 结束时间(yyyy-MM)
     * @return
     */
    public Map<String,Object> findGuideWork(ResidentVo qvo) throws Exception;

    /**
     * 随访量统计
     * @param qvo areaId 区域编码
     * @param qvo hospId 医院id
     * @param qvo teamId 团队id
     * @param qvo yearStart 开始时间（yyyy-MM)
     * @param qvo yearEnd 结束时间(yyyy-MM)
     * @return
     */
    public Map<String, Object> findIndexFollowPlan(ResidentVo qvo) throws Exception;

    /**
     * 健康指导统计
     * @param qvo areaId 区域编码
     * @param qvo hospId 医院id
     * @param qvo teamId 团队id
     * @param qvo yearStart 开始时间（yyyy-MM)
     * @param qvo yearEnd 结束时间(yyyy-MM)
     * @return
     */
    public Map<String, Object> findIndexGuide(ResidentVo qvo) throws Exception;

    /**
     * 健康教育统计
     * @param qvo areaId 区域编码
     * @param qvo hospId 医院id
     * @param qvo teamId 团队id
     * @param qvo yearStart 开始时间（yyyy-MM)
     * @param qvo yearEnd 结束时间(yyyy-MM)
     * @return
     */
    public Map<String, Object> findIndexHealth(ResidentVo qvo) throws Exception;

    /**
     * 求助量统计
     * @param qvo areaId 区域编码
     * @param qvo hospId 医院id
     * @param qvo teamId 团队id
     * @param qvo yearStart 开始时间（yyyy-MM)
     * @param qvo yearEnd 结束时间(yyyy-MM)
     * @return
     */
    public Map<String,Object> findIndexHelp(ResidentVo qvo) throws Exception;

    /**
     * 未缴费统计
     * @param qvo areaId 区域编码
     * @param qvo hospId 医院id
     * @param qvo teamId 团队id
     * @param qvo yearStart 开始时间（yyyy-MM)
     * @param qvo yearEnd 结束时间(yyyy-MM)
     * @return
     */
    public Map<String,Object> findIndexUnpaid(ResidentVo qvo) throws Exception;

    /**
     * 咨询统计
     * @param qvo
     * @return
     */
    public Map<String,Object> findConsult(PerformanceVo qvo) throws Exception;

    /**
     * 随访统计（完成量/计划量）
     * @param qvo
     * @return
     */
    public Map<String,Object> findVisit(PerformanceVo qvo) throws Exception;

    /**
     * 健康指导统计
     * @param qvo
     * @return
     */
    public Map<String,Object> findGuide(PerformanceVo qvo) throws Exception;

    /**
     * 健康教育统计
     * @param qvo
     * @return
     */
    public Map<String,Object> findHealth(PerformanceVo qvo) throws Exception;


}
