package com.ylz.bizDo.web.po;

import com.ylz.bizDo.cd.po.*;
import com.ylz.packaccede.allDo.SysDao;
import com.ylz.packcommon.common.CodeGroupConstrats;
import com.ylz.packcommon.common.bean.BasePO;
import com.ylz.packcommon.common.bizDo.SpringHelper;
import org.apache.commons.lang.StringUtils;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by zzl on 2017/8/18.
 */
@Entity
@Table(name = "CD_DEPT")
public class WebCdDept extends BasePO {
    @Id
    @Column(name = "ID", unique = true, nullable = false, length = 36)
    @GeneratedValue(generator = "paymentableGenerator")
    @GenericGenerator(name = "paymentableGenerator", strategy = "assigned")
    private String id;//主键
    private String snumber;//部门编号
    private String sname;//部门名称
    private String sort;//排序
    private String state;//判断是否启用
    private String rid;//关联角色id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="sid")
    private CdDept sid;//父id
    private String notes;//备注
    private String deptType;//部门类别 0:机构,1:部门
    private String unitType;//单位类型 0：疾控，1：医院
    private String city;//市
    private String area;//区
    @OneToMany( cascade = { CascadeType.ALL },fetch=FetchType.LAZY, mappedBy = "sid")  //自身关联
    @Fetch(FetchMode.SUBSELECT)
    private List<CdDept> slist;


    @ManyToMany(cascade={CascadeType.MERGE,CascadeType.REFRESH},fetch=FetchType.LAZY)//多对多关联
    @JoinTable(name="CD_DEPT_ROLE",
            joinColumns={@JoinColumn(name="id")},
            inverseJoinColumns={@JoinColumn(name="rid")})
    private List<CdRole> roles;

    @OneToMany(mappedBy="cdDept",cascade={CascadeType.MERGE,CascadeType.REFRESH},fetch = FetchType.LAZY)
    private List<CdUser> user;

    @Column(name = "cjrId",length = 36)
    private String cjrId;//创建人ID
    @Column(name = "cjdwId",length = 36)
    private String cjdwId;//创建单位ID
    @Column(name = "cjsj")
    private Date cjsj;//创建时间
    @Column(name = "xgrId",length = 36)
    private String xgrId;//修改人ID
    @Column(name = "xgdwId",length = 36)
    private String xgdwId;//修改单位ID
    @Column(name = "xgsj")
    private Date xgsj;//修改时间


    /** default constructor */
    public WebCdDept() {
    }

    /** minimal constructor */
    public WebCdDept(String id) {
        this.id = id;
    }

