package com.ylz.packcommon.common.bizDo;

import org.apache.commons.lang.StringUtils;

import java.security.MessageDigest;
import java.util.UUID;

public class CodeGenerator
{
    private static String DIGIST_STR = "0123456789";

    public static String getUUID()
    {
        UUID uuid = UUID.randomUUID();
        return uuid.toString().toUpperCase();
    }

    public static String getUUID32()
    {
        return getUUID().replace("-", "");
    }

    public static String getSHADigest(String srcStr)
    {
        return getDigest(srcStr, "SHA-1");
    }

    public static String getMD5Digest(String srcStr)
    {
        return getDigest(srcStr, "MD5");
    }



    public static String getRandomDigist(int length)
    {
        int srcStrLen = DIGIST_STR.length();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++)
        {
            int maxnum = (int)(Math.random() * 1000.0D);
            int result = maxnum % srcStrLen;
            char temp = DIGIST_STR.charAt(result);
            sb.append(temp);
        }
        return sb.toString();
    }

    private static String getDigest(String srcStr, String alg) {
        if ((StringUtils.isEmpty(srcStr)) || (StringUtils.isEmpty(alg))) {
            throw new RuntimeException("加密的串和密钥不能为空");
        }
        try
        {
            MessageDigest alga = MessageDigest.getInstance(alg);

            alga.update(srcStr.getBytes());
            byte[] digesta = alga.digest();
            return byte2hex(digesta);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static String byte2hex(byte[] b)
    {
        StringBuffer hs = new StringBuffer();
        String stmp = "";
        for (int n = 0; n < b.length; n++)
        {
            stmp = Integer.toHexString(b[n] & 0xFF);
            if (stmp.length() == 1) {
                hs.append("0");
            }
            hs.append(stmp);
        }
        return hs.toString().toUpperCase();
    }


    static
    {
        String aphaStr = "abcdefghijklmnopqrstuvwxyz";
    }
}