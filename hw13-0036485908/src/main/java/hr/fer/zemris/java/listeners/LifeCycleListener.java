package hr.fer.zemris.java.listeners;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * The listener class responsible for generating a timestamp when the server
 * boots up. This timestamp provides user with the ability of checking the time
 * thah has elapsed since the server has last been down.
 *
 * @author Damjan Vučina
 */
@WebListener
public class LifeCycleListener implements ServletContextListener {

	/**
	 * Receives notification that the ServletContext is about to be shut down.
	 * NOTICE: not implemented here.
	 */
	@Override
	public void contextDestroyed(ServletContextEvent e) {
		// do nothing
	}

	/**
	 * After receiving notification that the web application initialization process
	 * is started, generates a timestamp that provides user with the ability of
	 * checking the time thah has elapsed since the server has last been down.
	 */
	@Override
	public void contextInitialized(ServletContextEvent e) {
		ServletContext context = e.getServletContext();
		context.setAttribute("birth", System.currentTimeMillis());
	}

}
