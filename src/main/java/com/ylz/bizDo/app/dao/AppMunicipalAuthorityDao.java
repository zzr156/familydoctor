package com.ylz.bizDo.app.dao;

import com.ylz.bizDo.app.po.AppMunicipalAuthority;

/**
 * Created by asus on 2017/08/14.
 */
public interface AppMunicipalAuthorityDao {
    public AppMunicipalAuthority findByAreaCode(String cityCode) throws Exception;
}
