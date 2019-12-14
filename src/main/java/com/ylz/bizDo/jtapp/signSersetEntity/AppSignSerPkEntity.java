package com.ylz.bizDo.jtapp.signSersetEntity;

import com.ylz.bizDo.cd.po.CdCode;
import com.ylz.packaccede.allDo.SysDao;
import com.ylz.packcommon.common.CodeGroupConstrats;
import com.ylz.packcommon.common.bizDo.SpringHelper;
import org.apache.commons.lang.StringUtils;

/**服务内容层
 * Created by zzl on 2017/8/23.
 */
public class AppSignSerPkEntity {
    private String pid;//服务内容id
    private String pName;//服务内容名称
    private String pValue;//服务内容值
    private String pState;//是否是基础服务内容
    private String pImage;//图标

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getpName() {
        return pName;
    }

    public void setpName(String pName) {
        this.pName = pName;
    }

    public String getpValue() {
        return pValue;
    }

    public void setpValue(String pValue) {
        this.pValue = pValue;
    }

    public String getpState() {
        return pState;
    }

    public void setpState(String pState) {
        this.pState = pState;
    }

    public String getpImage() {
        return pImage;
    }

    public void setpImage(String pImage) {
        this.pImage = pImage;
    }

    public String getPimageName() throws Exception{
        if(StringUtils.isNotBlank(this.getpImage())){
            SysDao dao = (SysDao) SpringHelper.getBean("sysDao");
            CdCode value = dao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_ICON, this.getpImage());
            if(value!=null) {
                return value.getCodeTitle();
            }
        }
        return "";
    }
}
