/**
 * 
 */
package com.ylz.bizDo.jtapp.basicHealthEntity.findHealthInfo.entity;

import java.io.Serializable;

/**
 *<p>Title:LiverFunction</p> 
 *<p>Description:</p>
 *<p>Company:ylz</p>
 *@author cairongjie
 *@date 2016-7-8 下午3:14:55
 */
public class LiverFunction implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String ALB;
	private String ALT;
	private String AST;
	private String DBIL;
	private String TBIL;
	
	public LiverFunction(){}
	public LiverFunction(String ALB,String ALT,String AST,
			String DBIL,String TBIL){
		this.ALB=ALB;
		this.ALT=ALT;
		this.AST=AST;
		this.DBIL=DBIL;
		this.TBIL=TBIL;
	}
	/**
	 * @return the aLB
	 */
	public String getALB() {
		return ALB;
	}
	/**
	 * @param aLB the aLB to set
	 */
	public void setALB(String aLB) {
		ALB = aLB;
	}
	/**
	 * @return the aLT
	 */
	public String getALT() {
		return ALT;
	}
	/**
	 * @param aLT the aLT to set
	 */
	public void setALT(String aLT) {
		ALT = aLT;
	}
	/**
	 * @return the aST
	 */
	public String getAST() {
		return AST;
	}
	/**
	 * @param aST the aST to set
	 */
	public void setAST(String aST) {
		AST = aST;
	}
	/**
	 * @return the dBIL
	 */
	public String getDBIL() {
		return DBIL;
	}
	/**
	 * @param dBIL the dBIL to set
	 */
	public void setDBIL(String dBIL) {
		DBIL = dBIL;
	}
	/**
	 * @return the tBIL
	 */
	public String getTBIL() {
		return TBIL;
	}
	/**
	 * @param tBIL the tBIL to set
	 */
	public void setTBIL(String tBIL) {
		TBIL = tBIL;
	}
	

}
