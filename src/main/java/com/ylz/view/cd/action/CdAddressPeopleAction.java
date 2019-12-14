package com.ylz.view.cd.action;

import com.ylz.bizDo.cd.po.CdAddress;
import com.ylz.bizDo.cd.po.CdAddressPeople;
import com.ylz.bizDo.cd.po.CdCode;
import com.ylz.bizDo.cd.vo.CdAddressPeopleQvo;
import com.ylz.bizDo.cd.vo.CdAddressSvo;
import com.ylz.packaccede.common.action.CommonAction;
import com.ylz.packcommon.common.CodeGroupConstrats;
import com.ylz.packcommon.common.comEnum.CommonEnable;
import com.ylz.packcommon.common.exception.ActionException;
import com.ylz.packcommon.common.exception.DaoException;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zzl on 2017/7/13.
 */
@SuppressWarnings("all")
@Action(
        value="addrPeople",
        results={
                @Result(name="list", location="/intercept/cd/addrPeople/addrPeople_list.jsp"),
                @Result(name="edit", location="/intercept/cd/addrPeople/addrPeople_edit.jsp"),
                @Result(name = "jsonVo", type = "json",params={"root","jsonVo","contentType", "text/html"}),
                @Result(name = "json", type = "json",params={"root","jsons","contentType", "text/html"}),
                @Result(name = "jsontreelist", type = "json",params={"root","jsonList","contentType", "text/html"}),
        }
)
public class CdAddressPeopleAction extends CommonAction {
    private CdAddressPeople vo;
    private CdAddressPeopleQvo qvo;

    /**
     * 菜单链接
     * @return
     */
    public String forList(){
        return "list";
    }

    /**
     * 准备新增或修改
     * @return
     */
    public String forAddOrEdit(){
        return "edit";
    }

    /**
     * 查询列表
     * @return
     */
    public String list(){
        try{
            CdAddressPeopleQvo qvo = (CdAddressPeopleQvo)getQvoJson(CdAddressPeopleQvo.class);
            List<CdAddressPeople> ls = sysDao.getCdAddressPeopleDao().findList(qvo);
            jsons.setRowsQvo(ls,qvo);
        }catch (Exception e){
            e.printStackTrace();
            new ActionException(this.getClass(),e.getMessage());
            this.newMsgJson(this.finalErrorMsg);
            return "json";
        }
        return "json";
    }

    /**
     * 查询单个记录
     * @return
     */
    public String jsonByOne(){
        try{
            String id = this.getRequest().getParameter("id");
            this.setJsonVo((CdAddressPeople) sysDao.getServiceDo().find(CdAddressPeople.class, id));
            return "jsonVo";
        }catch (Exception e){
            e.printStackTrace();
            new ActionException(this.getClass(),e.getMessage());
            this.newMsgJson(this.finalErrorMsg);
            return "json";
        }
    }

    /**
     * 新增
     * @return
     */
    public String add(){
        try{
            CdAddressPeople vo = (CdAddressPeople)getVoJson(CdAddressPeople.class);
            if(vo!=null){
                CdAddress addr = sysDao.getCdAddressDao().findByCode(vo.getAreaCode());
                CdAddressPeople vv = sysDao.getCdAddressPeopleDao().findByYearCode(vo.getAreaCode(),vo.getAreaYear());
                if(vv!=null){
                    this.newMsgJson("该地区今年以设置目标");
                    return "json";
                }
                if(addr!=null){
                    vo.setAreaName(addr.getAreaName());
                    vo.setAreaSname(addr.getAreaSname());
                }
                vo.setAreaCreateTime(Calendar.getInstance());
                sysDao.getServiceDo().add(vo);
                this.newMsgJson(this.finalSuccessrMsg);
            }
        }catch (Exception e){
            e.printStackTrace();
            new ActionException(this.getClass(),e.getMessage());
            this.newMsgJson(this.finalErrorMsg);
            return "json";
        }
        return "json";
    }

