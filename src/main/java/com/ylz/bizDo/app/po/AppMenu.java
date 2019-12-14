package com.ylz.bizDo.app.po;

import com.ylz.bizDo.cd.po.CdCode;
import com.ylz.packaccede.allDo.SysDao;
import com.ylz.packcommon.common.CodeGroupConstrats;
import com.ylz.packcommon.common.bean.BasePO;
import com.ylz.packcommon.common.bizDo.SpringHelper;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * AppMenu entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "APP_MENU")
public class AppMenu extends BasePO {

	// Fields
	@Id
	@Column(name = "ID", unique = true, nullable = false, length = 36)
	@GeneratedValue(generator = "paymentableGenerator")
	@GenericGenerator(name = "paymentableGenerator", strategy = "uuid2")
	private String id;//主键
	@Column(name = "MENU_MODULE", length = 15)
	private String menuModule;//菜单模块 1.首页,2.特色服务,3.消息,4.我的
	@Column(name = "MENU_STATE", length = 20)
	private String menuState;//菜单状态
	@Column(name = "MENU_NAME", length = 40)
	private String menuName;//菜单名称
	@Column(name = "MENU_VALUE", length = 20)
	private String menuValue;//菜单值
	@Column(name = "MENU_TYPE", length = 10)
	private String menuType;// 菜单类型 1居民端,2医生端 3管理端
	@Column(name = "MENU_SORT", length = 20)
	private String menuSort;//排序

	// Constructors

	/** default constructor */
	public AppMenu() {
	}

	/** minimal constructor */
	public AppMenu(String id) {
		this.id = id;
	}

	/** full constructor */
	public AppMenu(String id, String menuModule, String menuState,
			String menuName, String menuValue, String menuType, String menuSort) {
		this.id = id;
		this.menuModule = menuModule;
		this.menuState = menuState;
		this.menuName = menuName;
		this.menuValue = menuValue;
		this.menuType = menuType;
		this.menuSort = menuSort;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMenuModule() {
		return menuModule;
	}

	public void setMenuModule(String menuModule) {
		this.menuModule = menuModule;
	}


	public String getMenuState() {
		return this.menuState;
	}

	public void setMenuState(String menuState) {
		this.menuState = menuState;
	}

	public String getMenuName() {
		return this.menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	public String getMenuValue() {
		return this.menuValue;
	}

	public void setMenuValue(String menuValue) {
		this.menuValue = menuValue;
	}

	public String getMenuType() {
		return this.menuType;
	}

	public void setMenuType(String menuType) {
		this.menuType = menuType;
	}

	public String getMenuSort() {
		return this.menuSort;
	}

	public void setMenuSort(String menuSort) {
		this.menuSort = menuSort;
	}

	public String getStrMenuModule() throws Exception{
		SysDao dao =  (SysDao) SpringHelper.getBean("sysDao");
		CdCode value = dao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_APP_MENU_MODULE, this.getMenuModule());
		if(value!=null) {
            return value.getCodeTitle();
        }
		return "";
	}

	public String getStrMenuType() throws Exception{
		SysDao dao =  (SysDao) SpringHelper.getBean("sysDao");
		CdCode value = dao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_APP_MENU_TYPE, this.getMenuType());
		if(value!=null) {
            return value.getCodeTitle();
        }
		return "";
	}


	public String getStrMenuState() throws Exception{
		SysDao dao =  (SysDao) SpringHelper.getBean("sysDao");
		CdCode value = dao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_ENABLE, this.getMenuState());
		if(value!=null) {
            return value.getCodeTitle();
        }
		return "";
	}

}