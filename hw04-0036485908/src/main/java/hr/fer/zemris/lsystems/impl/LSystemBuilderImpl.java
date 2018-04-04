package hr.fer.zemris.lsystems.impl;

import static java.lang.Math.cos;
import static java.lang.Math.pow;
import static java.lang.Math.sin;
import static java.lang.Math.toRadians;

import java.awt.Color;
import java.util.function.Function;

import hr.fer.zemris.java.custom.collections.Dictionary;
import hr.fer.zemris.lsystems.LSystem;
import hr.fer.zemris.lsystems.LSystemBuilder;
import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.commands.ColorCommand;
import hr.fer.zemris.lsystems.impl.commands.DrawCommand;
import hr.fer.zemris.lsystems.impl.commands.PopCommand;
import hr.fer.zemris.lsystems.impl.commands.PushCommand;
import hr.fer.zemris.lsystems.impl.commands.RotateCommand;
import hr.fer.zemris.lsystems.impl.commands.ScaleCommand;
import hr.fer.zemris.lsystems.impl.commands.SkipCommand;
import hr.fer.zemris.math.Vector2D;

/**
 * The class that represents an object that implements a Lindermayer fractal
 * system. An L-system consists of an alphabet of symbols that is used to make
 * strings, a collection of production rules that expands each symbol into some
 * larger string of symbols, an initial "axiom" string from which the
 * construction and the mechanism for translating the generated strings into
 * geometric structures begins. The rules of the L-system grammar are applied
 * iteratively starting from the initial state. As many rules as possible are
 * applied simultaneously, per iteration. It provides a method for generating a
 * string as the result of iterative application of grammar rules on initial
 * axiom as well as a method for visualing constructed fractals.
 * 
 * @author Damjan Vučina
 */
public class LSystemBuilderImpl implements LSystemBuilder {

	/**
	 * The collection that stores commands that are to be performed based of
	 * specified grammar for a certain fractal.
	 */
	Dictionary commands;

	/**
	 * The collection that stores productions that are to be performed based of
	 * specified grammar for a certain fractal. The rules of the L-system grammar
	 * are applied iteratively starting from the initial state. As many rules as
	 * possible are applied simultaneously, per iteration.
	 */
	Dictionary productions;

	/** The unit length of a fractal's segment. */
	private double unitLength;

	/**
	 * The unit length degree scaler a fractal's segment. It is used to keep the
	 * fractal's dimensions in place when drawing its segments iteratively.
	 */
	private double unitLengthDegreeScaler;

	/** The starting state of the fractal represented in form of a Vector2D. */
	private Vector2D origin;

	/**
	 * The starting angle of the fractal. Vector2D with angle zero in the applied
	 * coordinate system is parallel with abscissa.
	 */
	private double angle;

	/**
	 * The initial "axiom" string from which the construction begins.The rules of
	 * the L-system grammar are applied iteratively starting from the initial state,
	 * i.e. axiom. As many rules as possible are applied simultaneously, per
	 * iteration.
	 */
	private String axiom;

	/**
	 * Instantiates a new object that implements a Lindermayer fractal system. It
	 * provides a method for generating a string as the result of iterative
	 * application of grammar rules on initial axiom as well as a method for
	 * visualing constructed fractals. Default unit length is 0.1. Default unit
	 * length degree scaler is 1. Default color for drawing fractals is black.
	 */
	public LSystemBuilderImpl() {
		commands = new Dictionary();
		productions = new Dictionary();

		unitLength = 0.1;
		unitLengthDegreeScaler = 1;
		origin = new Vector2D(0, 0);
		angle = 0;
		axiom = "";
	}

