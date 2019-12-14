package com.ylz.bizDo.app.po;

import com.ylz.packcommon.common.bean.BasePO;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * AppMeunRole entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "APP_MEUN_ROLE")
public class AppMeunRole extends BasePO {

	// Fields
	@Id
	@Column(name = "ID", unique = true, nullable = false, length = 36)
	@GeneratedValue(generator = "paymentableGenerator")
	@GenericGenerator(name = "paymentableGenerator", strategy = "uuid2")
	private String id;
	@Column(name = "MEUN_ROLE_ID", length = 36)
	private String meunRoleId;
	@Column(name = "MENU_DR_ID", length = 36)
	private String menuDrId;
	@Column(name = "MEUN_ROLE_NAME", length = 20)
	private String meunRoleName;
	@Column(name = "MENU_ROLE_VALUE", length = 10)
	private String menuRoleValue;

	// Constructors

	/** default constructor */
	public AppMeunRole() {
	}

	/** minimal constructor */
	public AppMeunRole(String id) {
		this.id = id;
	}

	/** full constructor */
	public AppMeunRole(String id, String meunRoleId, String menuDrId,
					   String meunRoleName, String menuRoleValue) {
		this.id = id;
		this.meunRoleId = meunRoleId;
		this.menuDrId = menuDrId;
		this.meunRoleName = meunRoleName;
		this.menuRoleValue = menuRoleValue;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMeunRoleId() {
		return this.meunRoleId;
	}

	public void setMeunRoleId(String meunRoleId) {
		this.meunRoleId = meunRoleId;
	}

	public String getMenuDrId() {
		return this.menuDrId;
	}

	public void setMenuDrId(String menuDrId) {
		this.menuDrId = menuDrId;
	}

	public String getMeunRoleName() {
		return this.meunRoleName;
	}

	public void setMeunRoleName(String meunRoleName) {
		this.meunRoleName = meunRoleName;
	}

	public String getMenuRoleValue() {
		return this.menuRoleValue;
	}

	public void setMenuRoleValue(String menuRoleValue) {
		this.menuRoleValue = menuRoleValue;
	}

}