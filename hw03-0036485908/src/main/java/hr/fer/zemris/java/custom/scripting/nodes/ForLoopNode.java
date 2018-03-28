package hr.fer.zemris.java.custom.scripting.nodes;

import hr.fer.zemris.java.custom.scripting.elems.Element;
import hr.fer.zemris.java.custom.scripting.elems.ElementVariable;

/**
 * The class that represents a node which consist of a single for-loop
 * construct. It inherits from Node class. It is defined by the case insensitive
 * and space-ignoring FOR tag meaning that you can write {$ FOR … $} or {$ For …
 * $} or {$ foR … $} or similar. FOR-tag, is not an empty tag. It has content
 * and an accompanying END-tag must be present to close it. ForLoopNode can have
 * three or four parameters (as specified by user): first it must have one
 * ElementVariable and after that two or three Elements of type variable, number
 * or string. If user specifies something which does not obeys this rule, an
 * exception is thrown.Valid variable name starts by letter and after follows
 * zero or more letters, digits or underscores. If name is not valid, it is
 * invalid. This variable names are valid: A7_bb, counter, tmp_34; these are
 * not: _a21, 32, 3s_ee etc. It allows escaping in Strings in form of
 * translating '\\' to '\' and '\"' to '"'. \n, \r and \t have its usual meaning
 * (ascii 10, 13 and 9). Other escape sequences are invalid.
 * 
 * @author Damjan Vučina
 */
public class ForLoopNode extends Node {

	/**
	 * The variable property. Valid variable name starts by letter and after follows
	 * zero or more letters, digits or underscores. If name is not valid, it is
	 * invalid. This variable names are valid: A7_bb, counter, tmp_34; these are
	 * not: _a21, 32, 3s_ee etc.
	 */
	private ElementVariable variable;

	/** The start expression property. */
	private Element startExpression;

	/** The end expression property. */
	private Element endExpression;

	/** The step expression property, that can be null. */
	private Element stepExpression;

	/**
	 * Instantiates a new ForLoop node.
	 *
	 * @param variable
	 *            the variable of the ForLoop node. Valid variable name starts by
	 *            letter and after follows zero or more letters, digits or
	 *            underscores. If name is not valid, it is invalid. This variable
	 *            names are valid: A7_bb, counter, tmp_34; these are not: _a21, 32,
	 *            3s_ee etc.
	 * @param startExpression
	 *            the start expression of the ForLoop node.
	 * @param endExpression
	 *            the end expression of the ForLoop node.
	 * @param stepExpression
	 *            the step expression of the ForLoop node.
	 */
	public ForLoopNode(ElementVariable variable, Element startExpression, Element endExpression,
			Element stepExpression) {

		super();
		this.variable = variable;
		this.startExpression = startExpression;
		this.endExpression = endExpression;
		this.stepExpression = stepExpression;
	}

	/**
	 * Gets the variable of the ForLoop node.
	 *
	 * @return the variable of the ForLoop node.
	 */
	public ElementVariable getVariable() {
		return variable;
	}

	/**
	 * Gets the start expression of the ForLoop node.
	 *
	 * @return the start expression of the ForLoop node.
	 */
	public Element getStartExpression() {
		return startExpression;
	}

	/**
	 * Gets the end expression of the ForLoop node..
	 *
	 * @return the end expression of the ForLoop node.
	 */
	public Element getEndExpression() {
		return endExpression;
	}

	/**
	 * Gets the step expression of the ForLoop node..
	 *
	 * @return the step expression of the ForLoop node.
	 */
	public Element getStepExpression() {
		return stepExpression;
	}

}
