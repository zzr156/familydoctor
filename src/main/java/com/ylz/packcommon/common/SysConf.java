package com.ylz.packcommon.common;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SysConf implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6195748910827427180L;
	private List<ConfAttr> items;
	String[] ids, descs, values;
    public void reSubItems()
    {
        if (ids != null) {
            for (int i = 0; i < ids.length; i++)
            {
            	ConfAttr c=new ConfAttr();
                if (ids[i].length() != 0) {
                    c.setId(ids[i]);
                }
                if (descs[i].length() != 0) {
                    c.setDesc(descs[i]);
                }
                if (values[i].length() != 0) {
                    c.setValue(values[i]);
                }

                if (null == getItems()) {
                    this.setItems(new ArrayList<ConfAttr>());
                }
                this.getItems().add(c);
            }
        }
    }
	
	public String[] getIds() {
		return ids;
	}

	public void setIds(String[] ids) {
		this.ids = ids;
	}

	public String[] getDescs() {
		return descs;
	}

	public void setDescs(String[] descs) {
		this.descs = descs;
	}

	public String[] getValues() {
		return values;
	}

	public void setValues(String[] values) {
		this.values = values;
	}

	public List<ConfAttr> getItems() {
		return items;
	}
	public void setItems(List<ConfAttr> items) {
		this.items = items;
	}

	public ConfAttr getItemValue(String id)
	{
		if(this.getItems()!=null)
		{
			for(ConfAttr it:this.getItems())
			{
				if(it.getId().equals(id))
				{	
					return it;
				}
			}
			
		}
		return null;
	}
	
}
