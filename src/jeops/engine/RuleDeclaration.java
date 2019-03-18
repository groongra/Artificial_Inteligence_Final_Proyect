package jeops.engine;

/**
 * This class models a variable declaration (universally quantified)
 * used in a rule definition.
 *
 * @version 0.02  29 Jun 1999   First version with comments in English.
 * @author Carlos Figueira Filho (<a href="mailto:csff@di.ufpe.br">csff@di.ufpe.br</a>)
 * @history 0.01  30 Nov 1997   Esta classe modela uma declaração de
 *                              variável (universalmente quantificada) a
 *                              ser usada em uma regra.
 */
public class RuleDeclaration {

	/**
	 * The name of the class to which the variable declared belongs.
	 */
	private String className;

	/**
	 * The identifier (which must be unique) of the variable.
	 */
	private String ident;

	/**
	 * Class constructor.
	 *
	 * @param className the name of the class of the variable
	 * @param ident the identifier of the variable
	 */
	public RuleDeclaration(String className, String ident) {
		this.className = className;
		this.ident = ident;
	}
	/**
	 * Prints a tree representation of this base. Useful for debugging.
	 *
	 * @param spaces the identation of the information to be printed.
	 */
	public void dump (int spaces) {
		for (int i = 0; i < spaces; i++)
			System.out.print(" ");
		System.out.println(className + " " + ident);
	}
	/**
	 * Returns the class name of the variable declaration.
	 *
	 * @return the class name of the variable declaration.
	 */
	public String getClassName() {
		return className;
	}
	/**
	 * Returns the identifier of the variable.
	 *
	 * @return the identifier of the variable.
	 */
	public String getIdent() {
		return ident;
	}
}