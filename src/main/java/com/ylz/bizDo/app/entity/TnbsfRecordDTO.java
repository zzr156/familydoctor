package com.ylz.bizDo.app.entity;

import java.io.Serializable;


/**
 * <p>
 * Title: JsbsfRecordDTO2.java<／p>
 * <p>
 * Description: 2型糖尿病患者随访记录<／p>
 * 
 * @author lyy
 * @date 2018年10月30日 下午5:15:13
 */
public class TnbsfRecordDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 885413213044183174L;

	private String tablename; // 表名
	private String sfrq; // 随访日期
	private String sfys; // 随访医生
	private String sffl; // 随访分类
	private String kfxt; // 空腹血糖值

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

	public String getKfxt() {
		return kfxt;
	}

	public void setKfxt(String kfxt) {
		this.kfxt = kfxt;
	}

}
