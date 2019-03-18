package jeops.expressions;

/**
 * This class models an action statement of the inference engine. The
 * possible statements can be simple method calls, variable
 * declaration, assignment or including, removing and modifying
 * objects from the object base.
 *
 * @version 0.05  23 May 1999   First version with comments in English;
 *                           added the <b>modified</b> statement.
 * @author Carlos Figueira Filho (<a href="mailto:csff@di.ufpe.br">csff@di.ufpe.br</a>)
 * @history 0.02  26 Dez 1997
 * @history 0.03  30 Mar 1998   Mais uma opção para a ação: atribuição.
 *                           Os campos ident e expressao estao sendo 
 *                           usados como o lado esquerdo e direito da
 *                           atribuição. Há mais um construtor para poder
 *                           receber estes parâmetros.
 * @history 0.04  01 Abr 1998   Mais uma opção para a ação: declaração
 *                           de variável, com possível inicialização da
 *                           mesma. Há um novo campo, nomeClasse, para
 *                           armazenar o nome da classe do objeto
 *                           declarado.
 */
public class ActionJavaExpr {

	/**
	 * Constant used to indicate that an expression type is a method
	 * call.
	 */
	public static final int METHOD = 0;

	/**
	 * Constant used to indicate that an expression type is an assertion
	 * to the object base.
	 */
	public static final int CREATE = 1;

	/**
	 * Constant used to indicate that an expresison type is a deletion
	 * of an object to object base.
	 */
	public static final int DELETE = 2;

	/**
	 * Constant used to indicate that an expression type is an assignment.
	 */
	public static final int ASSIGN = 3;

	/**
	 * Constant used to indicate that an expression type is a (local)
	 * declaration.
	 */
	public static final int DECLAR = 4;

	/**
	 * Constant used to indicate that this expression is a
	 * <b>modified</b> statement.
	 *
	 * @since 0.05
	 */
	public static final int MODIFY = 5;

	/**
	 * This expression's type.
	 */
	private int type;

	/**
	 * The identifier in the LHS of an assignment.
	 */
	private String ident;

	/**
	 * The name of the method to be invoked, if applicable.
	 */
	private String methodName;

	/**
	 * The expression representing the object that will be inserted,
	 * removed or modified in the object base, the RHS of an
	 * assignment, a method to be invoked, or the expression that
	 * will initialize a variable being declared.
	 */
	private JavaExpr expression;

	/**
	 * The class of the locally declared object.
	 *
	 * @since 0.04
	 */
	private String className;

/**
 * Class constructor for declaration statements.
 *
 * @param type the type of this action statement. It must be
 *          equals to ActionJavaExpr.DECLAR
 * @param className the class of the object being declared.
 * @param ident the identifier of the object being declared.
 * @param expression the initializer for the object being
 *          declared. It can be <code>null</code>.
 * @see #DECLAR
 */
public ActionJavaExpr(int type, String className, String ident,
								JavaExpr expression) {
	this.type = type;
	this.expression = expression;
	this.className = className;
	this.ident = ident;
}
/**
 * Class constructor for assignment statements.
 *
 * @param type the type of this action statement. It must be
 *          equals to ActionJavaExpr.ATRIB
 * @param ident the identifier of the LHS of the assignment.
 * @param expression the RHS of the assignment.
 * @see #ATRIB
 */
public ActionJavaExpr(int type, String ident, JavaExpr expression) {
	this.type = type;
	this.expression = expression;
	this.ident = ident;
}
/**
 * Class constructor.
 *
 * @param type the type of this action statement.
 * @param expression the expression that is either being
 *          executed, inserted into, removed from or modified in
 *          the object base.
 * @see #METODO
 * @see #CREATE
 * @see #DELETE
 * @see #ATRIB
 * @see #DECLAR
 * @see #MODIFY
 */
public ActionJavaExpr(int type, JavaExpr expression) {
	this.type = type;
	this.expression = expression;
}
	/**
	 * Prints the tree for the expression of this precondition. Useful
	 * for debugging.
	 */
	public void dump () {
		dump(0);
	}
	/**
	 * Prints the tree for the expression of this precondition. Useful
	 * for debugging.
	 *
	 * @param spaces the identation for the printed output.
	 */
	public void dump (int spaces) {
		int i;
		for (i = 0; i < spaces; i++)
			System.out.print(" ");
		System.out.print("ActionJavaExpr - ");
		switch (type) {
			case DELETE :
			case MODIFY :
			case CREATE :
			case METHOD :
				switch (type) {
					case DELETE :
						System.out.println("RETRACT");
						break;
					case CREATE :
						System.out.println("ASSERT");
						break;
					case MODIFY :
						System.out.println("MODIFIED");
						break;
					case METHOD :
						System.out.println("METODO");
						break;
				}
				expression.dump(spaces + 2);
				break;
			case ASSIGN :
				System.out.println("ATRIB TO " + ident);
				expression.dump(spaces + 2);
				break;
			case DECLAR:
				System.out.print("DECLAR " + className + " " + ident);
				if (expression != null) {
					System.out.println (" =");
					expression.dump(spaces + 2);
				} else
					System.out.println ();
				break;
		}
	}
	/**
	 * Returns the class name of a locally declared object, if
	 * applicable.
	 *
	 * @return the class name of a locally declared object.
	 */
	public String getClassName() {
		return className;
	}
	/**
	 * Returns the expression that will produce the object to be
	 * inserted in the object base, or the RHS of the assignment,
	 * if applicable.
	 *
	 * @return the RHS expression of the attribution or the object
	 *          to be inserted in the object base.
	 */
	public JavaExpr getExpression() {
		return expression;
	}
	/**
	 * Returns the variable identifier, in case of a method call
	 * or the LHS of an assignment.
	 *
	 * @return the variable identifier for the LHS of an assignment
	 *          or the variable used to invoke a method.
	 */
	public String getIdent() {
		return ident;
	}
	/**
	 * Returns the name of the method to be invoked, if applicable.
	 *
	 * @return the name of the method to be invoked, if applicable.
	 */
	public String getMethodName() {
		return methodName;
	}
	/**
	 * Returns the type of this expression.
	 *
	 * @return the type of this expression.
	 */
	public int getType() {
		return type;
	}
	/*
	 * Returns a String representation of this expression. Useful for debugging.
	 *
	 * @return a String representing this expression.
	 */
	public String toString() {
		StringBuffer sb = new StringBuffer();
		switch (type) {
			case METHOD: sb.append("METODO"); break;
			case CREATE: sb.append("CREATE"); break;
			case DELETE: sb.append("DELETE"); break;
			case ASSIGN : sb.append("ATRIB"); break;
			case DECLAR: sb.append("DECLAR"); break;
			case MODIFY: sb.append("MODIFY"); break;
			default : sb.append("Erro!"); break;
		}
		return sb.toString();
	}
}