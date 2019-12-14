import com.ylz.packcommon.common.util.AESR;
import com.ylz.packcommon.common.util.Base64Utils;
import com.ylz.packcommon.common.util.ExtendDate;
import com.ylz.packcommon.common.util.JsonUtil;
import com.ylz.webservices.server.PtFamilyService;
import com.ylz.webservices.server.impl.PtFamilyServiceImpl;
import javax.xml.namespace.QName;
import org.apache.cxf.binding.soap.saaj.SAAJOutInterceptor;

import org.apache.cxf.endpoint.Client;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;

import java.io.Reader;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Administrator on 2018/3/19.
 */
public class ServerTest {
    private static final String ylzCsKey = "ylzdocortest@)!*)!";
    public static void main(String [] args) throws Exception{

        String result = "{\"patientIdno\":\"350623200001210549\"" +
                "}";
        byte[] encryptResult = AESR.encrypt(result, ylzCsKey, 16);
        String jmstr = Base64Utils.encode(encryptResult);
        System.out.println(Base64Utils.encode(encryptResult));

        byte[] decryptResult = AESR.decrypt(Base64Utils.decode("vMpuJNlqktRMFRq/RYPbEN1RU6oLuqFhzAkD6PH2GtRlilj/p8aeEvAg7mOpSG9XWxdnF4nZIaUr1x/LtapyxoTzGIM7rJryvE4q0TmTe+eCx6bERUSjY7L1pjg22FUJp+o/3zmIxnPZraKgK397vwB6550CNuGgOXo8rcQ4zK1XseZxbQ4pesmlaDeQ64zqbEa7QLpS7Vs/M3tYFtaidvFhb2V4IdhmmQORTyr0LjE5vK2Buio4ik3mDdUpaSIoPgpsM6Ic8gOIjjyx7fx1spinf6M/QFnjcAj5FJ3iIrVXB5LOWuHY9NJfH/MqV06O7WvAS4BO5Xc5hITR074n0Kv9bsuD26mtrfTu8GTOlNMmqSBmOsKFzruaTaDQVRpQmM+vfYOoBgKjdR2839S8IEQ5Rmaloi26iBNnw8bzklgHY1RbQsZS6fAzdIr6ZsnXADbmUb3DL3eFmPysOSc2cg=="),"ylzdocortest@)!*)!",16);
        String strJson = new String(decryptResult,"UTF-8");
        System.out.println(strJson);

//        Long time = 1553417415081;
        long longTime = Long.parseLong("1553417415081");
        Date date = new Date(longTime );
        System.out.println(ExtendDate.getYMD_h_m_s(date));
        String rule = "242884817";
        String token = "835225f871d1ad25";
        StringBuilder sb =new StringBuilder(token);
        System.out.println(token);
        int v = 1;
        for(int i=0;i<rule.length();i++){
            String jg = token.substring(token.length()-v,token.length()-v+1);
            sb.insert(Integer.parseInt(rule.substring(i,i+1)),jg);
            v++;
        }
        System.out.println(sb);
        JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
        String json = "{\"strJson\":\"d5429fa03ef691aaef4dca21e724fa3d01ac2e92170aa56fac707d7180ab5c18c2e84585dd8e3c9efbebab268a749f3a\"}";
        factory.getOutInterceptors().add(new SAAJOutInterceptor());
        factory.setServiceClass(PtFamilyService.class);
        factory.setAddress("http://localhost:8080/f/webService/PtFamilyService");
        PtFamilyService s= (PtFamilyService) factory.create();
        System.out.println(s.getSignInfoByCard(json));


//        JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
//        Client client = dcf.createClient("http://localhost:8080/f/webService/PtFamilyService?wsdl");
////    client.getOutInterceptors().add(new WSS4JOutInterceptor(outProps));
//        String namespace = "http://server.webservices.ylz.com/";
//        Object[] objects=client.invoke(new QName(namespace, "sayHello"), "1212");
//        System.out.println("Server said: " + objects[0].toString());

    }
}
