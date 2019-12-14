package com.ylz.packcommon.common.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class FileToStringArry {
	 /** 
     * 文件转化为字节数组 
     *  
     * @param file 
     * @return 
     */  
    public static byte[] getBytesFromFile(File file) {  
        byte[] ret = null;  
        try {  
            if (file == null) {  
                // log.error("helper:the file is null!");  
                return null;  
            }  
            FileInputStream in = new FileInputStream(file);  
            ByteArrayOutputStream out = new ByteArrayOutputStream(4096);  
            byte[] b = new byte[4096];  
            int n;  
            while ((n = in.read(b)) != -1) {  
                out.write(b, 0, n);  
            }  
            in.close();  
            out.close();  
            ret = out.toByteArray();  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
        return ret;  
    }  
}
