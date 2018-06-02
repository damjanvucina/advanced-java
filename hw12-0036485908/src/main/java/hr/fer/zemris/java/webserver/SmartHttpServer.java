package hr.fer.zemris.java.webserver;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PushbackInputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import hr.fer.zemris.java.custom.scripting.exec.SmartScriptEngine;
import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;
import hr.fer.zemris.java.webserver.RequestContext.RCCookie;

/**
 * The core class of the developed server. This class is responsible for loading
 * the data from properties files, performing communication with the user,
 * analyzing user's input and delegating the procession of the user's request to
 * the designated helper classes and workers. This class defines a few workers
 * responsible for performing simple calculation tasks and drawing simple
 * objects.
 * 
 * @author Damjan Vučina
 */
public class SmartHttpServer {

	/** The Constant ADDRESS_PROPERTY. */
	private static final String ADDRESS_PROPERTY = "server.address";

	/** The Constant DOMAIN_NAME_PROPERTY. */
	private static final String DOMAIN_NAME_PROPERTY = "server.domainName";

	/** The Constant PORT_PROPERTY. */
	private static final String PORT_PROPERTY = "server.port";

	/** The Constant WORKER_THREADS_PROPERTY. */
	private static final String WORKER_THREADS_PROPERTY = "server.workerThreads";

	/** The Constant DOCUMENT_ROOT_PROPERTY. */
	private static final String DOCUMENT_ROOT_PROPERTY = "server.documentRoot";

	/** The Constant TIMEOUT_PROPERTY. */
	private static final String TIMEOUT_PROPERTY = "session.timeout";

	/** The Constant MIME_CONFIG_PROPERTY. */
	private static final String MIME_CONFIG_PROPERTY = "server.mimeConfig";

	/** The Constant WORKERS_CONFIG_PROPERTY. */
	private static final String WORKERS_CONFIG_PROPERTY = "server.workers";

	/** The Constant DEFAULT_MIME_TYPE. */
	private static final String DEFAULT_MIME_TYPE = "application/octet-stream";

	/** The Fully Qualified Class name for the EchoParams Worker */
	private static final String ECHO_FQCN = "hr.fer.zemris.java.webserver.workers.EchoParams";

	/** The Fully Qualified Class name for the Circle Worker */
	@SuppressWarnings("unused")
	private static final String CIRCLE_FQCN = "hr.fer.zemris.java.webserver.workers.CircleWorker";

	/** The Fully Qualified Class name for the Hello Worker */
	@SuppressWarnings("unused")
	private static final String HELLO_FQCN = "hr.fer.zemris.java.webserver.workers.HelloWorker";

	/** The Fully Qualified Class name for the Home Worker */
	private static final String HOME_FQCN = "hr.fer.zemris.java.webserver.workers.Home";

	/** The Fully Qualified Class name for the BgColor Worker */
	private static final String BGCOLOR_FQCN = "hr.fer.zemris.java.webserver.workers.BgColorWorker";

	/** The Constant COOKIE_SEPARATOR. */
	private static final String COOKIE_SEPARATOR = ";";

	/** The address. */
	private String address;

	/** The domain name. */
	@SuppressWarnings("unused")
	private String domainName;

	/** The port. */
	private int port;

	/** The worker threads. */
	private int workerThreads;

	/** The session timeout. */
	private int sessionTimeout;

	/** The mime types. */
	private Map<String, String> mimeTypes = new HashMap<>();

	/** The server thread. */
	private ServerThread serverThread;

	/** The thread pool. */
	private ExecutorService threadPool;

	/** The document root. */
	private Path documentRoot;

	/** The mime config. */
	private String mimeConfig;

	/** The workers config. */
	private String workersConfig;

	/** The workers map. */
	private Map<String, IWebWorker> workersMap = new HashMap<>();

	/** The worker name map. */
	private Map<String, IWebWorker> workerNameMap = new HashMap<>();

	/** The sessions. */
	private Map<String, SessionMapEntry> sessions = new HashMap<String, SmartHttpServer.SessionMapEntry>();