    public Date getXgsj() {
        return xgsj;
    }
    public void setXgsj(Date xgsj) {
        this.xgsj = xgsj;
    }
    public List<CdUser> getUser() {
        return user;
    }
    public String getCjrId() {
        return cjrId;
    }
    public void setCjrId(String cjrId) {
        this.cjrId = cjrId;
    }
    public String getCjdwId() {
        return cjdwId;
    }
    public void setCjdwId(String cjdwId) {
        this.cjdwId = cjdwId;
    }
    public Date getCjsj() {
        return cjsj;
    }
    public void setCjsj(Date cjsj) {
        this.cjsj = cjsj;
    }
    public String getXgrId() {
        return xgrId;
    }
    public void setXgrId(String xgrId) {
        this.xgrId = xgrId;
    }
    public String getXgdwId() {
        return xgdwId;
    }
    public void setXgdwId(String xgdwId) {
        this.xgdwId = xgdwId;
    }
    public void setUser(List<CdUser> user) {
        this.user = user;
    }
    public List<CdRole> getRoles() {
        return roles;
    }
    public void setRoles(List<CdRole> roles) {
        this.roles = roles;
    }
    public List<CdDept> getSlist() {
        return slist;
    }
    public void setSlist(List<CdDept> slist) {
        this.slist = slist;
    }
    public String getNotes() {
        return notes;
    }
    public void setNotes(String notes) {
        this.notes = notes;
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    public String getSnumber() {
        return snumber;
    }
    public void setSnumber(String snumber) {
        this.snumber = snumber;
    }
    public String getSname() {
        return sname;
    }
    public void setSname(String sname) {
        this.sname = sname;
    }
    public String getSort() {
        return sort;
    }
    public void setSort(String sort) {
        this.sort = sort;
    }
    public String getState() {
        return state;
    }
    public void setState(String state) {
        this.state = state;
    }
    public String getRid() {
        return rid;
    }
    public void setRid(String rid) {
        this.rid = rid;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public CdDept getSid() {
        return sid;
    }
    public void setSid(CdDept sid) {
        this.sid = sid;
    }
    public String getDeptType() {
        return deptType;
    }
    public void setDeptType(String deptType) {
        this.deptType = deptType;
    }

    public String getDeptSid(){
        if(getSid() != null){
            return getSid().getId();
        }
        return null;
    }

    public String getUnitType() {
        return unitType;
    }

    public void setUnitType(String unitType) {
        this.unitType = unitType;
    }

    public String getDeptState() {
        return getState();
    }

    public void setDeptState(String deptState){
        this.setState(deptState);
    }

    public String getParnentSname(){
        if(getSid() != null){
            if(StringUtils.isNotBlank(getSid().getId())){
                SysDao service =  (SysDao) SpringHelper.getBean("sysDao");
                CdDept vo =  (CdDept)service.getServiceDo().find(CdDept.class,getSid().getId());
                return vo.getSname();
            }
        }
        return null;
    }


    //权限处理
    public void setRoleid(String[] roleid){
        this.setRoles(new ArrayList<CdRole>());
        SysDao service =  (SysDao) SpringHelper.getBean("sysDao");
        if (roleid != null) {
            for (String i : roleid) {
                CdRole vo = (CdRole)service.getServiceDo().find(CdRole.class,i);
                if(vo != null){
                    this.getRoles().add(vo);
                }
            }
        }
    }

    public String[] getRoleid(){
        if(this.getRoles()!=null && this.getRoles().size()>0){
            String[] ss=new String[this.getRoles().size()];
            int i=0;
            for(CdRole r:this.getRoles()){
                ss[i]=r.getId();
                i++;
            }
            return ss;
        }
        return null;
    }

    public String getRoleName(){
        String names=null;
        if(this.getRoles()!=null && this.getRoles().size()>0){
            for(CdRole s:this.getRoles()){
                if(StringUtils.isBlank(names)){
                    names =  s.getRname();
                }else{
                    names = names +","+ s.getRname();
                }
            }
        }
        return names;
    }

    public String getParnentId() {
        if(getSid() != null){
            return getSid().getId();
        }
        return null;
    }

    public void setParnentId(String parnentId) {
        SysDao service =  (SysDao) SpringHelper.getBean("sysDao");
        if(StringUtils.isNotBlank(parnentId)){
            this.setSid((CdDept)service.getServiceDo().find(CdDept.class,parnentId));
        }
    }
    //根据deptType获取部门类型名称
    public String getDeptTypeName() throws Exception{
        SysDao dao =  (SysDao) SpringHelper.getBean("sysDao");
        CdCode value = dao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_DEPTTYPE, this.getDeptType());
        if(value!=null) {
            return value.getCodeTitle();
        }
        return "";
    }
    //根据state获取部门状态名称
    public String getStateName() throws Exception{
        SysDao dao =  (SysDao) SpringHelper.getBean("sysDao");
        CdCode value = dao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_ENABLE, this.getState());
        if(value!=null) {
            return value.getCodeTitle();
        }
        return "";
    }
    /**
     * 获取城市名称
     * @return
     */
    public String getCityName(){
        SysDao dao =  (SysDao) SpringHelper.getBean("sysDao");
        if(this.getCity()!=null){
            CdAddress value=(CdAddress) dao.getServiceDo().find(CdAddress.class,this.getCity());
            if(value!=null){
                return value.getAreaSname();
            }
        }
        return "";
    }
    /**
     * 获取地区名称
     * @return
     */
    public String getAreaName(){
        SysDao dao =  (SysDao) SpringHelper.getBean("sysDao");
        if(this.getArea()!=null){
            CdAddress value=(CdAddress) dao.getServiceDo().find(CdAddress.class,this.getArea());
            if(value!=null){
                return value.getAreaSname();
            }
        }
        return "";
    }
}
