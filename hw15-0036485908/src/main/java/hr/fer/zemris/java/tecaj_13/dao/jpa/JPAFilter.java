package hr.fer.zemris.java.tecaj_13.dao.jpa;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;

/**
 * The filter class used for chaining and redirecting user's requests.
 * 
 * @author Damjan Vučina
 */
@WebFilter("/servleti/*")
public class JPAFilter implements Filter {

	/**
	 * Called by the web container to indicate to a filter that it is being placed
	 * into service. NOTICE: Not implemented here
	 */
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	/**
	 * The doFilter method of the Filter is called by the container each time a
	 * request/response pair is passed through the chain due to a client request for
	 * a resource at the end of the chain.
	 */
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		try {
			chain.doFilter(request, response);
		} finally {
			JPAEMProvider.close();
		}
	}

	/**
	 * Called by the web container to indicate to a filter that it is being taken
	 * out of service. NOTICE: Not implemented here
	 */
	@Override
	public void destroy() {
	}

}