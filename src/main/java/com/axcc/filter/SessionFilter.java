package com.axcc.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by tongshiru on 2018/10/02.
 */
@Order(1)
//重点
@WebFilter(filterName = "SessionFilter", urlPatterns = "/*")
public class SessionFilter implements Filter {
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
		HttpSession session = request.getSession(false);
		String uri = request.getRequestURI();
		logger.info("filter url:", uri);
		String requestURI = request.getRequestURI();
		// 登陆页面不需要session判断
		if (requestURI.contains("/login")) {
			chain.doFilter(request, response);
		} else {
			// session中包含user对象,则是登录状态
			if(session!=null&&session.getAttribute("user") != null){
				chain.doFilter(request, response);
			}else{
				//chain.doFilter(request, response);
				// 超时标志
				response.setHeader("sessionstatus", "1");
				return;
				/*String path = request.getContextPath();
				// http://localhost:8080/
				String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
				response.sendRedirect(request.getContextPath()+"/carCollecting/webapp/html/loginAReg/login.html");*/

			}
		}
	}

	public void init(FilterConfig filterConfig) {}

	public void destroy() {}
}
