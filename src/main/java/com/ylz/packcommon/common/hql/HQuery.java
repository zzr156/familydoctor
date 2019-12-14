package com.ylz.packcommon.common.hql;

@SuppressWarnings("serial")
public class HQuery implements java.io.Serializable
{
	private HqlDataBoundingType boundType;

	private String queryString;

	private ParaList paralist;

	private String orderby;

	private String groupby;

	private int pageStartNo;

	private int pageSize;

	@SuppressWarnings("rawtypes")
	private Class poClass;

	@SuppressWarnings("unused")
	private HQuery()
	{

	}

	public HQuery(HqlDataBoundingType boundType)
	{
		this.boundType = boundType;
	}

	public HQuery(HqlDataBoundingType boundType, String hql)
	{
		this.boundType = boundType;
		this.queryString = hql;
	}

	public HQuery(String hql)
	{
		this.boundType = HqlDataBoundingType.PLACEHOLDER;
		this.queryString = hql;
	}

	public HQuery addHqlSplit(String hqlSplit)
	{
		queryString = (new StringBuffer(queryString)).append(hqlSplit).toString();
		return this;
	}

	public String getQueryString()
	{
		return queryString;
	}

	public HQuery setQueryString(String queryString)
	{

		this.queryString = queryString;
		return this;

	}

	public ParaList getParalist()
	{
		return paralist;
	}

	public void setParalist(ParaList paralist)
	{
		this.paralist = paralist;
	}

	public String getOrderby()
	{
		return orderby;
	}

	public HQuery setOrderby(String orderby)
	{
		this.orderby = orderby;
		return this;
	}

	public String getGroupby()
	{
		return groupby;
	}

	public HQuery setGroupby(String groupby)
	{
		this.groupby = groupby;
		return this;
	}

	public int getPageStartNo()
	{
		return pageStartNo;
	}

	public HQuery setPageStartNo(int pageStartNo)
	{
		this.pageStartNo = pageStartNo;
		return this;
	}

	public int getPageSize()
	{
		return pageSize;
	}

	public HQuery setPageSize(int pageSize)
	{
		this.pageSize = pageSize;
		return this;
	}

	@SuppressWarnings("rawtypes")
	public Class getPoClass()
	{
		return poClass;
	}

	@SuppressWarnings("rawtypes")
	public void setPoClass(Class poClass)
	{
		this.poClass = poClass;
		if (this.poClass != null)
		{
			String poName = poClass.getName();
			setQueryString("from " + poName + " as " + "a");
		}
	}

	@SuppressWarnings("rawtypes")
	public HQuery(Class poClass)
	{
		setPoClass(poClass);
	}

	@SuppressWarnings("rawtypes")
	public HQuery(Class poClass, int pageStartNo, int pageSize)
	{
		setPoClass(poClass);
		this.pageStartNo = pageStartNo;
		this.pageSize = pageSize;
	}

	public HqlDataBoundingType getBoundType()
	{
		return boundType;
	}

	public void setBoundType(HqlDataBoundingType boundType)
	{
		this.boundType = boundType;
	}

	public Paras getParas()
	{
		return new Paras(this.boundType);
	}

	@SuppressWarnings("unchecked")
	public HQuery addParas(Paras p, int index)
	{
		if (this.getParalist() == null)
		{
			this.setParalist(new ParaList());

		}
		this.paralist.add(index, p);
		return this;

	}

	@SuppressWarnings("unchecked")
	public HQuery addParas(Paras p)
	{
		if (this.getParalist() == null)
		{
			this.setParalist(new ParaList());

		}
		this.paralist.add(p);
		return this;

	}

    public HQuery addParas(String s,int typeNo)
    {
        if (this.getParalist() == null)
        {
            this.setParalist(new ParaList());

        }
        this.paralist.add(this.getParas().setPValue(s).setTypeNo(typeNo));
        return this;
    }

	public ParaList getNewParaList()
	{
		return new ParaList();
	}

}
