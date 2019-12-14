package com.ylz.bizDo.app.po;

import com.ylz.bizDo.cd.po.CdCode;
import com.ylz.bizDo.jtapp.drEntity.AppDrUserEntity;
import com.ylz.packaccede.allDo.SysDao;
import com.ylz.packcommon.common.CodeGroupConstrats;
import com.ylz.packcommon.common.bean.BasePO;
import com.ylz.packcommon.common.bizDo.SpringHelper;
import com.ylz.packcommon.common.util.ExtendDate;
import org.apache.commons.lang.StringUtils;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Calendar;

/**医生团队表
 * AppTeam entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "APP_TEAM")
public class AppTeam extends BasePO {

	// Fields
	@Id
	@Column(name = "ID", unique = true, nullable = false, length = 36)
	@GeneratedValue(generator = "paymentableGenerator")
	@GenericGenerator(name = "paymentableGenerator", strategy = "uuid2")
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
	private String teamType;//团队类型 0家签,1三师共管,2居家养老
	@Column(name = "TEAM_EASE_GROUP_ID",length = 50)
	private String teamEaseGroupId;//环信团队主键
	@Column(name = "TEAM_EASE_GROUP_NAME",length = 50)
	private String teamEaseGroupName;//环信团队名称
	@Column(name = "TEAM_EASE_ROOM_ID",length = 50)
	private String teamEaseRoomId;//聊天室主键
	@Column(name = "TEAM_EASE_ROOM_NAME",length = 50)
	private String teamEaseRoomName;//环信聊天名称
	@Column(name = "TEAM_DEL_STATE",length = 10)
	private String teamDelState= "0" ;//团队删除状态(1已删除 默认0正常)
	@Column(name = "TEAM_DEL_TIME")
	private Calendar teamDelTime;//删除时间


	/*@Column(name = "TEAM_PEOPLE",length = 10)
	private String teamPeople;//团队目标量*/
	// Constructors
	public AppTeam(){

	}
	/** minimal constructor */
	public AppTeam(String id) {
		this.id = id;
	}

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
		if(StringUtils.isBlank(this.teamCode)){
			return "";
		}
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


	public String getTeamDrName() throws Exception {
		SysDao dao = (SysDao) SpringHelper.getBean("sysDao");
		AppDrUserEntity entity = dao.getAppDrUserDao().findUserById(teamDrId);
		if(entity != null && StringUtils.isNotBlank(entity.getDrName())){
			return entity.getDrName();
		}else {
			if(StringUtils.isBlank(this.teamDrName)){
				return "";
			}
		}
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

	public String getTeamEaseRoomId() {
		return teamEaseRoomId;
	}

	public void setTeamEaseRoomId(String teamEaseRoomId) {
		this.teamEaseRoomId = teamEaseRoomId;
	}

	/**
	 * 有效标识名称
	 * @return
	 */
	public String getTeamStateName() throws Exception{
		if(StringUtils.isNotBlank(this.getTeamState())){
			SysDao dao =  (SysDao) SpringHelper.getBean("sysDao");
			CdCode value = dao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_TEAM_STATE, this.getTeamState());
			if(value!=null) {
                return value.getCodeTitle();
            }
		}
		return "";
	}

	/**
	 * 处理时间
	 * @return
	 */
	public String getStrTeamCreateTime(){
		if(this.getTeamCreateTime()!=null){
			return ExtendDate.getYMD(this.getTeamCreateTime());
		}else{
			return "";
		}
	}

    /**
     * 类型名称
     * @return
     */
	public String getStrTypeName() throws Exception{
        if(StringUtils.isNotBlank(this.getTeamType())){
            SysDao dao =  (SysDao) SpringHelper.getBean("sysDao");
            CdCode value = dao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_TEAMTYPE, this.getTeamType());
            if(value!=null) {
                return value.getCodeTitle();
            }
        }
        return "";
    }

	public String getTeamEaseGroupId() {
		return teamEaseGroupId;
	}

	public void setTeamEaseGroupId(String teamEaseGroupId) {
		this.teamEaseGroupId = teamEaseGroupId;
	}


	public String getTeamEaseGroupName() {
		return teamEaseGroupName;
	}

	public void setTeamEaseGroupName(String teamEaseGroupName) {
		this.teamEaseGroupName = teamEaseGroupName;
	}

	public String getTeamEaseRoomName() {
		return teamEaseRoomName;
	}

	public void setTeamEaseRoomName(String teamEaseRoomName) {
		this.teamEaseRoomName = teamEaseRoomName;
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