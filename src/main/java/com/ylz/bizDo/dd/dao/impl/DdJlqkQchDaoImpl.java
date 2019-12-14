package com.ylz.bizDo.dd.dao.impl;

import com.ylz.bizDo.dd.dao.DdJlqkQchDao;
import com.ylz.bizDo.dd.po.DdJlqkQch;
import com.ylz.packaccede.allDo.SysDao;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service("ddJlqkQchDao")
@Transactional(rollbackForClassName={"Exception"})
public class DdJlqkQchDaoImpl  implements DdJlqkQchDao {

	@Autowired
	private SysDao sysDao;

	public DdJlqkQch findByYear(int year,int month,int day) throws Exception {
		return (DdJlqkQch) sysDao.getServiceDo().getSessionFactory().getCurrentSession()
				.createCriteria(DdJlqkQch.class)
				.add(Restrictions.eq("ddYear", year))
				.add(Restrictions.eq("ddMonth", month))
				.add(Restrictions.eq("ddDay", day))
				.uniqueResult();
	}
	
	public DdJlqkQch findByYear(int year,String type) throws Exception {
		return (DdJlqkQch) sysDao.getServiceDo().getSessionFactory().getCurrentSession()
				.createCriteria(DdJlqkQch.class)
				.add(Restrictions.eq("ddYear", year))
				.add(Restrictions.eq("ddType", type))
				.uniqueResult();
	}

}
