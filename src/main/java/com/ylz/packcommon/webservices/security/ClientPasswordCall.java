package com.ylz.packcommon.webservices.security;

import org.apache.ws.security.WSPasswordCallback;

import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.UnsupportedCallbackException;
import java.io.IOException;

public class ClientPasswordCall implements CallbackHandler
{

	@Override
	public void handle(Callback[] callbacks) throws IOException,UnsupportedCallbackException {
		for(int i=0;i<callbacks.length;i++)     
        {     
             WSPasswordCallback pc = (WSPasswordCallback)callbacks[i];
             pc.setIdentifier("hzk");
             pc.setPassword("a");
        }
		
	}

}
