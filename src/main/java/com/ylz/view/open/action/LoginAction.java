package com.ylz.view.open.action;


import com.ylz.bizDo.app.po.AppDrUser;
import com.ylz.bizDo.app.po.AppHospDept;
import com.ylz.bizDo.app.po.AppTeamMember;
import com.ylz.bizDo.cd.po.*;
import com.ylz.bizDo.cd.vo.MenuSvo;
import com.ylz.bizDo.web.po.WebCdDept;
import com.ylz.packaccede.common.action.CommonAction;
import com.ylz.packcommon.common.CodeGroupConstrats;
import com.ylz.packcommon.common.Constant;
import com.ylz.packcommon.common.comEnum.AddressType;
import com.ylz.packcommon.common.exception.ActionException;
import com.ylz.packcommon.common.util.AreaUtils;
import com.ylz.packcommon.common.util.Md5Util;
import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@SuppressWarnings("serial")
@Action(value = "login",
        results = {
                @Result(name = "index", location = "/open/index.jsp"),
                @Result(name = "admin", location = "/intercept/sign/echarts/echarts_admin.jsp"),
                @Result(name = "error", location = "/open/error.jsp"),
                @Result(name = "invalid.token", location = "/open/login.jsp"),
                @Result(name = "resetPwd", location = "/open/resetUserPwd.jsp"),
                @Result(name = "json", type = "json", params = {"root", "jsons", "contentType", "text/html"}),
                @Result(name = "jsonVo", type = "json", params = {"root", "jsonVo", "contentType", "text/html"}),
                @Result(name = "jsonlist", type = "json", params = {"root", "jsonList", "contentType", "text/html"})
        }
)
public class LoginAction extends CommonAction {
    private String ypassword;// 新密码
    private String userNewPassword;// 新密码
    private String userPwdOk;// 确认新密码
    private CdUser us;
    private String usname;
    private List<CdMenu> menulist;
    private List<MenuSvo> menuList;
    private String isSqyl = "0";


    /**
     * 准备修改密码
     */
    public String forResetPwd() {
        String ids = this.getRequest().getParameter("checkRow");
        this.getRequest().setAttribute("paramId", ids);
        this.getRequest().setAttribute("ypassword", "");
        this.getRequest().setAttribute("userNewPassword", "");
        this.getRequest().setAttribute("userPwdOk", "");
        return "resetPwd";
    }

    /**
     * 重置密码
     * 用户点击重置密码按钮， 验证新密码与确认新密码正确后执行这个方法<br>
     * 失败与成功都返回用户管理页面<br>
     */
    public String resetPwd() {
        try{
            if (isvers("addTime")) {
                return this.forResetPwd();
            }
            String ids = this.getRequest().getParameter("paramId");
            this.getRequest().setAttribute("paramId", ids);
            this.getRequest().setAttribute("ypassword", this.getYpassword());
            this.getRequest().setAttribute("userNewPassword", this.getUserNewPassword());
            this.getRequest().setAttribute("userPwdOk", this.getUserPwdOk());
            us = sysDao.getUserDo().findUser(ids);
            if (us != null) {
                this.setYpassword(Md5Util.MD5(this.getYpassword()));
                if (!us.getUserPassword().equals(this.getYpassword())) {
                    this.setMsg("原密码错误！");
                    return "resetPwd";
                }
                // 加密
                String md5NewPwd = Md5Util.MD5(this.getUserNewPassword());
                us.setUserPassword(md5NewPwd);
                // 修改
                sysDao.getServiceDo().saveUpdate(us);
                this.setMsg("修改密码成功！");
            }
        }catch (Exception e){
            e.printStackTrace();
            this.setMsg(e.getMessage());
        }

        return "resetPwd";
    }

