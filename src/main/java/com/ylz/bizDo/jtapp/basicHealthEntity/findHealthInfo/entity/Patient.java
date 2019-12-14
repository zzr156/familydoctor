/**
 * 
 */
package com.ylz.bizDo.jtapp.basicHealthEntity.findHealthInfo.entity;

import java.io.Serializable;


/**
 *<p>Title:Patient</p> 
 *<p>Description:</p>
 *<p>Company:ylz</p>
 *@author cairongjie
 *@date 2016-6-28 下午2:15:48
 */

public class Patient implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Id id;
	private String name;
	private Sex sex;
	private String birthDate;
	private Marriage marriage;
	private Value nation;
	private Value work;
	private Value degree;
	private String age;
	private String address;
	private Organization organization;
	private Value country;
	private String idno;
	private String registeredAddress;
	private String telephone;
	private String postalCode;
	private String birthAdd;
	
	
	public Patient(){}
	
	
	public Patient(Id id,String name){
		this.id=id;
		this.name=name;
	}
	public Patient(Id id,String name,Sex sex){
		this.id=id;
		this.name=name;
		this.sex=sex;
	}
	
	public Patient(Id id,String name,Sex sex,String birthDate,
			Marriage marriage){
		this.id=id;
		this.name=name;
		this.sex=sex;
		this.birthDate=birthDate;
		this.marriage=marriage;
		
	}
	
	public Patient(Id id,String name,Sex sex,Value nation,Value work,
			Value degree,String birthAdd,String birthDate,String age,
			Marriage marriage,String address,Organization organization,
			Value country,String idno,String registeredAddress,
			String telephone,String postalCode){
		this.id=id;
		this.name=name;
		this.sex=sex;
		this.nation=nation;
		this.work=work;
		this.degree=degree;
		this.birthAdd=birthAdd;
		this.birthDate=birthDate;
		this.age=age;
		this.marriage=marriage;
		this.address=address;
		this.organization=organization;
		this.country=country;
		this.idno=idno;
		this.registeredAddress=registeredAddress;
		this.telephone=telephone;
		this.postalCode=postalCode;
	}
	
	public Patient(Id id,String name,Sex sex,String birthDate,
			Marriage marriage,Value nation,Value work,
			Value degree,String age,String address,
			Organization organization,Value country,
			String idno,String registeredAddress,
			String telephone,String postalCode){
		this.id=id;
		this.name=name;
		this.sex=sex;
		this.birthDate=birthDate;
		this.marriage=marriage;
		this.nation=nation;
		this.work=work;
		this.degree=degree;
		this.age=age;
		this.address=address;
		this.organization=organization;
		this.country=country;
		this.telephone=telephone;
		this.postalCode=postalCode;
	}
	
	/**
	 * @return the id
	 */
	public Id getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Id id) {
		this.id = id;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the sex
	 */
	public Sex getSex() {
		return sex;
	}
	/**
	 * @param sex the sex to set
	 */
	public void setSex(Sex sex) {
		this.sex = sex;
	}
	/**
	 * @return the birthDate
	 */
	public String getBirthDate() {
		return birthDate;
	}
	/**
	 * @param birthDate the birthDate to set
	 */
	public void setBirthDate(String birthDate) {
		this.birthDate = birthDate;
	}
	/**
	 * @return the marriage
	 */
	public Marriage getMarriage() {
		return marriage;
	}
	/**
	 * @param marriage the marriage to set
	 */
	public void setMarriage(Marriage marriage) {
		this.marriage = marriage;
	}
	/**
	 * @return the nation
	 */
	public Value getNation() {
		return nation;
	}
	/**
	 * @param nation the nation to set
	 */
	public void setNation(Value nation) {
		this.nation = nation;
	}
	/**
	 * @return the work
	 */
	public Value getWork() {
		return work;
	}
	/**
	 * @param work the work to set
	 */
	public void setWork(Value work) {
		this.work = work;
	}
	/**
	 * @return the degree
	 */
	public Value getDegree() {
		return degree;
	}
	/**
	 * @param degree the degree to set
	 */
	public void setDegree(Value degree) {
		this.degree = degree;
	}
	/**
	 * @return the age
	 */
	public String getAge() {
		return age;
	}
	/**
	 * @param age the age to set
	 */
	public void setAge(String age) {
		this.age = age;
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
	/**
	 * @return the organization
	 */
	public Organization getOrganization() {
		return organization;
	}
	/**
	 * @param organization the organization to set
	 */
	public void setOrganization(Organization organization) {
		this.organization = organization;
	}
	/**
	 * @return the country
	 */
	public Value getCountry() {
		return country;
	}
	/**
	 * @param country the country to set
	 */
	public void setCountry(Value country) {
		this.country = country;
	}
	/**
	 * @return the idno
	 */
	public String getIdno() {
		return idno;
	}
	/**
	 * @param idno the idno to set
	 */
	public void setIdno(String idno) {
		this.idno = idno;
	}
	/**
	 * @return the registeredAddress
	 */
	public String getRegisteredAddress() {
		return registeredAddress;
	}
	/**
	 * @param registeredAddress the registeredAddress to set
	 */
	public void setRegisteredAddress(String registeredAddress) {
		this.registeredAddress = registeredAddress;
	}
	/**
	 * @return the telephone
	 */
	public String getTelephone() {
		return telephone;
	}
	/**
	 * @param telephone the telephone to set
	 */
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	/**
	 * @return the postalCode
	 */
	public String getPostalCode() {
		return postalCode;
	}
	/**
	 * @param postalCode the postalCode to set
	 */
	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}


	/**
	 * @return the birthAdd
	 */
	public String getBirthAdd() {
		return birthAdd;
	}


	/**
	 * @param birthAdd the birthAdd to set
	 */
	public void setBirthAdd(String birthAdd) {
		this.birthAdd = birthAdd;
	}
 
	
	
	
 
	
}

