/**
 * 
 */
package com.ylz.bizDo.jtapp.basicHealthEntity.findHealthInfo.entity;

import java.io.Serializable;

/**
 *<p>Title:Lung</p> 
 *<p>Description:</p>
 *<p>Company:ylz</p>
 *@author cairongjie
 *@date 2016-7-8 下午2:27:53
 */
public class Lung implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Rale rale;
	private Rale breathSound;
	
	public Lung(){}
	public Lung(Rale rale,Rale breathSound){
		this.rale=rale;
		this.breathSound=breathSound;
	}
	/**
	 * @return the rale
	 */
	public Rale getRale() {
		return rale;
	}
	/**
	 * @param rale the rale to set
	 */
	public void setRale(Rale rale) {
		this.rale = rale;
	}
	/**
	 * @return the breathSound
	 */
	public Rale getBreathSound() {
		return breathSound;
	}
	/**
	 * @param breathSound the breathSound to set
	 */
	public void setBreathSound(Rale breathSound) {
		this.breathSound = breathSound;
	}
	
	

}
