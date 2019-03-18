package jeops.engine;

import java.util.Vector;

/**
 * A rule sorter that will fire the least recently used rule.
 *
 * @see jeops.engine.ConflictSet
 * @version 0.01  13 Dez 1998
 * @author Carlos Figueira Filho (<a href="mailto:csff@di.ufpe.br">csff@di.ufpe.br</a>)
 */
public class LRURuleSorter extends RuleSorter {

	/**
	 * The names of the rules that are stored in this sorter.
	 */
	private Vector names;

	/**
	 * Class constructor.
	 */
	public LRURuleSorter() {
		super();
		names = new Vector();
	}
	/**
	 * Removes all rules of this sorter.
	 */
	public void flush() {
		super.flush();
		names.removeAllElements();
	}
	/**
	 * Inserts a rule in this sorter.
	 *
	 * @param rule the rule to be inserted.
	 */
	public void insertRule(Rule rule) {
		super.insertRule(rule);
		String name = rule.getName();
		if (!names.contains(name)) {
			names.insertElementAt(name, 0);
		}
	}
	/**
	 * Returns the next rule to be fired. In the return of this method,
	 * the symbolic table of the table shall be correctly filled. Also,
	 * after this method has been called, all instanciations of the rule
	 * are moved to the "end of the firing line".
	 *
	 * @return the rule to be fired, among those that have been inserted
	 *          in this object, according to the policy defined in this
	 *          class.
	 */
	public Rule nextRule() throws NoMoreRulesException {
		if (getFireableRules().size() == 0) {
			throw new NoMoreRulesException();
		} else {
			Rule result = null;
			String name = null;
			int i;
			for (i = 0; i < names.size(); i++) {
				name = (String) names.elementAt(i);
				Vector rules = (Vector) getFireableRules().get(name);
				if (rules != null && rules.size() != 0) {
					result = (Rule) rules.elementAt(0);
					break;
				}
			}
			names.removeElement(name);
			names.addElement(name);
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