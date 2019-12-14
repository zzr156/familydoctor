package com.ylz.bizDo.plan.bizDo;

import com.ylz.bizDo.app.po.AppDrUser;
import com.ylz.bizDo.plan.Entity.AppFollowGroupEntity;
import com.ylz.bizDo.plan.Entity.AppFollowListEntity;
import com.ylz.bizDo.plan.Entity.AppFollowPlanXYEntity;
import com.ylz.bizDo.plan.Entity.AppFollowRecordEntity;
import com.ylz.bizDo.plan.po.AppFollowPlan;
import com.ylz.bizDo.plan.vo.AppFollowAddQvo;
import com.ylz.bizDo.plan.vo.AppFollowGroupQvo;
import com.ylz.bizDo.plan.vo.AppFollowPlanQvo;
import com.ylz.bizDo.plan.vo.AppFollowStateQvo;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017-7-23.
 */
public interface AppNewFollowPlanDao {

    /**
     * 根据服务类型筛选随访人群
     * @param qvo group
     * @return
     */
    List<AppFollowGroupEntity> findFollowByGroup(AppFollowGroupQvo qvo) throws Exception;

    /**
     * 新增随访计划(制定随访计划)
     * @param qvo patientId;//多个用分割隔开
     * @param qvo followWay;//随访方式
     * @param qvo followState;//随访状态
     * @param qvo teamId;
     * @param qvo followDate;//随访计划时间
     * @return
     */
    public AppFollowPlan addFollowPlan(AppFollowAddQvo qvo, AppDrUser drUser) throws Exception;

    /**
     * 查询随访计划
     * @param qvo group
     * @param qvo day
     * @param qvo patientName
     * @param qvo drId
     * @return
     */
    public List<AppFollowGroupEntity> findFollowPlan(AppFollowGroupQvo qvo) throws Exception;

    /**
     * 查询我的随访记录
     * @param qvo group
     * @param qvo startDate
     * @param qvo endDate
     * @param qvo patientName
     * @return
     */
    public List<AppFollowGroupEntity> findFollowRecord(AppFollowGroupQvo qvo) throws Exception;

    /**
     * 查询患者的随访记录
     * @param qvo patientId
     * @return
     */
    public List<AppFollowRecordEntity> findPersonFollowRecord(AppFollowGroupQvo qvo) throws Exception;


    /**
     *  随访失访或死亡
     *  @param qvo followId;//随访主表id
     *  @param qvo state;//失访或死亡
     *  @param qvo reason;//失访或死亡原因
     *  @param qvo date;//失访下次随访日期或死亡日期
     *  @param qvo xAxis;
     *  @param qvo yAxis;
     * @return
     */
    public AppFollowPlan saveFollowPlanState(AppFollowStateQvo qvo) throws Exception;

    /**
     *根据类型查询已完成的随访记录
     * @param type
     * @return
     */
    public List<AppFollowPlan> findDoneByType(String type) throws Exception;

    /**
     *  面访的坐标
     *  @param drId
     * @return
     */
    public List<AppFollowPlanXYEntity> findDrFollowLocation(String drId,String followDate) throws Exception;

    /**
     *  随访列表(时间次数)
     *  @param qvo drId
     * @return
     */
    public List<AppFollowListEntity> findDrFollowLocationList(AppFollowPlanQvo qvo) throws Exception;

    /**
     * 随访日程统计
     * @param qvo
     * @return
     */
    public Map<String, Object> findDrFollowLocationCount(AppFollowPlanQvo qvo) throws Exception;
}
