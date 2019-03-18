package jeops.expressions;

/**
 * This class models an unary expression of (our subset of) the java language.
 * The unary expressions currently supported are:
 * <ul>
 *  <li> Logical NOT (<code>!</code>)
 *  <li> Unary Minus (<code>-</code>)
 * </ul>
 *
 * @version 0.01  02 Mar 1999
 * @author Carlos Figueira Filho (<a href="mailto:csff@di.ufpe.br">csff@di.ufpe.br</a>)
 */
public class UnaryJavaExpr extends JavaExpr {

	/**
	 * Constant used to indicate that this expression represents a logical NOT.
	 */
	public static final int LNOT = 0;

	/**
	 * Constant used to indicate that this expression represents an unary minus.
	 */
	public static final int UMINUS = 1;

	/**
	 * The type of this unary expression.
	 *
	 * @see #LNOT
	 * @see #UMINUS
	 */
	private int unaryType;

	/**
	 * The subexpression of this unary expression.
	 */
	private JavaExpr exp;

	/**
	 * Class constructor.
	 *
	 * @param exp the subexpression of this unary expression.
	 * @param unaryType the type of this expression.
	 * @see #LNOT
	 * @see #UMINUS
	 */
	public UnaryJavaExpr(JavaExpr exp, int unaryType) {
		this.type = ((unaryType == LNOT) ? JavaExpr.BOOL : JavaExpr.INT);
		this.exp = exp;
		this.unaryType = unaryType;
		this.identObjetos = (StringSet) exp.identObjetos();
	}
	/**
	 * Prints the tree for the expression of this precondition. Useful
	 * for debugging.
	 *
	 * @param spaces the identation for the printed output.
	 */
	public void dump(int spaces) {
		for (int i = 0; i < spaces; i++) System.out.print(" ");
		if (unaryType == LNOT)
			System.out.println("Logical NOT");
		else
			System.out.println("Unary Minus");
		exp.dump(spaces + 2);
	}
	/**
	 * Returns an object representing the class of this expression.
	 */
	public Class getClasse() {
		if (this.unaryType == LNOT)
			return Boolean.TYPE;
		else
			return Integer.TYPE;
	}
	/**
	 * Returns the boolean result of this expression. If its type is not logical not,
	 *   <code>false</code> will be returned.
	 *
	 * @param table the symbolic table where the values of the variables
	 *          used within this expression are stored.
	 * @exception MethodEvaluationException if an exception was thrown while
	 *   evaluating a method that may occur within this expression.
	 * @exception FieldAccessException if an exception was thrown while
	 *   accessing the field's value.
	 */
	protected boolean internalEvaluateBoolean(SymbolicTable table)
				throws MethodEvaluationException, FieldAccessException {
		if (unaryType == LNOT) {
			boolean aux = exp.evaluateBool(table);
			return !aux;
		} else
			return false;
	}
	/**
	 * Returns the integer result of this expression. If its type is not unary minus,
	 *   it will return <code>0</code> (zero).
	 *
	 * @param table the symbolic table where the values of the variables
	 *          used within this expression are stored.
	 * @exception MethodEvaluationException if an exception was thrown while
	 *   evaluating a method that may occur within this expression.
	 * @exception FieldAccessException if an exception was thrown while
	 *   accessing the field's value.
	 */
	protected int internalEvaluateInt(SymbolicTable table)
				throws MethodEvaluationException, FieldAccessException {
		if (unaryType == UMINUS) {
			int aux = exp.evaluateInt(table);
			return -aux;
		} else
			return 0;
	}
	/**
	 * Returns in an object the result of this expression. As the only kind of
	 *   unary expressions (logical not and unary minus) return primitive types,
	 *   it will always return <code>null</code>.
	 *
	 * @param table the symbolic table where the values of the variables
	 *          used within this expression are stored.
	 */
	protected Object internalEvaluateObject(SymbolicTable table) {
		return null;
	}
}