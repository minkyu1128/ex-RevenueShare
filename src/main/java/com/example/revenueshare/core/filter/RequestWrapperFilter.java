package com.example.revenueshare.core.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class RequestWrapperFilter implements Filter {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		RequestWrapper readableRequestWrapper = new RequestWrapper((HttpServletRequest) request);	//RequestWrapper 클래스로 wrapping
		chain.doFilter(readableRequestWrapper, response);
	}

}
