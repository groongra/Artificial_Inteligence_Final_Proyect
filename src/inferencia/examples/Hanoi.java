package inferencia.examples;

import java.util.Vector;

/**
 * This class models an encapsulation for a solution for the Towers
 * of Hanoi problem.
 *
 * @version 0.01  15 Mar 1998
 * @author Carlos Figueira Filho (<a href="mailto:csff@di.ufpe.br">csff@di.ufpe.br</a>)
 */
public class Hanoi {

	/**
	 * The number of discs in this problem.
	 */
	private int discs;

	/**
	 * The pin from where the discs come.
	 */
	private int source;

	/**
	 * The pin the disks have to be moved to.
	 */
	private int destination;

	/**
	 * The first subproblem used to solve this problem.
	 */
	private Hanoi sub1;

	/**
	 * The second subproblem used to solve this problem.
	 */
	private Hanoi sub2;

	/**
	 * Flag which indicates whether this problem is solved.
	 */
	private boolean ok;

	/**
	 * The problem solution, made up by several Strings in the
	 * form "<pin1> moved to <pin2>".
	 */
	private Vector solucao = new Vector();

/**
 * Class constructor.
 *
 * @param numDiscs the number of the discs for this instance.
 * @param source the source pin
 * @param destination the destination pin
 */
public Hanoi (int numDiscs, int source, int destination) {
	this.discs = numDiscs;
	this.source = source;
	this.destination = destination;
}
	/**
	 * Adds a movement to the solution.
	 *
	 * @param from the pin from where the disc is moved.
	 * @param to the pin to where the disc is moved.
	 */
	public void addMove(int from, int to) {
		solucao.addElement(from + " moved to " + to);
	}
	/**
	 * Prints the tree for the expression of this precondition. Useful
	 * for debugging.
	 */
	public void dump() {
		dump(0);
	}
	/**
	 * Prints the tree for the expression of this precondition. Useful
	 * for debugging.
	 *
	 * @param spaces the identation for the printed output.
	 */
	public void dump(int spaces) {
		int i, j;
		if (sub1 != null)
			sub1.dump(spaces);
		for (i = 0; i < solucao.size(); i++) {
			for (j = 0; j < spaces; j++)
				System.out.print(" ");
			System.out.println("Disk " + solucao.elementAt(i) + ".");
		}
		if (sub2 != null)
			sub2.dump(spaces);
	}
	/**
	 * Returns the destination pin for this problem.
	 *
	 * @return the destination pin for this problem.
	 */
	public int getDestination() {
		return destination;
	}
	/**
	 * Returns the number of discs of this problem.
	 *
	 * @return the number of discs of this problem.
	 */
	public int getDiscs() {
		return discs;
	}
	/**
	 * Returns the intermediate pin form this problem.
	 *
	 * @return the intermediate pin form this problem.
	 */
	public int getIntermediate() {
		return (6 - source - destination);
	}
	/**
	 * Returns the state of this problem.
	 *
	 * @return <code>true</code> if this problem has already been
	 *          solved; <code>false</code> otherwise.
	 */
	public boolean getOk() {
		return ok;
	}
	/**
	 * Returns the source pin for this problem.
	 *
	 * @return the source pin for this problem.
	 */
	public int getSource() {
		return source;
	}
	/**
	 * Returns the first subproblem for this problem.
	 *
	 * @return the first subproblem for this problem.
	 */
	public Hanoi getSub1() {
		return sub1;
	}
	/**
	 * Returns the second subproblem for this problem.
	 *
	 * @return the second subproblem for this problem.
	 */
	public Hanoi getSub2() {
		return sub2;
	}
	/**
	 * Determines whether this problem has already been solved.
	 *
	 * @param newValue the new value for the state of this problem.
	 */
	public void setOk(boolean newValue) {
		this.ok = newValue;
	}
	/**
	 * Determines the first subproblem for this problem.
	 *
	 * @param sub1 the new value for the first subproblem of this one.
	 */
	public void setSub1(Hanoi sub1) {
		this.sub1 = sub1;
	}
	/**
	 * Determines the second subproblem for this problem.
	 *
	 * @param sub1 the new value for the second subproblem of this one.
	 */
	public void setSub2(Hanoi sub2) {
		this.sub2 = sub2;
	}
}