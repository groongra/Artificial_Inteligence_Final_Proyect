package jeops.expressions;

/**
 * This class models an exception that may be thrown when a method is
 * invoked.
 *
 * @version 0.01  29 Jun 1999   First version with comments in English.
 * @author Carlos Figueira Filho (<a href="mailto:csff@di.ufpe.br">csff@di.ufpe.br</a>)
 * @history 0.01  10 Dez 1997    Esta classe modela uma exceção que
 *                               ocorre quando um método foi invocado.
 */
public class MethodEvaluationException extends Exception {

	/**
	 * Class constructor.
	 */
	public MethodEvaluationException() {
		super();
	}
	/**
	 * Class constructor, given a detail message.
	 *
	 * @param s the message that explains why this exception happened.
	 */
	public MethodEvaluationException(String s) {
		super(s);
	}
}