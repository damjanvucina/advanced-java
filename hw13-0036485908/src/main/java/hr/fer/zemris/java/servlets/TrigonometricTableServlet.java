package hr.fer.zemris.java.servlets;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static java.lang.Math.toRadians;
import static java.lang.Math.sin;
import static java.lang.Math.cos;

/**
 * The class that represents a servlet responsible for generating a table of
 * angles and their sine and cosine values. The results are rounded to 10
 * decimals. User can either generate a default page containing angles 0 - 90 or
 * specify starting and ending angles using the given form.
 * 
 * @author Damjan Vučina
 */
@WebServlet("/trigonometric")
public class TrigonometricTableServlet extends HttpServlet {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The Constant that represents starting angle. */
	private static final Integer DEFAULT_A = 0;

	/** The Constant that reoresents ending angle. */
	private static final Integer DEFAULT_B = 360;

	/**
	 * Method responsible for acquiring parameters specified by the user that define
	 * the starting and ending angles for the calculation process. This method
	 * prepares the collection of resulting objects and redirects to
	 * trigonometric.jsp for displaying purposes.
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String strA = req.getParameter("a");
		String strB = req.getParameter("b");

		Integer a;
		try {
			a = Integer.valueOf(strA);
		} catch (NumberFormatException | NullPointerException ignorable) {
			a = DEFAULT_A;
		}

		Integer b;
		try {
			b = Integer.valueOf(strB);
		} catch (NumberFormatException | NullPointerException ignorable) {
			b = DEFAULT_B;
		}

		if (a > b) {
			Integer copyA = a;
			a = b;
			b = copyA;
		}

		if (b > a + 720) {
			b = a + 720;
		}

		List<TrigonometryEncapsulation> list = new ArrayList<>();
		for (int i = a; i <= b; i++) {
			list.add(new TrigonometryEncapsulation(i, sin(toRadians(i)), cos(toRadians(i))));
		}

		HttpSession session = req.getSession();
		session.setAttribute("angles", list);

		req.getRequestDispatcher("/WEB-INF/pages/trigonometric.jsp").forward(req, resp);
	}

	/**
	 * The Class representing an encapsulation of an angle and its sine and cosine values.
	 */
	public static class TrigonometryEncapsulation {

		/** The decimal format. */
		private DecimalFormat decimalFormat;

		/** The value of the angle. */
		private int value;

		/** The sin of the angle. */
		private String sin;

		/** The cos of the angle. */
		private String cos;

		/**
		 * Instantiates a new trigonometry encapsulation.
		 *
		 * @param value
		 *            The value of the angle.
		 * @param sin
		 *            The sin of the angle.
		 * @param cos
		 *            The cos of the angle.
		 */
		public TrigonometryEncapsulation(int value, double sin, double cos) {
			decimalFormat = new DecimalFormat("#.##########");

			this.value = value;
			this.sin = decimalFormat.format(sin);
			this.cos = decimalFormat.format(cos);
		}

		/**
		 * Gets the value.
		 *
		 * @return the value
		 */
		public int getValue() {
			return value;
		}

		/**
		 * Gets the sin value.
		 *
		 * @return the sin value
		 */
		public String getSin() {
			return sin;
		}

		/**
		 * Gets the cos value.
		 *
		 * @return the cos value
		 */
		public String getCos() {
			return cos;
		}

	}
}
