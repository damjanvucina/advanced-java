package hr.fer.zemris.java.webserver;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PushbackInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import hr.fer.zemris.java.webserver.RequestContext.RCCookie;

public class SmartHttpServer {
	private static final String ADDRESS_PROPERTY = "server.address";
	private static final String DOMAIN_NAME_PROPERTY = "server.domainName";
	private static final String PORT_PROPERTY = "server.port";
	private static final String WORKER_THREADS_PROPERTY = "server.workerThreads";
	private static final String DOCUMENT_ROOT_PROPERTY = "server.documentRoot";
	private static final String TIMEOUT_PROPERTY = "session.timeout";

	private static final String MIME_CONFIG_PROPERTY = "server.mimeConfig";
	private static final String WORKERS_CONFIG_PROPERTY = "server.workers";

	private static final String HOST_REGEX = "(\\w+):(\\d+)";

	private static final String DEFAULT_MIME_TYPE = "application/octet-stream";

	private String address;
	private String domainName;
	private int port;
	private int workerThreads;
	private int sessionTimeout;
	private Map<String, String> mimeTypes = new HashMap<String, String>();
	private ServerThread serverThread;
	private ExecutorService threadPool;
	private Path documentRoot;

	private String mimeConfig;
	private String workersConfig;

	public SmartHttpServer(String configFileName) {
		Properties properties = new Properties();

		properties = loadProperties(properties, configFileName);
		setUpServer(properties);

		properties = loadProperties(properties, mimeConfig);
		setUpMime(properties);

		// properties = loadProperties(properties, workersConfig);
		// setUpWorkers(properties);
	}

	private Properties loadProperties(Properties properties, String fileName) {
		properties.clear();
		InputStream inputStream = null;

		try {
			inputStream = new FileInputStream(fileName);
			properties.load(inputStream);

		} catch (IOException e) {
			e.printStackTrace();
		}

		return properties;
	}

	private void setUpMime(Properties properties) {
		properties.forEach((key, value) -> mimeTypes.put(key.toString(), value.toString()));
	}

	private void setUpServer(Properties properties) {
		address = properties.getProperty(ADDRESS_PROPERTY);
		domainName = properties.getProperty(DOMAIN_NAME_PROPERTY);
		port = Integer.parseInt(properties.getProperty(PORT_PROPERTY));
		workerThreads = Integer.parseInt(properties.getProperty(WORKER_THREADS_PROPERTY));
		documentRoot = Paths.get(properties.getProperty(DOCUMENT_ROOT_PROPERTY));
		sessionTimeout = Integer.parseInt(properties.getProperty(TIMEOUT_PROPERTY));

		mimeConfig = String.valueOf(Paths.get(properties.getProperty(MIME_CONFIG_PROPERTY)));
		workersConfig = String.valueOf(Paths.get(properties.getProperty(WORKERS_CONFIG_PROPERTY)));
	}

	protected synchronized void start() {
		// … start server thread if not already running …
		// … init threadpool by Executors.newFixedThreadPool(...); …
		if (serverThread == null) {
			serverThread = new ServerThread();
			serverThread.start();
		}

		threadPool = Executors.newFixedThreadPool(workerThreads);
	}

	protected synchronized void stop() {
		// … signal server thread to stop running …
		// … shutdown threadpool …
		serverThread.interrupt();
		threadPool.shutdown();
	}

	public static void main(String[] args) {
		SmartHttpServer server = new SmartHttpServer("config/server.properties");
		server.start();
	}

