package com.ylz.bizDo.web.dao;

import com.ylz.bizDo.mangecount.vo.ResidentVo;

import java.util.Map;

/**
 * 居民分析
 */
public interface WebResidentAnalysisDao {

    /**
     * 缴费情况
     * @param qvo areaId 区域主键 hospId 社区主键 teamId 团队主键
     * @return
     */
    public Map findPayStateCount(ResidentVo qvo) throws Exception;

    /**
     * 健康分布
     * @param qvo areaId 区域主键 hospId 社区主键 teamId 团队主键
     * @return
     */
    public Map findHealthGroupCount(ResidentVo qvo) throws Exception;

    /**
     * 服务分布
     * @param qvo areaId 区域主键 hospId 社区主键 teamId 团队主键
     * @return
     */
    public Map findPersGroupCount(ResidentVo qvo) throws Exception;

    /**
     * 服务分布重点人群
     * @param qvo areaId 区域主键 hospId 社区主键 teamId 团队主键
     * @return
     */
    public String findPersGroupCountFocusGroups(ResidentVo qvo) throws Exception;

    /**
     * 查询性别男女
     * @param  qvo areaId 区域主键 hospId 社区主键 teamId 团队主键
     * @return
     */
    public Map<String,Object> findCountGender(ResidentVo qvo) throws Exception;

    /**
     * 年龄分布
     * @param  qvo areaId 区域主键 hospId 社区主键 teamId 团队主键
     * @return
     */
    public Map<String,Object> findCountAge(ResidentVo qvo) throws Exception;



    /**
     * 缴费情况
     * @param qvo areaId 区域主键 hospId 社区主键 teamId 团队主键
     * @return
     */
    public Map findPayStateCountInitialise(ResidentVo qvo) throws Exception;

    /**
     * 健康分布
     * @param qvo areaId 区域主键 hospId 社区主键 teamId 团队主键
     * @return
     */
    public Map findHealthGroupCountInitialise(ResidentVo qvo) throws Exception;

    /**
     * 服务分布
     * @param qvo areaId 区域主键 hospId 社区主键 teamId 团队主键
     * @return
     */
    public Map findPersGroupCountInitialise(ResidentVo qvo) throws Exception;

    /**
     * 查询性别男女
     * @param  qvo areaId 区域主键 hospId 社区主键 teamId 团队主键
     * @return
     */
    public Map<String,Object> findCountGenderInitialise(ResidentVo qvo) throws Exception;

    /**
     * 年龄分布
     * @param  qvo areaId 区域主键 hospId 社区主键 teamId 团队主键
     * @return
     */
    public Map<String,Object> findCountAgeInitialise(ResidentVo qvo) throws Exception;

}
