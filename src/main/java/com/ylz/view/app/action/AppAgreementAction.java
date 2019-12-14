package com.ylz.view.app.action;

import com.ylz.bizDo.app.po.AppAgreement;
import com.ylz.bizDo.app.po.AppHospDept;
import com.ylz.bizDo.app.vo.AppAgreementQvo;
import com.ylz.bizDo.cd.po.CdCode;
import com.ylz.bizDo.cd.vo.CdAddressSvo;
import com.ylz.packaccede.common.action.CommonAction;
import com.ylz.packcommon.common.CodeGroupConstrats;
import com.ylz.packcommon.common.comEnum.CommonEnable;
import com.ylz.packcommon.common.exception.ActionException;
import com.ylz.packcommon.common.exception.DaoException;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Action(
        value="agreement",
        results={
                @Result(name="list", location="/intercept/app/agreement/agreement_list.jsp"),
                @Result(name="edit", location="/intercept/app/agreement/agreement_edit.jsp"),
                @Result(name = "json", type = "json", params = { "root", "jsons","contentType", "text/html"}),
                @Result(name = "jsontreelist", type = "json",params={"root","jsonList","contentType", "text/html"}),
                @Result(name = "jsonVo", type = "json", params = { "root", "jsonVo","contentType", "text/html"}),
                @Result(name = "jsonLayui", type = "json",params={"root","jsonLayui","contentType", "application/json"})
        }
)
public class AppAgreementAction extends CommonAction {

    private static final long serialVersionUID = 1L;

    private AppAgreement vo;


    /**
     * 查询全部
     * @return
     */
    public String forList() {
        return "list";
    }

    /**
     * 查询全部
     * @return
     */
    public String list() {
        try{
            AppAgreementQvo qvo = (AppAgreementQvo)getQvoJson(AppAgreementQvo.class);
            List<AppAgreement> ls = sysDao.getAppAgreementDao().findListQvo(qvo);
            jsons.setRowsQvo(ls,qvo);
        }catch (Exception e){
            e.printStackTrace();
            new ActionException(this.getClass(), e.getMessage());
            this.newMsgJson(finalErrorMsg);
        }
        return "json";
    }

    /**
     * 准备新增或者修改
     * @return
     */
    public String forAddOrEdit(){
        return "edit";
    }

    /**
     * 查询单条记录
     * @return
     */
    public String jsonByOne(){
        String id = this.getRequest().getParameter("id");
        this.setJsonVo((AppAgreement) sysDao.getServiceDo().find(AppAgreement.class, id));
        return "jsonVo";
    }

    /**
     * 添加
     * @return
     */
    public String add() {
        try {
            AppAgreement vo = (AppAgreement)getVoJson(AppAgreement.class);
            if (vo != null) {
                sysDao.getServiceDo().add(vo);
                this.newMsgJson(finalSuccessrMsg);
            }else{
                this.newMsgJson(finalErrorMsg);
            }
        } catch (DaoException e) {
            e.printStackTrace();
            new ActionException(this.getClass(), e.getMessage());
            this.newMsgJson(finalErrorMsg);
            return "json";
        }
        return "json";
    }

    /**
     * 更新
     * @return
     */
    public String modify() {
        try {
            AppAgreement vo = (AppAgreement)getVoJson(AppAgreement.class);
            if (vo != null) {
                AppAgreement ment = (AppAgreement) sysDao.getServiceDo().find(AppAgreement.class,vo.getId());
                ment.setMentTitle(vo.getMentTitle());
                ment.setMentHospId(vo.getMentHospId());
                ment.setMentContent(vo.getMentContent());
                ment.setMentState(vo.getMentState());
                ment.setMentType(vo.getMentType());
                this.sysDao.getServiceDo().removePoFormSession(ment);
                this.sysDao.getServiceDo().modify(ment);
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
     * 批量删除
     * @return
     */
    public String delete() {
        try {
            String id = this.getRequest().getParameter("id");
            String[] ids = id.split(";");
            if(ids != null && ids.length >0){
                for(String s : ids){
                    sysDao.getServiceDo().delete(AppAgreement.class,s);
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
     * 页面初始化
     * @return
     */
    public String findCmmInit(){
        try{
            Map<String, Object> map = new HashMap<String, Object>();
            //状态
            List<CdCode> lsEnable = this.getSysDao().getCodeDao().findGroupList(CodeGroupConstrats.GROUP_ENABLE, CommonEnable.QIYONG.getValue());
            List<CdCode> lsMentType = this.getSysDao().getCodeDao().findGroupList(CodeGroupConstrats.GROUP_MENTTYPE,CommonEnable.QIYONG.getValue());
            List<CdCode> lsMentUseType = this.getSysDao().getCodeDao().findGroupList(CodeGroupConstrats.GROUP_MENTUSETYPE,CommonEnable.QIYONG.getValue());
            // List<AppHospDept> lsHosp = this.getSysDao().getAppHospDeptDao().findByState(CommonEnable.QIYONG.getValue());
            // List<CdAddressSvo> lsAddress = this.getSysDao().getCdAddressDao().findByNumLength(4);
            //加载市
            List<CdAddressSvo> lsCity = this.getSysDao().getCdAddressDao().findCity();

            map.put("enable", lsEnable);
            map.put("mentType",lsMentType);
            // map.put("hosp",lsHosp);
            map.put("mentUseType",lsMentUseType);
            // map.put("address",lsAddress);
            map.put("city",lsCity);
            this.getJsons().setMap(map);
        }catch (Exception e){
            e.printStackTrace();
            new ActionException(this.getClass(),e.getMessage());
            this.newMsgJson(this.finalErrorMsg);
        }
        return "json";
    }

    /**
     * 查找医院
     * @return
     */
    public String findHospCmm(){
        try {
            String cityCode = this.getRequest().getParameter("areaCode");
            List<AppHospDept> ls= this.getSysDao().getAppHospDeptDao().findByAreaCode(cityCode);
            this.getJsons().setRows(ls);
        }catch (Exception e){
            e.printStackTrace();
            new ActionException(this.getClass(), e.getMessage());
            this.newMsgJson(finalErrorMsg);
            return "json";
        }
        return "json";
    }



    /**
     * Dmao
     * 医院id查询协议
     * @return
     */
    public String findListHosp() {
        try {
            AppAgreementQvo qvo = (AppAgreementQvo)getVoJson(AppAgreementQvo.class);
            List<AppAgreement> ls = sysDao.getAppAgreementDao().findListHosp(qvo);
            //jsons.setRowsQvo(ls,qvo);
            this.getJsonLayui().setData(ls);
            this.getJsonLayui().setCount(qvo.getItemCount());
        }catch (Exception e){
            e.printStackTrace();
        }
        return "jsonLayui";

    }

    public AppAgreement getVo() {
        return vo;
    }

    public void setVo(AppAgreement vo) {
        this.vo = vo;
    }

}
