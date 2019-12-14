package com.ylz.bizDo.app.dao;

import com.ylz.bizDo.app.entity.AppMenuEntity;
import com.ylz.bizDo.app.po.AppMenu;
import com.ylz.bizDo.app.vo.AppMenuQvo;

import java.util.List;

/**
 * Created by asus on 2017/08/03.
 */
public interface AppMenuDao {
    public List<AppMenu> findListQvo(AppMenuQvo qvo) throws Exception;

    public List<AppMenu> findMenulist(AppMenuQvo qvo) throws Exception;

    public List<AppMenuEntity> findMenuEntity(AppMenuQvo qvo) throws Exception;
}
