package com.ylz.packcommon.common.util;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 14-10-12.
 */
public class JsonList {
    private int total=12;
    private List rows;
    private String msg;
    private Object vo;
    private String result;
    private Object qvo;
    private Map<String, Object> map;
    private String code;//代码 //默认800成功 900失败

    public Object getVo() {
        return vo;
    }

    public void setVo(Object vo) {
        this.vo = vo;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
    public List getRows() {
        return rows;
    }

    public void setRows(List rows) {
        this.rows = rows;
    }

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public Object getQvo() {
		return qvo;
	}

	public void setQvo(Object qvo) {
		this.qvo = qvo;
	}

    public void setRowsQvo(List rows,Object qvo) {
        this.rows = rows;
        this.qvo=qvo;
    }

	public Map<String, Object> getMap() {
		return map;
	}

	public void setMap(Map<String, Object> map) {
		this.map = map;
	}

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
