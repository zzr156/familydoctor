/**
 * 
 */
package com.ylz.bizDo.jtapp.basicHealthEntity.findpersonandjzinfo;

import java.io.Serializable;
import java.util.List;


/**
 *<p>Title:PersonAndJzInfoDTO</p> 
 *<p>Description:</p>
 *<p>Company:ylz</p>
 *@author cairongjie
 *@date 2016-6-21 下午1:01:15
 */
public class PersonAndJzInfoDTO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 个人信息
	 */
	private PersonInfo personInfo;

	/**
	 * 最近就诊信息
	 */
	private List<JzInfoDTO> jzInfoDTOs;
	/**
	 * @return the personInfo
	 */
	public PersonInfo getPersonInfo() {
		return personInfo;
	}

	/**
	 * @param personInfo the personInfo to set
	 */
	public void setPersonInfo(PersonInfo personInfo) {
		this.personInfo = personInfo;
	}

	/**
	 * @return the jzInfoDTOs
	 */
	public List<JzInfoDTO> getJzInfoDTOs() {
		return jzInfoDTOs;
	}

	/**
	 * @param jzInfoDTOs the jzInfoDTOs to set
	 */
	public void setJzInfoDTOs(List<JzInfoDTO> jzInfoDTOs) {
		this.jzInfoDTOs = jzInfoDTOs;
	}
	
	
	
	

}
