package com.ylz.bizDo.app.dao;

import com.ylz.bizDo.app.po.AppResidentManage;
import com.ylz.bizDo.app.vo.AppResidentManageQvo;

import java.util.List;

/**
 * Created by zzl on 2017/6/14.
 */
public interface AppResidentManageDao  {
    //分页查询
    public List<AppResidentManage> findAll(AppResidentManageQvo qvo) throws Exception;
}
