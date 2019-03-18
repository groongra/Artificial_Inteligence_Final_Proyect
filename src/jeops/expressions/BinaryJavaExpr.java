package jeops.expressions;

/**
 * This class models a binary expression of (our subset of) the java language.
 * The unary expressions currently supported are:
 * <ul>
 *  <li> Boolean expressions
 *  <ul>
 *   <li> Logical OR (<code>||</code>)
 *   <li> Logical AND (<code>&&</code>)
 *  </ul>
 *  <li> Relational expressions
 *  <ul>
 *   <li> Equality (<code>==</code>)
 *   <li> Inequality (<code>!=</code>)
 *   <li> Greater Than (<code>&gt;</code>)
 *   <li> Greater or Equals (<code>&gt;=</code>)
 *   <li> Less Than (<code>&lt;</code>)
 *   <li> Less or Equals (<code>&lt;=</code>)
 *  </ul>
 *  <li> Arithmetic expressions
 *  <ul>
 *   <li> Sum (<code>+</code>)
 *   <li> Subtraction (<code>-</code>)
 *   <li> Product (<code>*</code>)
 *   <li> Division (<code>/</code>)
 *  </ul>
 * </ul>
 *
 * @version 0.01  02 Mar 1999
 * @author Carlos Figueira Filho (<a href="mailto:csff@di.ufpe.br">csff@di.ufpe.br</a>)
 */
public class BinaryJavaExpr extends JavaExpr {

	/**
	 * Constant used to indicate that this expression represents a logical OR.
	 */
	public static final int LOR = 0;

	/**
	 * Constant used to indicate that this expression represents a logical AND.
	 */
	public static final int LAND = 1;

	/**
	 * Constant used to indicate that this expression represents an
	 *   equality comparison.
	 */
	public static final int EQ = 10;

	/**
	 * Constant used to indicate that this expression represents an
	 *   inequality comparison.
	 */
	public static final int NEQ = 11;

	/**
	 * Constant used to indicate that this expression represents a greater
	 *   than comparison.
	 */
	public static final int GT = 12;

	/**
	 * Constant used to indicate that this expression represents a greater
	 *   than or equals comparison.
	 */
	public static final int GEQ = 13;

	/**
	 * Constant used to indicate that this expression represents a less
	 *   than comparison.
	 */
	public static final int LT = 14;

	/**
	 * Constant used to indicate that this expression represents a less
	 *   than or equals comparison.
	 */
	public static final int LEQ = 15;

	/**
	 * Constant used to indicate that this expression represents a sum.
	 */
	public static final int SUM = 20;

	/**
	 * Constant used to indicate that this expression represents a subtraction.
	 */
	public static final int SUB = 21;

	/**
	 * Constant used to indicate that this expression represents a multiplication.
	 */
	public static final int MUL = 22;

	/**
	 * Constant used to indicate that this expression represents a division.
	 */
	public static final int DIV = 23;

	/**
	 * The type of this unary expression.
	 *
	 * @see #LOR
	 * @see #LAND
	 * @see #EQ
	 * @see #NEQ
	 * @see #GT
	 * @see #GEQ
	 * @see #LT
	 * @see #LEQ
	 * @see #SUM
	 * @see #SUB
	 * @see #MUL
	 * @see #DIV
	 */
	private int binaryType;

	/**
	 * The left subexpression of this binary expression.
	 */
	private JavaExpr left;

	/**
	 * The right subexpression of this binary expression.
	 */
	private JavaExpr right;

