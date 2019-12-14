package com.ylz.bizDo.mangecount.dao;

import com.ylz.bizDo.app.entity.AppManageArchivingCountEntity;
import com.ylz.bizDo.app.po.AppTeam;
import com.ylz.bizDo.app.vo.AppManageArchivingCountQvo;
import com.ylz.bizDo.jtapp.commonEntity.AppArchivintPeopleEntity;
import com.ylz.bizDo.jtapp.commonEntity.AppArchivintPeopleTTEntity;
import com.ylz.bizDo.mangecount.vo.ResidentVo;
import com.ylz.bizDo.smjq.smVo.AppSmMnanageQvo;

import java.util.List;
import java.util.Map;

/**
 * 签约分析
 */
public interface AppSignAnalysisDao {

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
     * 签约转取统计(建档立卡)
     * @param qvo
     * @return
     */
    public Map<String,Object> findSignAnalysisArchivingListInitialise(ResidentVo qvo) throws Exception;

    /**
     * 签约转取统计所有(建档立卡)
     * @param qvo
     * @return
     */
    public Map<String,Object> findSignAnalysisArchivingAllListInitialise(ResidentVo qvo) throws Exception;

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

    /**
     * 转诊统计
     * @param qvo
     * @return
     */
    public Map<String,Object> findReferral(ResidentVo qvo) throws Exception;


    /**
     * 对基卫接口签约统计
     * @param qvo
     * @return
     */
    public Map<String,Object> findSignAnalysisIndexMotoe(ResidentVo qvo) throws Exception;

    /**
     * 续签钻取统计
     * @param qvo
     * @return
     */
    public Map<String,Object> findRenewSign(ResidentVo qvo) throws Exception;

    /**
     * 服务续签人群
     * @param qvo
     * @return
     */
    public Map<String,Object> findServeRenew(ResidentVo qvo) throws Exception;

    /**
     * 转签数
     * @param qvo
     * @return
     */
    public Map<String,Object> findGoToSignT(ResidentVo qvo) throws Exception;

    /**
     * 跨区转签数
     * @param qvo
     * @return
     */
    public Map<String,Object> findGoToSignArea(ResidentVo qvo) throws Exception;

    /**
     * 跨社区转签数
     * @param qvo
     * @return
     */
    public Map<String,Object> findGoToSignHosp(ResidentVo qvo) throws Exception;

    /**
     * 跨团队转签数
     * @param qvo
     * @return
     */
    public Map<String,Object> findGoToSignTeam(ResidentVo qvo) throws Exception;

    /**
     * 查询上年度签约量 本年度签约量 续签量
     * @param qvo
     * @return
     */
    public Map<String,Object> signAndRenew(ResidentVo qvo) throws Exception;

    /**
     * 查询高血压随访数和糖尿病随访数
     * @param qvo
     * @return
     */
    public Map<String,Object> findPerFollowPlan(ResidentVo qvo) throws Exception;

    public List<AppManageArchivingCountEntity> findCountList(AppManageArchivingCountQvo qvo) throws Exception;

    /**
     * 福建省建档立卡农村贫困人口家庭医生签约服务工作情况表钻取统计
     */
    public List<Map<String, Object>> archivingCardCount(ResidentVo qvo) throws Exception;

    /**
     * 福建省2018年建档立卡农村贫困人口家庭医生签约服务分类管理报表
     * @param qvo
     * @return
     */
    public List<Map<String, Object>> archivingCardServeCount(ResidentVo qvo) throws Exception;

    /**
     * 根据机构查询统计数
     * @param qvo
     * @return
     */
    public Map<String,Object> findArchivingCountByHosp(ResidentVo qvo) throws Exception;

    /**
     * 根据机构查询建档立卡签约人员信息
     * @param qvo
     * @return
     */
    public List<AppArchivintPeopleEntity> findArchivingPeopleByHosp(ResidentVo qvo) throws Exception;

    /**
     * 建档立卡各未签原因调度统计
     * @param qvo
     * @return
     */
    public Map<String,Object> findManageArchiving(ResidentVo qvo) throws Exception;

    /**
     * 查询三明尤溪管理平台数据
     * @param qvo
     * @return
     */
    public Map<String,Object> findManageSmNCD(AppSmMnanageQvo qvo) throws Exception;

    public List<AppArchivintPeopleTTEntity> findArchivingTTPeopleByHosp(ResidentVo qvo) throws Exception;

    public Map<String,Object> findPovCount(ResidentVo qvo) throws Exception;

    /**
     * 随访量、健康指导、健康教育、求助量、未缴费人数统计
     * @param qvo
     * @return
     * @throws Exception
     */
    public Map<String,Object> findOtherCount(ResidentVo qvo) throws Exception;
    /**
     * 查询随访量、健康指导、健康教育、求助量、未缴费人数(查询调度数据)
     * @param qvo areaId 区域编码
     * @param qvo hospId 医院id
     * @param qvo teamId 团队id
     * @param qvo yearStart 开始时间（yyyy-MM)
     * @param qvo yearEnd 结束时间(yyyy-MM)
     * @return
     */
    public Map<String,Object> findMangeOtherCount(ResidentVo qvo) throws Exception;

    /**
     * 排名统计
     * @param qvo
     * @return
     * @throws Exception
     */
    public Map<String,Object> findRankCount(ResidentVo qvo) throws Exception;
}
