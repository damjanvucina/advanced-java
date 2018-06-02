package hr.fer.zemris.java.webserver;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * The Class class that defines the current web context, i.e. charset, encoding,
 * mimeytpe etc. It also defines maps used for storing persistent and temporary
 * parameters as well streams for communication with the user.
 * 
 * @author Damjan Vučina
 */
public class RequestContext {

	/** The Constant DEFAULT_ENCODING. */
	public static final String DEFAULT_ENCODING = "UTF-8";

	/** The Constant DEFAULT_STATUS_CODE. */
	public static final int DEFAULT_STATUS_CODE = 200;

	/** The Constant DEFAULT_STATUS_TEXT. */
	public static final String DEFAULT_STATUS_TEXT = "OK";

	/** The Constant DEFAULT_MIME_TYPE. */
	public static final String DEFAULT_MIME_TYPE = "text/html";

	/** The output stream. */
	private OutputStream outputStream;

	/** The charset. */
	private Charset charset;

	/** The encoding. */
	private String encoding;

	/** The status code. */
	private int statusCode;

	/** The status text. */
	private String statusText;

	/** The mime type. */
	private String mimeType;

	/** The parameters. */
	private Map<String, String> parameters;

	/** The temporary parameters. */
	private Map<String, String> temporaryParameters;

	/** The persistent parameters. */
	private Map<String, String> persistentParameters;

	/** The output cookies. */
	private List<RCCookie> outputCookies;

	/** The header generated. */
	private boolean headerGenerated;

	/** The dispatcher. */
	private IDispatcher dispatcher;

	/**
	 * Gets the dispatcher.
	 *
	 * @return the dispatcher
	 */
	public IDispatcher getDispatcher() {
		return dispatcher;
	}

	/**
	 * Instantiates a new request context.
	 *
	 * @param outputStream
	 *            the output stream
	 * @param parameters
	 *            the parameters
	 * @param persistentParameters
	 *            the persistent parameters
	 * @param outputCookies
	 *            the output cookies
	 */
	//@formatter:off
	public RequestContext(
			OutputStream outputStream, 
			Map<String,String> parameters, 
			Map<String,String> persistentParameters, 
			List<RCCookie> outputCookies) {
		
		Objects.requireNonNull(outputStream, "Output stream cannot be null.");
		
		this.outputStream = outputStream;
		this.parameters = (parameters != null) ? parameters : new HashMap<>();
		this.persistentParameters = (parameters != null) ? persistentParameters : new HashMap<>();
		this.outputCookies = (parameters != null) ? outputCookies : new ArrayList<>();
		
		this.temporaryParameters = new HashMap<>();
		
		encoding = DEFAULT_ENCODING;
		statusCode = DEFAULT_STATUS_CODE;
		statusText = DEFAULT_STATUS_TEXT;
		mimeType = DEFAULT_MIME_TYPE;
		
	}
	
	/**
	 * Instantiates a new request context.
	 *
	 * @param outputStream the output stream
	 * @param parameters the parameters
	 * @param persistentParameters the persistent parameters
	 * @param outputCookies the output cookies
	 * @param temporaryParameters the temporary parameters
	 * @param dispatcher the dispatcher
	 */
	public RequestContext(
			OutputStream outputStream, 
			Map<String,String> parameters, 
			Map<String,String> persistentParameters, 
			List<RCCookie> outputCookies, 
			Map<String,String> temporaryParameters, 
			IDispatcher dispatcher) {
			
			this(outputStream, parameters, persistentParameters, outputCookies);
			this.dispatcher = dispatcher;
			this.temporaryParameters = temporaryParameters;
	}
	//@formatter:on

	/**
	 * Adds the RC cookie to the output cookies.
	 *
	 * @param cookie
	 *            the cookie
	 */
	public void addRCCookie(RCCookie cookie) {
		Objects.requireNonNull(cookie, "Cookie cannot be null");

		outputCookies.add(cookie);
	}

