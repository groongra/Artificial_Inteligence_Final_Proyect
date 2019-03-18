package jeops.engine;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;
import jeops.expressions.ActionJavaExpr;
import jeops.expressions.Classe;
import jeops.expressions.JavaExpr;
import jeops.expressions.FieldAccessException;
import jeops.expressions.MethodEvaluationException;
import jeops.expressions.SymbolicTable;

/**
 * This class models a rule to be evaluated in the inference engine.
 *
 * @version 0.12  First version with comments in English.
 * @author Carlos Figueira Filho (<a href="mailto:csff@di.ufpe.br">csff@di.ufpe.br</a>)
 * @see jeops.engine.RuleBase
 * @history 0.06  09 Jan 1998
 * @history 0.07  01 Mar 1998  A mudan�a mais radical feita nesta projeto
 *               at� o momento. As premissas, que antes eram armazenadas
 *               numa estrutura de vetor, agora passar�o a ser
 *               armazenadas numa matriz bidimensional, de forma que a
 *               premissa que estiver na <b>i</b>-esima linha da matriz
 *               possui como vari�veis apenas as declara��es de n�meros
 *               1 a <b>i</b>. Esta altera��o dar� um grande ganho de
 *               efici�ncia ao mecanismo de escolha da regra a ser
 *               disparada. De modo similar, as declara��es locais
 *               tamb�m apresentar�o esta mesma mudan�a.
 *                 Em termos de m�todos de acesso, a mudan�a maior ser�
 *               feita nos m�todo disparavel() e avaliaDeclaracoesLocais.
 *               As vers�es anteriores dos mesmos continuar�o a existir
 *               por motivos de compatibilidade reversa, mas dever�o
 *               deixar de existir num futuro pr�ximo. Os m�todo que
 *               devem ser usados recebem um valor inteiro como
 *               par�metro, indicando qual linha da matriz de premissas ou
 *               declara��es locais deve ser analisada/avaliada.
 *                 Outros m�todos que ser�o alterados: adicionaPremissa(Premissa)
 *               e adicionaDeclaracoesLocais(DeclaracaoLocal), para que se
 *               decida para qual linha das respectivas matrizes o argumento
 *               ir�.
 * @history 0.08  05 Mar 1998  No ato do disparo da regra, n�o ser� mais
 *               feita a chamada ao m�todo getMethod(String,Class[]), da
 *               classe Class, e sim ao m�todo procuraMetodoReflexo(
 *               Class,String,Class[]) da classe ObjectMethodNode.
 * @history 0.09  30 Mar 1998  A regra agora pode ter declaracoes de
 *               variaveis locais nas acoes, podendo haver tambem
 *               atribuicoes a estas variaveis. Outras classes que tambem
 *               serao alteradas sao a ActionJavaExpr (permitindo atribs)
 *               e o parser.
 * @history 0.10  01 Abr 1998  As declara��es de vari�veis locais do campo
 *               de a��es n�o est� mais num campo separado - e sim no
 *               pr�prio campo de a��es. Por isso, houve uma mudan�a
 *               no m�todo <code>dispara(BaseDeObjetos)</code>.
 *               Com isso, tamb�m foi eliminado o campo declLocaisAcao.
 * @history 0.11  08 Dez 1998  Inclus�o dos m&eacute;todo de clonagem de
 *               regras e de teste de igualdade (equals). Foi inserido
 *               tamb&eacute;m um novo construtor que recebe a prioridade
 *               da regra (menor valor => maior prioridade)
 */
public class Rule implements Cloneable {

	/**
	 * This rule's name.
	 */
	private String name;

	/**
	 * The set of declarations for this rule.
	 */
	private Vector declarations;

	/**
	 * The local declarations array of this rule.
	 *
	 * @invariant (localDeclarations == declarations == null) ||
	 *          (localDeclarations.size() == declarations.size())
	 */
	private Vector localDeclarations;

	/**
	 * The preconditions array of this rule.
	 *
	 * @invariant (preconditions == declarations == null) ||
	 *          (preconditions.size() == declarations.size())
	 */
	private Vector preconditions;

	/**
	 * The set of arrays of this rule, to be executed when it is chosen
	 * by the conflict set.
	 */
	private Vector actions;

	/**
	 * The symbolic table used in this rule.
	 */
	private SymbolicTable symbolicTable;

