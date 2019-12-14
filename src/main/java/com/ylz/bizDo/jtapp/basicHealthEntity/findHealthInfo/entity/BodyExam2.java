/**
 * 
 */
package com.ylz.bizDo.jtapp.basicHealthEntity.findHealthInfo.entity;

import java.io.Serializable;

/**
 *<p>Title:BodyExam2</p> 
 *<p>Description:</p>
 *<p>Company:ylz</p>
 *@author cairongjie
 *@date 2016-7-8 下午4:34:36
 */
public class BodyExam2 implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4107769867524498203L;
	private Value lipsCyanotic;
	private Value neckVein;
	private Value expectoration;
	
	public BodyExam2(){}
	public BodyExam2(Value lipsCyanotic,Value neckVein,
			Value expectoration){
		this.lipsCyanotic=lipsCyanotic;
		this.neckVein=neckVein;
		this.expectoration=expectoration;
		
	}
	/**
	 * @return the lipsCyanotic
	 */
	public Value getLipsCyanotic() {
		return lipsCyanotic;
	}
	/**
	 * @param lipsCyanotic the lipsCyanotic to set
	 */
	public void setLipsCyanotic(Value lipsCyanotic) {
		this.lipsCyanotic = lipsCyanotic;
	}
	/**
	 * @return the neckVein
	 */
	public Value getNeckVein() {
		return neckVein;
	}
	/**
	 * @param neckVein the neckVein to set
	 */
	public void setNeckVein(Value neckVein) {
		this.neckVein = neckVein;
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
	 

}
