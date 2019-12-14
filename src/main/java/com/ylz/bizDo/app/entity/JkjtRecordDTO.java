package com.ylz.bizDo.app.entity;

import java.io.Serializable;


/**
 * <p>
 * Title: GrjbxxRecordDTO2.java<／p>
 * <p>
 * Description: 健康体检表<／p>
 * 
 * @author lyy
 * @date 2018年10月30日 下午5:07:49
 */
public class JkjtRecordDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 885413213044183174L;

	private String tablename; // 表名
	private String tjrq; // 体检日期
	private String zrys; // 责任医生
	private String jkpj; // 健康评价
	private String jkzd; // 健康指导

	public String getTablename() {
		return tablename;
	}

	public void setTablename(String tablename) {
		this.tablename = tablename;
	}

	public String getTjrq() {
		return tjrq;
	}

	public void setTjrq(String tjrq) {
		this.tjrq = tjrq;
	}

	public String getZrys() {
		return zrys;
	}

	public void setZrys(String zrys) {
		this.zrys = zrys;
	}

	public String getJkpj() {
		return jkpj;
	}

	public void setJkpj(String jkpj) {
		this.jkpj = jkpj;
	}

	public String getJkzd() {
		return jkzd;
	}

	public void setJkzd(String jkzd) {
		this.jkzd = jkzd;
	}


}
