package com.ylz.bizDo.news.vo;

import com.ylz.bizDo.app.po.AppLabelManage;
import com.ylz.packaccede.allDo.SysDao;
import com.ylz.packcommon.common.bizDo.SpringHelper;
import com.ylz.packcommon.common.util.ExtendDate;
import org.apache.commons.lang.StringUtils;

import java.sql.Timestamp;
import java.util.Calendar;


public class NewsTablePo {
	
	private String id;//主键
	private String tableTitle;//标题
	private String tableContent;//内容
	private String tableCjrxm;//创建人姓名
	private String strTableCjsj;//发布时间
	private String tableBrowse;//浏览次数
	private String strTableState;//状态
	private String strTableType;//新闻类别
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTableTitle() {
		return tableTitle;
	}
	public void setTableTitle(String tableTitle) {
		this.tableTitle = tableTitle;
	}
	public String getTableContent() {
		return tableContent;
	}
	public void setTableContent(String tableContent) {
		this.tableContent = tableContent;
	}
	public String getTableCjrxm() {
		return tableCjrxm;
	}
	public void setTableCjrxm(String tableCjrxm) {
		this.tableCjrxm = tableCjrxm;
	}
	public String getStrTableCjsj() {
		return strTableCjsj;
	}
	public void setStrTableCjsj(Timestamp strTableCjsj) {
		Calendar cal = new java.util.GregorianCalendar();
		cal.setTime(strTableCjsj);
		this.strTableCjsj = ExtendDate.getYMD_h_m(cal);
	}
	public String getTableBrowse() {
		return tableBrowse;
	}
	public void setTableBrowse(String tableBrowse) {
		this.tableBrowse = tableBrowse;
	}
	public String getStrTableState() {
		return strTableState;
	}
	public void setStrTableState(String strTableState) {
		this.strTableState = strTableState;
	}
	public String getStrTableType() {
		return strTableType;
	}
	public void setStrTableType(String strTableType) throws Exception {
		SysDao dao =  (SysDao) SpringHelper.getBean("sysDao");
		if(StringUtils.isNotBlank(strTableType)){
			AppLabelManage value = dao.getAppLabelManageDao().findLabelByValue("3",strTableType);
			if(value != null){
				this.strTableType= value.getLabelTitle();
			}else{
				this.strTableType = "";
			}
		}else{
			this.strTableType = "";
		}
	}
	
	
	
}
