package com.ylz.packcommon.common;

public class ConfAttr
{
	// 属性标识

	String id;

	// 属性描述

	String desc;

	// 属性值

	String value;
	public ConfAttr(String id,String desc)
	{
		this.id=id;
		this.desc=desc;
	}
	public ConfAttr()
	{

	}
	public String getDesc()
	{
		return desc;
	}

	public void setDesc(String desc)
	{
		this.desc = desc;
	}

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public String getValue()
	{
		return value;
	}

	public void setValue(String value)
	{
		this.value = value;
	}

}
