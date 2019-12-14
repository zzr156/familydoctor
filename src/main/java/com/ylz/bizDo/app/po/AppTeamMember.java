package com.ylz.bizDo.app.po;

import com.ylz.bizDo.cd.po.CdCode;
import com.ylz.bizDo.jtapp.drEntity.AppDrUserEntity;
import com.ylz.packaccede.allDo.SysDao;
import com.ylz.packcommon.common.CodeGroupConstrats;
import com.ylz.packcommon.common.bean.BasePO;
import com.ylz.packcommon.common.bizDo.SpringHelper;
import org.apache.commons.lang.StringUtils;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**团队成员表
 * AppTeamMember entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "APP_TEAM_MEMBER")
public class AppTeamMember extends BasePO {

	// Fields
	@Id
	@Column(name = "ID", unique = true, nullable = false, length = 36)
	@GeneratedValue(generator = "paymentableGenerator")
	@GenericGenerator(name = "paymentableGenerator", strategy = "uuid2")
	private String id;
	@Column(name = "MEM_TEAMID", length = 36)
	private String memTeamid;//团队主键
	@Column(name = "MEM_DR_ID", length = 36)
	private String memDrId;//成员主键
	@Column(name = "MEM_DR_NAME", length = 36)
	private String memDrName;//成员名称
	@Column(name = "MEM_WORK_TYPE", length = 10)
	private String memWorkType;//成员工作类型  ..健康管理师,2.专科医生,3.全科医生,4.医技人员,5.公卫医师,6.社区护士
	@Column(name = "MEM_TEAM_NAME", length = 200)
	private String memTeamName;//团队名称
	@Column(name = "MEM_STATE", length = 10)
	private String memState;// 团队角色 0：队长 1：成员
	@Column(name = "DR_ROLE", length = 10)
	private String drRole;//成员职称 1管理员 2专科医生 3全科医生 4医技人员 5公卫医师 6社区护士 7助理
	@Column(name = "DR_TEL", length = 20)
	private String drTel;//电话号码

	public String getDrTel() {
		return drTel;
	}

	public void setDrTel(String drTel) {
		this.drTel = drTel;
	}

	// Constructors

	/** default constructor */
	public AppTeamMember() {
	}

	/** minimal constructor */
	public AppTeamMember(String id) {
		this.id = id;
	}

	/** full constructor */
	public AppTeamMember(String id, String memTeamid, String memDrId,
			String memDrName, String memWorkType,String memTeamName,String memState) {
		this.id = id;
		this.memTeamid = memTeamid;
		this.memDrId = memDrId;
		this.memDrName = memDrName;
		this.memWorkType = memWorkType;
		this.memTeamName = memTeamName;
		this.memState = memState;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}


	public String getMemTeamid() {
		return this.memTeamid;
	}

	public void setMemTeamid(String memTeamid) {
		this.memTeamid = memTeamid;
	}


	public String getMemDrId() {
		return this.memDrId;
	}

	public void setMemDrId(String memDrId) {
		this.memDrId = memDrId;
	}


	public String getMemDrName() throws Exception {
		SysDao dao = (SysDao) SpringHelper.getBean("sysDao");
		AppDrUserEntity entity = dao.getAppDrUserDao().findUserById(memDrId);
		if(entity != null && StringUtils.isNotBlank(entity.getDrName())){
			return entity.getDrName();
		}
		return this.memDrName;
	}

	public void setMemDrName(String memDrName) {
		this.memDrName = memDrName;
	}


	public String getMemWorkType() {
		return this.memWorkType;
	}

	public void setMemWorkType(String memWorkType) {
		this.memWorkType = memWorkType;
	}

	public String getMemTeamName() {
		return memTeamName;
	}

	public void setMemTeamName(String memTeamName) {
		this.memTeamName = memTeamName;
	}

	public String getMemState() {
		return memState;
	}

	public void setMemState(String memState) {
		this.memState = memState;
	}

	public String getDrRole() {
		if(StringUtils.isNotBlank(drRole)) {
			return drRole;
		}else {
			return null;
		}
	}

	public void setDrRole(String drRole) {
		this.drRole = drRole;
	}

	/**
	 * 工作类型名称
	 * @return
	 */
	public String getMemWorkTypeName() throws Exception{
		if(StringUtils.isNotBlank(this.getMemWorkType())){
			SysDao dao =  (SysDao) SpringHelper.getBean("sysDao");
			CdCode value = dao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_WORKTYPE, this.getMemWorkType());
			if(value!=null) {
                return value.getCodeTitle();
            }
		}
		return "";
	}

	public String getHospName(){
		if(StringUtils.isNotBlank(this.getMemTeamid())){
			SysDao dao =  (SysDao) SpringHelper.getBean("sysDao");
			AppTeam team = (AppTeam)dao.getServiceDo().find(AppTeam.class,this.getMemTeamid());
			if(team!=null){
				if(StringUtils.isNotBlank(team.getTeamHospName())){
					return team.getTeamHospName();
				}
			}
		}
		return "";
	}

	/**
	 * 获取职称名称
	 * @return
	 */
	public String getDrRoleName() throws Exception{
		if(StringUtils.isNotBlank(this.getDrRole())){
			SysDao dao = (SysDao)SpringHelper.getBean("sysDao");
			CdCode value = dao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_MEMBERSTITLE, this.getDrRole());
			if(value!=null){
				return value.getCodeTitle();
			}
		}
		return "";
	}
}