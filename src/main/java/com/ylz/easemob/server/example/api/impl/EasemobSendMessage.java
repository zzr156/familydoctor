package com.ylz.easemob.server.example.api.impl;

import com.ylz.easemob.server.example.api.SendMessageAPI;
import com.ylz.easemob.server.example.comm.EasemobAPI;
import com.ylz.easemob.server.example.comm.OrgInfo;
import com.ylz.easemob.server.example.comm.ResponseHandler;
import com.ylz.easemob.server.example.comm.TokenUtil;
import io.swagger.client.ApiException;
import io.swagger.client.api.MessagesApi;
import io.swagger.client.model.Msg;

public class EasemobSendMessage implements SendMessageAPI {
    private ResponseHandler responseHandler = new ResponseHandler();
    private MessagesApi api = new MessagesApi();
    @Override
    public Object sendMessage(final Object payload) {
        return responseHandler.handle(new EasemobAPI() {
            @Override
            public Object invokeEasemobAPI() throws ApiException {
                return api.orgNameAppNameMessagesPost(OrgInfo.ORG_NAME,OrgInfo.APP_NAME,TokenUtil.getAccessToken(), (Msg) payload);
            }
        });
    }
}