    /**
     * 统一初始化
     * @return
     */
    public String findCmmInit(){
        try{
            Map<String, Object> map = new HashMap<String, Object>();
            List<CdCode> level = this.getSysDao().getCodeDao().findGroupList(CodeGroupConstrats.GROUP_HOSPLEVEL, CommonEnable.QIYONG.getValue());
            //省
            List<CdAddressSvo> ls= this.getSysDao().getCdAddressDao().findByPidList(null);
            //上限方式
            List<CdCode> top = this.getSysDao().getCodeDao().findGroupList(CodeGroupConstrats.GROUP_FORMULAMODE,CommonEnable.QIYONG.getValue());
            map.put("top",top);
            map.put("province",ls);
            map.put("leve",level);
            this.getJsons().setMap(map);
        }catch (Exception e){
            e.printStackTrace();
            this.getJsons().setCode("900");
            this.getJsons().setMsg(e.getMessage());
        }

        return "json";
    }
    /**
     * 修改
     * @return
     */
    public String modify(){
        try {
            CdAddressPeople vo = (CdAddressPeople)getVoJson(CdAddressPeople.class);
            if (vo != null) {
                CdAddressPeople role = (CdAddressPeople) sysDao.getServiceDo().find(CdAddressPeople.class,vo.getId());
                role.setAreaPopulation(vo.getAreaPopulation());
                role.setAreaRate(vo.getAreaRate());
                role.setAreaTarget(vo.getAreaTarget());
                role.setAreaYear(vo.getAreaYear());
                role.setAreaFocus(vo.getAreaFocus());
                role.setAreaFocusRate(vo.getAreaFocusRate());
                role.setAreaFocusTarget(vo.getAreaFocusTarget());
                if(StringUtils.isNotBlank(vo.getAreaSignTop())){
                    role.setAreaSignTop(vo.getAreaSignTop());
                }
                if(StringUtils.isNotBlank(vo.getAreaSignWay())){
                    role.setAreaSignWay(vo.getAreaSignWay());
                }
                if(StringUtils.isNotBlank(vo.getAreaDisSignTop())){
                    role.setAreaDisSignTop(vo.getAreaDisSignTop());
                }
                if(StringUtils.isNotBlank(vo.getAreaDisSignWay())){
                    role.setAreaDisSignWay(vo.getAreaDisSignWay());
                }
                this.sysDao.getServiceDo().removePoFormSession(role);
                this.sysDao.getServiceDo().modify(role);
                this.newMsgJson(finalSuccessrMsg);
            }else{
                this.newMsgJson(finalErrorMsg);
            }
        }catch (DaoException e) {
            e.printStackTrace();
            new ActionException(this.getClass(), e.getMessage());
            this.newMsgJson(finalErrorMsg);
            return "json";
        }
        return "json";
    }
    /**
     * 删除
     * @return
     */
    public String delete(){
        try {
            String id = this.getRequest().getParameter("id");
            if(StringUtils.isNotBlank(id)){
                String[] ids = id.split(";");
                for(String s:ids){
                    sysDao.getServiceDo().delete(CdAddressPeople.class,s);
                }
            }
        } catch (DaoException e) {
            e.printStackTrace();
            new ActionException(this.getClass(), e.getMessage());
            this.newMsgJson(finalErrorMsg);
            return "json";
        }
        this.newMsgJson(finalSuccessrMsg);
        return "json";
    }

    /**
     * 通过区域编号查询参考人数
     * @return
     */
    public String findPeopleCmm(){
        try{
            String code = this.getRequest().getParameter("code");
            CdAddressPeople vo = this.sysDao.getCdAddressPeopleDao().findByCode(code);
            //查询上一级的两率
            CdAddressPeople vv = this.sysDao.getCdAddressPeopleDao().findUpByCode(code);
            Map<String,Object> map = new HashMap<String,Object>();
            map.put("v1",vo);
            map.put("v2",vo);
            this.getJsons().setMap(map);
            return "json";
        }catch (Exception e){
            e.printStackTrace();
            new ActionException(this.getClass(), e.getMessage());
            this.newMsgJson(finalErrorMsg);
            return "json";
        }
    }
    public CdAddressPeople getVo() {
        return vo;
    }

    public void setVo(CdAddressPeople vo) {
        this.vo = vo;
    }

    public CdAddressPeopleQvo getQvo() {
        return qvo;
    }

    public void setQvo(CdAddressPeopleQvo qvo) {
        this.qvo = qvo;
    }
}
