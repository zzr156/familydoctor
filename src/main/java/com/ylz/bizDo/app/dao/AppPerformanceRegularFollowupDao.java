package com.ylz.bizDo.app.dao;

import com.ylz.bizDo.jtapp.commonEntity.AppChildLyEntity;
import com.ylz.bizDo.jtapp.commonEntity.AppLyTxEntity;
import com.ylz.bizDo.jtapp.commonVo.AppCommLyQvo;
import com.ylz.bizDo.jtapp.commonVo.AppCommQvo;

import java.util.List;

/**
 * Created by zzl on 2017/11/14.
 */
public interface AppPerformanceRegularFollowupDao {
    /**
     * 查询履约定期随访记录
     * @param qvo
     * @return
     */
    public List<AppLyTxEntity> findList(AppCommLyQvo qvo) throws Exception;

    /**
     * 查询0-6岁儿童健康检查履约情况
     * @param qvo
     * @return
     */
    public List<AppChildLyEntity> findChildHealth(AppCommLyQvo qvo) throws Exception;

    /**
     * 产后访视履约人员列表
     * @param qvo
     * @return
     */
    public List<AppLyTxEntity> findPostPartumList(AppCommLyQvo qvo) throws Exception;

}