	protected class ServerThread extends Thread {
		@Override
		public void run() {
			// given in pesudo-code:
			// open serverSocket on specified port
			// while(true) {
			// Socket client = serverSocket.accept();
			// ClientWorker cw = new ClientWorker(client);
			// submit cw to threadpool for execution
			// }
			ServerSocket serverSocket = null;
			try {
				serverSocket = new ServerSocket(port);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			while (isAlive()) {
				Socket client = null;

				try {
					client = serverSocket.accept();
				} catch (IOException e) {
					e.printStackTrace();
				}
				ClientWorker cw = new ClientWorker(client);
				threadPool.submit(cw);
			}
			try {
				serverSocket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private class ClientWorker implements Runnable {
		private Socket csocket;
		private PushbackInputStream istream;
		private OutputStream ostream;
		private String version;
		private String method;
		private String host;
		private Map<String, String> params = new HashMap<String, String>();
		private Map<String, String> tempParams = new HashMap<String, String>();
		private Map<String, String> permPrams = new HashMap<String, String>();
		private List<RCCookie> outputCookies = new ArrayList<RequestContext.RCCookie>();
		private String SID;

		public ClientWorker(Socket csocket) {
			super();
			this.csocket = csocket;
		}

		@Override
		public void run() {
			try {
				istream = new PushbackInputStream(csocket.getInputStream());
				ostream = csocket.getOutputStream();

				List<String> request = extractHeaders();

				if (request.size() < 1) {
					sendError(400, "Header is less than a single line.");
				}

				String firstLine = request.get(0);
				String[] items = firstLine.split("\n");

				String method = items[0];
				String requestedPath = items[1];
				String version = items[2];

				if (!"GET".equals(method)) {
					sendError(400, "Method is not GET, was: " + method);
				}
				if (!"HTTP/1.0".equals(version) && !"HTTP/1.1".equals(version)) {
					sendError(400, "Invalid protocol version , was: " + version);
				}

				String hostName = null;
				for (String r : request.subList(1, request.size())) {

					r = r.replace(" ", "");
					if (r.startsWith("Host:")) {
						String result = host = r.substring(6); // 6 = "Host:".length()

						Pattern pattern = Pattern.compile(HOST_REGEX);
						Matcher matcher = pattern.matcher(host);
						if (matcher.matches()) {
							hostName = matcher.group(1);
						}
						break;
					}
				}

				if (host == null) {
					host = requestedPath;// provjeri ovo
				}

				int pathParamSeparator = requestedPath.indexOf("\\?");

				String path = requestedPath.substring(0, pathParamSeparator);
				String paramString = requestedPath.substring(pathParamSeparator + 1);

				parseParameters(paramString);

				Path resolvedReqPath = documentRoot.resolve(requestedPath);
				if (!resolvedReqPath.startsWith(documentRoot)) {
					sendError(403, "Forbidden.");
					return;
				}

				if (!Files.isRegularFile(resolvedReqPath) && !Files.isReadable(resolvedReqPath)) {
					sendError(404, "File not found.");
					return;
				}

				int extensionsSeparatorIndex = String.valueOf(resolvedReqPath).lastIndexOf(".");
				String fileExtension = String.valueOf(resolvedReqPath).substring(extensionsSeparatorIndex + 1);

				String mimeValue = mimeTypes.get(fileExtension);
				if (mimeValue == null) {
					mimeValue = DEFAULT_MIME_TYPE;
				}

				// postavi cookije
				RequestContext rc = new RequestContext(ostream, params, permPrams, outputCookies);
				rc.setMimeType(mimeValue);
				rc.setStatusCode(200);

				byte[] content = Files.readAllBytes(resolvedReqPath);
				rc.write(content);

			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		private void parseParameters(String paramString) {
			String[] paramPairs = paramString.split("\\?");

			for (String pair : paramPairs) {
				int pairSeparatorIndex = pair.indexOf("=");
				String key = pair.substring(0, pairSeparatorIndex);
				String value = pair.substring(pairSeparatorIndex + 1);

				params.put(key, value);
			}
		}

		private void sendError(int statusCode, String statusText) throws IOException {

			ostream.write(("HTTP/1.1 " + statusCode + " " + statusText + "\r\n" + "Server: simple java server\r\n"
					+ "Content-Type: text/plain;charset=UTF-8\r\n" + "Content-Length: 0\r\n" + "Connection: close\r\n"
					+ "\r\n").getBytes(StandardCharsets.US_ASCII));
			ostream.flush();

		}

		private List<String> extractHeaders() {
			List<String> headers = new ArrayList<String>();
			String currentLine = null;

			byte[] request = readRequest(istream);
			String requestStr = new String(request, StandardCharsets.US_ASCII);

			for (String s : requestStr.split("\n")) {
				if (s.isEmpty())
					break;
				char c = s.charAt(0);
				if (c == 9 || c == 32) {
					currentLine += s;
				} else {
					if (currentLine != null) {
						headers.add(currentLine);
					}
					currentLine = s;
				}
			}
			if (!currentLine.isEmpty()) {
				headers.add(currentLine);
			}

			return headers;
		}

		private byte[] readRequest(PushbackInputStream istream) {

			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			int state = 0;
			l: while (true) {
				int b = 0;
				try {
					b = istream.read();
				} catch (IOException e) {
					e.printStackTrace();
				}
				if (b == -1)
					return null;
				if (b != 13) {
					bos.write(b);
				}
				switch (state) {
				case 0:
					if (b == 13) {
						state = 1;
					} else if (b == 10)
						state = 4;
					break;
				case 1:
					if (b == 10) {
						state = 2;
					} else
						state = 0;
					break;
				case 2:
					if (b == 13) {
						state = 3;
					} else
						state = 0;
					break;
				case 3:
					if (b == 10) {
						break l;
					} else
						state = 0;
					break;
				case 4:
					if (b == 10) {
						break l;
					} else
						state = 0;
					break;
				}
			}
			return bos.toByteArray();
		}

	}

}
