package com.ylz.bizDo.app.dao.impl;

import cn.jpush.api.push.PushResult;
import com.thoughtworks.xstream.XStream;
import com.ylz.bizDo.app.dao.AppMsgCodeDao;
import com.ylz.bizDo.app.po.AppMsgCode;
import com.ylz.bizDo.jtapp.commonEntity.AppMessageEntity;
import com.ylz.bizDo.jtapp.commonEntity.AppMessageEntityXml;
import com.ylz.packaccede.allDo.SysDao;
import com.ylz.packcommon.common.comEnum.CommSF;
import com.ylz.packcommon.common.util.HtmlUtils;
import com.ylz.packcommon.common.util.JsonUtil;
import com.ylz.packcommon.common.util.PropertiesUtil;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Service("appMsgCodeDao")
@Transactional(rollbackForClassName={"Exception"})
public class AppMsgCodeDaoImpl implements AppMsgCodeDao {

    @Autowired
    private SysDao sysDao;

    @Override
    public boolean sendMessage(String tel, String content) throws Exception{
        boolean result = false;
        try {
            AppMsgCode code = this.sysDao.getAppMsgCodeDao().findByOne();
            if(code != null){
                Calendar nowDate = Calendar.getInstance();
                long start = nowDate.getTimeInMillis();
                long end = code.getMsgLastTime().getTimeInMillis();
                long jg = Long.parseLong(PropertiesUtil.getConfValue("lastTime"));
                if(start - end <=jg){
                    HtmlUtils.msgMessage(code.getMsgResultBody(),tel,content);
                }else{
                    String msgCode = HtmlUtils.msgLogin();
                    AppMessageEntity v = JsonUtil.fromJson(msgCode,AppMessageEntity.class);
                    if(v != null){
                        HtmlUtils.msgMessage(v.getResultBody(),tel,content);
                        code.setMsgResultBody(v.getResultBody());
                        code.setMsgLastTime(Calendar.getInstance());
                        this.sysDao.getServiceDo().modify(code);
                    }
                }
            }else{
                String msgCode = HtmlUtils.msgLogin();
                AppMessageEntity v = JsonUtil.fromJson(msgCode,AppMessageEntity.class);
                if(v != null){
                    HtmlUtils.msgMessage(v.getResultBody(),tel,content);
                    code = new AppMsgCode();
                    code.setMsgResultBody(v.getResultBody());
                    code.setMsgLastTime(Calendar.getInstance());
                    this.sysDao.getServiceDo().add(code);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }

    public AppMessageEntityXml sendMessageNew(String tel,String content) throws Exception{
        try {
            HttpClient client = HttpClients.createDefault();
            //获取极光方式判断
            String state = PropertiesUtil.getConfValue("notDirectState");
            if(CommSF.YES.getValue().equals(state)){
                JSONObject jsonParam = new JSONObject();
                String urlLogin = PropertiesUtil.getConfValue("jgIntegrateUrl")+"?act=rtSendMobile";
                jsonParam.put("tel",tel);
                jsonParam.put("content",content);
                String  str = HtmlUtils.getInstance().executeResponseJson(client, "post", urlLogin, jsonParam,"utf-8");
                if(org.apache.commons.lang.StringUtils.isNotBlank(str)){
                    JSONObject jsonAll = JSONObject.fromObject(str);
                    if(jsonAll.get("msg")!= null && jsonAll.get("msg").equals("发送成功!")){
                        AppMessageEntityXml entitys = JsonUtil.fromJson(jsonAll.get("vo").toString(),AppMessageEntityXml.class);
                        return  entitys;
                    }
                }
            }else{
                List<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("OperID","yilianzhong"));
                params.add(new BasicNameValuePair("OperPass","yFcppL6a"));
                params.add(new BasicNameValuePair("AppendID","999"));
                params.add(new BasicNameValuePair("DesMobile",tel));
                params.add(new BasicNameValuePair("Content", content));
                params.add(new BasicNameValuePair("Content_Code","1"));
                String urlLogin = "http://qxtsms.guodulink.net:8000/QxtSms/QxtFirewall";
                String str1 = HtmlUtils.getInstance().executeResponse(client, "post", urlLogin, params,"utf-8");
                XStream xstream =new XStream();
                xstream.alias("response", AppMessageEntityXml.class);
                AppMessageEntityXml xml = (AppMessageEntityXml) xstream.fromXML(str1);
                return  xml;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public AppMsgCode findByOne() throws Exception{
        return (AppMsgCode)sysDao.getServiceDo().getSessionFactory().getCurrentSession().createCriteria(AppMsgCode.class).uniqueResult();
    }
}
