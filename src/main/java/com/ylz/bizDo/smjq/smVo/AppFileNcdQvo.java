package com.ylz.bizDo.smjq.smVo;

import com.ylz.bizDo.jtapp.basicHealthEntity.T_dwellerfileYlkDTO;

/**
 * Created by zzl on 2019/4/24.
 */
public class AppFileNcdQvo {
    private T_dwellerfileYlkDTO fileVo;//健康档案
    private String urlType;//城市编码

    public T_dwellerfileYlkDTO getFileVo() {
        return fileVo;
    }

    public void setFileVo(T_dwellerfileYlkDTO fileVo) {
        this.fileVo = fileVo;
    }

    public String getUrlType() {
        return urlType;
    }

    public void setUrlType(String urlType) {
        this.urlType = urlType;
    }
}
