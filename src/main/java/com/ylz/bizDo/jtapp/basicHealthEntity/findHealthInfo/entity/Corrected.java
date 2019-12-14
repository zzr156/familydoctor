/**
 * 
 */
package com.ylz.bizDo.jtapp.basicHealthEntity.findHealthInfo.entity;

import java.io.Serializable;

/**
 *<p>Title:Corrected</p> 
 *<p>Description:</p>
 *<p>Company:ylz</p>
 *@author cairongjie
 *@date 2016-7-8 下午2:06:46
 */
public class Corrected implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String left;
	private String right;
	
	public Corrected(){}
	public Corrected(String left,String right){
		this.left=left;
		this.right=right;
	}
	/**
	 * @return the left
	 */
	public String getLeft() {
		return left;
	}
	/**
	 * @param left the left to set
	 */
	public void setLeft(String left) {
		this.left = left;
	}
	/**
	 * @return the right
	 */
	public String getRight() {
		return right;
	}
	/**
	 * @param right the right to set
	 */
	public void setRight(String right) {
		this.right = right;
	}


}
