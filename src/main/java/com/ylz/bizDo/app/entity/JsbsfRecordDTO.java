package com.ylz.bizDo.app.entity;

import java.io.Serializable;


/**
 * <p>
 * Title: JsbsfRecordDTO.java<／p>
 * <p>
 * Description: 严重精神障碍患者随访记录<／p>
 * 
 * @author lyy
 * @date 2018年10月30日 下午5:11:20
 */
public class JsbsfRecordDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 885413213044183174L;

	private String tablename; // 表名
	private String sfrq; // 随访日期
	private String sfys; // 随访医生
	private String sffl; // 随访分类

	public String getTablename() {
		return tablename;
	}

	public void setTablename(String tablename) {
		this.tablename = tablename;
	}

	public String getSfrq() {
		return sfrq;
	}

	public void setSfrq(String sfrq) {
		this.sfrq = sfrq;
	}

	public String getSfys() {
		return sfys;
	}

	public void setSfys(String sfys) {
		this.sfys = sfys;
	}

	public String getSffl() {
		return sffl;
	}

	public void setSffl(String sffl) {
		this.sffl = sffl;
	}


}
