package com.ylz.view.sign.action;

import com.ylz.bizDo.app.po.AppAgreement;
import com.ylz.bizDo.app.po.AppHospDept;
import com.ylz.bizDo.app.vo.AppAgreementQvo;
import com.ylz.bizDo.cd.po.CdCode;
import com.ylz.bizDo.cd.vo.CdAddressSvo;
import com.ylz.packaccede.common.action.CommonAction;
import com.ylz.packcommon.common.CodeGroupConstrats;
import com.ylz.packcommon.common.comEnum.CommonEnable;
import com.ylz.packcommon.common.comEnum.CommonUseType;
import com.ylz.packcommon.common.exception.ActionException;
import com.ylz.packcommon.common.exception.DaoException;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Action(
        value = "openagreement",
        results = {
                @Result(name = "list", location = "/intercept/app/agreement/agreement_list.jsp"),
                @Result(name = "edit", location = "/intercept/app/agreement/agreement_edit.jsp"),
                @Result(name = "json", type = "json", params = {"root", "jsons", "contentType", "text/html"}),
                @Result(name = "jsontreelist", type = "json", params = {"root", "jsonList", "contentType", "text/html"}),
                @Result(name = "jsonVo", type = "json", params = {"root", "jsonVo", "contentType", "text/html"}),
                @Result(name = "jsonLayui", type = "json", params = {"root", "jsonLayui", "contentType", "application/json"})
        }
)
public class AppAgreementAction extends CommonAction {

    private static final long serialVersionUID = 1L;

    private AppAgreement vo;


    /**
     * 查询全部
     *
     * @return
     */
    public String forList() {
        return "list";
    }

    /**
     * 查询全部
     *
     * @return
     */
    public String list() {
        try {
            AppAgreementQvo qvo = (AppAgreementQvo) getQvoJson(AppAgreementQvo.class);
            List<AppAgreement> ls = sysDao.getAppAgreementDao().findListQvo(qvo);
            jsons.setRowsQvo(ls, qvo);
        }catch (Exception e){
            e.printStackTrace();
            new ActionException(this.getClass(), e.getMessage());
            this.newMsgJson(finalErrorMsg);
        }
        return "json";
    }

    /**
     * 准备新增或者修改
     *
     * @return
     */
    public String forAddOrEdit() {
        return "edit";
    }

    /**
     * 查询单条记录
     *
     * @return
     */
    public String jsonByOne() {
        String id = this.getRequest().getParameter("id");
        this.setJsonVo((AppAgreement) sysDao.getServiceDo().find(AppAgreement.class, id));
        return "jsonVo";
    }

