package com.ylz.bizDo.app.dao;

import com.ylz.bizDo.app.po.AppRole;
import com.ylz.bizDo.app.vo.AppRoleQvo;

import java.util.List;

/**
 * Created by zzl on 2017/6/14.
 */
public interface AppRoleDao  {
    public List<AppRole> findListQvo(AppRoleQvo qvo) throws Exception;
}
