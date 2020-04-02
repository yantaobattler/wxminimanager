package com.wx.wxminimanager.tools;

import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * 
 * @version 1.0 2020年4月1日
 * @author yantao
 */

public class ControllerTools {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

    
	
	/**
	 *  
	 * @param request
	 * @param response
	 * @param sessionname
	 * @return
	 */
	public boolean hasSession(HttpServletRequest request, String sessionname){
		
		logger.info("checking session...");
		
		if (null == request.getSession().getAttribute(sessionname)) {
			
			logger.info("no session {}", sessionname);
			return false;
//			request.getRequestDispatcher(request.getContextPath() + forward).forward(request, response);

		} else {
			
			logger.info("has session {}", sessionname);
			return false;
		
		}
    }
	
	
	public String encodePwd(String pwd) {
		
		//密码加密方法
		String newpwd = pwd+pwd;
		
		return newpwd;
		
	}
}
