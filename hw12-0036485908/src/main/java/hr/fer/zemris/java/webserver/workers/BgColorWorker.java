package hr.fer.zemris.java.webserver.workers;

import hr.fer.zemris.java.webserver.IWebWorker;
import hr.fer.zemris.java.webserver.RequestContext;

public class BgColorWorker implements IWebWorker{
	private static final String DEFAULT_BGCOLOR="7F7F7F";

	@Override
	public void processRequest(RequestContext context) throws Exception {
		String bgColor = context.getPersistentParameter("bgcolor");
		if(bgColor != null) {
			context.setTemporaryParameter("background", new String(bgColor));
			
		} else {
			context.setTemporaryParameter("background", new String(bgColor));
		}
		
		context.getDispatcher().dispatchRequest("/private/home.smscr");
	}

}
