package jeops.engine;

import java.util.Enumeration;
import java.util.Vector;

/**
 * A rule sorter that will fire each rule only once, even if it has
 * more than one possible instantiations.
 *
 * @see jeops.engine.ConflictSet
 * @version 0.01  08 Dez 1998
 * @author Carlos Figueira Filho (<a href="mailto:csff@di.ufpe.br">csff@di.ufpe.br</a>)
 * @invariant there are no rule in the fireableRules whose name is in
 *          the history set.
 */
public class OneShotRuleSorter extends RuleSorter {

	/**
	 * The name of the rules that have already been fired.
	 */
	private Vector history;

	/**
	 * Class constructor.
	 */
	public OneShotRuleSorter() {
		super();
		history = new Vector();
	}
	/**
	 * Removes all rules of this sorter. Also, clears the history of the
	 * rules that have already been fired.
	 */
	public void flush() {
		super.flush();
		history.removeAllElements();
	}
	/**
	 * Inserts a rule in this sorter.
	 *
	 * @param rule the rule to be inserted.
	 */
	public void insertRule(Rule rule) {
		if (!history.contains(rule.getName())) {
			super.insertRule(rule);
		}
	}
	/**
	 * Returns the next rule to be fired. In the return of this method,
	 * the symbolic table of the table shall be correctly filled. Also,
	 * after this method has been called, all instanciations of the rule
	 * are removed from the conflict set.
	 *
	 * @return the rule to be fired, among those that have been inserted
	 *          in this object, according to the policy defined in this
	 *          class.
	 */
	public Rule nextRule() throws NoMoreRulesException {
		if (getFireableRules().size() == 0) {
			throw new NoMoreRulesException();
		} else {
			Enumeration e;
			Rule result = null;
			String key = null;
			for (e = getFireableRules().keys(); e.hasMoreElements(); ) {
				key = (String) e.nextElement();
				Vector rules = (Vector) getFireableRules().get(key);
				if (rules != null && rules.size() != 0) {
					result = (Rule) rules.elementAt(0);
					break;
				}
			}
			if (result == null) {
				throw new NoMoreRulesException();
			}
			getFireableRules().remove(key);
			history.addElement(result.getName());
			return result;
		}
	}
/**
 * Callback method, used to inform this class that a rule was
 * removed from this sorter. It won't be needed.
 *
 * @param ruleName the name of the removed rule.
 */
public void ruleRemoved(String ruleName) {}
}