package com.ylz.packcommon.common.util;

import cn.jiguang.common.ClientConfig;
import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jpush.api.JPushClient;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Message;
import cn.jpush.api.push.model.Options;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.audience.AudienceTarget;
import cn.jpush.api.push.model.notification.AndroidNotification;
import cn.jpush.api.push.model.notification.IosNotification;
import cn.jpush.api.push.model.notification.Notification;
import cn.jpush.api.schedule.ScheduleResult;
import com.ylz.packcommon.common.comEnum.CommSF;
import com.ylz.packcommon.common.comEnum.CommonSuccessFail;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *  java后台极光推送
 */
public class JiguangPushUtils {

    private static final Logger log = LoggerFactory.getLogger(JiguangPushUtils.class);
    //正式
//    private static String masterSecretPatient = "4bb5ed86d7defa5c6085b401";
//    private static String appKeyPatient = "cc375d7798450c546e0a9df4";
//    private static String masterSecretDr = "1c4da5f51ac4301f1e075f4c";
//    private static String appKeyDr = "bdb6aa981f902cf9de0ab1c4";


    //测试
//    private static String masterSecretTestPatient = "654ad5e4f8abe533bc7a53a4";
//    private static String appKeyTestPatient = "d019c629a9ab3d0143c8c971";
//    private static String masterSecretTestDr = "1214fb756722a2afe8a0c363";
//    private static String appKeyTestDr = "1ee7ef5b493fca0be945228b";
//
//
//    //福州
//    private static String masterSecretFzPatient = "ba3b58ed393230fe9226bd77";
//    private static String appKeyFzPatient = "fd9ccd1e93b41297a1917574";
//    private static String masterSecretFzDr = "b8cb1b505aa740368cce35cf";
//    private static String appKeyFzDr = "9df72c205634e56b2517dca5";

    private static final String ALERT = "66666小胖胖是大帅哥~~~~~";

    public static void main(String[] args) {
        JiguangPushUtils s = new JiguangPushUtils();
        s.jiguangPush();
    }

