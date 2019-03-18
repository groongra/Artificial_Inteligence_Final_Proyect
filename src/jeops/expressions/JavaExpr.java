package jeops.expressions;

/**
 * This class models an expression of (our subset of) the java language.
 *
 * @version 0.04  23 Fev 1999   First version with the comments in English.
 * @author Carlos Figueira Filho (<a href="mailto:csff@di.ufpe.br">csff@di.ufpe.br</a>)
 * @history 0.02  26 Fev 1998   Atributo com as variáveis do tipo VarObjectNode da expressão.
 * @history 0.03  23 Fev 1999   Métodos para se retornar o valor inteiro ou booleano desta
 *							 expressão.
 */
public abstract class JavaExpr {

	/**
	 * Constant used to indicate that this expression's type is <code>int</code>.
	 */
	public static final int INT = 0;

	/**
	 * Constant used to indicate that this expression's type is <code>boolean</code>.
	 */
	public static final int BOOL = 1;

	/**
	 * Constant used to indicate that this expression's type is <code>Object</code>.
	 *  (or any of this subclasses).
	 */
	public static final int OBJ = 2;

	/**
	 * This expression's type.
	 *
	 * @since 0.03
	 * @see #INT
	 * @see #BOOL
	 * @see #OBJ
	 */
	protected int type;

	/**
	 * The object identifiers (variables) declared in this expression.
	 */
	protected StringSet identObjetos;

	/**
	 * Prints the tree for the expression of this precondition. Useful
	 * for debugging.
	 */
	public void dump() {
		dump(0);
	}
	/**
	 * Prints the tree for the expression of this precondition. Useful
	 * for debugging.
	 *
	 * @param spaces the identation for the printed output.
	 */
	public abstract void dump(int spaces);
	/**
	 * Returns the boolean result of this expression. If this is not a boolean
	 *   expression, the value <code>false</code> will be returned.
	 *
	 * @param table the symbolic table where the values of the variables
	 *          used within this rule are stored.
	 * @exception MethodEvaluationException if an exception was thrown while
	 *          evaluating a method that may occur within this expression.
	 * @exception FieldAccessException if an exception was thrown while
	 *          accessing a field of an object that may occur in this
	 *          expression.
	 * @since 0.03
	 */
	public boolean evaluateBool(SymbolicTable table) throws MethodEvaluationException, FieldAccessException {
		if (type == BOOL)
			return internalEvaluateBoolean(table);
		else
			return false;
	}
	/**
	 * Returns the int result of this expression. If this is not an integer
	 *   expression, the value <code>0 (zero)</code> will be returned.
	 *
	 * @param table the symbolic table where the values of the variables
	 *          used within this rule are stored.
	 * @exception MethodEvaluationException if an exception was thrown while
	 *   evaluating a method that may occur within this expression.
	 * @exception FieldAccessException if an exception was thrown while
	 *   accessing a field of an object that may occur in this expression.
	 * @since 0.03
	 */
	public int evaluateInt(SymbolicTable table)
				throws MethodEvaluationException, FieldAccessException {
		if (type == INT)
			return internalEvaluateInt(table);
		else
			return 0;
	}
	/**
	 * Returns in an object the result of the evaluation of this expression. If
	 *   needed, the result will be wrapped in one of the language wrapper
	 *   classes for the primitive types.
	 *
	 * @param table the symbolic table where the values of the variables
	 *          used within this rule are stored.
	 * @exception MethodEvaluationException if an exception was thrown while
	 *   evaluating a method that may occur within this expression.
	 * @exception FieldAccessException if an exception was thrown while
	 *   accessing a field of an object that may occur in this expression.
	 */
	public Object evaluateObj(SymbolicTable table)
				throws MethodEvaluationException, FieldAccessException {
		switch (type) {
			case INT : return new Integer(internalEvaluateInt(table));
			case BOOL: return new Boolean(internalEvaluateBoolean(table));
			case OBJ : return internalEvaluateObject(table);
			default  : return null;
		}
	}
	/**
	 * Returns an object representing the class of this expression.
	 */
	public abstract Class getClasse();
	/**
	 * Returns all object variable identifiers that are used in this expression.
	 */
	public java.util.Enumeration identObjetos() {
		return identObjetos;
	}
	/**
	 * Returns the boolean result of this expression. If its type is not boolean,
	 *   <code>false</code> must be returned. This method must be overriden by
	 *   JavaExpr's subclasses.
	 *
	 * @param table the symbolic table where the values of the variables
	 *          used within this expression are stored.
	 * @exception MethodEvaluationException if an exception was thrown while
	 *   evaluating a method that may occur within this expression.
	 * @exception FieldAccessException if an exception was thrown while
	 *   accessing a field of an object that may occur in this expression.
	 */
	protected abstract boolean internalEvaluateBoolean(SymbolicTable table)
				throws MethodEvaluationException, FieldAccessException;
	/**
	 * Returns the integer result of this expression. If its type is not integer,
	 *   <code>0</code> (zero) must be returned. This method must be overriden by
	 *   JavaExpr's subclasses.
	 *
	 * @param table the symbolic table where the values of the variables
	 *          used within this expression are stored.
	 * @exception MethodEvaluationException if an exception was thrown while
	 *   evaluating a method that may occur within this expression.
	 * @exception FieldAccessException if an exception was thrown while
	 *   accessing a field of an object that may occur in this expression.
	 */
	protected abstract int internalEvaluateInt(SymbolicTable table)
			throws MethodEvaluationException, FieldAccessException;
	/**
	 * Returns in an object the result of this expression. If its result is a
	 *   primitive type, <code>null</code> must be returned. This method must
	 *   be implemented by JavaExpr's subclasses.
	 *
	 * @param table the symbolic table where the values of the variables
	 *          used within this expression are stored.
	 * @exception MethodEvaluationException if an exception was thrown while
	 *   evaluating a method that may occur within this expression.
	 * @exception FieldAccessException if an exception was thrown while
	 *   accessing a field of an object that may occur in this expression.
	 */
	protected abstract Object internalEvaluateObject(SymbolicTable table)
				throws MethodEvaluationException, FieldAccessException;
	/**
	 * Returns the number of object identifiers (variables) in this expression.
	 */
	public int numIdentObjetos() {
		return identObjetos.size();
	}
	/**
	 * Returns the type of this expression.
	 */
	public int tipo() {
		return type;
	}
}