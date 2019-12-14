package com.ylz.bizDo.jtapp.basicHealthEntity;

import java.util.List;

/**
 * Created by lintingjie on 2017/7/23.
 */
public class ResidentRecordsEntity {

    ResidentHealthFiles t_dwellerfile;
    ElderHealthEntity t_elderlyhealth;
    List<CmdinfoEntity> t_cm_dinfo;

    public ResidentHealthFiles getT_dwellerfile() {
        return t_dwellerfile;
    }

    public void setT_dwellerfile(ResidentHealthFiles t_dwellerfile) {
        this.t_dwellerfile = t_dwellerfile;
    }

    public ElderHealthEntity getT_elderlyhealth() {
        return t_elderlyhealth;
    }

    public void setT_elderlyhealth(ElderHealthEntity t_elderlyhealth) {
        this.t_elderlyhealth = t_elderlyhealth;
    }

    public List<CmdinfoEntity> getT_cm_dinfo() {
        return t_cm_dinfo;
    }

    public void setT_cm_dinfo(List<CmdinfoEntity> t_cm_dinfo) {
        this.t_cm_dinfo = t_cm_dinfo;
    }
}
