/**
 * 
 */
package com.ylz.bizDo.jtapp.basicHealthEntity.findHealthInfo.entity;

import java.io.Serializable;

/**
 *<p>Title:UrineRoutine</p> 
 *<p>Description:</p>
 *<p>Company:ylz</p>
 *@author cairongjie
 *@date 2016-7-8 下午3:13:52
 */
public class UrineRoutine implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String SG;
	private PRO PRO;
	private PRO GLU;
	private PRO KET;
	private String PH;
	private PRO OB;
	public UrineRoutine(){}
	public UrineRoutine(String SG,PRO PRO,PRO GLU,PRO KET,String PH,PRO OB){
		this.SG=SG;
		this.PRO=PRO;
		this.GLU=GLU;
		this.KET=KET;
		this.PH=PH;
		this.OB=OB;
	}
	/**
	 * @return the sG
	 */
	public String getSG() {
		return SG;
	}
	/**
	 * @param sG the sG to set
	 */
	public void setSG(String sG) {
		SG = sG;
	}
	/**
	 * @return the pRO
	 */
	public PRO getPRO() {
		return PRO;
	}
	/**
	 * @param pRO the pRO to set
	 */
	public void setPRO(PRO pRO) {
		PRO = pRO;
	}
	/**
	 * @return the gLU
	 */
	public PRO getGLU() {
		return GLU;
	}
	/**
	 * @param gLU the gLU to set
	 */
	public void setGLU(PRO gLU) {
		GLU = gLU;
	}
	/**
	 * @return the kET
	 */
	public PRO getKET() {
		return KET;
	}
	/**
	 * @param kET the kET to set
	 */
	public void setKET(PRO kET) {
		KET = kET;
	}
	/**
	 * @return the pH
	 */
	public String getPH() {
		return PH;
	}
	/**
	 * @param pH the pH to set
	 */
	public void setPH(String pH) {
		PH = pH;
	}
	/**
	 * @return the oB
	 */
	public PRO getOB() {
		return OB;
	}
	/**
	 * @param oB the oB to set
	 */
	public void setOB(PRO oB) {
		OB = oB;
	}


}
