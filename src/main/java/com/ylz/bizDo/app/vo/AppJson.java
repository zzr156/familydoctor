package com.ylz.bizDo.app.vo;

import java.util.List;
import java.util.Map;

/**
 * Created by hzk on 2017/5/9.
 */
public class AppJson {
    private String ukey;
    private List rows;
    private String msgCode = "800";//800默认成功
    private String msg;
    private Object vo;
    private Object qvo;
    private Map<String, Object> map;
    private String entity;

    public String getUkey() {
        return ukey;
    }

    public void setUkey(String ukey) {
        this.ukey = ukey;
    }

    public List getRows() {
        return rows;
    }

    public void setRows(List rows) {
        this.rows = rows;
    }

    public String getMsgCode() {
        return msgCode;
    }

    public void setMsgCode(String msgCode) {
        this.msgCode = msgCode;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getVo() {
        return vo;
    }

    public void setVo(Object vo) {
        this.vo = vo;
    }

    public Object getQvo() {
        return qvo;
    }

    public void setQvo(Object qvo) {
        this.qvo = qvo;
    }

    public Map<String, Object> getMap() {
        return map;
    }

    public void setMap(Map<String, Object> map) {
        this.map = map;
    }

    public String getEntity() {
        return entity;
    }

    public void setEntity(String entity) {
        this.entity = entity;
    }

    //    public String getUkey() {
//        return ukey;
//    }
//
//    public void setUkey(String ukey) {
//        this.ukey = ukey;
//    }
//
//    public String getRows() {
//        return rows;
//    }
//
//    public void setRows(String rows) {
//        this.rows = rows;
//    }
//
//    public void setRows(List rows) {
//        this.rows = JsonUtil.toJson(rows);
//    }
//
//    public void setRows(List rows,String key) {
//        try {
//            this.rows = EncryptUtils.aesEncrypt(JsonUtil.toJson(rows),key);
////            this.rows = JsonUtil.toJson(rows);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    public String getMsgCode() {
//        return msgCode;
//    }
//
//    public void setMsgCode(String msgCode) {
//        this.msgCode = msgCode;
//    }
//
//    public String getMsg() {
//        return msg;
//    }
//
//    public void setMsg(String msg) {
//        this.msg = msg;
//    }
//
//    public String getVo() {
//        return vo;
//    }
//
//    public void setVo(String vo) {
//        this.vo = vo;
//    }
//
//    public void setVo(Object vo){
//        this.vo = JsonUtil.toJson(vo);
//    }
//
//    public void setVo(Object vo,String key) {
//        try {
//            this.vo = EncryptUtils.aesEncrypt(JsonUtil.toJson(vo),key);
////            this.vo = JsonUtil.toJson(vo);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    public String getQvo() {
//        return qvo;
//    }
//
//    public void setQvo(String qvo) {
//        this.qvo = qvo;
//    }
//
//    public String getMap() {
//        return map;
//    }
//
//    public void setMap(String map) {
//        this.map = map;
//    }
//
//    public void setMap(Map<String,Object> map){
//        this.map = JsonUtil.toJson(map);
//    }
//
//    public void setMap(Map<String, Object> map, String key) {
//        try {
//            this.map = EncryptUtils.aesEncrypt(JsonUtil.toJson(map),key);
////            this.map = JsonUtil.toJson(map);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
}
