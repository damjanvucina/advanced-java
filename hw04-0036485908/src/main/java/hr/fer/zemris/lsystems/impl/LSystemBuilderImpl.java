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

public class LSystemBuilderImpl implements LSystemBuilder {

	Dictionary commands;
	Dictionary productions;

	private double unitLength;
	private double unitLengthDegreeScaler;
	private Vector2D origin;
	private double angle;
	private String axiom;

	public LSystemBuilderImpl() {
		commands = new Dictionary();
		productions = new Dictionary();

		unitLength = 0.1;
		unitLengthDegreeScaler = 1;
		origin = new Vector2D(0, 0);
		angle = 0;
		axiom = "";
	}

	@Override
	public LSystem build() {
		return new LSystem() {

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

					// identifyCommand((String) command, context, painter);
					//performCommand((Command) command, context, painter);
					((Command)command).execute(context, painter);
				}
			}
		};
	}

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

	private int findIndexOfOperator(String arguments) {
		for (int i = 0, length = arguments.length(); i < length; i++) {
			if (arguments.charAt(i) == '+' || arguments.charAt(i) == '-' || arguments.charAt(i) == '*'
					|| arguments.charAt(i) == '/') {
				return i;
			}
		}
		return -1;
	}

	private LSystemBuilder process(String line, Function<String, LSystemBuilder> action) {
		try {
			return action.apply(line);
		} catch (NumberFormatException | NullPointerException e) {
			System.out.println("Invalid directive format");
		}

		return null;
	}

	private String normalize(String line, int cutoff) {
		return line.substring(cutoff).trim();
	}

	@Override
	public LSystemBuilder registerCommand(char symbol, String action) {
		Command command = identifyCommand(action);
		commands.put(symbol, command);
		return this;
	}

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

	private Color extractColor(String color) {
		try {
			 return Color.decode("#" + color);
		} catch (NumberFormatException e) {
			System.out.println("Invalid hexadecimal representation of the requested color.");
		}

		return null;
	}

	private double extractFactor(String factor) {
		try {
			return Double.parseDouble(factor);
		} catch (NumberFormatException | NullPointerException e) {
			System.out.println("Invalid command argument format.");
		}

		return 0;
	}

	@Override
	public LSystemBuilder registerProduction(char symbol, String production) {
		productions.put(symbol, production);
		return this;
	}

	@Override
	public LSystemBuilder setAngle(double angle) {
		this.angle = angle;
		return this;
	}

	@Override
	public LSystemBuilder setAxiom(String axiom) {
		this.axiom = axiom;
		return this;
	}

	@Override
	public LSystemBuilder setOrigin(double x, double y) {
		origin.setX(x);
		origin.setY(y);
		return this;
	}

	@Override
	public LSystemBuilder setUnitLength(double unitLength) {
		this.unitLength = unitLength;
		return this;
	}

	@Override
	public LSystemBuilder setUnitLengthDegreeScaler(double unitLengthDegreeScaler) {
		this.unitLengthDegreeScaler = unitLengthDegreeScaler;
		return this;
	}
}
