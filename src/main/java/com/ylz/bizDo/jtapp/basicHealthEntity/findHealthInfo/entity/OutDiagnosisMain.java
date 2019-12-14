/**
 * 
 */
package com.ylz.bizDo.jtapp.basicHealthEntity.findHealthInfo.entity;

import java.io.Serializable;

/**
 *<p>Title:OutDiagnosisMain</p> 
 *<p>Description:</p>
 *<p>Company:ylz</p>
 *@author cairongjie
 *@date 2016-6-29 下午4:10:49
 */
public class OutDiagnosisMain implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Icd icd;
	private Result result;
	
	public OutDiagnosisMain(){}
	public OutDiagnosisMain(Icd icd,Result result){
		this.icd=icd;
		this.result=result;
	}
	/**
	 * @return the icd
	 */
	public Icd getIcd() {
		return icd;
	}
	/**
	 * @param icd the icd to set
	 */
	public void setIcd(Icd icd) {
		this.icd = icd;
	}
	/**
	 * @return the result
	 */
	public Result getResult() {
		return result;
	}
	/**
	 * @param result the result to set
	 */
	public void setResult(Result result) {
		this.result = result;
	}
	
	
}
