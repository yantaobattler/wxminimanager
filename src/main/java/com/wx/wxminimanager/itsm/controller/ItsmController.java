package com.wx.wxminimanager.itsm.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.wx.wxminimanager.itsm.model.ItsmModel;
import com.wx.wxminimanager.itsm.service.ItsmService;
import com.wx.wxminimanager.itsm.service.impl.ItsmServiceImpl;
import com.wx.wxminimanager.user.model.UserModel;
import com.wx.wxminimanager.user.service.UserService;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
*
* 
* @version 1.0 2020年3月27日
* @author yantao
*/

@Controller
@RequestMapping("/itsm")
public class ItsmController {
	
	private  final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Resource
	private ItsmService itsmservice;
	private boolean islogin;
	String rsp_code, rsp_msg, next = "";

	
	@ModelAttribute
    public void hasSession(HttpServletRequest request){
		if (null == request.getSession().getAttribute("user")) {
			islogin = false;
//			request.getRequestDispatcher(request.getContextPath() + forward).forward(request, response);
		} else {
			islogin = true;
		}
	}
	
	
	@ResponseBody
	@RequestMapping(value = "/add")
	public String login(HttpServletRequest request) {
		
		Gson gson = new Gson();
		
		Map<String, String> rsp_map = new HashMap<String, String>();
		
		ItsmModel itsm = new ItsmModel();
		itsm.setTopic(request.getParameter("topic"));
		itsm.setText(request.getParameter("text"));
		itsm.setDate(request.getParameter("date"));
		
		logger.info("add itsm: {}", gson.toJson(itsm));
		
		boolean checkflg = itsmservice.add(itsm);
		
		if (checkflg){
			
			rsp_code = "000000";	
			
		} else {
			
			rsp_code = "999999";
			rsp_msg  = "异常错误!";
			
		}
		
		rsp_map.put("rsp_code", rsp_code);
		rsp_map.put("rsp_msg", rsp_msg);
		
		
		String rsp_string = gson.toJson(rsp_map);
		logger.info("return {}", rsp_string);
		return rsp_string;
	}
	
	
	@ResponseBody
	@RequestMapping(value="/select")
	public String select(HttpServletRequest request) {
				
//		UserModel user = (UserModel) request.getSession().getAttribute("user");
//    	logger.info("userid: {}, request /itsm/select", user.getUserid());
    	
		ItsmModel[] itsmlist = itsmservice.selectlist();
		
		Map<String, Object> rsp_map = new HashMap<String, Object>();
		Gson gson = new Gson();
		
		rsp_map.put("code", "0");
		rsp_map.put("data", itsmlist);
		
		String rsp_string = gson.toJson(rsp_map);
		
		logger.info("return {}", rsp_string);
		
		return rsp_string;
		
	}
	

	@RequestMapping("/select_page")
    public ModelAndView chgpwd_page(HttpServletRequest request) {
    	
        ModelAndView modelandview = new ModelAndView();
		
		if (!islogin) {
			modelandview.setViewName("index");
			logger.info("request /itsm/select_page, but no session, return modelandview: /index");
			return modelandview;
		}
		
		UserModel user = (UserModel) request.getSession().getAttribute("user");
    	logger.info("userid: {}, request /itsm/select_page", user.getUserid());
		
		logger.info("userid: {}, return modelandview: /itsm/select_page", user.getUserid());
				
		modelandview.setViewName("itsm/select_page");
		
		return modelandview;
    }
	

}
