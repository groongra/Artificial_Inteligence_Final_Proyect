package jeops.engine;

import java.util.Enumeration;
import java.util.Vector;

/**
 * The default rule sorter used in this inference engine. It will be the
 * one used to choose the apropriate rule to be fired whenever no other
 * sorter is provided by the user. This sorter won't specify any special
 * ordering, and will return (probably - it can be changed later) the
 * first fireable rule.
 *
 * @see jeops.engine.ConflictSet
 * @version 0.01  03 Dez 1998
 * @author Carlos Figueira Filho (<a href="mailto:csff@di.ufpe.br">csff@di.ufpe.br</a>)
 */
public class DefaultRuleSorter extends RuleSorter {

	/**
	 * Class constructor.
	 */
	public DefaultRuleSorter() {
		super();
	}
	/**
	 * Inserts a rule in this sorter.
	 *
	 * @param rule the rule to be inserted.
	 */
	public void insertRule(Rule rule) {
		super.insertRule(rule);
	}
	/**
	 * Returns the next rule to be fired. In the return of this method,
	 * the symbolic table of the table shall be correctly filled. Also,
	 * after this method has been called, the rule (along with the
	 * instanciation that made it fireable) is removed from the conflict
	 * set.
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
			for (e = getFireableRules().keys(); e.hasMoreElements(); ) {
				String key = (String) e.nextElement();
				Vector rules = (Vector) getFireableRules().get(key);
				if (rules != null && rules.size() != 0) {
					result = (Rule) rules.elementAt(0);
					break;
				}
			}
			removeRule(result);
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