package com.ylz.bizDo.cd.vo;

import com.ylz.bizDo.cd.po.CdUser;
import com.ylz.packaccede.allDo.SysDao;
import com.ylz.packcommon.common.bizDo.SpringHelper;
import org.apache.commons.lang.StringUtils;

import java.util.List;

/**
 * 二级菜单
 */
public class MenuTwoVo {
    private String fid;
    private String title;
    private String icon;
    private String href;
    private String spread="false";
    private List<MenuTwoVo> children;


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
            this.href = href;
    }

    public String getSpread() {
        return spread;
    }

    public void setSpread(String spread) {
        this.spread = spread;
    }

    public List<MenuTwoVo> getChildren() {
        return children;
    }

    public String getFid() {
        return fid;
    }

    public void setFid(String fid) {
        this.fid = fid;
    }

    public void setChildren(String fid) {
        SysDao dao= (SysDao) SpringHelper.getBean("sysDao");
        List<MenuTwoVo> ls=null;
        try {
            if(StringUtils.isNotBlank(fid)) {
                ls = dao.getMenuDo().findTwoList(fid);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.children = ls;
        this.children = ls;
    }


}
