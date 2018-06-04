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

@WebServlet("/trigonometric")
public class TrigonometricTableServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Integer DEFAULT_A = 0;
	private static final Integer DEFAULT_B = 360;

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
		for(int i = a; i <= b; i++) {
			list.add(new TrigonometryEncapsulation(i, sin(toRadians(i)), cos(toRadians(i))));
		}
		
		HttpSession session = req.getSession();
		session.setAttribute("angles", list);
		
		req.getRequestDispatcher("/WEB-INF/pages/trigonometric.jsp").forward(req, resp);
	}
	
	public static class TrigonometryEncapsulation{
		private DecimalFormat decimalFormat;
		private int value;
		private String sin;
		private String cos;
		
		public TrigonometryEncapsulation(int value, double sin, double cos) {
			decimalFormat = new DecimalFormat("#.##########");
			
			this.value = value;
			this.sin = decimalFormat.format(sin);
			this.cos = decimalFormat.format(cos);
		}

		public int getValue() {
			return value;
		}

		public String getSin() {
			return sin;
		}

		public String getCos() {
			return cos;
		}
		
		
	}
}
