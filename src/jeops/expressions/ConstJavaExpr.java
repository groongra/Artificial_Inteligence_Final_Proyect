package jeops.expressions;

/**
 * This class models an expression of (our subset of) the java language which is
 *   composed only of a single constant. Currently, those types of constants are
 *   supported:
 * <ul>
 *  <li> Integer numbers
 *  <li> Boolean constants (<code>true</code> and <code>false</code>)
 *  <li> String constants
 *  <li> <code>null</code>
 * </ul>
 *
 * @version 0.01  02 Mar 1999
 * @author Carlos Figueira Filho (<a href="mailto:csff@di.ufpe.br">csff@di.ufpe.br</a>)
 */
public class ConstJavaExpr extends JavaExpr {

	/**
	 * The integer value of this variable.
	 */
	private int intValue;

	/**
	 * The boolean value of this variable.
	 */
	private boolean boolValue;

	/**
	 * The object value of this variable.
	 */
	private Object objValue;

	/**
	 * Class constructor for <code>null</code> constants.
	 */
	public ConstJavaExpr() {
		this.type = JavaExpr.OBJ;
		this.objValue = null;
		this.identObjetos = new StringSet();
	}
	/**
	 * Class constructor for integer constants.
	 *
	 * @param value the value of this constant.
	 */
	public ConstJavaExpr(int value) {
		this.type = JavaExpr.INT;
		this.intValue = value;
		this.identObjetos = new StringSet();
	}
	/**
	 * Class constructor for String constants.
	 *
	 * @param value the value of this constant.
	 */
	public ConstJavaExpr(String value) {
		this.type = JavaExpr.OBJ;
		this.objValue = value;
		this.identObjetos = new StringSet();
	}
	/**
	 * Class constructor for boolean constants.
	 *
	 * @param value the value of this constant.
	 */
	public ConstJavaExpr(boolean value) {
		this.type = JavaExpr.BOOL;
		this.boolValue = value;
		this.identObjetos = new StringSet();
	}
	/**
	 * Prints the tree for the expression of this precondition. Useful
	 * for debugging.
	 *
	 * @param spaces the identation for the printed output.
	 */
	public void dump(int spaces) {
		for (int i = 0; i < spaces; i++) System.out.print(" ");
		System.out.print("Constant(");
		switch (type) {
			case JavaExpr.INT: System.out.println(intValue + ")"); break;
			case JavaExpr.BOOL: System.out.println(boolValue + ")"); break;
			case JavaExpr.OBJ: System.out.println(objValue + ")"); break;
		}
	}
	/**
	 * Returns an object representing the class of this expression.
	 */
	public Class getClasse() {
		Class result = null;
		switch (type) {
			case JavaExpr.INT : result = Integer.TYPE; break;
			case JavaExpr.BOOL: result = Boolean.TYPE; break;
			default:
				try {
					if (objValue == null)
						result = Class.forName("java.lang.Object");
					else
						result = String.class;
				} catch (ClassNotFoundException e) {}
		}
		return result;
	}
	/**
	 * Returns the boolean result of this expression.  If this is not an
	 *   object constant, it will return <code>false</code>.
	 *
	 * @param table the symbolic table where the values of the variables
	 *          used within this expression are stored.
	 */
	protected boolean internalEvaluateBoolean(SymbolicTable table) {
		return boolValue;
	}
	/**
	 * Returns the integer result of this expression.  If this is not an
	 *   object constant, it will return <code>0</code> (zero).
	 *
	 * @param table the symbolic table where the values of the variables
	 *          used within this expression are stored.
	 */
	protected int internalEvaluateInt(SymbolicTable table) {
		return intValue;
	}
	/**
	 * Returns in an object the result of this expression. If this is not an
	 *   object constant, it will return <code>null</code>.
	 *
	 * @param table the symbolic table where the values of the variables
	 *          used within this expression are stored.
	 */
	protected Object internalEvaluateObject(SymbolicTable table) {
		return objValue;
	}
}