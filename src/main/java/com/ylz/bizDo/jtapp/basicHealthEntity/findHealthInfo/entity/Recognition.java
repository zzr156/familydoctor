/**
 * 
 */
package com.ylz.bizDo.jtapp.basicHealthEntity.findHealthInfo.entity;

import java.io.Serializable;

/**
 *<p>Title:Recognition</p> 
 *<p>Description:</p>
 *<p>Company:ylz</p>
 *@author cairongjie
 *@date 2016-7-8 下午1:53:37
 */
public class Recognition implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Value screenResult;
	private String score;
	
	public Recognition(){}
	public Recognition(Value screenResult,String score){
		this.screenResult=screenResult;
		this.score=score;
		
	}
	/**
	 * @return the screenResult
	 */
	public Value getScreenResult() {
		return screenResult;
	}
	/**
	 * @param screenResult the screenResult to set
	 */
	public void setScreenResult(Value screenResult) {
		this.screenResult = screenResult;
	}
	/**
	 * @return the score
	 */
	public String getScore() {
		return score;
	}
	/**
	 * @param score the score to set
	 */
	public void setScore(String score) {
		this.score = score;
	}
	 

}
