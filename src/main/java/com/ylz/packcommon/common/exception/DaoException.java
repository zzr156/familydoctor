package com.ylz.packcommon.common.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



/**
 * Dao层异常
 * @date 2012/04/07
 */
public class DaoException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8801718407102159973L;

	private Logger log = null;

	@SuppressWarnings({  "rawtypes" })
	public DaoException(Class clazz, String message) {
		super(message);
        System.out.println(message);
		log = LoggerFactory.getLogger(clazz);
		log.error(message);
	}
	
	@SuppressWarnings({  "rawtypes" })
	public DaoException(Class clazz, Throwable throwable) {
		super(throwable);
        throwable.printStackTrace();
		log = LoggerFactory.getLogger(clazz);
		log.error(throwable.getMessage());
	}
}
