package com.ylz.packcommon.common.util;

/**
 * Created by hzk on 2017/6/27.
 */
public class IdCardAreaUtils {
    private static final String fzIdCard = "A";//福州市
    private static final String fzArea = "3501";
    private static final String ptIdCard = "B";//莆田市
    private static final String ptArea = "3503";
    private static final String qzIdCard = "C";//泉州市
    private static final String qzArea = "3505";
    private static final String xmIdCard = "D";//厦门市
    private static final String xmArea = "3502";
    private static final String zzIdCard = "E";//漳州市
    private static final String zzArea = "3506";
    private static final String lyIdCard = "F";//龙岩市
    private static final String lyArea = "3508";
    private static final String smIdCard = "G";//三明市
    private static final String smArea = "3504";
    private static final String npIdCard = "H";//南平市
    private static final String npArea = "3507";
    private static final String ndIdCard = "J";//宁德市
    private static final String ndArea = "3509";
    private static final String szIdCard = "K";//省直系统
    private static final String szArea = "3501";

    public static String getIdCardidCard(String idCard){
        String areaCode = null;
        idCard = idCard.toUpperCase();
        if(idCard.contains(fzIdCard)){
            areaCode = fzArea;
        }else if(idCard.contains(ptIdCard)){
            areaCode = ptArea;
        }else if(idCard.contains(qzIdCard)){
            areaCode = qzArea;
        }else if(idCard.contains(xmIdCard)){
            areaCode = xmArea;
        }else if(idCard.contains(zzIdCard)){
            areaCode = zzArea;
        }else if(idCard.contains(lyIdCard)){
            areaCode = lyArea;
        }else if(idCard.contains(smIdCard)){
            areaCode = smArea;
        }else if(idCard.contains(npIdCard)){
            areaCode = npArea;
        }else if(idCard.contains(ndIdCard)){
            areaCode = ndArea;
        }else if(idCard.contains(szIdCard)){
            areaCode = szArea;
        }
        return areaCode;
    }



}
