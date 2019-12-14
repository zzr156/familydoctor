package com.ylz.bizDo.news.dao;

import com.ylz.bizDo.news.po.NewsType;
import com.ylz.bizDo.news.vo.NewsTypePo;
import com.ylz.bizDo.news.vo.NewsTypePoTop;
import com.ylz.bizDo.news.vo.NewsTypeTableList;

import java.util.List;

public interface NewsTypeDao  {
	/**
	 * 根据id查询NewsTypePoTop
	 * @param id
	 * @return
	 */
	public List<NewsTypePoTop> findTopToNewsType(String id) throws Exception;
	/**
	 * 查询所有的新闻类型
	 * @return
	 */
	public List<NewsTypePo> findAllNewsType() throws Exception;
	/**
	 * 查询顶级新闻类型
	 * @return
	 */
	public List<NewsTypePoTop> findAllNewsTypeTop(String state) throws Exception;
	/**
	 * 查询父节点编码
	 * @param typePid
	 * @return
	 */
	public String findByIdTypeNum(String typePid) throws Exception;
	/**
	 * 查询编码最大值
	 * @param typePid
	 * @return
	 */
	public String findMaxNum(String typePid) throws Exception;
	/**
	 * 根据状态查询所有的新闻类型NewsTypeTableList
	 * @return
	 */
	public List<NewsTypeTableList> findTypeTableList(String state,String num) throws Exception;
	/**
	 * 根据状态查询所有的新闻类型NewsType
	 * @return
	 */
	public List<NewsType> findNewsTypeList(String state) throws Exception;
	/**
	 * 根据num查询所有的新闻类型num
	 * @return
	 */
	public NewsType findNewsTypenum(String num) throws Exception;

}
