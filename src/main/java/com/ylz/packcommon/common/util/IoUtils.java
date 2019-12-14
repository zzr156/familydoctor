package com.ylz.packcommon.common.util;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.ylz.packcommon.common.comEnum.CommonShortType;
import com.ylz.packcommon.common.comEnum.ExtendDateType;
import com.ylz.packcommon.ctyunOos.Common;
import org.springframework.stereotype.Component;

import java.io.*;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;


@Component("ioUtils")
public class IoUtils implements Runnable
{

	private AmazonS3Client client;
	private String bucketName = "appfamilydoctor";

	public IoUtils() {
		client = Common.getClient();
	}

	public String getFileExtendName(String filepath)
	{
		int i = filepath.lastIndexOf(".");
		return filepath.substring(i + 1);
	}

	public String getFileName(String filePath1)
	{
		try
		{
			String filePath = filePath1;
			filePath = filePath.replace('?', 'I');
			filePath = filePath.replace('*', 'X');
			filePath = filePath.replace('�', 'O');
			String _strName = (new File(filePath)).getName();
			return _strName;
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			return null;
		}
	}

	/**
	 * 删除附件中的文件
	 * @param filePath String 文件路径
	 * @throws Exception
	 */
	public void DeleteFile(String filePath) throws Exception
	{
		java.io.File file = new java.io.File(filePath);
		file.delete();
	}

	/**
	 * 取得文件的文件输出流
	 * @param filePath String 文件路径
	 * @throws Exception
	 * @return OutputStream 输出流
	 */
	public java.io.InputStream getFileStream(String filePath) throws Exception
	{
		java.io.FileInputStream fos = new java.io.FileInputStream(filePath);
		return fos;
	}

	@SuppressWarnings("deprecation")
	public String UpLoadFile(File formFile, String filePath, String fileName) throws Exception
	{
		String path = null;
		try
		{
			path = this.CreatePath(filePath);
			int pos = fileName.lastIndexOf(".");
			String hz = fileName.substring(pos);
			path = path+"//" + getRandomFileName()+hz;
			InputStream stream = new FileInputStream(formFile) ; // 把文件读入
			OutputStream bos = new FileOutputStream(path); // 建立一个上传文件的输出流
			org.apache.commons.io.output.ByteArrayOutputStream bao = new org.apache.commons.io.output.ByteArrayOutputStream(102400);
			org.apache.commons.io.CopyUtils.copy(stream, bos);
			bao.writeTo(bos);
			bos.close();
			stream.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw e;
		}
		return path;
	}
	public String UpLoadFile2(File formFile, String filePath, String fileName,String fileNames) throws Exception
	{
		String path = null;
		try
		{
			path = this.CreatePath(filePath);
			int pos = fileName.lastIndexOf(".");
			String hz = fileName.substring(pos);
			path = path+ fileNames+hz;
			InputStream stream = new FileInputStream(formFile) ; // 把文件读入
			OutputStream bos = new FileOutputStream(path); // 建立一个上传文件的输出流
			org.apache.commons.io.output.ByteArrayOutputStream bao = new org.apache.commons.io.output.ByteArrayOutputStream(102400);
			org.apache.commons.io.CopyUtils.copy(stream, bos);
			bao.writeTo(bos);
			bos.close();
			stream.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw e;
		}
		return path;
	}

	public String pathUrl(String drFilePath, String filePath, String hz) throws Exception
	{
		String path = null;
		try
		{
//			path = this.CreatePath(drFilePath,filePath);
			java.io.File file = new java.io.File(drFilePath+filePath);
			file.mkdirs();
			path = filePath+"/" + hz;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw e;
		}
		return path;
	}
	@SuppressWarnings("deprecation")
	public String UpLoadFile(File formFile, String filePath) throws Exception
	{
		String path = null;
		try
		{
			path = this.CreatePath(filePath);
			path = path + getRandomFileName();
			InputStream stream =new FileInputStream(formFile) ; // 把文件读入
			OutputStream bos = new FileOutputStream(filePath + formFile.getName()); // 建立一个上传文件的输出流
			org.apache.commons.io.output.ByteArrayOutputStream bao = new org.apache.commons.io.output.ByteArrayOutputStream(102400);
			org.apache.commons.io.CopyUtils.copy(stream, bos);
			bao.writeTo(bos);
			bos.close();
			stream.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw e;
		}
		return path;
	}

