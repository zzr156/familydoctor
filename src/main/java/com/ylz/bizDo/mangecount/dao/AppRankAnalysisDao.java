package com.ylz.bizDo.mangecount.dao;

import com.ylz.bizDo.mangecount.vo.ResidentVo;

import java.util.Map;

/**
 * 排名分析
 */
public interface AppRankAnalysisDao {
    /**
     * 综合排名
     * @param qvo areaId 区域主键 hospId 社区主键
     * @return
     */
    public Map<String,Object> findRanking(ResidentVo qvo) throws Exception;

    /**
     * 新综合排名（工作进度、签约完成率、续签率）
     * @param qvo
     * @return
     */
    public Map<String,Object> findNewRanking(ResidentVo qvo) throws Exception;
}
