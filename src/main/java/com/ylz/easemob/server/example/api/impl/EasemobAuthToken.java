package com.ylz.easemob.server.example.api.impl;

import com.ylz.easemob.server.example.api.AuthTokenAPI;
import com.ylz.easemob.server.example.comm.TokenUtil;


public class EasemobAuthToken implements AuthTokenAPI{

	@Override
	public Object getAuthToken(){
		return TokenUtil.getAccessToken();
	}
}
