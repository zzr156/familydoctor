package com.ylz.packcommon.common.util;

/**
 * Created by Administrator on 14-5-8.
 */
public class WDWUtil {
    /**

     * @描述：是否是2003的excel，返回true是2003

     * @作者：wdw

     * @时间：2011-8-9 下午03:20:49

     * @参数：@param fileName

     * @参数：@return

     * @返回值：boolean

     */

    public static boolean isExcel2003(String fileName){

        return fileName.matches("^.+\\.(?i)(xls)$");

    }

    /**

     * @描述：是否是2007的excel，返回true是2007

     * @作者：wdw

     * @时间：2011-8-9 下午03:21:37

     * @参数：@param fileName

     * @参数：@return

     * @返回值：boolean

     */

    public static boolean isExcel2007(String fileName){

        return fileName.matches("^.+\\.(?i)(xlsx)$");

    }


}
