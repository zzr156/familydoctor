package com.ylz.packcommon.common.util;



import com.ylz.packcommon.reapal.agent.utils.Base64;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
/** *//**
 * <p>
 * BASE64编码解码工具包
 * </p>
 * <p>
 * 依赖javabase64-1.3.1.jar
 * </p>
 *
 * @author IceWee
 * @date 2012-5-19
 * @version 1.0
 */
public class Base64Utils {
    private static String SF_DF_BASE64= "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=";//自定义时解码使用
    /** *//**
     * 文件读取缓冲区大小
     */
    private static final int CACHE_SIZE = 1024;

    /** *//**
     * <p>
     * BASE64字符串解码为二进制数据
     * </p>
     *
     * @param base64
     * @return
     * @throws Exception
     */
    public static byte[] decode(String base64) throws Exception {
        return Base64.decode(base64.getBytes());
    }
    /**
     * 自定义的解码实现
     * @param base64
     * @return
     * @throws Exception
     */
    public static byte[] selfDecode1(String base64)throws Exception {
        int n,i,j,pad;
        byte [] dst;
        char [] src;
        int len = 0;
        pad=0;
        n = base64.length();
        src = new char [n];
        for(i=0;i<n;i++){//复制到src中
            src[i] = base64.charAt(i);
        }
        while(n>0&&src[n-1]=='=') {
            src[n-1]=0;
            pad++;
            n--;
        }

        for(i=0;i<n;i++)   {  //map base64 ASCII character to 6 bit value
            int iTt = SF_DF_BASE64.indexOf(src[i]);
            if(iTt<0) {
                break;
            }
            src[i] = (char)iTt;
        }
        dst = new byte[n*3/4+1];
        for(i=0,j=0;i<n;i+=4,j+=3) {
            dst[j] = (byte)((src[i]<<2) + ((src[i+1]&0x30)>>4));
            dst[j+1] = (byte)(((src[i+1]&0x0F)<<4) + ((src[i+2]&0x3C)>>2));
            dst[j+2] = (byte)(((src[i+2]&0x03)<<6) + src[i+3]);
            len+=3;
        }
        len-=pad;
        return dst;
    }
    /** *//**
     * <p>
     * 二进制数据编码为BASE64字符串
     * </p>
     *
     * @param bytes
     * @return
     * @throws Exception
     */
    public static String encode(byte[] bytes) throws Exception {
        return new String(Base64.encode(bytes));
    }

    /** *//**
     * <p>
     * 二进制数据编码为BASE64字符串
     * </p>
     *
     * @param buf
     * @return
     * @throws Exception
     */
    public static String selfEncode1(byte[] buf) throws Exception {
        int n,buflen,i,j;
        byte []dst = null;
        //CString buf = src;
        buflen=n=buf.length;
        dst = new byte[buflen/3*4+3];
        for(i=0,j=0;i<=buflen-3;i+=3,j+=4) {
            dst[j] = (byte)((buf[i]&0xFC)>>2);
            dst[j+1] = (byte)(((buf[i]&0x03)<<4) + ((buf[i+1]&0xF0)>>4));
            dst[j+2] = (byte)(((buf[i+1]&0x0F)<<2) + ((buf[i+2]&0xC0)>>6));
            dst[j+3] = (byte)(buf[i+2]&0x3F);
        }
        if(n%3==1) {
            dst[j] = (byte)((buf[i]&0xFC)>>2);
            dst[j+1] = (byte)(((buf[i]&0x03)<<4));
            dst[j+2]=64;
            dst[j+3]=64;
            j+=4;
        }
        else if(n%3==2) {
            dst[j] = (byte)((buf[i]&0xFC)>>2);
            dst[j+1] = (byte)(((buf[i]&0x03)<<4)+((buf[i+1]&0xF0)>>4));
            dst[j+2] = (byte)(((buf[i+1]&0x0F)<<2));
            dst[j+3]=64;
            j+=4;
        }
        for(i=0;i<j;i++) /* map 6 bit value to base64 ASCII character */ {
            dst[i] = (byte)SF_DF_BASE64.charAt((int)dst[i]);
        }
        dst[j]=0;
        return new String(dst);
    }

    /** *//**
     * <p>
     * 将文件编码为BASE64字符串
     * </p>
     * <p>
     * 大文件慎用，可能会导致内存溢出
     * </p>
     *
     * @param filePath 文件绝对路径
     * @return
     * @throws Exception
     */
    public static String encodeFile(String filePath) throws Exception {
        byte[] bytes = fileToByte(filePath);
        return encode(bytes);
    }

    /** *//**
     * <p>
     * BASE64字符串转回文件
     * </p>
     *
     * @param filePath 文件绝对路径
     * @param base64 编码字符串
     * @throws Exception
     */
    public static void decodeToFile(String filePath, String base64) throws Exception {
        byte[] bytes = decode(base64);
        byteArrayToFile(bytes, filePath);
    }

    /** *//**
     * <p>
     * 文件转换为二进制数组
     * </p>
     *
     * @param filePath 文件路径
     * @return
     * @throws Exception
     */
    public static byte[] fileToByte(String filePath) throws Exception {
        byte[] data = new byte[0];
        File file = new File(filePath);
        if (file.exists()) {
            FileInputStream in = new FileInputStream(file);
            ByteArrayOutputStream out = new ByteArrayOutputStream(2048);
            byte[] cache = new byte[CACHE_SIZE];
            int nRead = 0;
            while ((nRead = in.read(cache)) != -1) {
                out.write(cache, 0, nRead);
                out.flush();
            }
            out.close();
            in.close();
            data = out.toByteArray();
        }
        return data;
    }

    /** *//**
     * <p>
     * 二进制数据写文件
     * </p>
     *
     * @param bytes 二进制数据
     * @param filePath 文件生成目录
     */
    public static void byteArrayToFile(byte[] bytes, String filePath) throws Exception {
        InputStream in = new ByteArrayInputStream(bytes);
        File destFile = new File(filePath);
        if (!destFile.getParentFile().exists()) {
            destFile.getParentFile().mkdirs();
        }
        destFile.createNewFile();
        OutputStream out = new FileOutputStream(destFile);
        byte[] cache = new byte[CACHE_SIZE];
        int nRead = 0;
        while ((nRead = in.read(cache)) != -1) {
            out.write(cache, 0, nRead);
            out.flush();
        }
        out.close();
        in.close();
    }


}