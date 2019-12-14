package com.ylz.packcommon.common.util;

import com.ylz.bizDo.cd.po.CdUserXxx;
import com.ylz.packcommon.common.Constrats;
import com.ylz.packcommon.common.comEnum.ExtendDateType;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Random;

public class CaKeyUtil {
	
	private static final String p12 = ".p12";

	/**
	 * 创建Ca证书
	 * @return
	 */
	public static CdUserXxx CaCreateKey(String account,String userId){
		CdUserXxx x = null;
		String path = null;
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		try {
			String yh = CaKeyUtil.getRandomFileName(account);//用户
			String sjm = CaKeyUtil.getRandomPassWord();//密码
			path = CaKeyUtil.CreatePath(PropertiesUtil.getConfValue("keyfile"))+yh;
			//生成客户端证书 
			String one = PropertiesUtil.getConfValue("keyjkdfile")+String.format(Constrats.KEY_BUZOU_ONE, yh,yh,sjm,path,sjm,PropertiesUtil.getConfValue("keyYxDay"));
			//由于是双向SSL认证，服务器必须要信任客户端证书，因此，必须把客户端证书添加为服务器的信任认证。由于不能直接将PKCS12格式的证书库导入，我们必须先把客户端证书导出为一个单独的CER文件，使用如下命令，先把客户端证书导出为一个单独的cer文件： 。 
			String two = PropertiesUtil.getConfValue("keyjkdfile")+String.format(Constrats.KEY_BUZOU_TWO, yh,path,path,sjm);
			//添加客户端证书到服务器中（将已签名数字证书导入密钥库） 
			String three = Constrats.KEY_BUZOU_Y+PropertiesUtil.getConfValue("keyjkdfile")+String.format(Constrats.KEY_BUZOU_THREE, yh,path,PropertiesUtil.getConfValue("keykhdfile")+"server");
			//生成bat内容
			String bat = CaKeyUtil.getBatFile(one, two, three, path);
			//执行cmd命令
			Runtime.getRuntime().exec(bat).waitFor();
			path = path+CaKeyUtil.p12;
			String dqsj = ExtendDateUtil.addDate(format.format(Calendar.getInstance().getTime()), ExtendDateType.DAYS.getValue(), Integer.parseInt(PropertiesUtil.getConfValue("keyYxDay")));
			Calendar cal = Calendar.getInstance();
			cal.setTime(format.parse(dqsj));
			x = new CdUserXxx(null,userId,account,sjm,path,Calendar.getInstance(),cal);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return x;
	}
	
	public static void main(String[] args) {
//		System.out.println(CaKeyUtil.CaCreateKey("admin"));
	}
	
	
	/**
	 * 生成bat文件
	 * @param one
	 * @param two
	 * @param three
	 * @return
	 * @throws IOException 
	 */
	public static String getBatFile(String one,String two,String three,String path) throws IOException{
		String result = path+".bat";
		//bat内容
		String data = "@echo off \n";
		data += one+"\n"+two+"\n"+three;
		data.replace("\'", "\""); 
		File txt = new File(result);
		txt.createNewFile();//创建文件
		byte bytes[] = new byte[512]; bytes = data.getBytes();//写文件
		int b = data.length(); 
		FileOutputStream fos = new FileOutputStream(txt);
		fos.write(bytes, 0, b);
		fos.close();
		return result;
	}
	/**
	 * 创建文件夹路径
	 * @param path
	 * @return
	 * @throws Exception
	 */
	public static String CreatePath(String path) throws Exception{
		Calendar cal = Calendar.getInstance();
		path =  path +new SimpleDateFormat("yyyy\\MM\\dd").format(cal.getTime())+"\\";
		java.io.File file = new java.io.File(path);
		file.mkdirs();
		return path;
	}
	
	
	/**
	 * 用户名随机
	 * @param account
	 * @return
	 */
	public static String getRandomFileName(String account) {

        SimpleDateFormat simpleDateFormat;

        simpleDateFormat = new SimpleDateFormat("yyyyMMdd");

        Calendar cal = Calendar.getInstance();

        String str = simpleDateFormat.format(cal.getTime());

        Random random = new Random();

        int rannum = (int) (random.nextDouble() * (99999 - 10000 + 1)) + 10000;// 获取5位随机数

        return account + rannum + str;// 当前时间
    }
	
	/**
	 * 随机密码
	 * @return
	 */
	public static String getRandomPassWord() {

        SimpleDateFormat simpleDateFormat;

        simpleDateFormat = new SimpleDateFormat("yyyyMMdhhmmss");

        Calendar cal = Calendar.getInstance();

        String str = simpleDateFormat.format(cal.getTime());

        Random random = new Random();

        int rannum = (int) (random.nextDouble() * (99999 - 10000 + 1)) + 10000;// 获取5位随机数

        return rannum + str;// 当前时间
    }
}
