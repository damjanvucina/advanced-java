package hr.fer.zemris.java.webserver.workers;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import hr.fer.zemris.java.webserver.IWebWorker;
import hr.fer.zemris.java.webserver.RequestContext;

public class BgColorWorker implements IWebWorker {
	private static final String BGCOLOR = "bgcolor";
	private static final String HEX_VALIDATOR = "\\p{XDigit}{6}";
	Pattern pattern = Pattern.compile(HEX_VALIDATOR);
	Matcher matcher;

	@Override
	public void processRequest(RequestContext context) throws Exception {
		System.out.println("usa san");

		String color = context.getParameter(BGCOLOR);
		if (color != null && isColorRepresentation(color)) {
			System.out.println("boja promjenjena");
			context.setPersistentParameter(BGCOLOR, color);
			context.getDispatcher().dispatchRequest("/index2.html");
		} else {
			System.out.println("boja nije promjenjena");
		}
	}

	private boolean isColorRepresentation(String color) {
		matcher = pattern.matcher(color);

		return matcher.matches();
	}

}
