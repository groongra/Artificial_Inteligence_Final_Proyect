package jeops.expressions;

/**
 * This class models an exception raised when an illegal field access is attempted.
 *
 * @version 0.01  08 Mar 1999
 * @author Carlos Figueira Filho (<a href="mailto:csff@di.ufpe.br">csff@di.ufpe.br</a>)
 */
public class FieldAccessException extends Exception {

	/**
	 * Creates a new FieldAccessException without detail message.
	 */
	public FieldAccessException() {
		super();
	}
	/**
	 * Creates a new FieldAccessException with a given detail message.
	 *
	 * @param s the detail message for this exception.
	 */
	public FieldAccessException(String s) {
		super(s);
	}
}