package com.ylz.bizDo.app.dao;

import com.ylz.bizDo.app.entity.AppSystemVersionEntity;
import com.ylz.bizDo.app.po.AppSystemVsersion;
import com.ylz.bizDo.app.vo.AppSystemVesionQvo;
import com.ylz.bizDo.app.vo.AppSystemVsersionQvo;

import java.util.List;

/**
 * 系统版本更新
 */
public interface AppSystemVsersionDao {

    /**
     * 系统版本更新查询
     * @param qvo
     * @return
     */
    public List<AppSystemVsersion> findListQvo(AppSystemVesionQvo qvo) throws Exception;

    /**
     * 系统版本更新查询
     * @param qvo
     * @return
     */
    public AppSystemVersionEntity findSystemVersion(AppSystemVsersionQvo qvo) throws Exception;
}
