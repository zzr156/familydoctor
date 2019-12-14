package com.ylz.bizDo.performance.bizDo;

import com.ylz.bizDo.app.po.AppPerformancePhysicalExamination;
import com.ylz.bizDo.jtapp.commonEntity.AppLyTxEntity;
import com.ylz.bizDo.jtapp.commonEntity.AppTcmLyEntity;
import com.ylz.bizDo.jtapp.commonVo.AppCommQvo;
import com.ylz.bizDo.jtapp.drVo.AppLyQvo;
import com.ylz.bizDo.mangecount.vo.ResidentVo;
import com.ylz.bizDo.performance.vo.HealthGroupVo;
import com.ylz.bizDo.performance.vo.PerformanceVo;
import com.ylz.packcommon.common.exception.DaoException;

import java.util.List;
import java.util.Map;

/**
 * 绩效
 */
public interface AppPerFormanceDao {

    /**
     * 首页统计(签约人员,未缴费人群,咨询量,求助量)
     * @param qvo areaId 区域 hospId 医院主键 teamId 团队主键 drId 医生主键
     * @return
     */
    public Map<String,Object> findIndexCount(PerformanceVo qvo) throws Exception;

    /**
     * 工作进度统计(总计划,未完成,完成,完成率,周,月数据)
     * @param qvo areaId 区域 hospId 医院主键 teamId 团队主键 drId 医生主键
     * @return
     */
    public Map<String,Object> findWorkPlanCount(PerformanceVo qvo) throws Exception;

    /**
     * 签约居民健康分布情况
     * @param qvo hospId 医院主键 drId 医生主键 labelGruops 疾病类型 多使用分号隔开 labelGruopsColor 疾病类型颜色 多使用分号隔开
     * @return
     */
    public List<HealthGroupVo> findHealthGroup(PerformanceVo qvo) throws Exception;

    /**
     * 健康干预工作量统计
     * @param qvo hospId 医院主键 drId 医生主键
     * @return
     */
    public Map findHealthMeddle(PerformanceVo qvo) throws Exception;

    /**
     * 拒管居民动态
     * @param qvo hospId 医院主键 drId 医生主键 startYyyyMm 开始月份 endYyyyMm 结束月份
     */
    public Map findRefuseSign(PerformanceVo qvo) throws Exception;

    /**
     * 随访工作量统计
     * @param qvo hospId 医院主键 drId 医生主键
     * @return
     */
    public Map<String,Object> findFollowWorkPlanCount(PerformanceVo qvo) throws Exception;

    /**
     * 随访任务量统计
     * @param qvo hospId 医院主键 drId 医生主键
     * @return
     * @throws DaoException
     */
    public Map<String, Object> findByTime(PerformanceVo qvo) throws Exception;

    /**
     * 工作进度，随访，健康干预
     * @param qvo hospId 社区主键
     * @return
     */
    public Map findTeamNo(PerformanceVo qvo) throws Exception;
    /**
     * 团队排名 工作进度，随访，健康干预
     * @param qvo hospId 社区主键
     * @return
     */
    public Map findTeamTop(PerformanceVo qvo) throws Exception;

    /**
     * 健康分布
     * @param qvo areaId 区域主键 hospId 社区主键 teamId 团队主键 drId 医生主键
     * @return
     */
    public Map findHealthGroupCount(ResidentVo qvo) throws Exception;

    /**
     * 服务分布
     * @param qvo areaId 区域主键 hospId 社区主键 teamId 团队主键 drId 医生主键
     * @return
     */
    public Map findPersGroupCount(ResidentVo qvo) throws Exception;

    /**
     * 统计遵从医嘱
     * @param qvo drId 医生主键
     * @param qvo startDate 开始日期
     * @param qvo endDate 结束日期
     * @return
     */
    public List<Map<String, Object>> findFollowDoctor(PerformanceVo qvo) throws Exception;

    /**
     * 医生完成健康教育记录
     * @param qvo
     * @return
     */
    public AppLyTxEntity findHealthEducation(AppCommQvo qvo) throws Exception;

    /**
     * 医生完成健康指导记录
     * @param qvo
     * @return
     */
    public AppLyTxEntity findHealthGuide(AppCommQvo qvo) throws Exception;

    /**
     * 医生完成用药指导记录
     * @param qvo
     * @return
     */
    public AppLyTxEntity findGuidelines(AppCommQvo qvo) throws Exception;

    /**
     * 查询定期随访（高血压、糖尿病、重性精神病、肺结核）
     * @param qvo
     * @return
     */
    public List<AppLyTxEntity> findVisit(AppCommQvo qvo) throws Exception;

    /**
     * 中医药健康指导履约记录
     * @param qvo
     * @return
     */
    public List<AppTcmLyEntity> findTcmLy(AppCommQvo qvo) throws Exception;

    /**
     * 孕期保健服务履约记录
     * @param qvo
     * @return
     */
    public List<AppLyTxEntity> findPrenatalCare(AppCommQvo qvo) throws Exception;

    /**
     * 产后42天健康检查记录
     * @param qvo
     * @return
     */
    public List<AppLyTxEntity> findPostPartum(AppCommQvo qvo) throws Exception;

    /**
     * 健康体检履约记录
     * @param qvo
     * @return
     */
    public List<AppPerformancePhysicalExamination> findJktjList(AppCommQvo qvo) throws Exception;

    /**
     * 履约通知
     * @param qvo
     * @return
     * @throws Exception
     */
    public String performanceNotice(AppLyQvo qvo) throws Exception;

    /**
     * 年龄分布
     * @param qvo
     * @return
     * @throws Exception
     */
    public Map findAgeCount(ResidentVo qvo) throws Exception;

    /**
     * 经济性质分布
     * @param qvo
     * @return
     * @throws Exception
     */
    public Map findEconomicCount(ResidentVo qvo) throws Exception;

    /**
     * 性别分布
     * @param qvo
     * @return
     * @throws Exception
     */
    public Map findGenderCount(ResidentVo qvo) throws Exception;


}
