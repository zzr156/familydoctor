/**
 * 
 */
package com.ylz.bizDo.jtapp.basicHealthEntity.findHealthInfo.entity;

import java.io.Serializable;


/**
 *<p>Title:DiagnosisComparing</p> 
 *<p>Description:</p>
 *<p>Company:ylz</p>
 *@author cairongjie
 *@date 2016-7-7 下午4:00:53
 */

public class DiagnosisComparing implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Value clinic2out;
	private Value in2out;
	private Value preoper2oper;
	private Value radiation2oper;
	private Value clinic2pathology;
	private Value radiation2pathology;
	
	public DiagnosisComparing(){}
	public DiagnosisComparing(Value clinic2out,Value in2out,Value preoper2oper,
			Value radiation2oper,Value clinic2pathology,Value radiation2pathology){
		this.clinic2out=clinic2out;
		this.in2out=in2out;
		this.preoper2oper=preoper2oper;
		this.radiation2oper=radiation2oper;
		this.clinic2pathology=clinic2pathology;
		this.radiation2pathology=radiation2pathology;
	}
	/**
	 * @return the clinic2out
	 */
	public Value getClinic2out() {
		return clinic2out;
	}
	/**
	 * @param clinic2out the clinic2out to set
	 */
	public void setClinic2out(Value clinic2out) {
		this.clinic2out = clinic2out;
	}
	/**
	 * @return the in2out
	 */
	public Value getIn2out() {
		return in2out;
	}
	/**
	 * @param in2out the in2out to set
	 */
	public void setIn2out(Value in2out) {
		this.in2out = in2out;
	}
	/**
	 * @return the preoper2oper
	 */
	public Value getPreoper2oper() {
		return preoper2oper;
	}
	/**
	 * @param preoper2oper the preoper2oper to set
	 */
	public void setPreoper2oper(Value preoper2oper) {
		this.preoper2oper = preoper2oper;
	}
	/**
	 * @return the radiation2oper
	 */
	public Value getRadiation2oper() {
		return radiation2oper;
	}
	/**
	 * @param radiation2oper the radiation2oper to set
	 */
	public void setRadiation2oper(Value radiation2oper) {
		this.radiation2oper = radiation2oper;
	}
	/**
	 * @return the clinic2pathology
	 */
	public Value getClinic2pathology() {
		return clinic2pathology;
	}
	/**
	 * @param clinic2pathology the clinic2pathology to set
	 */
	public void setClinic2pathology(Value clinic2pathology) {
		this.clinic2pathology = clinic2pathology;
	}
	/**
	 * @return the radiation2pathology
	 */
	public Value getRadiation2pathology() {
		return radiation2pathology;
	}
	/**
	 * @param radiation2pathology the radiation2pathology to set
	 */
	public void setRadiation2pathology(Value radiation2pathology) {
		this.radiation2pathology = radiation2pathology;
	}
	
	
	
	

}
