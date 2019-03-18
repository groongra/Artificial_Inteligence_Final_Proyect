package inferencia.examples;

import jeops.engine.KnowledgeBase;
import jeops.engine.RuleNotFiredException;

/**
 * Class used to test the Fibonacci example for JEOPS.
 *
 * @version 0.01  22 May 1999
 * @author Carlos Figueira Filho (<a href="mailto:csff@di.ufpe.br">csff@di.ufpe.br</a>)
 */
public class ProgFibo {
/**
 * Starts the application.
 *
 * @param args a command-line arguments array. None is needed.
 */
public static void main(java.lang.String[] args) {
	// Insira aqui o c�digo para iniciar a aplica��o.

	Fibonacci[] f = new Fibonacci[4];
	for (int i = 0; i < f.length; i++) {
		f[i] = new Fibonacci(i+3);
	}
	KnowledgeBase kb = new KnowledgeBase("src/inferencia/examples/fibo.rules", new jeops.engine.PriorityRuleSorter());
	for (int i = 0; i < f.length; i++) {
		kb.join(f[i]);
	}
	kb.run();
	for (int i = 0; i < f.length; i++) {
		System.out.println("fibo("+f[i].getN()+") = " + f[i].getValue());
	}
}
}