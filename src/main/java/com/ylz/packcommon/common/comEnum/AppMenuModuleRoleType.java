package com.ylz.packcommon.common.comEnum;

/**
 * 模块权限
 *
 */
public enum AppMenuModuleRoleType {

	/**
	 * 居民
	 */
	PATIENT("1"),
	/**
	 * 医生
	 */
	DR("2"),
	/**
	 * 管理
	 */
	MANAGE("3"),
	/**
	 * 手持机
	 */
	POSS("4");
	private String value;
	private AppMenuModuleRoleType(String value){
		this.value = value;
	}
	
	public String getValue(){
		return this.value;
	}
	
}
