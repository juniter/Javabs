package com.juniter.conf;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebFilter("/*")
public class CustomerHeaderFilter implements Filter {
	private static final Logger logger = LoggerFactory.getLogger(CustomerHeaderFilter.class);

	@Override
	public void destroy() {

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain filter)
			throws IOException, ServletException {
		logger.info("Customer Filter SetHeader。。。。");
		HttpServletResponse res = (HttpServletResponse) response;
		res.addHeader("Access-Control-Allow-Origin", "*");
		res.addHeader("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT");
		res.addHeader("Access-Control-Allow-Headers", "Content-Type");
		res.setCharacterEncoding("UTF-8");
		res.setHeader("Content-Type", "application/json;charset=UTF-8");
		filter.doFilter(request, response);
	}

	@Override
	public void init(FilterConfig conf) throws ServletException {

	}

}
