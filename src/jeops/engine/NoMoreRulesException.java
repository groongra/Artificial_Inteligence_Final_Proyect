package jeops.engine;

/**
 * This class defines an exception that will be thrown when there aren't
 * more rules to be fired in a conflict set.
 *
 * @version 0.01  08 Dez 1998
 * @author Carlos Figueira Filho (<a href="mailto:csff@di.ufpe.br">csff@di.ufpe.br</a>)
 */
public class NoMoreRulesException extends Exception {

	/**
	 * Creates a new RuleNotFiredException, without a detailed message.
	 */
	public NoMoreRulesException() {
		super();
	}
	/**
	 * Creates a new NoMoreRulesException with a given detail message.
	 */
	public NoMoreRulesException(String s) {
		super(s);
	}
}