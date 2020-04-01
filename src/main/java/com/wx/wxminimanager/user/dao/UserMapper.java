package com.wx.wxminimanager.user.dao;

import org.apache.ibatis.annotations.Param;
import com.wx.wxminimanager.user.model.UserModel;

public interface UserMapper {
	
	public UserModel selectUserByUsername(@Param("username") String username);
	
	public boolean updateLogintime(@Param("username") String username, @Param("this_time") String this_time);
		
	public int addUser(UserModel user);
	
	public int updateUser(UserModel user);
	
	public int deleteUser(UserModel user);
	
}
