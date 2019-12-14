package com.ylz.bizDo.web.dao;

import com.ylz.bizDo.app.po.AppTeam;
import com.ylz.bizDo.mangecount.vo.ResidentVo;

import java.util.Map;

/**
 * 签约分析
 */
public interface WebSignAnalysisDao {

    /**
     * 签约首页统计
     * @param qvo
     * @return
     */
    public Map<String,Object> findSignAnalysisIndex(ResidentVo qvo) throws Exception;


    /**
     * 签约转取统计
     * @param qvo
     * @return
     */
    public Map<String,Object> findSignAnalysisList(ResidentVo qvo) throws Exception;


    /**
     * 签约转取统计
     * @param qvo
     * @return
     */
    public Map<String,Object> findSignAnalysisListInitialise(ResidentVo qvo) throws Exception;

    /**
     * 经济类型统计
     * @param qvo
     * @return
     */
    public String findSignEconomicTypeList(ResidentVo qvo) throws Exception;

    /**
     * 经济类型统计
     * @param qvo
     * @return
     */
    public Map<String,Object> findSignEconomicTypeListInitialise(ResidentVo qvo) throws Exception;

    /**
     * 续签数
     * @param qvo
     * @return
     */
    public Map<String,Object> findSignRenew(ResidentVo qvo) throws Exception;

    /**
     * 上一年度签约数
     * @param qvo
     * @return
     */
    public Map<String,Object> findSignTotalRenew(ResidentVo qvo) throws Exception;

    /**
     * 评价统计
     * @param qvo
     * @return
     * @throws Exception
     */
    public Map<String, Object> findAssess(ResidentVo qvo) throws Exception;

    /**
     * 经济类型统计
     * @param qvo
     * @return
     */
    public Map<String,Object> findSignEconomicTypeCount(ResidentVo qvo) throws Exception;


    /**
     * 团队数据
     */
    public Map<String,Object> getSianCountTeam(AppTeam team, String startDate, String endDate) throws Exception;

    /**
     * 转签统计
     * @param qvo
     * @return
     */
    public Map<String,Object> findGotoSign(ResidentVo qvo) throws Exception;

}
