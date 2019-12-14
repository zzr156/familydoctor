/**
 * 
 */
package com.ylz.bizDo.jtapp.basicHealthEntity.findHealthInfo.entity;

import java.io.Serializable;

/**
 *<p>Title:Symptom2</p> 
 *<p>Description:</p>
 *<p>Company:ylz</p>
 *@author cairongjie
 *@date 2016-7-8 下午4:30:34
 */
public class Symptom2 implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5730533362379763836L;
	private Value cough;
	private Value expectoration;
	private Value hardlyBreath;
	
	public Symptom2(){}
	public Symptom2(Value cough,Value expectoration,Value hardlyBreath){
		this.cough=cough;
		this.expectoration=expectoration;
		this.hardlyBreath=hardlyBreath;
		
	}
	/**
	 * @return the cough
	 */
	public Value getCough() {
		return cough;
	}
	/**
	 * @param cough the cough to set
	 */
	public void setCough(Value cough) {
		this.cough = cough;
	}
	/**
	 * @return the expectoration
	 */
	public Value getExpectoration() {
		return expectoration;
	}
	/**
	 * @param expectoration the expectoration to set
	 */
	public void setExpectoration(Value expectoration) {
		this.expectoration = expectoration;
	}
	/**
	 * @return the hardlyBreath
	 */
	public Value getHardlyBreath() {
		return hardlyBreath;
	}
	/**
	 * @param hardlyBreath the hardlyBreath to set
	 */
	public void setHardlyBreath(Value hardlyBreath) {
		this.hardlyBreath = hardlyBreath;
	}
	
}
