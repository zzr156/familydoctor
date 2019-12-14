package com.ylz.bizDo.assessment.dao;

import com.ylz.bizDo.assessment.po.Assessment;
import com.ylz.bizDo.assessment.vo.*;
import com.ylz.bizDo.jtapp.commonEntity.AssessNewsEntity;

import java.util.List;

/**
 * Created by zms on 2018/6/8.
 */
public interface AssessmentDao {

    // 查询列表
    List<AssessListVo> findAssessList(AssessListQvo qvo) throws Exception;

    // 查询已经考核过的人
    String findAssess(String hospId) throws Exception;

    //
    Assessment findAssessment(Assessment assessment) throws Exception;

    // 保存
    Assessment saveOrUpdateAssessment(Assessment po) throws Exception;

    // 所属人群
    List<AssessmentGroup> findGroup(AssessmentVo qvo) throws Exception;

    // 查询该病人相关信息(接口参数信息)
    interfaceQvo findInterceParams(AssessmentVo qvo) throws Exception;

    // 退回
    int treatAssess(ReviewVo vo) throws Exception;

    // 查询协议到期前一个月还未考核的人员
    List<AssessmentVo> findNotAssess(String hospId) throws Exception;

    // 查找32项目截止本月前最后一周还没考核的人
    List<String> findNotAssessDetail(AssessmentVo qvo) throws Exception;

    // 文件下载
    Object downLoad(AssessFileVo vo) throws Exception;

    // 签约单数量统计
    int countSignNum(AssessListQvo qvo) throws Exception;

    // 签约单数量统计
    List<AssessmentCountVo> countSignNumForArea(AssessListQvo qvo) throws Exception;

    // 已考核完成数量统计
    int countFinishNum(AssessListQvo qvo) throws Exception;

    // 各评级人数统计
    List<AssessmentCountVo> countRatingNum(AssessListQvo qvo) throws Exception;

    // 各评级人数统计
    List<AssessmentCountVo> countRatingNumForArea(AssessListQvo qvo) throws Exception;

    /**
     * 查询到期前一个月未考核人数 医生分组
     *
     * @return
     */
    public List<AssessNewsEntity> findAssessMonthSign() throws Exception;

    /**
     * 查询提前一周前还未考核人数 医生分组
     *
     * @return
     */
    public List<AssessNewsEntity> findAssessWeekSign() throws Exception;

    /**
     * 查询到期前一个月未考核人数 根据医生id查询
     *
     * @return
     */
    public List<AssessNewsEntity> findAssessMonthSignById(String drId) throws Exception;

    /**
     * 每月需上传佐证的考核项目，提前一周提醒还有那些人还未考核 根据医生id查询
     *
     * @param drId
     * @return
     */
    public List<String> findAssessWeekSignById(String drId) throws Exception;
}
