package jeops.expressions;

/**
 * This class models an exception that might be thrown when a reference
 * to an inexistent class was attempted.
 *
 * @version 0.02  28 Jun 1999   First version with comments in English.
 * @author Carlos Figueira Filho (<a href="mailto:csff@di.ufpe.br">csff@di.ufpe.br</a>)
 * @history 0.01  26 Dez 1997   Esta classe modela uma exceção que ocorre
 *    quando se tentou utilizar uma classe que não existe (ou não foi
 *    encontrada).
 */
public class NoSuchClassException extends Exception {

	/**
	 * Class constructor.
	 */
	public NoSuchClassException() {
		super();
	}
	/**
	 * Class constructor, given a detail message.
	 *
	 * @param s the message that explains why this exception happened.
	 */
	public NoSuchClassException(String s) {
		super(s);
	}
}