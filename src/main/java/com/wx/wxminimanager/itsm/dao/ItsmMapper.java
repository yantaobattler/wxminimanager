package com.wx.wxminimanager.itsm.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.wx.wxminimanager.itsm.model.ItsmModel;


public interface ItsmMapper {
	
	public ItsmModel[] select();
		
	public int add(ItsmModel itsm);

	
}
