package com.ylz.packcommon.common.wechat.pojo;

/**
 * 凭证
 * 
 * @author xyy
 * @date 2014-10-14
 * 
 */
public class Token {
	
	// 接口访问凭证
	private String accessToken;
	// 凭证有效期，单位：秒
	private int expiresIn;

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public int getExpiresIn() {
		return expiresIn;
	}

	public void setExpiresIn(int expiresIn) {
		this.expiresIn = expiresIn;
	}
}