package com.ylz.bizDo.app.dao.impl;

import com.ylz.bizDo.app.dao.AppAgreementDao;
import com.ylz.bizDo.app.po.*;
import com.ylz.bizDo.app.vo.AppAgreementQvo;
import com.ylz.bizDo.cd.po.CdAddress;
import com.ylz.bizDo.cd.po.CdCode;
import com.ylz.packaccede.allDo.SysDao;
import com.ylz.packcommon.common.CodeGroupConstrats;
import com.ylz.packcommon.common.comEnum.*;
import com.ylz.packcommon.common.util.AreaUtils;
import com.ylz.packcommon.common.util.ExtendDate;
import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("appAgreementDao")
@Transactional(rollbackForClassName={"Exception"})
public class AppAgreementDaoImpl implements AppAgreementDao {

    @Autowired
    private SysDao sysDao;


    @Override
    public List<AppAgreement> findListQvo(AppAgreementQvo qvo)  throws Exception{
        Map<String,Object> map = new HashMap<String,Object>();
        String sql = "SELECT a.* FROM APP_AGREEMENT a ";

        if(StringUtils.isNotBlank(qvo.getOrgName())){
            map.put("orgName","%"+qvo.getOrgName()+"%");
            sql += "INNER JOIN APP_HOSP_DEPT b ON a.MENT_HOSP_ID = b.ID WHERE 1=1 AND b.HOSP_NAME LIKE :orgName ";
        }else{
            sql += " WHERE 1=1 ";
        }
        if(StringUtils.isNotBlank(qvo.getAreaCity())){
            map.put("mentCity", qvo.getAreaCity());
            sql += " AND a.MENT_CITY = :mentCity ";
        }

        return sysDao.getServiceDo().findSqlMap(sql, map, AppAgreement.class, qvo);
    }


    @Override
    public AppAgreement findByCityId(String patientCity) throws Exception {
        return  (AppAgreement) sysDao.getServiceDo().getSessionFactory().getCurrentSession()
                .createCriteria(AppAgreement.class)
                .add(Restrictions.eq("mentCityId", patientCity))
                .add(Restrictions.eq("mentUseType", CommonUseType.XITONG.getValue()))
                .add(Restrictions.eq("mentState", CommonEnable.QIYONG.getValue()))
                .uniqueResult();
    }

    @Override
    public AppAgreement findByHospId(String hospId, String qyType)  throws Exception{
        return  (AppAgreement) sysDao.getServiceDo().getSessionFactory().getCurrentSession()
                .createCriteria(AppAgreement.class)
                .add(Restrictions.eq("mentHospId", hospId))
                .add(Restrictions.eq("mentType",qyType))
                .add(Restrictions.eq("mentUseType", CommonUseType.GEREN.getValue()))
                .add(Restrictions.eq("mentState", CommonEnable.QIYONG.getValue()))
                .uniqueResult();
    }

    @Override
    public List<AppAgreement> findListHosp(AppAgreementQvo qvo) throws Exception {
        Map<String,Object> map = new HashMap<String,Object>();
        String sql = "SELECT * FROM APP_AGREEMENT a WHERE 1=1  ";
        if(StringUtils.isNotBlank(qvo.getHospId())){
            map.put("hospid", qvo.getHospId());
            sql += " AND a.MENT_HOSP_ID  = :hospid ";
        }
        return sysDao.getServiceDo().findSqlMap(sql, map, AppAgreement.class, qvo);
    }

    @Override
    public List<AppAgreement> findEnabled(AppAgreement qvo) throws Exception {
        StringBuilder hql = new StringBuilder();
        hql.append("from AppAgreement where mentState='1' and mentType='1' and mentUseType='2'");
        hql.append(" and mentHospId = :mentHospId");
        HashMap map =new HashMap();
        map.put("mentHospId",qvo.getMentHospId());
        List<AppAgreement> ls = sysDao.getServiceDo().findHqlMap(hql.toString(),map);
        return ls;
    }


