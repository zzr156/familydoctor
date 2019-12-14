package com.ylz.bizDo.assessment.vo.view;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * Title: GravidaFollowUpRecordsDTO.java<／p>
 * <p>
 * Description: 孕产妇随访记录结果<／p>
 * 
 * @author lyy
 * @date 2018年7月11日 下午9:42:57
 */
public class GravidaFollowUpRecordsDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4292847990557275242L;

	private List<Fy_edwcsf> yzFyEdwcsfList; // 孕中期随访次数
	private List<Fy_edwcsf> ywFyEdwcsfList; // 孕晚期随访次数
	private List<Fy_chfsjl> fyChfsjlList; // 产后访视

	public List<Fy_edwcsf> getYzFyEdwcsfList() {
		return yzFyEdwcsfList;
	}

	public void setYzFyEdwcsfList(List<Fy_edwcsf> yzFyEdwcsfList) {
		this.yzFyEdwcsfList = yzFyEdwcsfList;
	}

	public List<Fy_edwcsf> getYwFyEdwcsfList() {
		return ywFyEdwcsfList;
	}

	public void setYwFyEdwcsfList(List<Fy_edwcsf> ywFyEdwcsfList) {
		this.ywFyEdwcsfList = ywFyEdwcsfList;
	}

	public List<Fy_chfsjl> getFyChfsjlList() {
		return fyChfsjlList;
	}

	public void setFyChfsjlList(List<Fy_chfsjl> fyChfsjlList) {
		this.fyChfsjlList = fyChfsjlList;
	}

}
