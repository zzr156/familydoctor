/**
 * 
 */
package com.ylz.bizDo.jtapp.basicHealthEntity.findHealthInfo.entity;

import java.io.Serializable;

/**
 *<p>Title:Item</p> 
 *<p>Description:</p>
 *<p>Company:ylz</p>
 *@author cairongjie
 *@date 2016-6-28 下午2:33:45
 */
public class Item implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Icd icd;
	private Result result;
	private Prop prop;
	private String time;
	private Value value;
	private String quantity;
	private String unit;
	private String price;
	private String total;
	private String notes;
	private Dept dept;
	private String bed;
	private String orderTime;
	private String orderDoctor;
	private String checkNurse;
	private String stopTime;
	private String stopDoctor;
	private String stopNurse;
	private String orderCode;
	private String stdCode;
	private String orderName;
	private String freq;
	private String execNurse;
	private String execTime;
	private Type type;
	private Value oper;
	private Value part;
	private String surgeon;
	private String assistant1;
	private String assistant2;
	private Value anesthesia;
	private Value incisionAndHeal;
	
	public Item(){}
	
	public Item(Icd icd){
		this.icd=icd;
	}
	
	public Item(Icd icd,Result result){
		this.icd=icd;
		this.result=result;
	}
	
	public Item(String time,Value value,String quantity,
			String unit,String price,String total,String notes){
		this.time=time;
		this.value=value;
		this.quantity=quantity;
		this.unit=unit;
		this.price=price;
		this.total=total;
		this.notes=notes;
		
	}
	
	public Item(Icd icd,Result result,Prop prop){
		this.icd=icd;
		this.result=result;
		this.prop=prop;
		
	}
	
	public Item(String time,Dept dept,String bed){
		this.time=time;
		this.dept=dept;
		this.bed=bed;
	}
	
	public Item(Type type,String time,String notes){
		this.type=type;
		this.time=time;
		this.notes=notes;
	}
	public Item(Value oper,Value part,String time,String surgeon,
			String assistant1,String assistant2,Value anesthesia,
			Value incisionAndHeal){
		this.oper=oper;
		this.part=part;
		this.time=time;
		this.surgeon=surgeon;
		this.assistant1=assistant1;
		this.assistant2=assistant2;
		this.anesthesia=anesthesia;
		this.incisionAndHeal=incisionAndHeal;
		
	}
	
	public Item(Icd icd,Result result,Prop prop,String time,Value value,String quantity,
			String unit,String price,String total,String notes,Dept dept,String bed){
		this.icd=icd;
		this.result=result;
		this.prop=prop;
		this.time=time;
		this.value=value;
		this.quantity=quantity;
		this.unit=unit;
		this.price=price;
		this.total=total;
		this.notes=notes;
		this.dept=dept;
		this.bed=bed;
		
	}
	/**
	 * @return the icd
	 */
	public Icd getIcd() {
		return icd;
	}
	/**
	 * @param icd the icd to set
	 */
	public void setIcd(Icd icd) {
		this.icd = icd;
	}
	/**
	 * @return the result
	 */
	public Result getResult() {
		return result;
	}
	/**
	 * @param result the result to set
	 */
	public void setResult(Result result) {
		this.result = result;
	}
	/**
	 * @return the prop
	 */
	public Prop getProp() {
		return prop;
	}
	/**
	 * @param prop the prop to set
	 */
	public void setProp(Prop prop) {
		this.prop = prop;
	}
	/**
	 * @return the time
	 */
	public String getTime() {
		return time;
	}
	/**
	 * @param time the time to set
	 */
	public void setTime(String time) {
		this.time = time;
	}
	/**
	 * @return the value
	 */
	public Value getValue() {
		return value;
	}
	/**
	 * @param value the value to set
	 */
	public void setValue(Value value) {
		this.value = value;
	}
	/**
	 * @return the quantity
	 */
	public String getQuantity() {
		return quantity;
	}
	/**
	 * @param quantity the quantity to set
	 */
	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}
	/**
	 * @return the unit
	 */
	public String getUnit() {
		return unit;
	}
	/**
	 * @param unit the unit to set
	 */
	public void setUnit(String unit) {
		this.unit = unit;
	}
	/**
	 * @return the price
	 */
	public String getPrice() {
		return price;
	}
	/**
	 * @param price the price to set
	 */
	public void setPrice(String price) {
		this.price = price;
	}
	/**
	 * @return the total
	 */
	public String getTotal() {
		return total;
	}
	/**
	 * @param total the total to set
	 */
	public void setTotal(String total) {
		this.total = total;
	}
	/**
	 * @return the notes
	 */
	public String getNotes() {
		return notes;
	}
	/**
	 * @param notes the notes to set
	 */
	public void setNotes(String notes) {
		this.notes = notes;
	}

	/**
	 * @return the dept
	 */
	public Dept getDept() {
		return dept;
	}

	/**
	 * @param dept the dept to set
	 */
	public void setDept(Dept dept) {
		this.dept = dept;
	}

	/**
	 * @return the bed
	 */
	public String getBed() {
		return bed;
	}

	/**
	 * @param bed the bed to set
	 */
	public void setBed(String bed) {
		this.bed = bed;
	}

	/**
	 * @return the orderTime
	 */
	public String getOrderTime() {
		return orderTime;
	}

	/**
	 * @param orderTime the orderTime to set
	 */
	public void setOrderTime(String orderTime) {
		this.orderTime = orderTime;
	}

	/**
	 * @return the orderDoctor
	 */
	public String getOrderDoctor() {
		return orderDoctor;
	}

	/**
	 * @param orderDoctor the orderDoctor to set
	 */
	public void setOrderDoctor(String orderDoctor) {
		this.orderDoctor = orderDoctor;
	}

 
	/**
	 * @return the checkNurse
	 */
	public String getCheckNurse() {
		return checkNurse;
	}

	/**
	 * @param checkNurse the checkNurse to set
	 */
	public void setCheckNurse(String checkNurse) {
		this.checkNurse = checkNurse;
	}

	/**
	 * @return the stopTime
	 */
	public String getStopTime() {
		return stopTime;
	}

	/**
	 * @param stopTime the stopTime to set
	 */
	public void setStopTime(String stopTime) {
		this.stopTime = stopTime;
	}

	/**
	 * @return the stopDoctor
	 */
	public String getStopDoctor() {
		return stopDoctor;
	}

	/**
	 * @param stopDoctor the stopDoctor to set
	 */
	public void setStopDoctor(String stopDoctor) {
		this.stopDoctor = stopDoctor;
	}

	/**
	 * @return the stopNurse
	 */
	public String getStopNurse() {
		return stopNurse;
	}

	/**
	 * @param stopNurse the stopNurse to set
	 */
	public void setStopNurse(String stopNurse) {
		this.stopNurse = stopNurse;
	}

	/**
	 * @return the orderCode
	 */
	public String getOrderCode() {
		return orderCode;
	}

	/**
	 * @param orderCode the orderCode to set
	 */
	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}

	/**
	 * @return the stdCode
	 */
	public String getStdCode() {
		return stdCode;
	}

	/**
	 * @param stdCode the stdCode to set
	 */
	public void setStdCode(String stdCode) {
		this.stdCode = stdCode;
	}

	/**
	 * @return the orderName
	 */
	public String getOrderName() {
		return orderName;
	}

	/**
	 * @param orderName the orderName to set
	 */
	public void setOrderName(String orderName) {
		this.orderName = orderName;
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
	 * @return the execNurse
	 */
	public String getExecNurse() {
		return execNurse;
	}

	/**
	 * @param execNurse the execNurse to set
	 */
	public void setExecNurse(String execNurse) {
		this.execNurse = execNurse;
	}

	/**
	 * @return the execTime
	 */
	public String getExecTime() {
		return execTime;
	}

	/**
	 * @param execTime the execTime to set
	 */
	public void setExecTime(String execTime) {
		this.execTime = execTime;
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
	 * @return the oper
	 */
	public Value getOper() {
		return oper;
	}

	/**
	 * @param oper the oper to set
	 */
	public void setOper(Value oper) {
		this.oper = oper;
	}

	/**
	 * @return the part
	 */
	public Value getPart() {
		return part;
	}

	/**
	 * @param part the part to set
	 */
	public void setPart(Value part) {
		this.part = part;
	}

	/**
	 * @return the surgeon
	 */
	public String getSurgeon() {
		return surgeon;
	}

	/**
	 * @param surgeon the surgeon to set
	 */
	public void setSurgeon(String surgeon) {
		this.surgeon = surgeon;
	}

	/**
	 * @return the assistant1
	 */
	public String getAssistant1() {
		return assistant1;
	}

	/**
	 * @param assistant1 the assistant1 to set
	 */
	public void setAssistant1(String assistant1) {
		this.assistant1 = assistant1;
	}

	/**
	 * @return the assistant2
	 */
	public String getAssistant2() {
		return assistant2;
	}

	/**
	 * @param assistant2 the assistant2 to set
	 */
	public void setAssistant2(String assistant2) {
		this.assistant2 = assistant2;
	}

	/**
	 * @return the anesthesia
	 */
	public Value getAnesthesia() {
		return anesthesia;
	}

	/**
	 * @param anesthesia the anesthesia to set
	 */
	public void setAnesthesia(Value anesthesia) {
		this.anesthesia = anesthesia;
	}

	/**
	 * @return the incisionAndHeal
	 */
	public Value getIncisionAndHeal() {
		return incisionAndHeal;
	}

	/**
	 * @param incisionAndHeal the incisionAndHeal to set
	 */
	public void setIncisionAndHeal(Value incisionAndHeal) {
		this.incisionAndHeal = incisionAndHeal;
	}
	
}
