package com.ylz.bizDo.assessment.vo.view;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.ylz.packcommon.common.util.ExtendDate;

/**
 * 第2～5次产前随访服务记录表
 * 
 * @author hp
 *
 */
public class Fy_edwcsf implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 743134334695482981L;

	private String xtbh00;// 系统编号
	private String scxtbh;// 手册系统编号
	private String cbbj00;// 次别标记（2~5次、追加）
	private Date sfrq00; // 随访日期
	private BigDecimal yz0000;// 孕周（周）
	private String zs0000;// 主诉
	private BigDecimal tz0000;// 体重（Kg）
	private BigDecimal ckjcgg;// 产科检查：宫底高度（cm）
	private BigDecimal ckjcfw;// 产科检查：腹围（cm）
	private String ckjctw;// 产科检查：胎位
	private BigDecimal ckjctx;// 产科检查：胎心率（次/分钟）改用cktxl0
	private BigDecimal xyssy0;// 血压：收缩压（mmHg）
	private BigDecimal xyszy0;// 血压：舒张压（mmHg）
	private BigDecimal xhdb00;// 血红蛋白（g/L）
	private String ndb000;// 尿蛋白
	private String qtfzjc;// 其他辅助检查
	private String fl0000;// 分类
	private String zd0000;// 指导
	private String zz0000;// 转诊
	private String zzyy00;// 转诊原因
	private String zzjgks;// 转诊机构及科室
	private Date xcsfrq; // 下次随访日期
	private String sfysqm;// 随访医生签名
	private String sfysbh;// 随访医生编号
	private BigDecimal yzts00;// 孕周（天）
	private String cktxl0;// 产科检查：胎心率（次/分钟） 替代原CKJCTX
	private String wi_id;
	private java.sql.Timestamp zhxgrq;// 最后修改日期
	private String zhxgz0;// 最后修改者
	private java.sql.Timestamp cjrq00;// 创建日期
	private String cjz000;// 创建者
	private String drwdw0;// 导入外单位
	private String yyid00;// 医院id
	private String blflag;// 补录标志(0-否，1-是)
	private String bz0000;
	private String outbz0;// 外出备注
	private String isout;// 是否外出(1-是，2-否)
	
	public String getSfrq00Str() {
		return ExtendDate.getYMD(getSfrq00());
	}

	public String getXtbh00() {
		return xtbh00;
	}

	public void setXtbh00(String xtbh00) {
		this.xtbh00 = xtbh00;
	}

	public String getScxtbh() {
		return scxtbh;
	}

	public void setScxtbh(String scxtbh) {
		this.scxtbh = scxtbh;
	}

	public String getCbbj00() {
		return cbbj00;
	}

	public void setCbbj00(String cbbj00) {
		this.cbbj00 = cbbj00;
	}

	public Date getSfrq00() {
		return sfrq00;
	}

	public void setSfrq00(Date sfrq00) {
		this.sfrq00 = sfrq00;
	}

	public BigDecimal getYz0000() {
		return yz0000;
	}

	public void setYz0000(BigDecimal yz0000) {
		this.yz0000 = yz0000;
	}

	public String getZs0000() {
		return zs0000;
	}

	public void setZs0000(String zs0000) {
		this.zs0000 = zs0000;
	}

	public BigDecimal getTz0000() {
		return tz0000;
	}

	public void setTz0000(BigDecimal tz0000) {
		this.tz0000 = tz0000;
	}

	public BigDecimal getCkjcgg() {
		return ckjcgg;
	}

	public void setCkjcgg(BigDecimal ckjcgg) {
		this.ckjcgg = ckjcgg;
	}

	public BigDecimal getCkjcfw() {
		return ckjcfw;
	}

	public void setCkjcfw(BigDecimal ckjcfw) {
		this.ckjcfw = ckjcfw;
	}

	public String getCkjctw() {
		return ckjctw;
	}

	public void setCkjctw(String ckjctw) {
		this.ckjctw = ckjctw;
	}

	public BigDecimal getCkjctx() {
		return ckjctx;
	}

	public void setCkjctx(BigDecimal ckjctx) {
		this.ckjctx = ckjctx;
	}

	public BigDecimal getXyssy0() {
		return xyssy0;
	}

	public void setXyssy0(BigDecimal xyssy0) {
		this.xyssy0 = xyssy0;
	}

	public BigDecimal getXyszy0() {
		return xyszy0;
	}

	public void setXyszy0(BigDecimal xyszy0) {
		this.xyszy0 = xyszy0;
	}

	public BigDecimal getXhdb00() {
		return xhdb00;
	}

	public void setXhdb00(BigDecimal xhdb00) {
		this.xhdb00 = xhdb00;
	}

	public String getNdb000() {
		return ndb000;
	}

	public void setNdb000(String ndb000) {
		this.ndb000 = ndb000;
	}

	public String getQtfzjc() {
		return qtfzjc;
	}

	public void setQtfzjc(String qtfzjc) {
		this.qtfzjc = qtfzjc;
	}

	public String getFl0000() {
		return fl0000;
	}

	public void setFl0000(String fl0000) {
		this.fl0000 = fl0000;
	}

	public String getZd0000() {
		return zd0000;
	}

	public void setZd0000(String zd0000) {
		this.zd0000 = zd0000;
	}

	public String getZz0000() {
		return zz0000;
	}

	public void setZz0000(String zz0000) {
		this.zz0000 = zz0000;
	}

	public String getZzyy00() {
		return zzyy00;
	}

	public void setZzyy00(String zzyy00) {
		this.zzyy00 = zzyy00;
	}

	public String getZzjgks() {
		return zzjgks;
	}

	public void setZzjgks(String zzjgks) {
		this.zzjgks = zzjgks;
	}

	public Date getXcsfrq() {
		return xcsfrq;
	}

	public void setXcsfrq(Date xcsfrq) {
		this.xcsfrq = xcsfrq;
	}

	public String getSfysqm() {
		return sfysqm;
	}

	public void setSfysqm(String sfysqm) {
		this.sfysqm = sfysqm;
	}

	public String getSfysbh() {
		return sfysbh;
	}

	public void setSfysbh(String sfysbh) {
		this.sfysbh = sfysbh;
	}

	public BigDecimal getYzts00() {
		return yzts00;
	}

	public void setYzts00(BigDecimal yzts00) {
		this.yzts00 = yzts00;
	}

	public String getCktxl0() {
		return cktxl0;
	}

	public void setCktxl0(String cktxl0) {
		this.cktxl0 = cktxl0;
	}

	public String getWi_id() {
		return wi_id;
	}

	public void setWi_id(String wi_id) {
		this.wi_id = wi_id;
	}

	public java.sql.Timestamp getZhxgrq() {
		return zhxgrq;
	}

	public void setZhxgrq(java.sql.Timestamp zhxgrq) {
		this.zhxgrq = zhxgrq;
	}

	public String getZhxgz0() {
		return zhxgz0;
	}

	public void setZhxgz0(String zhxgz0) {
		this.zhxgz0 = zhxgz0;
	}

	public java.sql.Timestamp getCjrq00() {
		return cjrq00;
	}

	public void setCjrq00(java.sql.Timestamp cjrq00) {
		this.cjrq00 = cjrq00;
	}

	public String getCjz000() {
		return cjz000;
	}

	public void setCjz000(String cjz000) {
		this.cjz000 = cjz000;
	}

	public String getDrwdw0() {
		return drwdw0;
	}

	public void setDrwdw0(String drwdw0) {
		this.drwdw0 = drwdw0;
	}

	public String getYyid00() {
		return yyid00;
	}

	public void setYyid00(String yyid00) {
		this.yyid00 = yyid00;
	}

	public String getBlflag() {
		return blflag;
	}

	public void setBlflag(String blflag) {
		this.blflag = blflag;
	}

	public String getBz0000() {
		return bz0000;
	}

	public void setBz0000(String bz0000) {
		this.bz0000 = bz0000;
	}

	public String getOutbz0() {
		return outbz0;
	}

	public void setOutbz0(String outbz0) {
		this.outbz0 = outbz0;
	}

	public String getIsout() {
		return isout;
	}

	public void setIsout(String isout) {
		this.isout = isout;
	}

}