package jeops.engine;

import java.util.Vector;
import java.util.Enumeration;

/**
 * This classes models the conflict set of the inference engine. The
 * conflict set is the place where the fireable rules are stored, and
 * one of them is to be chosen to fire at the moment. <br>
 * The user can determine the rule ordering policy used to choose the
 * rule to fire. It can be achieved by using the
 * <code>setRuleSorter(RuleSorter)</code> method.
 *
 * @see jeops.engine.RuleSorter
 * @version 0.01  13 Dez 1998
 * @author Carlos Figueira Filho (<a href="mailto:csff@di.ufpe.br">csff@di.ufpe.br</a>)
 */
public class ConflictSet {

	/**
	 * The object used to order the rules to choose the one that is to be
	 * fired.
	 */
	private RuleSorter ruleSorter;

	/**
	 * Creates a new conflict set, using the default rule sorter.
	 */
	public ConflictSet() {
		this.ruleSorter = new DefaultRuleSorter();
	}
	/**
	 * Creates a new conflict set, given its rule sorter.
	 */
	public ConflictSet(RuleSorter ruleSorter) {
		this.ruleSorter = ruleSorter;
	}
	/**
	 * Removes all rules from this conflict set.
	 */
	public void flush() {
		ruleSorter.flush();
	}
	/**
	 * Inserts a rule in this conflict set. The rule's symbolic table will
	 * be filled with the objects that made it fireable.
	 * 
	 * @param rule the rule to be inserted.
	 */
	public void insertRule(Rule rule) {
		ruleSorter.insertRule(rule);
	}
	/**
	 * Checks whether this set has any elements.
	 *
	 * @return <code>false</code> if there is at least one fireable rule
	 *          in this set; <code>true</code> otherwise.
	 */
	public boolean isEmpty() {
		return ruleSorter.isEmpty();
	}
	/**
	 * Returns the next rule to be fired. In the return of this method,
	 * the symbolic table of the table shall be correctly filled.
	 *
	 * @return the rule to be fired, among those that have been inserted
	 *          in this object, according to the policy defined in the
	 *          rule sorter.
	 * @exception NoMoreRulesException if there aren't any more rules
	 *          to be fired.
	 */
	public Rule nextRule() throws NoMoreRulesException {
		return ruleSorter.nextRule();
	}
	/**
	 * Re-test all instanciations of the rules in this set to check
	 * whether they're still fireable, removing those that aren't
	 * anymore. This method tends to be a little slow, so it must be
	 * used with care.
	 */
	public void removeNonFirebleRules() {
		ruleSorter.removeNonFireableRules();
	}
/**
 * Remove all rules from this set that uses the given object
 * in its instantiations.
 *
 * @param obj the given object
 */
public void removeRulesWith(Object obj) {
	ruleSorter.removeRulesWith(obj);
}
	/**
	 * Modifies this conflict set's rule sorter.
	 *
	 * @param ruleSorter the new rule sorter for this conflict set.
	 */
	public void setRuleSorter(RuleSorter ruleSorter) {
		this.ruleSorter = ruleSorter;
	}
}