package hr.fer.zemris.java.hw07.shell;

/**
 * The Enum defining the states of the parser. Non group state is everything
 * outside of this sequence: &{...} which defines the group state.
 */
public enum NameBuilderParserState {

	/** The group state. */
	GROUP,
	/** The non-group state. */
	NONGROUP
}
