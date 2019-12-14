package com.ylz.bizDo.assessment.vo.view;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * Title: AntenatalRecordsDTO.java<／p>
 * <p>
 * Description: 孕产妇基卫产检记录结果<／p>
 * 
 * @author lyy
 * @date 2018年7月11日 下午9:35:20
 */
public class AntenatalRecordsDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3581745204867994067L;

	private List<TjbgAllInfoDTO2> tjbgList; // 体检报告
	private List<Fy_fncqjcDTO> fyFncqjcDTOList; // 产前检查

	public List<TjbgAllInfoDTO2> getTjbgList() {
		return tjbgList;
	}

	public void setTjbgList(List<TjbgAllInfoDTO2> tjbgList) {
		this.tjbgList = tjbgList;
	}

	public List<Fy_fncqjcDTO> getFyFncqjcDTOList() {
		return fyFncqjcDTOList;
	}

	public void setFyFncqjcDTOList(List<Fy_fncqjcDTO> fyFncqjcDTOList) {
		this.fyFncqjcDTOList = fyFncqjcDTOList;
	}

}
