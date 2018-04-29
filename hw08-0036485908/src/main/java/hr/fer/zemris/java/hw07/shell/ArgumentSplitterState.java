package hr.fer.zemris.java.hw07.shell;

/**
 * The enumeration used in the process of parsing the user input path to enable
 * spaces in paths.
 * 
 * @author Damjan Vučina
 */
public enum ArgumentSplitterState {

	/** Currently being parsed path is quoted. */
	PATH,
	/**  Currently being parsed path is non quoted. */
	NONPATH
}
