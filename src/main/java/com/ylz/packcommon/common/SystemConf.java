package com.ylz.packcommon.common;

import com.thoughtworks.xstream.XStream;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;


public class SystemConf
{
	// 配置文件名


	private final static String CONF_FILE = "systemConf.xml";


	/**
	 * 取systemConf.xml文件， 如果文件不存在的话，此function会新建一个在web-inf/下， 并初始化XML
	 *
	 * @return File
	 */
	private static File getConfFile() throws RuntimeException
	{
		try
		{
			File f = new File(SystemUtil.getWebInfPath() + "/" + CONF_FILE);
			if (!f.exists())
			{
				File fdir = new File(SystemUtil.getWebInfPath());
				fdir.mkdirs();
				f.createNewFile();
				SysConf sysconf=new SysConf();
				List<ConfAttr> cs=new ArrayList<ConfAttr>();
				for (ConfAttrActive c : ConfAttrActive.values())
				{
					cs.add(new ConfAttr(c.getId(), c.getDesc()));

				}
				sysconf.setItems(cs);
				XStream xstream = new XStream();
				xstream.useAttributeFor(String.class);
				xstream.useAttributeFor(int.class);
				xstream.useAttributeFor(boolean.class);
				OutputStreamWriter out = new OutputStreamWriter(new FileOutputStream(f), "utf-8");
				xstream.toXML(sysconf, out);
				
			}
			return f;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			
		}
		return null;
	}


	public static SysConf getSysConfXml() throws RuntimeException{
		// TODO Auto-generated method stub
		try {
			XStream xstream = new XStream();
			xstream.useAttributeFor(String.class);
			xstream.useAttributeFor(int.class);
			xstream.useAttributeFor(boolean.class);
			File f = new File(SystemUtil.getWebInfPath() + "/" + CONF_FILE);
			String xml=FileUtils.readFileToString(f,"UTF-8");
			SysConf sysconf = (SysConf) xstream.fromXML(xml);
			return sysconf;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static void saveSysConfXml(SysConf sysconf) throws RuntimeException{
		// TODO Auto-generated method stub
		try {
			sysconf.reSubItems();
			XStream xstream = new XStream();
			xstream.useAttributeFor(String.class);
			xstream.useAttributeFor(int.class);
			xstream.useAttributeFor(boolean.class);
			OutputStreamWriter out = new OutputStreamWriter(new FileOutputStream(getConfFile()), "utf-8");
			xstream.toXML(sysconf, out);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(
					e.getMessage() == null ? "saveSysConfXml error."
							: e.getMessage());
		}
	}
	
	//根据id取属性

	public static ConfAttr getAttrById(ConfAttrActive confActive)throws RuntimeException
	{
		if (confActive == null) {
            return null;
        }
		List<ConfAttr> attrs = getSysConfXml().getItems();
		if (attrs != null && attrs.size() != 0)
		{
			for (ConfAttr attr : attrs)
			{
				if (attr.getId().equals(confActive.getId())) {
                    return attr;
                }

			}
		}
		return null;
	}
	
	public static ConfAttr getHasAttr(List<ConfAttr> _list,String id)throws RuntimeException
	{
		if (_list == null) {
            return null;
        }
		for (ConfAttr attr2 : _list)
		{
			if (attr2.getId().equals(id)) {
                return attr2;
            }
		}
		return null;
	}
	/**
	 * 重建配置文件
	 *
	 * @throws Exception
	 */
	public static void reBuildConf() throws Exception
	{
		SysConf sysconf=new SysConf();
		List<ConfAttr> cs=new ArrayList<ConfAttr>();
		for (ConfAttrActive c : ConfAttrActive.values())
		{
			ConfAttr co=getSysConfXml().getItemValue(c.id);
			if(co!=null)
			{
				cs.add(co);
			}
			else
			{
				cs.add(new ConfAttr(c.getId(), c.getDesc()));
			}

		}
		sysconf.setItems(cs);
		XStream xstream = new XStream();
		xstream.useAttributeFor(String.class);
		xstream.useAttributeFor(int.class);
		xstream.useAttributeFor(boolean.class);
		File f = new File(SystemUtil.getWebInfPath() + "/" + CONF_FILE);
		OutputStreamWriter out = new OutputStreamWriter(new FileOutputStream(f), "utf-8");
		xstream.toXML(sysconf, out);
	}
	
	

}
