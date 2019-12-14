package com.ylz.bizDo.news.vo;

import com.ylz.bizDo.cd.po.CdCode;
import com.ylz.packaccede.allDo.SysDao;
import com.ylz.packcommon.common.CodeGroupConstrats;
import com.ylz.packcommon.common.bizDo.SpringHelper;
import org.apache.commons.lang.StringUtils;



/**
 * 新闻类别树结构
 * @author dws
 *
 */
public class NewsTypePo {

 	private String id;//主键
    private String typeNum;//类别编码
    private String typePid;//上级id
    private String typeName;//类型名称
    private String typeState;//类型状态
    private String typeStateName;//状态名称
    private String _parentId;
    private String typeImageUrl;
    
    
	public String getTypeImageUrl() {
		return typeImageUrl;
	}
	public void setTypeImageUrl(String typeImageUrl) {
		this.typeImageUrl = typeImageUrl;
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
	public String getTypePid() {
		if(StringUtils.isNotBlank(typePid)) {
            return typePid;
        }
		return "";
	}
	public void setTypePid(String typePid) {
		this.typePid = typePid;
	}
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	public String getTypeState() {
		return typeState;
	}
	public void setTypeState(String typeState) {
		this.typeState = typeState;
	}
	
	public String getTypeStateName() throws Exception {
		if(this.getTypeState()!=null){
			SysDao dao =  (SysDao) SpringHelper.getBean("sysDao");
			CdCode value = dao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_ENABLE, this.getTypeState());
			if(value!=null) {
                return value.getCodeTitle();
            }
		}
		return "";
	}
	public void setTypeStateName(String typeStateName) {
		this.typeStateName = typeStateName;
	}
	public String get_parentId() {
		return _parentId;
	}
	public void set_parentId(String _parentId) {
		this._parentId = _parentId;
	}
}
