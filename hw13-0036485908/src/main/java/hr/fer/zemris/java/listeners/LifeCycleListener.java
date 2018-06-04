package hr.fer.zemris.java.listeners;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class LifeCycleListener implements ServletContextListener {

	@Override
	public void contextDestroyed(ServletContextEvent e) {
		//do nothing
	}

	@Override
	public void contextInitialized(ServletContextEvent e) {
		ServletContext context = e.getServletContext();
		context.setAttribute("birth", System.currentTimeMillis());
	}

}
