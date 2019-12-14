package com.ylz.bizDo.app.po;

import com.ylz.packcommon.common.bean.BasePO;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 *  服务设置表权限表菜单项
 */
@Entity
@Table(name = "APP_SERVE_ROLE_SON")
public class AppServeRoleSon extends BasePO {

	// Fields
	@Id
	@Column(name = "ID", unique = true, nullable = false, length = 36)
	@GeneratedValue(generator = "paymentableGenerator")
	@GenericGenerator(name = "paymentableGenerator", strategy = "uuid2")
	private String id;//主键
	@Column(name = "SON_SERVE_ROLE_ID", length = 36)
	private String sonServeRoleId;//模块权限主键
	@Column(name = "SON_SERVE_ID", length = 36)
	private String sonServeId;//功能菜单主键
	@Column(name = "SON_SERVE_SET_NUM", length = 36)
	private String sonServeSetNum;//次数
	@Column(name = "SON_SERVE_SET_SPACE", length = 36)
	private String sonServeSetSpace;//间隔
	@Column(name = "SON_SERVE_SET_SPACE_TYPE", length = 36)
	private String sonServeSetSpaceType;//间隔类型

	// Constructors

	/** default constructor */
	public AppServeRoleSon() {
	}

	/** minimal constructor */
	public AppServeRoleSon(String id) {
		this.id = id;
	}

	/** full constructor */
	public AppServeRoleSon(String id, String sonServeRoleId, String sonServeId,String sonServeSetNum) {
		this.id = id;
		this.sonServeRoleId = sonServeRoleId;
		this.sonServeId = sonServeId;
		this.sonServeSetNum = sonServeSetNum;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSonServeRoleId() {
		return sonServeRoleId;
	}

	public void setSonServeRoleId(String sonServeRoleId) {
		this.sonServeRoleId = sonServeRoleId;
	}

	public String getSonServeId() {
		return sonServeId;
	}

	public void setSonServeId(String sonServeId) {
		this.sonServeId = sonServeId;
	}

	public String getSonServeSetNum() {
		return sonServeSetNum;
	}

	public void setSonServeSetNum(String sonServeSetNum) {
		this.sonServeSetNum = sonServeSetNum;
	}

	public String getSonServeSetSpace() {
		return sonServeSetSpace;
	}

	public void setSonServeSetSpace(String sonServeSetSpace) {
		this.sonServeSetSpace = sonServeSetSpace;
	}

	public String getSonServeSetSpaceType() {
		return sonServeSetSpaceType;
	}

	public void setSonServeSetSpaceType(String sonServeSetSpaceType) {
		this.sonServeSetSpaceType = sonServeSetSpaceType;
	}
}