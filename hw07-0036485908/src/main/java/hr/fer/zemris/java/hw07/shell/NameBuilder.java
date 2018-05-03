package hr.fer.zemris.java.hw07.shell;

/**
 * The interface specifing methods which need to be implemented by each
 * NameBuilder class.
 * 
 * @author Damjan Vučina
 */
public interface NameBuilder {

	/**
	 * Executes the NameBuilder.
	 *
	 * @param info
	 *            the info
	 */
	void execute(NameBuilderInfo info);

	/**
	 * Gets the string builder, i.e. the segment that this NameBuilderImplementation
	 * was supposed to produce.
	 *
	 * @return the string builder
	 */
	StringBuilder getStringBuilder();
}
