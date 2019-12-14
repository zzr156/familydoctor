package com.ylz.packcommon.common.dao;

import com.ylz.packcommon.common.CommConditionVo;
import com.ylz.packcommon.common.bean.BasePO;
import com.ylz.packcommon.common.exception.DaoException;
import com.ylz.packcommon.common.hql.HQuery;
import org.hibernate.SessionFactory;

import java.io.Serializable;
import java.util.List;

/**
 * dao基类接口
 * @date 2012/04/07
 */
public interface IBaseDao{
	/**`
	 * 取Session
	 * @return 
	 */
	public SessionFactory getSessionFactory();

	public SessionFactory getSessionFactoryRead();

	/**
	 * 去持久po
	 * @return 
	 */
	public Object removePoFormSession(Object o);
	/**
	 * 保存实例
	 */
	public void add(Object po) throws DaoException;
	/**
	 * 修改实例
	 */
	public void modify(Object po) throws DaoException;
	/**
	 * 保存或修改实例
	 */
	public void saveUpdate(Object po) throws DaoException;
	/**
	 * 删除实例
	 */
	public void delete(Object po) throws DaoException;
	/**
	 * 删除多实例
	 */
	@SuppressWarnings("rawtypes")
	public void deleteList(Class clazz, Serializable[] id) throws DaoException;
	/**
	 * 删除所有
	 */
	@SuppressWarnings("rawtypes")
	public void deleteAll(Class clazz)throws DaoException;
	/**
	 * 查找单个实例
	 */
	public BasePO findObject(Class<?> poClass,java.io.Serializable pk) throws DaoException;
	/**
	 * 查找所有
	 */
	public List<BasePO> findAll(Class<?> poClass) throws DaoException;
	/**
	 * 查询总条数
	 */
	@SuppressWarnings("rawtypes")
	public int getAllCount(Class poClass) throws DaoException;
	/**
	 * 根据一个查询语句查询记录
	 */
	@SuppressWarnings("rawtypes")
	public List findHquery(HQuery _query) throws DaoException;
	/**
	 * 根据一个查询来取得本次查旬的总条数
	 */
	public int getAllCountHquery(HQuery _query) throws DaoException;
	/**
	 * 根据字段值来查询
	 */
	@SuppressWarnings("rawtypes")
	public List loadByPk(Class clazz, String keyName, Object keyValue) throws DaoException;
	@SuppressWarnings("rawtypes")
	public List loadByPk(Class clazz, String keyName, Object keyValue,CommConditionVo qvo) throws DaoException;
	/**
	 * 分页
	 */
	public CommConditionVo getNewQvo(CommConditionVo qvo,HQuery _query);
	/**
	 * 查找所有记录分页
	 */
	@SuppressWarnings({ "rawtypes" })
	public List findAll(Class poClass,CommConditionVo qvo) throws DaoException;
}
