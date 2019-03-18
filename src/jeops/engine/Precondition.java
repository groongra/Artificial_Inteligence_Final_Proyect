package jeops.engine;

import jeops.expressions.JavaExpr;

/**
 * This class models one of the preconditions of a rule.
 *
 * @version 0.02  24 Apr 1999 The enclosed expression is no longer of class
 *                             BoolNode, but a regular JavaExpr.
 * @author Carlos Figueira Filho (<a href="mailto:csff@di.ufpe.br">csff@di.ufpe.br</a>)
 * @history 0.01  08 Dez 1997
 */
public class Precondition {

	/**
	 * The expression corresponding to this precondition.
	 */
	private JavaExpr expression;

	/**
	 * Creates a new precondition for a rule, given the expression that
	 * represents it.
	 *
	 * @param expression the expresison that represents this precondition.
	 */
	public Precondition (JavaExpr expression) {
		this.expression = expression;
	}
	/**
	 * Prints the tree for the expression of this precondition. Useful
	 * for debugging.
	 *
	 * @param spaces the identation for the printed output.
	 */
	public void dump (int spaces) {
		expression.dump(spaces);
	}
	/**
	 * Returns the expression of this precondition.
	 *
	 * @return the expression of this precondition.
	 */
	public JavaExpr getExpression() {
		return expression;
	}
	/*
	 * Returns a String representation of this object. Useful for debugging.
	 *
	 * @return a String representing this expression.
	 */
	public String toString() {
		return expression.toString();
	}
}