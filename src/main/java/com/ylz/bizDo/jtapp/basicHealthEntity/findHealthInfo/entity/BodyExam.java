/**
 * 
 */
package com.ylz.bizDo.jtapp.basicHealthEntity.findHealthInfo.entity;

import java.io.Serializable;


/**
 *<p>Title:BodyExam</p> 
 *<p>Description:</p>
 *<p>Company:ylz</p>
 *@author cairongjie
 *@date 2016-7-8 下午2:12:47
 */
public class BodyExam implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Value skinSclera;
	private Lymph lymph;
	private Mouth mouth;
	private Value chest;
	private Lung lung;
	private Heart heart;
	private Abdominal abdominal;
	private Leg leg;
	private RectalTouch rectalTouch;
	private Rale prostate;
	private String other;
	
	public BodyExam(){}
	public BodyExam(Value skinSclera,Lymph lymph,Mouth mouth,Lung lung,
			Heart heart,Abdominal abdominal,Leg leg,RectalTouch rectalTouch,
			Rale prostate,String other){
		this.skinSclera=skinSclera;
		this.lymph=lymph;
		this.mouth=mouth;
		this.lung=lung;
		this.heart=heart;
		this.abdominal=abdominal;
		this.leg=leg;
		this.rectalTouch=rectalTouch;
		this.prostate=prostate;
		this.other=other;
	}
	/**
	 * @return the skinSclera
	 */
	public Value getSkinSclera() {
		return skinSclera;
	}
	/**
	 * @param skinSclera the skinSclera to set
	 */
	public void setSkinSclera(Value skinSclera) {
		this.skinSclera = skinSclera;
	}
	/**
	 * @return the lymph
	 */
	public Lymph getLymph() {
		return lymph;
	}
	/**
	 * @param lymph the lymph to set
	 */
	public void setLymph(Lymph lymph) {
		this.lymph = lymph;
	}
	/**
	 * @return the mouth
	 */
	public Mouth getMouth() {
		return mouth;
	}
	/**
	 * @param mouth the mouth to set
	 */
	public void setMouth(Mouth mouth) {
		this.mouth = mouth;
	}
	/**
	 * @return the chest
	 */
	public Value getChest() {
		return chest;
	}
	/**
	 * @param chest the chest to set
	 */
	public void setChest(Value chest) {
		this.chest = chest;
	}
	/**
	 * @return the lung
	 */
	public Lung getLung() {
		return lung;
	}
	/**
	 * @param lung the lung to set
	 */
	public void setLung(Lung lung) {
		this.lung = lung;
	}
	/**
	 * @return the heart
	 */
	public Heart getHeart() {
		return heart;
	}
	/**
	 * @param heart the heart to set
	 */
	public void setHeart(Heart heart) {
		this.heart = heart;
	}
	/**
	 * @return the abdominal
	 */
	public Abdominal getAbdominal() {
		return abdominal;
	}
	/**
	 * @param abdominal the abdominal to set
	 */
	public void setAbdominal(Abdominal abdominal) {
		this.abdominal = abdominal;
	}
	/**
	 * @return the leg
	 */
	public Leg getLeg() {
		return leg;
	}
	/**
	 * @param leg the leg to set
	 */
	public void setLeg(Leg leg) {
		this.leg = leg;
	}
	/**
	 * @return the rectalTouch
	 */
	public RectalTouch getRectalTouch() {
		return rectalTouch;
	}
	/**
	 * @param rectalTouch the rectalTouch to set
	 */
	public void setRectalTouch(RectalTouch rectalTouch) {
		this.rectalTouch = rectalTouch;
	}
	/**
	 * @return the prostate
	 */
	public Rale getProstate() {
		return prostate;
	}
	/**
	 * @param prostate the prostate to set
	 */
	public void setProstate(Rale prostate) {
		this.prostate = prostate;
	}
	/**
	 * @return the other
	 */
	public String getOther() {
		return other;
	}
	/**
	 * @param other the other to set
	 */
	public void setOther(String other) {
		this.other = other;
	}
	
	
	
	

}