	/**
	 * Writes given data to the output stream.
	 *
	 * @param data
	 *            the data
	 * @return the request context
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public RequestContext write(byte[] data) throws IOException {
		if (!headerGenerated) {
			generateHeader();
		}

		outputStream.write(data);
		return this;
	}

	/**
	 * Generates the header depending on the predefined encoding, status text,
	 * status code and mime type.
	 */
	private void generateHeader() {
		charset = Charset.forName(encoding);
		StringBuilder sb = new StringBuilder();

		sb.append("HTTP/1.1 " + statusCode + " " + statusText + "\r\n");
		sb.append("Content-Type: " + mimeType);
		if (mimeType.startsWith("text/")) {
			sb.append("; charset=" + encoding);
		}
		sb.append("\r\n");

		if (!outputCookies.isEmpty()) {
			for (RCCookie cookie : outputCookies) {
				sb.append("Set-Cookie: ").append(cookie.getName()).append("=").append(cookie.getValue());

				if (cookie.getDomain() != null) {
					sb.append("; Domain=").append(cookie.getDomain());
				}
				if (cookie.getPath() != null) {
					sb.append("; Path=").append(cookie.getPath());
				}
				if (cookie.getMaxAge() != null) {
					sb.append("; Max-Age=").append(cookie.getMaxAge());
				}

				sb.append("\r\n");
			}
		}

		// end of the header
		sb.append("\r\n");

		try {
			outputStream.write(sb.toString().getBytes(charset));
		} catch (IOException e) {
			e.printStackTrace();
		}
		headerGenerated = true;
	}

	/**
	 * Writes given text to the output stream.
	 *
	 * @param text
	 *            the text
	 * @return the request context
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public RequestContext write(String text) throws IOException {
		if (!headerGenerated) {
			generateHeader();
		}

		outputStream.write(text.getBytes(charset));
		return this;
	}

	/**
	 * Sets the persistent parameter.
	 *
	 * @param name
	 *            the name
	 * @param value
	 *            the value
	 */
	public void setPersistentParameter(String name, String value) {
		Objects.requireNonNull(name, "Name cannot be null.");
		Objects.requireNonNull(value, "Value cannot be null.");

		persistentParameters.put(name, value);
	}

	/**
	 * Removes the persistent parameter.
	 *
	 * @param name
	 *            the name
	 */
	public void removePersistentParameter(String name) {
		Objects.requireNonNull(name, "Name cannot be null.");

		persistentParameters.remove(name);
	}

	/**
	 * Gets the temporary parameter.
	 *
	 * @param name
	 *            the name
	 * @return the temporary parameter
	 */
	public String getTemporaryParameter(String name) {
		Objects.requireNonNull(name, "Name cannot be null.");

		return temporaryParameters.get(name);
	}

	/**
	 * Gets the temporary parameter names.
	 *
	 * @return the temporary parameter names
	 */
	public Set<String> getTemporaryParameterNames() {
		Set<String> set = temporaryParameters.keySet();

		if (set != null) {
			return Collections.unmodifiableSet(set);
		}

		return null;
	}

	/**
	 * Sets the temporary parameter.
	 *
	 * @param name
	 *            the name
	 * @param value
	 *            the value
	 */
	public void setTemporaryParameter(String name, String value) {
		Objects.requireNonNull(name, "Name cannot be null.");
		Objects.requireNonNull(value, "Value cannot be null.");

		temporaryParameters.put(name, value);
	}

	/**
	 * Removes the temporary parameter.
	 *
	 * @param name
	 *            the name
	 */
	public void removeTemporaryParameter(String name) {
		Objects.requireNonNull(name, "Name cannot be null.");

		temporaryParameters.remove(name);
	}

	/**
	 * Gets the parameter.
	 *
	 * @param name
	 *            the name
	 * @return the parameter
	 */
	public String getParameter(String name) {
		Objects.requireNonNull(name, "Name cannot be null.");

		return parameters.get(name);
	}

	/**
	 * Gets the parameter names.
	 *
	 * @return the parameter names
	 */
	public Set<String> getParameterNames() {
		Set<String> set = parameters.keySet();

		if (set != null) {
			return Collections.unmodifiableSet(set);
		}

		return null;
	}

	/**
	 * Gets the persistent parameter.
	 *
	 * @param name
	 *            the name
	 * @return the persistent parameter
	 */
	public String getPersistentParameter(String name) {
		Objects.requireNonNull(name, "Name cannot be null.");

		return persistentParameters.get(name);
	}

	/**
	 * Gets the persistent parameter names.
	 *
	 * @return the persistent parameter names
	 */
	public Set<String> getPersistentParameterNames() {
		Set<String> set = persistentParameters.keySet();

		if (set != null) {
			return Collections.unmodifiableSet(set);
		}

		return null;
	}

	/**
	 * Gets the temporary parameters.
	 *
	 * @return the temporary parameters
	 */
	public Map<String, String> getTemporaryParameters() {
		return temporaryParameters;
	}

	/**
	 * Checks if is header generated.
	 *
	 * @return true, if is header generated
	 */
	public boolean isHeaderGenerated() {
		return headerGenerated;
	}

	/**
	 * Sets the header generated.
	 *
	 * @param headerGenerated
	 *            the new header generated
	 */
	public void setHeaderGenerated(boolean headerGenerated) {
		this.headerGenerated = headerGenerated;
	}

