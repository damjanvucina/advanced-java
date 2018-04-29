package hr.fer.zemris.java.hw06.shell;

/**
 * The enum that defines the result of the execution of the specified command.
 * MyShell program will end by terminating only if there is no way of recovering
 * from the user's invalid input.
 * 
 * @author Damjan Vučina
 */
public enum ShellStatus {

	/** The continue status that signifies that the processing of the command has been successful. */
	CONTINUE,
	/** The terminate status that signifies that the processing of the command has failed. */
	TERMINATE
}
