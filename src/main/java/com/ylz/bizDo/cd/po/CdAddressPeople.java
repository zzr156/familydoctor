package com.ylz.bizDo.cd.po;

import com.ylz.packcommon.common.bean.BasePO;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Calendar;

/**
 * Created by PC on 2017/2/28.
 * 统计数据
 */
@Entity
@Table(name="CP_CITY_AREA_PEOPLE")
@org.hibernate.annotations.Cache(usage= CacheConcurrencyStrategy.READ_WRITE)//要使用hibernate的二级缓存Cache的注释一定要添加上
public class CdAddressPeople extends BasePO {
	@Id
    @Column(name = "ID", unique = true, nullable = false, length = 36)
    @GeneratedValue(generator = "paymentableGenerator")
    @GenericGenerator(name = "paymentableGenerator", strategy = "uuid2")
    private String id;//主键
	@Column(name = "AREA_CODE",length = 36)
	private String areaCode;//地址编号
	@Column(name = "AREA_NAME",length = 50)
	private String areaName;//地址全称
	@Column(name = "AREA_SNAME",length = 50)
	private String areaSname;//地址简称
	@Column(name = "LEVEL_NAME",length = 38)
	private String level;//等级
    @Column(name = "AREA_POPULATION")
    private String areaPopulation;//人口数
    @Column(name = "AREA_TARGET",length = 38)
    private String areaTarget;//目标数
	@Column(name = "AREA_YEAR",length = 10)
    private String areaYear;//年份
	@Column(name = "AREA_RATE",length = 10)
	private String areaRate;//率
	@Column(name = "AREA_CREATE_TIME")
	private Calendar areaCreateTime;//创建时间
	@Column(name = "UP_AREA_ID",length = 36)
	private String upAreaId;//上一级区划编号
	@Column(name = "AREA_FOCUS",length = 38)
	private String areaFocus;//重点人群数
	@Column(name = "AREA_FOCUS_RATE",length = 10)
	private String areaFocusRate;//重点人群率
	@Column(name = "UP_AREA_POPULATION",length = 50)
	private String upAreaPopulation;//参考总人数
	@Column(name = "AREA_FOCUS_TARGET",length = 38)
	private String areaFocusTarget;//重点人群目标数
	@Column(name = "UP_AREA_FOCUS",length = 38)
	private String upAreaFocus;//参考重点人数
	@Column(name = "AREA_STATE",length = 10)
	private String areaState;//状态 0未修改 1已修改过
	@Column(name = "AREA_SIGN_TOP",length = 38)
	private String areaSignTop;//签约上限人数
	@Column(name = "AREA_SIGN_WAY",length = 10)
	private String areaSignWay;//签约上限人数方式 0医生 1团队
	@Column(name = "AREA_DIS_SIGN_TOP",length = 38)
	private String areaDisSignTop;//慢性病患者签约上限人数
	@Column(name = "AREA_DIS_SIGN_WAY",length = 10)
	private String areaDisSignWay;//慢性病患者签约上限人数方式 0医生 1团队
	@Column(name = "AREA_ECONOMIC",length = 50)
	private String areaEconomic;//人口经济性质人数 （）
	@Column(name = "AREA_UP_ECONOMIC",length = 50)
	private String areaUpEconomic;//人口经济性质参考人数
	@Column(name = "AREA_ECONOMIC_TARGET",length = 50)
	private String areaEconomicTarget;//人口经济性质目标人数
	@Column(name = "AREA_ECONOMIC_RATE",length = 10)
	private String areaEconomicRate;//人口经济性质目标率

	@Column(name = "AREA_ECONOMIC_JKLM",length = 50)
	private String areaEconomicJklm; // 人口经济性质-建卡立名管理数
	@Column(name = "AREA_ECONOMIC_DBH",length = 50)
	private String areaEconomicDbh;  // 人口经济性质-低保户管理数
	@Column(name = "AREA_ECONOMIC_TKH",length = 50)
	private String areaEconomicTkh;  // 人口经济性质-特困户管理数
	@Column(name = "AREA_ECONOMIC_JSJT",length = 50)
	private String areaEconomicJsjt; // 人口经济性质-计生家庭人口管理数


