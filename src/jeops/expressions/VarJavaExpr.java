package jeops.expressions;

/**
 * This class models an expression of the java language which is composed only.
 *   of a single variable (identifier).
 *
 * @version 0.01  02 Mar 1999
 * @author Carlos Figueira Filho (<a href="mailto:csff@di.ufpe.br">csff@di.ufpe.br</a>)
 */
public class VarJavaExpr extends JavaExpr {

	/**
	 * The type of this variable.
	 */
	private Class variableType;

	/**
	 * The identifier of this variable.
	 */
	private String ident;

	/**
	 * Class constructor.
	 *
	 * @param variableType the type of this variable (or the class name,
	 *          if it is an object identifier.
	 * @param ident the identifier of this variable.
	 */
	public VarJavaExpr(String variableType, String ident)
					throws ClassNotFoundException {
		if (variableType.equals("int")) {
			this.type = JavaExpr.INT;
			this.variableType = Integer.TYPE;
		} else if (variableType.equals("boolean")) {
			this.type = JavaExpr.BOOL;
			this.variableType = Boolean.TYPE;
		} else {
			this.type = JavaExpr.OBJ;
			this.variableType = Class.forName(variableType);
		}
		this.ident = ident;
		this.identObjetos = new StringSet(ident);
	}
	/**
	 * Prints the tree for the expression of this precondition. Useful
	 * for debugging.
	 *
	 * @param spaces the identation for the printed output.
	 */
	public void dump(int spaces) {
		for (int i = 0; i < spaces; i++) System.out.print(" ");
		System.out.println(variableType.getName() + " variable(" + ident + ")");
	}
	/**
	 * Returns an object representing the class of this expression.
	 */
	public Class getClasse() {
		return variableType;
	}
	/**
	 * Returns the boolean result of this expression. If the type of this variable
	 *   is not boolean, it will return <code>false</code>.
	 *
	 * @param table the symbolic table where the values of the variables
	 *          used within this expression are stored.
	 */
	protected boolean internalEvaluateBoolean(SymbolicTable table) {
		Object aux = internalEvaluateObject(table);
		if (aux instanceof Boolean)
			return ((Boolean)aux).booleanValue();
		else
			return false;
	}
	/**
	 * Returns the integer result of this expression. If the type of this variable
	 *   is not integer, it will return <code>0</code> (zero).
	 *
	 * @param table the symbolic table where the values of the variables
	 *          used within this expression are stored.
	 */
	protected int internalEvaluateInt(SymbolicTable table) {
		Object aux = internalEvaluateObject(table);
		if (aux instanceof Integer)
			return ((Integer)aux).intValue();
		else
			return 0;
	}
	/**
	 * Returns in an object the result of this expression. It will return the
	 *   value stored in the symbolic table for this identifier.
	 *
	 * @param table the symbolic table where the values of the variables
	 *          used within this expression are stored.
	 */
	protected Object internalEvaluateObject(SymbolicTable table) {
		return table.getReference(ident);
	}
}