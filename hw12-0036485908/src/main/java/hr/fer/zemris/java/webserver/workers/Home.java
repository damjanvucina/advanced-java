package hr.fer.zemris.java.webserver.workers;

import hr.fer.zemris.java.webserver.IWebWorker;
import hr.fer.zemris.java.webserver.RequestContext;

/**
 * The class representing a worker used for setting the temporary color
 * parameters.
 * 
 * @author Damjan Vučina
 */
public class Home implements IWebWorker {

	/** The Constant DEFAULT_BGCOLOR. */
	private static final String DEFAULT_BGCOLOR = "7F7F7F";

	/**
	 * Method used for setting the temporary color parameters.
	 */
	@Override
	public void processRequest(RequestContext context) throws Exception {
		context.setMimeType("text/html");
		String bgColor = context.getPersistentParameter("bgcolor");
		if (bgColor != null) {
			context.setTemporaryParameter("background", new String(bgColor));

		} else {
			context.setTemporaryParameter("background", DEFAULT_BGCOLOR);
		}

		context.getDispatcher().dispatchRequest("/private/home.smscr");
	}

}
