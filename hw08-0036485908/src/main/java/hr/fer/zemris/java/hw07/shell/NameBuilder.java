package hr.fer.zemris.java.hw07.shell;

/**
 * The interface specifing methods which need to be implemented by each
 * NameBuilder class.
 * 
 * @author Damjan Vučina
 */
public interface NameBuilder extends NameBuilderInfo {

	/**
	 * Executes the NameBuilder.
	 *
	 * @param info
	 *            the info
	 */
	void execute(NameBuilderInfo info);
}
