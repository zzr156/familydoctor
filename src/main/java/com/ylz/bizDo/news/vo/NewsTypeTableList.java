package com.ylz.bizDo.news.vo;

import java.util.List;

/**
 * 类别新闻雷彪
 * @author Administrator
 *
 */
public class NewsTypeTableList {
	private String id;//类别id
	private String typeNum;//类别编码
	private String typeName;//类别名称
	private String typePid;
	private String typeState;//是否启用
	private List<NewsTablePo> newsList;//类别下的新闻列表
	private int itemCount=0;
	private String typeImageUrl;
	
	
	public String getTypeImageUrl() {
		return typeImageUrl;
	}
	public void setTypeImageUrl(String typeImageUrl) {
		this.typeImageUrl = typeImageUrl;
	}
	public int getItemCount() {
		return itemCount;
	}
	public void setItemCount(int itemCount) {
		this.itemCount = itemCount;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTypeNum() {
		return typeNum;
	}
	public void setTypeNum(String typeNum) {
		this.typeNum = typeNum;
	}
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	public String getTypePid() {
		return typePid;
	}
	public void setTypePid(String typePid) {
		this.typePid = typePid;
	}
	public String getTypeState() {
		return typeState;
	}
	public void setTypeState(String typeState) {
		this.typeState = typeState;
	}
	public List<NewsTablePo> getNewsList() {
		return newsList;
	}
	public void setNewsList(List<NewsTablePo> newsList) {
		this.newsList = newsList;
	}
	
//	public void setNewsList(String newsList) {
//		if(this.getId()!=null){
//			SysDao dao =  (SysDao) SpringHelper.getBean("sysDao");
//			NewsTableQvo qvo=new NewsTableQvo();
//			qvo.setTableNewsType(this.getId());
//			this.newsList=dao.getNewsTableDao().findAllNewsTable(qvo);
//		}else
//			this.newsList = new ArrayList<NewsTablePo>();
//	}
	
	
}
