package jeops.engine;

import java.util.Enumeration;
import java.util.Vector;
import jeops.expressions.SymbolicTable;

/**
 * Class that models the heart of the inference engine. It will decide when
 * the rules are fired, filling their symbolic tables with the objects needed
 * for the decision making.
 *
 * @version 0.05  23 May 1999   The first version with comments in English.
 *                        There was also a major change in this class: now
 *                        the fireable rules are stored in a conflict set,
 *                        which will define the policy for the ordering of the
 *                        rules being fired.
 * @author Carlos Figueira Filho (<a href="mailto:csff@di.ufpe.br">csff@di.ufpe.br</a>)
 * @see jeops.engine.Rule
 * @history 0.03  17 Dez 1997   Esta � a classe que modela o cora��o do
 *                        motor de infer�ncia. Ela decidir� quando as regras
 *                        ser�o usadas, preenchendo a tabela de s�mbolos com
 *                        os objetos necess�rios para a tomada da decis�o.
 * @history 0.04  01 Mar 1998   Altera��o profunda no m�todo regraDisparavel()
 *                        A id�ia de se avaliar todas as possibilidades at�
 *                        se encontrar uma combina��o que torne as premissas
 *                        verdadeiras (ou at� exaurir todas as possibilidades)
 *                        de uma regra foi abolida. A partir desta vers�o, se
 *                        um objeto associado a uma vari�vel declarada provocar
 *                        que uma das premissas seja avaliada para
 *                        <code>false</code>, todas as outras possibilidades
 *                        para esta associa��o n�o mais s�o testadas.
 */
public class RuleBase {

	/**
	 * The set of rules in this base.
	 */
	private Vector rules;

	/**
	 * The object base on which the engine (of the base) is acting.
	 */
	private ObjectBase objectBase;

	/**
	 * The conflict set for this base.
	 */
	private ConflictSet conflictSet;

