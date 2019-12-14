package com.ylz.packcommon.common.hql;

import java.util.ArrayList;

@SuppressWarnings({ "rawtypes", "serial" })
public class ParaList extends ArrayList implements java.io.Serializable
{
     @SuppressWarnings("unchecked")
	public void addParas(int index, Paras p)
     {
          super.add(index, p);
     }

     @SuppressWarnings("unchecked")
	public void addParas(Paras p)
     {
          super.add(p);
     }

     public Paras getParas(int index)
     {
          return (Paras)super.get(index);
     }

     public int indexofParas(Paras p)
     {
          return super.indexOf(p);
     }

     public void removeParas(int index)
     {
          super.remove(index);
     }
     protected ParaList()
     {

     }
}
