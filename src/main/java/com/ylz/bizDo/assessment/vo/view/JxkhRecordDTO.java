package com.ylz.bizDo.assessment.vo.view;

import java.io.Serializable;

/**
 * <p>
 * Title: JxkhRecordDTO.java<／p>
 * <p>
 * Description: 1． 获取记录列表<／p>
 * 
 * @author lyy
 * @date 2018年9月29日 上午20:26:45
 */
public class JxkhRecordDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 885413213044183174L;

	private String yyid00; // 机构编号
	private String orgname; // 机构名称
	private String ksmc; // 科室名称
	private String ksbh; // 科室编号
	private String ysxm; // 医生姓名
	private String ysbh; // 医生编号
	private String czxw; // 操作行为(值为：调阅档案或就诊)
	private String sfzh; // 身份证号
	private String yxsksj; // 有效刷卡时间
	private String sbkh; // 社保卡号

	public String getYyid00() {
		return yyid00;
	}

	public void setYyid00(String yyid00) {
		this.yyid00 = yyid00;
	}

	public String getOrgname() {
		return orgname;
	}

	public void setOrgname(String orgname) {
		this.orgname = orgname;
	}

	public String getKsmc() {
		return ksmc;
	}

	public void setKsmc(String ksmc) {
		this.ksmc = ksmc;
	}

	public String getKsbh() {
		return ksbh;
	}

	public void setKsbh(String ksbh) {
		this.ksbh = ksbh;
	}

	public String getYsxm() {
		return ysxm;
	}

	public void setYsxm(String ysxm) {
		this.ysxm = ysxm;
	}

	public String getYsbh() {
		return ysbh;
	}

	public void setYsbh(String ysbh) {
		this.ysbh = ysbh;
	}

	public String getCzxw() {
		return czxw;
	}

	public void setCzxw(String czxw) {
		this.czxw = czxw;
	}

	public String getSfzh() {
		return sfzh;
	}

	public void setSfzh(String sfzh) {
		this.sfzh = sfzh;
	}

	public String getYxsksj() {
		return yxsksj;
	}

	public void setYxsksj(String yxsksj) {
		this.yxsksj = yxsksj;
	}

	public String getSbkh() {
		return sbkh;
	}

	public void setSbkh(String sbkh) {
		this.sbkh = sbkh;
	}

}