    /**
     * 用户登录
     * 用户点击登录链接，传act=login字符串<br>
     * 登录成功返回工程主页，不是用户管理页面<br>
     * 返回的是userlogin.action
     */
    public String login() throws Exception {
        if (isvers("loginTime")) {
            return this.index();
        }
        String yzm = getRequest().getParameter("yzm");
        String account = getRequest().getParameter("account");
        String userPassword = getRequest().getParameter("userPassword");
        this.getRequest().setAttribute("account", account);

        if (!yzm.equals((super.getRequest().getSession()
                .getAttribute(com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY)))) {
            this.newMsgJson("验证码输入错误！");
            return "json";
        }

        // 加密
        String md5UserPassword = Md5Util.MD5(userPassword);

        CdUser user = sysDao.getUserDo().findUser(account, md5UserPassword);
        // CdUser user = sysDao.getUserDo().findByUserName(account);
        if (user == null) {
            // 如果账号匹配，密码不匹配则提示 功能：防止同账号不同密码造成的一个账号有多条记录
            user = sysDao.getUserDo().findUserByAccount(account);
            if (user != null) {
                this.newMsgJson("密码错误,请重新输入！");
                return "json";
            } else {
                AppDrUser drUser = sysDao.getAppDrUserDao().findByUserWeb(account, md5UserPassword);
                if (drUser == null) {
                    this.newMsgJson("该用户不存在或密码错误，请重新输入！");
                    return "json";
                } else {
                    if ("0".equals(drUser.getDrWebState())) {
                        this.newMsgJson("该用户不存在或密码错误，请重新输入！");
                        return "json";
                    } else {
                        this.getRequest().getSession().setAttribute("DrTypeRole", drUser.getDrTypeRole() != null ? drUser.getDrTypeRole() : "");
                        CdUser cdUser = new CdUser();
                        cdUser.setAccount(drUser.getDrAccount());
                        cdUser.setCdDpetId(drUser.getDrHospId());
                        cdUser.setHospId(drUser.getDrHospId());
                        cdUser.setUserPassword(drUser.getDrPwd());
                        cdUser.setUserName(drUser.getDrName());
                        cdUser.setDrId(drUser.getId());

                        AppHospDept dept = (AppHospDept) sysDao.getServiceDo().find(AppHospDept.class, drUser.getDrHospId());
                        if (dept != null) {
                            String areaCode = AreaUtils.getAreaCode(dept.getHospAreaCode());
                            WebCdDept cdDept = (WebCdDept) sysDao.getServiceDo().find(WebCdDept.class, dept.getId());
                            if (cdDept == null) {
                                cdDept = new WebCdDept();
                                cdDept.setCjsj(new Date());
                                cdDept.setArea(dept.getHospAreaCode());
                                cdDept.setId(dept.getId());
                                cdDept.setDeptType("0");
                                sysDao.getServiceDo().add(cdDept);
                            }
                            // 不删原有权限，添加新权限
                            String[] roleIds = sysDao.getUserDo().getRoleIdd(areaCode, drUser.getDrRole(), null);
                            if (roleIds.length > 0) {
                                cdUser.setRoleid(roleIds);
                            }
                            // 重新赋值权限
                            /*List<CdRole> listRole = sysDao.getUserDo().getRoleList(areaCode, drUser.getDrRole());
                            if (listRole != null && listRole.size() > 0) {
                                cdUser.setRole(listRole);
                            }

                            if (areaCode.length() == 4) {
                                String[] roleId = new String[]{AppRoleType.SHI.getValue()};
                                cdUser.setRoleid(roleId);
                            } else {
                                if (StringUtils.isNotBlank(drUser.getDrRole())) {
                                    if (drUser.getDrRole().indexOf(AppRoleType.QU.getValue()) != -1 || drUser.getDrRole().indexOf(AppRoleType.SHEQU.getValue()) != -1) {
                                        String[] roleId = new String[]{AppRoleType.SHEQU.getValue(), AppRoleType.JDLK.getValue()};
                                        cdUser.setRoleid(roleId);
                                    } else {
                                        String[] roleId = new String[]{AppRoleType.SHEQU.getValue()};
                                        cdUser.setRoleid(roleId);
                                    }
                                } else {
                                    String[] roleId = new String[]{AppRoleType.SHEQU.getValue()};
                                    cdUser.setRoleid(roleId);
                                }
                            }*/
                            cdUser.setCdDpetId(cdDept.getId());
                            sysDao.getServiceDo().add(cdUser);
                        }
                        user = cdUser;
                        // 登录时session增加drRole,add by WangCheng
                        this.getRequest().getSession().setAttribute("drRole", drUser.getDrRole() != null ? drUser.getDrRole() : "");
                        // cdUser.setRoleid(AppRoleType.SHENG.getValue());
                    }
                }
            }
        } else {
            if (StringUtils.isNotBlank(user.getDrId())) {
                AppDrUser drUser = (AppDrUser) sysDao.getServiceDo().find(AppDrUser.class, user.getDrId());
                if (drUser != null) {
                    if (StringUtils.isNotBlank(drUser.getDrHospId())) {
                        AppHospDept dept = (AppHospDept) sysDao.getServiceDo().find(AppHospDept.class, drUser.getDrHospId());
                        if (dept != null) {
                            this.getRequest().getSession().setAttribute("HospLabelState", dept.getLabelState());
                            String roleStr = "";
                            if (user.getRoleid().length > 0) {
                                for (String role : user.getRoleid()) {
                                    if (StringUtils.isBlank(roleStr)) {
                                        roleStr = role;
                                    } else {
                                        roleStr += ";" + role;
                                    }
                                }
                            }
                            String areaCode = AreaUtils.getAreaCode(dept.getHospAreaCode());

                            // 不删原有权限，添加新权限
                            String[] roleIds = sysDao.getUserDo().getRoleIdd(areaCode, drUser.getDrRole(), roleStr);
                            if (roleIds.length > 0) {
                                user.setRoleid(roleIds);
                                sysDao.getServiceDo().modify(user);
                            }
                            // 重新赋值权限
                            /*List<CdRole> listRole = sysDao.getUserDo().getRoleList(areaCode, drUser.getDrRole());
                            if (listRole != null && listRole.size() > 0) {
                                user.setRole(listRole);
                                sysDao.getServiceDo().modify(user);
                            }

                            if (areaCode.length() == 4) {
                                if (roleStr.indexOf(AppRoleType.SHI.getValue()) == -1) {
                                    String[] roleId = new String[]{AppRoleType.SHI.getValue()};
                                    user.setRoleid(roleId);
                                    sysDao.getServiceDo().modify(user);
                                }
                            } else {
                                if (StringUtils.isNotBlank(drUser.getDrRole())) {
                                    if (drUser.getDrRole().indexOf(AppRoleType.QU.getValue()) != -1 || drUser.getDrRole().indexOf(AppRoleType.SHEQU.getValue()) != -1) {
                                        if (roleStr.indexOf(AppRoleType.SHEQU.getValue()) == -1 && roleStr.indexOf(AppRoleType.JDLK.getValue()) == -1) {
                                            String[] roleId = new String[]{AppRoleType.SHEQU.getValue(), AppRoleType.JDLK.getValue()};
                                            user.setRoleid(roleId);
                                            sysDao.getServiceDo().modify(user);
                                        } else if (roleStr.indexOf(AppRoleType.SHEQU.getValue()) == -1) {
                                            String[] roleId = new String[]{AppRoleType.SHEQU.getValue()};
                                            user.setRoleid(roleId);
                                            sysDao.getServiceDo().modify(user);
                                        } else if (roleStr.indexOf(AppRoleType.JDLK.getValue()) == -1) {
                                            String[] roleId = new String[]{AppRoleType.JDLK.getValue()};
                                            user.setRoleid(roleId);
                                            sysDao.getServiceDo().modify(user);
                                        }
                                    }
                                } else {
                                    if (roleStr.indexOf(AppRoleType.SHEQU.getValue()) == -1) {
                                        String[] roleId = new String[]{AppRoleType.SHEQU.getValue()};
                                        user.setRoleid(roleId);
                                        sysDao.getServiceDo().modify(user);
                                    }
                                }
                            }*/
                        }
                    }
                    // 登录时session增加drRole,add by WangCheng
                    this.getRequest().getSession().setAttribute("drRole", drUser.getDrRole() != null ? drUser.getDrRole() : "");
                    this.getRequest().getSession().setAttribute("DrTypeRole", drUser.getDrTypeRole() != null ? drUser.getDrTypeRole() : "");
                }
            }
        }

        /*String code = this.sysDao.getMsgPhoneAsyncBean().msgPhoneCheck(account, user.getMobilePhone(), yzm);
        if (!"1".equals(code)) {
            this.newMsgJson("验证码过期，请重新获取！");
            return "json";
        }*/

        // 设置session，获取未登录的user
        if (StringUtils.isNotBlank(user.getHospId())) {
            AppHospDept dept = (AppHospDept) sysDao.getServiceDo().find(AppHospDept.class, user.getHospId());
            if (dept != null) {
                this.getRequest().getSession().setAttribute("HospAreaCode", dept.getHospCityareaId());
                this.getRequest().getSession().setAttribute("upHospAreaCode", dept.getHospUpcityareaId());
                this.getRequest().getSession().setAttribute("HospName", dept.getHospName());
                if (StringUtils.isNotBlank(dept.getHospCityareaId())) {
                    CdCode code = sysDao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_JDSOURCETYPE, AreaUtils.getAreaCode(dept.getHospCityareaId(), "2"));
                    if (code != null) {
                        this.getRequest().getSession().setAttribute("jdSourceType", code.getCodeTitle());
                    }
                }
                String type = "0";
                if (AreaUtils.getAreaCode(dept.getHospAreaCode()).length() > 6) {
                    type = "1";
                }
                this.getRequest().getSession().setAttribute("HospDissoluation", type);
                if (StringUtils.isNotBlank(dept.getHospLevel())) {
                    this.getRequest().getSession().setAttribute("HospLevel", dept.getHospLevel());
                } else {
                    CdAddress address = sysDao.getCdAddressDao().findByCacheCode(dept.getHospAreaCode());
                    if (address != null) {
                        this.getRequest().getSession().setAttribute("HospLevel", address.getLevel());
                    }
                }
            }
        }

