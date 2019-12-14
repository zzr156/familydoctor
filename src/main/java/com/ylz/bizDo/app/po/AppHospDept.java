package com.ylz.bizDo.app.po;

import com.ylz.bizDo.cd.po.CdCode;
import com.ylz.packaccede.allDo.SysDao;
import com.ylz.packcommon.common.CodeGroupConstrats;
import com.ylz.packcommon.common.bean.BasePO;
import com.ylz.packcommon.common.bizDo.SpringHelper;
import org.apache.commons.lang.StringUtils;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * 医院表
 */
@Entity
@Table(name = "APP_HOSP_DEPT")
public class AppHospDept extends BasePO{

	// Fields
	@Id
	@Column(name = "ID", unique = true, nullable = false, length = 36)
	@GeneratedValue(generator = "paymentableGenerator")
	@GenericGenerator(name = "paymentableGenerator", strategy = "uuid2")
	private String id;//主键
	@Column(name = "HOSP_NAME", length = 70)
	private String hospName;//医院名称
	@Column(name = "HOSP_CODE", length = 20)
	private String hospCode;//医院编码
	@Column(name = "HOSP_CITYAREA_ID", length = 36)
	private String hospCityareaId;//区域主键
	@Column(name = "HOSP_UPCITYAREA_ID", length = 36)
	private String hospUpcityareaId;//区域上级主键
	@Column(name = "HOSP_AREA_CODE", length = 20)
	private String hospAreaCode;//区域编码
	@Column(name = "HOSP_ADDRESS", length = 50)
	private String hospAddress;//医院地址
	@Column(name = "HOSP_TEL", length = 15)
	private String hospTel;//医院电话
	@Column(name = "HOSP_INTRO", length = 65535)
	private String hospIntro;//医院简介
	@Column(name = "HOSP_IMAGEURL", length = 100)
	private String hospImageurl;//医院图片
	@Column(name = "HOSP_LEVEL", length = 10)
	private String hospLevel;//等级 1省,2市,3区,4..社区
	@Column(name = "HOSP_STATE", length = 10)
	private String hospState;// 状态 1 开启 0 关闭
	@Column(name = "HOSP_PAGE_STYLE", length = 6)
	private String hospPageStyle;//页面风格
	@Column(name = "HOSP_SER_STATE",length = 10)
	private String hospSerState;//是否开启签约服务 否0 是1
	@Column(name = "HOSP_LEVEL_TYPE",length = 10)
	private String hospLevelType="7";//上级医院：0综合医院 1专科医院 2中医医院 3中西医结合医院 4民族医医院 5康复医院 6妇幼保健院  基层医院：7社区卫生服务中心（站） 8中心卫生院 9乡镇卫生院
	@Column(name = "HOSP_MEAL_ID",length = 36)
	private String hospMealId;//医院默认服务包主键
	@Column(name = "HOSP_CACHET_ABROAB_URL",length = 200)
	private String hospCachetAbroabUrl;//公章外网图片地址
	@Column(name = "HOSP_CACHET_WITHIN_URL",length = 200)
	private String hospCachetWithinUrl;//公章内网图片地址
	@Column(name = "HOSP_LABEL_STATE",length = 5)
	private String labelState;//疾病标签权限（0：未开启，1：开启）
	@Column(name = "HOSP_X",length = 10)
	private String hospX;//机构x坐标
	@Column(name = "HOSP_Y",length = 10)
	private String hospY;//机构y坐标
	// Constructors

	/** default constructor */
	public AppHospDept() {
	}

	/** minimal constructor */
	public AppHospDept(String id) {
		this.id = id;
	}