	/**
	 * 建立目录(如果存在则不建立 __可以同时建子目录 )
	 * @param path String 目录名
	 * @throws Exception
	 * @return String 目录名
	 */
	public String CreatePath(String path) throws Exception{
		Calendar cal = Calendar.getInstance();
		path =  path +"//" +new SimpleDateFormat("yyyy/MM/dd").format(cal.getTime())+"//";
		java.io.File file = new java.io.File(path);
		file.mkdirs();
		return path;
	}

	public String CreatePath(String fistPath,String path) throws Exception{
		Calendar cal = Calendar.getInstance();
		path =  path +"//" +new SimpleDateFormat("yyyy/MM/dd").format(cal.getTime())+"//";
		java.io.File file = new java.io.File(fistPath+path);
		file.mkdirs();
		return path;
	}


	public  String getRandomFileName() {

        SimpleDateFormat simpleDateFormat;

        simpleDateFormat = new SimpleDateFormat("yyyyMMddhhmmss");

        Calendar cal = Calendar.getInstance();

        String str = simpleDateFormat.format(cal.getTime());

        Random random = new Random();

        int rannum = (int) (random.nextDouble() * (99999 - 10000 + 1)) + 10000;// 获取5位随机数

        return rannum + str;// 当前时间
    }

	/**
	 * 上传文件
	 * @param base64String
	 * @param type
	 * @return
	 * @throws Exception
	 */
    public Map<String,Object> getCtyunOosSample(String base64String, String type) throws Exception {
		Map<String,Object> map = new HashMap<String,Object>();
		String upLoadType = PropertiesUtil.getConfValue("uploadType");//1上传本地,2调用oos服务
		String objectName = null;
		String name = this.getRandomFileName()+Md5Util.MD5(base64String);
		if(upLoadType.equals("1")){
			name = name+".jpg";
			if(CommonShortType.HUANGZHE.getValue().equals(type)){
				objectName = PropertiesUtil.getConfValue("filePictureHz")+new SimpleDateFormat("yyyy/MM/dd").format(Calendar.getInstance().getTime());
			}else if(CommonShortType.YISHENG.getValue().equals(type)){
				objectName = PropertiesUtil.getConfValue("filePictureYz")+new SimpleDateFormat("yyyy/MM/dd").format(Calendar.getInstance().getTime());
			}
			String path = this.pathUrl(PropertiesUtil.getConfValue("filePicture"),objectName,name);
			FileUtils.decoderBase64File(base64String,PropertiesUtil.getConfValue("filePicture")+path);
			map.put("objectUrl",PropertiesUtil.getConfValue("filePictureUrl")+path);
			objectName = objectName+name;
		}else if(upLoadType.equals("2")){
			File file = FileUtils.Base64File(base64String);
			if(CommonShortType.HUANGZHE.getValue().equals(type)){
				objectName = PropertiesUtil.getConfValue("filePictureHz")+new SimpleDateFormat("yyyy/MM/dd").format(Calendar.getInstance().getTime())+name+".jpg";
			}else if(CommonShortType.YISHENG.getValue().equals(type)){
				objectName = PropertiesUtil.getConfValue("filePictureYz")+new SimpleDateFormat("yyyy/MM/dd").format(Calendar.getInstance().getTime())+name+".jpg";
			}
			//进行上传文件拼装
			PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, objectName, file);
			//进行上传
			client.putObject(putObjectRequest);
			//获取文件
//		GetObjectRequest getObjectRequest = new GetObjectRequest(bucketName, objectName);
//		S3Object object = client.getObject(getObjectRequest);
			String strDate = ExtendDateUtil.addDate(ExtendDate.getYMD(Calendar.getInstance()), ExtendDateType.YEARS.getValue(),100);
			Date date = ExtendDate.getCalendar(strDate).getTime();
			URL urlObject = client.generatePresignedUrl(bucketName,objectName,date);
			map.put("objectUrl",urlObject.toString());
		}
		map.put("objectName",objectName);
		return map;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub

	}

	public static void main(String[] args) {
		try{
			SimpleDateFormat simpleDateFormat;
			simpleDateFormat = new SimpleDateFormat("yyyyMMddhhmmss");
			Calendar cal = Calendar.getInstance();
			String str = simpleDateFormat.format(cal.getTime());
			Random random = new Random();
			int rannum = (int) (random.nextDouble() * (99999 - 10000 + 1)) + 10000;// 获取5位随机数
			IoUtils c = new IoUtils();
			String objectName = PropertiesUtil.getConfValue("filePictureHz")+new SimpleDateFormat("yyyy/MM/dd").format(Calendar.getInstance().getTime())+rannum+".jpg";
			System.out.println(objectName);
		}catch (Exception e){
			e.printStackTrace();
		}
	}


}