	/**
	 * Class constructor.
	 *
	 * @param left the left subexpression of this binary expression.
	 * @param right the right subexpression of this binary expression.
	 * @param binaryType the type of this expression.
	 * @see #LOR
	 * @see #LAND
	 * @see #EQ
	 * @see #NEQ
	 * @see #GT
	 * @see #GEQ
	 * @see #LT
	 * @see #LEQ
	 * @see #SUM
	 * @see #SUB
	 * @see #MUL
	 * @see #DIV
	 */
	public BinaryJavaExpr(JavaExpr left, JavaExpr right, int binaryType) {
		switch (binaryType) {
			case LOR :
			case LAND :
			case EQ :
			case NEQ :
			case GT :
			case GEQ :
			case LT :
			case LEQ : this.type = BOOL; break;
			case SUB :
			case MUL :
			case DIV : this.type = INT; break;
			case SUM : if ((left.type == OBJ) ||
							(right.type == OBJ)) {
							this.type = OBJ;
						} else {
							this.type = INT;
						}
		}
		this.left = left;
		this.right = right;
		this.binaryType = binaryType;
		this.identObjetos = new StringSet((StringSet)left.identObjetos(),
										  (StringSet)right.identObjetos());
	}
	/**
	 * Prints the tree of this expression with the given identation.
	 *   Useful for debugging.
	 *
	 * @param spaces the identation level for this dumping.
	 */
	public void dump(int spaces) {
		for (int i = 0; i < spaces; i++) System.out.print(" ");
		switch (binaryType) {
			case LOR : System.out.println("Logical OR"); break;
			case LAND : System.out.println("Logical AND"); break;
			case EQ : System.out.println("Relational Equality"); break;
			case NEQ : System.out.println("Relational Inequality"); break;
			case GT : System.out.println("Relational Greater than"); break;
			case GEQ : System.out.println("Relational Greater than or equals"); break;
			case LT : System.out.println("Relational Less than"); break;
			case LEQ : System.out.println("Relational Less than or equals"); break;
			case SUM : System.out.println("Arithmetic Sum"); break;
			case SUB : System.out.println("Arithmetic Subtraction"); break;
			case MUL : System.out.println("Arithmetic Multiplication"); break;
			case DIV : System.out.println("Arithmetic Division"); break;
		}
		left.dump(spaces + 2);
		right.dump(spaces + 2);
	}
	/**
	 * Returns an object representing the class of this expression.
	 */
	public Class getClasse() {
		switch (binaryType) {
			case LOR :
			case LAND :
			case EQ :
			case NEQ :
			case GT :
			case GEQ :
			case LT :
			case LEQ : return Boolean.TYPE;
			case SUB :
			case MUL :
			case DIV : return Integer.TYPE;
			case SUM : if ((left.getClasse() == String.class) ||
							(right.getClasse() == String.class)) {
							return String.class;
						} else {
							return Integer.TYPE;
						}
			default  : return null;
		}
	}
	/**
	 * Returns the boolean result of this expression.
	 *
	 * @param table the symbolic table where the values of the variables
	 *          used within this expression are stored.
	 * @return the boolean result of this expression; if its type is not
	 *          a relational operation, logical <b>and</b> or logical
	 *          <b>or</b>, <code>false</code> will be returned.
	 * @exception MethodEvaluationException if an exception was thrown while
	 *   evaluating a method that may occur within this expression.
	 * @exception FieldAccessException if an exception was thrown while
	 *   accessing a field of an object that may occur in this expression.
	 */
	protected boolean internalEvaluateBoolean(SymbolicTable table) throws 
						MethodEvaluationException, FieldAccessException {
		int intLeft, intRight;
		intLeft = left.evaluateInt(table);
		intRight = right.evaluateInt(table);
		boolean boolLeft, boolRight;
		boolLeft = left.evaluateBool(table);
		boolRight = right.evaluateBool(table);
		Object objLeft, objRight;
		objLeft = left.evaluateObj(table);
		objRight = right.evaluateObj(table);
		switch (binaryType) {
			case LOR : return (boolLeft || boolRight);
			case LAND : return (boolLeft && boolRight);
			case EQ :
				if (objLeft == null) {
					return objRight == null;
				} else {
					return (objLeft.equals(objRight));
				}
			case NEQ :
				if (objLeft == null) {
					return objRight != null;
				} else {
					return (!objLeft.equals(objRight));
				}
			case GT : return (intLeft > intRight);
			case GEQ : return (intLeft >= intRight);
			case LT : return (intLeft < intRight);
			case LEQ : return (intLeft <= intRight);
			default  : return false;
		}
	}
	/**
	 * Returns the integer result of this expression.
	 *
	 * @param table the symbolic table where the values of the variables
	 *          used within this expression are stored.
	 * @return the integer result of this expression. If its type is not
	 *          an arithmetic expression, it will return <code>0</code>
	 *          (zero).
	 * @exception MethodEvaluationException if an exception was thrown while
	 *   evaluating a method that may occur within this expression.
	 * @exception FieldAccessException if an exception was thrown while
	 *   accessing a field of an object that may occur in this expression.
	 */
	protected int internalEvaluateInt(SymbolicTable table)
				throws MethodEvaluationException, FieldAccessException {
		int valueLeft, valueRight;
		valueLeft = left.evaluateInt(table);
		valueRight = right.evaluateInt(table);
		switch (binaryType) {
			case SUM : return (valueLeft + valueRight);
			case SUB : return (valueLeft - valueRight);
			case MUL : return (valueLeft * valueRight);
			case DIV : return (valueLeft / valueRight);
			default  : return 0;
		}
	}
	/**
	 * Returns in an object the result of this expression.
	 *
	 * @param table the symbolic table where the values of the variables
	 *          used within this expression are stored.
	 * @return the result of this expression, wrapped in an object.
	 *          If the kind of this expression if not a SUM (string
	 *          concatenation), it will return <code>null</code>.
	 * @exception MethodEvaluationException if an exception was thrown while
	 *   evaluating a method that may occur within this expression.
	 * @exception FieldAccessException if an exception was thrown while
	 *   accessing a field of an object that may occur in this expression.
	 */
	protected Object internalEvaluateObject(SymbolicTable table)
				throws MethodEvaluationException, FieldAccessException {
		if (this.type == OBJ && this.binaryType == SUM) {
			return ("" + left.evaluateObj(table) + right.evaluateObj(table));
		} else {
			return null;
		}
	}
}