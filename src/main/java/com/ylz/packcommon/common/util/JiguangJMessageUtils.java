package com.ylz.packcommon.common.util;

import cn.jiguang.common.ClientConfig;
import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jiguang.common.resp.ResponseWrapper;
import cn.jmessage.api.JMessageClient;
import cn.jmessage.api.common.model.RegisterInfo;
import cn.jmessage.api.common.model.cross.CrossFriendPayload;
import cn.jmessage.api.common.model.cross.CrossGroup;
import cn.jmessage.api.group.CreateGroupResult;
import cn.jmessage.api.user.UserInfoResult;
import cn.jpush.api.JPushClient;
import cn.jpush.api.push.PushResult;
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
import com.ylz.packcommon.common.comEnum.CommonEnable;
import com.ylz.packcommon.common.comEnum.CommonShortType;
import com.ylz.packcommon.common.comEnum.CommonSuccessFail;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 *  java后台极光IM
 */
public class JiguangJMessageUtils {

    private static final Logger log = LoggerFactory.getLogger(JiguangJMessageUtils.class);

    public static void main(String[] args) {

    }

    /**
     * 极光注册账号
     * @param userId
     * @param type
     * @return String
     */
    public static String getJmessageregisterUsers(String userId,String userName,String userGender,String type){
        try {
            int grender = 0;
            if(StringUtils.isNotBlank(userGender) ){
                if(userGender.equals("1")  || userGender.equals("2")) {
                    grender = Integer.parseInt(userGender);
                }
            }
            Map<String,Object> map = JiguangJMessageUtils.getJmessageKey(type);
             String master = map.get("master") != null ? map.get("master").toString() : null;
             String key = map.get("key") != null ? map.get("key").toString() : null;
            //获取极光方式判断
            String state = PropertiesUtil.getConfValue("notDirectState");
            if(CommSF.YES.getValue().equals(state)){
                CloseableHttpClient client = HttpClients.createDefault();
                JSONObject jsonParam = new JSONObject();
                String urlLogin = PropertiesUtil.getConfValue("jgIntegrateUrl")+"?act=rtJmessageregisterUsers";
                jsonParam.put("userId",userId);
                jsonParam.put("userName",userName);
                jsonParam.put("userGender",grender);
                jsonParam.put("master",master);
                jsonParam.put("key",key);
                String  str = HtmlUtils.getInstance().executeResponseJson(client, "post", urlLogin, jsonParam,"utf-8");
                if(StringUtils.isNotBlank(str)){
                    JSONObject jsonAll = JSONObject.fromObject(str);
                    if(jsonAll.get("msg")!= null && jsonAll.get("msg").equals("1")){
                        return "1";
                    }
                }
            }else{
                if(StringUtils.isNotBlank(master) && StringUtils.isNotBlank(key)){
                    JMessageClient jMessageClient = new JMessageClient(key,master);
                    RegisterInfo info = RegisterInfo.newBuilder()
                            .setUsername(userId).setPassword(userId)
                            .setNickname(userName).setGender(grender)
                            .build();
                    RegisterInfo[] registerInfos = new RegisterInfo[]{info};
                    String result = jMessageClient.registerUsers(registerInfos);
                    return  result;
                }
            }
        } catch (APIConnectionException e) {
            log.error("Connection error. Should retry later. ", e);
            return null;
        } catch (APIRequestException e) {
            log.error("Error response from JMessage server. Should review and fix it. ", e);
            return null;
        }catch (Exception e) {
            log.error("Error response from JPush server. Should review and fix it. ", e);
            return null;
        }
        return  null;
    }

