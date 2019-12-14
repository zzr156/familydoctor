package com.ylz.bizDo.cd.dao;

import com.ylz.bizDo.cd.po.CdDissolutionWarning;
import com.ylz.bizDo.cd.po.CdMessage;
import com.ylz.bizDo.cd.vo.CdDissolutionWarningVo;

import java.util.List;
import java.util.Map;

/**
 * 消息管理业务定义
 * @author admin
 *
 */
public interface CdMessageDao {
	/**
	 * 查询
	 */
	public List<CdMessage> find() throws Exception;

	/**
	 * 获取当前机构下医生的解约预警设置
	 * WangCheng
	 * @param vo
	 * @return
	 */
	public CdDissolutionWarning getDissolutionWarning(CdDissolutionWarningVo vo) throws Exception;

	/**
	 * 计算即将解约的签约量
	 * WangCheng
	 * @param orgId
	 * @param warningDay
	 * @return
	 * @throws Exception
	 */
	public int countDissolutionNum(String orgId,String areaCode,String warningDay) throws Exception;

}
