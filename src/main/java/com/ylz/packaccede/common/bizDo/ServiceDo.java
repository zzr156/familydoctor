package com.ylz.packaccede.common.bizDo;

import com.ylz.packcommon.common.CommConditionVo;
import com.ylz.packcommon.common.bean.BasePO;
import com.ylz.packcommon.common.exception.DaoException;
import com.ylz.packcommon.common.hql.HQuery;
import org.hibernate.SessionFactory;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 公共dao方法
 */
public interface ServiceDo {
	
	/**
     * 取Session
     * @return
     */
    public SessionFactory getSessionFactory();
    /**
     * 去持久po
     * @return
     */
    public Object removePoFormSession(Object o);
    /**
     * 保存实例
     */
    public void add(BasePO oTO) throws DaoException;
    /**
     * 修改实例
     */
    public void modify(BasePO oTO) throws DaoException;
    /**
     * 保存或修改实例
     */
    public void saveUpdate(BasePO oTO) throws DaoException;
    /**
     * 删除实例
     */
    public void delete(Object oTO) throws DaoException;
    public void delete(Class poClass, java.io.Serializable pk) throws DaoException;
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
    @SuppressWarnings("rawtypes")
    public Object find(Class poClass, java.io.Serializable pk) throws DaoException;
    /**
     * 查找所有
     */
    @SuppressWarnings("rawtypes")
    public List findAll(Class poClass) throws DaoException;
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
	public List findAll(Class poClass, CommConditionVo qvo)throws DaoException;
	
	/**
	 * 直接使用sql
	 * @param sql
	 * @return
	 */
	public List<Map> findSql(String sql);

	/**
	 * 带Map参数sql
	 * @param sql
	 * @param map
	 * @return
	 */
	public List<Map> findSqlMap(String sql,Map<String,Object> map);

	/**
	 * 带Map参数sql带分页
	 * @param sql
	 * @param map
	 * @return
	 */
	public List<Map> findSqlMap(String sql,Map<String,Object> map,CommConditionVo qvo);

	/**
	 * 带Map参数sql,返回指定对象
	 * @param sql
	 * @param map
	 * @param clazz
	 * @return
	 */
	public List findSqlMap(String sql,Map<String,Object> map,Class clazz);

	/**
	 * 带Map参数sql,返回指定对象
	 * @param sql
	 * @param map
	 * @param clazz
	 * @param first
	 * @param max
	 * @return
	 */
	public List findSqlMap(String sql,Map<String,Object> map,Class clazz,int first,int max);

	/**
	 * 带Map参数sql和分页,返回指定对象,
	 * @param sql
	 * @param map
	 * @param qvo
	 * @return
	 */
	public List findSqlMap(String sql,Map<String,Object> map,Class clazz,CommConditionVo qvo);

	/**
	 * 查询条数
	 * @param sql
	 * @param map
	 * @return
	 */
	public int gteSqlCount(String sql,Map<String,Object> map);

	/**
	 * sql更新
	 * @param sql
	 * @return
	 */
	public int update(String sql);
	/**
	 * sql更新 带参数
	 * @param sql
	 * @return
	 */
	public int update(String sql,Map<String, Object> map);
	/**
	 * sql新增
	 * @param sql
	 */
	public void add(String sql);

	/**
	 * sql返回集合
	 * @param sql
	 * @param clazz
	 * @return
	 */
	public List findSqlList(String sql,Class clazz);

	/**
	 * sql返回任意对象
	 * @param sql
	 * @return
	 */
	public Object findSqlObject(String sql);

	/**
	 * sql返回任意对象
	 * @param sql
	 * @return
	 */
	public Object findSqlObject(String sql,Map<String, Object> map);

	/**
	 * 返回非托管实体
	 * @param sql
	 * @param map
	 * @param clazz
	 * @return
	 */
	public List findSqlMapRVo(String sql, Map<String, Object> map, Class clazz);
	/**
	 * 非托管实体分页,返回指定对象,
	 * @param sql
	 * @param map
	 * @param qvo
	 * @return
	 */
	public List findSqlMapRVo(String sql, Map<String, Object> map, Class clazz, CommConditionVo qvo);

	public List findSqlMapRVo(String sql,String sqlCount ,Map<String, Object> map, Class clazz, CommConditionVo qvo);

	
	/**
	 * hibernate缓存
	 * @param sql
	 * @param map
	 * @param clazz
	 * @return
	 */
	public List findCachSqlMap(String sql,Map<String,Object> map,Class clazz);
	
	  /**
		 * 带Map参数sql,返回指定对象 带in 方法
		 * @param sql
		 * @param map
		 * @param clazz
		 * @return
		 */
	    public List findSqlMapArray(String sql,Map<String,Object> map,Class clazz,String[] in);

	/**
	 * 查询count使用
	 * @param sql
	 * @param map
	 * @return
	 */
	public int findCount(String sql,Map<String,Object> map);

	/**
	 * 查询二级缓存count使用
	 * @param sql
	 * @param map
	 * @return
	 */
	public int findCachCount(String sql,Map<String,Object> map);


	//hql

	/**
	 * hql查询
	 * @param hql
	 * @return
	 */
	public List findHqlMap(String hql) throws Exception;

	/**
	 * hql查询
	 * @param hql
	 * @param map
	 * @return
	 */
	public List findHqlMap(String hql,Map<String,Object> map) throws Exception;

	/**
	 * hql查询单个并返回特定对象
	 * @param hql
	 * @param map
	 * @return
	 */
	public Object findHqlOne(String hql,Map<String,Object> map,Class clazz) throws Exception;

	public Object findHqlOne(String hql,Map<String,Object> map) throws Exception;

	/**
	 * hql分页查询
	 * @param hql
	 * @param map
	 * @param qvo
	 * @return
	 */
	public List findHqlMap(String hql,Map<String,Object> map,CommConditionVo qvo) throws Exception;

	/**
	 * Hql非拖管查询
	 * @param hql
	 * @param map
	 * @param clazz
	 * @return
	 */
	public List findHqlMapRVo(String hql, Map<String, Object> map, Class clazz) throws Exception;

	/**
	 * hql非拖管分页查询
	 * @param hql
	 * @param map
	 * @param clazz
	 * @param qvo
	 * @return
	 */
	public List findHqlMapRVo(String hql, Map<String, Object> map, Class clazz, CommConditionVo qvo) throws Exception;
	/**
	 * hql查询count使用
	 * @param hql
	 * @param map
	 * @return
	 */
	public int findHqlCount(String hql,Map<String,Object> map) throws Exception;

	/**
	 * 根据sql删除
	 * @param sql
	 * @return
	 */
	public int deleteSql(String sql);

	/**
	 * 取序列
	 * @param tabel
	 * @return
	 */
	public String getSequence(String tabel);

	public String getSequence(String tabel,int length);

	/**
	 * 缓存查询
	 * @param sql
	 * @param map
	 * @param clazz
	 * @return
	 * @throws Exception
	 */
	public List findCachHqlMap(String sql,Map<String,Object> map,Class clazz) throws Exception;

	/**
	 * 缓存查询
	 * @param sql
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public List findCachHqlMap(String sql,Map<String,Object> map) throws Exception;

	/**
	 * 緩存查詢
	 * @param hql
	 * @param map
	 * @param qvo
	 * @return
	 * @throws Exception
	 */
	public List findCachHqlMap(String hql,Map<String,Object> map,CommConditionVo qvo) throws Exception;


}
