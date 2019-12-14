package com.ylz.packcommon.common;

import java.io.File;

/**
 * <p>
 * 此类提供取得系统运行的各个环境
 *
 */
public class SystemUtil
{
	/**
	 * 取得web-inf/的路径
	 * @return
	 * @throws Exception
	 */
	public static String getWebInfPath()
	{
            String classPath = SystemUtil.class.getClassLoader().getResource("/").getPath();
            String rootPath = "";
            System.out.println(classPath);
            //windows下
            if ("\\".equals(File.separator)) {
                rootPath = classPath.substring(1,classPath.indexOf("/WEB-INF/classes"));
                rootPath = rootPath.replace("/", "\\");
                rootPath=rootPath+"\\WEB-INF\\ini";
            }
            //linux下
            if ("/".equals(File.separator)) {
                rootPath = classPath.substring(0,classPath.indexOf("/WEB-INF/classes"));
                rootPath = rootPath.replace("\\", "/");
                rootPath=rootPath+"/WEB-INF/ini";
            }
            System.out.println(rootPath);
            return rootPath;


	}

	public static void main(String[] args)
	{


		try
		{
                    SystemUtil s=new SystemUtil();
                    s.getWebInfPath();
		}
		catch (Exception e)
		{

			e.printStackTrace();
		}

	}

}
