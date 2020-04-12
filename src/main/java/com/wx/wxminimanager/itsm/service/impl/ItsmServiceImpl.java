package com.wx.wxminimanager.itsm.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.wx.wxminimanager.itsm.dao.ItsmMapper;
import com.wx.wxminimanager.itsm.model.ItsmModel;
import com.wx.wxminimanager.itsm.service.ItsmService;
import com.wx.wxminimanager.tools.ControllerTools;


/**
 *
 * 
 * @version 1.0 2020年3月27日
 * @author yantao
 */

@Service("itsmService")
public class ItsmServiceImpl implements ItsmService {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	private ControllerTools tools = new ControllerTools();

	@Resource
	private ItsmMapper dao;

	@Override
	public boolean add(ItsmModel itsm) {

		if (itsm.getText()  == null &&
			itsm.getTopic() == null &&
			itsm.getDate()  == null) {
			logger.info("add itsm is null");
			return false;
		}


		int r = dao.add(itsm);
		
		if (r == 1) {
			return true;
		} else {
			return false;
		}
		
	}

	@Override
	public ItsmModel[] selectlist() {
		
		ItsmModel[] list = dao.select();
		
		return list;
	}

}
