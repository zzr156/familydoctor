package com.ylz.bizDo.jtapp.ysChangeEntity;

import com.ylz.bizDo.cd.po.CdCode;
import com.ylz.packaccede.allDo.SysDao;
import com.ylz.packcommon.common.CodeGroupConstrats;
import com.ylz.packcommon.common.bizDo.SpringHelper;
import org.apache.commons.lang.StringUtils;

/**
 * Created by zzl on 2017/9/4.
 */
public class YsChangeDrEntity {
    private String id;
    private String name;
    private String sex;
    private String imageUrl;
    private String drType;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getDrType() {
        return drType;
    }

    public void setDrType(String drType) {
        this.drType = drType;
    }

    public String getStrDrType() throws Exception{
        String str = "";
        if(StringUtils.isNotBlank(this.getDrType())){
            SysDao dao = (SysDao) SpringHelper.getBean("sysDao");
            CdCode value = dao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_YSTYPE, this.getDrType());
            if(value!=null) {
                str =  value.getCodeTitle();
            }
        }
        return str;
    }
}
