/**
 * 
 */
package com.ylz.bizDo.jtapp.basicHealthEntity.findHealthInfo.entity;

import java.io.Serializable;

/**
 *<p>Title:Abdominal</p> 
 *<p>Description:</p>
 *<p>Company:ylz</p>
 *@author cairongjie
 *@date 2016-7-8 下午2:40:56
 */
public class Abdominal implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Rale hepatomegaly;
	private Rale splenomegaly;
	private Rale mass;
	private Rale tenderness;
	private Rale shiftingDullness;
	private Value renalPain;
	
	public Abdominal(){}
	public Abdominal(Rale hepatomegaly,Rale splenomegaly,
			Rale mass,Rale tenderness,Rale shiftingDullness,
			Value renalPain){
		this.hepatomegaly=hepatomegaly;
		this.splenomegaly=splenomegaly;
		this.mass=mass;
		this.tenderness=tenderness;
		this.shiftingDullness=shiftingDullness;
		this.renalPain=renalPain;
	}
	/**
	 * @return the hepatomegaly
	 */
	public Rale getHepatomegaly() {
		return hepatomegaly;
	}
	/**
	 * @param hepatomegaly the hepatomegaly to set
	 */
	public void setHepatomegaly(Rale hepatomegaly) {
		this.hepatomegaly = hepatomegaly;
	}
	/**
	 * @return the splenomegaly
	 */
	public Rale getSplenomegaly() {
		return splenomegaly;
	}
	/**
	 * @param splenomegaly the splenomegaly to set
	 */
	public void setSplenomegaly(Rale splenomegaly) {
		this.splenomegaly = splenomegaly;
	}
	/**
	 * @return the mass
	 */
	public Rale getMass() {
		return mass;
	}
	/**
	 * @param mass the mass to set
	 */
	public void setMass(Rale mass) {
		this.mass = mass;
	}
	/**
	 * @return the tenderness
	 */
	public Rale getTenderness() {
		return tenderness;
	}
	/**
	 * @param tenderness the tenderness to set
	 */
	public void setTenderness(Rale tenderness) {
		this.tenderness = tenderness;
	}
	/**
	 * @return the shiftingDullness
	 */
	public Rale getShiftingDullness() {
		return shiftingDullness;
	}
	/**
	 * @param shiftingDullness the shiftingDullness to set
	 */
	public void setShiftingDullness(Rale shiftingDullness) {
		this.shiftingDullness = shiftingDullness;
	}
	/**
	 * @return the renalPain
	 */
	public Value getRenalPain() {
		return renalPain;
	}
	/**
	 * @param renalPain the renalPain to set
	 */
	public void setRenalPain(Value renalPain) {
		this.renalPain = renalPain;
	}
	
	
	

}
