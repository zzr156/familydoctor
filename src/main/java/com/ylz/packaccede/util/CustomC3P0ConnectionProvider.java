package com.ylz.packaccede.util;

import org.springframework.beans.factory.FactoryBean;

import java.util.Properties;

/**
 * Created by hzk on 17-3-15.
 */
public class CustomC3P0ConnectionProvider implements FactoryBean{

	private Properties properties;  
    
    public Object getObject() throws Exception {  
        return getProperties();  
    }  
  
    public Class getObjectType() {  
        return java.util.Properties.class;  
    }  
  
    public boolean isSingleton() {  
        return true;  
    }  
  
    public Properties getProperties() {  
        return properties;  
    }  
  
    public void setProperties(Properties inProperties) {  
        this.properties = inProperties;  
        String originalUsername = properties.getProperty("user");  
        String originalPassword = properties.getProperty("password");  
        if (originalUsername != null){  
            String newUsername = SecUtil.decrypt(originalUsername);  
            properties.put("user", newUsername);  
        }  
        if (originalPassword != null){  
            String newPassword = SecUtil.decrypt(originalPassword);  
            properties.put("password", newPassword);  
        }  
    }  
   
    
    
    
}
