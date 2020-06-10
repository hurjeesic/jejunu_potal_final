package kr.ac.jejunu;

import javax.servlet.*;
import java.io.IOException;

public class MyFilter implements Filter {
	@Override
	public void init(FilterConfig filterConfig) {
		System.out.println("********************** filter init **********************");
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		System.out.println("********************** filter before **********************");
		request.setCharacterEncoding("UTF-8");
		chain.doFilter(request, response);
		System.out.println("********************** filter after **********************");
	}

	@Override
	public void destroy() {
		System.out.println("********************** filter destroy **********************");
	}
}
