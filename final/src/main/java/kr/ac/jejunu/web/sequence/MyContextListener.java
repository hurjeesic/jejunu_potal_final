package kr.ac.jejunu.web.sequence;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class MyContextListener implements ServletContextListener {
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		System.out.println("********************** context init **********************");
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		System.out.println("********************** context destroy **********************");
	}
}
