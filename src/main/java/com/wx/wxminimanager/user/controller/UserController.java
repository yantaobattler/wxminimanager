package com.wx.wxminimanager.user.controller;

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
@RequestMapping("/user")
public class UserController {
	
	private  final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Resource
	private UserService userservice;
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
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(HttpServletRequest request) {
		
		logger.info("======request /user/login=======");
		
		Map<String, String> rsp_map = new HashMap<String, String>();
		
		UserModel user = new UserModel();
		user.setUsername(request.getParameter("username"));
		user.setPassword(request.getParameter("password"));
		HttpSession session = request.getSession();
		
		int checkflg = userservice.checkUser(user, session);
		
		if (1 == checkflg) {
			
			rsp_code = "000001";
			rsp_msg  = "用户名不存在!";
			
		} else if (2 == checkflg){
			
			rsp_code = "000002";
			rsp_msg  = "密码不正确!";
			
		} else if (0 == checkflg){
			
			rsp_code = "000000";
			rsp_msg  = "登陆成功!";		
			next     = "/main";
			
		} else {
			
			rsp_code = "999999";
			rsp_msg  = "异常错误!";
			
		}
		
		rsp_map.put("rsp_code", rsp_code);
		rsp_map.put("rsp_msg", rsp_msg);
		rsp_map.put("next", next);
		
		Gson gson = new Gson();
		String rsp_string = gson.toJson(rsp_map);
		
		logger.info("======response /user/login↓↓↓===");
		logger.info("return JSON {}", rsp_string);
		logger.info("======response /user/login↑↑↑===");
		
		return rsp_string;
	}
	
	
	@RequestMapping(value="/logout")
	public ModelAndView logout(HttpServletRequest request) {
		
		ModelAndView modelandview = new ModelAndView();
		
		if (!islogin) {
			modelandview.setViewName("index");
			logger.info("request /user/logout, but no session, return modelandview: /index");
			return modelandview;
		}
		
		UserModel user = (UserModel) request.getSession().getAttribute("user");
    	logger.info("userid: {}, request /user/logout", user.getUserid());
    	
		userservice.logoutUser(request.getSession());
		
		logger.info("return modelandview: /index");
				
		modelandview.setViewName("/index");
		
		return modelandview;
		
	}
	

	@RequestMapping("/chgpwd_page")
    public ModelAndView chgpwd_page(HttpServletRequest request) {
    	
        ModelAndView modelandview = new ModelAndView();
		
		if (!islogin) {
			modelandview.setViewName("index");
			logger.info("request /user/chgpwd_page, but no session, return modelandview: /index");
			return modelandview;
		}
		
		UserModel user = (UserModel) request.getSession().getAttribute("user");
    	logger.info("userid: {}, request /user/chgpwd_page", user.getUserid());
		
		logger.info("userid: {}, return modelandview: /user/chgpwd_page", user.getUserid());
				
		modelandview.setViewName("/user/chgpwd_page");
		
		return modelandview;
    }
	
	
	@ResponseBody
	@RequestMapping(value = "/chgpwd", method = RequestMethod.POST)
    public String chgpwd(HttpServletRequest request) {
		
		UserModel user = (UserModel) request.getSession().getAttribute("user");
		
		logger.info("userid: {}, request /user/chgpwd", user.getUserid());
			
		String old_pass = request.getParameter("old_pass");
		String pass = request.getParameter("pass");
		
		Map<String, String> rsp_map = userservice.changePassword(user, old_pass, pass);
		
		Gson gson = new Gson();
		String rsp_string = gson.toJson(rsp_map);
		
		logger.info("userid: {}, password update success", user.getUserid());
		logger.info("userid: {}, response /user/chgpwd↓↓↓", user.getUserid());
		logger.info("return JSON {}", rsp_string);
		logger.info("userid: {}, response /user/chgpwd↑↑↑", user.getUserid());
		
		request.getSession().removeAttribute("user");
		logger.info("=======remove Session======");
		
		return rsp_string;

    }
    
	
	@RequestMapping("/admin-list")
    public ModelAndView adminlist_page(HttpServletRequest request) {
    	
        ModelAndView modelandview = new ModelAndView();
		
		if (!islogin) {
			modelandview.setViewName("index");
			logger.info("request /user/admin-list, but no session, return modelandview: /index");
			return modelandview;
		}
		
		UserModel user = (UserModel) request.getSession().getAttribute("user");
    	logger.info("userid: {}, request /user/admin-list", user.getUserid());
		
		logger.info("userid: {}, return modelandview: /user/admin-list", user.getUserid());
				
		modelandview.setViewName("/user/admin-list");
		
		return modelandview;
    }
	
	
	@RequestMapping("/admin-rule")
    public ModelAndView adminrule_page(HttpServletRequest request) {
    	
        ModelAndView modelandview = new ModelAndView();
		
		if (!islogin) {
			modelandview.setViewName("index");
			logger.info("request /user/admin-rule, but no session, return modelandview: /index");
			return modelandview;
		}
		
		UserModel user = (UserModel) request.getSession().getAttribute("user");
    	logger.info("userid: {}, request /user/admin-rule", user.getUserid());
		
		logger.info("userid: {}, return modelandview: /user/admin-rule", user.getUserid());
				
		modelandview.setViewName("/user/admin-rule");
		
		return modelandview;
    }
	
	
	@ResponseBody
	@RequestMapping("/111111login")
    public ModelAndView index() {
    	
    	logger.info("=========request aa==========");
    	
    	ModelAndView modelandview = new ModelAndView();
    	modelandview.setViewName("login");
//    	modelandview.addObject(attributeName, attributeValue);
    	
    	logger.info("trans to view:[login]");
    	
        return modelandview;
    }
    
    @ResponseBody
    @RequestMapping("/user111111/login")
    public String login(HttpServletRequest request, HttpServletResponse response, Model model) throws JsonProcessingException {
    	
    	logger.info("=========request /user/login==========");
    	String username=request.getParameter("username");
        String password=request.getParameter("password");
        logger.info("{}==={}===", username,password);
        
        Map<String, String> rsp_map = new HashMap<String, String>();
        rsp_map.put("msg1", "haha1");
        rsp_map.put("msg2", "haha2");
        
        ObjectMapper mapper = new ObjectMapper();
        String rsp_jsonString = mapper.writerWithDefaultPrettyPrinter()
        		.writeValueAsString(rsp_map);
        
        logger.info("rsp_jsonString==={}", rsp_jsonString);

    	
    	
        return rsp_jsonString;
    }
    

}
