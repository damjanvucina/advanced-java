package hr.fer.zemris.java.servlets;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 * The servlet class responsible for generating an xls file containing the band
 * names and number of votes each of the band received.
 * 
 * @author Damjan Vučina
 */
@WebServlet("/glasanje-xls")
public class GlasanjeXLSServlet extends HttpServlet {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Generates a an xls file containing the band names and number of votes each of
	 * the band received based on the attribute previously set by the
	 * GlasanjeRezultatiServlet class.
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		@SuppressWarnings("unchecked")
		Map<String, Integer> map = (Map<String, Integer>) req.getServletContext().getAttribute("results");

		generateXLS(map, resp);
	}

	/**
	 * Generates XLS file based on the map containing the band names and number of
	 * votes each of the band received..
	 *
	 * @param map
	 *            the map
	 * @param resp
	 *            the resp
	 */
	private void generateXLS(Map<String, Integer> map, HttpServletResponse resp) {
		resp.setContentType("application/vnd.ms-excel");
		resp.setHeader("Content-Disposition", "attachment; filename=voting.xls");

		try {
			HSSFWorkbook hwb = new HSSFWorkbook();

			HSSFSheet sheet = hwb.createSheet("Band Voting");
			HSSFRow rowhead = sheet.createRow((short) 0);

			rowhead.createCell((short) 0).setCellValue("Band");
			rowhead.createCell((short) 1).setCellValue("Votes");

			int rowCounter = 1;
			for (Map.Entry<String, Integer> entry : map.entrySet()) {
				HSSFRow row = sheet.createRow((short) rowCounter++);
				row.createCell((short) 0).setCellValue(entry.getKey());
				row.createCell((short) 1).setCellValue(entry.getValue());
			}

			hwb.write(resp.getOutputStream());
			hwb.close();
		} catch (Exception ex) {
			System.out.println(ex);

		}
	}
}
