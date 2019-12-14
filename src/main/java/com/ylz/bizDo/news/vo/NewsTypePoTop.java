package com.ylz.bizDo.news.vo;

import org.apache.commons.lang.StringUtils;



/**
 * 新闻类别树结构
 * @author dws
 *
 */
public class NewsTypePoTop {

	private String id;//主键
	private String typeName;//菜单名
	private String typePid;//树
	  
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	public String getTypePid() {
		if(StringUtils.isNotBlank(typePid)) {
            return typePid;
        }
		return "";
		
	}
	public void setTypePid(String typePid) {
		this.typePid = typePid;
	}
	
	public String getResultNewsType(){
		return this.id+";;;"+this.typeName;
		
	}
	  
	  
	  
}
