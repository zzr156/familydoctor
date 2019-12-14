package com.ylz.bizDo.assessment.dao;

import com.ylz.bizDo.assessment.po.AssessmentTeamShare;

import java.util.List;

/**
 * Created by zms on 2018/6/13.
 */
public interface AssessmentTeamShareDao {

    /**
     * 查找某个团队某个时间区间内的共享资源
     *
     * @param teamId 团队ID
     * @param year   年份
     * @param month  月份
     * @return 团队共享资源列表
     * @throws Exception 异常
     */
    public List<AssessmentTeamShare> listShare(String teamId, Integer year, Integer month) throws Exception;

    /**
     * 查找某个团队某年某月的共享资源
     *
     * @param teamId 团队ID
     * @param year   年份
     * @param month  月份
     * @return 团队共享资源
     * @throws Exception 异常
     */
    public AssessmentTeamShare getShare(String teamId, Integer year, Integer month) throws Exception;
}
