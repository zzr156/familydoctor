package com.ylz.packcommon.common;

import java.io.Serializable;

public class CommConditionVo implements Serializable
{
	private static final long serialVersionUID = 1L;

	private int pageStartNo = 1;

	private int pageSize = 10;

	private int itemCount = 0;
    public String sort;
    public  String order;
    public void restnew()
    {
        pageStartNo=1;
        pageSize=20;
    }

    @SuppressWarnings("unused")
	private int pageCount;

	String isPassDept = "false";
	

	private boolean needCount = true;// 如果为false时，则不进行算数量和分页
	

	public boolean isNeedCount()
	{
		return needCount;
	}

	public void setNeedCount(boolean needCount)
	{
		this.needCount = needCount;
	}

	public int getPageSize()
	{
		return pageSize;
	}

	public void setPageSize(int pageSize)
	{
		this.pageSize = pageSize;
	}

	public void setPageStartNo(int pageStartNo)
	{
		this.pageStartNo = pageStartNo;
	}

	public void setItemCount(int itemCount)
	{
		this.itemCount = itemCount;
	}

	public int getPageStartNo()
	{
//		if(this.getPageCount()<this.pageStartNo)
//			return 1;
		return pageStartNo;
	}

	public int getItemCount()
	{
		return itemCount;
	}

	public String getIsPassDept()
	{
		return isPassDept;
	}

    public int getPageCount()
    {
        return (this.getItemCount()+this.getPageSize()-1)/this.getPageSize();
    }


    public void setIsPassDept(String isPassDept)
	{
		this.isPassDept = isPassDept;
	}

    public void setPageCount(int pageCount)
    {
        this.pageCount = pageCount;
    }

    public String getDescs() {
        return String.valueOf(this.getPageStartNo()) + "/"
                + String.valueOf(this.getPageCount()) + "每页显示:"
                + String.valueOf(this.getPageSize()) + "条共有"
     + String.valueOf(this.getItemCount()) + "条";
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }
}
