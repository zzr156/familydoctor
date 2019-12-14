package com.ylz.bizDo.app.dao;

import com.ylz.bizDo.app.po.AppServePackage;
import com.ylz.bizDo.app.vo.AppServePackageQvo;

import java.util.List;

/**
 * 服务包dao
 * Created by zzl on 2017/8/11.
 */
public interface AppServePackageDao  {
    /**
     * 根据条件查询数据列表
     * @param qvo
     * @return
     * @throws Exception
     */
    public List<AppServePackage> findList(AppServePackageQvo qvo) throws Exception;

    /**
     * 组合查询服务内容
     * @param roleType
     * @param hospId
     * @param areaCode
     * @param type
     * @return
     * @throws Exception
     */
    public List<AppServePackage> findListByType(String roleType,String hospId,String areaCode,String type) throws Exception;
    /**
     * 查询最新编号
     * @return
     */
    public String findCode()throws Exception;

    public Boolean isReferencedByGroup(AppServePackage pk)throws Exception;

    /**
     * 根据服务编号查询服务内容
     * @param value
     * @return
     */
    public AppServePackage findByValue(String value)throws Exception;

    public List<AppServePackage> findListNQvo(AppServePackageQvo qvo) throws Exception;

    public List<AppServePackage> findPakege(String pkId,String fwType) throws Exception;

    /**
     * 根据服务内容id查询集合
     * @param pkIds
     * @return
     */
    public List<AppServePackage> findPakeges(String pkIds) throws Exception;

}
