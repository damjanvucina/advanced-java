package hr.fer.zemris.java.webserver;

/**
 * The interface that defines a method used for processing the user's request
 * within the current context.
 * 
 * @author Damjan Vučina
 */
public interface IWebWorker {

	/**
	 * Process request.
	 *
	 * @param context
	 *            the context
	 * @throws Exception
	 *             the exception
	 */
	void processRequest(RequestContext context) throws Exception;
}