    /**
     * 极光推送
     */
    public void jiguangPush(){
        try{
            String alias = "45fc0a2366834afbd90265fa3c53bda9";//声明别名
            log.info("对别名" + alias + "的用户推送信息");
            String content = "{'123':'333','456','6666'}";
//            PushResult result = push_tag_tagAnd(String.valueOf(alias),String.valueOf("350203012000"),ALERT,content,"0");
            PushResult result = push_patient(alias,"测试",JiguangPushUtils.ALERT);
//            ScheduleResult result = push_tag_tagAnd(String.valueOf(alias),String.valueOf("350203012000"),ALERT,content,"0","test_schedule_example","2017-09-13 16:51:25");
            if(result != null && result.isResultOK()){
                log.info("针对别名" + alias + "的信息推送成功！");
            }else{
                log.info("针对别名" + alias + "的信息推送失败！");
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }


    /**
     * 生成极光推送对象PushPayload（采用java SDK）
     * @param tag
     * @param alert
     * @return PushPayload
     */
    public static PushPayload buildPushObject_android_ios_tag_alert(String tag, String alert,String content){
        boolean result = true;
        return PushPayload.newBuilder()
                .setPlatform(Platform.android_ios())
                .setAudience(Audience.tag(tag))
                .setNotification(Notification.newBuilder()
                        .addPlatformNotification(AndroidNotification.newBuilder()
                                .addExtra("content", content)
                                .setAlert(alert)
                                .build())
                        .addPlatformNotification(IosNotification.newBuilder()
                                .addExtra("content", content)
                                .setAlert(alert)
                                .setSound("default")
                                .incrBadge(1)
                                .build())
                        .build())
                .setOptions(Options.newBuilder()
                        .setApnsProduction(result)//true-推送生产环境 false-推送开发环境（测试使用参数）
                        .setTimeToLive(90)//消息在JPush服务器的失效时间（测试使用参数）
                        .build())
                .build();
    }

    /**
     * 生成极光推送对象PushPayload（采用java SDK）
     * @param tag
     * @param alert
     * @return PushPayload
     */
    public static PushPayload buildPushObject_android_ios_tag_tagand_alert(String tag,String tagAnd, String alert,String content){
        boolean result = true;
        return PushPayload.newBuilder()
                .setPlatform(Platform.android_ios())
                .setAudience(Audience.newBuilder()
                        .addAudienceTarget(AudienceTarget.tag(tag.split(",")))
                        .addAudienceTarget(AudienceTarget.tag_and(tagAnd.split(",")))
                        .build())
                .setNotification(Notification.newBuilder()
                        .addPlatformNotification(AndroidNotification.newBuilder()
                                .addExtra("content", content)
                                .setAlert(alert)
                                .build())
                        .addPlatformNotification(IosNotification.newBuilder()
                                .addExtra("content", content)
                                .setAlert(alert)
                                .setSound("default")
                                .incrBadge(1)
                                .build())
                        .build())
                .setOptions(Options.newBuilder()
                        .setApnsProduction(result)//true-推送生产环境 false-推送开发环境（测试使用参数）
                        .setTimeToLive(90)//消息在JPush服务器的失效时间（测试使用参数）
                        .build())
                .build();
    }

    /**
     * 生成极光推送对象PushPayload（采用java SDK）
     * @param alias
     * @param alert
     * @return PushPayload
     */
    public static PushPayload buildPushObject_android_ios_alias_alert(String alias, String alert,String content){
        boolean result = true;
        return PushPayload.newBuilder()
                .setPlatform(Platform.android_ios())
                .setAudience(Audience.alias(alias))
                .setNotification(Notification.newBuilder()
                        .addPlatformNotification(AndroidNotification.newBuilder()
                                .addExtra("content", content)
                                .setAlert(alert)
                                .build())
                        .addPlatformNotification(IosNotification.newBuilder()
                                .addExtra("content", content)
                                .setAlert(alert)
                                .setSound("default")
                                .incrBadge(1)
                                .build())
                        .build())
                .setOptions(Options.newBuilder()
                        .setApnsProduction(result)//true-推送生产环境 false-推送开发环境（测试使用参数）
                        .setTimeToLive(90)//消息在JPush服务器的失效时间（测试使用参数）
                        .build())
                .build();
    }

    /**
     * 生成极光推送对象PushPayload（采用java SDK）自定义消息
     * @param alias
     * @param alert
     * @return PushPayload
     */
    public static PushPayload buildPushObject_android_ios_alias_alert_message(String alias, String alert,String content){
        boolean result = true;
        return PushPayload.newBuilder()
                .setPlatform(Platform.android_ios())
                .setAudience(Audience.alias(alias))
                .setMessage(Message.newBuilder().setMsgContent(content).setTitle(alert).build())
                .setOptions(Options.newBuilder()
                        .setApnsProduction(result)//true-推送生产环境 false-推送开发环境（测试使用参数）
                        .setTimeToLive(90)//消息在JPush服务器的失效时间（测试使用参数）
                        .build())
                .build();
    }
    /**
     * 极光推送方法(采用java SDK)
     * @param alias
     * @param alert
     * @return PushResult
     */
    public static PushResult push_dr(String alias, String alert,String content){

        try {
            //获取极光方式判断
            String state = PropertiesUtil.getConfValue("notDirectState");
            if(CommSF.YES.getValue().equals(state)){
                String master = PropertiesUtil.getConfValue("masterSecrettDr");
                String key = PropertiesUtil.getConfValue("appKeyDr");
                CloseableHttpClient client = HttpClients.createDefault();
                JSONObject jsonParam = new JSONObject();
                String urlLogin = PropertiesUtil.getConfValue("jgIntegrateUrl")+"?act=rtPushDr";
                jsonParam.put("alias",alias);
                jsonParam.put("alert",alert);
                jsonParam.put("content",content);
                jsonParam.put("master",master);
                jsonParam.put("key",key);
                String  str = HtmlUtils.getInstance().executeResponseJson(client, "post", urlLogin, jsonParam,"utf-8");
                if(StringUtils.isNotBlank(str)){
                    JSONObject jsonAll = JSONObject.fromObject(str);
                    if(jsonAll.get("msg")!= null && jsonAll.get("msg").equals("推送成功!")){
                        PushResult pushResult = new PushResult();
                        return pushResult;
                    }
                }
            }else {
                ClientConfig clientConfig = ClientConfig.getInstance();
                JPushClient   jpushClient = new JPushClient(PropertiesUtil.getConfValue("masterSecrettDr"),
                        PropertiesUtil.getConfValue("appKeyDr"), null, clientConfig);
                PushPayload payload = buildPushObject_android_ios_alias_alert(alias,alert,content);
                if(payload != null){
                    return jpushClient.sendPush(payload);
                }
            }
            return  null;
        } catch (APIConnectionException e) {
            log.error("Connection error. Should retry later. ", e);
            return null;
        } catch (APIRequestException e) {
            log.error("Error response from JPush server. Should review and fix it. ", e);
            return null;
        } catch (Exception e) {
            log.error("Error response from JPush server. Should review and fix it. ", e);
            return null;
        }
    }

    /**
     * 极光推送方法(采用java SDK)
     * @param tag
     * @param alert
     * @return PushResult
     */
    public static PushResult push_tag(String tag, String alert,String content){
        try {
            //获取极光方式判断
            String state = PropertiesUtil.getConfValue("notDirectState");
            if(CommSF.YES.getValue().equals(state)){
                String master = PropertiesUtil.getConfValue("masterSecretPatient");
                String key = PropertiesUtil.getConfValue("appKeyPatient");
                CloseableHttpClient client = HttpClients.createDefault();
                JSONObject jsonParam = new JSONObject();
                String urlLogin = PropertiesUtil.getConfValue("jgIntegrateUrl")+"?act=rtPushTag";
                jsonParam.put("tag",tag);
                jsonParam.put("alert",alert);
                jsonParam.put("content",content);
                jsonParam.put("master",master);
                jsonParam.put("key",key);
                String  str = HtmlUtils.getInstance().executeResponseJson(client, "post", urlLogin, jsonParam,"utf-8");
                if(StringUtils.isNotBlank(str)){
                    JSONObject jsonAll = JSONObject.fromObject(str);
                    if(jsonAll.get("msg")!= null && jsonAll.get("msg").equals("推送成功!")){
                        PushResult pushResult = new PushResult();
                        return pushResult;
                    }
                }
            }else{
                ClientConfig clientConfig = ClientConfig.getInstance();
                JPushClient   jpushClient = new JPushClient(PropertiesUtil.getConfValue("masterSecretPatient"),
                        PropertiesUtil.getConfValue("appKeyPatient"), null, clientConfig);
                PushPayload payload = buildPushObject_android_ios_tag_alert(tag,alert,content);
                if(payload != null){
                    return jpushClient.sendPush(payload);
                }
            }
            return  null;
        } catch (APIConnectionException e) {
            log.error("Connection error. Should retry later. ", e);
            return null;
        } catch (APIRequestException e) {
            log.error("Error response from JPush server. Should review and fix it. ", e);
            return null;
        } catch (Exception e) {
            log.error("Error response from JPush server. Should review and fix it. ", e);
            return null;
        }
    }


    /**
     * 极光推送方法(采用java SDK)
     * @param tag
     * @param alert
     * @return PushResult
     */
    public static PushResult push_tag_tagAnd(String tag, String tagand ,String alert,String content){
        try {
            //获取极光方式判断
            String state = PropertiesUtil.getConfValue("notDirectState");
            if(CommSF.YES.getValue().equals(state)){
                String master = PropertiesUtil.getConfValue("masterSecretPatient");
                String key = PropertiesUtil.getConfValue("appKeyPatient");
                CloseableHttpClient client = HttpClients.createDefault();
                JSONObject jsonParam = new JSONObject();
                String urlLogin = PropertiesUtil.getConfValue("jgIntegrateUrl")+"?act=rtPushTagAnd";
                jsonParam.put("tag",tag);
                jsonParam.put("tagAnd",tagand);
                jsonParam.put("alert",alert);
                jsonParam.put("content",content);
                jsonParam.put("master",master);
                jsonParam.put("key",key);
                String  str = HtmlUtils.getInstance().executeResponseJson(client, "post", urlLogin, jsonParam,"utf-8");
                if(StringUtils.isNotBlank(str)){
                    JSONObject jsonAll = JSONObject.fromObject(str);
                    if(jsonAll.get("msg")!= null && jsonAll.get("msg").equals("推送成功!")){
                        PushResult pushResult = new PushResult();
                        return pushResult;
                    }
                }
            }else{
                ClientConfig clientConfig = ClientConfig.getInstance();
                JPushClient   jpushClient = new JPushClient(PropertiesUtil.getConfValue("masterSecretPatient"),
                        PropertiesUtil.getConfValue("appKeyPatient"), null, clientConfig);
                PushPayload payload = buildPushObject_android_ios_tag_tagand_alert(tag,tagand,alert,content);
                if(payload != null){
                    return jpushClient.sendPush(payload);
                }
            }
            return  null;
        } catch (APIConnectionException e) {
            log.error("Connection error. Should retry later. ", e);
            return null;
        } catch (APIRequestException e) {
            log.error("Error response from JPush server. Should review and fix it. ", e);
            return null;
        } catch (Exception e) {
            log.error("Error response from JPush server. Should review and fix it. ", e);
            return null;
        }
    }

    /**
     * 极光推送方法(采用java SDK)
     * @param tag
     * @param alert
     * @return PushResult
     */
    public static ScheduleResult push_tag_tagAnd(String tag, String tagand , String alert, String content,
                                                 String name, String time){
        try {
            ClientConfig clientConfig = ClientConfig.getInstance();
            JPushClient   jpushClient = new JPushClient(PropertiesUtil.getConfValue("masterSecretPatient"),
                    PropertiesUtil.getConfValue("appKeyPatient"), null, clientConfig);
            PushPayload payload = buildPushObject_android_ios_tag_tagand_alert(tag,tagand,alert,content);
            if(payload != null){
                return jpushClient.createSingleSchedule(name,time,payload);
            }
            return  null;
        } catch (APIConnectionException e) {
            log.error("Connection error. Should retry later. ", e);
            return null;
        } catch (APIRequestException e) {
            log.error("Error response from JPush server. Should review and fix it. ", e);
            return null;
        }catch (Exception e) {
            log.error("Error response from JPush server. Should review and fix it. ", e);
            return null;
        }
    }


    /**
     * 极光推送方法(采用java SDK)
     * @param alias
     * @param alert
     * @return PushResult
     */
    public static PushResult push_patient(String alias, String alert,String content){
        try {
            //获取极光方式判断
            String state = PropertiesUtil.getConfValue("notDirectState");
            if(CommSF.YES.getValue().equals(state)){
                String master = PropertiesUtil.getConfValue("masterSecretPatient");
                String key = PropertiesUtil.getConfValue("appKeyPatient");
                CloseableHttpClient client = HttpClients.createDefault();
                JSONObject jsonParam = new JSONObject();
                String urlLogin = PropertiesUtil.getConfValue("jgIntegrateUrl")+"?act=rtPushPatient";
                jsonParam.put("alias",alias);
                jsonParam.put("alert",alert);
                jsonParam.put("content",content);
                jsonParam.put("master",master);
                jsonParam.put("key",key);
                String  str = HtmlUtils.getInstance().executeResponseJson(client, "post", urlLogin, jsonParam,"utf-8");
                if(StringUtils.isNotBlank(str)){
                    JSONObject jsonAll = JSONObject.fromObject(str);
                    if(jsonAll.get("msg")!= null && jsonAll.get("msg").equals("推送成功!")){
                        PushResult pushResult = new PushResult();
                        return pushResult;
                    }
                }
            }else{
                ClientConfig clientConfig = ClientConfig.getInstance();
                JPushClient   jpushClient = new JPushClient(PropertiesUtil.getConfValue("masterSecretPatient"),
                        PropertiesUtil.getConfValue("appKeyPatient"), null, clientConfig);
                PushPayload payload = buildPushObject_android_ios_alias_alert(alias,alert,content);
                if(payload != null){
                    return jpushClient.sendPush(payload);
                }
            }
            return  null;
        } catch (APIConnectionException e) {
            log.error("Connection error. Should retry later. ", e);
            return null;
        } catch (APIRequestException e) {
            log.error("Error response from JPush server. Should review and fix it. ", e);
            return null;
        }catch (Exception e) {
            log.error("Error response from JPush server. Should review and fix it. ", e);
            return null;
        }
    }

    /**
     * 极光推送方法(采用java SDK)
     * @param alias
     * @param alert
     * @return PushResult
     */
    public static PushResult push_tv(String alias, String alert,String content){
        try {
            ClientConfig clientConfig = ClientConfig.getInstance();
            JPushClient   jpushClient = new JPushClient(PropertiesUtil.getConfValue("masterSecrettTv"),
                    PropertiesUtil.getConfValue("appKeyTv"), null, clientConfig);
            PushPayload payload = buildPushObject_android_ios_alias_alert_message(alias,alert,content);
            if(payload != null){
                return jpushClient.sendPush(payload);
            }
            return  null;
        } catch (APIConnectionException e) {
            log.error("Connection error. Should retry later. ", e);
            return null;
        } catch (APIRequestException e) {
            log.error("Error response from JPush server. Should review and fix it. ", e);
            return null;
        }catch (Exception e) {
            log.error("Error response from JPush server. Should review and fix it. ", e);
            return null;
        }
    }

}
