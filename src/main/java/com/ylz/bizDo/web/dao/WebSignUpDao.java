package com.ylz.bizDo.web.dao;

import com.ylz.bizDo.app.po.AppServeSetmeal;
import com.ylz.bizDo.app.po.AppSignForm;
import com.ylz.bizDo.app.po.AppTeamMember;
import com.ylz.bizDo.web.po.WebDrUser;
import com.ylz.bizDo.web.po.WebHospDept;
import com.ylz.bizDo.web.po.WebTeam;
import com.ylz.bizDo.web.vo.WebServeMealVo;
import com.ylz.bizDo.web.vo.WebSignUpVo;

import java.util.List;

/**
 * 签约接口
 */
public interface WebSignUpDao {

    /**
     * 签约数据上传
     * @param vo
     * @return
     * @throws Exception
     */
    public AppSignForm webSignUp(WebSignUpVo vo) throws Exception;

    /**
     * 添加或修改医院机构
     * @param vo
     * @return
     * @throws Exception
     */
    public WebHospDept webSaveHosp(WebSignUpVo vo) throws Exception;

    /**
     * 添加或修改医生信息
     * @param vo
     * @return
     * @throws Exception
     */
    public WebDrUser webSaveDr(WebSignUpVo vo) throws Exception;

    /**
     * 添加或修改团队数据
     * @param vo
     * @return
     * @throws Exception
     */
    public WebTeam webSaveTeam(WebSignUpVo vo) throws Exception;

    /**
     * 添加或删除团队成员
     * @param vo
     * @return
     * @throws Exception
     */
    public AppTeamMember webSaveTeamM(WebSignUpVo vo) throws Exception;

    /**
     * 删除当前签约单
     * @param vo
     * @return
     * @throws Exception
     */
    public AppSignForm webDeleteSign(WebSignUpVo vo) throws Exception;

    /**
     * 修改签约单缴费状态
     * @param vo
     * @return
     * @throws Exception
     */
    public AppSignForm webModifySign(WebSignUpVo vo) throws Exception;

    /**
     * 签约单团队变更
     * @param vo
     * @return
     * @throws Exception
     */
    public AppSignForm webChangeSign(WebSignUpVo vo) throws Exception;

    public AppServeSetmeal webAddServeMeal(WebServeMealVo vo) throws Exception;

    /**
     * 转签数据上传
     * @param vo
     * @return
     * @throws Exception
     */
    public AppSignForm webGotoSignUp(WebSignUpVo vo) throws Exception;

    /**
     * 续签数据上传
     * @param vo
     * @return
     * @throws Exception
     */
    public AppSignForm webContinueSign(WebSignUpVo vo) throws Exception;

    /**
     * 盖瑞签约数据上传
     * @param qvo
     * @return
     * @throws Exception
     */
    public AppSignForm gairuiSignUp(WebSignUpVo qvo) throws Exception;
}