    /**
     * 获取极光用户信息
     * @param userId
     * @param type
     * @return
     */
    public static UserInfoResult getJmessageUserInfo(String userId,String type){
        try {
            Map<String,Object> map = JiguangJMessageUtils.getJmessageKey(type);
            String master = map.get("master") != null ? map.get("master").toString() : null;
            String key = map.get("key") != null ? map.get("key").toString() : null;
            //获取极光方式判断
            String state = PropertiesUtil.getConfValue("notDirectState");
            if(CommSF.YES.getValue().equals(state)){
                CloseableHttpClient client = HttpClients.createDefault();
                JSONObject jsonParam = new JSONObject();
                String urlLogin = PropertiesUtil.getConfValue("jgIntegrateUrl")+"?act=rtJmessageUserInfo";
                jsonParam.put("userId",userId);
                jsonParam.put("master",master);
                jsonParam.put("key",key);
                String  str = HtmlUtils.getInstance().executeResponseJson(client, "post", urlLogin, jsonParam,"utf-8");
                if(StringUtils.isNotBlank(str)){
                    JSONObject jsonAll = JSONObject.fromObject(str);
                    if(jsonAll.get("msg")!= null && jsonAll.get("msg").equals("1")){
                        UserInfoResult userInfoResult = new UserInfoResult();
                        return userInfoResult;
                    }
                }
            }else{
                if(StringUtils.isNotBlank(master) && StringUtils.isNotBlank(key)){
                    JMessageClient jMessageClient = new JMessageClient(key,master);
                    UserInfoResult userInfoResult = jMessageClient.getUserInfo(userId);
                    return  userInfoResult;
                }
            }
        } catch (APIConnectionException e) {
            log.error("Connection error. Should retry later. ", e);
            return null;
        } catch (APIRequestException e) {
            log.error("Error response from JMessage server. Should review and fix it. ", e);
            return null;
        }catch (Exception e) {
            log.error("Error response from JPush server. Should review and fix it. ", e);
            return null;
        }
        return  null;
    }

    /**
     * 极光跨平台加好友
     * @param drId
     * @param patientId
     * @param appKeyPatient
     * @param type
     * @return
     */
    public static ResponseWrapper getJmessageaddCrossFriends(String drId,String patientId,String appKeyPatient,String type){
        try {
            Map<String,Object> map = JiguangJMessageUtils.getJmessageKey(type);
            String master = map.get("master") != null ? map.get("master").toString() : null;
            String key = map.get("key") != null ? map.get("key").toString() : null;
            //获取极光方式判断
            String state = PropertiesUtil.getConfValue("notDirectState");
            if(CommSF.YES.getValue().equals(state)){
                CloseableHttpClient client = HttpClients.createDefault();
                JSONObject jsonParam = new JSONObject();
                String urlLogin = PropertiesUtil.getConfValue("jgIntegrateUrl")+"?act=rtJmessageaddCrossFriends";
                jsonParam.put("userId",drId);
                jsonParam.put("userFriendId",patientId);
                jsonParam.put("appKeyPatient",appKeyPatient);
                jsonParam.put("master",master);
                jsonParam.put("key",key);
                String  str = HtmlUtils.getInstance().executeResponseJson(client, "post", urlLogin, jsonParam,"utf-8");
                if(StringUtils.isNotBlank(str)){
                    JSONObject jsonAll = JSONObject.fromObject(str);
                    if(jsonAll.get("msg")!= null && jsonAll.get("msg").equals("1")){
                        ResponseWrapper responseWrapper = new ResponseWrapper();
                        return responseWrapper;
                    }
                }
            }else{
                if(StringUtils.isNotBlank(master) && StringUtils.isNotBlank(key)){
                    JMessageClient jMessageClient = new JMessageClient(key,master);
                    String[] users = new String[]{patientId};
                    CrossFriendPayload payload = new CrossFriendPayload(appKeyPatient,users);
                    ResponseWrapper responseWrapper = jMessageClient.addCrossFriends(drId,payload);
                    return responseWrapper;
                }
            }
        } catch (APIConnectionException e) {
            log.error("Connection error. Should retry later. ", e);
            return null;
        } catch (APIRequestException e) {
            log.error("Error response from JMessage server. Should review and fix it. ", e);
            return null;
        }catch (Exception e) {
            log.error("Error response from JPush server. Should review and fix it. ", e);
            return null;
        }
        return  null;
    }

    /**
     *  极光创建群,userList不为空,在创建群时 可以把人添加进去
     * @param userId
     * @param teamName
     * @param userList
     * @param type
     * @return
     */
    public static CreateGroupResult getJmessageCreateGroup(String userId,String teamName,String[] userList,String type){
        try {
            Map<String,Object> map = JiguangJMessageUtils.getJmessageKey(type);
            String master = map.get("master") != null ? map.get("master").toString() : null;
            String key = map.get("key") != null ? map.get("key").toString() : null;
            //获取极光方式判断
            String state = PropertiesUtil.getConfValue("notDirectState");
            if(CommSF.YES.getValue().equals(state)){
                CloseableHttpClient client = HttpClients.createDefault();
                JSONObject jsonParam = new JSONObject();
                String urlLogin = PropertiesUtil.getConfValue("jgIntegrateUrl")+"?act=rtJmessageCreateGroup";
                jsonParam.put("userId",userId);
                jsonParam.put("teamName",teamName);
                jsonParam.put("userList",userList);
                jsonParam.put("master",master);
                jsonParam.put("key",key);
                String  str = HtmlUtils.getInstance().executeResponseJson(client, "post", urlLogin, jsonParam,"utf-8");
                if(StringUtils.isNotBlank(str)){
                    JSONObject jsonAll = JSONObject.fromObject(str);
                    if(jsonAll.get("msg")!= null && jsonAll.get("msg").equals("1")){
                        CreateGroupResult createGroupResult = new CreateGroupResult();
                        return createGroupResult;
                    }
                }
            }else{
                if(StringUtils.isNotBlank(master) && StringUtils.isNotBlank(key)){
                    JMessageClient jMessageClient = new JMessageClient(key,master);
                    CreateGroupResult createGroupResult = jMessageClient.createGroup(userId,teamName,teamName,null,2,userList);
                    return createGroupResult;
                }
            }
        } catch (APIConnectionException e) {
            log.error("Connection error. Should retry later. ", e);
            return null;
        } catch (APIRequestException e) {
            log.error("Error response from JMessage server. Should review and fix it. ", e);
            return null;
        }catch (Exception e) {
            log.error("Error response from JPush server. Should review and fix it. ", e);
            return null;
        }
        return  null;
    }

