package com.ylz.bizDo.flow.po;

import com.ylz.bizDo.cd.po.CdUser;
import com.ylz.packaccede.allDo.SysDao;
import com.ylz.packcommon.common.bizDo.SpringHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by PC on 2015/9/7.
 */
public enum FlowStepFindUsers {

    SQZ_USER(1,"申请者","0"),
    USER_ID(2,"指定人员","1"),
    ZW_ID(3,"指定职位","2"),
    JS_ID(4,"指定角色","3"),
    CURRENT_USER(5,"登入者同部门的所有员工","0"),
    CURRENT_ZW(6,"登入者同部门指定职位","2"),
    ALL_USER(7,"所有人员","0");


    private int typeid;
    private String typeName;
    private String typeState;//0:不显示任何选择人员,1:只显示选择人员按钮,2:显示职位,3:显示角色
    FlowStepFindUsers(int typeid, String typeName,String typeState)
    {
        this.typeid = typeid;
        this.typeName = typeName;
        this.typeState=typeState;
    }

    /**
     * 流程步骤人员设置
     * @param lxid //类型id
     * @param jsbm //角色编码
     * @param zwbm //职位编码
     * @param bmbm //部门编码
     * @param userid //用户id
     * @param ywclass //业务主表Clss
     * @param ywid  //业务主表id
     * @return
     */
    public static List<CdUser> findStaffsById(int lxid,String currentUserId, String jsbm, String zwbm, String bmbm, String userid,Class ywclass,String ywid){
        SysDao dao= (SysDao) SpringHelper.getBean("sysDao");
        List<CdUser> ls=new ArrayList<CdUser>();
        switch (lxid) {
            case 1:
                return ls;
            case 2:
                ls.add((CdUser)dao.getServiceDo().find(CdUser.class,userid));
                return ls;
            case 3:
                return ls;
            case 4:
                return ls;
            case 5:
                return ls;
            case 6:
                return ls;
            case 7:
                return dao.getServiceDo().findAll(CdUser.class);
        }
        return null;
    }

    public int getTypeid() {
        return typeid;
    }

    public void setTypeid(int typeid) {
        this.typeid = typeid;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getTypeState() {
        return typeState;
    }

    public void setTypeState(String typeState) {
        this.typeState = typeState;
    }
}
