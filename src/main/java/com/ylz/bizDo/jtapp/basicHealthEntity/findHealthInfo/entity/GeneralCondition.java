/**
 * 
 */
package com.ylz.bizDo.jtapp.basicHealthEntity.findHealthInfo.entity;

import java.io.Serializable;

/**
 *<p>Title:GeneralCondition</p> 
 *<p>Description:</p>
 *<p>Company:ylz</p>
 *@author cairongjie
 *@date 2016-7-8 下午1:47:35
 */
public class GeneralCondition implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String temperature;
	private String pulseRate;
	private String breathFreq;
	private String high;
	private String weight;
	private String hip;
	private String waistline;
	private String BMI;
	private String WHR;
	private String SBP;
	private String DBP;
	private Recognition recognition;
	private EmotionState emotionState;
	/**
	 * @return the temperature
	 */
	public String getTemperature() {
		return temperature;
	}
	/**
	 * @param temperature the temperature to set
	 */
	public void setTemperature(String temperature) {
		this.temperature = temperature;
	}
	/**
	 * @return the pulseRate
	 */
	public String getPulseRate() {
		return pulseRate;
	}
	/**
	 * @param pulseRate the pulseRate to set
	 */
	public void setPulseRate(String pulseRate) {
		this.pulseRate = pulseRate;
	}
	/**
	 * @return the breathFreq
	 */
	public String getBreathFreq() {
		return breathFreq;
	}
	/**
	 * @param breathFreq the breathFreq to set
	 */
	public void setBreathFreq(String breathFreq) {
		this.breathFreq = breathFreq;
	}
	/**
	 * @return the high
	 */
	public String getHigh() {
		return high;
	}
	/**
	 * @param high the high to set
	 */
	public void setHigh(String high) {
		this.high = high;
	}
	/**
	 * @return the weight
	 */
	public String getWeight() {
		return weight;
	}
	/**
	 * @param weight the weight to set
	 */
	public void setWeight(String weight) {
		this.weight = weight;
	}
	/**
	 * @return the hip
	 */
	public String getHip() {
		return hip;
	}
	/**
	 * @param hip the hip to set
	 */
	public void setHip(String hip) {
		this.hip = hip;
	}
	/**
	 * @return the waistline
	 */
	public String getWaistline() {
		return waistline;
	}
	/**
	 * @param waistline the waistline to set
	 */
	public void setWaistline(String waistline) {
		this.waistline = waistline;
	}
	/**
	 * @return the bMI
	 */
	public String getBMI() {
		return BMI;
	}
	/**
	 * @param bMI the bMI to set
	 */
	public void setBMI(String bMI) {
		BMI = bMI;
	}
	/**
	 * @return the wHR
	 */
	public String getWHR() {
		return WHR;
	}
	/**
	 * @param wHR the wHR to set
	 */
	public void setWHR(String wHR) {
		WHR = wHR;
	}
	/**
	 * @return the sBP
	 */
	public String getSBP() {
		return SBP;
	}
	/**
	 * @param sBP the sBP to set
	 */
	public void setSBP(String sBP) {
		SBP = sBP;
	}
	/**
	 * @return the dBP
	 */
	public String getDBP() {
		return DBP;
	}
	/**
	 * @param dBP the dBP to set
	 */
	public void setDBP(String dBP) {
		DBP = dBP;
	}
	/**
	 * @return the recognition
	 */
	public Recognition getRecognition() {
		return recognition;
	}
	/**
	 * @param recognition the recognition to set
	 */
	public void setRecognition(Recognition recognition) {
		this.recognition = recognition;
	}
	/**
	 * @return the emotionState
	 */
	public EmotionState getEmotionState() {
		return emotionState;
	}
	/**
	 * @param emotionState the emotionState to set
	 */
	public void setEmotionState(EmotionState emotionState) {
		this.emotionState = emotionState;
	}
	
	
	

}
