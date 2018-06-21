package hr.fer.zemris.java.tecaj_13.web.init;

import hr.fer.zemris.java.tecaj_13.dao.jpa.JPAEMFProvider;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * The Listener class used for setting up the application
 * 
 * @author Damjan Vučina
 */
@WebListener
public class Inicijalizacija implements ServletContextListener {

	/**
	 * Receives notification that the web application initialization process is
	 * starting. Sets entity manager factory object as attribute.
	 */
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("baza.podataka.za.blog");
		sce.getServletContext().setAttribute("my.application.emf", emf);
		JPAEMFProvider.setEmf(emf);
	}

	/**
	 * Receives notification that the ServletContext is about to be shut down.
	 * Closes entity manager factory object.
	 */
	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		JPAEMFProvider.setEmf(null);
		EntityManagerFactory emf = (EntityManagerFactory) sce.getServletContext().getAttribute("my.application.emf");
		if (emf != null) {
			emf.close();
		}
	}
}