    /**
     * 添加同应用群成员
     * @param groupId
     * @param addUserList
     * @param removeUserList
     * @param type
     * @return
     */
    public static String getJmessageaddOrRemoveMembers(String groupId,String[] addUserList,String[] removeUserList,String type){
        String result = CommonSuccessFail.FAIL.getValue();
        try {
            Map<String,Object> map = JiguangJMessageUtils.getJmessageKey(type);
            String master = map.get("master") != null ? map.get("master").toString() : null;
            String key = map.get("key") != null ? map.get("key").toString() : null;
            //获取极光方式判断
            String state = PropertiesUtil.getConfValue("notDirectState");
            if(CommSF.YES.getValue().equals(state)){
                CloseableHttpClient client = HttpClients.createDefault();
                JSONObject jsonParam = new JSONObject();
                String urlLogin = PropertiesUtil.getConfValue("jgIntegrateUrl")+"?act=rtJmessageaddOrRemoveMembers";
                jsonParam.put("groupId",groupId);
                jsonParam.put("addUserList",addUserList);
                jsonParam.put("removeUserList",removeUserList);
                jsonParam.put("master",master);
                jsonParam.put("key",key);
                String  str = HtmlUtils.getInstance().executeResponseJson(client, "post", urlLogin, jsonParam,"utf-8");
                if(StringUtils.isNotBlank(str)){
                    JSONObject jsonAll = JSONObject.fromObject(str);
                    if(jsonAll.get("msg")!= null && jsonAll.get("msg").equals("1")){
                        result = CommonSuccessFail.SUCCESS.getValue();
                    }
                }
            }else{
                if(StringUtils.isNotBlank(master) && StringUtils.isNotBlank(key)){
                    JMessageClient jMessageClient = new JMessageClient(key,master);
                    jMessageClient.addOrRemoveMembers(Long.parseLong(groupId),addUserList,removeUserList);
                    result = CommonSuccessFail.SUCCESS.getValue();
                }
            }
        } catch (APIConnectionException e) {
            log.error("Connection error. Should retry later. ", e);
            return result;
        } catch (APIRequestException e) {
            log.error("Error response from JMessage server. Should review and fix it. ", e);
            return result;
        }catch (Exception e) {
            log.error("Error response from JPush server. Should review and fix it. ", e);
            return result;
        }
        return  result;
    }

