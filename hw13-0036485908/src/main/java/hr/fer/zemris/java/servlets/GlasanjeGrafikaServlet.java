package hr.fer.zemris.java.servlets;

import java.awt.Dimension;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
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

@WebServlet("/glasanje-grafika")
public class GlasanjeGrafikaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("image/png");
		OutputStream writeStream = resp.getOutputStream();

		PieDataset dataset = createDataset(req);
		JFreeChart chart = createChart(dataset, "Voting visual representation");
		ChartPanel chartPanel = new ChartPanel(chart);
		chartPanel.setPreferredSize(new Dimension(400, 400));

		ChartUtils.writeChartAsPNG(writeStream, chart, 400, 400);
		writeStream.flush();
		writeStream.close();
	}

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

	private PieDataset createDataset(HttpServletRequest req) {
		DefaultPieDataset result = new DefaultPieDataset();
		@SuppressWarnings("unchecked")
		Map<String, Integer> map = (Map<String, Integer>) req.getServletContext().getAttribute("results");
		map.forEach((k, v) ->{
			if(v > 0) {
				result.setValue(k, v);
			}
		});
		return result;

	}

}
