package com.ylz.bizDo.assessment.vo.view;

import java.io.Serializable;
import java.util.List;

import com.ylz.bizDo.plan.Entity.MxjbsfDTO;

/**
 * <p>Title: ChronicDiseaseFollowUpRecordsDTO.java<／p>
 * <p>Description: 慢性病随访记录结果<／p>
 * @author lyy
 * @date 2018年7月11日 下午10:01:18
 */
public class ChronicDiseaseFollowUpRecordsDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5308094170200441247L;

	private List<MxjbsfDTO> mxjbsfDTOList; // 慢性病随访记录

	public List<MxjbsfDTO> getMxjbsfDTOList() {
		return mxjbsfDTOList;
	}

	public void setMxjbsfDTOList(List<MxjbsfDTO> mxjbsfDTOList) {
		this.mxjbsfDTOList = mxjbsfDTOList;
	}

}
