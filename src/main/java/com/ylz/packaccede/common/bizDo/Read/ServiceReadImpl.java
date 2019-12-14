package com.ylz.packaccede.common.bizDo.Read;

import com.ylz.packaccede.common.bizDo.ServiceDo;
import com.ylz.packcommon.common.CommConditionVo;
import com.ylz.packcommon.common.bean.BasePO;
import com.ylz.packcommon.common.dao.IBaseDao;
import com.ylz.packcommon.common.exception.DaoException;
import com.ylz.packcommon.common.hql.HQuery;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

@Service("serviceReadDo")
@Transactional
public class ServiceReadImpl implements ServiceReadDo {
	String hqlcount;

	@Autowired
	private IBaseDao baseDao;

	@SuppressWarnings("rawtypes")
	@Override
	public Object find(Class poClass, java.io.Serializable pk) throws DaoException {
		try
		{
			return (BasePO) baseDao.getSessionFactoryRead().getCurrentSession().get(poClass, pk);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new DaoException(this.getClass(), e);
		}
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List loadByPk(Class clazz, String keyName, Object keyValue)throws DaoException {
		try
		{
			return baseDao.getSessionFactoryRead().getCurrentSession().createCriteria(clazz).add(Restrictions.eq(keyName,keyValue)).list();
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new DaoException(this.getClass(), e);

		}
	}
	@SuppressWarnings("rawtypes")
	@Override
	public List loadByPk(Class clazz, String keyName, Object keyValue,CommConditionVo qvo) throws DaoException {
		try
		{
			int count = ((Number)baseDao.getSessionFactoryRead().
					getCurrentSession().createCriteria(clazz).
					add(Restrictions.eq(keyName,keyValue))
					.setProjection(Projections.rowCount())
					.uniqueResult()).intValue();

			qvo.setItemCount(count);

			return baseDao.getSessionFactoryRead().
					getCurrentSession().createCriteria(clazz).
					add(Restrictions.eq(keyName,keyValue))
					.setFirstResult((qvo.getPageStartNo() - 1) * qvo.getPageSize())
					.setMaxResults(qvo.getPageSize())
					.list();
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new DaoException(this.getClass(), e);

		}
	}

	public List<Map> findSql(String sql)
    {
        Query query = baseDao.getSessionFactoryRead().getCurrentSession().createSQLQuery(sql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        return query.list();
    }

    public List<Map> findSqlMap(String sql,Map<String,Object> map)
    {
        Query query = baseDao.getSessionFactoryRead().getCurrentSession().createSQLQuery(sql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).setProperties(map);
        return query.list();
    }

	public List<Map> findSqlMap(String sql,Map<String,Object> map,CommConditionVo qvo)
	{
		String sqlCount="SELECT count(1) FROM ("+sql+")  ffffffffff";
		Query query2 = baseDao.getSessionFactoryRead().getCurrentSession().createSQLQuery(sqlCount).setProperties(map);
		Number summoney= (Number) query2.uniqueResult();
		int count=summoney.intValue();
		qvo.setItemCount(count);
		if(count >0){
			Query query = baseDao.getSessionFactoryRead().getCurrentSession().createSQLQuery(sql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).setProperties(map);
			query.setFirstResult((qvo.getPageStartNo() - 1) * qvo.getPageSize());
			query.setMaxResults(qvo.getPageSize());
			List<Map> ls=query.list();
			return ls;
		}
		return null;
	}

    public List findSqlMap(String sql,Map<String,Object> map,Class clazz)
    {
        Query query = baseDao.getSessionFactoryRead().getCurrentSession().createSQLQuery(sql).addEntity(clazz).setProperties(map);
        return query.list();
    }

    public List findSqlMap(String sql,Map<String,Object> map,Class clazz,int first,int max)
    {
        Query query = baseDao.getSessionFactoryRead().getCurrentSession().createSQLQuery(sql).addEntity(clazz).setProperties(map);
        query.setFirstResult(first);
        query.setMaxResults(max);
        return query.list();
    }

    public List findSqlMap(String sql,Map<String,Object> map,Class clazz,CommConditionVo qvo)
    {
    	String sqlCount="SELECT count(1) FROM ("+sql+")  ffffffffff";
        Query query2 = baseDao.getSessionFactoryRead().getCurrentSession().createSQLQuery(sqlCount).setProperties(map);
        Number summoney= (Number) query2.uniqueResult();
        int count=summoney.intValue();
        qvo.setItemCount(count);
        if(count >0){
        	  Query query = baseDao.getSessionFactoryRead().getCurrentSession().createSQLQuery(sql).addEntity(clazz).setProperties(map);
              query.setFirstResult((qvo.getPageStartNo() - 1) * qvo.getPageSize());
              query.setMaxResults(qvo.getPageSize());
              List<Map> ls=query.list();
              return ls;
        }
        return null;
    }

    public int gteSqlCount(String sql,Map<String,Object> map)
    {
        Query query = baseDao.getSessionFactoryRead().getCurrentSession().createSQLQuery(sql).setProperties(map);
        Number summoney= (Number) query.uniqueResult();
        return summoney.intValue();
    }

	@Override
	public int update(String sql) {
		Query query = baseDao.getSessionFactoryRead().getCurrentSession().createSQLQuery(sql);
		return query.executeUpdate();
	}
    public int update(String sql,Map<String, Object> map){
        Query query = baseDao.getSessionFactoryRead().getCurrentSession().createSQLQuery(sql).setProperties(map);
        return query.executeUpdate();
    }
	@Override
	public void add(String sql) {
		this.baseDao.getSessionFactoryRead().getCurrentSession().createSQLQuery(sql).executeUpdate();
		
	}

    public Object findSqlObject(String sql)
    {
        Query query = this.baseDao.getSessionFactoryRead().getCurrentSession().createSQLQuery(sql);
        return query.uniqueResult();
    }
    
    public Object findSqlObject(String sql,Map<String, Object> map)
    {
        Query query = this.baseDao.getSessionFactoryRead().getCurrentSession().createSQLQuery(sql).setProperties(map);
        return query.uniqueResult();
    }
    
    public List findSqlList(String sql,Class clazz)
    {
        Query query = this.baseDao.getSessionFactoryRead().getCurrentSession().createSQLQuery(sql).addEntity(clazz);
        return query.list();
    }
    
    public List findCachSqlMap(String sql,Map<String,Object> map,Class clazz)
    {
        Query query = baseDao.getSessionFactoryRead().getCurrentSession().createSQLQuery(sql).addEntity(clazz).setProperties(map);
        return query.setCacheable(true).list();
    }
    @Override
    public List findSqlMapRVo(String sql, Map<String, Object> map, Class clazz) {
        Query query = this.baseDao.getSessionFactoryRead().getCurrentSession().createSQLQuery(sql).setResultTransformer(Transformers.aliasToBean(clazz)).setProperties(map);
        return query.list();
    }

    @Override
    public List findSqlMapRVo(String sql, Map<String, Object> map, Class clazz,
                              CommConditionVo qvo) {
    	String sqlCount ="SELECT count(1) FROM ("+sql+")  ffffffffff";
        Query query2 = this.baseDao.getSessionFactoryRead().getCurrentSession().createSQLQuery(sqlCount).setProperties(map);
        Number summoney= (Number) query2.uniqueResult();
        int count=summoney.intValue();
        qvo.setItemCount(count);
        if(count >0){
        	 Query query = this.baseDao.getSessionFactoryRead().getCurrentSession().createSQLQuery(sql).setResultTransformer(Transformers.aliasToBean(clazz)).setProperties(map);
             query.setFirstResult((qvo.getPageStartNo() - 1) * qvo.getPageSize());
             query.setMaxResults(qvo.getPageSize());
             List<Map> ls=query.list();
             return ls;
        }
        
        
        return null;
    }
    
    public List findSqlMapArray(String sql,Map<String,Object> map,Class clazz,String[] in)
   	{
   		Query query = baseDao.getSessionFactoryRead().getCurrentSession().createSQLQuery(sql).addEntity(clazz).setProperties(map).setParameterList("in", in);
   		return query.list();
   	}

   	public int findCount(String sql,Map<String,Object> map){
		Query query2 = this.baseDao.getSessionFactoryRead().getCurrentSession().createSQLQuery(sql).setProperties(map);
		Number summoney= (Number) query2.uniqueResult();
		if(summoney != null){
			int count=summoney.intValue();
			return count;
		}else{
			return 0;
		}

	}

	public int findCachCount(String sql,Map<String,Object> map){
		Query query2 = this.baseDao.getSessionFactoryRead().getCurrentSession().createSQLQuery(sql).setProperties(map);
		Number summoney= (Number) query2.setCacheable(true).uniqueResult();
		int count=summoney.intValue();
		return count;
	}

	//Hql操作
	public List findHqlMap(String hql) throws Exception
	{
		Query query = baseDao.getSessionFactoryRead().getCurrentSession().createQuery(hql);
		return query.list();
	}

	public List findHqlMap(String hql,Map<String,Object> map) throws Exception
	{
		Query query = baseDao.getSessionFactoryRead().getCurrentSession().createQuery(hql).setProperties(map);
		return query.list();
	}

	public  Object findHqlOne(String hql,Map<String,Object> map,Class clazz) throws Exception
	{
		Query query = this.baseDao.getSessionFactoryRead().getCurrentSession().createQuery(hql).setResultTransformer(Transformers.aliasToBean(clazz)).setProperties(map);
		return query.uniqueResult();
	}

	@Override
	public Object findHqlOne(String hql, Map<String, Object> map) throws Exception {
		Query query = this.baseDao.getSessionFactoryRead().getCurrentSession().createQuery(hql).setProperties(map);
		return query.uniqueResult();
	}

	public List findHqlMap(String hql, Map<String,Object> map, CommConditionVo qvo) throws Exception
	{
		if (hql.startsWith("select") || hql.startsWith("SELECT")) {
			if(hql.indexOf("FROM")==-1){
				hqlcount = "SELECT count(1) " + hql.substring(hql.indexOf("from"));
			}else {
				hqlcount = "SELECT count(1) " + hql.substring(hql.indexOf("FROM"));
			}
		}else {
			hqlcount = "SELECT count(1) " + hql;
		}
		Query query2 = baseDao.getSessionFactoryRead().getCurrentSession().createQuery(hqlcount).setProperties(map);
		Number summoney= (Number) query2.uniqueResult();
		int count=summoney.intValue();
		qvo.setItemCount(count);
		if(count >0){
			Query query = baseDao.getSessionFactoryRead().getCurrentSession().createQuery(hql).setProperties(map);
			query.setFirstResult((qvo.getPageStartNo() - 1) * qvo.getPageSize());
			query.setMaxResults(qvo.getPageSize());
			List ls=query.list();
			return ls;
		}
		return null;
	}


	public List findHqlMapRVo(String hql, Map<String, Object> map, Class clazz) throws Exception{
		Query query = this.baseDao.getSessionFactoryRead().getCurrentSession().createQuery(hql).setResultTransformer(Transformers.aliasToBean(clazz)).setProperties(map);
		return query.list();
	}

	public List findHqlMapRVo(String hql, Map<String, Object> map, Class clazz, CommConditionVo qvo) throws Exception{
		if (hql.startsWith("select") || hql.startsWith("SELECT")) {
			if(hql.indexOf("FROM")==-1){
				hqlcount = "SELECT count(1) " + hql.substring(hql.indexOf("from"));
			}else {
				hqlcount = "SELECT count(1) " + hql.substring(hql.indexOf("FROM"));
			}
		}else {
			hqlcount = "SELECT count(1) " + hql;
		}
		Query query2 = baseDao.getSessionFactoryRead().getCurrentSession().createQuery(hqlcount).setProperties(map);
		Number summoney= (Number) query2.uniqueResult();
		int count=summoney.intValue();
		qvo.setItemCount(count);
		if(count >0){
			Query query = this.baseDao.getSessionFactoryRead().getCurrentSession().createQuery(hql).setResultTransformer(Transformers.aliasToBean(clazz)).setProperties(map);
			query.setFirstResult((qvo.getPageStartNo() - 1) * qvo.getPageSize());
			query.setMaxResults(qvo.getPageSize());
			List ls=query.list();
			return ls;
		}
		return null;
	}

	/**
	 * hql查询count使用
	 * @param hql
	 * @param map
	 * @return
	 */
	public int findHqlCount(String hql,Map<String,Object> map) throws Exception{
		Query query2 = this.baseDao.getSessionFactoryRead().getCurrentSession().createQuery(hql).setProperties(map);
		Number summoney= (Number) query2.uniqueResult();
		int count=summoney.intValue();
		return count;
	}

	public String getSequence(String tabel){
		String sql="SELECT "+tabel+".NEXTVAL FROM DUAL";
		Query query = this.baseDao.getSessionFactoryRead().getCurrentSession().createSQLQuery(sql);
		Number summoney= (Number) query.uniqueResult();
		return String.format(String.valueOf(summoney.longValue()));
	}

	public String getSequence(String tabel,int length){
		String sql="SELECT "+tabel+".NEXTVAL FROM DUAL";
		Query query = this.baseDao.getSessionFactoryRead().getCurrentSession().createSQLQuery(sql);
		Number summoney= (Number) query.uniqueResult();
		return String.format("%0"+length+"d",summoney.longValue());
	}

	public List findCachHqlMap(String sql,Map<String,Object> map,Class clazz) throws Exception
	{
		Query query = baseDao.getSessionFactoryRead().getCurrentSession().createQuery(sql).setResultTransformer(Transformers.aliasToBean(clazz)).setProperties(map);
		return query.setCacheable(true).list();
	}

	public List findCachHqlMap(String sql,Map<String,Object> map) throws Exception
	{
		Query query = baseDao.getSessionFactoryRead().getCurrentSession().createQuery(sql).setProperties(map);
		return query.setCacheable(true).list();
	}

	public List findCachHqlMap(String hql,Map<String,Object> map,CommConditionVo qvo) throws Exception
	{
		if (hql.startsWith("select") || hql.startsWith("SELECT")) {
			if(hql.indexOf("FROM")==-1){
				hqlcount = "SELECT count(1) " + hql.substring(hql.indexOf("from"));
			}else {
				hqlcount = "SELECT count(1) " + hql.substring(hql.indexOf("FROM"));
			}
		}else {
			hqlcount = "SELECT count(1) " + hql;
		}
		Query query2 = baseDao.getSessionFactoryRead().getCurrentSession().createQuery(hqlcount).setProperties(map);
		Number summoney= (Number) query2.uniqueResult();
		int count=summoney.intValue();
		qvo.setItemCount(count);
		if(count >0){
			Query query = baseDao.getSessionFactoryRead().getCurrentSession().createQuery(hql).setProperties(map);
			query.setFirstResult((qvo.getPageStartNo() - 1) * qvo.getPageSize());
			query.setMaxResults(qvo.getPageSize());
			List ls=query.setCacheable(true).list();
			return ls;
		}
		return null;
	}



}
