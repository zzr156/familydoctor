package com.ylz.bizDo.app.po;

import com.ylz.bizDo.cd.po.CdCode;
import com.ylz.bizDo.cd.po.CdUser;
import com.ylz.packaccede.allDo.SysDao;
import com.ylz.packcommon.common.CodeGroupConstrats;
import com.ylz.packcommon.common.bean.BasePO;
import com.ylz.packcommon.common.bizDo.SpringHelper;
import org.apache.commons.lang.StringUtils;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * 标签管理
 */
@Entity
@Table(name = "APP_LABEL_MANAGE")
public class AppLabelManage extends BasePO {

	// Fields
	@Id
	@Column(name = "ID", unique = true, nullable = false, length = 36)
	@GeneratedValue(generator = "paymentableGenerator")
	@GenericGenerator(name = "paymentableGenerator", strategy = "uuid2")
	private String id;//主键
	@Column(name = "LABEL_TITLE", length = 30)
	private String labelTitle;// 标签名称
	@Column(name = "LABEL_VALUE", length = 30)
	private String labelValue;//标签对应值
	@Column(name = "LABEL_TYPE", length = 6)
	private String labelType;//标签类型 1健康情况,2疾病情况,自定义
	@Column(name = "LABEL_PATIENT_ID", length = 36)
	private String labelPatientId;//创建标签用户
//	@Column(name = "LABEL_SORT", length = 6)
//	private String labelSort;//排序

	// Constructors

	/** default constructor */
	public AppLabelManage() {
	}

	/** minimal constructor */
	public AppLabelManage(String id) {
		this.id = id;
	}

	/** full constructor */
	public AppLabelManage(String id, String labelTitle, String labelValue,
			String labelType, String labelPatientId) {
		this.id = id;
		this.labelTitle = labelTitle;
		this.labelValue = labelValue;
		this.labelType = labelType;
		this.labelPatientId = labelPatientId;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getLabelTitle() {
		return this.labelTitle;
	}

	public void setLabelTitle(String labelTitle) {
		this.labelTitle = labelTitle;
	}

	public String getLabelValue() {
		return this.labelValue;
	}

	public void setLabelValue(String labelValue) {
		this.labelValue = labelValue;
	}

	public String getLabelType() {
		return this.labelType;
	}

	public void setLabelType(String labelType) {
		this.labelType = labelType;
	}

	public String getLabelPatientId() {
		return this.labelPatientId;
	}

	public void setLabelPatientId(String labelPatientId) {
		this.labelPatientId = labelPatientId;
	}

	/**
	 * 标签名称
	 * @return
	 */
	public String getLabelTypeName() throws Exception{
		SysDao dao = (SysDao) SpringHelper.getBean("sysDao");
		if(StringUtils.isNotBlank(this.getLabelType())){
			CdCode value = dao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_LABELTYPE, this.getLabelType());
			if(value!=null) {
                return value.getCodeTitle();
            }
		}
		return  "";
	}

	/**
	 * 创建者姓名
	 * @return
	 */
	public String getLabelPatientName(){
		SysDao dao = (SysDao) SpringHelper.getBean("sysDao");
		if(StringUtils.isNotBlank(this.getLabelPatientId())){
			CdUser user = (CdUser) dao.getServiceDo().find(CdUser.class,this.getLabelPatientId());
			if(user!=null){
				if(StringUtils.isNotBlank(user.getUserName())){
					return user.getUserName();
				}
			}
		}
		return "";
	}

//	public String getLabelSort() {
//		return labelSort;
//	}
//
//	public void setLabelSort(String labelSort) {
//		this.labelSort = labelSort;
//	}
}