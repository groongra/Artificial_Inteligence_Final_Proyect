package jeops.expressions;

import java.lang.reflect.Method;
import java.util.Enumeration;

/**
 * This class models a method call in (our subset of) the java language.
 *
 * @version 0.01  02 Mar 1999
 * @author Carlos Figueira Filho (<a href="mailto:csff@di.ufpe.br">csff@di.ufpe.br</a>)
 */
public class MethodJavaExpr extends JavaExpr {

	/**
	 * The class of the object that receives the method call.
	 */
	private Class objectType;

	/**
	 * The parameters passed to this method.
	 */
	private JavaExpr[] parameters;

	/**
	 * The name of the method represented by this object.
	 */
	private String methodName;

	/**
	 * The identifier of the object that receives the method call.
	 */
	private String objectIdent;

	/**
	 * The reflex of the method represented by this object.
	 */
	private Method reflexMethod;

	/**
	 * The expression whose result receives the method call.
	 */
	private JavaExpr previousExpression;

	/**
	 * Class constructor, for methods whose object in which it is invoked 
	 *   does NOT come from another expression.
	 *
	 * @param objType the class of the object that receives the method call.
	 * @param objId the identifier of the object that receives the method call.
	 * @param name the method name.
	 * @param parameters the parameters passed to the method
	 * @exception NoSuchMethodException if there is no method with the given name and
	 *               parameters.
	 * @exception NoSuchClassException if there is no class with the given name.
	 */
	public MethodJavaExpr(String objType, String objId, String name,
								 JavaExpr[] parameters)
								throws NoSuchMethodException, NoSuchClassException {
		this.objectIdent = objId;
		this.methodName = name;
		try {
			this.objectType = Class.forName(objType);
		} catch (ClassNotFoundException e) {
			throw new NoSuchClassException (e.getMessage());
		}
		Class params[] = new Class[parameters.length];
		this.parameters = new JavaExpr[parameters.length];
		for (int i = 0; i < params.length; i++) {
			this.parameters[i] = parameters[i];
			params[i] = parameters[i].getClasse();
		}

		Classe novoTipoObjeto = new Classe(objectType);
		reflexMethod = novoTipoObjeto.getMethod(name, params);

		if (reflexMethod.getReturnType() == Integer.TYPE)
			this.type = JavaExpr.INT;
		else if (reflexMethod.getReturnType() == Boolean.TYPE)
			this.type = JavaExpr.BOOL;
		else
			this.type = JavaExpr.OBJ;

		identObjetos = new StringSet(objectIdent);
		for (int i = 0; i < parameters.length; i++)
			identObjetos = new StringSet(identObjetos,
								  (StringSet)parameters[i].identObjetos());
	}
	/**
	 * Class constructor, for methods whose object in which it is invoked 
	 *   DOES come from another expression.
	 *
	 * @param prevExpr the expression which results in the object that receives
	 *          this method call.
	 * @param name the method name.
	 * @param parameters the parameters passed to the method
	 * @param table the symbolic table for the rule.
	 * @exception NoSuchMethodException if there is no method with the given name and
	 *               parameters.
	 */
	public MethodJavaExpr(JavaExpr prevExpr, String name,
								 JavaExpr[] parameters)
													throws NoSuchMethodException {
		previousExpression = prevExpr;
		this.objectIdent = null;
		this.methodName = name;
		objectType = prevExpr.getClasse();
		Class params[] = new Class[parameters.length];
		this.parameters = new JavaExpr[parameters.length];
		for (int i = 0; i < params.length; i++) {
			this.parameters[i] = parameters[i];
			params[i] = parameters[i].getClasse();
		}

		Classe novoTipoObjeto = new Classe(objectType);
		reflexMethod = novoTipoObjeto.getMethod(name, params);

		if (reflexMethod.getReturnType() == Integer.TYPE)
			this.type = JavaExpr.INT;
		else if (reflexMethod.getReturnType() == Boolean.TYPE)
			this.type = JavaExpr.BOOL;
		else
			this.type = JavaExpr.OBJ;

		identObjetos = (StringSet) prevExpr.identObjetos();
		for (int i = 0; i < parameters.length; i++)
			identObjetos = new StringSet(identObjetos,
								 (StringSet)parameters[i].identObjetos());
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
		System.out.println("Method call: " + objectIdent + "." + methodName);
		for (i = 0; i < parameters.length; i++)
			parameters[i].dump(spaces + 2);
		if (objectIdent == null) {
			for (i = 0; i < spaces; i++)
				System.out.print(" ");
			System.out.println("  before:");
			previousExpression.dump(spaces + 4);
		}
	}
	/**
	 * Returns an object representing the class of this expression.
	 */
	public Class getClasse() {
		return reflexMethod.getReturnType();
	}
	/**
	 * Returns the boolean result of this expression. If the method doesn't return a
	 *   boolean value, it will return <code>false</code>.
	 *
	 * @param table the symbolic table where the values of the variables
	 *          used within this expression are stored.
	 * @exception MethodEvaluationException if an exception was thrown while
	 *   evaluating this method.
	 * @exception FieldAccessException if an exception was thrown while
	 *   accessing the field's value.
	 */
	protected boolean internalEvaluateBoolean(SymbolicTable table)
				throws MethodEvaluationException, FieldAccessException {
		Object aux = internalEvaluateObject(table);
		if (aux instanceof Boolean)
			return ((Boolean)aux).booleanValue();
		else
			return false;
	}
	/**
	 * Returns the integer result of this expression. If the method doesn't return an
	 *   integer value, it will return <code>0</code> (zero).
	 *
	 * @param table the symbolic table where the values of the variables
	 *          used within this expression are stored.
	 * @exception MethodEvaluationException if an exception was thrown while
	 *   evaluating this method.
	 * @exception FieldAccessException if an exception was thrown while
	 *   accessing the field's value.
	 */
	protected int internalEvaluateInt(SymbolicTable table)
				throws MethodEvaluationException, FieldAccessException {
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
	 *   evaluating this method.
	 * @exception FieldAccessException if an exception was thrown while
	 *   accessing the field's value.
	 */
	protected Object internalEvaluateObject(SymbolicTable table)
				throws MethodEvaluationException, FieldAccessException {
		Object receptor;
		if (objectIdent != null)
			receptor = table.getReference(objectIdent);
		else
			receptor = previousExpression.evaluateObj(table);
		Object args[] = new Object[parameters.length];
		for (int i = 0; i < args.length; i++)
			args[i] = parameters[i].evaluateObj(table);
		Object result;
		try {
			result = reflexMethod.invoke(receptor, args);
		} catch (Exception e) {
			throw new MethodEvaluationException (e.getMessage());
		}
		return result;
	}
	/**
	 * Returns the name of the method represented by this expression.
	 */
	public String methodName() {
		return methodName;
	}
	/**
	 * Returns the identifier of the object that receives the method call.
	 */
	public String objectIdent() {
		return objectIdent;
	}
	/**
	 * Returns the class of the object that receives the method call.
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
		StringBuffer sb = new StringBuffer();
		sb.append("MethodJavaExpr - "+objectIdent+"."+methodName+"(");
		for (int i = 0; i < parameters.length; i++) {
			sb.append(parameters[i]);
			if (i != parameters.length - 1)
				sb.append(", ");
			else
				sb.append(")");
		}
		return sb.toString();
	}
}