package com.ylz.packcommon.dtkl;

import com.ylz.packcommon.common.util.MessageDtklUtil;

/**
 * Created by zk on 14-3-19.
 */
public class test {
	public static void main(String[] args) {
		String website = "xmcdc";
		String username = "rpwangg";
		String tel = "13860180662";
		String msg = MessageDtklUtil.encrypt(website, username, tel);
		System.out.println("pass is :" + msg);
	}
}
