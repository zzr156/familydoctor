package com.ylz.bizDo.app.dao;

import com.ylz.bizDo.app.po.AppServeGroup;
import com.ylz.bizDo.app.po.AppServeObject;
import com.ylz.bizDo.app.vo.AppServeGroupQvo;
import com.ylz.bizDo.app.vo.AppServePackageQvo;
import com.ylz.bizDo.app.vo.AppServeSetmealQvo;

import java.util.List;

/**服务组合dao
 * Created by zzl on 2017/8/13.
 */
public interface AppServeGroupDao {
    /**
     * 初始查询
     * @param qvo
     * @return
     * @throws Exception
     */
    public List<AppServeGroup> findList(AppServeGroupQvo qvo) throws Exception;

    /**
     * 无分页查询
     * @param qvo
     * @return
     * @throws Exception
     */
    public List<AppServeGroup> findAllList(AppServeSetmealQvo qvo) throws Exception;

    /**
     * 套餐查询组合
     * @param roleType
     * @param hospId
     * @param areaCode
     * @return
     * @throws Exception
     */
    public List<AppServeGroup> findByGroup(String roleType,String hospId,String areaCode) throws Exception;

    /**
     * 查询最新编号
     * @return
     */
    public String findCode() throws Exception;

    public Boolean isReferencedByMeal(AppServeGroup pk) throws Exception;
    /**
     * 套餐查询组合
     * @param qvo
     * @return
     * @throws Exception
     */
    public List<AppServeGroup> findByGroup(AppServeSetmealQvo qvo) throws Exception;

    public AppServeGroup findByValue(String value) throws Exception;

    public List<AppServeGroup> findGroups(String groupIds) throws Exception;

    public List<AppServeObject> findListN(AppServePackageQvo qvo) throws Exception;
}
