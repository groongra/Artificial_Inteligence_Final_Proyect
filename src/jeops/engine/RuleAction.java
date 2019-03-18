package jeops.engine;

import jeops.expressions.ActionJavaExpr;

/**
 * This class models an action of a rule to be executed if the rule's
 * preconditions evaluate to true.
 *
 * @version 0.02  28 Jun 1999  First version with comments in English,
 * @author Carlos Figueira Filho (<a href="mailto:csff@di.ufpe.br">csff@di.ufpe.br</a>)
 * @history 0.01  08 Dez 1997   Esta classe modela uma ação de uma regra
 *          a ser tomada caso as premissas da regra forem verdadeiras.
 */
public class RuleAction {

	/**
	 * The corresponding java action.
	 */
	private ActionJavaExpr expression;

	/**
	 * Class constructor.
	 *
	 * @param expression the expression that represents this action.
	 */
	public RuleAction (ActionJavaExpr expression) {
		this.expression = expression;
	}
	/**
	 * Prints this expression's tree with the specified identation.
	 * Useful for debugging.
	 */
	public void dump (int espacos) {
		expression.dump(espacos);
	}
	/**
	 * Returns the execution expression for this action.
	 *
	 * @return the execution expression for this action.
	 */
	public ActionJavaExpr getExpression() {
		return expression;
	}
}