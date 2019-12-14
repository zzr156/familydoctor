package com.ylz.packcommon.common.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;


public class FormatFunction
{

	static final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	public static String clobToStr(Object clob)
	{
		if(clob == null) {
            return null;
        }
		if (clob.getClass().getName().equals(String.class.getName())) {
            return (String)clob;
        }
//		if (
//			clob.getClass().getName().equals(oracle.sql.CLOB.class.getName())
//			|| clob.getClass().getName().equals(java.sql.Clob.class.getName())
//			|| clob.getClass().getName().equals(com.mysql.jdbc.Clob.class.getName())
//		)
		else
		{
			String reString = "";
			try
			{
				Reader is = null;
//				if(clob.getClass().getName().equals(oracle.sql.CLOB.class.getName()))
					is = ((java.sql.Clob) clob).getCharacterStream();// 得到流
//				if(clob.getClass().getName().equals(com.mysql.jdbc.Clob.class.getName()))
//					is = ((com.mysql.jdbc.Clob) clob).getCharacterStream();// 得到流
//				if(clob.getClass().getName().equals(java.sql.Clob.class.getName()))
//					is = ((java.sql.Clob) clob).getCharacterStream();// 得到流

				BufferedReader br = new BufferedReader(is);
				String s = br.readLine();
				StringBuffer sb = new StringBuffer();
				while (s != null)
				{// 执行循环将字符串全部取出付值给StringBuffer由StringBuffer转成STRING
					sb.append(s);
					s = br.readLine();
				}
				reString = sb.toString();
			}
			catch (SQLException e)
			{
				e.printStackTrace();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
			return reString;
		}
//		return null;
	}

	public static String getYMD_h_m_s(Calendar cal)
	{
		if (cal == null) {
            return null;
        }
		return formatter.format(cal.getTime());
	}
	public static String getYMD(Calendar cal)
	{
		if (cal == null) {
            return null;
        }
		return ExtendDate.getYMD(cal);
	}

}
