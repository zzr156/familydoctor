package com.ylz.bizDo.assessment.vo.view;

import java.io.Serializable;

/**
 * <p>
 * Title: T_cm_dinfoDTO
 * </p>
 * <p>
 * Description:慢性病管理--人员信息
 * </p>
 * <p>
 * Copyright:Copyright(c)2004
 * </p>
 * <p>
 * Company:实达科技
 * </p>
 *
 * @hibernate.class table="T_cm_dinfoDTO"
 * @author 劳动力99开发小组
 * @version 1.0
 */

public class TcmdinfoDTO implements Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = 6395891798683501737L;

	private java.lang.String ci_id; // 编号
	private java.lang.String df_id; // 居民编号
	private java.lang.String ci_bdate; // 疾病开始时间
	private java.math.BigDecimal ci_type; // 疾病类型
	private java.lang.String ciid; // 编号
	private java.lang.String dfid; // 居民编号
	private java.math.BigDecimal cclid; // 慢性病种类
	private java.lang.String cibdate; // 疾病开始时间
	private java.math.BigDecimal citype; // 疾病类型
	private java.math.BigDecimal hsick; // 合并症
	private java.math.BigDecimal asick; // 相关疾病
	private java.math.BigDecimal udrug; // 常用药物
	private java.math.BigDecimal cstatus; // 目前状况
	private java.lang.String oxygentime; // 吸氧时间(h)(患者平均每日的吸氧时间，计量单位为h)
	private java.lang.String fbedreason; // 家庭病床建立原因
	private java.lang.String fbedcreatedata; // 家庭病床建床日期
	private java.lang.String fbedcanceldata; // 家庭病床撤床日期
	private java.lang.String coalslong; // 家中煤火取暖时间(年)
	private java.math.BigDecimal coalsmark; // 家中煤火取暖标志
	private java.math.BigDecimal fsmoke; // 家庭成员吸烟标志
	private java.math.BigDecimal protectmark; // 防护措施标志
	private java.lang.String hazardworktime; // 从事有危害因素职业时长(年)
	private java.lang.String hazardwork; // 有危害因素的具体职业
	private java.lang.String hazardworktype; // 职业暴露危险因素种类
	private java.lang.String hazardworkname; // 职业暴露危险因素名称
	private java.math.BigDecimal hazardworkmark; // 职业暴露标志
	private java.lang.String cbehavior; // 遵医行为1.良好 2.一般 3.差
	private java.lang.String psychological; // 心理状态代码
	private java.math.BigDecimal nodrinkage; // 戒酒年龄(岁)
	private java.math.BigDecimal nodrinkmark; // 戒酒标志
	private java.math.BigDecimal drunkmark; // 醉酒标志
	private java.math.BigDecimal bdrinkage; // 开始饮酒年龄(岁)
	private java.math.BigDecimal drinktype; // 饮酒种类代码
	private java.math.BigDecimal drinkfrequency; // 饮酒频率代码
	private java.math.BigDecimal nosmokeage; // 戒烟年龄(岁
	private java.math.BigDecimal bsmokeage; // 开始吸烟年龄(岁)
	private java.lang.String smokestatus; // 吸烟状况代码1.从不吸烟 2.过去吸，已戒烟 3.吸烟
	private java.lang.String dietcode; // 饮食习惯代码
	private java.math.BigDecimal weeksport; // 周运动次数
	private java.math.BigDecimal holdonsport; // 坚持运动时长(年)
	private java.lang.String sporttime; // 运动时间(min)
	private java.lang.String sportfrequency; // 运动频率类别代码
	private java.lang.String sportdesc; // 运动方式说明
	private java.math.BigDecimal daysmoke; // 日吸烟量（支）
	private java.lang.String daydrink; // 日饮酒量(ml)
	private java.lang.String yyid00; // 医院id
	private java.lang.String yyno00; // 医院编号
	private java.lang.String zzdah0; // 纸质档案号
	private java.lang.String ssid00; // 社会保障号（智业上传）
	private java.math.BigDecimal isdel0; // 删除标记

	private java.math.BigDecimal jdrbh0; // 建档人编号
	private java.lang.String jdrxm0; // 建档人姓名
	private java.lang.String jdrq00; // 建档日期
	private java.lang.String jdsj00; // 建档时间
	private java.math.BigDecimal sfbyqz; // 是否本院确诊
	private java.lang.String qzjgid; // 确诊机构
	private java.lang.String qzysbh; // 确诊医生编号
	private java.lang.String qzysxm; // 确诊医生姓名
	private java.lang.String gxygbh; // 更新员工编号
	private java.lang.String gxygxm; // 更新员工姓名
	private java.lang.String zhgxrq; // 最后更新日期
	private java.lang.String zhgxsj; // 最后更新时间
	private java.lang.String sfgg00; // 是否规范管理

	public java.lang.String getCi_id() {
		return ci_id;
	}

	public void setCi_id(java.lang.String ci_id) {
		this.ci_id = ci_id;
	}

	public java.lang.String getDf_id() {
		return df_id;
	}

	public void setDf_id(java.lang.String df_id) {
		this.df_id = df_id;
	}

	public java.lang.String getCi_bdate() {
		return ci_bdate;
	}

	public void setCi_bdate(java.lang.String ci_bdate) {
		this.ci_bdate = ci_bdate;
	}

	public java.math.BigDecimal getCi_type() {
		return ci_type;
	}

	public void setCi_type(java.math.BigDecimal ci_type) {
		this.ci_type = ci_type;
	}

	public java.lang.String getCiid() {
		return ciid;
	}

	public void setCiid(java.lang.String ciid) {
		this.ciid = ciid;
	}

	public java.lang.String getDfid() {
		return dfid;
	}

	public void setDfid(java.lang.String dfid) {
		this.dfid = dfid;
	}

	public java.math.BigDecimal getCclid() {
		return cclid;
	}

	public void setCclid(java.math.BigDecimal cclid) {
		this.cclid = cclid;
	}

	public java.lang.String getCibdate() {
		return cibdate;
	}

	public void setCibdate(java.lang.String cibdate) {
		this.cibdate = cibdate;
	}

	public java.math.BigDecimal getCitype() {
		return citype;
	}

	public void setCitype(java.math.BigDecimal citype) {
		this.citype = citype;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public java.math.BigDecimal getHsick() {
		return hsick;
	}

	public void setHsick(java.math.BigDecimal hsick) {
		this.hsick = hsick;
	}

	public java.math.BigDecimal getAsick() {
		return asick;
	}

	public void setAsick(java.math.BigDecimal asick) {
		this.asick = asick;
	}

	public java.math.BigDecimal getUdrug() {
		return udrug;
	}

	public void setUdrug(java.math.BigDecimal udrug) {
		this.udrug = udrug;
	}

	public java.math.BigDecimal getCstatus() {
		return cstatus;
	}

	public void setCstatus(java.math.BigDecimal cstatus) {
		this.cstatus = cstatus;
	}

	public java.lang.String getOxygentime() {
		return oxygentime;
	}

	public void setOxygentime(java.lang.String oxygentime) {
		this.oxygentime = oxygentime;
	}

	public java.lang.String getFbedreason() {
		return fbedreason;
	}

	public void setFbedreason(java.lang.String fbedreason) {
		this.fbedreason = fbedreason;
	}

	public java.lang.String getFbedcreatedata() {
		return fbedcreatedata;
	}

	public void setFbedcreatedata(java.lang.String fbedcreatedata) {
		this.fbedcreatedata = fbedcreatedata;
	}

	public java.lang.String getFbedcanceldata() {
		return fbedcanceldata;
	}

	public void setFbedcanceldata(java.lang.String fbedcanceldata) {
		this.fbedcanceldata = fbedcanceldata;
	}

	public java.lang.String getCoalslong() {
		return coalslong;
	}

	public void setCoalslong(java.lang.String coalslong) {
		this.coalslong = coalslong;
	}

	public java.math.BigDecimal getCoalsmark() {
		return coalsmark;
	}

	public void setCoalsmark(java.math.BigDecimal coalsmark) {
		this.coalsmark = coalsmark;
	}

	public java.math.BigDecimal getFsmoke() {
		return fsmoke;
	}

	public void setFsmoke(java.math.BigDecimal fsmoke) {
		this.fsmoke = fsmoke;
	}

	public java.math.BigDecimal getProtectmark() {
		return protectmark;
	}

	public void setProtectmark(java.math.BigDecimal protectmark) {
		this.protectmark = protectmark;
	}

	public java.lang.String getHazardworktime() {
		return hazardworktime;
	}

	public void setHazardworktime(java.lang.String hazardworktime) {
		this.hazardworktime = hazardworktime;
	}

	public java.lang.String getHazardwork() {
		return hazardwork;
	}

	public void setHazardwork(java.lang.String hazardwork) {
		this.hazardwork = hazardwork;
	}

	public java.lang.String getHazardworktype() {
		return hazardworktype;
	}

	public void setHazardworktype(java.lang.String hazardworktype) {
		this.hazardworktype = hazardworktype;
	}

	public java.lang.String getHazardworkname() {
		return hazardworkname;
	}

	public void setHazardworkname(java.lang.String hazardworkname) {
		this.hazardworkname = hazardworkname;
	}

	public java.math.BigDecimal getHazardworkmark() {
		return hazardworkmark;
	}

	public void setHazardworkmark(java.math.BigDecimal hazardworkmark) {
		this.hazardworkmark = hazardworkmark;
	}

	public java.lang.String getCbehavior() {
		return cbehavior;
	}

	public void setCbehavior(java.lang.String cbehavior) {
		this.cbehavior = cbehavior;
	}

	public java.lang.String getPsychological() {
		return psychological;
	}

	public void setPsychological(java.lang.String psychological) {
		this.psychological = psychological;
	}

	public java.math.BigDecimal getNodrinkage() {
		return nodrinkage;
	}

	public void setNodrinkage(java.math.BigDecimal nodrinkage) {
		this.nodrinkage = nodrinkage;
	}

	public java.math.BigDecimal getNodrinkmark() {
		return nodrinkmark;
	}

	public void setNodrinkmark(java.math.BigDecimal nodrinkmark) {
		this.nodrinkmark = nodrinkmark;
	}

	public java.math.BigDecimal getDrunkmark() {
		return drunkmark;
	}

	public void setDrunkmark(java.math.BigDecimal drunkmark) {
		this.drunkmark = drunkmark;
	}

	public java.math.BigDecimal getBdrinkage() {
		return bdrinkage;
	}

	public void setBdrinkage(java.math.BigDecimal bdrinkage) {
		this.bdrinkage = bdrinkage;
	}

	public java.math.BigDecimal getDrinktype() {
		return drinktype;
	}

	public void setDrinktype(java.math.BigDecimal drinktype) {
		this.drinktype = drinktype;
	}

	public java.math.BigDecimal getDrinkfrequency() {
		return drinkfrequency;
	}

	public void setDrinkfrequency(java.math.BigDecimal drinkfrequency) {
		this.drinkfrequency = drinkfrequency;
	}

	public java.math.BigDecimal getNosmokeage() {
		return nosmokeage;
	}

	public void setNosmokeage(java.math.BigDecimal nosmokeage) {
		this.nosmokeage = nosmokeage;
	}

	public java.math.BigDecimal getBsmokeage() {
		return bsmokeage;
	}

	public void setBsmokeage(java.math.BigDecimal bsmokeage) {
		this.bsmokeage = bsmokeage;
	}

	public java.lang.String getSmokestatus() {
		return smokestatus;
	}

	public void setSmokestatus(java.lang.String smokestatus) {
		this.smokestatus = smokestatus;
	}

	public java.lang.String getDietcode() {
		return dietcode;
	}

	public void setDietcode(java.lang.String dietcode) {
		this.dietcode = dietcode;
	}

	public java.math.BigDecimal getWeeksport() {
		return weeksport;
	}

	public void setWeeksport(java.math.BigDecimal weeksport) {
		this.weeksport = weeksport;
	}

	public java.math.BigDecimal getHoldonsport() {
		return holdonsport;
	}

	public void setHoldonsport(java.math.BigDecimal holdonsport) {
		this.holdonsport = holdonsport;
	}

	public java.lang.String getSporttime() {
		return sporttime;
	}

	public void setSporttime(java.lang.String sporttime) {
		this.sporttime = sporttime;
	}

	public java.lang.String getSportfrequency() {
		return sportfrequency;
	}

	public void setSportfrequency(java.lang.String sportfrequency) {
		this.sportfrequency = sportfrequency;
	}

	public java.lang.String getSportdesc() {
		return sportdesc;
	}

	public void setSportdesc(java.lang.String sportdesc) {
		this.sportdesc = sportdesc;
	}

	public java.math.BigDecimal getDaysmoke() {
		return daysmoke;
	}

	public void setDaysmoke(java.math.BigDecimal daysmoke) {
		this.daysmoke = daysmoke;
	}

	public java.lang.String getDaydrink() {
		return daydrink;
	}

	public void setDaydrink(java.lang.String daydrink) {
		this.daydrink = daydrink;
	}

	public java.lang.String getYyid00() {
		return yyid00;
	}

	public void setYyid00(java.lang.String yyid00) {
		this.yyid00 = yyid00;
	}

	public java.lang.String getYyno00() {
		return yyno00;
	}

	public void setYyno00(java.lang.String yyno00) {
		this.yyno00 = yyno00;
	}

	public java.lang.String getZzdah0() {
		return zzdah0;
	}

	public void setZzdah0(java.lang.String zzdah0) {
		this.zzdah0 = zzdah0;
	}

	public java.lang.String getSsid00() {
		return ssid00;
	}

	public void setSsid00(java.lang.String ssid00) {
		this.ssid00 = ssid00;
	}

	public java.math.BigDecimal getIsdel0() {
		return isdel0;
	}

	public void setIsdel0(java.math.BigDecimal isdel0) {
		this.isdel0 = isdel0;
	}

	public java.math.BigDecimal getJdrbh0() {
		return jdrbh0;
	}

	public void setJdrbh0(java.math.BigDecimal jdrbh0) {
		this.jdrbh0 = jdrbh0;
	}

	public java.lang.String getJdrxm0() {
		return jdrxm0;
	}

	public void setJdrxm0(java.lang.String jdrxm0) {
		this.jdrxm0 = jdrxm0;
	}

	public java.lang.String getJdrq00() {
		return jdrq00;
	}

	public void setJdrq00(java.lang.String jdrq00) {
		this.jdrq00 = jdrq00;
	}

	public java.lang.String getJdsj00() {
		return jdsj00;
	}

	public void setJdsj00(java.lang.String jdsj00) {
		this.jdsj00 = jdsj00;
	}

	public java.math.BigDecimal getSfbyqz() {
		return sfbyqz;
	}

	public void setSfbyqz(java.math.BigDecimal sfbyqz) {
		this.sfbyqz = sfbyqz;
	}

	public java.lang.String getQzjgid() {
		return qzjgid;
	}

	public void setQzjgid(java.lang.String qzjgid) {
		this.qzjgid = qzjgid;
	}

	public java.lang.String getQzysbh() {
		return qzysbh;
	}

	public void setQzysbh(java.lang.String qzysbh) {
		this.qzysbh = qzysbh;
	}

	public java.lang.String getQzysxm() {
		return qzysxm;
	}

	public void setQzysxm(java.lang.String qzysxm) {
		this.qzysxm = qzysxm;
	}

	public java.lang.String getGxygbh() {
		return gxygbh;
	}

	public void setGxygbh(java.lang.String gxygbh) {
		this.gxygbh = gxygbh;
	}

	public java.lang.String getGxygxm() {
		return gxygxm;
	}

	public void setGxygxm(java.lang.String gxygxm) {
		this.gxygxm = gxygxm;
	}

	public java.lang.String getZhgxrq() {
		return zhgxrq;
	}

	public void setZhgxrq(java.lang.String zhgxrq) {
		this.zhgxrq = zhgxrq;
	}

	public java.lang.String getZhgxsj() {
		return zhgxsj;
	}

	public void setZhgxsj(java.lang.String zhgxsj) {
		this.zhgxsj = zhgxsj;
	}

	public java.lang.String getSfgg00() {
		return sfgg00;
	}

	public void setSfgg00(java.lang.String sfgg00) {
		this.sfgg00 = sfgg00;
	}

	public static interface IFindylkGroup {
	}
}