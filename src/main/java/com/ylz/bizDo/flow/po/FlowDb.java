package com.ylz.bizDo.flow.po;

/**
 * Created by PC on 2015/11/24
 * ����ʵ��.
 */
public class FlowDb {
    private String tid;//task id
    private String eid;//EXECUTION_ID_
    private String pnid;//PROC_INST_ID_
    private String pid;//PROC_DEF_ID_
    private String flowname;//���̲�������
    private String typename;//������������

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }

    public String getEid() {
        return eid;
    }

    public void setEid(String eid) {
        this.eid = eid;
    }

    public String getPnid() {
        return pnid;
    }

    public void setPnid(String pnid) {
        this.pnid = pnid;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getFlowname() {
        return flowname;
    }

    public void setFlowname(String flowname) {
        this.flowname = flowname;
    }

    public String getTypename() {
        return typename;
    }

    public void setTypename(String typename) {
        this.typename = typename;
    }
}
