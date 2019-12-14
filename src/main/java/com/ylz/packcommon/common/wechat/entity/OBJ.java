package com.ylz.packcommon.common.wechat.entity;

import java.io.Serializable;

public class OBJ implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private String name;
	private String num;
	private String state;
	private String time;
	private String path;
	private String content;
	private String type;
	private String other;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getNum() {
		return num;
	}
	public void setNum(String num) {
		this.num = num;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getOther() {
		return other;
	}
	public void setOther(String other) {
		this.other = other;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public OBJ() {
		super();
		// TODO Auto-generated constructor stub
	}
	public OBJ(String id, String name, String num, String state, String time,
			String path, String content, String type, String other) {
		super();
		this.id = id;
		this.name = name;
		this.num = num;
		this.state = state;
		this.time = time;
		this.path = path;
		this.content = content;
		this.type = type;
		this.other = other;
	}
	
	
}
