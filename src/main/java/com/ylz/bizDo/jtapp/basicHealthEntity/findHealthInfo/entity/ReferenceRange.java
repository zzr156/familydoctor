/**
 * 
 */
package com.ylz.bizDo.jtapp.basicHealthEntity.findHealthInfo.entity;

import java.io.Serializable;

/**
 *<p>Title:ReferenceRange</p> 
 *<p>Description:</p>
 *<p>Company:ylz</p>
 *@author cairongjie
 *@date 2016-6-29 上午11:42:04
 */
public class ReferenceRange implements Serializable{
	
	private ObservationRange observationRange;
	
	public ReferenceRange(){}
	public ReferenceRange(ObservationRange observationRange){
		this.observationRange=observationRange;
	}

	/**
	 * @return the observationRange
	 */
	public ObservationRange getObservationRange() {
		return observationRange;
	}

	/**
	 * @param observationRange the observationRange to set
	 */
	public void setObservationRange(ObservationRange observationRange) {
		this.observationRange = observationRange;
	}
	
	

}
