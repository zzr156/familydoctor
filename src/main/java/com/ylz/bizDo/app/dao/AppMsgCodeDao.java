package com.ylz.bizDo.app.dao;

import com.ylz.bizDo.app.po.AppMsgCode;
import com.ylz.bizDo.jtapp.commonEntity.AppMessageEntityXml;

/**
 * Created by asus on 2017/6/22.
 */
public interface AppMsgCodeDao {

    public boolean sendMessage(String tel,String content) throws Exception;

    public AppMessageEntityXml sendMessageNew(String tel,String content) throws Exception;

    public AppMsgCode findByOne() throws Exception;
}
