package inferencia.examples;

/**
 * This class models an encapsulation for a solution for the Fibonacci
 * series.
 *
 * @version 0.01  15 Mar 1998
 * @author Carlos Figueira Filho (<a href="mailto:csff@di.ufpe.br">csff@di.ufpe.br</a>)
 */
public class Fibonacci {

	/**
	 * The order of this element of the series.
	 */
	public int n;

	/**
	 * The value of the element in the series.
	 */
	public int value;

	/**
	 * The first subproblem used to solve this recursion.
	 */
	public Fibonacci son1;

	/**
	 * The second subproblem used to solve this recursion.
	 */
	public Fibonacci son2;
/**
 * Class constructor.
 *
 * @param n the order of the series.
 */
public Fibonacci(int n) {
	this.n = n;
	this.value = -1;
}
/**
 * Returns the order of this element of the series.
 *
 * @return the order of this element of the series.
 */
public int getN() {
	return n;
}
/**
 * Returns the first subproblem used to solve this recursion.
 *
 * @return the first subproblem used to solve this recursion
 */
public Fibonacci getSon1() {
	return son1;
}
/**
 * Returns the second subproblem used to solve this recursion.
 *
 * @return the second subproblem used to solve this recursion
 */
public Fibonacci getSon2() {
	return son2;
}
/**
 * Returns the value of this element of the series.
 *
 * @return the value of this element of the series, or -1 if
 *          the value hasb't been calculated yet.
 */
public int getValue() {
	return value;
}
/**
 * Defines the first subproblem used to solve this recursion.
 *
 * @param newValue the first subproblem used to solve this recursion.
 */
public void setSon1(Fibonacci newValue) {
	this.son1 = newValue;
}
/**
 * Defines the second subproblem used to solve this recursion.
 *
 * @param newValue the second subproblem used to solve this recursion.
 */
public void setSon2(Fibonacci newValue) {
	this.son2 = newValue;
}
/**
 * Defines the value of this element in the series.
 *
 * @param newValue the value of this element in the series.
 */
public void setValue(int newValue) {
	this.value = newValue;
}
}