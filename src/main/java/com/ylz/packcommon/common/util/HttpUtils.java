package com.ylz.packcommon.common.util;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class HttpUtils {

	private HttpUtils() {
	}
	/**
	 * 
	 * @param url
	 * @param httpMethod
	 * @param multiValueMap
	 * @param headerRequest
	 * @return
	 */
	public static ResponseEntity<String> getHttp(final String url, HttpMethod httpMethod,
		MultiValueMap<String, String> multiValueMap, HttpHeaders headerRequest) {
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<String> responseEntity = null;
		// 进行头部参数和一般参数集合
		HttpEntity<MultiValueMap<String, String>> bodyAndHeader = new HttpEntity<MultiValueMap<String, String>>(
				multiValueMap, headerRequest);
		responseEntity = restTemplate.exchange(url, httpMethod, bodyAndHeader, String.class);
		return responseEntity;
	}

    public static String getPostByXml(String urlStr,String xml){
        String line="";
        try {
            URL url=new URL(urlStr);
            URLConnection con=url.openConnection();
            con.setDoOutput(true);
            OutputStreamWriter out=new OutputStreamWriter(con.getOutputStream());
            System.out.println("Exedata satart\n"+xml+"\nExe end");
            out.write(new String(xml.getBytes("ISO-8859-1")));
            out.flush();
            out.close();
            BufferedReader br=new BufferedReader(new InputStreamReader(con.getInputStream()));
            for(line=br.readLine();line!=null;line=br.readLine()) {
                System.out.println(line);
            }

        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return line;
    }
}
