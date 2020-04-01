package com.wx.wxminimanager.user.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.wx.wxminimanager.user.dao.UserMapper;
import com.wx.wxminimanager.user.model.UserModel;
import com.wx.wxminimanager.user.service.UserService;

/**
 *
 * 
 * @version 1.0 2020年3月27日
 * @author yantao
 */

@Service("userService")
public class UserServiceImpl implements UserService {
	
	private  final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Resource
	private UserMapper dao;

	@Override
	public int checkUser(UserModel user, HttpSession session) {
		
		UserModel user_in_db = dao.selectUserByUsername(user.getUsername());
		
		if (null == user_in_db) {
			return 1;
		} 
				
		logger.debug("user:{}/{}...", user.getUsername(), user.getPassword());
		logger.debug("user_in_db:{}/{}...", user_in_db.getUsername(), user_in_db.getPassword());
		
		if (!user.getPassword().equals(user_in_db.getPassword())) {
			return 2;
		} 
		if (user.getPassword().equals(user_in_db.getPassword())) {
			
			loginUser(session, user_in_db);
			return 0;
			
		}
		
		return 9;
	}

	@Override
	public boolean changePassword(UserModel user, String new_password) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void loginUser(HttpSession session, UserModel user) {
		
		SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String this_time = dateformat.format(new Date());

		dao.updateLogintime(user.getUsername(), this_time);
		session.setAttribute("user", user);

	}

	@Override
	public void logoutUser(HttpSession session) {
		
		session.removeAttribute("user");
		logger.info("=======logout successfully======");

	}

	@Override
	public boolean addUser(UserModel user) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteUser(UserModel[] user) {
		// TODO Auto-generated method stub
		return false;
	}

}
