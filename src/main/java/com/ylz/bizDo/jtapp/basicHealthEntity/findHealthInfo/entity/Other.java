/**
 * 
 */
package com.ylz.bizDo.jtapp.basicHealthEntity.findHealthInfo.entity;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;

/**
 *<p>Title:Other</p> 
 *<p>Description:</p>
 *<p>Company:ylz</p>
 *@author cairongjie
 *@date 2016-7-8 下午4:39:11
 */
@XmlType(name="com.start.webInterface.common.dto.byXmlHealthInfo.Other")
@XmlAccessorType(XmlAccessType.NONE)
public class Other implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4716712309459049243L;
	private SixMinuteWalk sixMinuteWalk;
	private String Sa02;
	private Lung2 lung;
	private String SGRQ;
	
	public Other(){}
	public Other(SixMinuteWalk sixMinuteWalk,String Sa02,
			Lung2 lung,String SGRQ){
		this.sixMinuteWalk=sixMinuteWalk;
		this.Sa02=Sa02;
		this.lung=lung;
		this.SGRQ=SGRQ;
	}
	/**
	 * @return the sixMinuteWalk
	 */
	public SixMinuteWalk getSixMinuteWalk() {
		return sixMinuteWalk;
	}
	/**
	 * @param sixMinuteWalk the sixMinuteWalk to set
	 */
	public void setSixMinuteWalk(SixMinuteWalk sixMinuteWalk) {
		this.sixMinuteWalk = sixMinuteWalk;
	}
	/**
	 * @return the sa02
	 */
	public String getSa02() {
		return Sa02;
	}
	/**
	 * @param sa02 the sa02 to set
	 */
	public void setSa02(String sa02) {
		Sa02 = sa02;
	}
	/**
	 * @return the lung
	 */
	public Lung2 getLung() {
		return lung;
	}
	/**
	 * @param lung the lung to set
	 */
	public void setLung(Lung2 lung) {
		this.lung = lung;
	}
	/**
	 * @return the sGRQ
	 */
	public String getSGRQ() {
		return SGRQ;
	}
	/**
	 * @param sGRQ the sGRQ to set
	 */
	public void setSGRQ(String sGRQ) {
		SGRQ = sGRQ;
	}
	
	
}
