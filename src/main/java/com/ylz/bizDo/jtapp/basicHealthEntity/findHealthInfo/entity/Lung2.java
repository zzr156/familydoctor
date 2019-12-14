/**
 * 
 */
package com.ylz.bizDo.jtapp.basicHealthEntity.findHealthInfo.entity;

import java.io.Serializable;

/**
 *<p>Title:Lung2</p> 
 *<p>Description:</p>
 *<p>Company:ylz</p>
 *@author cairongjie
 *@date 2016-7-8 下午4:46:59
 */
public class Lung2 implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String FEV1;
	private String FEV1FVC;
	
	public Lung2(){}
	public Lung2(String FEV1,String FEV1FVC){
		this.FEV1=FEV1;
		this.FEV1FVC=FEV1FVC;
	}
	/**
	 * @return the fEV1
	 */
	public String getFEV1() {
		return FEV1;
	}
	/**
	 * @param fEV1 the fEV1 to set
	 */
	public void setFEV1(String fEV1) {
		FEV1 = fEV1;
	}
	/**
	 * @return the fEV1FVC
	 */
	public String getFEV1FVC() {
		return FEV1FVC;
	}
	/**
	 * @param fEV1FVC the fEV1FVC to set
	 */
	public void setFEV1FVC(String fEV1FVC) {
		FEV1FVC = fEV1FVC;
	}

}
