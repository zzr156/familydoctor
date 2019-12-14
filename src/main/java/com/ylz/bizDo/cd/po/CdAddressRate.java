package com.ylz.bizDo.cd.po;

import com.ylz.packcommon.common.bean.BasePO;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * Created by PC on 2017/2/28.
 * 统计率省市
 */
@Entity
@Table(name="CP_CITY_AREA_RATE")
public class CdAddressRate extends BasePO {
	@Id
    @Column(name = "CITY_AREA_ID", unique = true, nullable = false, length = 36)
    @GeneratedValue(generator = "paymentableGenerator")
    @GenericGenerator(name = "paymentableGenerator", strategy = "uuid2")
    private String id;//主键
	@Column(name = "AREA_NAME",length = 50)
	private String areaName;//地址全称
	@Column(name = "AREA_SNAME",length = 50)
	private String areaSname;//地址简称
	@Column(name = "LEVEL_NAME",length = 38)
	private String level;//等级
    @Column(name = "AREA_POPULATION_RATE")
    private String areaPopulationRate;//人口签约率
	@Column(name = "AREA_POINT_PEOPLE_RATE")
	private String areaPointPeopleRate;//重点人群目标率
	@Column(name = "AREA_YEAR",length = 10)
    private String areaYear;//年份
	@Column(name = "AREA_ECONOMIC_RATE")
	private String areaEconomicRate;//人口经济性质目标率
	@Column(name = "AREA_PEOPLE_ECONOMIC_RATE")
	private String areaPeopleEconomicRate;//


	public CdAddressRate() {
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

	public String getAreaPopulationRate() {
		return areaPopulationRate;
	}

	public void setAreaPopulationRate(String areaPopulationRate) {
		this.areaPopulationRate = areaPopulationRate;
	}

	public String getAreaPointPeopleRate() {
		return areaPointPeopleRate;
	}

	public void setAreaPointPeopleRate(String areaPointPeopleRate) {
		this.areaPointPeopleRate = areaPointPeopleRate;
	}

	public String getAreaYear() {
		return areaYear;
	}

	public void setAreaYear(String areaYear) {
		this.areaYear = areaYear;
	}

	public String getAreaEconomicRate() {
		return areaEconomicRate;
	}

	public void setAreaEconomicRate(String areaEconomicRate) {
		this.areaEconomicRate = areaEconomicRate;
	}

	public String getAreaPeopleEconomicRate() {
		return areaPeopleEconomicRate;
	}

	public void setAreaPeopleEconomicRate(String areaPeopleEconomicRate) {
		this.areaPeopleEconomicRate = areaPeopleEconomicRate;
	}
}
