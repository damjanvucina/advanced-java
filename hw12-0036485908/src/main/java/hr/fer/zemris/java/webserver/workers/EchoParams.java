package hr.fer.zemris.java.webserver.workers;

import java.io.IOException;
import java.util.Map;

import hr.fer.zemris.java.webserver.IWebWorker;
import hr.fer.zemris.java.webserver.RequestContext;

public class EchoParams implements IWebWorker {

	@Override
	public void processRequest(RequestContext context) {
		context.setMimeType("text/html");
		try {
			context.write(
					"<html><body><table style=\"border: 1px solid black; border-collapse : collapse;\"");
			
			context.write("<tr><th style=\"border: 1px solid black ;\">Key</th><th style=\"border: 1px solid black ;\">Value</th></tr>");

			for (Map.Entry<String, String> entry : context.getParameters().entrySet()) {
				context.write("<tr>");

				context.write("<td style=\"border: 1px solid black ;\">");
				context.write(entry.getKey());
				context.write("</td>");

				context.write("<td style=\"border: 1px solid black ;\">");
				context.write(entry.getValue());
				context.write("</td>");

				context.write("</tr>");
			}

			context.write("</table></body></html>");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
