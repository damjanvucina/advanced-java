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
						processed=finalString;
						StringBuilder stringBuilder = new StringBuilder();
						for (int j = 0, length = processed.length(); j < length; j++) {
							Object production = productions.get(processed.charAt(j));

							if (production == null) {
								stringBuilder.append(processed.charAt(j));
								continue;
							}
							
							stringBuilder.append(production);
							
//							production =(String) production;
//							
//							finalString = finalString.substring(0, j) + production + finalString.substring(j+1);
							
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
					throw new IllegalArgumentException("Unsupported command request. Command was: " + command);
				}
			}

			private Color extractColor(String color) {
				try {
					//return Color.decode("#" + color);
					return Color.getColor(color);
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
			line = line.toLowerCase();
			
			if (line.startsWith("origin")) {
				process(normalize(line, "origin".length()), (parameters) -> {
					String[] arguments = parameters.split(" ");
					return setOrigin(Double.parseDouble(arguments[0]), Double.parseDouble(arguments[1]));
				});

			} else if (line.startsWith("angle")) {
				process(normalize(line, "angle".length()), (parameters) -> {
					String[] arguments = parameters.split(" ");
					return setAngle(Double.parseDouble(arguments[0]));
				});

			} else if (line.startsWith("unitlengthdegreescaler")) {
				process(normalize(line, "unitLengthDegreeScaler".length()), (parameters) -> {
					String[] arguments = parameters.split(" ");
					double factor = performCalculation(arguments);
					return setUnitLengthDegreeScaler(factor);
				});

			} else if (line.startsWith("unitlength")) {
				process(normalize(line, "unitLength".length()), (parameters) -> {
					String[] arguments = parameters.split(" ");
					return setUnitLength(Double.parseDouble(arguments[0]));
				});

			} else if (line.startsWith("command")) {
				process(normalize(line, "command".length()), (parameters) -> {
					String[] arguments = parameters.split(" ");
					return registerCommand(arguments[0].charAt(0), parameters.substring(arguments[0].length()).trim());
				});

			} else if (line.startsWith("axiom")) {
				process(normalize(line, "axiom".length()), (parameters) -> {
					String[] arguments = parameters.split(" ");
					return setAxiom(arguments[0]);
				});

			} else if (line.startsWith("production")) {
				process(normalize(line, "production".length()), (parameters) -> {
					String[] arguments = parameters.split(" ");
					return registerProduction(arguments[0].charAt(0), parameters.substring(arguments[0].length()).trim());
				});

			} else if (line.equals("")) {
				continue;

			} else {
				throw new IllegalArgumentException("Unsupported directive, was: " + line);
			}
		}
		return this;
	}

	private double performCalculation(String[] arguments) {
		if(arguments.length==1) {
			return Double.parseDouble(arguments[0]);
			
		} else if (arguments.length==3) {
			switch (arguments[1]) {
			
			case "+":
				return Double.parseDouble(arguments[0]) + Double.parseDouble(arguments[2]);
				
			case "-":
				return Double.parseDouble(arguments[0]) - Double.parseDouble(arguments[2]);
				
			case "*":
				return Double.parseDouble(arguments[0]) * Double.parseDouble(arguments[2]);
				
			case "/":
				return Double.parseDouble(arguments[0]) / Double.parseDouble(arguments[2]);

			default:
				throw new IllegalArgumentException("Unknown calculation operation requested.");
			}
			
		} else {
			throw new IllegalArgumentException("Unknown calculation operation requested.");
		}
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
