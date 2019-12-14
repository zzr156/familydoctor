package com.ylz.bizDo.cd.dao;


import com.ylz.bizDo.cd.entity.CdCodeEntity;
import com.ylz.bizDo.cd.po.CdCode;
import com.ylz.bizDo.cd.vo.CdCodeQvo;
import com.ylz.bizDo.jtapp.commonEntity.AppCodeEntity;
import com.ylz.bizDo.jtapp.commonEntity.AppGroupEntity;
import com.ylz.bizDo.jtapp.commonEntity.AppMeddleEntity;
import com.ylz.bizDo.jtapp.drVo.AppGroupVo;

import java.util.List;

public interface CdCodeDao{
	//根据group 状态查询基础数据
	public List<CdCode> findGroupList(String group,String state) throws Exception;
	//根据qvo 状态查询基础数据
	public List<CdCode> findGroupListQvo(CdCodeQvo qvo) throws Exception;
	//根据组名,值获取code数据
	public CdCode findCodeGroupValue(String group,String value) throws Exception;
	//根据组名获取code数据
	public CdCode findCodeGroupValue(String group) throws Exception;
	public List<CdCodeEntity> findCmmCodeGroup(String strJson) throws Exception;

    public List<CdCode> findGroupListTopSix(String groupFamilyrelation, String value) throws Exception;

	public List<AppGroupEntity> findAppGroup(AppGroupVo vo) throws Exception;

	public List<AppMeddleEntity> findMeddle(String group, String state) throws Exception;

	public List<AppMeddleEntity> findMeddlel(String group, String state) throws Exception;


	/**
	 * Dmao
	 * 查询该地区签约费用 及 医保交互
	 */
	public CdCode findSign(String code) throws Exception;

	/**
	 * 查询亲人关系
	 * @return
	 */
	public List<AppCodeEntity> findCodeFamily() throws Exception;

	public List<CdCode> findJsGroupListQvo(CdCodeQvo qvo) throws Exception;



}
