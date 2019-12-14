package com.ylz.bizDo.app.dao;

import com.ylz.bizDo.app.entity.AppOutpatientCountEntity;
import com.ylz.bizDo.app.po.AppOutpatientCount;
import com.ylz.bizDo.jtapp.patientEntity.AppTeamManagEntity;
import com.ylz.bizDo.mangecount.vo.ResidentVo;

import java.util.List;

/**
 * Created by lenovo on 2018/1/11.
 */
public interface AppOutpatientCountDao {

    /*
     *门诊费用 医保年度支出调度统计
     */
    public void AppOutpatientCount(String date, List<AppTeamManagEntity> lsTeam) throws Exception;

    /*
     * 查询当月是否有数据
     */
    public AppOutpatientCount findYearOutpatienByHospTeamId(String year, String month,String hospId, String teamId) throws Exception;

    /**
     * 查询团队数据
     */
    public AppOutpatientCountEntity findOutpatienByCount(ResidentVo qvo) throws Exception;

}
