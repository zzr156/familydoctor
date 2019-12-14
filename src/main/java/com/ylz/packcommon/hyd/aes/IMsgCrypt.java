package com.ylz.packcommon.hyd.aes;

/**
 * Copyright:YLZinfo 2017 cop. ltd.
 * </p>
 * <p>
 * Company:易联众技术股份有限公司
 * Created by  ZJT on 2017/2/20.
 */
public interface IMsgCrypt {
    /**
     * 对数据包进行加密
     * @param msg
     * @return
     */
    String encrypt(String msg);

    /**
     * 对数据包进行解密
     * @param encryptMsg
     * @return
     */
    String decrypt(String encryptMsg);
}
