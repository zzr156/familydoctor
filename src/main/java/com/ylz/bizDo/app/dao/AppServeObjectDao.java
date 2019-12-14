package com.ylz.bizDo.app.dao;


import com.ylz.bizDo.app.po.AppServeObject;
import com.ylz.bizDo.app.vo.AppServeGroupQvo;
import com.ylz.bizDo.app.vo.AppServeObjectQvo;

import java.util.List;

/** 服务人群dao
 * Created by zzl on 2017/8/11.
 */
public interface AppServeObjectDao {
    /**
     * 条件查询数据列表
     * @param qvo
     * @return
     * @throws Exception
     */
    public List<AppServeObject> findList(AppServeObjectQvo qvo) throws Exception;

    /**
     * 权限查询服务人群
     * @return
     * @throws Exception
     */
    public List<AppServeObject> findAllList() throws  Exception;

    /**
     * 组合查询服务人群
     * @param roleType
     * @param hospId
     * @param areaCode
     * @return
     * @throws Exception
     */
    public List<AppServeObject> findByPeople(String roleType,String hospId,String areaCode) throws Exception;

    /**
     * 查询编号
     * @return
     */
    public String findCode() throws Exception;

    public Boolean isReferencedByGroup(AppServeObject pk) throws Exception;

    public List<AppServeObject> findByPeople(AppServeGroupQvo qvo) throws Exception;

    /**
     * 根据编号查询
     * @param value
     * @return
     */
    public AppServeObject findByValue(String value) throws Exception;
}
