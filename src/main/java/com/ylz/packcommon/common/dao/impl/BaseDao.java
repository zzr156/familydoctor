package com.ylz.packcommon.common.dao.impl;


import com.ylz.packcommon.common.CommConditionVo;
import com.ylz.packcommon.common.bean.BasePO;
import com.ylz.packcommon.common.dao.IBaseDao;
import com.ylz.packcommon.common.exception.DaoException;
import com.ylz.packcommon.common.hql.HQuery;
import com.ylz.packcommon.common.hql.HqlDataBoundingType;
import com.ylz.packcommon.common.hql.Paras;
import com.ylz.packcommon.common.util.ExtendDate;
import org.hibernate.FlushMode;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.annotations.FlushModeType;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.sql.Types;
import java.util.List;

/**
 * DAO基类
 * @date 2012/04/07
 * hzk
 */
@Repository("baseDao")
public class BaseDao implements IBaseDao
{
	@Autowired
	private SessionFactory sessionFactory;
	@Autowired
	private SessionFactory sessionFactoryRead;

	public SessionFactory getSessionFactoryRead() {
		return sessionFactoryRead;
	}

	public void setSessionFactoryRead(SessionFactory sessionFactoryRead) {
		this.sessionFactoryRead = sessionFactoryRead;
	}

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	public Object removePoFormSession(Object o)
	{
		sessionFactory.getCurrentSession().evict(o);
		return o;
	}

	/**
	 * 保存实例
	 */
	public void add(Object s) throws DaoException {
		try {
			sessionFactory.getCurrentSession().save(s);
		} catch (Exception e) {
			e.printStackTrace();
			throw new DaoException(this.getClass(), e);
		}
	}

	/**
	 * 修改实例
	 */
	public void modify(Object s) throws DaoException {
		try {
			sessionFactory.getCurrentSession().update(s);
			sessionFactory.getCurrentSession().setFlushMode(FlushMode.COMMIT);
		} catch (Exception e) {
			e.printStackTrace();
			throw new DaoException(this.getClass(), e);
		}
	}

	/**
	 * 保存或修改实例
	 */
	public void saveUpdate(Object s) throws DaoException {
		try {
			sessionFactory.getCurrentSession().saveOrUpdate(s);
		} catch (Exception e) {
			e.printStackTrace();
			throw new DaoException(this.getClass(), e);
		}
	}

	/**
	 * 删除实例
	 */
	public void delete(Object s) throws DaoException {
		try {
			sessionFactory.getCurrentSession().delete(s);
		} catch (Exception e) {
			e.printStackTrace();
			throw new DaoException(this.getClass(), e);
		}
	}
	
	/**
	 * 删除多实例
	 */
	@SuppressWarnings("rawtypes")
	public void deleteList(Class clazz, Serializable[] id) throws DaoException {
		try {
	        for (int i = 0; i < id.length; i++) { 
	            this.delete(this.findObject(clazz, id[i]));      
	        }
		} catch (Exception e) {
			e.printStackTrace();
			throw new DaoException(this.getClass(), e);
		}
	}
	
	/**
	 * 删除所有
	 */
	@SuppressWarnings("rawtypes")
	public void deleteAll(Class clazz)throws DaoException
	{
		try {
			Query query = sessionFactory.getCurrentSession().createQuery(" delete from " + clazz.getSimpleName());  
            query.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			throw new DaoException(this.getClass(), e);
		}
	}
	
	/**
	 * 查找单个实例
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public BasePO findObject(Class poClass, Serializable pk)throws DaoException {
		try
		{
				return (BasePO) sessionFactory.getCurrentSession().get(poClass, pk);
		}
		catch (Exception e)
		{
				e.printStackTrace();
				throw new DaoException(this.getClass(), e);
		}
	}
	
	/**
	 * 查找所有
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List<BasePO> findAll(Class poClass) throws DaoException {
		try{
			return sessionFactory.getCurrentSession().createCriteria(poClass).list();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new DaoException(this.getClass(), e);
		}
	}

	/**
	 * 查询总条数
	 */
	@SuppressWarnings("rawtypes")
	public int getAllCount(Class poClass) throws DaoException
	{
		try{
			HQuery hq=new HQuery("select count(*) from ");
			hq.addHqlSplit(poClass.getSimpleName());
			Query query = sessionFactory.getCurrentSession().createQuery(hq.getQueryString());
			Number summoney = (Number) (query.iterate().next());
			int count=summoney.intValue();
			return count;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new DaoException(this.getClass(), e);
		}
	}
	
