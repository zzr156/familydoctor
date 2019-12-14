/**
 * 
 */
package com.ylz.bizDo.jtapp.basicHealthEntity.findHealthInfo.entity;

import java.io.Serializable;

/**
 *<p>Title:ObservationRange</p> 
 *<p>Description:</p>
 *<p>Company:ylz</p>
 *@author cairongjie
 *@date 2016-6-29 上午11:43:30
 */
public class ObservationRange implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String notes;
	private Low low;
	private High high;
	
	public ObservationRange(){}
	public ObservationRange(String notes,Low low,High high){
		this.notes=notes;
		this.low=low;
		this.high=high;
	}
	
	/**
	 * @return the notes
	 */
	public String getNotes() {
		return notes;
	}
	/**
	 * @param notes the notes to set
	 */
	public void setNotes(String notes) {
		this.notes = notes;
	}
	/**
	 * @return the low
	 */
	public Low getLow() {
		return low;
	}
	/**
	 * @param low the low to set
	 */
	public void setLow(Low low) {
		this.low = low;
	}
	/**
	 * @return the high
	 */
	public High getHigh() {
		return high;
	}
	/**
	 * @param high the high to set
	 */
	public void setHigh(High high) {
		this.high = high;
	}
	
	
	
	
	

}
