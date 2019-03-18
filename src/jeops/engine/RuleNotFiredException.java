package jeops.engine;

/**
 * This class models an exception that may be thrown when a rule can't
 * be fired, maybe due to its bad formation or by an exception being
 * thrown when firing it.
 *
 * @version 0.02  29 Jun 1999   First version with comments in English.
 * @author Carlos Figueira Filho (<a href="mailto:csff@di.ufpe.br">csff@di.ufpe.br</a>)
 * @history 0.01  10 Dez 1997  Esta classe modela uma exceção que ocorre
 *                    quando uma regra não pode ser disparada, provavelmente
 *                    pela má formação da entrada.
 */
public class RuleNotFiredException extends Exception {

	/**
	 * Class constructor.
	 */
	public RuleNotFiredException() {
		super();
	}
	/**
	 * Class constructor, given a detail message.
	 *
	 * @param s the message that explains why this exception happened.
	 */
	public RuleNotFiredException(String s) {
		super(s);
	}
}