	public CdAddressPeople() {
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public String getAreaSname() {
		return areaSname;
	}

	public void setAreaSname(String areaSname) {
		this.areaSname = areaSname;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getAreaPopulation() {
		return areaPopulation;
	}

	public void setAreaPopulation(String areaPopulation) {
		this.areaPopulation = areaPopulation;
	}

	public String getAreaTarget() {
		return areaTarget;
	}

	public void setAreaTarget(String areaTarget) {
		this.areaTarget = areaTarget;
	}

	public String getAreaYear() {
		return areaYear;
	}

	public void setAreaYear(String areaYear) {
		this.areaYear = areaYear;
	}

	public String getAreaRate() {
		return areaRate;
	}

	public void setAreaRate(String areaRate) {
		this.areaRate = areaRate;
	}

	public String getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	public Calendar getAreaCreateTime() {
		return areaCreateTime;
	}

	public void setAreaCreateTime(Calendar areaCreateTime) {
		this.areaCreateTime = areaCreateTime;
	}

	public String getUpAreaId() {
		return upAreaId;
	}

	public void setUpAreaId(String upAreaId) {
		this.upAreaId = upAreaId;
	}

	public String getAreaFocus() {
		return areaFocus;
	}

	public void setAreaFocus(String areaFocus) {
		this.areaFocus = areaFocus;
	}

	public String getAreaFocusRate() {
		return areaFocusRate;
	}

	public void setAreaFocusRate(String areaFocusRate) {
		this.areaFocusRate = areaFocusRate;
	}

	public String getUpAreaPopulation() {
		return upAreaPopulation;
	}

	public void setUpAreaPopulation(String upAreaPopulation) {
		this.upAreaPopulation = upAreaPopulation;
	}

	public String getAreaFocusTarget() {
		return areaFocusTarget;
	}

	public void setAreaFocusTarget(String areaFocusTarget) {
		this.areaFocusTarget = areaFocusTarget;
	}

	public String getUpAreaFocus() {
		return upAreaFocus;
	}

	public void setUpAreaFocus(String upAreaFocus) {
		this.upAreaFocus = upAreaFocus;
	}

	public String getAreaState() {
		return areaState;
	}

	public void setAreaState(String areaState) {
		this.areaState = areaState;
	}

	public String getAreaSignTop() {
		return areaSignTop;
	}

	public void setAreaSignTop(String areaSignTop) {
		this.areaSignTop = areaSignTop;
	}

	public String getAreaSignWay() {
		return areaSignWay;
	}

	public void setAreaSignWay(String areaSignWay) {
		this.areaSignWay = areaSignWay;
	}

	public String getAreaDisSignTop() {
		return areaDisSignTop;
	}

	public void setAreaDisSignTop(String areaDisSignTop) {
		this.areaDisSignTop = areaDisSignTop;
	}

	public String getAreaDisSignWay() {
		return areaDisSignWay;
	}

	public void setAreaDisSignWay(String areaDisSignWay) {
		this.areaDisSignWay = areaDisSignWay;
	}

	public String getAreaEconomic() {
		return areaEconomic;
	}

	public void setAreaEconomic(String areaEconomic) {
		this.areaEconomic = areaEconomic;
	}

	public String getAreaUpEconomic() {
		return areaUpEconomic;
	}

	public void setAreaUpEconomic(String areaUpEconomic) {
		this.areaUpEconomic = areaUpEconomic;
	}

	public String getAreaEconomicTarget() {
		return areaEconomicTarget;
	}

	public void setAreaEconomicTarget(String areaEconomicTarget) {
		this.areaEconomicTarget = areaEconomicTarget;
	}

	public String getAreaEconomicRate() {
		return areaEconomicRate;
	}

	public void setAreaEconomicRate(String areaEconomicRate) {
		this.areaEconomicRate = areaEconomicRate;
	}

	public String getAreaEconomicJklm() {
		return areaEconomicJklm;
	}

	public void setAreaEconomicJklm(String areaEconomicJklm) {
		this.areaEconomicJklm = areaEconomicJklm;
	}

	public String getAreaEconomicDbh() {
		return areaEconomicDbh;
	}

	public void setAreaEconomicDbh(String areaEconomicDbh) {
		this.areaEconomicDbh = areaEconomicDbh;
	}

	public String getAreaEconomicTkh() {
		return areaEconomicTkh;
	}

	public void setAreaEconomicTkh(String areaEconomicTkh) {
		this.areaEconomicTkh = areaEconomicTkh;
	}

	public String getAreaEconomicJsjt() {
		return areaEconomicJsjt;
	}

	public void setAreaEconomicJsjt(String areaEconomicJsjt) {
		this.areaEconomicJsjt = areaEconomicJsjt;
	}
}
