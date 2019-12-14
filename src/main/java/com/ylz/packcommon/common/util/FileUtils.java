package com.ylz.packcommon.common.util;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.*;

/**
 * Created by admin on 2017/5/11.
 */
public class FileUtils {


    /**
     * 将文件转成base64 字符串
     * @return  *
     * @throws Exception
     */
    public static String encodeBase64File(String path) throws Exception {
        File file = new File(path);
        FileInputStream inputFile = new FileInputStream(file);
        byte[] buffer = new byte[(int) file.length()];
        inputFile.read(buffer);
        inputFile.close();
        return new BASE64Encoder().encode(buffer);

    }

    public static String encodeBase64File(File file) throws Exception {
        FileInputStream inputFile = new FileInputStream(file);
        byte[] buffer = new byte[(int) file.length()];
        inputFile.read(buffer);
        inputFile.close();
        return new BASE64Encoder().encode(buffer);

    }

    /**
     * 将base64字符解码保存文件
     * @param base64Code
     * @param targetPath
     * @throws Exception
     */

    public static void decoderBase64File(String base64Code, String targetPath)
            throws Exception {
        byte[] buffer = new BASE64Decoder().decodeBuffer(base64Code);
        FileOutputStream out = new FileOutputStream(targetPath);
        out.write(buffer);
        out.close();

    }

    /**
     * 将base64转成FILE
     * @param base64Code
     * @return
     * @throws Exception
     */
    public static File Base64File(String base64Code)
            throws Exception {
        byte[] buffer = new BASE64Decoder().decodeBuffer(base64Code);
        File file = File.createTempFile("oos-java-sdk-", ".jpg");
        FileOutputStream out = new FileOutputStream(file);
        out.write(buffer);
        out.close();
        return  file;
    }




    /**
     * 将base64字符保存文本文件
     * @param base64Code
     * @param targetPath
     * @throws Exception
     */

    public static void toFile(String base64Code, String targetPath)
            throws Exception {

        byte[] buffer = base64Code.getBytes();
        FileOutputStream out = new FileOutputStream(targetPath);
        out.write(buffer);
        out.close();
    }

    public static void main(String[] args) {
        try {
            String base64Code = encodeBase64File("D:/Capture001.png");
            System.out.println(base64Code);
//            decoderBase64File(base64Code, "D:/kk.jpg");
//            toFile(base64Code, "D:\\three.txt");
//            File file = Base64File(text);
//            System.out.println(file);
        } catch (Exception e) {
            e.printStackTrace();

        }

    }

}

