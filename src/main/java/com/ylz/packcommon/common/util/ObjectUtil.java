package com.ylz.packcommon.common.util;


import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;


/**
 * 
 * @author robin
 *
 */

public class ObjectUtil
{
     /**
      * 对类的深度clone
      * @param o Object
      * @return Object
      */
     public static Object deepClone(Object o) throws IOException,ClassNotFoundException
     {
     	
     	ByteArrayOutputStream bo=new ByteArrayOutputStream();
     	ObjectOutputStream oo=new ObjectOutputStream(bo);
     	oo.writeObject(o);
     	ByteArrayInputStream bi=new ByteArrayInputStream(bo.toByteArray());
     	ObjectInputStream oi=new ObjectInputStream(bi);

     	return oi.readObject();
         
     }
     public static List listClone(List l) throws Exception
     { 
     	if(l==null || l.size()==0) {
            return null;
        }
     	Vector v=new Vector(0);
     	v.addAll(l);
     	v=(Vector)v.clone();
     	List _l=new ArrayList();
     	_l.addAll(v);
     	return v;
     
     }
}
