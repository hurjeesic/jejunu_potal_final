package kr.ac.jejunu.web.sequence;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Controller;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import java.io.IOException;

@Controller("/myServlet")
@WebServlet(urlPatterns = "/hello")
public class MyServlet extends GenericServlet {
	@Override
	public void destroy() {
		System.out.println("*********************** destroy ***********************");
		super.destroy();
	}

	@Override
	public void init(ServletConfig config) throws ServletException {
		System.out.println("*********************** init ***********************");
//		bean 등록 방법 -> ApplicationContext 에서 package 정의 후 class 반영
//		ApplicationContext applicationContext = new AnnotationConfigApplicationContext("kr.ac.jejunu");
//		this.dao = applicationContext.getBean("beanName", classNAme.class);

		super.init(config);
	}

	@Override
	public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
		System.out.println("*********************** service ***********************");
	}
}
