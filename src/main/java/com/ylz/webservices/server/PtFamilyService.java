package com.ylz.webservices.server;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;


/**
 * 
 * 如果对方使用的工程也是srping架构，可以把接口类与实体类打成jar包,给对方，对方通过srping配置调用。
 *
 */
@WebService
@SOAPBinding(style = SOAPBinding.Style.RPC)
public interface PtFamilyService {
	
	public String sayHello(@WebParam(name = "name") String ttt);

	public String getSignInfoByCard(String json);

}