	/**
	 * A method that is used for creating and visualizing a fractal in Lindermayer
	 * fractal system. The rules of the L-system grammar are applied iteratively
	 * starting from the initial state. As many rules as possible are applied
	 * simultaneously, per iteration. Ultimately, it visualizes the constructed
	 * fractal.
	 */
	@Override
	public LSystem build() {
		return new LSystem() {

			/**
			 * Method that is used for iterative application of the rules of the L-system
			 * grammar starting from the initial state. As many rules as possible are
			 * applied simultaneously, per iteration. As many rules as possible are applied
			 * simultaneously, per iteration.
			 */
			@Override
			public String generate(int level) {
				if (level < 0) {
					throw new IllegalArgumentException("Level cannot be set to negative value, was: " + level);

				} else {
					String processed = axiom;
					String finalString = processed;

					for (int i = 0; i < level; i++) {
						processed = finalString;
						StringBuilder stringBuilder = new StringBuilder();
						for (int j = 0, length = processed.length(); j < length; j++) {
							Object production = productions.get(processed.charAt(j));

							if (production == null) {
								stringBuilder.append(processed.charAt(j));
								continue;
							}

							stringBuilder.append(production);
						}

						finalString = stringBuilder.toString();
					}
					return finalString;
				}
			}

			/**
			 * Method that is used for reproducing a visual representation of the fractal
			 * after all the grammar rules have been applied. Default unit length is 0.1.
			 * Default unit length degree scaler is 1. Default color for drawing fractals is
			 * black.
			 */
			@Override
			public void draw(int level, Painter painter) {
				Context context = new Context();
				TurtleState turtleState = new TurtleState(origin.copy(),
						new Vector2D(cos(toRadians(angle)), sin(toRadians(angle))), Color.BLACK,
						unitLength * pow(unitLengthDegreeScaler, level));
				context.pushState(turtleState);

				String processed = generate(level);

				for (char c : processed.toCharArray()) {
					Object command = commands.get(c);

					if (command == null) {
						continue;
					}

					((Command) command).execute(context, painter);
				}
			}
		};
	}

	/**
	 * Method that is used for creating a fractal in Lindermayer fractal system by
	 * parsing the user's text input.
	 * 
	 * @throws IllegalArgumentException
	 *             if user requested an unsupported directive.
	 */
	@Override
	public LSystemBuilder configureFromText(String[] lines) {

		for (String line : lines) {

			// instant line = line.toLowerCase() conversion would mess up productions since
			// they ARE case sensitive
			if (line.toLowerCase().startsWith("origin")) {
				process(normalize(line, "origin".length()), (parameters) -> {
					String[] arguments = parameters.split(" ");
					return setOrigin(Double.parseDouble(arguments[0]), Double.parseDouble(arguments[1]));
				});

			} else if (line.toLowerCase().startsWith("angle")) {
				process(normalize(line, "angle".length()), (parameters) -> {
					String[] arguments = parameters.split(" ");
					return setAngle(Double.parseDouble(arguments[0]));
				});

			} else if (line.toLowerCase().startsWith("unitlengthdegreescaler")) {
				process(normalize(line, "unitLengthDegreeScaler".length()), (parameters) -> {
					String arguments = parameters.replace(" ", "");
					double factor = performCalculation(arguments);
					return setUnitLengthDegreeScaler(factor);
				});

			} else if (line.toLowerCase().startsWith("unitlength")) {
				process(normalize(line, "unitLength".length()), (parameters) -> {
					String[] arguments = parameters.split(" ");
					return setUnitLength(Double.parseDouble(arguments[0]));
				});

			} else if (line.toLowerCase().startsWith("command")) {
				process(normalize(line, "command".length()), (parameters) -> {
					String[] arguments = parameters.split(" ");
					return registerCommand(arguments[0].charAt(0), parameters.substring(arguments[0].length()).trim());
				});

			} else if (line.toLowerCase().startsWith("axiom")) {
				process(normalize(line, "axiom".length()), (parameters) -> {
					String[] arguments = parameters.split(" ");
					return setAxiom(arguments[0]);
				});

			} else if (line.toLowerCase().startsWith("production")) {
				process(normalize(line, "production".length()), (parameters) -> {
					String[] arguments = parameters.split(" ");
					return registerProduction(arguments[0].charAt(0),
							parameters.substring(arguments[0].length()).trim());
				});

			} else if (line.equals("")) {
				continue;

			} else {
				throw new IllegalArgumentException("Unsupported directive, was: " + line);
			}
		}
		return this;
	}

