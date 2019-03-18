package inferencia.examples;

import jeops.engine.KnowledgeBase;

/**
 * Class used to test the Towers of Hanoi example for JEOPS.
 *
 * @version 0.01  22 May 1999
 * @author Carlos Figueira Filho (<a href="mailto:csff@di.ufpe.br">csff@di.ufpe.br</a>)
 */
public class ProgHanoi {
/**
 * Starts the application.
 *
 * @param args a command-line arguments array. None is needed,
 *          but one can pass the number of discs to be moved.
 */
public static void main(java.lang.String[] args) {

	int noDiscs;
	if (args.length == 0) {
		noDiscs = 6;
	} else {
		noDiscs = Integer.parseInt(args[0]);
	}
	Hanoi h = new Hanoi(noDiscs, 1, 3);
	KnowledgeBase kb = new KnowledgeBase("src/inferencia/examples/hanoi.rules", new jeops.engine.LRURuleSorter());
	kb.join(h);
	kb.run();
	System.out.println();
	System.out.println("Problem solution:");
	h.dump(2);
}
}