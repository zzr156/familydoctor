/**
 * 
 */
package com.ylz.bizDo.jtapp.basicHealthEntity.findpersonandjzinfo;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import java.io.Serializable;


/**
 *<p>Title:PersonInfo</p> 
 *<p>Description:</p>
 *<p>Company:ylz</p>
 *@author cairongjie
 *@date 2016-6-21 下午1:01:58
 */
public class PersonInfo implements Serializable{
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 健康档案号
	 */
	private String healthId;
	/**
	 * 社保卡号
	 */
    private String ssnumber;
    /**
	 * 姓名
	 */
    private String name;
    /**
     * 性别
     */
    private String gender;
    /**
	 * 民族
	 */
    private String nation;
    /**
	 * 出生日期
	 */
    private String birthday;
    /**
	 * 身份证号
	 */
    private String idcard;
    
    /**
     * 常住地址户籍标志
     */
    private String addressSign;
    /**
     * 地址类别
     */
    private String addressClass;
    
    /**
     * 地址
     */
    private String address;
    /**
     * 出生地址
     */
    private String birthAddress;
   /* *//**
     * 省
     *//*
    private String province;
    *//**
     * 市
     *//*
    private String city;
    *//**
     * 县
     *//*
    private String county;
    *//**
     * 乡
     *//*
    private String town;
    *//**
     * 村
     *//*
    private String village;
    *//**
     * 门牌号码
     *//*
    private String numberCode;*/
    /**
	 * 电子邮箱
	 */
    private String email;
    /**
     * 联系电话类别
     */
    private String phoneClass;
    /**
     * 联系电话类别代码
     */
    private String phoneCode;
    /**
	 * 联系电话
	 */
    private String telephone;
    /**
     * 机构名称
     */
    private String organizationName;
    /**
     *建档日期
     */
    private String recordDate;
    /**
     * 邮政编码
     */
    private String postCode;
    /**
     * 籍贯
     */
    private String nativestr;
    /**
     * 病人id
     */
    private String patientId;
    /**
     * 临时身份证
     */
    private String tmpIdcard;
    /**
     * 临时社保卡
     */
    private String tmpSsnumber;
    
    /**
	 * 默认构造函数
	 */
	public PersonInfo() {
	}
	 
	
	
	public String getHealthId() {
		return healthId;
	}


	public void setHealthId(String healthId) {
		this.healthId = healthId;
	}


	public String getSsnumber() {
		return ssnumber;
	}
	public void setSsnumber(String ssnumber) {
		this.ssnumber = ssnumber;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getNation() {
		return nation;
	}
	public void setNation(String nation) {
		this.nation = nation;
	}
	 
	/**
	 * @return the birthday
	 */
	public String getBirthday() {
		return birthday;
	}


	/**
	 * @return the tmpIdcard
	 */
	public String getTmpIdcard() {
		return tmpIdcard;
	}



	/**
	 * @param tmpIdcard the tmpIdcard to set
	 */
	public void setTmpIdcard(String tmpIdcard) {
		this.tmpIdcard = tmpIdcard;
	}



	/**
	 * @return the tmpSsnumber
	 */
	public String getTmpSsnumber() {
		return tmpSsnumber;
	}



	/**
	 * @param tmpSsnumber the tmpSsnumber to set
	 */
	public void setTmpSsnumber(String tmpSsnumber) {
		this.tmpSsnumber = tmpSsnumber;
	}



	/**
	 * @return the patientId
	 */
	public String getPatientId() {
		return patientId;
	}



	/**
	 * @param patientId the patientId to set
	 */
	public void setPatientId(String patientId) {
		this.patientId = patientId;
	}



	/**
	 * @param birthday the birthday to set
	 */
	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}


	public String getIdcard() {
		return idcard;
	}
	public void setIdcard(String idcard) {
		this.idcard = idcard;
	}
	
	/**
	 * @return the addressSign
	 */
	public String getAddressSign() {
		return addressSign;
	}


	/**
	 * @param addressSign the addressSign to set
	 */
	public void setAddressSign(String addressSign) {
		this.addressSign = addressSign;
	}


	/**
	 * @return the addressClass
	 */
	public String getAddressClass() {
		return addressClass;
	}


	/**
	 * @param addressClass the addressClass to set
	 */
	public void setAddressClass(String addressClass) {
		this.addressClass = addressClass;
	}


	
	/**
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}



	/**
	 * @param address the address to set
	 */
	public void setAddress(String address) {
		this.address = address;
	}



	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	 
	
	/**
	 * @return the phoneClass
	 */
	public String getPhoneClass() {
		return phoneClass;
	}


	/**
	 * @param phoneClass the phoneClass to set
	 */
	public void setPhoneClass(String phoneClass) {
		this.phoneClass = phoneClass;
	}


	/**
	 * @return the phoneCode
	 */
	public String getPhoneCode() {
		return phoneCode;
	}


	/**
	 * @param phoneCode the phoneCode to set
	 */
	public void setPhoneCode(String phoneCode) {
		this.phoneCode = phoneCode;
	}


	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	
	
	/**
	 * @return the organizationName
	 */
	public String getOrganizationName() {
		return organizationName;
	}


	/**
	 * @param organizationName the organizationName to set
	 */
	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
	}


	public int hashCode() {
		return (new HashCodeBuilder(17, 37)).append(getHealthId()).append(
				getIdcard()).append(getSsnumber()).toHashCode();
		
	}

	/**
	 * @return the recordDate
	 */
	public String getRecordDate() {
		return recordDate;
	}



	/**
	 * @return the postCode
	 */
	public String getPostCode() {
		return postCode;
	}



	/**
	 * @param postCode the postCode to set
	 */
	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}



	/**
	 * @return the birthAddress
	 */
	public String getBirthAddress() {
		return birthAddress;
	}



	/**
	 * @param birthAddress the birthAddress to set
	 */
	public void setBirthAddress(String birthAddress) {
		this.birthAddress = birthAddress;
	}



	/**
	 * @return the gender
	 */
	public String getGender() {
		return gender;
	}



	/**
	 * @param gender the gender to set
	 */
	public void setGender(String gender) {
		this.gender = gender;
	}



	/**
	 * @param recordDate the recordDate to set
	 */
	public void setRecordDate(String recordDate) {
		this.recordDate = recordDate;
	}



	/**
	 * @return the nativestr
	 */
	public String getNativestr() {
		return nativestr;
	}



	/**
	 * @param nativestr the nativestr to set
	 */
	public void setNativestr(String nativestr) {
		this.nativestr = nativestr;
	}



	public boolean equals(Object o) {
		if (!(o instanceof PersonInfo)) {
			return false;
		}
		if (o == this) {
            return true;
        }
		PersonInfo me = (PersonInfo) o;
		return new EqualsBuilder().append(getHealthId(), me.getHealthId())
				.append(getIdcard(), me.getIdcard()).append(getSsnumber(), me.getSsnumber()).isEquals();
	}

    
}

