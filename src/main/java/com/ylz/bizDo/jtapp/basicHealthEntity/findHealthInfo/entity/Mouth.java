/**
 * 
 */
package com.ylz.bizDo.jtapp.basicHealthEntity.findHealthInfo.entity;

import java.io.Serializable;

/**
 *<p>Title:Mouth</p> 
 *<p>Description:</p>
 *<p>Company:ylz</p>
 *@author cairongjie
 *@date 2016-7-8 下午2:23:23
 */
public class Mouth implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Value lip;
	private Value dentition;
	private String pharygeal;
	
	public Mouth(){}
	public Mouth(Value lip,Value dentition,String pharygeal){
		this.lip=lip;
		this.dentition=dentition;
		this.pharygeal=pharygeal;
	}
	/**
	 * @return the lip
	 */
	public Value getLip() {
		return lip;
	}
	/**
	 * @param lip the lip to set
	 */
	public void setLip(Value lip) {
		this.lip = lip;
	}
	/**
	 * @return the dentition
	 */
	public Value getDentition() {
		return dentition;
	}
	/**
	 * @param dentition the dentition to set
	 */
	public void setDentition(Value dentition) {
		this.dentition = dentition;
	}
	/**
	 * @return the pharygeal
	 */
	public String getPharygeal() {
		return pharygeal;
	}
	/**
	 * @param pharygeal the pharygeal to set
	 */
	public void setPharygeal(String pharygeal) {
		this.pharygeal = pharygeal;
	}
	
	

}
