package com.ylz.packcommon.common.dao.impl;


import com.ylz.packcommon.common.bizDo.SpringHelper;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

/**
 * DAO基类
 * @date 2012/04/07
 * hzk
 */
@Repository("baseMultiDao")
public class BaseMultiDao
{


	public MultiMethod getOrclDao() {
		SessionFactory sessionFactoryOracle = (SessionFactory)SpringHelper.getBean("sessionFactoryOracle");
		return new MultiMethod(sessionFactoryOracle);
	}




}
