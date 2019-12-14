package com.ylz.packcommon.common.util;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.*;

import java.io.IOException;
import java.util.List;
/**
 * 
 * @author zk
 *
 */
public final class JacksonUtils
{
	public static List<?> getJavaCollection(JavaType t, String jsstr) throws Exception
	{
		List<?> objs = mapper.readValue(jsstr, t);
		return objs;
	}
	
	public static Object getObject(String jstr, Class clsName)
	{
		try
		{
			if (jstr == null) {
                return null;
            }
			return mapper.readValue(jstr, clsName);
		}
		catch (JsonParseException e)
		{
			e.printStackTrace();
		}
		catch (JsonMappingException e)
		{
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		return null;
	}
	
	public static final ObjectMapper mapper = new ObjectMapper();
	static
	{
		mapper.setSerializationInclusion(Include.NON_NULL);
		
		mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, true);
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	}
	
	public static JavaType getCollectionType(Class<?> collectionClass, Class<?>... elementClasses)
	{
		return mapper.getTypeFactory().constructParametricType(collectionClass, elementClasses);
	}
	
	public static String objToStr(Object j)
	{
		try
		{
			mapper.setSerializationInclusion(Include.NON_NULL);
			return mapper.writeValueAsString(j);
			
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}
	
}
