package com.ylz.packcommon.webservices.security;

import org.apache.ws.security.WSPasswordCallback;

import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.UnsupportedCallbackException;
import java.io.IOException;


public class ServerPasswordCall implements CallbackHandler
{

	@Override
	public void handle(Callback[] callbacks) throws IOException,UnsupportedCallbackException {
		WSPasswordCallback pc = (WSPasswordCallback) callbacks[0];  
//		String pw = pc.getPassword();
        String idf = pc.getIdentifier();
        String pwd=WebUser.getPwd(idf);
        if (pwd!=null) {
        	//如果用户名正确，从用户表中查找该用户的密码，设置到pc中，pc会自动去比较传过来的密码是否正确
        	pc.setPassword(pwd);
        }
        else  
        {  
            throw new SecurityException("ws用户错误");  
        }
		
	}

}
