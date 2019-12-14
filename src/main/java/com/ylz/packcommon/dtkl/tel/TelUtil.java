package com.ylz.packcommon.dtkl.tel;

/**
 * 电话工具
 * 
 * @author cch
 * 
 */
public class TelUtil {
	/**
	 * 用于初始化电话工具
	 * 
	 * @return
	 */
	public static Itel iniTel() {
		// 这个地方以后要做成可配置的，根据不同的配置，初始化不同的类
		return new TelXmcdcImp();
	}
}
