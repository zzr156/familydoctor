package com.ylz.bizDo.app.entity;

import java.io.Serializable;


/**
 * <p>
 * Title: FjhsfRecordDTO.java<／p>
 * <p>
 * Description:肺结核患者随访记录 <／p>
 * 
 * @author lyy
 * @date 2018年10月30日 下午4:54:35
 */
public class FjhsfRecordDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 885413213044183174L;

	private String tablename; // 表名
	private String sfrq; // 随访日期
	private String pgys; // 评估医生

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

	public String getPgys() {
		return pgys;
	}

	public void setPgys(String pgys) {
		this.pgys = pgys;
	}

}
