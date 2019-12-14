package com.ylz.bizDo.app.dao;

import com.ylz.bizDo.app.po.AppWorkType;
import com.ylz.bizDo.app.vo.AppWorkTypeQvo;

import java.util.List;

/**
 * Created by zzl on 2017/8/17.
 */
public interface AppWorkTypeDao  {
    /**
     * 初始查询
     * @param qvo
     * @return
     * @throws Exception
     */
    public List<AppWorkType> findList(AppWorkTypeQvo qvo) throws Exception;

    public List<AppWorkType> findAllList() throws Exception ;
}
