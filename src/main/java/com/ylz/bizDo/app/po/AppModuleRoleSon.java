package com.ylz.bizDo.app.po;

import com.ylz.packcommon.common.bean.BasePO;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * AppModuleRoleSon entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "APP_MODULE_ROLE_SON")
public class AppModuleRoleSon extends BasePO {

	// Fields
	@Id
	@Column(name = "ID", unique = true, nullable = false, length = 36)
	@GeneratedValue(generator = "paymentableGenerator")
	@GenericGenerator(name = "paymentableGenerator", strategy = "uuid2")
	private String id;//主键
	@Column(name = "SON_MODULE_ROLE_ID", length = 36)
	private String sonModuleRoleId;//模块权限主键
	@Column(name = "SON_MENU_ID", length = 36)
	private String sonMenuId;//功能菜单主键

	// Constructors

	/** default constructor */
	public AppModuleRoleSon() {
	}

	/** minimal constructor */
	public AppModuleRoleSon(String id) {
		this.id = id;
	}

	/** full constructor */
	public AppModuleRoleSon(String id, String sonModuleRoleId, String sonMenuId) {
		this.id = id;
		this.sonModuleRoleId = sonModuleRoleId;
		this.sonMenuId = sonMenuId;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSonModuleRoleId() {
		return this.sonModuleRoleId;
	}

	public void setSonModuleRoleId(String sonModuleRoleId) {
		this.sonModuleRoleId = sonModuleRoleId;
	}

	public String getSonMenuId() {
		return this.sonMenuId;
	}

	public void setSonMenuId(String sonMenuId) {
		this.sonMenuId = sonMenuId;
	}

}