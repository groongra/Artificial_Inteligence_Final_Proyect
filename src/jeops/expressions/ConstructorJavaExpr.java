package jeops.expressions;

import java.lang.reflect.Constructor;
import java.util.Enumeration;

/**
 * This class models a method call in (our subset of) the java language.
 *
 * @version 0.01  02 Mar 1999
 * @author Carlos Figueira Filho (<a href="mailto:csff@di.ufpe.br">csff@di.ufpe.br</a>)
 */
public class ConstructorJavaExpr extends JavaExpr {

	/**
	 * The class of the object that will be created.
	 */
	private Class objectType;

	/**
	 * The parameters passed to this constructor.
	 */
	private JavaExpr[] parameters;

	/**
	 * The reflex of the constructor represented by this object.
	 */
	private Constructor reflexConstructor;

	/**
	 * Class constructor.
	 *
	 * @param objType the class of the object that receives the method call.
	 * @param parameters the parameters passed to the method
	 * @exception NoSuchConstructorException if there is no method with the given name and
	 *               parameters.
	 * @exception NoSuchClassException if there is no class with the given name.
	 */
	public ConstructorJavaExpr(String objType, JavaExpr[] parameters)
								throws NoSuchMethodException, NoSuchClassException {
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
		reflexConstructor = novoTipoObjeto.getConstructor(params);
		this.type = JavaExpr.OBJ;

		identObjetos = new StringSet();
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
		System.out.println("Constructor call: " + objectType);
		for (i = 0; i < parameters.length; i++)
			parameters[i].dump(spaces + 2);
	}
	/**
	 * Returns an object representing the class of this expression.
	 */
	public Class getClasse() {
		return objectType;
	}
	/**
	 * Returns the boolean result of this expression. It will always
	 *   return <code>0</code> (zero).
	 *
	 * @param table the symbolic table where the values of the variables
	 *          used within this expression are stored.
	 */
	protected boolean internalEvaluateBoolean(SymbolicTable table) {
		return false;
	}
	/**
	 * Returns the integer result of this expression. It will always
	 *   return <code>0</code> (zero).
	 *
	 * @param table the symbolic table where the values of the variables
	 *          used within this expression are stored.
	 */
	protected int internalEvaluateInt(SymbolicTable table) {
		return 0;
	}
	/**
	 * Returns in an object the result of this expression.
	 *
	 * @param table the symbolic table where the values of the variables
	 *          used within this expression are stored.
	 * @exception MethodEvaluationException if an exception was thrown while
	 *   evaluating a method that may occur in this expression.
	 * @exception FieldAccessException if an exception was thrown while
	 *   accessing a field of an object that may occur in this expression.
	 */
	protected Object internalEvaluateObject(SymbolicTable table)
				throws MethodEvaluationException, FieldAccessException {
		Object args[] = new Object[parameters.length];
		for (int i = 0; i < args.length; i++)
			args[i] = parameters[i].evaluateObj(table);
		Object result;
		try {
			result = reflexConstructor.newInstance(args);
		} catch (Exception e) {
			throw new MethodEvaluationException (e.getMessage());
		}
		return result;
	}
	/**
	 * Returns the class of the object that is created by this constructor.
	 */
	public Class objectType() {
		return objectType;
	}
	/*
	 * Returns a String representation of this expression. Useful for debugging.
	 *
	 * @return a String representing this expression.
	 */
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("ConstructorJavaExpr - "+objectType + "(");
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