    /**
     * 添加跨应用群成员
     * @param groupId
     * @param addUserList
     * @param removeUserList
     * @param type
     * @return
     */
    public static ResponseWrapper getJmessageaddOrRemoveCrossMembers(String groupId,String[] addUserList,String[] removeUserList
            ,String appKeyPatient,String type){
        String result = CommonSuccessFail.FAIL.getValue();
        try {
            Map<String,Object> map = JiguangJMessageUtils.getJmessageKey(type);
            String master = map.get("master") != null ? map.get("master").toString() : null;
            String key = map.get("key") != null ? map.get("key").toString() : null;
            //获取极光方式判断
            String state = PropertiesUtil.getConfValue("notDirectState");
            if(CommSF.YES.getValue().equals(state)){
                CloseableHttpClient client = HttpClients.createDefault();
                JSONObject jsonParam = new JSONObject();
                String urlLogin = PropertiesUtil.getConfValue("jgIntegrateUrl")+"?act=rtJmessageaddOrRemoveCrossMembers";
                jsonParam.put("groupId",groupId);
                jsonParam.put("addUserList",addUserList);
                jsonParam.put("removeUserList",removeUserList);
                jsonParam.put("appKeyPatient",appKeyPatient);
                jsonParam.put("master",master);
                jsonParam.put("key",key);
                String  str = HtmlUtils.getInstance().executeResponseJson(client, "post", urlLogin, jsonParam,"utf-8");
                if(StringUtils.isNotBlank(str)){
                    JSONObject jsonAll = JSONObject.fromObject(str);
                    if(jsonAll.get("msg")!= null && jsonAll.get("msg").equals("1")){
                        ResponseWrapper responseWrapper = new ResponseWrapper();
                        return responseWrapper;
                    }
                }
            }else{
                if(StringUtils.isNotBlank(master) && StringUtils.isNotBlank(key)){
                    JMessageClient jMessageClient = new JMessageClient(key,master);
                    CrossGroup crossGroup = new CrossGroup.Builder().setAppKey(appKeyPatient).setAddUsers(addUserList).build();
                    CrossGroup[] crossGroups = new CrossGroup[]{crossGroup};
                    ResponseWrapper responseWrapper = jMessageClient.addOrRemoveCrossGroupMember(Long.parseLong(groupId),crossGroups);
                    return responseWrapper;
                }
            }
        } catch (APIConnectionException e) {
            log.error("Connection error. Should retry later. ", e);
            return null;
        } catch (APIRequestException e) {
            log.error("Error response from JMessage server. Should review and fix it. ", e);
            return null;
        }catch (Exception e) {
            log.error("Error response from JPush server. Should review and fix it. ", e);
            return null;
        }
        return  null;
    }



    /**
     * 删除群
     * @param groupId
     * @param type
     * @return
     */
    public static String getJmessageDelGroup(String groupId,String type){
        String result = CommonSuccessFail.FAIL.getValue();
        try {
            Map<String,Object> map = JiguangJMessageUtils.getJmessageKey(type);
            String master = map.get("master") != null ? map.get("master").toString() : null;
            String key = map.get("key") != null ? map.get("key").toString() : null;

            //获取极光方式判断
            String state = PropertiesUtil.getConfValue("notDirectState");
            if(CommSF.YES.getValue().equals(state)){
                CloseableHttpClient client = HttpClients.createDefault();
                JSONObject jsonParam = new JSONObject();
                String urlLogin = PropertiesUtil.getConfValue("jgIntegrateUrl")+"?act=rtJmessageDelGroup";
                jsonParam.put("groupId",groupId);
                jsonParam.put("master",master);
                jsonParam.put("key",key);
                String  str = HtmlUtils.getInstance().executeResponseJson(client, "post", urlLogin, jsonParam,"utf-8");
                if(StringUtils.isNotBlank(str)){
                    JSONObject jsonAll = JSONObject.fromObject(str);
                    if(jsonAll.get("msg")!= null && jsonAll.get("msg").equals("1")){
                        result = CommonSuccessFail.SUCCESS.getValue();
                    }
                }
            }else{
                if(StringUtils.isNotBlank(master) && StringUtils.isNotBlank(key)){
                    JMessageClient jMessageClient = new JMessageClient(key,master);
                    jMessageClient.deleteGroup(Long.parseLong(groupId));
                    result = CommonSuccessFail.SUCCESS.getValue();
                }
            }
        } catch (APIConnectionException e) {
            log.error("Connection error. Should retry later. ", e);
            return result;
        } catch (APIRequestException e) {
            log.error("Error response from JMessage server. Should review and fix it. ", e);
            return result;
        }catch (Exception e) {
            log.error("Error response from JPush server. Should review and fix it. ", e);
            return result;
        }
        return  result;
    }



    /**
     * 根据类型获取极光的key
     * @param type
     * @return
     */
    public static Map<String,Object> getJmessageKey(String type){
        Map<String,Object> map = new HashMap<String,Object>();
        try{
            if(type.equals(CommonShortType.HUANGZHE.getValue())){
                map.put("master",PropertiesUtil.getConfValue("masterSecretPatient"));
                map.put("key",PropertiesUtil.getConfValue("appKeyPatient"));
            }else if(type.equals(CommonShortType.YISHENG.getValue())){
                map.put("master",PropertiesUtil.getConfValue("masterSecrettDr"));
                map.put("key",PropertiesUtil.getConfValue("appKeyDr"));
            }else if(type.equals(CommonShortType.ZNKT.getValue())){
                map.put("master",PropertiesUtil.getConfValue("masterSecrettTv"));
                map.put("key",PropertiesUtil.getConfValue("appKeyTv"));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return map;
    }

}
