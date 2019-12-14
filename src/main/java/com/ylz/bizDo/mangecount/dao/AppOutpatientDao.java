package com.ylz.bizDo.mangecount.dao;

import com.ylz.bizDo.app.entity.AppOutpatientCountEntity;
import com.ylz.bizDo.mangecount.vo.ResidentVo;

import java.util.List;
import java.util.Map;

/**
 * Created by lenovo on 2018/1/17.
 */
public interface AppOutpatientDao {


    /**
     * 福州 医保门诊，年度支出统计
     * @param qvo
     * @return
     */
    public Map<String,Object> findSignOutpatientTypeList(ResidentVo qvo) throws Exception;
}
