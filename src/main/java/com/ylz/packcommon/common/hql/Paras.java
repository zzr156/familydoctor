package com.ylz.packcommon.common.hql;


/**
 * Description: hql条件变量
 */
@SuppressWarnings("serial")
public class Paras   implements java.io.Serializable
{
	// 数据绑定类型
	private HqlDataBoundingType boundType;

	private String pName;

	private Object pValue;

	private int typeNo;

	@SuppressWarnings("unused")
	private Paras()
	{
	}

	/**
	 * full constructor
	 *
	 * @param bType
	 * @param pName
	 * @param pValue
	 * @param typeNo
	 */
	protected Paras(HqlDataBoundingType bType, String pName, Object pValue, int typeNo)
	{
		this.boundType = bType;
		this.pName = pName;
		this.pValue = pValue;
		this.typeNo = typeNo;
	}
	protected Paras(Object pValue, int typeNo)
	{
		this.pValue = pValue;
		this.typeNo = typeNo;
	}
	/**
	 * mini constructor
	 *
	 * @param bType
	 */
	protected Paras(HqlDataBoundingType bType)
	{
		this.boundType = bType;
	}

	public String getPName()
	{
		return pName;
	}

	public Paras setPName(String pName)
	{
		this.pName = pName;
		return this;
	}

	public int getTypeNo()
	{
		return typeNo;
	}

	public Paras setTypeNo(int typeNo)
	{
		this.typeNo = typeNo;
		return this;
	}

	public HqlDataBoundingType getBoundType()
	{
		return boundType;
	}

	protected  Paras setBoundType(HqlDataBoundingType boundType)
	{
		this.boundType = boundType;
		return this;
	}

	public Object getPValue()
	{
		return pValue;
	}

	public Paras setPValue(Object value)
	{
		pValue = value;
		return this;
	}

}
