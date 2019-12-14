package com.ylz.packcommon.common.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
public class Dbcon {
		
		public String dbcon(String testUrl,String info) {
			String result=null;
			URL targetUrl=null;
			try {
				targetUrl = new URL(testUrl);
				
				HttpURLConnection httpConnection = (HttpURLConnection) targetUrl.openConnection();
			
				httpConnection.setDoOutput(true);
			
				httpConnection.setRequestMethod("POST");
			
				httpConnection.setRequestProperty("Content-Type", "application/json");
			
				OutputStream outputStream = httpConnection.getOutputStream();
			
				outputStream.write(info.getBytes());
				outputStream.flush();
			
				String output="";
				if (httpConnection.getResponseCode() != 200) {
					result="ERROR: Failed : HTTP error code :"+httpConnection.getResponseCode();
					throw new RuntimeException("Failed : HTTP error code : "
				
					+ httpConnection.getResponseCode());
				
				}
			
				BufferedReader responseBuffer = new BufferedReader(new InputStreamReader(
			
				(httpConnection.getInputStream())));
			
			
//				System.out.println("连接数据库成功\n");
			
				result = responseBuffer.readLine();
				while ((output = responseBuffer.readLine()) != null) {
			
					System.out.println(output);
				}
				
				responseBuffer.close();
				httpConnection.disconnect();
		
			} catch (MalformedURLException e) {
		
				e.printStackTrace();
				result = "ERROR: "+e.toString();
				return result;
		
			} catch (IOException e) {
		
				e.printStackTrace();
				result = "ERROR: "+e.toString();
				return result;
		
			}
				
		
		if(result==null){
			result="";
		}	
		return result;
		}
}
