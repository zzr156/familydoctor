package com.ylz.webservices.security;

public enum WebUser {
	
	WEB_ADMIN("admin","abcd","1"),
	WEB_HZK("hzk","a","1");
	
	private String name;
	private String pwd;
	private String state;
	WebUser(String name, String pwd, String state)
	{
		this.name = name;
		this.pwd = pwd;
		this.state = state;
	}
	
	public static String getPwd(String s)
	{
		for (WebUser f : WebUser.values())
		{
			if (f.getName().equals(s)) {
                return f.pwd;
            }
		}
		return null;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	
	
}