	/**
	 * Gets the output cookies.
	 *
	 * @return the output cookies
	 */
	public List<RCCookie> getOutputCookies() {
		return outputCookies;
	}

	/**
	 * Sets the output cookies.
	 *
	 * @param outputCookies
	 *            the new output cookies
	 */
	public void setOutputCookies(List<RCCookie> outputCookies) {
		if (headerGenerated) {
			throw new HeaderGeneratedException("Header has already been generated.");
		}

		this.outputCookies = outputCookies;
	}

	/**
	 * Gets the persistent parameters.
	 *
	 * @return the persistent parameters
	 */
	public Map<String, String> getPersistentParameters() {
		return persistentParameters;
	}

	/**
	 * Sets the temporary parameters.
	 *
	 * @param temporaryParameters
	 *            the temporary parameters
	 */
	public void setTemporaryParameters(Map<String, String> temporaryParameters) {
		this.temporaryParameters = temporaryParameters;
	}

	/**
	 * Sets the persistent parameters.
	 *
	 * @param persistentParameters
	 *            the persistent parameters
	 */
	public void setPersistentParameters(Map<String, String> persistentParameters) {
		this.persistentParameters = persistentParameters;
	}

	/**
	 * Sets the encoding.
	 *
	 * @param encoding
	 *            the new encoding
	 */
	public void setEncoding(String encoding) {
		if (headerGenerated) {
			throw new HeaderGeneratedException("Header has already been generated.");
		}
		this.encoding = encoding;
	}

	/**
	 * Gets the parameters.
	 *
	 * @return the parameters
	 */
	public Map<String, String> getParameters() {
		return parameters;
	}

	/**
	 * Sets the status code.
	 *
	 * @param statusCode
	 *            the new status code
	 */
	public void setStatusCode(int statusCode) {
		if (headerGenerated) {
			throw new HeaderGeneratedException("Header has already been generated.");
		}

		this.statusCode = statusCode;
	}

	/**
	 * Sets the status text.
	 *
	 * @param statusText
	 *            the new status text
	 */
	public void setStatusText(String statusText) {
		if (headerGenerated) {
			throw new HeaderGeneratedException("Header has already been generated.");
		}

		this.statusText = statusText;
	}

	/**
	 * Sets the mime type.
	 *
	 * @param mimeType
	 *            the new mime type
	 */
	public void setMimeType(String mimeType) {
		if (headerGenerated) {
			throw new HeaderGeneratedException("Header has already been generated.");
		}

		this.mimeType = mimeType;
	}

	/**
	 * Gets the output stream.
	 *
	 * @return the output stream
	 */
	public OutputStream getOutputStream() {
		return outputStream;
	}

	/**
	 * Gets the charset.
	 *
	 * @return the charset
	 */
	public Charset getCharset() {
		return charset;
	}

	/**
	 * Sets the output stream.
	 *
	 * @param outputStream
	 *            the new output stream
	 */
	public void setOutputStream(OutputStream outputStream) {
		this.outputStream = outputStream;
	}

	/**
	 * Sets the charset.
	 *
	 * @param charset
	 *            the new charset
	 */
	public void setCharset(Charset charset) {
		this.charset = charset;
	}

	/**
	 * The class that represents a single RCCookie which is defined by its name, value, domain, path and maximum age.
	 */
	public static class RCCookie {

		/** The name. */
		private String name;

		/** The value. */
		private String value;

		/** The domain. */
		private String domain;

		/** The path. */
		private String path;

		/** The maximum age of the cookie. */
		private Integer maxAge;

		/**
		 * Gets the name.
		 *
		 * @return the name
		 */
		public String getName() {
			return name;
		}

		/**
		 * Gets the value.
		 *
		 * @return the value
		 */
		public String getValue() {
			return value;
		}

		/**
		 * Gets the domain.
		 *
		 * @return the domain
		 */
		public String getDomain() {
			return domain;
		}

		/**
		 * Gets the path.
		 *
		 * @return the path
		 */
		public String getPath() {
			return path;
		}

		/**
		 * Gets the max age.
		 *
		 * @return the max age
		 */
		public Integer getMaxAge() {
			return maxAge;
		}

		/**
		 * Instantiates a new RC cookie.
		 *
		 * @param name
		 *            the name
		 * @param value
		 *            the value
		 * @param maxAge
		 *            the max age
		 * @param domain
		 *            the domain
		 * @param path
		 *            the path
		 */
		public RCCookie(String name, String value, Integer maxAge, String domain, String path) {
			this.name = name;
			this.value = value;
			this.domain = domain;
			this.path = path;
			this.maxAge = maxAge;
		}

	}

}
