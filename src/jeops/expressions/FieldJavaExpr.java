package jeops.expressions;

import java.lang.reflect.Field;
import java.util.Enumeration;

/**
 * This class models a field access in (our subset of) the java language.
 *
 * @version 0.01  08 Mar 1999
 * @author Carlos Figueira Filho (<a href="mailto:csff@di.ufpe.br">csff@di.ufpe.br</a>)
 */
public class FieldJavaExpr extends JavaExpr {

	/**
	 * The class of the field object being accessed.
	 */
	private Class objectType;

	/**
	 * The name of the field represented by this object.
	 */
	private String fieldName;

	/**
	 * The reflex of the field represented by this object.
	 */
	private Field reflexField;

	/**
	 * The expression whose result has the field being accessed.
	 */
	private JavaExpr previousExpression;

	/**
	 * Class constructor.
	 *
	 * @param prevExpr the expression which results in the object whose field is
	 *          being accessed.
	 * @param name the field name.
	 * @exception NoSuchFieldException if there is no field with the given name.
	 */
	public FieldJavaExpr(JavaExpr prevExpr, String name)
												throws NoSuchFieldException {
		previousExpression = prevExpr;
		this.fieldName = name;
		objectType = prevExpr.getClasse();

		Classe novoTipoObjeto = new Classe(objectType);
		reflexField = novoTipoObjeto.getField(name);

		if (reflexField.getType() == Integer.TYPE)
			this.type = JavaExpr.INT;
		else if (reflexField.getType() == Boolean.TYPE)
			this.type = JavaExpr.BOOL;
		else
			this.type = JavaExpr.OBJ;

		identObjetos = (StringSet) prevExpr.identObjetos();
	}
	/**
	 * Prints the tree for the expression of this precondition. Useful
	 * for debugging.
	 *
	 * @param spaces the identation for the printed output.
	 */
	public void dump(int spaces) {
		int i;
		for (i = 0; i < spaces; i++) System.out.print(" ");
		System.out.println("Field Access: " + fieldName);
		for (i = 0; i < spaces; i++)
			System.out.print(" ");
		System.out.println("  before:");
		previousExpression.dump(spaces + 4);
	}
	/**
	 * Returns the name of the field represented by this expression.
	 */
	public String fieldName() {
		return fieldName;
	}
	/**
	 * Returns an object representing the class of this expression.
	 */
	public Class getClasse() {
		return reflexField.getType();
	}
/**
 * Returns the expression whose result has the field being
 * accessed.
 *
 * @return the expression whose result has the field being
 *          accessed.
 */
public JavaExpr getPreviousExpression() {
	return previousExpression;
}
	/**
	 * Returns the boolean result of this expression. If the field's type isn't
	 *   <code>boolean</code>, it will return <code>false</code>.
	 *
	 * @param table the symbolic table where the values of the variables
	 *          used within this expression are stored.
	 * @exception MethodEvaluationException if an exception was thrown while
	 *   evaluating method that might happen in the expression whose result
	 *   has the field being accessed.
	 * @exception FieldAccessException if an exception was thrown while
	 *   accessing the field's value.
	 */
	protected boolean internalEvaluateBoolean(SymbolicTable table)
				throws FieldAccessException, MethodEvaluationException {
		Object aux = internalEvaluateObject(table);
		if (aux instanceof Boolean)
			return ((Boolean)aux).booleanValue();
		else
			return false;
	}
	/**
	 * Returns the integer result of this expression. If the field's type isn't
	 *   <code>int</code>, it will return <code>0</code> (zero).
	 *
	 * @param table the symbolic table where the values of the variables
	 *          used within this expression are stored.
	 * @exception MethodEvaluationException if an exception was thrown while
	 *   evaluating method that might happen in the expression whose result
	 *   has the field being accessed.
	 * @exception FieldAccessException if an exception was thrown while
	 *   accessing the field's value.
	 */
	protected int internalEvaluateInt(SymbolicTable table)
				throws FieldAccessException, MethodEvaluationException {
		Object aux = internalEvaluateObject(table);
		if (aux instanceof Integer)
			return ((Integer)aux).intValue();
		else
			return 0;
	}
	/**
	 * Returns in an object the result of this expression.
	 *
	 * @param table the symbolic table where the values of the variables
	 *          used within this expression are stored.
	 * @exception MethodEvaluationException if an exception was thrown while
	 *   evaluating method that might happen in the expression whose result
	 *   has the field being accessed.
	 * @exception FieldAccessException if an exception was thrown while
	 *   accessing the field's value.
	 */
	protected Object internalEvaluateObject(SymbolicTable table)
				throws FieldAccessException, MethodEvaluationException {
		Object receptor = previousExpression.evaluateObj(table);
		Object result;
		try {
			result = reflexField.get(receptor);
		} catch (Exception e) {
			throw new FieldAccessException (e.getMessage());
		}
		return result;
	}
	/**
	 * Returns the class of the object whose field is being accessed.
	 */
	public Class objectType() {
		return objectType;
	}
	/**
	 * Returns a String representation of this expression. Useful for debugging.
	 *
	 * @return a String representing this expression.
	 */
	public String toString() {
		return ("FieldJavaExpr - " + fieldName);
	}
}