	/** full constructor */
	public AppHospDept(String id, String hospName, String hospCode,
			String hospCityareaId, String hospUpcityareaId,
			String hospAreaCode, String hospAddress, String hospTel,
			String hospIntro, String hospImageurl, String hospLevel,
			String hospState, String hospPageStyle,String hospSerState,
					   String hospLevelType,String hospMealId,String hospCachetAbroabUrl,
					   String hospCachetWithinUrl,String labelState,String hospX,String hospY) {
		this.id = id;
		this.hospName = hospName;
		this.hospCode = hospCode;
		this.hospCityareaId = hospCityareaId;
		this.hospUpcityareaId = hospUpcityareaId;
		this.hospAreaCode = hospAreaCode;
		this.hospAddress = hospAddress;
		this.hospTel = hospTel;
		this.hospIntro = hospIntro;
		this.hospImageurl = hospImageurl;
		this.hospLevel = hospLevel;
		this.hospState = hospState;
		this.hospPageStyle = hospPageStyle;
		this.hospSerState = hospSerState;
		this.hospLevelType = hospLevelType;
		this.hospMealId = hospMealId;
		this.hospCachetWithinUrl = hospCachetWithinUrl;
		this.hospCachetAbroabUrl = hospCachetAbroabUrl;
		this.labelState=labelState;
		this.hospX = hospX;
		this.hospY = hospY;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getHospName() {
		return this.hospName;
	}

	public void setHospName(String hospName) {
		this.hospName = hospName;
	}

	public String getHospCode() {
		if(StringUtils.isBlank(this.hospCode)){
			return "";
		}
		return this.hospCode;
	}

	public void setHospCode(String hospCode) {
		this.hospCode = hospCode;
	}

	public String getHospCityareaId() {
		return this.hospCityareaId;
	}

	public void setHospCityareaId(String hospCityareaId) {
		this.hospCityareaId = hospCityareaId;
	}

	public String getHospUpcityareaId() {
		return this.hospUpcityareaId;
	}

	public void setHospUpcityareaId(String hospUpcityareaId) {
		this.hospUpcityareaId = hospUpcityareaId;
	}

	public String getHospAreaCode() {
		return this.hospAreaCode;
	}

	public void setHospAreaCode(String hospAreaCode) {
		this.hospAreaCode = hospAreaCode;
	}

	public String getHospAddress() {
		return this.hospAddress;
	}

	public void setHospAddress(String hospAddress) {
		this.hospAddress = hospAddress;
	}

	public String getHospTel() {
		return this.hospTel;
	}

	public void setHospTel(String hospTel) {
		this.hospTel = hospTel;
	}

	public String getHospIntro() {
		return this.hospIntro;
	}

	public void setHospIntro(String hospIntro) {
		this.hospIntro = hospIntro;
	}

	public String getHospImageurl() {
		return this.hospImageurl;
	}

	public void setHospImageurl(String hospImageurl) {
		this.hospImageurl = hospImageurl;
	}

	public String getHospLevel() {
		return this.hospLevel;
	}

	public void setHospLevel(String hospLevel) {
		this.hospLevel = hospLevel;
	}

	public String getHospState() {
		return this.hospState;
	}

	public void setHospState(String hospState) {
		this.hospState = hospState;
	}

	public String getHospPageStyle() {
		return this.hospPageStyle;
	}

	public void setHospPageStyle(String hospPageStyle) {
		this.hospPageStyle = hospPageStyle;
	}

	public String getHospSerState() {
		return hospSerState;
	}

	public void setHospSerState(String hospSerState) {
		this.hospSerState = hospSerState;
	}

	//级别名称
	public String getHospLevelName() throws Exception{
		if(StringUtils.isNotBlank(this.getHospLevel())){
			SysDao dao = (SysDao) SpringHelper.getBean("sysDao");
			CdCode value = dao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_HOSPLEVEL,this.getHospLevel());
			if(value != null){
				return value.getCodeTitle();
			}
		}
		return "";
	}
	//状态名称
	public String getHospStateName() throws Exception{
		if(StringUtils.isNotBlank(this.getHospState())){
			SysDao dao = (SysDao) SpringHelper.getBean("sysDao");
			CdCode value = dao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_ENABLE,this.getHospState());
			if(value != null){
				return value.getCodeTitle();
			}
		}
		return "";
	}

	public String getHospLevelType() {
		return hospLevelType;
	}

	public void setHospLevelType(String hospLevelType) {
		this.hospLevelType = hospLevelType;
	}

	public String getHospMealId() {
		return hospMealId;
	}

	public void setHospMealId(String hospMealId) {
		this.hospMealId = hospMealId;
	}

	public String getHospCachetAbroabUrl() {
		return hospCachetAbroabUrl;
	}

	public void setHospCachetAbroabUrl(String hospCachetAbroabUrl) {
		this.hospCachetAbroabUrl = hospCachetAbroabUrl;
	}

	public String getHospCachetWithinUrl() {
		return hospCachetWithinUrl;
	}

	public void setHospCachetWithinUrl(String hospCachetWithinUrl) {
		this.hospCachetWithinUrl = hospCachetWithinUrl;
	}

	public String getLabelState() {
		return labelState;
	}

	public void setLabelState(String labelState) {
		this.labelState = labelState;
	}

	public String getHospX() {
		return hospX;
	}

	public void setHospX(String hospX) {
		this.hospX = hospX;
	}

	public String getHospY() {
		return hospY;
	}

	public void setHospY(String hospY) {
		this.hospY = hospY;
	}
}