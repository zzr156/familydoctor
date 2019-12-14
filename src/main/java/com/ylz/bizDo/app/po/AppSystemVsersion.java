package com.ylz.bizDo.app.po;

import com.ylz.bizDo.cd.po.CdCode;
import com.ylz.packaccede.allDo.SysDao;
import com.ylz.packcommon.common.CodeGroupConstrats;
import com.ylz.packcommon.common.bean.BasePO;
import com.ylz.packcommon.common.bizDo.SpringHelper;
import com.ylz.packcommon.common.util.ExtendDate;
import org.apache.commons.lang.StringUtils;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Calendar;

/**
 * 系统版本更新
 */
@Entity
@Table(name = "APP_SYSTEM_VSERSION")
public class AppSystemVsersion extends BasePO{

	// Fields
	@Id
	@Column(name = "ID", unique = true, nullable = false, length = 36)
	@GeneratedValue(generator = "paymentableGenerator")
	@GenericGenerator(name = "paymentableGenerator", strategy = "uuid2")
	private String id;//主键
	@Column(name = "SYSTEM", length = 10)
	private String system;//手机类型 1.医生端,2居民端
	@Column(name = "TYPE", length = 10)
	private String type;//类型 1安卓,2ios
	@Column(name = "VSERSION_CODE", length = 20)
	private String vsersionCode;//版本号
	@Column(name = "VSERSION_NAME", length = 20)
	private String vsersionName;//版本名称
	@Column(name = "UPDATE_TIME", length = 20)
	private Calendar updateTime;//更新日期
	@Column(name = "CHANGE_LOG", length = 500)
	private String changeLog;//更新内容
	@Column(name = "SYSTEM_FORCE", length = 10)
	private String systemForce;//是否强制更新
	@Column(name = "DOWN_LOAD_URL", length = 200)
	private String downLoadUrl;//下载地址
	@Column(name = "SYSTEM_UPDATE", length = 10)
	private String systemUpdate;//是否更新
	@Column(name = "CREATE_DATE", length = 19)
	private Calendar createDate;//创建时间

	// Constructors

	/** default constructor */
	public AppSystemVsersion() {
	}

	/** minimal constructor */
	public AppSystemVsersion(String id) {
		this.id = id;
	}

	/** full constructor */
	public AppSystemVsersion(String id, String system, String vsersionCode,
			String vsersionName, Calendar updateTime, String changeLog,
			String systemForce, String downLoadUrl, String systemUpdate,
		    Calendar createDate) {
		this.id = id;
		this.system = system;
		this.vsersionCode = vsersionCode;
		this.vsersionName = vsersionName;
		this.updateTime = updateTime;
		this.changeLog = changeLog;
		this.systemForce = systemForce;
		this.downLoadUrl = downLoadUrl;
		this.systemUpdate = systemUpdate;
		this.createDate = createDate;
	}

	// Property accessors
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSystem() {
		return this.system;
	}

	public void setSystem(String system) {
		this.system = system;
	}

	public String getVsersionCode() {
		return this.vsersionCode;
	}

	public void setVsersionCode(String vsersionCode) {
		this.vsersionCode = vsersionCode;
	}

	public String getVsersionName() {
		return this.vsersionName;
	}

	public void setVsersionName(String vsersionName) {
		this.vsersionName = vsersionName;
	}

	public Calendar getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Calendar updateTime) {
		this.updateTime = updateTime;
	}

	public String getChangeLog() {
		return this.changeLog;
	}

	public void setChangeLog(String changeLog) {
		this.changeLog = changeLog;
	}

	public String getSystemForce() {
		return systemForce;
	}

	public void setSystemForce(String systemForce) {
		this.systemForce = systemForce;
	}

	public String getDownLoadUrl() {
		return this.downLoadUrl;
	}

	public void setDownLoadUrl(String downLoadUrl) {
		this.downLoadUrl = downLoadUrl;
	}

	public String getSystemUpdate() {
		return systemUpdate;
	}

	public void setSystemUpdate(String systemUpdate) {
		this.systemUpdate = systemUpdate;
	}

	public Calendar getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Calendar createDate) {
		this.createDate = createDate;
	}

	public String getStrSystem() throws Exception{
		if(StringUtils.isNotBlank(this.getSystem())){
			SysDao dao = (SysDao) SpringHelper.getBean("sysDao");
			CdCode value = dao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_SYSTEM_CODE,this.getSystem());
			if(value != null) {
                return value.getCodeTitle();
            }
		}
		return "";
	}

	public String getStrUpdateTime(){
		return ExtendDate.getYMD_h_m(this.getUpdateTime());
	}


	public String getStrSystemFace() throws Exception{
		if(StringUtils.isNotBlank(this.getSystemForce())){
			SysDao dao = (SysDao) SpringHelper.getBean("sysDao");
			CdCode value = dao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_SYSTEM_TRUE_FALSE,this.getSystemForce());
			if(value != null) {
                return value.getCodeTitle();
            }
		}
		return "";
	}


	public String getStrSystemUpdate() throws Exception{
		if(StringUtils.isNotBlank(this.getSystemUpdate())){
			SysDao dao = (SysDao) SpringHelper.getBean("sysDao");
			CdCode value = dao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_SYSTEM_TRUE_FALSE,this.getSystemUpdate());
			if(value != null) {
                return value.getCodeTitle();
            }
		}
		return "";
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}


	public String getStrType(){
		if(StringUtils.isNotBlank(this.getType())){
			if(this.getType().equals("1")){
				return "Android";
			}else if(this.getType().equals("2")){
				return "Ios";
			}else if(this.getType().equals("3")){
				return "一体机";
			}else if(this.getType().equals("4")){
				return "POS机";
			}
		}
		return "";
	}
}