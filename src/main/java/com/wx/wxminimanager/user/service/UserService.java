package com.wx.wxminimanager.user.service;

import javax.servlet.http.HttpSession;

import com.wx.wxminimanager.user.model.UserModel;

/**
 *
 * 
 * @version 1.0 2020年3月27日
 * @author yantao
 */

public interface UserService {
	
	public boolean addUser(UserModel user);
	
	public boolean deleteUser(UserModel[] user);
	
	public int checkUser(UserModel user, HttpSession session);
    
    public boolean changePassword(UserModel user, String new_password);
	
    public void loginUser(HttpSession session, UserModel user);
	
    public void logoutUser(HttpSession session);

}
