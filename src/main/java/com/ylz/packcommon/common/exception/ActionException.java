package com.ylz.packcommon.common.exception;


import com.ylz.packcommon.common.util.JsonList;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * Action层异常
 * @date 2012/04/07
 */
public class ActionException extends Exception {

	private static final long serialVersionUID = 105188045974566349L;
	private Logger log = null;

	@SuppressWarnings({ "rawtypes" })
	public ActionException(Class clazz, String message) {
		super(message);
        System.out.println(message);
		log = LoggerFactory.getLogger(clazz);
		log.error(message);
	}

	@SuppressWarnings({ "rawtypes" })
	public ActionException(Class clazz, Throwable throwable) {
		super(throwable);
        throwable.printStackTrace();
		log = LoggerFactory.getLogger(clazz);
		log.error(throwable.getMessage());
	}

	public ActionException(Class clazz,String act, Exception e) {
		e.printStackTrace();
		log = LoggerFactory.getLogger(clazz);
		if(StringUtils.isNotBlank(e.getMessage())) {
			log.error(e.getMessage(),act);
		}else {
			log.error(e.toString(),act);
		}
	}
	public ActionException(Class clazz,String act,String msgcode, Exception e) {
		e.printStackTrace();
		log = LoggerFactory.getLogger(clazz);
		if(StringUtils.isNotBlank(e.getMessage())) {
			log.error(e.getMessage(),act,msgcode);
		}else {
			log.error(e.toString(),act,msgcode);
		}
	}

	public ActionException(Class clazz,String act,String msgcode,String msg, Exception e) {
		e.printStackTrace();
		log = LoggerFactory.getLogger(clazz);
		if(StringUtils.isNotBlank(e.getMessage())) {
			log.error(e.getMessage(),act,msgcode,msg);
		}else {
			log.error(e.toString(),act,msgcode,msg);
		}
	}

	public ActionException(Class clazz, String act, JsonList json, Exception e) {
		e.printStackTrace();
		log = LoggerFactory.getLogger(clazz);
		if(StringUtils.isNotBlank(e.getMessage())) {
			json.setCode("900");
			json.setMsg(e.getMessage());
			log.error(e.getMessage(),act);
		}else {
			json.setCode("900");
			json.setMsg(e.toString());
			log.error(e.toString(),act);
		}
	}

}
