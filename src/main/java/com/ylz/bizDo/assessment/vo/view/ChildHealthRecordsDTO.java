package com.ylz.bizDo.assessment.vo.view;

import java.io.Serializable;
import java.util.List;

/**
 * <p>Title: ChildHealthRecordsDTO.java<／p>
 * <p>Description: 儿童健康检查记录表<／p>
 * @author lyy
 * @date 2018年7月12日 上午8:45:56
 */
public class ChildHealthRecordsDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6319009688657518222L;

	private List<Fy_etfsjl> fyEtfsjlList;

	public List<Fy_etfsjl> getFyEtfsjlList() {
		return fyEtfsjlList;
	}

	public void setFyEtfsjlList(List<Fy_etfsjl> fyEtfsjlList) {
		this.fyEtfsjlList = fyEtfsjlList;
	}
	
}
