package com.ylz.bizDo.mangecount.dao;

import com.ylz.bizDo.mangecount.entity.ManageTeamCountEntity;
import com.ylz.bizDo.mangecount.vo.ResidentVo;

import java.util.List;
import java.util.Map;

/**
 * 居民分析
 */
public interface AppResidentAnalysisDao {

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
     * 查询性别男女(返回字段英文)
     * @param  qvo areaId 区域主键 hospId 社区主键 teamId 团队主键
     * @return
     */
    public Map<String,Object> findCountGenderEnglish(ResidentVo qvo) throws Exception;

    /**
     * 服务分布(返回字段英文)
     * @param qvo areaId 区域主键 hospId 社区主键 teamId 团队主键
     * @return
     */
    public Map findPersGroupCountEnglish(ResidentVo qvo) throws Exception;

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

    /**
     * 在时间内的签约数据计算
     * @param qvo
     * @return
     */
    public Map<String,Object> findCountByMin(ResidentVo qvo) throws Exception;

    /**
     * 糖尿病、高血压排名和综合排名统计
     * @param qvo
     * @return
     */
    public Map<String,Object> findChronicDisease(ResidentVo qvo) throws Exception;

    /**
     * 团队履约排行
     * @param qvo
     * @return
     */
    public List<ManageTeamCountEntity> findTeamCount(ResidentVo qvo) throws Exception;

    /**
     * 慢病统计
     * @param qvo
     * @return
     */
    public Map<String,Object> findChronic(ResidentVo qvo) throws Exception;

    /**
     * 签约团队统计
     * @param qvo
     * @return
     */
    public Map<String,Object> findManageTeam(ResidentVo qvo) throws Exception;

    /**
     * 按行政区划级别各级展示 签约总数，一般人群签约数，高血压签约数，
     * 糖尿病签约数、老年人签约数、孕产妇人群签约数，儿童签约数、
     * 严重精神障碍签约数、肺结核签约数；
     * @param qvo
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> findSignManage(ResidentVo qvo) throws Exception;
}
