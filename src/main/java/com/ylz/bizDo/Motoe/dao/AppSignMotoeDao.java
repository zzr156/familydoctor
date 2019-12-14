package com.ylz.bizDo.Motoe.dao;

import com.ylz.bizDo.Motoe.dao.vo.SignsurrenderVo;
import com.ylz.bizDo.app.po.AppPatientUser;
import com.ylz.bizDo.app.po.AppSignForm;
import com.ylz.bizDo.app.po.AppTeam;
import com.ylz.bizDo.mangecount.vo.ResidentVo;

import java.util.Map;

/**
 * 签约分析
 */
public interface AppSignMotoeDao {





    /**
     * 对基卫接口签约统计
     * @param qvo
     * @return
     */
    public Map<String,Object> findSignAnalysisIndexMotoe(ResidentVo qvo) throws Exception;


    /**
     * 签约转取统计
     * @param qvo
     * @return
     */
    public Map<String,Object> findSignAnalysisListMotoe(ResidentVo qvo) throws Exception;


    // 居民档案号 查询签约单
    public AppSignForm findSignformJMDA(SignsurrenderVo vo)throws Exception;


    // 基卫建档 验证死亡接口
    public AppSignForm findSignPatient(String patientIdno)throws Exception;
}
