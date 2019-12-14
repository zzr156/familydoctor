package com.ylz.bizDo.web.po;

import com.ylz.packcommon.common.bean.BasePO;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Calendar;

/**医生团队表
 * AppTeam entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "APP_TEAM")
public class WebTeam extends BasePO {

	// Fields
	@Id
	@Column(name = "ID", unique = true, nullable = false, length = 36)
	@GeneratedValue(generator = "paymentableGenerator")
	@GenericGenerator(name = "paymentableGenerator", strategy = "assigned")
	private String id;
	@Column(name = "TEAM_HOSP_ID", length = 36)
	private String teamHospId;//机构主键
	@Column(name = "TEAM_HOSP_NAME", length = 20)
	private String teamHospName;//医院名称
	@Column(name = "TEAM_NAME", length = 20)
	private String teamName;//团队名称
	@Column(name = "TEAM_CODE", length = 100)
	private String teamCode;//团队编号
	@Column(name = "TEAM_DR_ID", length = 36)
	private String teamDrId;//家庭医生主键
	@Column(name = "TEAM_DR_NAME", length = 20)
	private String teamDrName;//医生姓名
	@Column(name = "TEAM_DR_CODE", length = 20)
	private String teamDrCode;//医生编号
	@Column(name = "TEAM_SORT")
	private Integer teamSort;//排序
	@Column(name = "TEAM_STATE", length = 10)
	private String teamState;//状态
	@Column(name = "TEAM_REMARKS", length = 500)
	private String teamRemarks;//团队备注
	@Column(name = "TEAM_CREATE_TIME")
	private Calendar teamCreateTime;//创建时间
	@Column(name = "TEAM_TEL",length = 20)
	private String teamTel;//联系电话
	@Column(name = "TEAM_TYPE",length = 10)
	private String teamType;//团队类型
	/*@Column(name = "TEAM_PEOPLE",length = 10)
	private String teamPeople;//团队目标量*/
	// Constructors
	@Column(name = "TEAM_DEL_STATE",length = 10)
	private String teamDelState= "0" ;//团队删除状态(1已删除 默认0正常)
	@Column(name = "TEAM_DEL_TIME")
	private Calendar teamDelTime;//团队删除时间

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}


	public String getTeamHospId() {
		return this.teamHospId;
	}

	public void setTeamHospId(String teamHospId) {
		this.teamHospId = teamHospId;
	}


	public String getTeamHospName() {
		return this.teamHospName;
	}

	public void setTeamHospName(String teamHospName) {
		this.teamHospName = teamHospName;
	}


	public String getTeamName() {
		return this.teamName;
	}

	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}


	public String getTeamCode() {
		return this.teamCode;
	}

	public void setTeamCode(String teamCode) {
		this.teamCode = teamCode;
	}


	public String getTeamDrId() {
		return this.teamDrId;
	}

	public void setTeamDrId(String teamDrId) {
		this.teamDrId = teamDrId;
	}


	public String getTeamDrName() {
		return this.teamDrName;
	}

	public void setTeamDrName(String teamDrName) {
		this.teamDrName = teamDrName;
	}


	public String getTeamDrCode() {
		return this.teamDrCode;
	}

	public void setTeamDrCode(String teamDrCode) {
		this.teamDrCode = teamDrCode;
	}


	public Integer getTeamSort() {
		return this.teamSort;
	}

	public void setTeamSort(Integer teamSort) {
		this.teamSort = teamSort;
	}


	public String getTeamState() {
		return this.teamState;
	}

	public void setTeamState(String teamState) {
		this.teamState = teamState;
	}


	public String getTeamRemarks() {
		return this.teamRemarks;
	}

	public void setTeamRemarks(String teamRemarks) {
		this.teamRemarks = teamRemarks;
	}

	public Calendar getTeamCreateTime() {
		return teamCreateTime;
	}

	public void setTeamCreateTime(Calendar teamCreateTime) {
		this.teamCreateTime = teamCreateTime;
	}

	public String getTeamTel() {
		return teamTel;
	}

	public void setTeamTel(String teamTel) {
		this.teamTel = teamTel;
	}

	public String getTeamType() {
		return teamType;
	}

	public void setTeamType(String teamType) {
		this.teamType = teamType;
	}

	public String getTeamDelState() {
		return teamDelState;
	}

	public void setTeamDelState(String teamDelState) {
		this.teamDelState = teamDelState;
	}

	public Calendar getTeamDelTime() {
		return teamDelTime;
	}

	public void setTeamDelTime(Calendar teamDelTime) {
		this.teamDelTime = teamDelTime;
	}
}