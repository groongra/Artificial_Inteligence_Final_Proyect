package jeops.engine;

import java.util.Vector;

/**
 * A rule sorter that will choose the rule that has been first declared
 * in the rules file.
 *
 * @see jeops.engine.ConflictSet
 * @version 0.03  14 Mar 2019 Some prints to debug. Daniel Amigo
 * @version 0.01  13 Dez 1998
 * @author Carlos Figueira Filho (<a href="mailto:csff@di.ufpe.br">csff@di.ufpe.br</a>)
 */
public class PriorityRuleSorter extends RuleSorter {

	/**
	 * The names of the rules that are stored in this sorter.
	 */
	private Vector names;

	/**
	 * The quantity of each rules (corresponding with the rules Vector).
	 */
	private Vector quantities;

	/**
	 * The priorities associated with each rule.
	 */
	private Vector priorities;

	/**
	 * Class constructor.
	 */
	public PriorityRuleSorter() {
		super();
		names = new Vector();
		quantities = new Vector();
		priorities = new Vector();
	}
	/**
	 * Removes all rules of this sorter.
	 */
	public void flush() {
		super.flush();
		names.removeAllElements();
		quantities.removeAllElements();
		priorities.removeAllElements();
	}
	/**
	 * Inserts a rule in this sorter.
	 *
	 * @param rule the rule to be inserted.
	 */
	public void insertRule(Rule rule) {
		super.insertRule(rule);
		String name = rule.getName();
		int priority = rule.getPriority();
		int i = names.indexOf(name);
		if (i == -1) {
			int j;
			for (j = 0; j < priorities.size(); j++) {
				int pri = ((Integer) priorities.elementAt(j)).intValue();
				if (priority < pri) {
					break;
				}
			}
			names.insertElementAt(name, j);
			quantities.insertElementAt(new Integer(1), j);
			priorities.insertElementAt(new Integer(priority), j);
		} else {
			int val = ((Integer) quantities.elementAt(i)).intValue();
			quantities.setElementAt(new Integer(val + 1), i);
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
		if (getFireableRules().size() == 0) {
			throw new NoMoreRulesException();
		} else {
			String name = (String) names.elementAt(0);
			Vector rules = (Vector) getFireableRules().get(name);
			System.out.println("PriorityRuleSorter::nextRule(). "+rules.size()+" fireable rules:");
			for(int i = 0; i < rules.size(); i++) {
				Rule a = (Rule) rules.elementAt(i);
				System.out.println("\t "+(i+1)+": "+a.getName());
			}
			Rule result = (Rule) rules.elementAt(0);
			removeRule(result);
			return result;
		}
	}
/**
 * Callback method, used to inform this class that a rula was
 * removed from this sorter.
 *
 * @param ruleName the name of the removed rule.
 */
public void ruleRemoved(String ruleName) {
	int i = names.indexOf(ruleName);
	if (i != -1) {
		int quant = ((Integer) quantities.elementAt(i)).intValue();
		quant--;
		if (quant > 0) { // There's still rules of this kind.
			quantities.setElementAt(new Integer(quant), i);
		} else {
			names.removeElementAt(i);
			quantities.removeElementAt(i);
			priorities.removeElementAt(i);
		}
	}
}
}