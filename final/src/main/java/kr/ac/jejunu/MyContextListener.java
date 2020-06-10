package kr.ac.jejunu;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

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
