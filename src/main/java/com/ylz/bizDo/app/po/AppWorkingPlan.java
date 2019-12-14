package com.ylz.bizDo.app.po;

import com.ylz.packaccede.allDo.SysDao;
import com.ylz.packcommon.common.bean.BasePO;
import com.ylz.packcommon.common.bizDo.SpringHelper;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Calendar;


/**
 * 工作计划
 */
@Entity
@Table(name = "APP_WORKING_PLAN")
public class AppWorkingPlan extends BasePO {

	// Fields
	@Id
	@Column(name = "ID", unique = true, nullable = false, length = 36)
	@GeneratedValue(generator = "paymentableGenerator")
	@GenericGenerator(name = "paymentableGenerator", strategy = "uuid2")
	private String id;
	@Column(name = "PLAN_TYPE", length = 10)
	private String planType;//计划类型 1随访计划 2健康指导
	@Column(name = "PLAN_DATE", length = 19)
	private Calendar planDate;//计划时间
	@Column(name = "PLAN_FINISH_DATE", length = 19)
	private Calendar planFinishDate;//结束时间
	@Column(name = "PLAN_STATE", length = 10)
	private String planState;//计划状态 1未完成 2已完成
	@Column(name = "PLAN_DR_ID", length = 36)
	private String planDrId;//医生id
	@Column(name = "PLAN_TEAM_ID", length = 36)
	private String planTeamId;//团队id
	@Column(name = "PLAN_HOSP_ID", length = 36)
	private String planHospId;//机构id
	@Column(name = "PLAN_AREA_CODE", length = 15)
	private String planAreaCode;//机构区域编号
	@Column(name ="PALN_FOREIGN_ID", length = 36)
	private String planForeignId;//对应类型外健

	// Constructors

	/** default constructor */
	public AppWorkingPlan() {
	}

	/** minimal constructor */
	public AppWorkingPlan(String id) {
		this.id = id;
	}

	/** full constructor */
	public AppWorkingPlan(String id, String planType, Calendar planDate,
						  Calendar planFinishDate, String planState, String planDrId,
			String planTeamId, String planHospId, String planAreaCode,String planForeignId) {
		this.id = id;
		this.planType = planType;
		this.planDate = planDate;
		this.planFinishDate = planFinishDate;
		this.planState = planState;
		this.planDrId = planDrId;
		this.planTeamId = planTeamId;
		this.planHospId = planHospId;
		this.planAreaCode = planAreaCode;
		this.planForeignId = planForeignId;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPlanType() {
		return this.planType;
	}

	public void setPlanType(String planType) {
		this.planType = planType;
	}

	public Calendar getPlanDate() {
		return planDate;
	}

	public void setPlanDate(Calendar planDate) {
		this.planDate = planDate;
	}

	public Calendar getPlanFinishDate() {
		return planFinishDate;
	}

	public void setPlanFinishDate(Calendar planFinishDate) {
		this.planFinishDate = planFinishDate;
	}

	public String getPlanState() {
		return this.planState;
	}

	public void setPlanState(String planState) {
		this.planState = planState;
	}

	public String getPlanDrId() {
		return this.planDrId;
	}

	public void setPlanDrId(String planDrId) {
		this.planDrId = planDrId;
	}

	public String getPlanTeamId() {
		return this.planTeamId;
	}

	public void setPlanTeamId(String planTeamId) {
		this.planTeamId = planTeamId;
	}

	public String getPlanHospId() {
		return this.planHospId;
	}

	public void setPlanHospId(String planHospId) {
		this.planHospId = planHospId;
	}

	public String getPlanAreaCode() {
		return this.planAreaCode;
	}

	public void setPlanAreaCode(String planAreaCode) {
		this.planAreaCode = planAreaCode;
	}

	public String getPlanForeignId() {
		return planForeignId;
	}

	public void setPlanForeignId(String planForeignId) {
		this.planForeignId = planForeignId;
	}

	/**
	 * 医生姓名
	 * @return
	 */
	public String getDrName(){
		if(StringUtils.isNotBlank(this.getPlanDrId())){
			SysDao dao =  (SysDao) SpringHelper.getBean("sysDao");
			AppDrUser user =(AppDrUser) dao.getServiceDo().find(AppDrUser.class,this.getPlanDrId());
			if(user!=null){
				return user.getDrName();
			}
		}
		return "";
	}
}