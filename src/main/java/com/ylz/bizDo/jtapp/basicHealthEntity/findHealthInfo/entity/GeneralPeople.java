/**
 * 
 */
package com.ylz.bizDo.jtapp.basicHealthEntity.findHealthInfo.entity;

import java.io.Serializable;

/**
 *<p>Title:GeneralPeople</p> 
 *<p>Description:</p>
 *<p>Company:ylz</p>
 *@author cairongjie
 *@date 2016-7-8 下午3:12:00
 */
public class GeneralPeople implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private BloodRoutine bloodRoutine;
	private UrineRoutine urineRoutine;
	private Value OB;
	private LiverFunction liverFunction;
	private Renal renal;
	private BloodFat bloodFat;
	private BloodSugar bloodSugar;
	private HBV HBV;
	private Fundus fundus;
	private String PACS;
	private String chest;
	private String BScan;
	private CancerScreen cancerScreen;
	
	public GeneralPeople(){}
	public GeneralPeople(BloodRoutine bloodRoutine,UrineRoutine urineRoutine,
			Value OB,LiverFunction liverFunction,Renal renal,
			BloodFat bloodFat,BloodSugar bloodSugar,HBV HBV,
			CancerScreen cancerScreen,Fundus fundus,
			String PACS,String chest,String BScan){
		this.bloodRoutine=bloodRoutine;
		this.urineRoutine=urineRoutine;
		this.OB=OB;
		this.liverFunction=liverFunction;
		this.renal=renal;
		this.bloodFat=bloodFat;
		this.bloodSugar=bloodSugar;
		this.HBV=HBV;
		this.cancerScreen=cancerScreen;
		this.fundus=fundus;
		this.PACS=PACS;
		this.chest=chest;
		this.BScan=BScan;
	}
	/**
	 * @return the bloodRoutine
	 */
	public BloodRoutine getBloodRoutine() {
		return bloodRoutine;
	}
	/**
	 * @param bloodRoutine the bloodRoutine to set
	 */
	public void setBloodRoutine(BloodRoutine bloodRoutine) {
		this.bloodRoutine = bloodRoutine;
	}
	/**
	 * @return the urineRoutine
	 */
	public UrineRoutine getUrineRoutine() {
		return urineRoutine;
	}
	/**
	 * @param urineRoutine the urineRoutine to set
	 */
	public void setUrineRoutine(UrineRoutine urineRoutine) {
		this.urineRoutine = urineRoutine;
	}
	/**
	 * @return the oB
	 */
	public Value getOB() {
		return OB;
	}
	/**
	 * @param oB the oB to set
	 */
	public void setOB(Value oB) {
		OB = oB;
	}
	/**
	 * @return the liverFunction
	 */
	public LiverFunction getLiverFunction() {
		return liverFunction;
	}
	/**
	 * @param liverFunction the liverFunction to set
	 */
	public void setLiverFunction(LiverFunction liverFunction) {
		this.liverFunction = liverFunction;
	}
	/**
	 * @return the renal
	 */
	public Renal getRenal() {
		return renal;
	}
	/**
	 * @param renal the renal to set
	 */
	public void setRenal(Renal renal) {
		this.renal = renal;
	}
	/**
	 * @return the bloodFat
	 */
	public BloodFat getBloodFat() {
		return bloodFat;
	}
	/**
	 * @param bloodFat the bloodFat to set
	 */
	public void setBloodFat(BloodFat bloodFat) {
		this.bloodFat = bloodFat;
	}
	/**
	 * @return the bloodSugar
	 */
	public BloodSugar getBloodSugar() {
		return bloodSugar;
	}
	/**
	 * @param bloodSugar the bloodSugar to set
	 */
	public void setBloodSugar(BloodSugar bloodSugar) {
		this.bloodSugar = bloodSugar;
	}
	/**
	 * @return the hBV
	 */
	public HBV getHBV() {
		return HBV;
	}
	/**
	 * @param hBV the hBV to set
	 */
	public void setHBV(HBV hBV) {
		HBV = hBV;
	}
	/**
	 * @return the cancerScreen
	 */
	public CancerScreen getCancerScreen() {
		return cancerScreen;
	}
	/**
	 * @param cancerScreen the cancerScreen to set
	 */
	public void setCancerScreen(CancerScreen cancerScreen) {
		this.cancerScreen = cancerScreen;
	}
	/**
	 * @return the fundus
	 */
	public Fundus getFundus() {
		return fundus;
	}
	/**
	 * @param fundus the fundus to set
	 */
	public void setFundus(Fundus fundus) {
		this.fundus = fundus;
	}
	/**
	 * @return the pACS
	 */
	public String getPACS() {
		return PACS;
	}
	/**
	 * @param pACS the pACS to set
	 */
	public void setPACS(String pACS) {
		PACS = pACS;
	}
	/**
	 * @return the chest
	 */
	public String getChest() {
		return chest;
	}
	/**
	 * @param chest the chest to set
	 */
	public void setChest(String chest) {
		this.chest = chest;
	}
	/**
	 * @return the bScan
	 */
	public String getBScan() {
		return BScan;
	}
	/**
	 * @param bScan the bScan to set
	 */
	public void setBScan(String bScan) {
		BScan = bScan;
	}
	
	
	
	
	

}
