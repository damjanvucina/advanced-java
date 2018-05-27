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

public class RequestContext {
	public static final String DEFAULT_ENCODING = "UTF-8";
	public static final int DEFAULT_STATUS_CODE = 200;
	public static final String DEFAULT_STATUS_TEXT = "OK";
	public static final String DEFAULT_MIME_TYPE = "text/html";

	private OutputStream outputStream;
	private Charset charset;
	private String encoding;
	private int statusCode;
	private String statusText;
	private String mimeType;

	private Map<String, String> parameters;
	private Map<String, String> temporaryParameters;
	private Map<String, String> persistentParameters;
	private List<RCCookie> outputCookies;

	private boolean headerGenerated;

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
		
		encoding = DEFAULT_ENCODING;
		statusCode = DEFAULT_STATUS_CODE;
		statusText = DEFAULT_STATUS_TEXT;
		mimeType = DEFAULT_MIME_TYPE;
		
	}
	//@formatter:on

	public void addRCCookie(RCCookie cookie) {
		Objects.requireNonNull(cookie, "Cookie cannot be null");

		outputCookies.add(cookie);
	}

	public RequestContext write(byte[] data) throws IOException {
		if (!headerGenerated) {
			generateHeader();
		}

		outputStream.write(data);
		return this;
	}

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

	public RequestContext write(String text) throws IOException {
		if (!headerGenerated) {
			generateHeader();
		}

		outputStream.write(text.getBytes(charset));
		return this;
	}

	public void setPersistentParameter(String name, String value) {
		Objects.requireNonNull(name, "Name cannot be null.");
		Objects.requireNonNull(value, "Value cannot be null.");

		persistentParameters.put(name, value);
	}

	public void removePersistentParameter(String name) {
		Objects.requireNonNull(name, "Name cannot be null.");

		persistentParameters.remove(name);
	}

	public String getTemporaryParameter(String name) {
		Objects.requireNonNull(name, "Name cannot be null.");

		return temporaryParameters.get(name);
	}

	public Set<String> getTemporaryParameterNames() {
		Set<String> set = temporaryParameters.keySet();

		if (set != null) {
			return Collections.unmodifiableSet(set);
		}

		return null;
	}

	public void setTemporaryParameter(String name, String value) {
		Objects.requireNonNull(name, "Name cannot be null.");
		Objects.requireNonNull(value, "Value cannot be null.");

		temporaryParameters.put(name, value);
	}

	public void removeTemporaryParameter(String name) {
		Objects.requireNonNull(name, "Name cannot be null.");

		temporaryParameters.remove(name);
	}

	public String getParameter(String name) {
		Objects.requireNonNull(name, "Name cannot be null.");

		return parameters.get(name);
	}

	public Set<String> getParameterNames() {
		Set<String> set = parameters.keySet();

		if (set != null) {
			return Collections.unmodifiableSet(set);
		}

		return null;
	}

	public String getPersistentParameter(String name) {
		Objects.requireNonNull(name, "Name cannot be null.");

		return persistentParameters.get(name);
	}

	public Set<String> getPersistentParameterNames() {
		Set<String> set = persistentParameters.keySet();

		if (set != null) {
			return Collections.unmodifiableSet(set);
		}

		return null;
	}

	public Map<String, String> getTemporaryParameters() {
		return temporaryParameters;
	}

	public boolean isHeaderGenerated() {
		return headerGenerated;
	}

	public void setHeaderGenerated(boolean headerGenerated) {
		this.headerGenerated = headerGenerated;
	}

	public List<RCCookie> getOutputCookies() {
		return outputCookies;
	}

	public void setOutputCookies(List<RCCookie> outputCookies) {
		if (headerGenerated) {
			throw new HeaderGeneratedException("Header has already been generated.");
		}

		this.outputCookies = outputCookies;
	}

	public Map<String, String> getPersistentParameters() {
		return persistentParameters;
	}

	public void setTemporaryParameters(Map<String, String> temporaryParameters) {
		this.temporaryParameters = temporaryParameters;
	}

	public void setPersistentParameters(Map<String, String> persistentParameters) {
		this.persistentParameters = persistentParameters;
	}

	public void setEncoding(String encoding) {
		if (headerGenerated) {
			throw new HeaderGeneratedException("Header has already been generated.");
		}
		this.encoding = encoding;
	}

	public Map<String, String> getParameters() {
		return parameters;
	}

	public void setStatusCode(int statusCode) {
		if (headerGenerated) {
			throw new HeaderGeneratedException("Header has already been generated.");
		}

		this.statusCode = statusCode;
	}

	public void setStatusText(String statusText) {
		if (headerGenerated) {
			throw new HeaderGeneratedException("Header has already been generated.");
		}

		this.statusText = statusText;
	}

	public void setMimeType(String mimeType) {
		if (headerGenerated) {
			throw new HeaderGeneratedException("Header has already been generated.");
		}

		this.mimeType = mimeType;
	}

	public OutputStream getOutputStream() {
		return outputStream;
	}

	public Charset getCharset() {
		return charset;
	}

	public void setOutputStream(OutputStream outputStream) {
		this.outputStream = outputStream;
	}

	public void setCharset(Charset charset) {
		this.charset = charset;
	}

	public static class RCCookie {

		private String name;
		private String value;
		private String domain;
		private String path;
		private Integer maxAge;

		public String getName() {
			return name;
		}

		public String getValue() {
			return value;
		}

		public String getDomain() {
			return domain;
		}

		public String getPath() {
			return path;
		}

		public Integer getMaxAge() {
			return maxAge;
		}

		public RCCookie(String name, String value, Integer maxAge, String domain, String path) {
			this.name = name;
			this.value = value;
			this.domain = domain;
			this.path = path;
			this.maxAge = maxAge;
		}

	}

}
