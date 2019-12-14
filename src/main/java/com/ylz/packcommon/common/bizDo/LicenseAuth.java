package com.ylz.packcommon.common.bizDo;

import com.ylz.packcommon.common.comEnum.ExtendDateType;
import com.ylz.packcommon.common.util.ExtendDateUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.*;
import java.net.InetAddress;
import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class LicenseAuth
{

    public interface DateConsist {
        public static final String WEB_URL1 = "http://www.bjtime.cn";// bjTime
        public static final String WEB_URL2 = "http://www.baidu.com";// 百度
        public static final String WEB_URL3 = "http://www.taobao.com";// 淘宝
        public static final String WEB_URL4 = "http://www.ntsc.ac.cn";// 中国科学院国家授时中心
        public static final String WEB_URL5 = "http://www.360.cn";// 360
        public static final String WEB_URL6 = "http://www.beijing-time.org";// beijing-time
        public static final String WEB_URL7 = "http://www.jd.com/";// 京东
    }

    public static boolean checkLicense()
    {
        try
        {
            Resource resource = new ClassPathResource("license.dat");
            if (!resource.exists()) {
                System.out.println("没有发现授权许可证，请将授权许可证放到类路径下：license.dat");
                return false;
            }
            LineNumberReader reader = new LineNumberReader(new FileReader(resource.getFile()));
            String str = reader.readLine();
            String localMAC = getLocalMAC();
            localMAC = "!@#!@AD" + localMAC + "asdgfa&&^";
            String shaDigest = CodeGenerator.getSHADigest(localMAC);
            if (shaDigest.equals(str)) {
                return true;
            }
            return false;
        }
        catch (IOException e)
        {
            System.out.println("授权验证失败。");
        }return false;
    }

    private static String getLocalMAC()
    {
        try
        {
            InetAddress addr = InetAddress.getLocalHost();
            String ip = addr.getHostAddress().toString();
            return getMAC(ip);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return "ERROR";
    }

    private static String getMAC(String ipAddress) {
        if ((ipAddress.equalsIgnoreCase("localhost")) || (ipAddress.equalsIgnoreCase("127.0.0.1"))) {
            return getLocalMAC();
        }
        String address = "ERROR";
        String os = System.getProperty("os.name");
        if ((os != null) && (os.startsWith("Windows"))) {
            try {
                String command = "cmd.exe /c nbtstat -a " + ipAddress;
                Process p = Runtime.getRuntime().exec(command);
                BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
                String line;
                while ((line = br.readLine()) != null) {
                    if (line.indexOf("MAC") > 0) {
                        int index = line.indexOf("=");
                        index += 2;
                        address = line.substring(index);
                    }
                }

                br.close();
                return address.trim();
            } catch (IOException e) {
            }
        }
        return address;
    }
    public static boolean isConnect(){
        boolean connect = false;
        Runtime runtime = Runtime.getRuntime();
        Process process;
        try {
            process = runtime.exec("ping " + "www.baidu.com");
            InputStream is = process.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            String line = null;
            StringBuffer sb = new StringBuffer();
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
//            System.out.println("返回值为:"+sb);
            is.close();
            isr.close();
            br.close();

            if (null != sb && !sb.toString().equals("")) {
                String logString = "";
                if (sb.toString().indexOf("TTL") > 0) {
                    // 网络畅通
                    connect = true;
                } else {
                    // 网络不畅通
                    connect = false;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return connect;
    }
    /**
     * 默认时间格式
     */
    private static final String DEFAULT_FORMAT = "yyyy-MM-dd HH:mm:ss";

    public static boolean isGq() {
        SimpleDateFormat sdf =   new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" );
        boolean connect = false;
        try {
            String  wwDate=getWebsiteDatetime(DateConsist.WEB_URL2, "yyyy-MM-dd HH:mm:ss");
            Date newDate= sdf.parse(wwDate);
            String dateNew = "2016-10-17";
            String dateNow = ExtendDateUtil.addDate(dateNew, ExtendDateType.DAYS.getValue(), 30)+" 23:59:59";
            Date dateNoo=sdf.parse(dateNow);
            if (newDate.getTime()>dateNoo.getTime()){
                connect=false;
            }else {
                connect=true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return connect;
    }


    /**
     * 根据URL和格式化类型获取时间
     *
     * @param webuUrl
     *            网络URL
     * @param format
     *            格式
     * @return
     */
    public static String getWebsiteDatetime(String webuUrl, String format) {
        try {
            // 判断当前是否传入URL
            if (!StringUtils.isEmpty(webuUrl)) {
                URL url = new URL(webuUrl);// 获取url对象
                URLConnection uc = url.openConnection();// 获取生成连接对象
                uc.connect();// 发出连接请求
                long ld = uc.getDate();// 读取网站日期时间
                Date date = new Date(ld);// 转化为时间对象
                SimpleDateFormat sdf = new SimpleDateFormat(
                        format != null ? format : DEFAULT_FORMAT, Locale.CHINA);// 输出北京时间
                return sdf.format(date);
            } else {
                System.out.println("URL Error!!!");
            }
        } catch (Exception e) {
            System.out.println("----无法连接网络进行校验----");
        }
        return null;

    }
}