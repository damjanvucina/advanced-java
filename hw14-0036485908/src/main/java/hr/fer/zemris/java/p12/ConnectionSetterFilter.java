package hr.fer.zemris.java.p12;

import hr.fer.zemris.java.p12.dao.sql.SQLConnectionProvider;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.sql.DataSource;

/**
 * The Filter class responsible for intercepting requests, fetching an instance
 * of Connection class from designated Connection pool to be used by the current
 * thread and forwarding requests to the designated servlet classes
 * 
 * @author Damjan Vučina
 */
@WebFilter(filterName = "f1", urlPatterns = { "/servleti/*" })
public class ConnectionSetterFilter implements Filter {

	/**
	 * Called by the web container to indicate to a filter that it is being placed
	 * into service. Notice: Not implemented here
	 */
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	/**
	 * Called by the web container to indicate to a filter that it is being taken
	 * out of service. Notice: Not implemented here
	 */
	@Override
	public void destroy() {
	}

	/**
	 * Fetches an instance of Connection class from designated Connection pool to be
	 * used by the current thread and forwards request to the designated servlet
	 * class
	 */
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		DataSource ds = (DataSource) request.getServletContext().getAttribute("hr.fer.zemris.dbpool");
		Connection con = null;
		try {
			con = ds.getConnection();
		} catch (SQLException e) {
			throw new IOException("Baza podataka nije dostupna.", e);
		}
		SQLConnectionProvider.setConnection(con);
		try {
			chain.doFilter(request, response);
		} finally {
			SQLConnectionProvider.setConnection(null);
			try {
				con.close();
			} catch (SQLException ignorable) {
			}
		}
	}

}