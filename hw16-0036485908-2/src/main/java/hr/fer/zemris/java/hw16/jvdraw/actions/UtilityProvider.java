package hr.fer.zemris.java.hw16.jvdraw.actions;

import java.awt.Color;
import java.awt.Point;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import javax.swing.filechooser.FileNameExtensionFilter;

import hr.fer.zemris.java.hw16.jvdraw.geometry.Circle;
import hr.fer.zemris.java.hw16.jvdraw.geometry.FilledCircle;
import hr.fer.zemris.java.hw16.jvdraw.geometry.GeometricalObject;
import hr.fer.zemris.java.hw16.jvdraw.geometry.Line;

public class UtilityProvider {
	private static final String LINE = "LINE";
	private static final String CIRCLE = "CIRCLE";
	private static final String FILLED_CIRCLE = "FCIRCLE";
	private static final String ATTRIBUTE_SEPARATOR = " ";
	private static final String GEOM_OBJECT_SEPARATOR = "\n";
	public static final String JVD_EXTENSION = ".jvd";
	
	private static FileNameExtensionFilter jvdFilter  = new FileNameExtensionFilter(".jvd", "jvd");;
	
	public static FileNameExtensionFilter getJvdFilter() {
		return jvdFilter;
	}

	public static String toJVD(List<GeometricalObject> objects) {
		StringBuilder sbImage = new StringBuilder();

		for (GeometricalObject object : objects) {
			if (object instanceof Line) {
				sbImage.append(LINE);
				sbImage.append(acquireLineRepresentation((Line) object));

			} else if (object instanceof FilledCircle) {
				sbImage.append(FILLED_CIRCLE);
				sbImage.append(acquireFilledCircleRepresentation((FilledCircle) object));

			} else if (object instanceof Circle) {
				sbImage.append(CIRCLE);
				sbImage.append(acquireCircleRepresentation((Circle) object));

			} else {
				throw new IllegalArgumentException(
						"JVD transformation supported only for Line, Circle and FilledCircle; was: "
								+ object.getClass().getSimpleName());
			}

			sbImage.append(GEOM_OBJECT_SEPARATOR);
		}

		return sbImage.toString();
	}
	
	
	private static String acquireCircleRepresentation(Circle object) {
		StringBuilder sbCircle = new StringBuilder();

		sbCircle.append(ATTRIBUTE_SEPARATOR);
		sbCircle.append(extractCoordinates(object.getCenter()));
		sbCircle.append(ATTRIBUTE_SEPARATOR);
		sbCircle.append(object.calculateRadius());
		sbCircle.append(ATTRIBUTE_SEPARATOR);
		sbCircle.append(extractColor(object.getFgColor()));

		return sbCircle.toString();
	}

	private static String acquireFilledCircleRepresentation(FilledCircle object) {
		StringBuilder sbFilledCircle = new StringBuilder(acquireCircleRepresentation(object));

		sbFilledCircle.append(ATTRIBUTE_SEPARATOR);
		sbFilledCircle.append(extractColor(object.getBgColor()));

		return sbFilledCircle.toString();
	}

	private static String acquireLineRepresentation(Line object) {
		StringBuilder sbLine = new StringBuilder();

		sbLine.append(ATTRIBUTE_SEPARATOR);
		sbLine.append(extractCoordinates(object.getStartPoint()));
		sbLine.append(ATTRIBUTE_SEPARATOR);
		sbLine.append(extractCoordinates(object.getEndPoint()));
		sbLine.append(ATTRIBUTE_SEPARATOR);
		sbLine.append(extractColor(object.getFgColor()));

		return sbLine.toString();
	}

	private static Object extractColor(Color color) {
		StringBuilder sbColor = new StringBuilder();

		sbColor.append(color.getRed());
		sbColor.append(ATTRIBUTE_SEPARATOR);
		sbColor.append(color.getGreen());
		sbColor.append(ATTRIBUTE_SEPARATOR);
		sbColor.append(color.getBlue());

		return sbColor.toString();
	}

	private static String extractCoordinates(Point point) {
		StringBuilder sbCoordinates = new StringBuilder();

		sbCoordinates.append(point.x);
		sbCoordinates.append(ATTRIBUTE_SEPARATOR);
		sbCoordinates.append(point.y);

		return sbCoordinates.toString();
	}

	public static void saveJVD(Path savePath, List<GeometricalObject> objects) {
		String jvdRepresentation = UtilityProvider.toJVD(objects);
		byte[] bytes = jvdRepresentation.getBytes(StandardCharsets.UTF_8);
		
		try {
			Files.write(savePath, bytes);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	
	public static boolean isInvalidPath(Path path) {
		String p = String.valueOf(path);
		int numOfDots = p.length() - p.replace(".", "").length();
		
		return numOfDots > 1 || (numOfDots == 1 && !p.endsWith(JVD_EXTENSION));
	}

}
