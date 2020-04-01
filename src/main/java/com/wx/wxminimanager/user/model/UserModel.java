package com.wx.wxminimanager.user.model;

/**
 *
 * 
 * @version 1.0 2020年3月27日
 * @author yantao
 */

public class UserModel {
	
	private String userid;
	
	private String username;
	
	private String password;
	
	private String chinesename;
	
	private String groupid;
	
	private String this_time;

    private String last_time;
    

    public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getChinesename() {
		return chinesename;
	}

	public void setChinesename(String chinesename) {
		this.chinesename = chinesename;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getGroupid() {
		return groupid;
	}

	public void setGroupid(String groupid) {
		this.groupid = groupid;
	}

	public String getThis_time() {
		return this_time;
	}

	public void setThis_time(String this_time) {
		this.this_time = this_time;
	}

	public String getLast_time() {
		return last_time;
	}

	public void setLast_time(String last_time) {
		this.last_time = last_time;
	}

}