	/**
	 * Creates a new rule base, given its object base.
	 *
	 * @param base the object base over which it will work.
	 */
	public RuleBase(ObjectBase base) {
		this.rules = new Vector();
		objectBase = base;
		this.conflictSet = new ConflictSet();
	}
	/**
	 * Creates a new rule base, given its object base and a rule sorter
	 * to define the conflict resolution policy.
	 *
	 * @param base the object base.
	 * @param ruleSorter the sorter that will define this base's conflict
	 *          resolution policy.
	 */
	public RuleBase(ObjectBase base, RuleSorter ruleSorter) {
		this.rules = new Vector();
		objectBase = base;
		this.conflictSet = new ConflictSet(ruleSorter);
	}
	/**
	 * Adds a new rule to this rule base.
	 *
	 * @param r the rule being added to this base.
	 */
	public void addRule(Rule r) {
		rules.addElement(r);
	}
/**
 * Tells this base that a new object was inserted into its
 * object base, so that the rules can be checked against this
 * object.
 *
 * @param obj the object that was inserted.
 */
public void joinObject(Object obj) {
	int numRegra = -1, i;
	String objClass = obj.getClass().getName();
	for (Enumeration e = rules.elements(); e.hasMoreElements(); ) {
		Rule r = (Rule) e.nextElement();
		numRegra++;
		SymbolicTable tabela = r.getTable();
		Vector decls = r.getDeclarations();
		for (int novo = 0; novo < decls.size(); novo++) {
			int[] tams = new int[decls.size()];
			boolean semObjetos = false;
			for (i = 0; i < tams.length; i++) {
				String nomeClasse = ((RuleDeclaration)decls.elementAt(i)).getClassName();
				if (i == novo) {
					tams[i] = 1;
					if (!nomeClasse.equals(objClass)) {
						semObjetos = true;
						break;
					}
				} else {
					tams[i] = objectBase.objects(nomeClasse).size();
					if (tams[i] == 0) { // No objects of a needed class
						semObjetos = true;
					}
				}
			}
			if (semObjetos)
				continue;
			int[] numero = new int[tams.length];
			for (i = 0; i < numero.length; i++)
				numero[i] = 0;
			boolean fim = false;
			int linha = 0;
			while (!fim) {
				String nomeClasse = ((RuleDeclaration)decls.elementAt(linha)).getClassName();
				String ident = ((RuleDeclaration)decls.elementAt(linha)).getIdent();
				Object objeto = null;
				if (linha == novo) {
					objeto = obj;
				} else {
					Vector objsDaClasse = objectBase.objects(nomeClasse);
					objeto = objsDaClasse.elementAt(numero[linha]);
				}
				tabela.setReference(ident, objeto);

				if (r.isFireable(linha)) {
					if (linha != numero.length - 1) {
						numero[++linha] = 0;
					} else {
						conflictSet.insertRule(r);
						numero[linha]++;
						while (linha >= 0 && numero[linha] == tams[linha]) {
							numero[linha] = 0;
							linha--;
							if (linha >= 0) {
								numero[linha]++;
							}
						}
					}
				} else {
					numero[linha]++;
					while (linha >= 0 && numero[linha] == tams[linha]) {
						numero[linha] = 0;
						linha--;
						if (linha >= 0) {
							numero[linha]++;
						}
					}
				}
				fim = (linha < 0) || (numero[0] >= tams[0]);
			}
		}
	}
	return;
}
	/**
	 * Prints a tree representation of this base. Useful for debugging.
	 */
	public void dump () {
		dump(0);
	}
	/**
	 * Prints a tree representation of this base. Useful for debugging.
	 *
	 * @param spaces the identation of the information to be printed.
	 */
	public void dump (int spaces) {
		int i;
		for (i = 0; i < spaces; i++)
			System.out.print(" ");
		System.out.println("Rule base:");
		for (i = 0; i < rules.size(); i++)
			((Rule)rules.elementAt(i)).dump(spaces + 2);
	}
	/**
	 * Fires a single rule in the rule base, if there is such one in the
	 * conflict set.
	 *
	 * @param knowledgeBase the knowledge base that owns this rule base.
	 * @return <code>true</code> if a rule has been fired;
	 *          <code>false</code> otherwise.
	 * @exception RuleNotFiredException if the rule didn't fire despite its
	 *          preconditions being true, due to some error during its
	 *          actions execution.
	 */
	public boolean fireSingleRule(KnowledgeBase knowledgeBase) throws RuleNotFiredException {
		try {
			Rule r = conflictSet.nextRule();
			System.out.println("Firing the rule: " + r.getName());
			r.fire(knowledgeBase);
			return true;
		} catch (NoMoreRulesException e) {
			return false;
		}
	}
/**
 * Tells this base that an object was modified in its
 * object base, so that the rules with instantiations for
 * that object are retested, and new cases can be added
 * to the conflict set.
 *
 * @param obj the object that was modified in this base.
 */
public void modifyObject(Object obj) {
	retractObject(obj);
	joinObject(obj);
}
	/**
	 * Auxiliar method, used to print an internal number. Useful for
	 * debugging purposes only.
	 *
	 * @param number the number (represented as an int array) to be printed.
	 */
	private void printNumber(int[] number) {
		for (int i = 0; i < number.length; i++)
			System.out.print(number[i] + " ");
		System.out.println();
	}
	/**
	 * Removes all rules from this rule base.
	 */
	public void reset() {
		rules.removeAllElements();
		conflictSet.flush();
	}
/**
 * Tells this base that an object was removed from its
 * object base, so that the rules with instantiations for
 * that object are removed from the object base
 *
 * @param obj the object that was removed from this base.
 */
public void retractObject(Object obj) {
	conflictSet.removeRulesWith(obj);
}
	/**
	 * Defines a conflict set policy for this rule base.
	 *
	 * @param ruleSorter the sorter that implements the needed policy.
	 */
	public void setRuleSorter(RuleSorter ruleSorter) {
		conflictSet.setRuleSorter(ruleSorter);
	}
}