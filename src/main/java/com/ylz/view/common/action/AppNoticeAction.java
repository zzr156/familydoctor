package com.ylz.view.common.action;

import com.ylz.bizDo.app.po.AppNotice;
import com.ylz.bizDo.app.po.AppPatientUser;
import com.ylz.bizDo.jtapp.commonEntity.AppNoticeCountEntity;
import com.ylz.bizDo.jtapp.commonEntity.AppNoticeListEntity;
import com.ylz.bizDo.jtapp.commonEntity.NoticeViewEntity;
import com.ylz.bizDo.jtapp.commonVo.AppNoticeCountQvo;
import com.ylz.packaccede.common.action.CommonAction;
import com.ylz.packcommon.common.comEnum.NoticesReadType;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;

import java.util.List;

@SuppressWarnings("all")
@Action(
        value="appNotice",
        results={
                @Result(name = "ajson", type = "json",params={"root","ajson","contentType", "application/json"})
        }
)
public class AppNoticeAction extends CommonAction{

    /**
     *未阅读条数
     * @return
     */
    public String appUnReadNoticeCount(){
        try{
            AppNoticeCountQvo qvo = (AppNoticeCountQvo)this.getAppJson(AppNoticeCountQvo.class);
            if(qvo != null){
//                Map<String,Object> map = new HashMap<String,Object>();
//                List<AppNoticeCountEntity> ls = this.getSysDao().getAppNoticeDao().findUnReadNoticeCount(qvo.getDrPatientId());
                String type = "1";//患者
                AppPatientUser patientUser = this.getAppPatientUser();
                if(patientUser == null){
                    type = "2";//医生
                }
                List<AppNoticeCountEntity> ls = this.getSysDao().getAppNoticeDao().findReadNoticeCount(qvo.getDrPatientId(),type);
//                this.getAjson().setMap(map);
                if(ls != null && ls.size() >0){
                    this.getAjson().setRows(ls);
                }else{
                    this.getAjson().setMsg("暂无数据");
                }
            }else{
                this.getAjson().setMsgCode("900");
                this.getAjson().setMsg("参数格式错误");
            }
        }catch (Exception e){
            e.printStackTrace();
            this.getAjson().setMsgCode("900");
            this.getAjson().setMsg("系统错误,请联系管理员");
        }
        return "ajson";
    }

    /**
     * 查询消息列表
     * @return
     */
    public String appNoticeTypeList(){
        try{
            AppNoticeCountQvo qvo = (AppNoticeCountQvo)this.getAppJson(AppNoticeCountQvo.class);
            if(qvo != null){
                List<AppNoticeListEntity> ls = this.getSysDao().getAppNoticeDao().findNoticeList(qvo);
                this.getAjson().setQvo(qvo);
                this.getAjson().setRows(ls);
            }else{
                this.getAjson().setMsgCode("900");
                this.getAjson().setMsg("参数格式错误");
            }
        }catch (Exception e){
            e.printStackTrace();
            this.getAjson().setMsgCode("900");
            this.getAjson().setMsg("系统错误,请联系管理员");
        }
        return "ajson";
    }

    /**
     * 查询消息列表(包括内容）
     * @param drPatientId --医生主键或患者主键
     * @param noticeType --消息类型
     * @return
     */
    public String appFullNotice(){
        try{
            AppNoticeCountQvo qvo = (AppNoticeCountQvo)this.getAppJson(AppNoticeCountQvo.class);
            if(qvo != null){
                List<AppNotice> ls = this.getSysDao().getAppNoticeDao().findNoticeAll(qvo);
                this.getAjson().setRows(ls);
                this.getAjson().setMsgCode("800");
                this.getAjson().setQvo(qvo);
            }else{
                this.getAjson().setMsgCode("900");
                this.getAjson().setMsg("参数格式错误");
            }
        }catch (Exception e){
            e.printStackTrace();
            this.getAjson().setMsgCode("900");
            this.getAjson().setMsg("系统错误,请联系管理员");
        }
        return "ajson";
    }

    /**
     * 查看详细信息
     * @return
     */
    public String appReadDetailed(){
        try{
            AppNoticeCountQvo qvo = (AppNoticeCountQvo)this.getAppJson(AppNoticeCountQvo.class);
            if(qvo != null){
                NoticeViewEntity v =  this.getSysDao().getAppNoticeDao().findNoticeView(qvo.getNoticdId());
                if(v != null){
                    AppNotice p = (AppNotice)this.getSysDao().getServiceDo().find(AppNotice.class,qvo.getNoticdId());
                    p.setNoticeRead(NoticesReadType.YIDU.getValue());
                    this.getSysDao().getServiceDo().modify(p);
                    this.getAjson().setVo(v);
                }else{
                    this.getAjson().setMsgCode("900");
                    this.getAjson().setMsg("查无数据");
                }
            }else{
                this.getAjson().setMsgCode("900");
                this.getAjson().setMsg("参数格式错误");
            }
        }catch (Exception e){
            e.printStackTrace();
            this.getAjson().setMsgCode("900");
            this.getAjson().setMsg("系统错误,请联系管理员");
        }
        return "ajson";
    }

    /**
     * 消息设置为已读
     * @return
     */
    public String appReadState(){
        try{
            AppNoticeCountQvo qvo = (AppNoticeCountQvo)this.getAppJson(AppNoticeCountQvo.class);
            if(qvo != null){
              this.getSysDao().getAppNoticeDao().updateNoticeRead(qvo.getDrPatientId(),qvo.getNoticeType());
            }else{
                this.getAjson().setMsgCode("900");
                this.getAjson().setMsg("参数格式错误");
            }
        }catch (Exception e){
            e.printStackTrace();
            this.getAjson().setMsgCode("900");
            this.getAjson().setMsg("系统错误,请联系管理员");
        }
        return "ajson";
    }
    //患者登入时，查消息表做消息插入
    public String addNotice(){
        try{
            AppNoticeCountQvo qvo = (AppNoticeCountQvo)this.getAppJson(AppNoticeCountQvo.class);
            if(qvo==null){
                this.getAjson().setMsg("参数格式错误");
                this.getAjson().setMsgCode("900");
            }else{
                sysDao.getAppNoticeDao().addNoticeTg(qvo);
            }
        }catch (Exception e){
            e.printStackTrace();
            this.getAjson().setMsgCode("900");
            this.getAjson().setMsg(e.getMessage());
        }
        return "ajson";
    }





}
