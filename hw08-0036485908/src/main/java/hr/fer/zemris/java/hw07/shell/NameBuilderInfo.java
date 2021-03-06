package hr.fer.zemris.java.hw07.shell;

/**
 * The interface specifing the method for retrieving specific regular expression
 * groups.
 * 
 * @author Damjan Vučina
 */
public interface NameBuilderInfo {

	/**
	 * Gets the specific regular expression group
	 *
	 * @param index
	 *            the index of the group
	 * @return the group
	 */
	String getGroup(int index);
	
	/**
	 * Gets the string builder, i.e. the segment that this NameBuilderImplementation
	 * was supposed to produce.
	 *
	 * @return the string builder
	 */
	StringBuilder getStringBuilder();
}
