package com.ylz.bizDo.dd.dao;


import com.ylz.bizDo.dd.po.DdJlqkQch;

public interface DdJlqkQchDao  {

	public DdJlqkQch findByYear(int year, int month, int day) throws Exception;

	public DdJlqkQch findByYear(int year,String type) throws Exception;
}
