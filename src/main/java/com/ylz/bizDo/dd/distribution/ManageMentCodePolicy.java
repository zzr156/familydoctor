package com.ylz.bizDo.dd.distribution;

import java.io.IOException;

/**
 * 订单编码生成策略接口
 * @author dws
 *
 */
public interface ManageMentCodePolicy {
//	public String create(String departmentId, String departmentCode, String getTime) throws IOException;
	public String createAlipayPch(String pch,String getTime) throws Exception;
}
