package jeops.engine;

import java.util.Vector;
import jeops.parser.Parser;

/**
 * The main class of JEOPS. This class models the knowledge the agent
 * has about the world. In it are stored the facts (objects) and
 * (production) rules that act on the first.
 *
 *
 * @version 0.03  14 Mar 2019 Some prints to debug. Daniel Amigo
 * @version 0.02  29 Jun 1999  First version with comments in English.
 * @author Carlos Figueira Filho (<a href="mailto:csff@di.ufpe.br">csff@di.ufpe.br</a>)
 * @history 0.01  26 Ago 1998  Esta � a classe que modela o conhecimento
 *             do agente (que utiliza esta base) sobre o mundo. Nela est�o
 *             os fatos (objetos) e as regras (de produ��o) que atuam sobre
 *             ele.
 */
public class KnowledgeBase {

	/**
	 * The fact (object) base.
	 */
	private ObjectBase objectBase;

	/**
	 * The (production) rule base.
	 */
	private RuleBase ruleBase;

	/**
	 * Creates a new knowledge base, given the name of the file containing
	 *    the rules. If the rules are not in the specified syntax, the program
	 *    will abort.
	 *
	 * @param ruleFile  the name of the file containing the rules.
	 * @comingup a constructor receiving a Stream as parameter, so that applets
	 *            will also be able to use this knowledge base.
	 */
	public KnowledgeBase(String ruleFile) {
		this(ruleFile, new DefaultRuleSorter());
	}
	/**
	 * Creates a new knowledge base, given the name of the file containing
	 *    the rules. If the rules are not in the specified syntax, the program
	 *    will abort.
	 *
	 * @param ruleFile  the name of the file containing the rules.
	 * @param ruleSorter a rule sorter that will define the policy
	 *          for the rule firing.
	 * @comingup a constructor receiving a Stream as parameter, so that applets
	 *            will also be able to use this knowledge base.
	 */
	public KnowledgeBase(String ruleFile, RuleSorter ruleSorter) {
		objectBase = new ObjectBase();
		ruleBase = new RuleBase(objectBase, ruleSorter);
		try {
			new Parser(ruleFile, ruleBase);
		} catch (Exception e) {
			System.out.println("Parser error: " + e);
			e.printStackTrace();
			System.exit(0);
		}
	}
	/**
	 * Inserts a new object in this knowledge base.
	 *
	 * @param obj the object being inserted.
	 */
	public void join(Object obj) {
		objectBase.join(obj);
		ruleBase.joinObject(obj);
	}
	/**
	 * Remove all facts (objects) of the object base.
	 */
	public void flush() {
		objectBase.flush();
	}
	/**
	 * Returns the object base over which this knowledge base works.
	 *
	 * @return the object base over which this knowledge base works.
	 */
	public ObjectBase getObjectBase() {
		return objectBase;
	}
	/**
	 * Returns the rule base that controls this knowledge base.
	 *
	 * @return the rule base that controls this knowledge base.
	 */
	public RuleBase getRuleBase() {
		return ruleBase;
	}
	/**
	 * Returns the objects of a given class.
	 *
	 * @param className the name of the class.
	 */
	public Vector objects(String className) {
		return objectBase.objects(className);
	}
	/**
	 * Removes a given object of this knowledge base.
	 *
	 * @param obj the object being removed.
	 */
	public void retract(Object obj) {
		objectBase.remove(obj);
		ruleBase.retractObject(obj);
	}
	/**
	 * Fires the rules in the rule base with the objects present in the
	 *    object base, until no rule is fireable anymore.
	 */
	public void run() {
		boolean fim = true;
		int counter = 1;
		do {
			System.out.println("Iteration: "+counter);	// Here you can use Debug Mode to watch your objectBase in execution time!
			try {
				fim = !ruleBase.fireSingleRule(this);
			} catch (RuleNotFiredException e)  {
				System.out.println("Error firing rules: " + e);
				e.printStackTrace();
				System.exit(0);
			}
			counter++;
			System.out.println();
		} while (!fim);
	}
	/**
	 * Defines a conflict set policy for this knowledge base.
	 *
	 * @param ruleSorter the sorter that implements the needed policy.
	 */
	public void setRuleSorter(RuleSorter ruleSorter) {
		this.ruleBase.setRuleSorter(ruleSorter);
	}
}