    /**
     * 添加
     *
     * @return
     */
    public String add() throws Exception {
        try {
            AppAgreement vo = (AppAgreement) getVoJson(AppAgreement.class);
            //如果状态设置为启用就判断当前机构是否已存在启用状态的协议
            if (vo != null && CommonEnable.QIYONG.getValue().equals(vo.getMentState())) {
                List<AppAgreement> ls = (List<AppAgreement>) this.getSysDao().getAppAgreementDao().findEnabled(vo);
                if (ls.size() > 0) {
                    jsons.setCode("900");
                    jsons.setMsg("该机构已有一份【启用】协议!");
                    return "json";
                }
            }
            //设置默认参数
            vo.setMentUseType(CommonUseType.GEREN.getValue());
            vo.setMentType("1");
            vo.setMentCityId(vo.getMentCityId());
            if (vo != null) {
                sysDao.getServiceDo().add(vo);
                this.newMsgJson(finalSuccessrMsg);
                jsons.setCode("800");
            } else {
                this.newMsgJson(finalErrorMsg);
                jsons.setCode("900");
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
     * 协议内容更新
     * WangCheng
     *
     * @return
     */
    public String modify() throws Exception {
        try {
            AppAgreement vo = (AppAgreement) getVoJson(AppAgreement.class);
            if (vo != null) {
                if (CommonEnable.QIYONG.getValue().equals(vo.getMentState())) {//启用状态
                    List<AppAgreement> ls = this.getSysDao().getAppAgreementDao().findEnabled(vo);
                    if (ls.size() > 0) {//存在状态启用的在用协议
                        if(!ls.get(0).getId().equals(vo.getId())){//修改的不是正在使用的协议不让修改
                            jsons.setCode("900");
                            jsons.setMsg("该机构已有一份【启用】协议!");
                            return "json";
                        }else {//修改的是在用的协议
                            //获取此协议信息
                            AppAgreement ment = (AppAgreement) sysDao.getServiceDo().find(AppAgreement.class, vo.getId());
                            ment.setMentTitle(vo.getMentTitle());
                            ment.setMentHospId(vo.getMentHospId());
                            ment.setMentContent(vo.getMentContent());
                            ment.setMentState(vo.getMentState());
                            this.sysDao.getServiceDo().removePoFormSession(ment);
                            this.sysDao.getServiceDo().modify(ment);
                            this.newMsgJson(finalSuccessrMsg);
                            jsons.setCode("800");
                        }
                    } else {//在不存在启用协议的情况下，禁用的协议要转成启用协议
                        AppAgreement ment = (AppAgreement) sysDao.getServiceDo().find(AppAgreement.class, vo.getId());
                        ment.setMentTitle(vo.getMentTitle());
                        ment.setMentHospId(vo.getMentHospId());
                        ment.setMentContent(vo.getMentContent());
                        ment.setMentState(vo.getMentState());
                        //ment.setMentType(vo.getMentType());
                        this.sysDao.getServiceDo().removePoFormSession(ment);
                        this.sysDao.getServiceDo().modify(ment);
                        this.newMsgJson(finalSuccessrMsg);
                        jsons.setCode("800");
                    }
                }else {
                    AppAgreement ment = (AppAgreement) sysDao.getServiceDo().find(AppAgreement.class, vo.getId());
                    ment.setMentTitle(vo.getMentTitle());
                    ment.setMentHospId(vo.getMentHospId());
                    ment.setMentContent(vo.getMentContent());
                    ment.setMentState(vo.getMentState());
                    //ment.setMentType(vo.getMentType());
                    this.sysDao.getServiceDo().removePoFormSession(ment);
                    this.sysDao.getServiceDo().modify(ment);
                    this.newMsgJson(finalSuccessrMsg);
                    jsons.setCode("800");
                }
            } else {
                this.newMsgJson(finalErrorMsg);
                jsons.setCode("900");
            }
        } catch (DaoException e) {
            e.printStackTrace();
            new ActionException(this.getClass(), e.getMessage());
            jsons.setMsg(finalErrorMsg);
            this.newMsgJson(finalErrorMsg);
            return "json";
        }
        return "json";
    }


    /**
     * 批量删除
     *
     * @return
     */
    public String delete() {
        try {
            AppAgreementQvo qvo = new AppAgreementQvo();
            qvo.setHospId(this.getRequest().getParameter("hospId"));
            if (sysDao.getAppAgreementDao().findListHosp(qvo).size() <= 1) {
                this.newMsgJson("默认一条不能删除!");
                return "json";
            }
            String id = this.getRequest().getParameter("id");
            String[] ids = id.split(";");
            if (ids != null && ids.length > 0) {
                for (String s : ids) {
                    sysDao.getServiceDo().delete(AppAgreement.class, s);
                }
            }
        } catch (Exception e) {
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
     *
     * @return
     */
    public String findCmmInit() {
        try{
            Map<String, Object> map = new HashMap<String, Object>();
            //状态
            List<CdCode> lsEnable = this.getSysDao().getCodeDao().findGroupList(CodeGroupConstrats.GROUP_ENABLE, CommonEnable.QIYONG.getValue());
            List<CdCode> lsMentType = this.getSysDao().getCodeDao().findGroupList(CodeGroupConstrats.GROUP_MENTTYPE, CommonEnable.QIYONG.getValue());
            List<CdCode> lsMentUseType = this.getSysDao().getCodeDao().findGroupList(CodeGroupConstrats.GROUP_MENTUSETYPE, CommonEnable.QIYONG.getValue());
            // List<AppHospDept> lsHosp = this.getSysDao().getAppHospDeptDao().findByState(CommonEnable.QIYONG.getValue());
            // List<CdAddressSvo> lsAddress = this.getSysDao().getCdAddressDao().findByNumLength(4);
            //加载市
            List<CdAddressSvo> lsCity = this.getSysDao().getCdAddressDao().findCity();

            map.put("enable", lsEnable);
            map.put("mentType", lsMentType);
            // map.put("hosp",lsHosp);
            map.put("mentUseType", lsMentUseType);
            // map.put("address",lsAddress);
            map.put("city", lsCity);
            this.getJsons().setMap(map);
        }catch (Exception e){
            e.printStackTrace();
            new ActionException(this.getClass(), e.getMessage());
            this.newMsgJson(this.finalErrorMsg);
        }
        return "json";
    }

    /**
     * 查找医院
     *
     * @return
     */
    public String findHospCmm() {
       try {
           String cityCode = this.getRequest().getParameter("areaCode");
           List<AppHospDept> ls = this.getSysDao().getAppHospDeptDao().findByAreaCode(cityCode);
           this.getJsons().setRows(ls);
       }catch (Exception e){
           e.printStackTrace();
           jsons.setCode("900");
           jsons.setMsg(e.getMessage());
       }
        return "json";
    }


    /**
     * Dmao
     * 医院id查询协议
     *
     * @return
     */
    public String findListHosp() {
        try {
            AppAgreementQvo qvo = (AppAgreementQvo) getVoJson(AppAgreementQvo.class);
            List<AppAgreement> ls = sysDao.getAppAgreementDao().findListHosp(qvo);
            //jsons.setRowsQvo(ls,qvo);
            this.getJsonLayui().setData(ls);
            this.getJsonLayui().setCount(qvo.getItemCount());
        }catch (Exception e){
            e.printStackTrace();
        }
        return "jsonLayui";

    }

    public String findState() throws Exception {
        String id = this.getRequest().getParameter("id");
        if (StringUtils.isNotBlank(id)) {
            AppAgreement ls = sysDao.getAppAgreementDao().findState(id);
            if (ls != null) {
                jsons.setCode("800");
                jsons.setVo(ls);
            } else {
                jsons.setCode("900");
            }
        } else {
            jsons.setCode("900");
            jsons.setMsg("参数为空！");
        }
        return "json";
    }

    public AppAgreement getVo() {
        return vo;
    }

    public void setVo(AppAgreement vo) {
        this.vo = vo;
    }
}
