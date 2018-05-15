package hr.fer.zemris.java.gui.charts;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import static java.awt.BorderLayout.NORTH;
import static java.awt.BorderLayout.CENTER;

public class BarChartDemo extends JFrame {
	private static final String WHITESPACE = " ";
	private static final long serialVersionUID = 1L;
	private static final int DATA_START = 0;
	private static final int DATA_END = 6;
	private static final String XYVALUE_VALIDATOR = "(\\d+,\\d+\\s?)+";

	public BarChartDemo(Path path, BarChart chart) {
		setLocation(50, 50);
		setSize(700, 700);
		setTitle("BarChart Demonstration");
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		initGUI(path, chart);
	}

	private void initGUI(Path path, BarChart chart) {
		Container cp = getContentPane();
		cp.setLayout(new BorderLayout());
		cp.setBackground(Color.WHITE);
		
		JLabel pathLabel = new JLabel(path.toString());
		pathLabel.setHorizontalAlignment(JLabel.CENTER);
		pathLabel.setBorder(BorderFactory.createRaisedSoftBevelBorder());
		cp.add(pathLabel, NORTH);
		
		cp.add(new BarChartComponent(chart), CENTER);
	}

	public static void main(String[] args) {
		if (args.length != 1) {
			throw new IllegalArgumentException("Invalid number of arguments, was: " + args.length);
		}

		Path path = Paths.get(args[0]);

		if (Files.notExists(path)) {
			throw new IllegalArgumentException("Provided path does not exist.");
		}
		if (Files.isDirectory(path)) {
			throw new IllegalArgumentException("Provided path must be a regular file path, not a directory path");
		}

		List<String> list = new LinkedList<>();
		//@formatter:off
			try {
				list = Files.newBufferedReader(path, Charset.defaultCharset())
							.lines()
							.collect(Collectors.toList())
							.subList(DATA_START, DATA_END);
				
			} catch (IOException e) {
				System.out.println("Cannot read given file.");
			}
			//@formatter:off
			
			Pattern pattern = Pattern.compile(XYVALUE_VALIDATOR);
			Matcher matcher = pattern.matcher(list.get(2));
			if(!matcher.matches()) {
				throw new IllegalArgumentException("Invalid XYValue points format.");
			}
			
			String xDescription = list.get(0);
			String yDescription = list.get(1);
			
			//@formatter:off
			List<XYValue> values = Stream.of(list.get(2).split(WHITESPACE))
										 .map(s -> {
											  int x = Integer.parseInt(s.substring(0, s.indexOf(",")));
											  int y = Integer.parseInt(s.substring(s.indexOf(",") +1 ));
											  return new XYValue(x, y);
										 }).collect(Collectors.toList());
			//@formatter:on

		int minY = Integer.parseInt(list.get(3));
		int maxY = Integer.parseInt(list.get(4));
		int gapY = Integer.parseInt(list.get(5));

		BarChart chart = new BarChart(values, xDescription, yDescription, minY, maxY, gapY);

		SwingUtilities.invokeLater(() -> {
			BarChartDemo chartDemo = new BarChartDemo(path, chart);
			chartDemo.setVisible(true);
		});
	}
}
