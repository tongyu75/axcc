package com.axcc.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * Created by tongshiru on 2018/10/02.
 */
@Order(1)
//重点
@WebFilter(filterName = "CORSFilter", urlPatterns = "/*")
public class CORSFilter implements Filter
{
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
		HttpServletResponse response = (HttpServletResponse) res;
		HttpServletRequest request = (HttpServletRequest) req;
		String originUrl = request.getHeader("origin");
		// 正常情况下"Access-Control-Allow-Origin"设为“*”就可以，而现在设置成originUrl是因为了配合"Access-Control-Allow-Credentials"
		response.setHeader("Access-Control-Allow-Origin", originUrl);
		response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
		response.setHeader("Access-Control-Max-Age", "3600");
		response.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept, Authorization, Pragma, Cache-Control, Expires");
		// SessionFilter时返回header值，如果不加这个，前端页面无法获取header值
		response.setHeader("Access-Control-Expose-Headers", "sessionstatus");
		// ajax跨域请求，sessionId会不是同一个，所以加上下面的配置，这样sessionId就会相同（如果加下面的配置，那么上面的"Access-Control-Allow-Origin"值不能设置成“*”）
		response.addHeader("Access-Control-Allow-Credentials", "true");
		chain.doFilter(req, res);
	}

	public void init(FilterConfig filterConfig) {}

	public void destroy() {}
}