	/**
	 * Helper method that performs the calculation process in case the factor for
	 * unit scaling is not specified in the form of a single number but a simple
	 * mathematical operation. Supported mathematical operations are addition,
	 * subtraction, multiplication and division.
	 *
	 * @param arguments
	 *            the simple mathematical operation
	 * @return the result of the mathematical operation
	 */
	private double performCalculation(String arguments) {
		int indexOfOperator = findIndexOfOperator(arguments);

		String firstArgument = arguments.substring(0, indexOfOperator);
		String operator = arguments.substring(indexOfOperator, indexOfOperator + 1);
		String secondArgument = arguments.substring(indexOfOperator + 1);

		switch (operator) {
		case "+":
			return Double.parseDouble(firstArgument) + Double.parseDouble(secondArgument);

		case "-":
			return Double.parseDouble(firstArgument) - Double.parseDouble(secondArgument);

		case "*":
			return Double.parseDouble(firstArgument) * Double.parseDouble(secondArgument);

		case "/":
			return Double.parseDouble(firstArgument) / Double.parseDouble(secondArgument);

		default:
			throw new IllegalArgumentException("Unknown calculation operation requested.");
		}

	}

	/**
	 * Helper method that enables easier text input parsing. It used for finding the
	 * index of operator. Supported mathematical operations are addition(+),
	 * subtraction(-), multiplication(*) and division(/).
	 *
	 * @param arguments
	 *            the simple mathematical operation the text input
	 * @return the the index of operator
	 */
	private int findIndexOfOperator(String arguments) {
		for (int i = 0, length = arguments.length(); i < length; i++) {
			if (arguments.charAt(i) == '+' || arguments.charAt(i) == '-' || arguments.charAt(i) == '*'
					|| arguments.charAt(i) == '/') {
				return i;
			}
		}
		return -1;
	}

	/**
	 * Processes the given object that implements Function functional interface.
	 *
	 * @param line
	 *            the user's text input
	 * @param action
	 *            the object that implements Function functional interface.
	 * @return the instance of LSystemBuilder class
	 */
	private LSystemBuilder process(String line, Function<String, LSystemBuilder> action) {
		try {
			return action.apply(line);
		} catch (NumberFormatException | NullPointerException e) {
			System.out.println("Invalid directive format");
		}

		return null;
	}

	/**
	 * Normalizes the line given in method argument by cutting its starting tag
	 * (i.e. draw/rotate etc) and trims it leaving behind only user specified
	 * command arguments.
	 *
	 * @param line
	 *            the user's text input
	 * @param cutoff
	 *            the lentgh of the starting tag (i.e. draw/rotate etc)
	 * @return the string representation of user specified command arguments
	 */
	private String normalize(String line, int cutoff) {
		return line.substring(cutoff).trim();
	}

	/**
	 * Identifies the user specified command and stores it to the appropriate
	 * dictionary of commands. Two commands are considered equal if they have equal
	 * trigger symbols and in that case newly inserted command overwrites the old
	 * one.
	 */
	@Override
	public LSystemBuilder registerCommand(char symbol, String action) {
		Command command = identifyCommand(action);
		commands.put(symbol, command);
		return this;
	}

