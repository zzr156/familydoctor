package com.ylz.packaccede.common.bizDo;

import com.ylz.packcommon.common.CommConditionVo;
import com.ylz.packcommon.common.bean.BasePO;
import com.ylz.packcommon.common.dao.IBaseDao;
import com.ylz.packcommon.common.exception.DaoException;
import com.ylz.packcommon.common.hql.HQuery;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

@Service("serviceDo")
@Transactional
public class ServiceImpl implements ServiceDo {
	String hqlcount;
	String sqlCount;
	@Autowired
	private IBaseDao baseDao;
	
	public IBaseDao getBaseDao() {
		return baseDao;
	}

	public void setBaseDao(IBaseDao baseDao) {
		this.baseDao = baseDao;
	}

    @Override
    public SessionFactory getSessionFactory() {
        return baseDao.getSessionFactory();
    }

    @Override
    public Object removePoFormSession(Object o) {
        return baseDao.removePoFormSession(o);
    }

	@Override
	public void delete(Object oto) throws DaoException {
		baseDao.delete(oto);
	}
	public void delete(Class poClass, java.io.Serializable pk) throws DaoException {
		baseDao.delete(baseDao.findObject(poClass, pk));
	}

	@SuppressWarnings("rawtypes")
	@Override
	public void deleteList(Class clazz, Serializable[] id) throws DaoException {
		baseDao.deleteList(clazz, id);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public void deleteAll(Class clazz) throws DaoException {
		baseDao.deleteAll(clazz);
	}
	
	@Override
	public void saveUpdate(BasePO oto) throws DaoException {
		// TODO Auto-generated method stub
		oto.setHsUpdateTime(Calendar.getInstance());
		baseDao.saveUpdate(oto);
	}

    @Override
	public void add(BasePO oto) throws DaoException {
		oto.setHsCreateDate(Calendar.getInstance());
		oto.setHsUpdateTime(Calendar.getInstance());
		baseDao.add(oto);
	}

	@Override
	public void modify(BasePO oto) throws DaoException {
		oto.setHsUpdateTime(Calendar.getInstance());
		baseDao.modify(oto);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List findAll(Class poClass) throws DaoException {
		return baseDao.findAll(poClass);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Object find(Class poClass, java.io.Serializable pk) throws DaoException {
		return baseDao.findObject(poClass, pk);	
	}


	@SuppressWarnings("rawtypes")
	@Override
	public int getAllCount(Class poClass) throws DaoException {
		return baseDao.getAllCount(poClass);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List findHquery(HQuery _query) throws DaoException {
		return baseDao.findHquery(_query);
	}

	@Override
	public int getAllCountHquery(HQuery _query) throws DaoException {
		return baseDao.getAllCountHquery(_query);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List loadByPk(Class clazz, String keyName, Object keyValue)throws DaoException {
		return baseDao.loadByPk(clazz, keyName, keyValue);
	}
	@SuppressWarnings("rawtypes")
	@Override
	public List loadByPk(Class clazz, String keyName, Object keyValue,CommConditionVo qvo) throws DaoException {
		return baseDao.loadByPk(clazz, keyName, keyValue, qvo);
	}
	
	@Override
	public CommConditionVo getNewQvo(CommConditionVo qvo, HQuery _query) {
		return baseDao.getNewQvo(qvo, _query);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List findAll(Class poClass, CommConditionVo qvo)throws DaoException {
		return baseDao.findAll(poClass, qvo);
	}
	
	public List<Map> findSql(String sql)
    {
        Query query = baseDao.getSessionFactory().getCurrentSession().createSQLQuery(sql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        return query.list();
    }

    public List<Map> findSqlMap(String sql,Map<String,Object> map)
    {
        Query query = baseDao.getSessionFactory().getCurrentSession().createSQLQuery(sql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).setProperties(map);
        return query.list();
    }

	public List<Map> findSqlMap(String sql,Map<String,Object> map,CommConditionVo qvo)
	{
		String sqlCount="SELECT count(1) FROM ("+sql+")  ffffffffff";
		Query query2 = baseDao.getSessionFactory().getCurrentSession().createSQLQuery(sqlCount).setProperties(map);
		Number summoney= (Number) query2.uniqueResult();
		int count=summoney.intValue();
		qvo.setItemCount(count);
		if(count >0){
			Query query = baseDao.getSessionFactory().getCurrentSession().createSQLQuery(sql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).setProperties(map);
			query.setFirstResult((qvo.getPageStartNo() - 1) * qvo.getPageSize());
			query.setMaxResults(qvo.getPageSize());
			List<Map> ls=query.list();
			return ls;
		}
		return null;
	}

    public List findSqlMap(String sql,Map<String,Object> map,Class clazz)
    {
        Query query = baseDao.getSessionFactory().getCurrentSession().createSQLQuery(sql).addEntity(clazz).setProperties(map);
        return query.list();
    }

    public List findSqlMap(String sql,Map<String,Object> map,Class clazz,int first,int max)
    {
        Query query = baseDao.getSessionFactory().getCurrentSession().createSQLQuery(sql).addEntity(clazz).setProperties(map);
        query.setFirstResult(first);
        query.setMaxResults(max);
        return query.list();
    }

    public List findSqlMap(String sql,Map<String,Object> map,Class clazz,CommConditionVo qvo)
    {
    	String sqlCount="SELECT count(1) FROM ("+sql+")  ffffffffff";
        Query query2 = baseDao.getSessionFactory().getCurrentSession().createSQLQuery(sqlCount).setProperties(map);
        Number summoney= (Number) query2.uniqueResult();
        int count=summoney.intValue();
        qvo.setItemCount(count);
        if(count >0){
        	  Query query = baseDao.getSessionFactory().getCurrentSession().createSQLQuery(sql).addEntity(clazz).setProperties(map);
              query.setFirstResult((qvo.getPageStartNo() - 1) * qvo.getPageSize());
              query.setMaxResults(qvo.getPageSize());
              List<Map> ls=query.list();
              return ls;
        }
        return null;
    }

    public int gteSqlCount(String sql,Map<String,Object> map)
    {
        Query query = baseDao.getSessionFactory().getCurrentSession().createSQLQuery(sql).setProperties(map);
        Number summoney= (Number) query.uniqueResult();
        return summoney.intValue();
    }

	@Override
	public int update(String sql) {
		Query query = baseDao.getSessionFactory().getCurrentSession().createSQLQuery(sql);
		return query.executeUpdate();
	}
    public int update(String sql,Map<String, Object> map){
        Query query = baseDao.getSessionFactory().getCurrentSession().createSQLQuery(sql).setProperties(map);
        return query.executeUpdate();
    }
	@Override
	public void add(String sql) {
		this.baseDao.getSessionFactory().getCurrentSession().createSQLQuery(sql).executeUpdate();
		
	}

    public Object findSqlObject(String sql)
    {
        Query query = this.baseDao.getSessionFactory().getCurrentSession().createSQLQuery(sql);
        return query.uniqueResult();
    }
    
    public Object findSqlObject(String sql,Map<String, Object> map)
    {
        Query query = this.baseDao.getSessionFactory().getCurrentSession().createSQLQuery(sql).setProperties(map);
        return query.uniqueResult();
    }
    
    public List findSqlList(String sql,Class clazz)
    {
        Query query = this.baseDao.getSessionFactory().getCurrentSession().createSQLQuery(sql).addEntity(clazz);
        return query.list();
    }
    
    public List findCachSqlMap(String sql,Map<String,Object> map,Class clazz)
    {
        Query query = baseDao.getSessionFactory().getCurrentSession().createSQLQuery(sql).addEntity(clazz).setProperties(map);
        return query.setCacheable(true).list();
    }
    @Override
    public List findSqlMapRVo(String sql, Map<String, Object> map, Class clazz) {
        Query query = this.baseDao.getSessionFactory().getCurrentSession().createSQLQuery(sql).setResultTransformer(Transformers.aliasToBean(clazz)).setProperties(map);
        return query.list();
    }

    @Override
    public List findSqlMapRVo(String sql, Map<String, Object> map, Class clazz,
                              CommConditionVo qvo) {
    	String sqlCount ="SELECT count(1) FROM ("+sql+")  ffffffffff";
        Query query2 = this.baseDao.getSessionFactory().getCurrentSession().createSQLQuery(sqlCount).setProperties(map);
        Number summoney= (Number) query2.uniqueResult();
        int count=summoney.intValue();
        qvo.setItemCount(count);
        if(count >0){
        	 Query query = this.baseDao.getSessionFactory().getCurrentSession().createSQLQuery(sql).setResultTransformer(Transformers.aliasToBean(clazz)).setProperties(map);
             query.setFirstResult((qvo.getPageStartNo() - 1) * qvo.getPageSize());
             query.setMaxResults(qvo.getPageSize());
             List<Map> ls=query.list();
             return ls;
        }
        
        
        return null;
    }


	public List findSqlMapRVo(String sql,String sqlCount ,Map<String, Object> map, Class clazz, CommConditionVo qvo){
		String sqlCountAll ="SELECT count(1) FROM ("+sqlCount+")  ffffffffff";
		Query query2 = this.baseDao.getSessionFactory().getCurrentSession().createSQLQuery(sqlCountAll).setProperties(map);
		Number summoney= (Number) query2.uniqueResult();
		int count=summoney.intValue();
		qvo.setItemCount(count);
		if(count >0){
			Query query = this.baseDao.getSessionFactory().getCurrentSession().createSQLQuery(sql).setResultTransformer(Transformers.aliasToBean(clazz)).setProperties(map);
			query.setFirstResult((qvo.getPageStartNo() - 1) * qvo.getPageSize());
			query.setMaxResults(qvo.getPageSize());
			List<Map> ls=query.list();
			return ls;
		}
		return null;
	}
    
    public List findSqlMapArray(String sql,Map<String,Object> map,Class clazz,String[] in)
   	{
   		Query query = baseDao.getSessionFactory().getCurrentSession().createSQLQuery(sql).addEntity(clazz).setProperties(map).setParameterList("in", in);
   		return query.list();
   	}

   	public int findCount(String sql,Map<String,Object> map){
		Query query2 = this.baseDao.getSessionFactory().getCurrentSession().createSQLQuery(sql).setProperties(map);
		Number summoney= (Number) query2.uniqueResult();
		if(summoney != null){
			int count=summoney.intValue();
			return count;
		}else{
			return 0;
		}

	}

	public int findCachCount(String sql,Map<String,Object> map){
		Query query2 = this.baseDao.getSessionFactory().getCurrentSession().createSQLQuery(sql).setProperties(map);
		Number summoney= (Number) query2.setCacheable(true).uniqueResult();
		int count=summoney.intValue();
		return count;
	}

	//Hql操作
	public List findHqlMap(String hql) throws Exception
	{
		Query query = baseDao.getSessionFactory().getCurrentSession().createQuery(hql);
		return query.list();
	}

	public List findHqlMap(String hql,Map<String,Object> map) throws Exception
	{
		Query query = baseDao.getSessionFactory().getCurrentSession().createQuery(hql).setProperties(map);
		return query.list();
	}

	public  Object findHqlOne(String hql,Map<String,Object> map,Class clazz) throws Exception
	{
		Query query = this.baseDao.getSessionFactory().getCurrentSession().createQuery(hql).setResultTransformer(Transformers.aliasToBean(clazz)).setProperties(map);
		return query.uniqueResult();
	}

	@Override
	public Object findHqlOne(String hql, Map<String, Object> map) throws Exception {
		Query query = this.baseDao.getSessionFactory().getCurrentSession().createQuery(hql).setProperties(map);
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
		Query query2 = baseDao.getSessionFactory().getCurrentSession().createQuery(hqlcount).setProperties(map);
		Number summoney= (Number) query2.uniqueResult();
		int count=summoney.intValue();
		qvo.setItemCount(count);
		if(count >0){
			Query query = baseDao.getSessionFactory().getCurrentSession().createQuery(hql).setProperties(map);
			query.setFirstResult((qvo.getPageStartNo() - 1) * qvo.getPageSize());
			query.setMaxResults(qvo.getPageSize());
			List ls=query.list();
			return ls;
		}
		return null;
	}


	public List findHqlMapRVo(String hql, Map<String, Object> map, Class clazz) throws Exception{
		Query query = this.baseDao.getSessionFactory().getCurrentSession().createQuery(hql).setResultTransformer(Transformers.aliasToBean(clazz)).setProperties(map);
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
		Query query2 = baseDao.getSessionFactory().getCurrentSession().createQuery(hqlcount).setProperties(map);
		Number summoney= (Number) query2.uniqueResult();
		int count=summoney.intValue();
		qvo.setItemCount(count);
		if(count >0){
			Query query = this.baseDao.getSessionFactory().getCurrentSession().createQuery(hql).setResultTransformer(Transformers.aliasToBean(clazz)).setProperties(map);
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
		Query query2 = this.baseDao.getSessionFactory().getCurrentSession().createQuery(hql).setProperties(map);
		Number summoney= (Number) query2.uniqueResult();
		int count=summoney.intValue();
		return count;
	}

	/**
	 * 根据sql删除
	 * @param sql
	 * @return
	 */
	public int deleteSql(String sql){
		Query query = this.baseDao.getSessionFactory().getCurrentSession().createSQLQuery(sql);
		int i = query.executeUpdate();
		return i;
	}

	public String getSequence(String tabel){
		String sql="SELECT "+tabel+".NEXTVAL FROM DUAL";
		Query query = this.baseDao.getSessionFactory().getCurrentSession().createSQLQuery(sql);
		Number summoney= (Number) query.uniqueResult();
		return String.format(String.valueOf(summoney.longValue()));
	}

	public String getSequence(String tabel,int length){
		String sql="SELECT "+tabel+".NEXTVAL FROM DUAL";
		Query query = this.baseDao.getSessionFactory().getCurrentSession().createSQLQuery(sql);
		Number summoney= (Number) query.uniqueResult();
		return String.format("%0"+length+"d",summoney.longValue());
	}

	public List findCachHqlMap(String sql,Map<String,Object> map,Class clazz) throws Exception
	{
		Query query = baseDao.getSessionFactory().getCurrentSession().createQuery(sql).setResultTransformer(Transformers.aliasToBean(clazz)).setProperties(map);
		return query.setCacheable(true).list();
	}

	public List findCachHqlMap(String sql,Map<String,Object> map) throws Exception
	{
		Query query = baseDao.getSessionFactory().getCurrentSession().createQuery(sql).setProperties(map);
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
		Query query2 = baseDao.getSessionFactory().getCurrentSession().createQuery(hqlcount).setProperties(map);
		Number summoney= (Number) query2.uniqueResult();
		int count=summoney.intValue();
		qvo.setItemCount(count);
		if(count >0){
			Query query = baseDao.getSessionFactory().getCurrentSession().createQuery(hql).setProperties(map);
			query.setFirstResult((qvo.getPageStartNo() - 1) * qvo.getPageSize());
			query.setMaxResults(qvo.getPageSize());
			List ls=query.setCacheable(true).list();
			return ls;
		}
		return null;
	}



}
