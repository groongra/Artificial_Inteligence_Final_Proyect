package jeops.engine;

import java.util.Vector;
import java.util.Hashtable;

/**
 * A rule sorter that will fire each pair (rule, instanciations) only
 * once, that is, no rule will fire twice with the same list of objects.
 * In spite of what happens in other implementations of the rule sorter,
 * the control of which rules belong to the conflict set here is done
 * entirely in the insertion of the rule in this sorter. The
 * <code>nextRule()</code> operation will only retrieve the first one
 * and take it out of the set.
 *
 * @see jeops.engine.ConflictSet
 * @version 0.01  13 Dez 1998
 * @author Carlos Figueira Filho (<a href="mailto:csff@di.ufpe.br">csff@di.ufpe.br</a>)
 * @invariant there are no rule in the fireableRules which are also in
 *          the history set.
 */
public class NaturalRuleSorter extends RuleSorter {

	/**
	 * The rules that have already been fired. The structure of this
	 * hashtable is the following: the rules are the keys in the history
	 * Hashtable to other hashtables, which contain the symbolic tables
	 * filled with the objects that made the rule fireable.
	 */
	private Hashtable history;

	/**
	 * Class constructor.
	 */
	public NaturalRuleSorter() {
		super();
		history = new Hashtable();
	}
	/**
	 * Removes all rules of this sorter. Also, clears the history of the
	 * rules that have already been fired.
	 */
	public void flush() {
		super.flush();
		history.clear();
	}
	/**
	 * Inserts a rule in this sorter.
	 *
	 * @param rule the rule to be inserted.
	 */
	public void insertRule(Rule rule) {
		Object instances = history.get(rule.getName());
		Hashtable aux = null;
		boolean canBeInserted = false;

		if (instances == null) {
			aux = new Hashtable();
			canBeInserted = true;
		} else {
			aux = (Hashtable) instances;
			if (!aux.containsKey(rule.getTable())) {
				canBeInserted = true;
			}
		}
		if (canBeInserted) {
//			fireableRules.addElement(rule.clone());
			Object obj = rule.getTable().clone();
			aux.put(obj, obj);
			history.put(rule, obj);
		}
	}
	/**
	 * Returns the next rule to be fired. In the return of this method,
	 * the symbolic table of the table shall be correctly filled.
	 *
	 * @return the rule to be fired, among those that have been inserted
	 *          in this object, according to the policy defined in this
	 *          class.
	 */
	public Rule nextRule() throws NoMoreRulesException {
		if (isEmpty()) {
			throw new NoMoreRulesException();
		} else {
			Rule result = null ; //(Regra) fireableRules.elementAt(0);
//			fireableRules.removeElementAt(0);
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