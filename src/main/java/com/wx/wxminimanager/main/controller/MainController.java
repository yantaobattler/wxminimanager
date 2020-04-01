package com.wx.wxminimanager.main.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wx.wxminimanager.user.model.UserModel;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Controller
@RequestMapping("")
public class MainController {
	
	private  final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@ModelAttribute
    public void common(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        
		//下面所有方法执行前都会先执行这个
		
		logger.info("================================");
		logger.info("=========check session==========");
		
		if (null == request.getSession().getAttribute("user")) {
			
			logger.info("=========no session=============");
			logger.info("return modelandview: /index");
			request.getRequestDispatcher(request.getContextPath()).forward(request, response);

		} 

		return ;
		
    }
    
	@RequestMapping("/main")
    public ModelAndView main_page(HttpServletRequest request) {	
    	    	    	    	
    	UserModel user = (UserModel) request.getSession().getAttribute("user");
    	
    	ModelAndView modelandview = new ModelAndView();
    	modelandview.addObject("user", user);
    	modelandview.setViewName("main");
    	
    	logger.info("userid: {}, request /main", user.getUserid());
    	logger.info("user: {}, login success", user.getChinesename());
    	logger.info("userid: {}, return modelandview: /main", user.getUserid());
    	
        return modelandview;
    }
	
	
	@RequestMapping("/aa")
    public ModelAndView index() {
    	
    	logger.info("=========request aa==========");
    	
    	ModelAndView modelandview = new ModelAndView();
    	modelandview.setViewName("login");
//    	modelandview.addObject(attributeName, attributeValue);
    	
    	logger.info("trans to view:[login]");
    	
        return modelandview;
    }
    
    @ResponseBody
    @RequestMapping("/textuser/login")
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
