package com.ylz.packcommon.common.util;

import com.ylz.easemob.server.example.api.impl.EasemobIMUsers;
import io.swagger.client.model.RegisterUsers;

/**
 * Created by asus on 2017/7/4.
 */
public class HxLoginUtil {
    /**
     *
     * @param userId
     */
    public static void registeredEasemob(String userId){
        Object result = "";
        try{
            SericuryUtil p = new SericuryUtil();
            userId = p.encrypt(userId);
            RegisterUsers users = new RegisterUsers();
            io.swagger.client.model.User payload = new io.swagger.client.model.User()
                    .username(userId)
                    .password(userId);
            users.add(payload);
            EasemobIMUsers ease = new EasemobIMUsers();
            Object s = ease.getBlackList(userId);
            if(s == null){
                result = ease.createNewIMUserSingle(users);
                System.out.println(result);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    public static String registeredEasemobTemp(String userId){
        String jg = null;
        try{
            Object result = "";
            SericuryUtil p = new SericuryUtil();
            userId = p.encrypt(userId);
            RegisterUsers users = new RegisterUsers();
            io.swagger.client.model.User payload = new io.swagger.client.model.User()
                    .username(userId)
                    .password(userId);
            users.add(payload);
            EasemobIMUsers ease = new EasemobIMUsers();
            result = ease.createNewIMUserSingle(users);
            if(result != null){
                System.out.println(result);
                jg = result.toString();
            }

        }catch (Exception e){
            e.printStackTrace();
        }

        return jg;
    }
    /**
     * huan环信添加好用
     * @param userId
     * @param fridenId
     */
    public static void addFridenSignl(String userId,String fridenId){
        try{
            SericuryUtil p = new SericuryUtil();
            userId = p.encrypt(userId);
            fridenId = p.encrypt(fridenId);
            EasemobIMUsers ease = new EasemobIMUsers();
            Object result =  ease.addFriendSingle(userId,fridenId);
//            Object result = ease.addFriendSingle()
//            Object result = ease.createNewIMUserSingle(users);
            System.out.println(result);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void delEasemob(String userId){
        try{
            SericuryUtil p = new SericuryUtil();
            userId = p.encrypt(userId);
            EasemobIMUsers ease = new EasemobIMUsers();
            Object result =  ease.deleteIMUserByUserName(userId);
            System.out.println(result);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
//        hxLoginUtil.registeredEasemob("b5f857c7-8d62-43e8-bbd7-f2f4028a5982","阮美庆");
        HxLoginUtil.registeredEasemob("f4539ae0-2f97-4900-9b04-38d10037148c");

    }
}