        // 设置医生所属团队ID
        List<AppTeamMember> teamMembers = sysDao.getServiceDo().loadByPk(AppTeamMember.class, "memDrId", user.getDrId());
        if (teamMembers != null && teamMembers.size() > 0) {
            this.getRequest().getSession().setAttribute("teamId", teamMembers.get(0).getMemTeamid());
        }

        this.getRequest().getSession(false).setAttribute(Constant.SESSION_ATTRIBUTE_LOGIN_STAFF, user);
        this.getRequest().getSession().setAttribute("UserId", user.getUserId());
        this.getRequest().getSession().setAttribute("DrId", user.getDrId());
        this.getRequest().getSession().setAttribute("UserName", user.getUserName());

        // 设置单位id
        this.getRequest().getSession(false).setAttribute(Constant.ORG_ID, user.getHospId());

        this.setUsname(user.getAccount());
        this.setUs(user);
        /*this.newMsgJson(this.finalSuccessrMsg);
        return "json";*/
        return this.index();
    }

    /**
     * 查菜单到页面
     */
    public String index() {
        try{
// 获取用户
            CdUser user = (CdUser) this.getRequest().getSession(true).getAttribute(Constant.SESSION_ATTRIBUTE_LOGIN_STAFF);
            if (user == null) {
                return com.opensymphony.xwork2.Action.LOGIN;
            }
            List<CdMenuSon> son = new ArrayList<CdMenuSon>();
            if (user.getRole() != null && user.getRole().size() > 0) {
                String[] rids = new String[user.getRole().size()];
                System.out.println("权限数组：" + Arrays.toString(rids));
                for (int i = 0; i < user.getRole().size(); i++) {
                    rids[i] = user.getRole().get(i).getId();
                }
                if (rids != null && rids.length > 0) {
                    menuList = sysDao.getMenuDo().findByRoId(rids);
                    List<CdMenuSon> sonList = this.sysDao.getSonDo().findSonRoId(rids);
                    for (CdMenuSon i : sonList) {
                        son.add(i);
                    }
                }
            }
            if (user.getCdDept() != null) {
                if (user.getCdDept().getRoles() != null && user.getCdDept().getRoles().size() > 0) {
                    String[] rids = new String[user.getCdDept().getRoles().size()];
                    for (int i = 0; i < user.getCdDept().getRoles().size(); i++) {
                        rids[i] = user.getCdDept().getRoles().get(i).getId();
                    }
                    if (rids != null && rids.length > 0) {
                        List<CdMenuSon> sonList = this.sysDao.getSonDo().findSonRoId(rids);
                        for (CdMenuSon i : sonList) {
                            son.add(i);
                        }
                    }
                }
            }
            menulist = this.getSysDao().getMenuDo().selectAll();
            // 控制按钮的集合
            this.getRequest().getSession(false).setAttribute(Constant.SESSION_SON, son);
            getRequest().setAttribute("menuList", menuList);
            this.newMsgJson(this.finalSuccessrMsg);
            return "json";
        }catch (Exception e){
            e.printStackTrace();
            this.newMsgJson(this.finalErrorMsg);
            return "json";
        }

        // return "index";
    }

    /**
     * 查菜单到页面
     */
    public String indexCmm() {
        try{
            CdUser user = (CdUser) this.getRequest().getSession(true).getAttribute(Constant.SESSION_ATTRIBUTE_LOGIN_STAFF);
            if (user == null) {
                return com.opensymphony.xwork2.Action.LOGIN;
            }
            List<CdMenuSon> son = new ArrayList<CdMenuSon>();
            if (user.getRole() != null && user.getRole().size() > 0) {
                String[] rids = new String[user.getRole().size()];
                for (int i = 0; i < user.getRole().size(); i++) {
                    rids[i] = user.getRole().get(i).getId();
                }
                if (rids != null && rids.length > 0) {
                    menuList = sysDao.getMenuDo().findByRoId(rids);
                    List<CdMenuSon> sonList = this.sysDao.getSonDo().findSonRoId(rids);
                    for (CdMenuSon i : sonList) {
                        son.add(i);
                    }
                }
            }
            if (user.getCdDept().getRoles() != null && user.getCdDept().getRoles().size() > 0) {
                String[] rids = new String[user.getCdDept().getRoles().size()];
                for (int i = 0; i < user.getCdDept().getRoles().size(); i++) {
                    rids[i] = user.getCdDept().getRoles().get(i).getId();
                }
                if (rids != null && rids.length > 0) {
                    List<CdMenuSon> sonList = this.sysDao.getSonDo().findSonRoId(rids);
                    for (CdMenuSon i : sonList) {
                        son.add(i);
                    }
                }
            }
            menulist = this.getSysDao().getMenuDo().selectAll();
            // 控制按钮的集合
            this.getRequest().getSession(false).setAttribute(Constant.SESSION_SON, son);
            getRequest().setAttribute("menuList", menuList);
            return "index";
        }catch (Exception e){
            e.printStackTrace();
            this.newMsgJson(this.finalErrorMsg);
            return "json";
        }
    }

    /**
     * 登录验证码
     */
    /*public void loginIdentifyCode() {
        String account = CaUtil.getUserName();
        String account = "admin";
        String account = getRequest().getParameter("account");
        CdUser user = sysDao.getUserDo().findByUserName(account);
        String code = this.sysDao.getMsgPhoneAsyncBean().msgPhoneYzm(user.getUserId(), account, user.getMobilePhone());
        System.out.println(code);
        this.setJsonVo(code);
    }*/

    /**
     * 登录验证码
     */
    public String loginIdentifyCode() {
        try {
            String account = getRequest().getParameter("account");
            CdUser user = sysDao.getUserDo().findByUserName(account);
            String code = this.sysDao.getMsgPhoneAsyncBean().msgPhoneYzm(user.getUserId(), account, user.getMobilePhone());
            this.newMsgJson(code);
            return "json";
        } catch (Exception e) {
            e.printStackTrace();
            this.newMsgJson("false");
            return "json";
        }
    }

    public List<CdMenu> getMenulist() {
        return menulist;
    }

    public void setMenulist(List<CdMenu> menulist) {
        this.menulist = menulist;
    }

    public String getUsname() {
        return usname;
    }

    public void setUsname(String usname) {
        this.usname = usname;
    }

    public CdUser getUs() {
        return us;
    }

    public void setUs(CdUser us) {
        this.us = us;
    }

    public String getUserNewPassword() {
        return userNewPassword;
    }

    public void setUserNewPassword(String userNewPassword) {
        this.userNewPassword = userNewPassword;
    }

    public String getUserPwdOk() {
        return userPwdOk;
    }

    public void setUserPwdOk(String userPwdOk) {
        this.userPwdOk = userPwdOk;
    }

    public String getYpassword() {
        return ypassword;
    }

    public void setYpassword(String ypassword) {
        this.ypassword = ypassword;
    }

    public List<MenuSvo> getMenuList() {
        return menuList;
    }

    public void setMenuList(List<MenuSvo> menuList) {
        this.menuList = menuList;
    }

    public String loginJw() throws Exception {
        String account = getRequest().getParameter("acc");
        String orgid = getRequest().getParameter("orgCode");
        this.getRequest().setAttribute("account", account);
        String linkman = getRequest().getParameter("linkman");
        String hospid = null;
        String cityCode = AreaUtils.getAreaCode(linkman, "2");
        CdCode code = this.sysDao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_CITYCODE, cityCode);
        if (code != null) {
            if (code.getCodeTitle().equals(AddressType.FZ.getValue())) {

            } else if (code.getCodeTitle().equals(AddressType.QZ.getValue())) {
                hospid = "qz_";
            } else if (code.getCodeTitle().equals(AddressType.ZZ.getValue())) {
                hospid = "zz_";
            } else if (code.getCodeTitle().equals(AddressType.PT.getValue())) {
                hospid = "pt_";
            } else if (code.getCodeTitle().equals(AddressType.NP.getValue())) {
                hospid = "np_";
            } else if (code.getCodeTitle().equals(AddressType.SM.getValue())) {
                hospid = "sm_";
            }
            if (StringUtils.isNoneBlank(hospid)) {
                orgid = hospid + orgid;
            }
        }
        CdUser user = null;
        String role = getRequest().getParameter("role");
        if (StringUtils.isNotBlank(role) && role.equals("admin")) {
            this.setIsSqyl("1");
            List<CdUser> ls = sysDao.getServiceDo().loadByPk(CdUser.class, "account", "admin");
            user = ls.get(0);
            this.getRequest().getSession(false).setAttribute(Constant.SESSION_ATTRIBUTE_LOGIN_STAFF, user);
            this.getRequest().getSession().setAttribute("UserId", user.getUserId());
            this.getRequest().getSession().setAttribute("DrId", user.getDrId());
            this.getRequest().getSession().setAttribute("UserName", user.getUserName());
            this.getRequest().getSession().setAttribute("HospAreaCode", linkman);
            this.getRequest().getSession(false).setAttribute(Constant.ORG_ID, user.getHospId());
            this.setUsname(user.getAccount());
            this.setUs(user);
            return "admin";
        } else {
            // 加密orgCode
            user = sysDao.getUserDo().findUserOrgid(account, orgid);
        }
        // CdUser user = sysDao.getUserDo().findByUserName(account);
        if (user == null) {
            AppDrUser drUser = sysDao.getAppDrUserDao().findByUserWebOrg(account, orgid);
            if (drUser == null) {
                this.setMsg("该用户不存在或密码错误，请重新输入！");
                return "error";
            } else {
                if ("0".equals(drUser.getDrWebState())) {
                    this.setMsg("该用户不存在或密码错误，请重新输入！");
                    return "error";
                } else {
                    this.getRequest().getSession().setAttribute("DrTypeRole", drUser.getDrTypeRole() != null ? drUser.getDrTypeRole() : "");
                    CdUser cdUser = new CdUser();
                    cdUser.setAccount(drUser.getDrAccount());
                    cdUser.setCdDpetId(drUser.getDrHospId());
                    cdUser.setHospId(drUser.getDrHospId());
                    cdUser.setUserPassword(drUser.getDrPwd());
                    cdUser.setUserName(drUser.getDrName());
                    cdUser.setDrId(drUser.getId());
                    AppHospDept dept = (AppHospDept) sysDao.getServiceDo().find(AppHospDept.class, drUser.getDrHospId());

                    if (dept != null) {
                        String areaCode = AreaUtils.getAreaCode(dept.getHospAreaCode());
                        WebCdDept cdDept = (WebCdDept) sysDao.getServiceDo().find(WebCdDept.class, dept.getId());
                        if (cdDept == null) {
                            cdDept = new WebCdDept();
                            cdDept.setCjsj(new Date());
                            cdDept.setArea(dept.getHospAreaCode());
                            cdDept.setId(dept.getId());
                            cdDept.setDeptType("0");
                            sysDao.getServiceDo().add(cdDept);
                        }
                        // 不删原有权限，添加新权限
                        String[] roleId = sysDao.getUserDo().getRoleIdd(areaCode, drUser.getDrRole(), null);
                        if (roleId.length > 0) {
                            cdUser.setRoleid(roleId);
                        }
                        // 每次登入根据app医生权限赋值web权限
                        /*List<CdRole> listRole = sysDao.getUserDo().getRoleList(areaCode, drUser.getDrRole());
                        if (listRole != null && listRole.size() > 0) {
                            cdUser.setRole(listRole);
                        }

                        if (areaCode.length() == 4) {
                            String[] roleId = new String[]{AppRoleType.SHI.getValue()};
                            cdUser.setRoleid(roleId);
                        } else {
                            String[] roleId = new String[]{AppRoleType.SHEQU.getValue()};
                            cdUser.setRoleid(roleId);
                        }*/
                        cdUser.setCdDpetId(cdDept.getId());
                        sysDao.getServiceDo().add(cdUser);
                    }
                    user = cdUser;
                    // 登录时session增加drRole,add by WangCheng
                    this.getRequest().getSession().setAttribute("drRole", drUser.getDrRole() != null ? drUser.getDrRole() : "");
                    // cdUser.setRoleid(AppRoleType.SHENG.getValue());
                }
            }
        } else {
            if (StringUtils.isNotBlank(user.getDrId())) {
                AppDrUser drUser = (AppDrUser) sysDao.getServiceDo().find(AppDrUser.class, user.getDrId());
                if (drUser != null) {
                    if (StringUtils.isNotBlank(drUser.getDrHospId())) {
                        AppHospDept dept = (AppHospDept) sysDao.getServiceDo().find(AppHospDept.class, drUser.getDrHospId());
                        if (dept != null) {
                            String roleStr = "";
                            if (user.getRoleid().length > 0) {
                                for (String roles : user.getRoleid()) {
                                    if (StringUtils.isBlank(roleStr)) {
                                        roleStr = roles;
                                    } else {
                                        roleStr += ";" + roles;
                                    }
                                }
                            }
                            String areaCode = AreaUtils.getAreaCode(dept.getHospAreaCode());
                            // 不删原有权限，添加新权限
                            String[] roleIds = sysDao.getUserDo().getRoleIdd(areaCode, drUser.getDrRole(), roleStr);
                            if (roleIds.length > 0) {
                                user.setRoleid(roleIds);
                                sysDao.getServiceDo().modify(user);
                            }
                            // 每次登陆根据app医生权限赋值web权限
                            /*List<CdRole> listRole = sysDao.getUserDo().getRoleList(areaCode, drUser.getDrRole());
                            if (listRole != null && listRole.size() > 0) {
                                user.setRole(listRole);
                                sysDao.getServiceDo().modify(user);
                            }*/
                        }
                    }
                    // 登录时session增加drRole,add by WangCheng
                    this.getRequest().getSession().setAttribute("drRole", drUser.getDrRole() != null ? drUser.getDrRole() : "");
                    this.getRequest().getSession().setAttribute("DrTypeRole", drUser.getDrTypeRole() != null ? drUser.getDrTypeRole() : "");
                }
            }
        }

        if (StringUtils.isNotBlank(user.getHospId())) {
            AppHospDept dept = (AppHospDept) sysDao.getServiceDo().find(AppHospDept.class, user.getHospId());
            this.getRequest().getSession().setAttribute("HospLabelState", dept.getLabelState());
            this.getRequest().getSession().setAttribute("HospAreaCode", dept.getHospCityareaId());
            this.getRequest().getSession().setAttribute("upHospAreaCode", dept.getHospUpcityareaId());
            this.getRequest().getSession().setAttribute("HospName", dept.getHospName());
            if (StringUtils.isNotBlank(dept.getHospCityareaId())) {
                CdCode codeOne = sysDao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_JDSOURCETYPE, AreaUtils.getAreaCode(dept.getHospCityareaId(), "2"));
                if (codeOne != null) {
                    this.getRequest().getSession().setAttribute("jdSourceType", codeOne.getCodeTitle());
                }
            }
            String type = "0";
            if (AreaUtils.getAreaCode(dept.getHospAreaCode()).length() > 6) {
                type = "1";
            }
            this.getRequest().getSession().setAttribute("HospDissoluation", type);
            if (StringUtils.isNotBlank(dept.getHospLevel())) {
                this.getRequest().getSession().setAttribute("HospLevel", dept.getHospLevel());
            } else {
                CdAddress address = sysDao.getCdAddressDao().findByCacheCode(dept.getHospAreaCode());
                if (address != null) {
                    this.getRequest().getSession().setAttribute("HospLevel", address.getLevel());
                }
            }
        }

        // 设置医生所属团队ID
        List<AppTeamMember> teamMembers = sysDao.getServiceDo().loadByPk(AppTeamMember.class, "memDrId", user.getDrId());
        if (teamMembers != null && teamMembers.size() > 0) {
            this.getRequest().getSession().setAttribute("teamId", teamMembers.get(0).getMemTeamid());
        }

        this.getRequest().getSession(false).setAttribute(Constant.SESSION_ATTRIBUTE_LOGIN_STAFF, user);
        this.getRequest().getSession().setAttribute("UserId", user.getUserId());
        this.getRequest().getSession().setAttribute("DrId", user.getDrId());
        this.getRequest().getSession().setAttribute("UserName", user.getUserName());
        // 读卡所需参数
        String icklb0 = getRequest().getParameter("icklb0"); //使用读卡卡具型号
        this.getRequest().getSession().setAttribute("dkqlx", icklb0);

        String ickdk0 = getRequest().getParameter("ickdk0"); //读卡卡具端口
        this.getRequest().getSession().setAttribute("dkqdk", ickdk0);

        // String linkman = getRequest().getParameter("linkman"); //
        this.getRequest().getSession().setAttribute("dqbm", linkman); //地区编码

        String shortname = getRequest().getParameter("shortname"); //网点
        this.getRequest().getSession().setAttribute("wdbm", shortname);

        String xuuser = getRequest().getParameter("xuuser"); //新农合
        this.getRequest().getSession().setAttribute("xuuser", xuuser);
        String xpaw = getRequest().getParameter("xpaw");
        this.getRequest().getSession().setAttribute("xpaw", xpaw);

        String yuuser = getRequest().getParameter("yuuser"); //医保
        this.getRequest().getSession().setAttribute("yuuser", yuuser);
        String ypaw = getRequest().getParameter("ypaw");
        this.getRequest().getSession().setAttribute("ypaw", ypaw);
        // 设置单位id
        this.getRequest().getSession(false).setAttribute(Constant.ORG_ID, user.getHospId());

        this.setUsname(user.getAccount());
        this.setUs(user);
        return this.indexJw();
    }

    // 查菜单到页面
    public String indexJw() {
        try{
// 获取用户
            CdUser user = (CdUser) this.getRequest().getSession(true).getAttribute(Constant.SESSION_ATTRIBUTE_LOGIN_STAFF);
            if (user == null) {
                return com.opensymphony.xwork2.Action.LOGIN;
            }
            List<CdMenuSon> son = new ArrayList<CdMenuSon>();
            if (user.getRole() != null && user.getRole().size() > 0) {
                String[] rids = new String[user.getRole().size()];
                for (int i = 0; i < user.getRole().size(); i++) {
                    rids[i] = user.getRole().get(i).getId();
                }
                if (rids != null && rids.length > 0) {
                    menuList = sysDao.getMenuDo().findByRoId(rids);
                    List<CdMenuSon> sonList = this.sysDao.getSonDo().findSonRoId(rids);
                    for (CdMenuSon i : sonList) {
                        son.add(i);
                    }
                }
            }
            if (user.getCdDept() != null) {
                if (user.getCdDept().getRoles() != null && user.getCdDept().getRoles().size() > 0) {
                    String[] rids = new String[user.getCdDept().getRoles().size()];
                    for (int i = 0; i < user.getCdDept().getRoles().size(); i++) {
                        rids[i] = user.getCdDept().getRoles().get(i).getId();
                    }
                    if (rids != null && rids.length > 0) {
                        List<CdMenuSon> sonList = this.sysDao.getSonDo().findSonRoId(rids);
                        for (CdMenuSon i : sonList) {
                            son.add(i);
                        }
                    }
                }
            }
            menulist = this.getSysDao().getMenuDo().selectAll();
            // 控制按钮的集合
            this.getRequest().getSession(false).setAttribute(Constant.SESSION_SON, son);
            getRequest().setAttribute("menuList", menuList);
            this.newMsgJson(this.finalSuccessrMsg);
            return this.indexCmm();
        }catch (Exception e){
            e.printStackTrace();
            this.newMsgJson(this.finalErrorMsg);
            return this.indexCmm();
        }
    }

    public String getIsSqyl() {
        return isSqyl;
    }

    public void setIsSqyl(String isSqyl) {
        this.isSqyl = isSqyl;
    }

    /*public String[] getRoleIdd(String areaCode, String drRole, String roleStr) {
        String[] roleId = new String[]{};
        if (StringUtils.isBlank(roleStr)) {
            if (areaCode.length() == 4) {
                roleId = new String[]{AppRoleType.SHI.getValue()};
            } else {
                if (StringUtils.isNotBlank(drRole)) {
                    if (drRole.indexOf(AppRoleType.QU.getValue()) != -1 || drRole.indexOf(AppRoleType.SHEQU.getValue()) != -1) {
                        roleId = new String[]{AppRoleType.SHEQU.getValue(), AppRoleType.JDLK.getValue()};
                    } else {
                        roleId = new String[]{AppRoleType.SHEQU.getValue()};
                    }
                } else {
                    roleId = new String[]{AppRoleType.SHEQU.getValue()};
                }
            }
        } else {
            if (areaCode.length() == 4) {
                if (roleStr.indexOf(AppRoleType.SHI.getValue()) == -1) {
                    roleId = new String[]{AppRoleType.SHI.getValue()};
                }
            } else {
                if (StringUtils.isNotBlank(drRole)) {
                    if (drRole.indexOf(AppRoleType.QU.getValue()) != -1 || drRole.indexOf(AppRoleType.SHEQU.getValue()) != -1) {
                        if (roleStr.indexOf(AppRoleType.SHEQU.getValue()) == -1 && roleStr.indexOf(AppRoleType.JDLK.getValue()) == -1) {
                            roleId = new String[]{AppRoleType.SHEQU.getValue(), AppRoleType.JDLK.getValue()};
                        } else if (roleStr.indexOf(AppRoleType.SHEQU.getValue()) == -1) {
                            roleId = new String[]{AppRoleType.SHEQU.getValue()};
                        } else if (roleStr.indexOf(AppRoleType.JDLK.getValue()) == -1) {
                            roleId = new String[]{AppRoleType.JDLK.getValue()};
                        }
                    }
                } else {
                    if (roleStr.indexOf(AppRoleType.SHEQU.getValue()) == -1) {
                        roleId = new String[]{AppRoleType.SHEQU.getValue()};
                    }
                }
            }
        }
        return roleId;
    }*/
}