	/**
	 * Helper method used for identifying the command based of its name. Supported
	 * commands are draw, skip, scale, rotate, push, pop and color. Draw command
	 * calculates the turtle's finish position, draws the line with the user
	 * specified color and remembers new position of the turtle. Skip command does
	 * the very same only it does not draw any line, i.e. the line is invisible.
	 * Scale command scales turtle's step size. Rotate command changes the direction
	 * of the next commands performed by the turtle. Push command pushes copy of the
	 * current turtle state to stack. Pop command pops a state from the stack. Color
	 * command changes the color of the lines drawn by the turtle. "Turtle" is a
	 * synonym for an object that passes by and draws the requested fractal.
	 *
	 * @param command
	 *            the command
	 * @return the command
	 */
	private Command identifyCommand(String command) {
		if (command.startsWith("draw")) {
			double factor = extractFactor(normalize(command, "draw".length()));
			return new DrawCommand(factor);

		} else if (command.startsWith("skip")) {
			double factor = extractFactor(normalize(command, "skip".length()));
			return new SkipCommand(factor);

		} else if (command.startsWith("scale")) {
			double factor = extractFactor(normalize(command, "scale".length()));
			return new ScaleCommand(factor);

		} else if (command.startsWith("rotate")) {
			double factor = extractFactor(normalize(command, "rotate".length()));
			return new RotateCommand(factor);

		} else if (command.startsWith("push")) {
			return (new PushCommand());

		} else if (command.startsWith("pop")) {
			return new PopCommand();

		} else if (command.startsWith("color")) {
			Color color = extractColor(normalize(command, "color".length()));
			return new ColorCommand(color);

		} else {
			throw new IllegalArgumentException("Unsupported command request. Command was: " + command);
		}
	}

	/**
	 * Helper method used for extracting the color from user's text input.
	 *
	 * @param color
	 *            the string representation of the color specified by user in text
	 *            input
	 * @return the color specified by user in text input
	 */
	private Color extractColor(String color) {
		try {
			return Color.decode("#" + color);
		} catch (NumberFormatException e) {
			System.out.println("Invalid hexadecimal representation of the requested color.");
		}

		return null;
	}

	/**
	 * Helper method used for extracting the factor from user's text input.
	 *
	 * @param factor
	 *            the factor specified by the user in the text input
	 * @return the double value of the factor
	 */
	private double extractFactor(String factor) {
		try {
			return Double.parseDouble(factor);
		} catch (NumberFormatException | NullPointerException e) {
			System.out.println("Invalid command argument format.");
		}

		return 0;
	}

	/**
	 * Stores the production provided in the argument to the appropriate dictionary
	 * of productions. Two commands are considered equal if they have equal trigger
	 * symbols and in that case newly inserted command overwrites the old one. The
	 * productions of the L-system grammar are applied iteratively starting from the
	 * initial state. As many rules as possible are applied simultaneously, per
	 * iteration.
	 */
	@Override
	public LSystemBuilder registerProduction(char symbol, String production) {
		productions.put(symbol, production);
		return this;
	}

	/**
	 * Sets the direction of the next commands performed by the turtle. "Turtle" is
	 * a synonym for an object that passes by and draws the requested fractal.
	 */
	@Override
	public LSystemBuilder setAngle(double angle) {
		this.angle = angle;
		return this;
	}

	/**
	 * Sets the axiom, i.e. an initial string from which the construction and the
	 * mechanism for translating the generated strings into geometric structures
	 * begins.
	 */
	@Override
	public LSystemBuilder setAxiom(String axiom) {
		this.axiom = axiom;
		return this;
	}

	/**
	 * Sets the initial position of turtle in the coordinate system. "Turtle" is a
	 * synonym for an object that passes by and draws the requested fractal.
	 */
	@Override
	public LSystemBuilder setOrigin(double x, double y) {
		origin.setX(x);
		origin.setY(y);
		return this;
	}

	/**
	 * Sets the size of the unit length step the turtle performs. "Turtle" is a
	 * synonym for an object that passes by and draws the requested fractal.
	 */
	@Override
	public LSystemBuilder setUnitLength(double unitLength) {
		this.unitLength = unitLength;
		return this;
	}

	/**
	 * Sets the scaling factor for the unit length step the turtle performs.
	 * "Turtle" is a synonym for an object that passes by and draws the requested
	 * fractal.
	 */
	@Override
	public LSystemBuilder setUnitLengthDegreeScaler(double unitLengthDegreeScaler) {
		this.unitLengthDegreeScaler = unitLengthDegreeScaler;
		return this;
	}
}
