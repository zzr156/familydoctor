package com.ylz.bizDo.cd.dao;

import com.ylz.bizDo.cd.po.CdAddressPeople;
import com.ylz.bizDo.cd.vo.CdAddressPeopleQvo;
import com.ylz.bizDo.cd.vo.CdAddressPeopleVo;
import com.ylz.bizDo.jtapp.drEntity.AppDrTargetEntity;
import com.ylz.bizDo.jtapp.drVo.AppDrTargetQvo;

import java.util.List;

/**
 * Created by asus on 2017/6/26.
 */
public interface CdAddressPeopleDao {
    //查询列表
    public List<CdAddressPeople> findList(CdAddressPeopleQvo qvo) throws Exception;
    //根据年度和地区编号查询数据
    public CdAddressPeople findByYearCode(String areaCode,String year) throws Exception;

    /**
     * 根据条件查询指标记录
     * @param qvo
     * @return
     * @throws Exception
     */
    public AppDrTargetEntity findByQvo(AppDrTargetQvo qvo) throws Exception;

    /**
     * 修改指标
     * @param qvo
     * @return
     * @throws Exception
     */
    public AppDrTargetEntity modifyTarget(AppDrTargetQvo qvo) throws Exception;

    /**
     * 根据区域code查询参考人数
     * @param code
     * @return
     */
    public CdAddressPeople findByCode(String code) throws Exception;

    /**
     * 根据区域code查询上一级两率
     * @param code
     * @return
     */
    public CdAddressPeople findUpByCode(String code) throws Exception;

    public List<CdAddressPeople> findByCacheList(String code,String year) throws Exception;

    public CdAddressPeople findByCacheCode(String code,String year) throws Exception;

    /**
     * 根据code统计下级指标
     * @param code
     * @param year
     * @return
     */
    public CdAddressPeopleVo findByLevelCode(String code, String year,String level) throws Exception;
}
