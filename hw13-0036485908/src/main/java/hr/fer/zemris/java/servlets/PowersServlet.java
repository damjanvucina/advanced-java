package hr.fer.zemris.java.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 * The servlet class responsible for producing an xsl file containing the powers
 * of the numbers selected by the user. User selects starting number a, ending
 * number b and number of sheets n. On page i there is table with two columns.
 * The first column contains integer numbers from a to b. The second column
 * contains i-th powers of these numbers. a and b must be between -100 and 100.
 * n must be between 1 and 5.
 * 
 * @author Damjan Vučina
 */
@WebServlet("/powers")
public class PowersServlet extends HttpServlet {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Validates the numbers provided by the user, and starts the process of
	 * generating the xsl file with the specified paramaters.
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		int a = 0;
		int b = 0;
		int n = 0;

		int status;

		try {
			a = Integer.parseInt(req.getParameter("a"));
			b = Integer.parseInt(req.getParameter("b"));
			n = Integer.parseInt(req.getParameter("n"));
		} catch (NumberFormatException | NullPointerException e) {
			errorOccurred("Invalid parameters given. Each number must be defines as an integer.", req, resp);
			return;
		}

		status = validateLimits(a, b, n, req, resp);
		if (status == -1) {
			return;
		}

		generateXLS(a, b, n, resp);
	}

	/**
	 * Validates limits provided by the user.
	 *
	 * @param a
	 *            the lower limit. Must be between -100 and 100.
	 * @param b
	 *            the upper limit. Must be between -100 and 100.
	 * @param n
	 *            the number of sheets, i.e. the top power to be calculated
	 * @param req
	 *            the request
	 * @param resp
	 *            the response
	 * @return the status defining whether the user's input is in conformance with
	 *         the specified format
	 */
	private int validateLimits(int a, int b, int n, HttpServletRequest req, HttpServletResponse resp) {
		if (a < -100 || a > 100) {
			errorOccurred("a must be between -100 and 100, was: " + a, req, resp);
			return -1;
		}

		if (b < -100 || b > 100) {
			errorOccurred("b must be between -100 and 100, was: " + b, req, resp);
			return -1;
		}

		if (n < 1 || n > 5) {
			errorOccurred("n must be between 1 and 5, was: " + n, req, resp);
			return -1;
		}

		return 0;
	}

	/**
	 * Signals that an error occurred by redirecting to a error page with the error
	 * message.
	 *
	 * @param errorMessage
	 *            the error message
	 * @param req
	 *            the request
	 * @param resp
	 *            the response
	 */
	private void errorOccurred(String errorMessage, HttpServletRequest req, HttpServletResponse resp) {
		try {
			req.setAttribute("errorMessage", errorMessage);
			req.getRequestDispatcher("/WEB-INF/pages/XLSGeneratorError.jsp").forward(req, resp);
		} catch (ServletException | IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Generates XLS file. On page i there is table with two columns. The first
	 * column contains integer numbers from a to b. The second column contains i-th
	 * powers of these numbers. a and b must be between -100 and 100. n must be
	 * between 1 and 5.
	 *
	 * @param a
	 *            the lower limit. Must be between -100 and 100.
	 * @param b
	 *            the upper limit. Must be between -100 and 100.
	 * @param n
	 *            the number of sheets, i.e. the top power to be calculated
	 * @param resp
	 *            the response
	 */
	private void generateXLS(int a, int b, int n, HttpServletResponse resp) {
		resp.setContentType("application/vnd.ms-excel");
		resp.setHeader("Content-Disposition", "attachment; filename=powers.xls");

		try {
			HSSFWorkbook hwb = new HSSFWorkbook();

			for (int i = 1; i <= n; i++) {
				HSSFSheet sheet = hwb.createSheet("Sheet " + i);
				HSSFRow rowhead = sheet.createRow((short) 0);

				rowhead.createCell((short) 0).setCellValue("Number");
				rowhead.createCell((short) 1).setCellValue(i + ". power");

				int rowOffset = a - 1;
				for (int j = a; j <= b; j++) {
					HSSFRow row = sheet.createRow((short) j - rowOffset);
					row.createCell((short) 0).setCellValue(j);
					row.createCell((short) 1).setCellValue(Math.pow(j, i));
				}
			}

			hwb.write(resp.getOutputStream());
			hwb.close();
		} catch (Exception ex) {
			System.out.println(ex);

		}
	}
}
