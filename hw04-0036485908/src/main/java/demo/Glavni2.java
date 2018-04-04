package demo;

import hr.fer.zemris.lsystems.LSystem;
import hr.fer.zemris.lsystems.LSystemBuilderProvider;
import hr.fer.zemris.lsystems.gui.LSystemViewer;
import hr.fer.zemris.lsystems.impl.LSystemBuilderImpl;

/**
 * * The program that creates a Koch curve by specifing fractal parameters from
 * user's text input.
 */
public class Glavni2 {

	/**
	 * The main method.
	 *
	 * @param args
	 *            the arguments
	 */
	public static void main(String[] args) {
		LSystemViewer.showLSystem(createKochCurve2(LSystemBuilderImpl::new));
	}

	/**
	 * * Creates the Koch curve based on specified parameters in user's text input
	 *
	 * @param provider
	 *            the LSystemBuilderProvider
	 * @return the l system
	 */
	private static LSystem createKochCurve2(LSystemBuilderProvider provider) {
		String[] data = new String[] { "origin 0.05 0.4", "angle 0", "unitLength 0.9",
				"unitLengthDegreeScaler 1.0 / 3.0", "", "command F draw 1", "command + rotate 60",
				"command - rotate -60", "", "axiom F", "", "production F F+F--F+F" };
		return provider.createLSystemBuilder().configureFromText(data).build();
	}

}
