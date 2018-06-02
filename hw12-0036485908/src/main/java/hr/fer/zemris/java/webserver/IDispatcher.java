package hr.fer.zemris.java.webserver;

/**
 * The interface defining a method for redirecting requests.
 * 
 * @author Damjan Vučina
 */
public interface IDispatcher {

	/**
	 * Dispatches request.
	 *
	 * @param urlPath
	 *            the url path
	 * @throws Exception
	 *             the exception
	 */
	void dispatchRequest(String urlPath) throws Exception;
}