	/** The session random. */
	private Random sessionRandom = new Random();

	/**
	 * Instantiates a new smart http server and sets up properties.
	 *
	 * @param configFileName
	 *            the config file name
	 */
	public SmartHttpServer(String configFileName) {
		Properties properties = new Properties();

		properties = loadProperties(properties, configFileName);
		setUpServer(properties);

		properties = loadProperties(properties, mimeConfig);
		setUpMime(properties);

		properties = loadProperties(properties, workersConfig);
		setUpWorkers(properties);
		setUpNameWorkers();
	}

	/**
	 * Loads properties.
	 *
	 * @param properties
	 *            the properties
	 * @param fileName
	 *            the file name
	 * @return the properties
	 */
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

	/**
	 * Sets the up name workers, i.e. map that maps workers name to the designated
	 * classes.
	 * 
	 * @throws ClassNotFoundException
	 *             when an application tries to load in a class but no definition
	 *             for the class with the specified name could be found
	 * @throws InstantiationException
	 *             when an application tries to create an instance of a class using
	 *             the newInstance method in class Class, but the specified class
	 *             object cannot be instantiated.
	 * @throws IllegalAccessException
	 *             when an application tries to reflectively create an instance ,
	 *             set or get a field, or invoke a method, but the currently
	 *             executing method does not have access to the definition of the
	 *             specified class
	 * 
	 * 
	 */
	@SuppressWarnings("deprecation")
	private void setUpNameWorkers() {
		try {
			workerNameMap.put("HelloWorker", workersMap.get("/hello"));
			workerNameMap.put("CircleWorker", workersMap.get("/cw"));

			Class<?> referenceToClass = this.getClass().getClassLoader().loadClass(ECHO_FQCN);
			Object newObject = referenceToClass.newInstance();
			IWebWorker iww = (IWebWorker) newObject;
			workerNameMap.put("EchoParams", iww);

			Class<?> referenceToClass2 = this.getClass().getClassLoader().loadClass(HOME_FQCN);
			Object newObject2 = referenceToClass2.newInstance();
			IWebWorker iww2 = (IWebWorker) newObject2;
			workerNameMap.put("Home", iww2);

			Class<?> referenceToClass3 = this.getClass().getClassLoader().loadClass(BGCOLOR_FQCN);
			Object newObject3 = referenceToClass3.newInstance();
			IWebWorker iww3 = (IWebWorker) newObject3;
			workerNameMap.put("BgColorWorker", iww3);

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Sets up the workers.
	 *
	 * @param properties
	 *            the new up workers
	 */
	private void setUpWorkers(Properties properties) {
		properties.forEach((key, value) -> processWorker(key, value));
	}

	/**
	 * Processes worker by initializing the workers map.
	 *
	 * @param key
	 *            the key
	 * @param value
	 *            the value
	 */
	@SuppressWarnings("deprecation")
	private void processWorker(Object key, Object value) {
		try {
			String path = (String) key;
			String fqcn = (String) value;

			if (workersMap.containsKey(path)) {
				throw new IllegalArgumentException("Workers map already contains entry with the key set to: " + path);
			}

			Class<?> referenceToClass = this.getClass().getClassLoader().loadClass(fqcn);
			Object newObject = referenceToClass.newInstance();
			IWebWorker iww = (IWebWorker) newObject;

			workersMap.put(path, iww);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Sets the up mime type.
	 *
	 * @param properties
	 *            the new up mime
	 */
	private void setUpMime(Properties properties) {
		properties.forEach((key, value) -> mimeTypes.put(key.toString(), value.toString()));
	}

	/**
	 * Sets the up server.
	 *
	 * @param properties
	 *            the new up server
	 */
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

	/**
	 * Starts the server and creates a collector of inactive sessions.
	 */
	protected synchronized void start() {
		createInactiveSessionCollector();
		if (serverThread == null) {
			serverThread = new ServerThread();
			serverThread.start();
		}

		threadPool = Executors.newFixedThreadPool(workerThreads);
	}

	/**
	 * Creates the inactive session collector.
	 */
	private void createInactiveSessionCollector() {
		boolean isDeamon = true;
		Timer timer = new Timer(isDeamon);
		Map<String, SessionMapEntry> sessionCopy = new HashMap<>(sessions);

		TimerTask timerTask = new TimerTask() {

			@Override
			public void run() {
				for (Entry<String, SessionMapEntry> entry : sessionCopy.entrySet()) {
					if (isExpired(entry.getValue().validUntil)) {
						sessions.remove(entry.getKey());
					}
				}
			}

			private boolean isExpired(long validUntil) {
				return validUntil > System.currentTimeMillis() / 1000;
			}
		};

		long delay = 0;
		long period = 300000; // 300000 seconds = 5 minutes
		timer.schedule(timerTask, delay, period);
	}

	/**
	 * Stops the server.
	 */
	protected synchronized void stop() {
		serverThread.interrupt();
		threadPool.shutdown();
	}

	/**
	 * The main method.
	 *
	 * @param args
	 *            the arguments
	 */
	public static void main(String[] args) {
		SmartHttpServer server = new SmartHttpServer("config/server.properties");
		server.start();
	}

	/**
	 * The class that represents the work performed by this server.
	 */
	protected class ServerThread extends Thread {

		/**
		 * Method represents the work performed by this server.
		 */
		@Override
		public void run() {
			try {
				ServerSocket serverSocket = new ServerSocket();
				serverSocket.bind(new InetSocketAddress(address, port));
				while (isAlive()) {
					Socket client = serverSocket.accept();
					ClientWorker cw = new ClientWorker(client);
					threadPool.submit(cw);
				}
				serverSocket.close();

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * The Class that gives further information on a certain session. Used to track
	 * the clients of this server.
	 */
	private static class SessionMapEntry {

		/** The session id number. */
		String sid;

		/** The host. */
		String host;

		/** The valid until timestamp. */
		long validUntil;

		/** The map. */
		Map<String, String> map;

		/**
		 * Instantiates a new session map entry.
		 *
		 * @param sid
		 *            the sid
		 * @param host
		 *            the host
		 * @param validUntil
		 *            the valid until
		 * @param map
		 *            the map
		 */
		public SessionMapEntry(String sid, String host, long validUntil, Map<String, String> map) {
			this.sid = sid;
			this.host = host;
			this.validUntil = validUntil;
			this.map = map;
		}
	}

	/**
	 * The class that is responsible for inspecting the request header, extracting
	 * data from the header, reading and writing to the user.
	 */
	private class ClientWorker implements Runnable, IDispatcher {

		/** The client socket. */
		private Socket csocket;

		/** The input stream. */
		private PushbackInputStream istream;

		/** The output stream. */
		private OutputStream ostream;

		/** The version. */
		@SuppressWarnings("unused")
		private String version;

		/** The method. */
		@SuppressWarnings("unused")
		private String method;

		/** The host. */
		private String host;

		/** The parameters. */
		private Map<String, String> params = new HashMap<String, String>();

		/** The temporary params. */
		private Map<String, String> tempParams = new HashMap<String, String>();

		/** The permanent params. */
		private Map<String, String> permParams = new HashMap<String, String>();

		/** The output cookies. */
		private List<RCCookie> outputCookies = new ArrayList<RequestContext.RCCookie>();

		/** The session id. */
		@SuppressWarnings("unused")
		private String SID;

		/** The context. */
		private RequestContext context;

		/**
		 * Instantiates a new client worker.
		 *
		 * @param csocket
		 *            the csocket
		 */
		public ClientWorker(Socket csocket) {
			super();
			this.csocket = csocket;
		}

		/**
		 * Method that is responsible for inspecting the request header, extracting data
		 * from the header, reading and writing to the user.
		 */
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
				String[] items = firstLine.split(" ");

				String method = items[0];
				String requestedPath = items[1];
				String version = items[2];

				if (!"GET".equals(method)) {
					sendError(400, "Method is not GET, was: " + method);
				}
				if (!"HTTP/1.0".equals(version) && !"HTTP/1.1".equals(version)) {
					sendError(400, "Invalid protocol version , was: " + version);
				}

				for (String r : request.subList(1, request.size())) {

					r = r.replace(" ", "");
					if (r.startsWith("Host:")) {
						String h = r.substring(5);
						String[] hostParts = h.split(":");
						if (hostParts.length == 2) {
							host = hostParts[0];
						}

					}
				}

				if (host == null) {
					host = requestedPath;
				}

				checkSession(request);

				String paramString = null;
				String path = null;
				if (requestedPath.contains("?")) {
					int pathParamSeparator = requestedPath.indexOf("?");
					path = requestedPath.substring(0, pathParamSeparator);
					paramString = requestedPath.substring(pathParamSeparator + 1);
					parseParameters(paramString);

				} else {
					path = requestedPath;
				}

				internalDispatchRequest(path, true);

			} catch (IOException e) {
				e.printStackTrace();

			} catch (NullPointerException e) {

			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					ostream.flush();
					csocket.close();
				} catch (IOException e) {
					e.printStackTrace();
				}

			}
		}

		/**
		 * Performs checks on the current session. Depending on the request's cookie,
		 * this method delegates to helper methods responsible to creating new sessions
		 * or updating existing ones.
		 *
		 * @param request
		 *            the request
		 */
		private synchronized void checkSession(List<String> request) {
			String sidCandidate = null;
			for (String line : request) {
				if (!line.startsWith("Cookie:")) {
					continue;
				}

				String cookieLine = line.substring(7).replace(" ", ""); // Cookie: .length()==7
				String[] browserCookies = cookieLine.split(COOKIE_SEPARATOR);
				for (String cookie : browserCookies) {
					if (cookie.startsWith("sid")) {
						sidCandidate = cookie.substring(4, cookie.length()); // sid=".length()=4
						break;
					}
				}
			}

			if (sidCandidate == null) {
				processNewSid();
			} else {
				String browserHost = null;
				if (sessions.get(sidCandidate) != null) {
					browserHost = sessions.get(sidCandidate).host;
				}

				if (!host.equals(browserHost)) {
					processNewSid();
				} else {
					processExistingSid(sessions.get(sidCandidate));
				}
			}
		}

		/**
		 * Process existing session.
		 *
		 * @param entry
		 *            the entry
		 */
		private void processExistingSid(SessionMapEntry entry) {
			if (entry.validUntil < System.currentTimeMillis() / 1000) {
				sessions.remove(entry.sid);
				processNewSid();
			} else {
				entry.validUntil = System.currentTimeMillis() / 1000 + sessionTimeout;
				permParams = entry.map;
			}

		}

		/**
		 * Process new session.
		 */
		private void processNewSid() {
			String sid = generateSessionID();
			long validUntil = System.currentTimeMillis() / 1000 + sessionTimeout;
			Map<String, String> sessionMap = new ConcurrentHashMap<>();

			sessionMap.put("sid", sid);
			outputCookies.forEach(cookie -> sessionMap.put(cookie.getName(), cookie.getValue()));

			SessionMapEntry entry = new SessionMapEntry(sid, host, validUntil, sessionMap);
			permParams = entry.map;
			sessions.put(sid, entry);
			outputCookies.add(new RCCookie("sid", sid, null, host, "/"));
		}

		/**
		 * Generate session ID.
		 *
		 * @return the string
		 */
		private String generateSessionID() {
			int leftLimit = 65; // 'A'
			int rightLimit = 90; // 'Z'
			int targetLength = 20;

			StringBuilder buffer = new StringBuilder(targetLength);
			for (int i = 0; i < targetLength; i++) {
				int randomLimitedInt = leftLimit + (int) (sessionRandom.nextFloat() * (rightLimit - leftLimit + 1));
				buffer.append((char) randomLimitedInt);
			}

			return buffer.toString();
		}

		/**
		 * Parses the parameters.
		 *
		 * @param paramString
		 *            the param string
		 */
		private void parseParameters(String paramString) {
			String[] paramPairs = paramString.split("&");

			for (String pair : paramPairs) {
				int pairSeparatorIndex = pair.indexOf("=");
				String key = pair.substring(0, pairSeparatorIndex);
				String value = pair.substring(pairSeparatorIndex + 1);
				params.put(key, value);
			}
		}

		/**
		 * Sends error to the user.
		 *
		 * @param statusCode
		 *            the status code
		 * @param statusText
		 *            the status text
		 * @throws IOException
		 *             Signals that an I/O exception has occurred.
		 */
		private void sendError(int statusCode, String statusText) throws IOException {

			ostream.write(("HTTP/1.1 " + statusCode + " " + statusText + "\r\n" + "Server: simple java server\r\n"
					+ "Content-Type: text/plain;charset=UTF-8\r\n" + "Content-Length: 0\r\n" + "Connection: close\r\n"
					+ "\r\n").getBytes(StandardCharsets.US_ASCII));
			ostream.flush();

		}

		/**
		 * Extracts header.
		 *
		 * @return the list
		 */
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

		/**
		 * Reads request.
		 *
		 * @param istream
		 *            the istream
		 * @return the byte[]
		 */
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

		/**
		 * Dispatches the request to the worker classes and validates the level of
		 * clearance for reading private files.
		 *
		 * @param path
		 *            the path
		 * @param directCall
		 *            the direct call
		 * @throws Exception
		 *             the exception
		 */
		public void internalDispatchRequest(String path, boolean directCall) throws Exception {
			try {
				if (path.startsWith("/private") && directCall) {
					sendError(404, "File not found.");
					return;
				}
				Path resolvedReqPath = documentRoot.toAbsolutePath().normalize().resolve(path.substring(1))
						.toAbsolutePath();
				if (!resolvedReqPath.startsWith(documentRoot.normalize().toAbsolutePath())) {
					sendError(403, "Forbidden.");
					return;
				}

				if (path.startsWith("/ext/")) {
					String workerName = path.substring(5);// "/ext/".length()=5
					IWebWorker currentWorker = workerNameMap.get(workerName);
					if (currentWorker != null) {

						currentWorker.processRequest(acquireContext());
						return;
					}
				}

				if (workersMap.containsKey("/" + resolvedReqPath.getFileName())) {
					workersMap.get("/" + resolvedReqPath.getFileName()).processRequest(acquireContext());
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

				RequestContext rc = acquireContext();
				rc.setMimeType(mimeValue);
				rc.setStatusCode(200);

				if (path.endsWith(".smscr")) {
					processSmscrFile(resolvedReqPath, rc);

				} else {
					byte[] content = Files.readAllBytes(resolvedReqPath);
					rc.write(content);
				}

			} catch (IOException e) {
				e.printStackTrace();
			}

		}

		/**
		 * Acquires existing context or creates a new one.
		 *
		 * @return the request context
		 */
		private RequestContext acquireContext() {
			if (context == null) {
				context = new RequestContext(ostream, params, permParams, outputCookies, tempParams, this);
			}

			return context;
		}

		/**
		 * Process smscr file.
		 *
		 * @param resolvedReqPath
		 *            the resolved req path
		 * @param rc
		 *            the rc
		 */
		private void processSmscrFile(Path resolvedReqPath, RequestContext rc) {
			byte[] content = null;
			try {
				content = Files.readAllBytes(resolvedReqPath);
			} catch (IOException e) {
				e.printStackTrace();
			}

			String smscrContent = new String(content, Charset.defaultCharset());
			SmartScriptParser parser = new SmartScriptParser(smscrContent);
			DocumentNode document = parser.getDocumentNode();

			SmartScriptEngine engine = new SmartScriptEngine(document, rc);
			engine.execute();
		}

		/**
		 * Dispatches this non direct request.
		 */
		@Override
		public void dispatchRequest(String urlPath) throws Exception {
			internalDispatchRequest(urlPath, false);
		}
	}
}
