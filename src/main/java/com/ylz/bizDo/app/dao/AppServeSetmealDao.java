package com.ylz.bizDo.app.dao;

import java.util.List;
import java.util.Map;

import com.ylz.bizDo.app.po.AppServeSetmeal;
import com.ylz.bizDo.app.vo.AppServeSetmealQvo;
import com.ylz.bizDo.cd.po.CdUser;
import com.ylz.bizDo.jtapp.commonEntity.AppGrantInAidEntity;
import com.ylz.bizDo.jtapp.commonVo.AppCommQvo;
import com.ylz.bizDo.jtapp.drEntity.AppServeEntity;
import com.ylz.bizDo.jtapp.gaiRuiEntity.GaiRuiMealEntity;
import com.ylz.bizDo.jtapp.signSersetEntity.AppSignSerMealEntity;
import com.ylz.bizDo.jtapp.signSersetEntity.AppSignServeMealEntity;
import com.ylz.bizDo.jtapp.signSersetVo.AppSignSerQvo;

/**
 * Created by zzl on 2017/8/15.
 */
public interface AppServeSetmealDao {
    /**
     * 条件查询
     * @param qvo
     * @return
     * @throws Exception
     */
    public List<AppServeSetmeal> findList(AppServeSetmealQvo qvo) throws Exception;

    /**
     * 查询套餐信息
     * @return
     * @throws Exception
     */
    public List<AppSignSerMealEntity> findSerAllMeal(AppSignSerQvo qvo) throws Exception;

    /**
     * 查询最新编号
     * @return
     */
    public String findCode() throws Exception;

    /**
     * 根据医院id查询服务套餐
     * @param hospId
     * @return
     */
    public List<AppServeEntity> findSeverMeal(String hospId) throws Exception;

    /**
     * 导入套餐数据excel
     * @param map
     * @return
     */
    public String addImportExcelMeal(Map<Integer, String> map, CdUser user) throws Exception;

    /**
     * 根据服务包编号查询服务包
     * @param value
     * @return
     */
    public AppServeSetmeal findByValue(String value) throws Exception;

    /**
     * 根据ids查询多个服务包
     * @param ids
     * @return
     */
    public List<AppServeSetmeal> findListByIds(String ids) throws Exception;

    public List<AppSignServeMealEntity> findSignMeal(String ids) throws Exception;

    public List<AppServeSetmeal> findAllByNotGroup() throws Exception;

    /**
     * 根据服务人群、经济类型查询套餐财政补贴类型
     * @param qvo
     * @return
     */
    public List<AppGrantInAidEntity> findGrantInAidByMeal(AppCommQvo qvo) throws Exception;
    
    /**
	 * 由服务包总结出结果 
	 * 结果： Map<服务包ID， Map<服务人群ID， 服务内容(14项统计内容)&&&频次>>
	 * 
	 * @version 2018-05-07
	 * @author lyy
	 * @param list
	 * @return
	 */
 	public Map<String, Map<String, List<String>>> dealAppServeSetmeal(List<AppServeSetmeal> appServeSetmealList) throws Exception;
 	
 	/**
 	 * 如果有多个服务包叠加，则计算最后的服务内容和频次
 	 * 
 	 * @version 2018-05-08
 	 * @author lyy
 	 * @param packageId 服务包ID,多个用,隔开
 	 * @param resultMap 服务包结果
 	 * @return
 	 */
 	public Map<String, List<String>> countAppServeSetmeals(String packageId, Map<String, Map<String, List<String>>> resultMap) throws Exception;
 	
 	/**
 	 * 患者有多个服务类型,对应的服务项取频次最高项
 	 * 
 	 * @author lyy
 	 * @return
 	 */
 	public List<String> countAppServeObjects(Map<String, List<String>> map, String labelValue) throws Exception;

	/**
	 * 根据服务包查询下服务信息
	 * @param packId
	 * @return
	 * @throws Exception
	 */
 	public List<AppServeEntity> findSeverMealByIds(String packId) throws Exception;

	/**
	 * 根据服务包主键查询服务详细，返回单个
	 * @param packId
	 * @return
	 * @throws Exception
	 */
 	public AppServeEntity findOneSeverMealByIds(String packId) throws Exception;

	/**
	 * 根据服务包主键查询集合（多个服务包主键用“;”）
	 * @param pkId
	 * @return
	 * @throws Exception
	 */
 	public List<AppServeSetmeal> findMealByIds(String pkId) throws Exception;

	/**
	 * 根据机构查询服务包（盖瑞）
	 * @param hospId
	 * @return
	 * @throws Exception
	 */
 	public List<GaiRuiMealEntity> findListMealByHospId(String hospId) throws Exception;

	/**
	 * 根据服务包编码查询套餐id
	 * @param value
	 * @return
	 * @throws Exception
	 */
	public String findMealIdsByValue(String value) throws Exception;


}
