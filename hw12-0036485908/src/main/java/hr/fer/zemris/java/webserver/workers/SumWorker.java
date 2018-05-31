package hr.fer.zemris.java.webserver.workers;

import hr.fer.zemris.java.webserver.IWebWorker;
import hr.fer.zemris.java.webserver.RequestContext;

public class SumWorker implements IWebWorker {
	private static final int DEFAULT_A = 1;
	private static final int DEFAULT_B = 2;
	private static final String ADDITION_TAG = "zbroj";

	@Override
	public void processRequest(RequestContext context) {
		int a = 0;
		int b = 0;
		boolean parsedA = false;
		boolean parsedB = false;

		try {
			a = Integer.parseInt(context.getParameter("a"));
			parsedA = true;
		} catch (NumberFormatException | NullPointerException ignore) {
		}
		try {
			b = Integer.parseInt(context.getParameter("b"));
			parsedB = true;
		} catch (NumberFormatException | NullPointerException ignore) {
		}

		if (!parsedA) {
			a = DEFAULT_A;
		}
		if (!parsedB) {
			b = DEFAULT_B;
		}

		context.setTemporaryParameter("a", String.valueOf(a));
		context.setTemporaryParameter("b", String.valueOf(b));
		context.setTemporaryParameter(ADDITION_TAG, String.valueOf(a + b));

		try {
			context.getDispatcher().dispatchRequest("/private/calc.smscr");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
