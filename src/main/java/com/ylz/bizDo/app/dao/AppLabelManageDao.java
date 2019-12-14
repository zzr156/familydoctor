package com.ylz.bizDo.app.dao;

import com.ylz.bizDo.app.po.AppLabelManage;
import com.ylz.bizDo.app.vo.AppLabelManageQvo;
import com.ylz.bizDo.jtapp.commonEntity.AppMeddleEntity;
import com.ylz.bizDo.jtapp.gaiRuiEntity.GaiRuiLabelEntity;
import com.ylz.packcommon.common.exception.DaoException;

import java.util.List;

/**
 *标签管理
 */
public interface AppLabelManageDao {
    public List<AppLabelManage> findListQvo(AppLabelManageQvo qvo) throws Exception;

    /**
     * 根据类型和值查询 标签
     * @param type
     * @param value
     * @return
     */
    public AppLabelManage findLabelByValue(String type, String value) throws Exception;

    public List<AppLabelManage> findByType(String value) throws Exception;

    public List<AppMeddleEntity> findByMeddle(String value) throws Exception;

    public List<AppLabelManage> findList() throws Exception;

    /**
     * 根据类型查询标签(盖瑞)
     * @param group
     * @return
     * @throws Exception
     */
    public List<GaiRuiLabelEntity> findLabelByGroup(String group) throws Exception;
}
