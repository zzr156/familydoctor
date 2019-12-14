/**
 * 
 */
package com.ylz.bizDo.jtapp.basicHealthEntity.findHealthInfo.entity;

import java.io.Serializable;

/**
 *<p>Title:Heart</p> 
 *<p>Description:</p>
 *<p>Company:ylz</p>
 *@author cairongjie
 *@date 2016-7-8 下午2:36:39
 */
public class Heart implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String rate;
	private Value rhythm;
	private Rale murmur;
	
	
	public Heart(){}
	public Heart(String rate,Value rhythm,Rale murmur){
		this.rate=rate;
		this.rhythm=rhythm;
		this.murmur=murmur;
	}
	/**
	 * @return the rate
	 */
	public String getRate() {
		return rate;
	}
	/**
	 * @param rate the rate to set
	 */
	public void setRate(String rate) {
		this.rate = rate;
	}
	/**
	 * @return the rhythm
	 */
	public Value getRhythm() {
		return rhythm;
	}
	/**
	 * @param rhythm the rhythm to set
	 */
	public void setRhythm(Value rhythm) {
		this.rhythm = rhythm;
	}
	/**
	 * @return the murmur
	 */
	public Rale getMurmur() {
		return murmur;
	}
	/**
	 * @param murmur the murmur to set
	 */
	public void setMurmur(Rale murmur) {
		this.murmur = murmur;
	}
	
	

}
