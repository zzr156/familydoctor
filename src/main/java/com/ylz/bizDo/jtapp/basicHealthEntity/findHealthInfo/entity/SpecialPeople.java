/**
 * 
 */
package com.ylz.bizDo.jtapp.basicHealthEntity.findHealthInfo.entity;

import java.io.Serializable;

/**
 *<p>Title:SpecialPeople</p> 
 *<p>Description:</p>
 *<p>Company:ylz</p>
 *@author cairongjie
 *@date 2016-7-8 下午4:13:59
 */
public class SpecialPeople implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Diabetes diabetes;
	private Hypertension hypertension;
	private COPD COPD;
	
	public SpecialPeople(){}
	public SpecialPeople(Diabetes diabetes,Hypertension hypertension,
			COPD COPD){
		this.diabetes=diabetes;
		this.hypertension=hypertension;
		this.COPD=COPD;
	}
	/**
	 * @return the diabetes
	 */
	public Diabetes getDiabetes() {
		return diabetes;
	}
	/**
	 * @param diabetes the diabetes to set
	 */
	public void setDiabetes(Diabetes diabetes) {
		this.diabetes = diabetes;
	}
	/**
	 * @return the hypertension
	 */
	public Hypertension getHypertension() {
		return hypertension;
	}
	/**
	 * @param hypertension the hypertension to set
	 */
	public void setHypertension(Hypertension hypertension) {
		this.hypertension = hypertension;
	}
	/**
	 * @return the cOPD
	 */
	public COPD getCOPD() {
		return COPD;
	}
	/**
	 * @param cOPD the cOPD to set
	 */
	public void setCOPD(COPD cOPD) {
		COPD = cOPD;
	}
	
	
	

}
