package com.ylz.packcommon.hyd.exception;

/**
 * Copyright:YLZinfo 2017 cop. ltd.
 * </p>
 * <p>
 * Company:易联众技术股份有限公司
 * Created by  ZJT on 2017/2/20.
 */
public class BusinessException  extends RuntimeException {

    private int errorCode;

    private Object messageObj;

    private Object entity;

    public BusinessException(Object message) {
        super(message!=null ? message.toString() : "");
        this.messageObj = message;
    }

    public BusinessException(Object message, int errorCode) {
        super(message!=null ? message.toString() : "");
        this.errorCode = errorCode;
        this.messageObj = message;
    }

    public BusinessException(Object message, Throwable cause) {
        super(message!=null ? message.toString() : "", cause);
        this.messageObj = message;
    }

//    public BusinessException(Object message, Object entity) {
//        super(message!=null ? message.toString() : "");
//        this.messageObj = message;
//        this.entity = entity;
//    }

    public BusinessException(Throwable cause) {
        super(cause);
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public Object getMessageObj() {
        return messageObj;
    }

    public void setMessageObj(Object messageObj) {
        this.messageObj = messageObj;
    }

    public Object getEntity() {
        return entity;
    }

    public void setEntity(Object entity) {
        this.entity = entity;
    }
}

