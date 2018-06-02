package hr.fer.zemris.java.webserver.workers;

import hr.fer.zemris.java.webserver.IWebWorker;
import hr.fer.zemris.java.webserver.RequestContext;

/**
 * The class representing a worker used calculating the sum of the given two
 * parameters and formatting the output in a table.
 * 
 * @author Damjan Vučina
 */
public class SumWorker implements IWebWorker {

	/** The Constant DEFAULT_A value. */
	private static final int DEFAULT_A = 1;

	/** The Constant DEFAULT_B value. */
	private static final int DEFAULT_B = 2;

	/** The Constant ADDITION_TAG. */
	private static final String ADDITION_TAG = "zbroj";

	/**
	 * Method responsible for calculating the sum of the given two parameters and
	 * formatting the output in a table.
	 */
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
