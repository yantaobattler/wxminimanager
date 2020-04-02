package com.wx.wxminimanager.user.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.wx.wxminimanager.tools.ControllerTools;
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
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	private ControllerTools tools = new ControllerTools();

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
		
		if (!tools.encodePwd(user.getPassword()).equals(user_in_db.getPassword())) {
			return 2;
		} 
		if (tools.encodePwd(user.getPassword()).equals(user_in_db.getPassword())) {
			
			loginUser(session, user_in_db);
			return 0;
			
		}
		
		return 9;
	}

	@Override
	public Map<String, String> changePassword(UserModel user, String old_password, String new_password) {
		
		Map<String, String> rsp_map = new HashMap<String, String>();
		
		if (!tools.encodePwd(old_password).equals(user.getPassword())) {
			
			rsp_map.put("rsp_code", "000002");
			rsp_map.put("rsp_msg", "密码不正确!");
			logger.info("userid: {}, password update failed, older is wrong...", user.getUserid());
			return rsp_map;
			
		} else {
			
			UserModel newuser = new UserModel();
			newuser.setUserid(user.getUserid());
			newuser.setPassword(tools.encodePwd(new_password));
			
			int i = dao.updateUser(newuser);
			logger.info("userid: {}, password updated {} row", user.getUserid(), i);
			
			rsp_map.put("rsp_code", "000000");
			rsp_map.put("rsp_msg", "密码更新成功!");
			return rsp_map;
			
		}

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
