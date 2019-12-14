package com.ylz.packcommon.webservices.client;

import com.ylz.packcommon.webservices.server.IHelloService;
import org.springframework.stereotype.Component;

@Component
public class SysClient {

    //测试webservices客户端调用
    private IHelloService hellServiceClient;

    public IHelloService getHellServiceClient() {
        return hellServiceClient;
    }

    public void setHellServiceClient(IHelloService hellServiceClient) {
        this.hellServiceClient = hellServiceClient;
    }

}
