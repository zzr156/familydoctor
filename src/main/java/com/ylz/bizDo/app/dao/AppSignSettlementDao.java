package com.ylz.bizDo.app.dao;

import com.ylz.bizDo.jtapp.commonEntity.AppYbSignEntity;
import com.ylz.bizDo.jtapp.commonVo.AppYbSignQvo;

import java.util.List;

/**
 * Created by zzl on 2019/3/11.
 */
public interface AppSignSettlementDao {
    /**
     * 查询福州医保签约数据
     * @param qvo
     * @return
     * @throws Exception
     */
    public List<AppYbSignEntity> findYbSign(AppYbSignQvo qvo) throws Exception;

}
