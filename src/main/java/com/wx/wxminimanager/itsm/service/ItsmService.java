package com.wx.wxminimanager.itsm.service;

import com.wx.wxminimanager.itsm.model.ItsmModel;


/**
 *
 * 
 * @version 1.0 2020年3月27日
 * @author yantao
 */

public interface ItsmService {
	
	public boolean add(ItsmModel itsm);
	
	public ItsmModel[] selectlist();
	
}
