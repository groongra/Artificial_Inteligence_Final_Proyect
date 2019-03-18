package jeops.expressions;

import java.util.Vector;
import java.util.Enumeration;

/**
 * This class models a set of strings to be used in the evaluation of the
 * rules of the inference engine.
 *
 * @version 0.02  29 Jun 1999   First version with comments in English
 *    para ser usado na avalia��o das regras do motor de infer�ncia.
 * @author Carlos Figueira Filho (<a href="mailto:csff@di.ufpe.br">csff@di.ufpe.br</a>)
 * @history 0.01  01 Mar 1998   Esta classe modela um conjunto de Strings
 *    para ser usado na avalia��o das regras do motor de infer�ncia.
 */
class StringSet implements Enumeration {

	/**
	 * The set of strings.
	 */
	private Vector strings;

	/**
	 * The enumeration of the elements in this set.
	 */
	private Enumeration enume;

	/**
	 * Class constructor. Creates a new StringSet, initially empty.
	 */
	public StringSet() {
		strings = new Vector();
		reset();
	}
	/**
	 * Class constructor for a unitary set. Creates a new StringSet
	 * with a single element.
	 *
	 * @param str the element of the newly created set.
	 */
	public StringSet(String str) {
		strings = new Vector();
		strings.addElement(str);
		reset();
	}
	/**
	 * Creates a new StringSet, as the union of two other StringSets.
	 *
	 * @param s1 the first set
	 * @param s2 the second set
	 */
	public StringSet(StringSet s1, StringSet s2) {
		strings = new Vector();
		int i;
		for ( ; s1.hasMoreElements(); ) {
			Object obj = s1.nextElement();
			if (!s2.strings.contains(obj))
				strings.addElement(obj);
		}
		for ( ; s2.hasMoreElements(); )
			strings.addElement(s2.nextElement());
		s1.reset();    // Retorna as enumera��es �s suas posi��es
		s2.reset();    //  iniciais, para que possam ser compartilhadas.
		reset();
	}
	/**
	 * Checks whether this enumeration has more elements.
	 *
	 * @return <code>true</code> if this enumeration contains more
	 *          elements; <code>false</code> otherwise.
	 */
	public boolean hasMoreElements() {
		return enume.hasMoreElements();
	}
	/**
	 * Returns the next element of this enumeration.
	 *
	 * @return the next element of this enumeration
	 */
	public Object nextElement() {
		return enume.nextElement();
	}
	/**
	 * Returns this enumeration to its beginning.
	 */
	public void reset() {
		enume = strings.elements();
	}
/**
 * Returns the size of this set.
 *
 * @return the size of this set.
 */
public int size() {
	return strings.size();
}
	/**
	 * Returns a String representation of this object. Useful for
	 * debugging.
	 *
	 * @return a String representation of this object.
	 */
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("[");
		for (int i = 0; i < strings.size(); i++) {
			sb.append(strings.elementAt(i));
			if (i != strings.size() - 1)
				sb.append(", ");
		}
		sb.append("]");
		return sb.toString();
	}
}