	/**
	 * 根据一个查询语句查询记录
	 */
	@SuppressWarnings("rawtypes")
	public List findHquery(HQuery _query) throws DaoException
	{

		List itr = null;
		try
		{
			StringBuffer query_str = new StringBuffer(_query.getQueryString());
			if (_query.getOrderby() != null) {
                query_str.append(_query.getOrderby());
            }
			if (_query.getGroupby() != null) {
                query_str.append(_query.getGroupby());
            }
			Query query = sessionFactory.getCurrentSession().createQuery(query_str.toString());
			if (_query.getParalist() != null)
			{
				List list = _query.getParalist();
				for (int i = 0; i < list.size(); i++)
				{
					Paras param = (Paras) list.get(i);
					if (param.getBoundType() == HqlDataBoundingType.PLACEHOLDER && _query.getBoundType() == HqlDataBoundingType.PLACEHOLDER) {
                        switch (param.getTypeNo())
                        {
                            case Types.VARCHAR:
                                query.setString(i, param.getPValue().toString());
                                break;
                            case Types.INTEGER:
                                query.setInteger(i, (new Integer(param.getPValue().toString())).intValue());
                                break;
                            case Types.DATE:
                                query.setDate(i, (java.sql.Date) param.getPValue());
                                break;
                            case Types.DOUBLE:
                                query.setDouble(i, ((Double) param.getPValue()).doubleValue());
                                break;
                            case Types.FLOAT:
                                query.setFloat(i, ((Float) param.getPValue()).floatValue());
                                break;
                            case Types.BOOLEAN:
                                query.setBoolean(i, ((Boolean) param.getPValue()).booleanValue());
                                break;
                            case Types.CHAR:
                                query.setCharacter(i, ((Character) param.getPValue()).charValue());
                                break;
                            case Types.JAVA_OBJECT:
                                query.setEntity(i, param.getPValue());
                                break;
                            case Types.TIMESTAMP:
                                query.setCalendar(i, ExtendDate.getCalendar(param.getPValue().toString()));
                                break;
                        }
                    } else {
                        throw new Exception("hql 数据绑定方式不一致");
                    }
				}
			}
			if (_query.getPageStartNo() != 0)
			{
				int pageno=_query.getPageStartNo();
				query.setFirstResult((pageno - 1) * _query.getPageSize());
				query.setMaxResults(_query.getPageSize());
			}
			itr = query.list();
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new DaoException(this.getClass(), e);
		}
		return itr == null || itr.size() == 0 ? null : itr;

	}

    /**
     * 根据一个查询来取得本次查旬的总条数
     */
    @SuppressWarnings({ "unused", "rawtypes" })
    public int getAllCountHquery(HQuery _query) throws DaoException
    {
        List itr = null;
        try
        {
            StringBuffer query_str = new StringBuffer(_query.getQueryString());
            if (_query.getGroupby() != null) {
                query_str.append(_query.getGroupby());
            }
            String _hql = query_str.toString();
            if (_hql.startsWith("select") || _hql.startsWith("SELECT")) {
                _hql = "select count(*) " + _hql.substring(_hql.indexOf("from"));
            } else {
                _hql = "select count(*) " + _hql;
            }
            Query query = sessionFactory.getCurrentSession().createQuery(_hql);
            if (_query.getParalist() != null)
            {
                List list = _query.getParalist();
                for (int i = 0; i < list.size(); i++)
                {
                    Paras param = (Paras) list.get(i);
                    if (param.getBoundType() == HqlDataBoundingType.PLACEHOLDER && _query.getBoundType() == HqlDataBoundingType.PLACEHOLDER) {
                        switch (param.getTypeNo())
                        {
                            case Types.VARCHAR:
                                query.setString(i, param.getPValue().toString());
                                break;
                            case Types.INTEGER:
                                query.setInteger(i, (new Integer(param.getPValue().toString())).intValue());
                                break;
                            case Types.DATE:
                                query.setDate(i, (java.sql.Date) param.getPValue());
                                break;
                            case Types.DOUBLE:
                                query.setDouble(i, ((Double) param.getPValue()).doubleValue());
                                break;
                            case Types.FLOAT:
                                query.setFloat(i, ((Float) param.getPValue()).floatValue());
                                break;
                            case Types.BOOLEAN:
                                query.setBoolean(i, ((Boolean) param.getPValue()).booleanValue());
                                break;
                            case Types.CHAR:
                                query.setCharacter(i, ((Character) param.getPValue()).charValue());
                                break;
                            case Types.JAVA_OBJECT:
                                query.setEntity(i, param.getPValue());
                                break;
                            case Types.TIMESTAMP:
                                query.setCalendar(i, ExtendDate.getCalendar(param.getPValue().toString()));
                                break;
                        }
                    } else {
                        throw new Exception("hql 数据绑定方式不一致");
                    }
                }
            }
            int count=0;
            if (_query.getGroupby() != null)
            {
                count=query.list().size();
            }
            else
            {
                Number summoney = (Number) (query.iterate().next());
                count=summoney.intValue();
            }
            return count;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw new DaoException(this.getClass(), e);
        }

    }
	
	/**
	 * 根据字段值来查询
	 */
	@SuppressWarnings("rawtypes")
	public List loadByPk(Class clazz, String keyName, Object keyValue) throws DaoException
	{
		try
		{
			return sessionFactory.getCurrentSession().createCriteria(clazz).add(Restrictions.eq(keyName,keyValue)).list();
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new DaoException(this.getClass(), e);

		}
	}
	
	/**
	 * 根据字段值来查询
	 * 分页
	 */
	@SuppressWarnings("rawtypes")
	public List loadByPk(Class clazz, String keyName, Object keyValue,CommConditionVo qvo) throws DaoException
	{
		try
		{
			int count = ((Number)sessionFactory.
						getCurrentSession().createCriteria(clazz).
						add(Restrictions.eq(keyName,keyValue))
						.setProjection(Projections.rowCount())
						.uniqueResult()).intValue();

			qvo.setItemCount(count);

			return sessionFactory.
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
	
	/**
	 * 分页
	 */
	public CommConditionVo getNewQvo(CommConditionVo qvo,HQuery _query)
	{
		if (qvo.isNeedCount())
        {
			qvo.setItemCount(this.getAllCountHquery(_query));
			_query.setPageStartNo(qvo.getPageStartNo());
			_query.setPageSize(qvo.getPageSize());
        }
		return qvo;
	}
	
	/**
	 * 查找所有记录分页
	 */
	@SuppressWarnings({ "rawtypes" })
	@Override
	public List findAll(Class poClass,CommConditionVo qvo) throws DaoException {
		try{
			HQuery hq=new HQuery("from ");
			hq.addHqlSplit(poClass.getSimpleName());
			this.getNewQvo(qvo, hq);
			return this.findHquery(hq);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new DaoException(this.getClass(), e);
		}
	}
}