    public AppAgreement findState(String hospId)throws Exception
    {

        Map<String,Object> map = new HashMap<String,Object>();
        String sql = "SELECT * FROM APP_AGREEMENT a WHERE 1=1  and a.MENT_STATE ='1'  ";
        if(StringUtils.isNotBlank(hospId)){
            map.put("hospid", hospId);
            sql += " AND a.MENT_HOSP_ID  = :hospid ";
        }
        List<AppAgreement> ls =  sysDao.getServiceDo().findSqlMap(sql, map, AppAgreement.class);
        if(ls !=null && ls.size()>0){
            return ls.get(0);
        }
        return null;
    }

    @Override
    public String findContentByIdno(String idno) throws Exception {
        AppPatientUser patientUser = sysDao.getAppPatientUserDao().findPatientIdNo(idno);
        AppSignForm form = sysDao.getAppSignformDao().findSignFormByUserState(patientUser.getId());
        String content = "";
        if(form != null){
            String addrxx = "";
            String xyGroup = "";
            if(StringUtils.isBlank(form.getSignTeamId())){
                return "";
            }
            AppTeam team = (AppTeam)sysDao.getServiceDo().find(AppTeam.class,form.getSignTeamId());
            if(team == null){
                return "";
            }
            if(StringUtils.isBlank(form.getSignDrId())){
                return "";
            }
            AppDrUser user =(AppDrUser)sysDao.getServiceDo().find(AppDrUser.class,form.getSignDrId());
            if(user == null){
                return "";
            }
            if(StringUtils.isNotBlank(form.getSignHospId())){
                AppHospDept dept = (AppHospDept)sysDao.getServiceDo().find(AppHospDept.class,form.getSignHospId());
                if(dept != null){
                    if("350702".equals(AreaUtils.getAreaCode(dept.getHospAreaCode(),"3"))){
                        xyGroup = CodeGroupConstrats.GROUP_YPXY;
                    }else{
                        xyGroup = CodeGroupConstrats.GROUP_NPXY;
                    }
                    if("4".equals(dept.getHospLevel())){//街道级查机构上一级区级地址
                        CdAddress adrs = (CdAddress)sysDao.getServiceDo().find(CdAddress.class,dept.getHospUpcityareaId());
                        if(adrs != null){
                            addrxx = adrs.getAreaSname();
                        }
                    }else if("3".equals(dept.getHospLevel())){//区级直接查取本区级地址
                        CdAddress adrs = (CdAddress)sysDao.getServiceDo().find(CdAddress.class,dept.getHospAreaCode());
                        if(adrs != null){
                            addrxx = adrs.getAreaSname();
                        }
                    }else if("5".equals(dept.getHospLevel())){
                        CdAddress adrs = (CdAddress)sysDao.getServiceDo().find(CdAddress.class,AreaUtils.getAreaCode(dept.getHospAreaCode(),"3")+"000000");
                        if(adrs != null){
                            addrxx = adrs.getAreaSname();
                        }
                    }
                    CdAddress address = (CdAddress) sysDao.getServiceDo().find(CdAddress.class, dept.getHospUpcityareaId());
                    AppAgreement v = sysDao.getAppAgreementDao().findByHospId(form.getSignHospId(), "1");
                    content = v.getMentContent();
                    String patientName = "";
                    if (StringUtils.isNotBlank(patientUser.getPatientName())) {
                        patientName = patientUser.getPatientName();
                    }
                    String patientIdno = "";
                    if (StringUtils.isNotBlank(patientUser.getPatientIdno())) {
                        patientIdno = patientUser.getPatientIdno();
                    }
                    String patientAddress = "";
                    if (patientUser != null) {
                        if(StringUtils.isNotBlank(patientUser.getPatientProvince())){
                            CdAddress provience = (CdAddress) sysDao.getServiceDo().find(CdAddress.class, patientUser.getPatientProvince());
                            if(provience != null) {
                                patientAddress += provience.getAreaSname();
                            }
                        }
                        if(StringUtils.isNotBlank(patientUser.getPatientCity())){
                            CdAddress city = (CdAddress) sysDao.getServiceDo().find(CdAddress.class, patientUser.getPatientCity());
                            if(city != null) {
                                patientAddress += city.getAreaSname();
                            }
                        }
                        if(StringUtils.isNotBlank(patientUser.getPatientArea())){
                            CdAddress area = (CdAddress) sysDao.getServiceDo().find(CdAddress.class, patientUser.getPatientArea());
                            if(area != null) {
                                patientAddress += area.getAreaSname();
                            }
                        }
                        if(StringUtils.isNotBlank(patientUser.getPatientStreet())){
                            CdAddress street = (CdAddress) sysDao.getServiceDo().find(CdAddress.class, patientUser.getPatientStreet());
                            if(street != null) {
                                patientAddress += street.getAreaSname();
                            }
                        }
                        if(StringUtils.isNotBlank(patientUser.getPatientNeighborhoodCommittee())){
                            CdAddress committee = (CdAddress) sysDao.getServiceDo().find(CdAddress.class, patientUser.getPatientNeighborhoodCommittee());
                            if(committee != null) {
                                patientAddress += committee.getAreaSname();
                            }
                        }
                        if(StringUtils.isNotBlank(patientUser.getPatientAddress())){
                            patientAddress += patientUser.getPatientAddress();
                        }
                    }
                    String patientTel = "";
                    if (StringUtils.isNotBlank(patientUser.getPatientTel())) {
                        patientTel = patientUser.getPatientTel();
                    }
                    String areaSname = "";
                    if (StringUtils.isNotBlank(address.getAreaSname())) {
                        areaSname = address.getAreaSname();
                    }
                    String hospName = "";
                    if (StringUtils.isNotBlank(dept.getHospName())) {
                        hospName = dept.getHospName();
                    }
                    String teamName = "";
                    if (StringUtils.isNotBlank(team.getTeamName())) {
                        teamName = team.getTeamName();
                    }
                    String drAssistantName="";
                    String drAssistantTel="";
                    if(StringUtils.isNotBlank(form.getSignDrAssistantId())) {
                        AppDrUser user1 = (AppDrUser)sysDao.getServiceDo().find(AppDrUser.class,form.getSignDrAssistantId());
                        if(user!=null) {
                            if(StringUtils.isNotBlank(user1.getDrName())){
                                drAssistantName = user1.getDrName();
                            }
                            if(StringUtils.isNotBlank(user1.getDrTel())){
                                drAssistantTel = user1.getDrTel();
                            }
                        }
                    }
                    String drTel = "";
                    if (StringUtils.isNotBlank(user.getDrTel())) {
                        drTel = user.getDrTel();
                    }
                    String hospTel = "";
                    if (StringUtils.isNotBlank(dept.getHospTel())) {
                        hospTel = dept.getHospTel();
                    }
                    String patientCard = "";
                    if (StringUtils.isNotBlank(patientUser.getPatientCard())) {
                        patientCard = patientUser.getPatientCard();
                    }
                    String patientjmda="";
                    if(StringUtils.isNotBlank(patientUser.getPatientjmda())){
                        patientjmda= patientUser.getPatientjmda();
                    }

                    content = content.replace("{01}", patientjmda);
                    content = content.replace("{7}", drAssistantName);
                    content = content.replace("{8}", drAssistantTel);
                    content = content.replace("{1}", patientName);
                    content = content.replace("{2}", patientIdno);
                    content = content.replace("{3}", patientAddress);
                    content = content.replace("{4}", patientTel);
                    content = content.replace("{5}", areaSname);
                    content = content.replace("{6}", hospName);
//                                content = content.replace("{8}", drTel);
                    content = content.replace("{9}", hospTel);
                    content = content.replace("{31}", patientCard);
                    drTel = user.getDrTel();
                    String drName = user.getDrName();
                    if(StringUtils.isNotBlank(drName)) {
                        content = content.replace("{02}", drName);
                    }
                    if(StringUtils.isNotBlank(drTel)){
                        content = content.replace("{03}", drTel);
                    }else {
                        content = content.replace("{02}", "");
                        content = content.replace("{03}", "");
                    }
                    if(form.getSignFromDate() != null){
                        content = content.replace("{10}", ExtendDate.getYMD(form.getSignFromDate()));
                        content = content.replace("{11}", ExtendDate.getYMD(form.getSignToDate()));
                    }
                    if(StringUtils.isNotBlank(form.getSignNum())){
                        content = content.replace("{30}", form.getSignNum());
                    }
                    content = content.replace("{23}", teamName);
                    List<AppLabelGroup> ls = sysDao.getAppLabelGroupDao().findBySignGroup(form.getId(),"3");
                    String ypxy = "";
//                    boolean flag = false;
//                    boolean flags = false;
                    List<AppLabelEconomics> lsE = sysDao.getAppLabelGroupDao().findBySignEconomics(form.getId(),"4");
                    if(lsE != null && lsE.size()>0){
                        for(AppLabelEconomics p:lsE){
                           /* if(!EconomicType.YBRK.getValue().equals(p.getLabelValue())){
                                flags = true;
                            }*/
                            if(EconomicType.JDLKPKRK.getValue().equals(p.getLabelValue())){//建档立卡人口
                                CdCode code = sysDao.getCodeDao().findCodeGroupValue(xyGroup,"10");
                                if(code!=null){
                                    ypxy += code.getCodeTitle();
                                }
                            }
                        }
                    }
                    if(ls != null && ls.size() >0){
                        for(AppLabelGroup p : ls){
                            if(ResidentMangeType.PTRQ.getValue().equals(p.getLabelValue())){//普通人群
                                content = content.replace("{12}", "☑");//建立健康档案
                                content = content.replace("{13}", "☑");//健康教育
                                content = content.replace("{22}", "☑");//其它国家规定免费提供的基本公共卫生服务项目
                                CdCode code = sysDao.getCodeDao().findCodeGroupValue(xyGroup,p.getLabelValue());
                                if(code!=null){
                                    ypxy += code.getCodeTitle();
                                }
                            }
                            if(ResidentMangeType.ETLZLS.getValue().equals(p.getLabelValue())){//儿童(0~6岁)
                                content = content.replace("{12}", "☑");//建立健康档案
                                content = content.replace("{13}", "☑");//健康教育
                                content = content.replace("{14}", "☑");//为0-6岁儿童进行一类疫苗接种
                                content = content.replace("{15}", "☑");//为0-6岁儿童进行常规的体格检查
                                content = content.replace("{20}", "☑");//为老年人提供中医药健康管理服务，同时在儿童不同年龄阶段对儿童家长进行儿童中医药健康指导
                                content = content.replace("{22}", "☑");//其它国家规定免费提供的基本公共卫生服务项目
                                CdCode code = sysDao.getCodeDao().findCodeGroupValue(xyGroup,p.getLabelValue());
                                if(code!=null){
                                    ypxy += code.getCodeTitle();
                                }

                            }
                            if(ResidentMangeType.YCF.getValue().equals(p.getLabelValue())){//孕产妇
                                content = content.replace("{12}", "☑");//建立健康档案
                                content = content.replace("{13}", "☑");//健康教育
                                content = content.replace("{16}", "☑");//为孕产妇孕期进行基本保健指导，开展至少5次孕期保健服务和2次产后访视
                                content = content.replace("{22}", "☑");//其它国家规定免费提供的基本公共卫生服务项目
                                CdCode code = sysDao.getCodeDao().findCodeGroupValue(xyGroup,p.getLabelValue());
                                if(code!=null){
                                    ypxy += code.getCodeTitle();
                                }
                            }
                            if(ResidentMangeType.LNR.getValue().equals(p.getLabelValue())){//老年人
                                content = content.replace("{12}", "☑");//建立健康档案
                                content = content.replace("{13}", "☑");//健康教育
                                content = content.replace("{17}", "☑");//为65岁以上老年人每年1次健康体检，包括血生化、血尿常规、心电图、B超等辅助检查
                                content = content.replace("{20}", "☑");//为老年人提供中医药健康管理服务，同时在儿童不同年龄阶段对儿童家长进行儿童中医药健康指导
                                content = content.replace("{22}", "☑");//其它国家规定免费提供的基本公共卫生服务项目
                                CdCode code = sysDao.getCodeDao().findCodeGroupValue(xyGroup,p.getLabelValue());
                                if(code!=null){
                                    ypxy += code.getCodeTitle();
                                }
                            }
                            if(ResidentMangeType.GXY.getValue().equals(p.getLabelValue())){//高血压
                                content = content.replace("{12}", "☑");//建立健康档案
                                content = content.replace("{13}", "☑");//健康教育
                                content = content.replace("{18}", "☑");//为原发性高血压、2型糖尿病等慢性病患者提供定期随访、用药指导，每年免费体检1次等服务
                                content = content.replace("{22}", "☑");//其它国家规定免费提供的基本公共卫生服务项目
                                CdCode code = sysDao.getCodeDao().findCodeGroupValue(xyGroup,p.getLabelValue());
                                if(code!=null){
                                    ypxy += code.getCodeTitle();
                                }
                            }
                            if(ResidentMangeType.TNB.getValue().equals(p.getLabelValue())){//糖尿病
                                content = content.replace("{12}", "☑");//建立健康档案
                                content = content.replace("{13}", "☑");//健康教育
                                content = content.replace("{18}", "☑");//为原发性高血压、2型糖尿病等慢性病患者提供定期随访、用药指导，每年免费体检1次等服务
                                content = content.replace("{22}", "☑");//其它国家规定免费提供的基本公共卫生服务项目
                                CdCode code = sysDao.getCodeDao().findCodeGroupValue(xyGroup,p.getLabelValue());
                                if(code!=null){
                                    ypxy += code.getCodeTitle();
                                }
                            }

                            if(ResidentMangeType.YZJSZY.getValue().equals(p.getLabelValue())){//严重精神障碍
                                content = content.replace("{12}", "☑");//建立健康档案
                                content = content.replace("{13}", "☑");//健康教育
                                content = content.replace("{19}", "☑");//对居家重性精神疾病患者提供随访服务和每年健康体检1次
                                content = content.replace("{22}", "☑");//其它国家规定免费提供的基本公共卫生服务项目
                                CdCode code = sysDao.getCodeDao().findCodeGroupValue(xyGroup,p.getLabelValue());
                                if(code!=null){
                                    ypxy += code.getCodeTitle();
                                }
                            }
                            if(ResidentMangeType.JHB.getValue().equals(p.getLabelValue())){//结核病
                                content = content.replace("{12}", "☑");//建立健康档案
                                content = content.replace("{13}", "☑");//健康教育
                                content = content.replace("{21}", "☑");//为结核病患者提供用药、饮食、运动、心理等健康指导
                                content = content.replace("{22}", "☑");//其它国家规定免费提供的基本公共卫生服务项目
                                CdCode code = sysDao.getCodeDao().findCodeGroupValue(xyGroup,p.getLabelValue());
                                if(code!=null){
                                    ypxy += code.getCodeTitle();
                                }
                            }
                            if(ResidentMangeType.CJR.getValue().equals(p.getLabelValue())){//残疾人
                                content = content.replace("{12}", "☑");//建立健康档案
                                content = content.replace("{13}", "☑");//健康教育
                                content = content.replace("{22}", "☑");//其它国家规定免费提供的基本公共卫生服务项目
                                CdCode code = sysDao.getCodeDao().findCodeGroupValue(xyGroup,p.getLabelValue());
                                if(code!=null){
                                    ypxy += code.getCodeTitle();
                                }
                            }
                            if(ResidentMangeType.WEIZHI.getValue().equals(p.getLabelValue())){//未知
                                content = content.replace("{12}", "");//建立健康档案
                                content = content.replace("{13}", "");//健康教育
                                content = content.replace("{14}", "");//为0-6岁儿童进行一类疫苗接种
                                content = content.replace("{15}", "");//为0-6岁儿童进行常规的体格检查
                                content = content.replace("{16}", "");//为孕产妇孕期进行基本保健指导，开展至少5次孕期保健服务和2次产后访视
                                content = content.replace("{17}", "");//为65岁以上老年人每年1次健康体检，包括血生化、血尿常规、心电图、B超等辅助检查
                                content = content.replace("{18}", "");//为原发性高血压、2型糖尿病等慢性病患者提供定期随访、用药指导，每年免费体检1次等服务
                                content = content.replace("{19}", "");//对居家重性精神疾病患者提供随访服务和每年健康体检1次
                                content = content.replace("{20}", "");//为老年人提供中医药健康管理服务，同时在儿童不同年龄阶段对儿童家长进行儿童中医药健康指导
                                content = content.replace("{21}", "");//为结核病患者提供用药、饮食、运动、心理等健康指导
                                content = content.replace("{22}", "");//其它国家规定免费提供的基本公共卫生服务项目
                            }
                        }
                        /*if(flag){
                            CdCode code = sysDao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_YPXY,"5");
                            if(code!=null){
                                ypxy += code.getCodeTitle();
                            }
                        }*/
                        content = content.replace("{12}", "");//建立健康档案
                        content = content.replace("{13}", "");//健康教育
                        content = content.replace("{14}", "");//为0-6岁儿童进行一类疫苗接种
                        content = content.replace("{15}", "");//为0-6岁儿童进行常规的体格检查
                        content = content.replace("{16}", "");//为孕产妇孕期进行基本保健指导，开展至少5次孕期保健服务和2次产后访视
                        content = content.replace("{17}", "");//为65岁以上老年人每年1次健康体检，包括血生化、血尿常规、心电图、B超等辅助检查
                        content = content.replace("{18}", "");//为原发性高血压、2型糖尿病等慢性病患者提供定期随访、用药指导，每年免费体检1次等服务
                        content = content.replace("{19}", "");//对居家重性精神疾病患者提供随访服务和每年健康体检1次
                        content = content.replace("{20}", "");//为老年人提供中医药健康管理服务，同时在儿童不同年龄阶段对儿童家长进行儿童中医药健康指导
                        content = content.replace("{21}", "");//为结核病患者提供用药、饮食、运动、心理等健康指导
                        content = content.replace("{22}", "");//其它国家规定免费提供的基本公共卫生服务项目

                        content = content.replace("{04}","");//页头
                        content = content.replace("{06}","");//页未
                        content = content.replace("{05}","");//页未
                    }
                    /*if(flags){
                        CdCode code = sysDao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_YPXY,"9");
                        if(code!=null){
                            ypxy += code.getCodeTitle();
                        }
                    }*/
                    content=content.replace("{98}",ypxy);
                    content=content.replace("{97}",addrxx);
                   /* if(StringUtils.isNotBlank(ypxy)){
                        AppHospDept hospDept = (AppHospDept)sysDao.getServiceDo().find(AppHospDept.class,form.getSignHospId());
                        if(hospDept!=null){
                            if(StringUtils.isNotBlank(hospDept.getHospAreaCode())){
                                String code = AreaUtils.getAreaCode(hospDept.getHospAreaCode(),"3");
                                if("350702".equals(code)){
                                    content=content.replace("{98}",ypxy);
                                }
                            }
                        }
                    }*/
                    if(StringUtils.isNotBlank(form.getUpHpis())){
                        if(SignFormType.POSSTATE.getValue().equals(form.getUpHpis())){
                            List<AppSignSubtable> list = sysDao.getAppSignSubtableDao().findBySign(form.getId(),form.getUpHpis(), CommSF.YES.getValue());
                            if(list!= null && list.size()>0){
                                String result = "<image width='100' height='100' src='"+list.get(0).getImgUrl()+"'></image>";
                                content = content.replace("{999}",result);//甲方签名
                            }
                        }
                    }else{
                        content = content.replace("{999}","");//甲方签名
                    }
                    if(StringUtils.isNotBlank(form.getSigntext())){ //不知道为什么这样写总觉得不太合理
                        String text="";
                        String[] a =form.getSigntext().split("\n");
                        for(int i=0;i<a.length;i++){
                            if(i==0){
                                text="<p style=\"text-indent:32px;line-height:27px\">"+a[i]+"</p>";
                            }else{
                                text=text+"<p style=\"text-indent:32px;line-height:27px\">"+a[i]+"</p>";
                            }
                        }
                        text = text.replace("null","");
                        content=content.replace("{0}",text);//特色补充协议包
                    }else{
                        content=content.replace("{0}","");//特色补充协议包
                    }

                }
            }
        }else{
            content=content.replace("{97}","");
            content=content.replace("{98}","");
        }
        return content;
    }
}

