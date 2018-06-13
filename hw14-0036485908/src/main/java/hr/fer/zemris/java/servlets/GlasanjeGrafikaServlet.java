package hr.fer.zemris.java.servlets;

import java.awt.Dimension;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.chart.util.Rotation;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;

/**
 * The servlet class responsible for generating a pie-chart representing the
 * number of votes for each band. Bands that do not have a single vote at the
 * time of invocation are not displayed on the chart.
 * 
 * @author Damjan Vučina
 */
@WebServlet("/servleti/glasanje-grafika")
public class GlasanjeGrafikaServlet extends HttpServlet {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Generates a pie-chart representing the number of votes for each band based on
	 * the current standings as can be seen by downloading the designated xls file.
	 * Bands that do not have a single vote at the time of invocation are not
	 * displayed on the chart.
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("image/png");
		OutputStream writeStream = resp.getOutputStream();

		PieDataset dataset = createDataset();
		JFreeChart chart = createChart(dataset, "Voting visual representation");
		ChartPanel chartPanel = new ChartPanel(chart);
		chartPanel.setPreferredSize(new Dimension(400, 400));

		ChartUtils.writeChartAsPNG(writeStream, chart, 400, 400);
		writeStream.flush();
		writeStream.close();
	}

	/**
	 * Creates the pie-chart using the current band-vote as can be seen by
	 * downloading the designated xls file. Bands that do not have a single vote at
	 * the time of invocation are not displayed on the chart.
	 *
	 * @param dataset
	 *            the dataset
	 * @param title
	 *            the title of the chart
	 * @return the chart
	 */
	private JFreeChart createChart(PieDataset dataset, String title) {

		JFreeChart chart = ChartFactory.createPieChart3D(title, // chart title
				dataset, // data
				true, // include legend
				true, false);

		PiePlot3D plot = (PiePlot3D) chart.getPlot();
		plot.setStartAngle(290);
		plot.setDirection(Rotation.CLOCKWISE);
		plot.setForegroundAlpha(0.5f);
		return chart;

	}

	/**
	 * Creates the dataset by acquring the number of votes for each band from the
	 * txt file representing a database.
	 *
	 * @param req
	 *            the request
	 * @return the chart dataset
	 */
	private PieDataset createDataset() {
		DefaultPieDataset result = new DefaultPieDataset();
		result.setValue("Linux", 29);
		result.setValue("Mac", 20);
		result.setValue("Windows", 51);
		return result;

	}

}
