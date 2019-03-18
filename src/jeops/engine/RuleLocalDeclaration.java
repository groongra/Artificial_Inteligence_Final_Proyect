package jeops.engine;

import jeops.expressions.JavaExpr;

/**
 * This class models a local variable declaration of a rule.
 *
 * @version 0.02  29 Jun 1999   First version with comments in English.
 * @author Carlos Figueira Filho (<a href="mailto:csff@di.ufpe.br">csff@di.ufpe.br</a>)
 * @history 0.01  09 Jan 1997    Esta classe modela uma das declarações
 *                              (de variáveis) locais de uma regra.
 */
public class RuleLocalDeclaration {

	/**
	 * The class name of this variable.
	 */
	private String className;

	/**
	 * The identifier of this variable.
	 */
	private String ident;

	/**
	 * The expressions that defines this locally defined variable.
	 */
	private JavaExpr expression;

	/**
	 * Class constructor.
	 *
	 * @param className the name of the class of the variable
	 * @param ident the identifier of the variable
	 * @param expression the expression that defines the variable
	 */
	public RuleLocalDeclaration (String className, String ident, JavaExpr expression) {
		this.className = className;
		this.ident = ident;
		this.expression = expression;
	}
	/**
	 * Prints a tree representation of this base. Useful for debugging.
	 *
	 * @param spaces the identation of the information to be printed.
	 */
	public void dump (int espacos) {
		for (int i = 0; i < espacos; i++)
			System.out.print(" ");
		System.out.println(className + " " + ident);
		expression.dump(espacos + 2);
	}
	/**
	 * Returns the class name of this variable.
	 *
	 * @return the class name of this variable.
	 */
	public String getClassName() {
		return className;
	}
	/**
	 * Returns the expression that defines the locally declared variable.
	 *
	 * @return the expression that defines the locally declared variable.
	 */
	public JavaExpr getExpression() {
		return expression;
	}
	/**
	 * Returns this variable identifier.
	 *
	 * @return this variable identifier.
	 */
	public String getIdent() {
		return ident;
	}
}