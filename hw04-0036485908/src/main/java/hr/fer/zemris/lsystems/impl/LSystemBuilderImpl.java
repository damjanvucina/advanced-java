package hr.fer.zemris.lsystems.impl;

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

import static java.lang.Math.pow;
import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static java.lang.Math.toRadians;

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
				if (level < 1) {
					throw new IllegalArgumentException("Level cannot be set to negative value");
					
				} else {
					String processed = axiom;

					for (int i = 0; i < level; i++) {
						for (int j = 0, length = processed.length(); j < length; j++) {
							String production = productions.get(processed.charAt(j)).toString();

							if (production == null) {
								continue;
							}

							processed.replaceAll(String.valueOf(processed.charAt(j)), production);
						}
					}

					return processed;
				}
			}

			@Override
			public void draw(int level, Painter painter) {
				Context context = new Context();
				TurtleState turtleState = new TurtleState(origin,
						new Vector2D(cos(toRadians(angle)), sin(toRadians(angle))), Color.BLACK,
						unitLength * pow(unitLengthDegreeScaler, level));
				context.pushState(turtleState);

				String processed = generate(level);

				for (char c : processed.toCharArray()) {
					String command = commands.get(c).toString();

					if (command == null) {
						continue;
					}

					identifyCommand(command, context, painter);
				}
			}

			private void identifyCommand(String command, Context context, Painter painter) {
				if (command.startsWith("draw")) {
					double factor = extractFactor(normalize(command, "draw".length()));
					performCommand(new DrawCommand(factor), context, painter);					

				} else if (command.startsWith("skip")) {
					double factor = extractFactor(normalize(command, "skip".length()));
					performCommand(new SkipCommand(factor), context, painter);

				} else if (command.startsWith("scale")) {
					double factor = extractFactor(normalize(command, "scale".length()));
					performCommand(new ScaleCommand(factor), context, painter);

				} else if (command.startsWith("rotate")) {
					double factor = extractFactor(normalize(command, "rotate".length()));
					performCommand(new RotateCommand(factor), context, painter);

				} else if (command.startsWith("push")) {
					performCommand(new PushCommand(), context, painter);
					
				} else if (command.startsWith("pop")) {
					performCommand(new PopCommand(), context, painter);
					
				} else if (command.startsWith("color")) {
					Color color = extractColor(normalize(command, "color".length()));
					performCommand(new ColorCommand(color), context, painter);
					
				} else {
					throw new IllegalArgumentException("Unsupoorted command request. Command was: " + command);
				}
			}
			
			private Color extractColor(String color) {
				try {
					return Color.decode(color);
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

			private void performCommand(Command command, Context context, Painter painter) {
				command.execute(context, painter);
			}

		};
	}

	@Override
	public LSystemBuilder configureFromText(String[] lines) {

		for (String line : lines) {

			if (line.startsWith("origin")) {
				return process(normalize(line, "origin".length()), (parameters) -> {
					String[] arguments = parameters.split(" ");
					return setOrigin(Double.parseDouble(arguments[0]), Double.parseDouble(arguments[1]));
				});

			} else if (line.startsWith("angle")) {
				return process(normalize(line, "angle".length()), (parameters) -> {
					String[] arguments = parameters.split(" ");
					return setAngle(Double.parseDouble(arguments[0]));
				});

			} else if (line.startsWith("unitLength")) {
				return process(normalize(line, "unitLength".length()), (parameters) -> {
					String[] arguments = parameters.split(" ");
					return setUnitLength(Double.parseDouble(arguments[0]));
				});

			} else if (line.startsWith("unitLengthDegreeScaler")) {
				return process(normalize(line, "unitLengthDegreeScaler".length()), (parameters) -> {
					String[] arguments = parameters.split(" ");
					return setUnitLengthDegreeScaler(Double.parseDouble(arguments[0]));
				});

			} else if (line.startsWith("command")) {
				return process(normalize(line, "command".length()), (parameters) -> {
					String[] arguments = parameters.split(" ");
					return registerCommand(arguments[0].charAt(0), parameters.substring(arguments[0].length()));
				});

			} else if (line.startsWith("axiom")) {
				return process(normalize(line, "axiom".length()), (parameters) -> {
					String[] arguments = parameters.split(" ");
					return setAxiom(arguments[0]);
				});

			} else if (line.startsWith("production")) {
				return process(normalize(line, "production".length()), (parameters) -> {
					String[] arguments = parameters.split(" ");
					return registerProduction(arguments[0].charAt(0), parameters.substring(arguments[0].length()));
				});

			} else if (line.startsWith("")) {
				continue;

			} else {
				throw new IllegalArgumentException("Unsupported directive, was: " + line);
			}
		}
		return null;
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
		commands.put(symbol, action);
		return this;
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
