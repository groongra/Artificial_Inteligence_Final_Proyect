package jeops.engine;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;
import jeops.expressions.Triple;

/**
 * Abstract class that defines the methods needed by any rule sorter, used
 * by the conflict set to choose the rule to be fired.
 *
 * @see jeops.engine.ConflictSet
 * @version 0.05  24 May 1999  The fireable rules became a hashtable (it
 *                            was a Vector).
 * @author Carlos Figueira Filho (<a href="mailto:csff@di.ufpe.br">csff@di.ufpe.br</a>)
 * @history 0.01  03 Dez 1998
 * @history 0.02  13 Dez 1998  Became an abstract class as opposed to the
 *                            original interface implementation.
 * @history 0.04  23 May 1999  Method to remove all rules in the fireable
 *                            rules.
 */
public abstract class RuleSorter {

	/**
	 * The rules that can be fired.
	 */
	private Hashtable fireableRules;

	/**
	 * Class constructor.
	 */
	public RuleSorter() {
		fireableRules = new Hashtable();
	}
	/**
	 * Removes all rules of this sorter.
	 */
	public void flush() {
		fireableRules.clear();
	}
	/**
	 * Returns the hashtable that contains the fireable rules
	 * for this sorter.
	 *
	 * @return the fireable rules in this sorter.
	 */
	protected Hashtable getFireableRules() {
		return fireableRules;
	}
	/**
	 * Inserts a rule in this sorter. The rule's symbolic table will
	 * be filled with the objects that made it fireable.
	 *
	 * @param rule the rule to be inserted.
	 */
	public void insertRule(Rule rule) {
		Object obj = fireableRules.get(rule.getName());
		Vector rules;
		if (obj == null) {
			rules = new Vector();
		} else {
			rules = (Vector) obj;
		}
		if (!rules.contains(rule)) {
			rules.addElement(rule.clone());
			fireableRules.put(rule.getName(), rules);
		}
	}
	/**
	 * Checks whether this sorter has any rule to be fired.
	 *
	 * @return <code>false</code> if there is at least one fireable rule;
	 *          <code>true</code> otherwise.
	 */
	public boolean isEmpty() {
		return (fireableRules.size() == 0);
	}
	/**
	 * Returns the next rule to be fired. In the return of this method,
	 * the symbolic table of the table shall be correctly filled.
	 *
	 * @return the rule to be fired, among those that have been inserted
	 *          in this object, according to the policy defined in the
	 *          subclass that implements this method.
	 * @exception NoMoreRulesException if there aren't any more rules
	 *          to be fired.
	 */
	public abstract Rule nextRule() throws NoMoreRulesException;
	/**
	 * Re-test all instanciations of the rules to check whether they're
	 * still fireable, removing those that aren't anymore. This method
	 * tends to be a little slow, so it must be used with care.
	 */
	public void removeNonFireableRules() {
		synchronized (fireableRules) {
			for (Enumeration e = fireableRules.keys(); e.hasMoreElements(); ) {
				String key = (String) e.nextElement();
				Vector rules = (Vector) fireableRules.get(key);
				int size = rules.size();
				for (int i = size - 1; i >= 0; i--) {
					Rule r = (Rule) rules.elementAt(i);
					if (!r.isFireable()) {
						rules.removeElementAt(i);
						size--;
					}
				}
				if (size == 0) {
					fireableRules.remove(key);
				}
			}
		}
	}
/**
 * Remove the given rule from the fireable rules. This method will
 * tipically be called by this class' subclasses, when returning a
 * rule.
 *
 * @param rule the given rule
 */
public void removeRule(Rule rule) {
	synchronized (fireableRules) {
		String name = rule.getName();
		Vector rules = (Vector) fireableRules.get(name);
		rules.removeElement(rule);
		ruleRemoved(name);
		if (rules.size() == 0) {
			fireableRules.remove(name);
		}
	}
}
/**
 * Remove all rules from the fireable ones that uses the given
 * object in its instantiations.
 *
 * @param obj the given object
 */
public void removeRulesWith(Object obj) {
	synchronized (fireableRules) {
		for (Enumeration e = fireableRules.keys(); e.hasMoreElements(); ) {
			String key = (String) e.nextElement();
			Vector rules = (Vector) fireableRules.get(key);
			int size = rules.size();
			for (int i = size - 1; i >= 0; i--) {
				Rule r = (Rule) rules.elementAt(i);
				Vector triples = r.getTable().getTriples();
				for (int j = 0; j < triples.size(); j++) {
					Triple t = (Triple) triples.elementAt(j);
					if (t.getObject().equals(obj)) {
						rules.removeElementAt(i);
						this.ruleRemoved(r.getName());
						size--;
						break;
					}
				}
			}
			if (size == 0) {
				fireableRules.remove(key);
			}
		}
	}
}
/**
 * Callback method, used to inform this class subclasses that
 * a rule was removed from this sorter.
 *
 * @param ruleName the name of the removed rule.
 */
protected abstract void ruleRemoved(String ruleName);
}