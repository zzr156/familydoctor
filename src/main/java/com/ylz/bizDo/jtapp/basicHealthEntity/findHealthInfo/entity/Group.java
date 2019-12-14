/**
 * 
 */
package com.ylz.bizDo.jtapp.basicHealthEntity.findHealthInfo.entity;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;
import java.util.List;

/**
 *<p>Title:Group</p> 
 *<p>Description:</p>
 *<p>Company:ylz</p>
 *@author cairongjie
 *@date 2016-6-29 上午8:33:10
 */
@XmlRootElement(name="group")
@XmlType(name="com.start.webInterface.common.dto.byXmlHealthInfo.Group")
@XmlAccessorType(XmlAccessType.NONE)
public class Group implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Type type;
	private Payway payway;
	private String fee;
	private Method method;
	private String freq;
	private String startTime;
	private String endTime;
	private String days;
	
	/*@XmlElementWrapper
	@OneToMany(cascade=CascadeType.ALL)*/
	/*@XmlList
	@XmlElements(value={@XmlElement(name="item",type=Item.class)})*/
	private List<Item> item;
	/*@XmlElements(value={@XmlElement(name="medicine",type=Medicine.class)})*/
	private List<Medicine> medicine;
	
	
	public Group(){}
	public Group(Method method,String freq,String startTime,
			String endTime,String days,List<Medicine> medicine){
		this.method=method;
		this.freq=freq;
		this.startTime=startTime;
		this.endTime=endTime;
		this.days=days;
		this.medicine=medicine;
		
	}
	
	public Group(Payway payway,String fee,Method method,
			String freq,String startTime,String endTime,
			String days,List<Item> item,List<Medicine> medicines){
		this.payway=payway;
		this.fee=fee;
		this.method=method;
		this.freq=freq;
		this.startTime=startTime;
		this.endTime=endTime;
		this.days=days;
		this.item=item;
		this.medicine=medicines;
		
		
	}
	
	/**
	 * @return the type
	 */
	public Type getType() {
		return type;
	}
	/**
	 * @param type the type to set
	 */
	public void setType(Type type) {
		this.type = type;
	}
	/**
	 * @return the payway
	 */
	public Payway getPayway() {
		return payway;
	}
	/**
	 * @param payway the payway to set
	 */
	public void setPayway(Payway payway) {
		this.payway = payway;
	}
	/**
	 * @return the fee
	 */
	public String getFee() {
		return fee;
	}
	/**
	 * @param fee the fee to set
	 */
	public void setFee(String fee) {
		this.fee = fee;
	}	
	/**
	 * @return the method
	 */
	public Method getMethod() {
		return method;
	}
	/**
	 * @param method the method to set
	 */
	public void setMethod(Method method) {
		this.method = method;
	}
	/**
	 * @return the freq
	 */
	public String getFreq() {
		return freq;
	}
	/**
	 * @param freq the freq to set
	 */
	public void setFreq(String freq) {
		this.freq = freq;
	}
	/**
	 * @return the startTime
	 */
	public String getStartTime() {
		return startTime;
	}
	/**
	 * @param startTime the startTime to set
	 */
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	/**
	 * @return the endTime
	 */
	public String getEndTime() {
		return endTime;
	}
	/**
	 * @param endTime the endTime to set
	 */
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	/**
	 * @return the days
	 */
	public String getDays() {
		return days;
	}
	/**
	 * @param days the days to set
	 */
	public void setDays(String days) {
		this.days = days;
	}
	/**
	 * @return the item
	 */
	public List<Item> getItem() {
		return item;
	}
	/**
	 * @param item the item to set
	 */
	public void setItem(List<Item> item) {
		this.item = item;
	}
	/**
	 * @return the medicine
	 */
	public List<Medicine> getMedicine() {
		return medicine;
	}
	/**
	 * @param medicine the medicine to set
	 */
	public void setMedicine(List<Medicine> medicine) {
		this.medicine = medicine;
	}
	

	
}
