package com.ylz.packcommon.common.util;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor; 
/**
 * 实现两个实体类属性之间的复制 
 * @author dws
 *
 */
public class CopyUtils {

	 public static void Copy(Object source, Object dest) throws Exception {  
	        // 获取属性  
	        BeanInfo sourceBean = Introspector.getBeanInfo(source.getClass(),  
	                Object.class);
	        PropertyDescriptor[] sourceProperty = sourceBean  
	                .getPropertyDescriptors();  
	  
	        BeanInfo destBean = Introspector.getBeanInfo(dest.getClass(),  
	                Object.class);
	        PropertyDescriptor[] destProperty = destBean.getPropertyDescriptors();  
	  
	        try {  
	            for (int i = 0; i < sourceProperty.length; i++) {  
	  
	                for (int j = 0; j < destProperty.length; j++) {  

	                	if(sourceProperty[i].getName().equals("strPatientGender")){

						}else {
							if (sourceProperty[i].getName().equals(destProperty[j].getName())) {
								// 调用source的getter方法和dest的setter方法
								if(sourceProperty[i].getReadMethod().invoke(source) != null && sourceProperty[i].getReadMethod().invoke(source) !=""){
									destProperty[j].getWriteMethod().invoke(dest,sourceProperty[i].getReadMethod().invoke(source));
								}
								break;
							}
						}
	                }
	            }  
	        } catch (Exception e) {  
	            throw new Exception("属性复制失败:" + e.getMessage());  
	        }  
	    }  
}