	/**
	 * Table ised to speed up the determination of which line in the
	 * array a declaration or a local declaration must stay.
	 *
	 * @since 0.07
	 */
	private Hashtable hashLinhas;

	/**
	 * The priority of this rule. Lower values (starting from 0) indicate
	 * higher priorities.
	 *
	 * @since 0.11
	 */
	private int priority;

	/**
	 * Creates a new empty rule, given its name.
	 */
	private Rule(String nome) {
		this(nome, 0);
	}
	/**
	 * Creates an empty rule, given its name and priority.
	 *
	 * @param name this rule's name
	 * @param priority this rule's priority.
	 */
	public Rule(String name, int priority) {
		this.name = name;
		this.priority = priority;
		this.declarations = new Vector();
		this.localDeclarations = new Vector();
		this.preconditions = new Vector();
		this.actions = new Vector();
		this.symbolicTable = new SymbolicTable();
		this.hashLinhas = new Hashtable();
	}
	/**
	 * Adds a new action to this rule.
	 *
	 * @param action the new action.
	 */
	public void addAction(RuleAction action) {
		actions.addElement(action);
	}
	/**
	 * Adds a new declaration to this rule.
	 *
	 * @param decl the new declaration.
	 */
	public void addDeclaration(RuleDeclaration decl) {
		declarations.addElement(decl);
		localDeclarations.addElement(new Vector()); // 0.07 em 01.mar.98
		preconditions.addElement(new Vector());         // 0.07 em 01.mar.98
		symbolicTable.insert(decl.getClassName(), decl.getIdent());
		hashLinhas.put(decl.getIdent(), new Integer(declarations.size() - 1));
	}
	/**
	 * Adds a new local declaration to this rule.
	 *
	 * @param decl the new local declaration.
	 */
	public void addLocalDeclaration(RuleLocalDeclaration decl) {
		symbolicTable.insert(decl.getClassName(), decl.getIdent());
		int qualLinha = linhaDaMatriz(decl.getExpression());
		((Vector)localDeclarations.elementAt(qualLinha)).addElement(decl);
		hashLinhas.put(decl.getIdent(), new Integer(qualLinha));
	}
	/**
	 * Adds a new precondition to this rule.
	 *
	 * @param prec the new precondition.
	 */
	public void addPrecondition(Precondition prec) {
		int qualLinha = linhaDaMatriz(prec.getExpression());
		((Vector)preconditions.elementAt(qualLinha)).addElement(prec);
	}
	/**
	 * Clones this rule.
	 *
	 * @return a clone of this rule.
	 */
	public Object clone() {
		Rule r = new Rule(this.name);
		r.actions = this.actions;
		r.declarations = this.declarations;
		r.localDeclarations = this.localDeclarations;
		r.hashLinhas = this.hashLinhas;
		r.preconditions = this.preconditions;
		r.symbolicTable = (SymbolicTable) this.symbolicTable.clone();
		r.priority = priority;
		return r;
	}
	/**
	 * Prints the tree for the expression of this precondition. Useful
	 * for debugging.
	 */
	public void dump () {
		dump(0);
	}
	/**
	 * Prints the tree for the expression of this precondition. Useful
	 * for debugging.
	 *
	 * @param spaces the identation for the printed output.
	 */
	public void dump (int spaces) {
		int i, j;
		Vector v;
		for (i = 0; i < spaces; i++)
			System.out.print(" ");
		System.out.println("Rule " + name);
		for (i = 0; i < spaces; i++)
			System.out.print(" ");
		System.out.println("  Declarations");
		for (i = 0; i < declarations.size(); i++)
			((RuleDeclaration) declarations.elementAt(i)).dump(spaces+4);
		for (i = 0; i < spaces; i++)
			System.out.print(" ");
		System.out.println("  Local Declarations");
		for (i = 0; i < localDeclarations.size(); i++) {
			v = (Vector) localDeclarations.elementAt(i);
			for (j = 0; j < v.size(); j++) {
//				System.out.print (i + " - ");
				((RuleLocalDeclaration) v.elementAt(j)).dump(spaces+4);
			}
		}
		for (i = 0; i < spaces; i++)
			System.out.print(" ");
		System.out.println("  Preconditions");
		for (i = 0; i < preconditions.size(); i++) {
			v = (Vector) preconditions.elementAt(i);
			for (j = 0; j < v.size(); j++) {
//				System.out.print (i + " - ");
				((Precondition) v.elementAt(j)).dump(spaces+4);
			}
		}
		for (i = 0; i < spaces; i++)
			System.out.print(" ");
		System.out.println("  Actions");
		for (i = 0; i < actions.size(); i++)
			((RuleAction) actions.elementAt(i)).dump(spaces+4);
	}
	/**
	 * Compares this rule with the given object.
	 *
	 * @param obj the object being compared to this rule.
	 * @return <code>true</code> if this rule is equal to the given
	 *          object; <code>false</code> otherwise.
	 */
	public boolean equals(Object obj) {
		if (obj instanceof Rule) {
			Rule aux = (Rule) obj;
			return (aux.name.equals(this.name) &&
					aux.symbolicTable.equals(this.symbolicTable));
		} else {
			return false;
		}
	}
	/**
	 * Evaluates the local declarations of this tule. Prior to invoking
	 * this method, the symbolic table must be correctly filled.
	 *
	 * @exception MethodEvaluationException se is there was an error while
	 *          evaluating.
	 * @exception FieldAccessException se is there was an error while
	 *          evaluating.
	 * @deprecated Replaced by evaluateLocalDeclarations(int).
	 * @see #evaluateLocalDeclarations(int).
	 */
	public void evaluateLocalDeclarations()
				  throws MethodEvaluationException, FieldAccessException {
		for (int i = 0; i < localDeclarations.size(); i++)
			evaluateLocalDeclarations(i);
	}
	/**
	 * Evaluates the local declarations of this tule. Prior to invoking
	 * this method, the symbolic table must be correctly filled until
	 * the given line.
	 *
	 * @param line the given line.
	 * @exception MethodEvaluationException se is there was an error while
	 *          evaluating.
	 * @exception FieldAccessException se is there was an error while
	 *          evaluating.
	 */
	public void evaluateLocalDeclarations(int line)
		  throws MethodEvaluationException, FieldAccessException {
		Vector v = (Vector) localDeclarations.elementAt(line);
		for (int i = 0; i < v.size(); i++) {
			RuleLocalDeclaration decl = (RuleLocalDeclaration) v.elementAt(i);
			Object obj = decl.getExpression().evaluateObj(getTable());
			symbolicTable.setReference(decl.getIdent(), obj);
		}
	}
	/**
	 * Fires this rule. Prior to invoking this method, the symbolic table
	 * must be correctly filled.
	 *
	 * @param base  The knowledge base over which this rule will act.
	 * @exception RuleNotFiredException if the rule couldn't be fired
	 */
	public void fire(KnowledgeBase base)
					  throws RuleNotFiredException {
		int j;
		Object obj;
		Class[] classes;
		JavaExpr[] exprs;

		Vector declLocaisAcao = new Vector();
		for (j = 0; j < actions.size(); j++) {
			ActionJavaExpr a = ((RuleAction)actions.elementAt(j)).getExpression();
			if (a.getType() == ActionJavaExpr.DECLAR) {
				RuleDeclaration decLoc = new RuleDeclaration(
											  a.getClassName(), a.getIdent());
				declLocaisAcao.addElement(decLoc);
				symbolicTable.insert(decLoc.getClassName(), decLoc.getIdent());
			}
		}
		for (int i = 0; i < actions.size(); i++) {
			ActionJavaExpr acao = ((RuleAction)actions.elementAt(i)).getExpression();
			switch (acao.getType()) {
				case ActionJavaExpr.CREATE :
					try {
						JavaExpr expressao = acao.getExpression();
						obj = expressao.evaluateObj(getTable());
					} catch (MethodEvaluationException e1) {
						throw new RuleNotFiredException("Problems in method invocation.");
					} catch (FieldAccessException e1) {
						throw new RuleNotFiredException("Problems in method invocation.");
					}
					base.join(obj);
					break;
				case ActionJavaExpr.DELETE :
					try {
						JavaExpr expressao = acao.getExpression();
						obj = expressao.evaluateObj(getTable());
					} catch (MethodEvaluationException e1) {
						throw new RuleNotFiredException("Problems in method invocation.");
					} catch (FieldAccessException e1) {
						throw new RuleNotFiredException("Problems in method invocation.");
					}
					base.retract(obj);
					break;
				case ActionJavaExpr.MODIFY :
					try {
						JavaExpr expressao = acao.getExpression();
						obj = expressao.evaluateObj(getTable());
					} catch (MethodEvaluationException e1) {
						throw new RuleNotFiredException("Problems in method invocation.");
					} catch (FieldAccessException e1) {
						throw new RuleNotFiredException("Problems in method invocation.");
					}
					base.getRuleBase().modifyObject(obj);
					break;
				case ActionJavaExpr.METHOD :
					try {
						JavaExpr expressao = acao.getExpression();
						obj = expressao.evaluateObj(getTable());
					} catch (MethodEvaluationException e1) {
						throw new RuleNotFiredException("Problems in method invocation.");
					} catch (FieldAccessException e1) {
						throw new RuleNotFiredException("Problems in method invocation.");
					}
					break;
				case ActionJavaExpr.ASSIGN :
				case ActionJavaExpr.DECLAR :
					String esq = acao.getIdent();
					try {
						JavaExpr expr = acao.getExpression();
						Object dir;
						if (expr == null)
							dir = null;
						else
							dir = expr.evaluateObj(getTable());
						symbolicTable.setReference(esq, dir);
					} catch (MethodEvaluationException e) {
						System.out.println("Exception: " + e);
						e.printStackTrace();
					} catch (FieldAccessException e) {
						System.out.println("Exception: " + e);
						e.printStackTrace();
					}
					break;
			}
		}
		for (j = 0; j < declLocaisAcao.size(); j++)
			symbolicTable.remove(
					  ((RuleDeclaration)declLocaisAcao.elementAt(j)).getIdent());

	}
	/**
	 * Returns the declarations of this rule.
	 *
	 * @return the declarations of this rule.
	 */
	public Vector getDeclarations() {
		return declarations;
	}
	/**
	 * Returns the name of this rule.
	 *
	 * @return the name of this rule.
	 */
	public String getName() {
		return name;
	}
	/**
	 * Returns the priority of this rule.
	 *
	 * @return the priority of this rule.
	 */
	public int getPriority() {
		return priority;
	}
	/**
	 * Returns the symbolic table to be used in this rule.
	 *
	 * @return the symbolic table to be used in this rule.
	 */
	public SymbolicTable getTable() {
		return symbolicTable;
	}
	/**
	 * Returns a code used to store this rule in a hashtable.
	 *
	 * @return a <i>hashcode</i> for this rule.
	 */
	public int hashCode() {
		return name.hashCode();
	}
	/**
	 * Checks whether the preconditions of this rule are (all) true. When
	 * this method is called, the symbolic table must be correctly
	 *  filled.
	 *
	 * @return <CODE>true</CODE> if the rule can be fired;
	 *              <CODE>false</CODE> otherwise.
	 * @see #isFireable(int)
	 */
	public boolean isFireable() {
		boolean result = true;
		for (int i = 0; i < preconditions.size(); i++)
			if (!isFireable(i))
				result = false;
		return result;
	}
	/**
	 * Checks whether the preconditions in a given line of this rule
	 * are (all) true. When this method is called, the symbolic table
	 * must be correctly filled for the declarations of lines prior
	 * to the given one.
	 *
	 * @return <CODE>true</CODE> if the premisses in that line
	 *          are true'; <CODE>false</CODE> otherwise.
	 * @see #isFireable
	 */
	public boolean isFireable(int linha) {
		boolean result = true;
		try {
			evaluateLocalDeclarations(linha);
		} catch (MethodEvaluationException e) {
			return false;
		} catch (FieldAccessException e) {
			return false;
		}

		Vector v = (Vector) preconditions.elementAt(linha);
		for (Enumeration e = v.elements(); e.hasMoreElements(); ) {
			try {
				Precondition prem = (Precondition) e.nextElement();
				JavaExpr expr = prem.getExpression();
				boolean aux = expr.evaluateBool(getTable());
				if (!aux) {
					result = false;
				}
			} catch (MethodEvaluationException e1) {
				return false;
			} catch (FieldAccessException e1) {
				return false;
			}
		}
		return result;
	}
	/**
	 * Determines in which line of the declarations or preconditions
	 * matrix a given java expression belongs.
	 *
	 * @param expr the given expression.
	 * @return the line which the given expression belongs.
	 * @since 0.07
	 */
	private int linhaDaMatriz(JavaExpr expr) {
		Enumeration e = expr.identObjetos();
		int linha = 0;
		while (e.hasMoreElements()) {
			Integer pos = (Integer) hashLinhas.get(e.nextElement());
			if (pos != null) {
				int lin = pos.intValue();
				if (lin > linha)
					linha = lin;
			}
		}
		return linha;
	}
}