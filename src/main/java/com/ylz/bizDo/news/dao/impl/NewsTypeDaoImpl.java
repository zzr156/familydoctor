package com.ylz.bizDo.news.dao.impl;

import com.ylz.bizDo.news.dao.NewsTypeDao;
import com.ylz.bizDo.news.po.NewsType;
import com.ylz.bizDo.news.vo.NewsTypePo;
import com.ylz.bizDo.news.vo.NewsTypePoTop;
import com.ylz.bizDo.news.vo.NewsTypeTableList;
import com.ylz.packaccede.allDo.SysDao;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Service("newsTypeDao")
@Transactional(rollbackForClassName={"Exception"})
public class NewsTypeDaoImpl   implements NewsTypeDao{

	@Autowired
	private SysDao sysDao;
	
	/**
	 * 根据id查询NewsTypePoTop
	 * @param id
	 * @return
	 */
	public List<NewsTypePoTop> findTopToNewsType(String id) throws Exception{
		Map<String,Object> map = new HashMap<String,Object>();
		String sql = "SELECT a.id id,a.type_name typeName,a.type_pid typePid FROM NEWS_TYPE a where a.type_pid=:upId";
		map.put("upId",id);
		return sysDao.getServiceDo().findSqlMapRVo(sql, map, NewsTypePoTop.class);
	}

	/**
	 * 查询所有的新闻类型
	 * @return
	 */
	public List<NewsTypePo> findAllNewsType() throws Exception {
		Map<String,Object> map = new HashMap<String,Object>();
		String sql = "SELECT t.id,t.type_num as typeNum,t.type_name as typeName,t.type_pid as typePid,t.type_pid as _parentId,t.type_state as typeState,t.type_state as typeStateName,t.type_imageurl as typeImageUrl FROM NEWS_TYPE t ORDER BY t.type_num";
		return sysDao.getServiceDo().findSqlMapRVo(sql, map, NewsTypePo.class);
	}

	/**
	 * 查询顶级新闻类型
	 * @return
	 */
	public List<NewsTypePoTop> findAllNewsTypeTop(String state) throws Exception {
		Map<String,Object> map = new HashMap<String,Object>();
		String sql = "SELECT a.id id,a.type_name typeName,a.type_pid typePid FROM NEWS_TYPE a WHERE 1=1 ";
		if(StringUtils.isNotBlank(state)){
			 map.put("state", state);
			 sql +=" AND a.type_state =:state";
		 }
		sql+=" ORDER BY a.type_num ";
		return sysDao.getServiceDo().findSqlMapRVo(sql, map, NewsTypePoTop.class);
	}

	/**
	 * 查询父节点编码
	 * @param typePid
	 * @return
	 */
	public String findByIdTypeNum(String typePid) throws Exception {
		 Map<String,Object> map = new HashMap<String,Object>();
		 String sql=" SELECT MAX(type_num) type_num FROM NEWS_TYPE a WHERE 1=1";
		 if(StringUtils.isNotBlank(typePid)){
			 map.put("TYPEPID", typePid);
			 sql +=" AND a.id =:TYPEPID";
		 }
		return (String) sysDao.getServiceDo().findSqlObject(sql,map);
	}

	/**
	 * 查询编码最大值
	 * @param typePid
	 * @return
	 */
	public String findMaxNum(String typePid) throws Exception {
		Map<String,Object> map = new HashMap<String,Object>();
		String sql=" SELECT MAX(type_num) type_num FROM NEWS_TYPE a WHERE 1=1";
		if(StringUtils.isNotBlank(typePid)){
			map.put("TYPEPID", typePid);
			sql +=" AND a.type_pid =:TYPEPID";
		}else{
			sql +=" AND a.type_pid IS NULL";
		}
		return (String) sysDao.getServiceDo().findSqlObject(sql,map);
	}

	/**
	 * 根据状态查询所有的新闻类型NewsTypeTableList
	 * @return
	 */
	public List<NewsTypeTableList> findTypeTableList(String state,String num) throws Exception {
		Map<String,Object> map = new HashMap<String,Object>();
		String sql = "SELECT a.id id,a.type_num typeNum,a.type_name typeName,a.type_pid typePid,a.type_state typeState,a.type_imageurl typeImageUrl FROM NEWS_TYPE a where 1=1 ";
		if(StringUtils.isNotBlank(state)){
			map.put("state", state);
			sql+=" and a.type_state =:state";
		}
		if(StringUtils.isNotBlank(num)){
			map.put("num", num);
			sql+=" and a.type_num =:num";
		}
		sql+=" ORDER BY a.type_num ";
		List ls = sysDao.getServiceDo().findSqlMapRVo(sql, map, NewsTypeTableList.class);
		return ls;
	}

	/**
	 * 根据状态查询所有的新闻类型NewsType
	 * @return
	 */
	public List<NewsType> findNewsTypeList(String state) throws Exception {
		Map<String,Object> map = new HashMap<String,Object>();
		String sql = "SELECT * FROM NEWS_TYPE a where 1=1 ";
		if(StringUtils.isNotBlank(state)){
			map.put("state", state);
			sql+=" and a.type_state =:state";
		}
		sql+=" ORDER BY a.type_num ";
		return sysDao.getServiceDo().findSqlMap(sql, map, NewsType.class);
	}

	@Override
	public NewsType findNewsTypenum(String num) throws Exception {
		Map<String,Object> map = new HashMap<String,Object>();
		String sql = "SELECT a.id id,a.type_num typeNum,a.type_name typeName,a.type_pid typePid,a.type_state typeState FROM NEWS_TYPE a where 1=1 ";
		if(StringUtils.isNotBlank(num)){
			map.put("num", num);
			sql+=" and a.type_num =:num";
		}
		sql+=" ORDER BY a.type_num ";
		List<NewsType> ls = sysDao.getServiceDo().findSqlMapRVo(sql, map, NewsTypeTableList.class);
		if(ls!=null&&ls.size()>0){
			return ls.get(0);
		}
		return null;
	}
}
