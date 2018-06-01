package hr.fer.zemris.java.webserver.workers;

import java.io.IOException;
import java.util.Map;

import hr.fer.zemris.java.webserver.IWebWorker;
import hr.fer.zemris.java.webserver.RequestContext;

public class Home implements IWebWorker{
	private static final String DEFAULT_BGCOLOR="7F7F7F";
	
	@Override
	public void processRequest(RequestContext context) throws Exception {
		context.setMimeType("text/html");
		String bgColor = context.getPersistentParameter("bgcolor");
		if(bgColor != null) {
			context.setTemporaryParameter("background", new String(bgColor));
			
		} else {
			context.setTemporaryParameter("background", DEFAULT_BGCOLOR);
		}
		
		context.getDispatcher().dispatchRequest("/private/home.smscr");
	}
	
}
