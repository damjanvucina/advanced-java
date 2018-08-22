package hr.fer.zemris.java.hw16.jvdraw.actions;

import java.awt.Color;
import java.awt.Point;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

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
	public static final String JVD_EXTENSION = "jvd";
	public static final String[] EXPORT_EXTENSIONS = new String[] {"jpg", "jpeg", "gif", "png"};
	private static final String WHITESPACE = " ";
	public static final String LINE_REGEX = "LINE\\s(\\d+\\s){2}(\\d+\\s){2}(\\d+\\s\\d+\\s\\d+)";
	public static final String CIRCLE_REGEX = "CIRCLE\\s(\\d+\\s){2}(\\d+\\s){1}(\\d+\\s\\d+\\s\\d+)";
	public static final String FILLED_CIRCLE_REGEX = "FCIRCLE\\s(\\d+\\s){2}(\\d+\\s){1}(\\d+\\s){3}(\\d+\\s\\d+\\s\\d+)";

	private static FileNameExtensionFilter jvdFilter = new FileNameExtensionFilter(".jvd", "jvd");
	private static FileNameExtensionFilter exportFilter = new FileNameExtensionFilter("jpg, png and gif files", "jpg",
			"jpeg", "png", "gif");

	private static Pattern linePattern = Pattern.compile(LINE_REGEX);
	private static Pattern circlePattern = Pattern.compile(CIRCLE_REGEX);
	private static Pattern filledCirclePattern = Pattern.compile(FILLED_CIRCLE_REGEX);

	public static FileNameExtensionFilter getJvdFilter() {
		return jvdFilter;
	}

	public static FileNameExtensionFilter getExportFilter() {
		return exportFilter;
	}

	public static String getJvdExtension() {
		return JVD_EXTENSION;
	}
	
	public static String[] getExportExtensions() {
		return EXPORT_EXTENSIONS;
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

	public static List<GeometricalObject> fromFile(List<String> jvdLines) {
		List<GeometricalObject> objects = new ArrayList<>();

		for (String jvdLine : jvdLines) {
			if (linePattern.matcher(jvdLine).matches()) {
				objects.add(createLine(extractElements(jvdLine)));

			} else if (circlePattern.matcher(jvdLine).matches()) {
				objects.add(createCircle(extractElements(jvdLine)));

			} else if (filledCirclePattern.matcher(jvdLine).matches()) {
				objects.add(createFilledCircle(extractElements(jvdLine)));

			} else {
				return null;
			}
		}

		return objects;
	}

	//@formatter:off
	private static int[] extractElements(String jvdLine) {
		return Arrays.stream(jvdLine.split(WHITESPACE))
					 .skip(1) //skip LINE/CIRCLE/FCIRCLE identifier
					 .mapToInt(Integer::parseInt)
					 .toArray();
	}

	private static FilledCircle createFilledCircle(int[] elements) {
		return new FilledCircle(new Point(elements[0], elements[1]),
				  				new Point(elements[0], elements[1] + elements[2]),
				  				new Color(elements[3], elements[4], elements[5]),
				  				new Color(elements[6], elements[7], elements[8]));
	}

	private static Circle createCircle(int[] elements) {
		return new Circle(new Point(elements[0], elements[1]),
						  new Point(elements[0], elements[1] + elements[2]),
						  new Color(elements[3], elements[4], elements[5]));
	}

	
	private static Line createLine(int[] elements) {
		return new Line(new Point(elements[0], elements[1]),
						new Point(elements[2], elements[3]),
						new Color(elements[4], elements[5], elements[6]));
	}
	//@formatter:on

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

	public static boolean isInvalidExtension(Path path, List<String> validExtensions) {
		String p = String.valueOf(path);
		int numOfDots = p.length() - p.replace(".", "").length();
		
		String requestedExtension = null;
		if (numOfDots == 1) {
			requestedExtension = acquireExtension(p);
		}

		return numOfDots > 1 || (numOfDots == 1 && !validExtensions.contains(requestedExtension));
	}

	public static String acquireExtension(String p) {
		return p.substring(p.indexOf(".") + 1);
	}

	public static boolean isImageEdited(Path savedPath, List<GeometricalObject> currentlyDrawnObjects) {
		if (currentlyDrawnObjects.isEmpty()) {// canvas is empty
			return false;

		} else {
			if (savedPath == null) {// canvas is not empty and not saved
				return true;

			} else {
				return areJvdRepresentationsDifferent(savedPath, currentlyDrawnObjects);
			}
		}
	}

	private static boolean areJvdRepresentationsDifferent(Path savedPath,
			List<GeometricalObject> currentlyDrawnObjects) {

		String currentlyDrawnJvd = UtilityProvider.toJVD(currentlyDrawnObjects);

		List<String> jvdLines = UtilityProvider.loadFile(savedPath);
		List<GeometricalObject> savedObjects = UtilityProvider.fromFile(jvdLines);
		String savedJvd = UtilityProvider.toJVD(savedObjects);

		return !savedJvd.equals(currentlyDrawnJvd);
	}

	public static List<String> loadFile(Path filePath) {
		List<String> jvdLines = null;

		try {
			jvdLines = Files.readAllLines(filePath, StandardCharsets.UTF_8);
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		return jvdLines;
	}
	
	public static boolean extensionNotSet(Path savePath) {
		return !String.valueOf(savePath).contains(".");
	}

}
