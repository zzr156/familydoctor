package com.ylz.bizDo.cd.po;

import com.ylz.packaccede.allDo.SysDao;
import com.ylz.packcommon.common.bean.BasePO;
import com.ylz.packcommon.common.bizDo.SpringHelper;
import org.apache.commons.lang.StringUtils;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * Created by PC on 2017/2/28.
 * 地址信息
 */
@Entity
@Table(name="CP_CITY_AREA")
@org.hibernate.annotations.Cache(usage= CacheConcurrencyStrategy.READ_WRITE)//要使用hibernate的二级缓存Cache的注释一定要添加上
public class CdAddress extends BasePO {
	@Id
    @Column(name = "CITY_AREA_ID", unique = true, nullable = false, length = 36)
    @GeneratedValue(generator = "paymentableGenerator")
    @GenericGenerator(name = "paymentableGenerator", strategy = "uuid2")
    private String id;//主键
	@Column(name = "UP_CITY_AREA_ID",length = 36)
	private String pid;//父id自关联
    @Column(name = "AREA_CODE")
    private String ctcode;//编码
    @Column(name = "AREA_NAME",length = 50)
    private String areaName;//地址全称
    @Column(name = "LEVEL_NAME",length = 38)
    private String level;//等级
    @Column(name = "STATE",length = 10)
    private String state;//状态
    @Column(name = "AREA_SNAME",length = 50)
    private String areaSname;//地址简称
	@Column(name = "AREA_ALIAS",length = 50)
	private String areaAlias;//别名

	public CdAddress() {
	}

	public CdAddress(String id){
		this.id=id;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
	public String getCtcode() {
		return ctcode;
	}
	public void setCtcode(String ctcode) {
		this.ctcode = ctcode;
	}
	public String getAreaName() {
		return areaName;
	}
	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}
	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getAreaSname() {
		return areaSname;
	}
	public void setAreaSname(String areaSname) {
		this.areaSname = areaSname;
	}

	public String getAreaAlias() {
		return areaAlias;
	}

	public void setAreaAlias(String areaAlias) {
		this.areaAlias = areaAlias;
	}

	public String getAddrPName(){
		if(StringUtils.isNotBlank(this.getPid())){
			SysDao dao =  (SysDao) SpringHelper.getBean("sysDao");
			CdAddress p = (CdAddress)dao.getServiceDo().find(CdAddress.class, this.getPid());
				if(p != null) {
                    return p.getAreaName();
                }
		}
		return "";
	}
    
}
