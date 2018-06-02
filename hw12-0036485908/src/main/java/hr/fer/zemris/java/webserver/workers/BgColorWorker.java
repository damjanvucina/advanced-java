package hr.fer.zemris.java.webserver.workers;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import hr.fer.zemris.java.webserver.IWebWorker;
import hr.fer.zemris.java.webserver.RequestContext;

/**
 * The class responsible for changing the color of the browser's window.
 * 
 * @author Damjan Vučina
 */
public class BgColorWorker implements IWebWorker {

	/** The Constant BGCOLOR. */
	private static final String BGCOLOR = "bgcolor";

	/** The Constant HEX_VALIDATOR. */
	private static final String HEX_VALIDATOR = "\\p{XDigit}{6}";

	/** The pattern. */
	Pattern pattern = Pattern.compile(HEX_VALIDATOR);

	/** The matcher. */
	Matcher matcher;

	/**
	 * Changes the color of the browser's window.
	 */
	@Override
	public void processRequest(RequestContext context) throws Exception {
		String color = context.getParameter(BGCOLOR);
		if (color != null && isColorRepresentation(color)) {
			context.setPersistentParameter(BGCOLOR, color);
			context.getDispatcher().dispatchRequest("/index2.html");
		}
	}

	/**
	 * Checks if the given string is color representation, meaning it consist of 6
	 * hexadecimal digits.
	 *
	 * @param color
	 *            the color
	 * @return true, if is color representation
	 */
	private boolean isColorRepresentation(String color) {
		matcher = pattern.matcher(color);

		return matcher.matches();
	}

}
