package hr.fer.zemris.java.servlets;

import java.awt.Dimension;
import java.io.IOException;
import java.io.OutputStream;

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
 * The class responsible for the creation of a demonstration pie chart with
 * dummy data. The free jfreechart library is used for the process of creating
 * the chart.
 * 
 * @author Damjan Vučina
 */
@WebServlet("/reportImage")
public class ReportImageServlet extends HttpServlet {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Method assigned with the task of creating a dummy dataset to be shown on the
	 * pie-chart, creating the chart itself and writing the generated chart to the
	 * response stream as png image.
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("image/png");
		OutputStream writeStream = resp.getOutputStream();

		PieDataset dataset = createDataset();
		JFreeChart chart = createChart(dataset, "Operating Systems Distribution");
		ChartPanel chartPanel = new ChartPanel(chart);
		chartPanel.setPreferredSize(new Dimension(500, 500));

		ChartUtils.writeChartAsPNG(writeStream, chart, 500, 500);
	}

	/**
	 * Creates the chart with dummy values.
	 *
	 * @param dataset
	 *            the dummy dataset
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
	 * Creates the dummy dataset to be shown on the pie-chart.
	 *
	 * @return the pie dataset
	 */
	private PieDataset createDataset() {
		DefaultPieDataset result = new DefaultPieDataset();
		result.setValue("Linux", 29);
		result.setValue("Mac", 20);
		result.setValue("Windows", 51);
		return result;

	}

}
