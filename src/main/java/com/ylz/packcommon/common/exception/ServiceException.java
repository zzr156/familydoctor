package com.ylz.packcommon.common.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



/**
 * 业务逻辑层异常
 * @date 2012/04/07
 *
 */
public class ServiceException extends Exception{

	private static final long serialVersionUID = -71434260745841874L;
	
	private Logger log = null;

	@SuppressWarnings({ "rawtypes" })
	public ServiceException(Class clazz, String message) {
		super(message);
        System.out.println(message);
		log = LoggerFactory.getLogger(clazz);
		log.error(message);
	}
	
	@SuppressWarnings({ "rawtypes" })
	public ServiceException(Class clazz, Throwable throwable) {
		super(throwable);
        throwable.printStackTrace();
		log = LoggerFactory.getLogger(clazz);
		log.error(throwable.getMessage());
	}

}
