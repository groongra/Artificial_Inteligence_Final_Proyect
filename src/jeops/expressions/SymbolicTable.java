package jeops.expressions;

import java.util.Vector;

/**
 * This class models a symbolic table to be used by the rules.
 *
 * @version 0.06  22 May 1999   First version with comments in English.
 * @author Carlos Figueira Filho (<a href="mailto:csff@di.ufpe.br">csff@di.ufpe.br</a>)
 * @history 0.02  17 Dez 1997
 * @history 0.03  01 Abr 1998   Adição de um método para retirar um
 *                             elemento da tabela.
 * @history 0.04  03 Dez 1998   Adição de um método para clonar esta
 *                             tabela.
 * @history 0.05  08 Dez 1998   Adição dos métodos <code>equals</code> e 
 *                             <code>hashcode</code> para poder usar esta
 *                             tabela numa <i>hashtable</i>.
 */
public class SymbolicTable implements Cloneable {

	/**
	 * The triples (class name, identifier, object reference) vector
	 * of this table.
	 */
	private Vector triplas;

	/**
	 * Class constructor.
	 */
	public SymbolicTable() {
		triplas = new Vector();
	}
	/**
	 * Clones this table.
	 *
	 * @return an identical copy of this table.
	 * @since 0.04
	 */
	public Object clone() {
		SymbolicTable result = new SymbolicTable();
		for (int i = 0; i < triplas.size(); i++) {
			Triple t = (Triple) triplas.elementAt(i);
			result.insert(t.className, t.ident, t.object);
		}
		return result;
	}
	/**
	 * Prints this table. Useful for debugging.
	 */
	public void dump() {
		for (int i = 0; i < triplas.size(); i++) {
			Triple t = (Triple) triplas.elementAt(i);
			System.out.println(t.className + " - " + t.ident +
								  " - " + t.object);
		}
	}
	/**
	 * Compares this table with the given object.
	 *
	 * @param obj the object to be compared to this table.
	 * @return <code>true</code> if the given object is a table identical
	 *          to this one; <code>false</code> otherwise.
	 */
	public boolean equals(Object obj) {
		boolean result = false;
		if (obj instanceof SymbolicTable) {
			SymbolicTable t = (SymbolicTable) obj;
			Vector v = t.triplas;
			if (v.size() == triplas.size()) {
				for (int i = 0; i < v.size(); i++) {
					Triple t1 = (Triple) v.elementAt(i);
					Triple t2 = (Triple) triplas.elementAt(i);
					if (!t1.totallyEquals(t2)) {
						return false;
					}
				}
				result = true;
			}
		}
		return result;
	}
	/**
	 * Returns the class of a variable, given its identifier. If the
	 * variable doesn't belong to this table, <code>null</code> will
	 * be returned.
	 *
	 * @param ident the identifier of the variable
	 * @return the class name of the variable
	 */
	public String getClassName(String ident) {
		int pos = indexOf(ident);
		if (pos != -1)
		   return (((Triple)triplas.elementAt(pos)).className);
		else
		   return null;
	}
	/**
	 * Returns the value of a variable, given its identifier.
	 *
	 * @param ident the identifier of the needed variable.
	 * @return the value of a variable, given its identifier.
	 */
	public Object getReference(String ident) {
		int pos = indexOf(ident);
		if (pos != -1) {
			Triple t = (Triple) triplas.elementAt(pos);
			return t.object;
		} else
			return null;
	}
/**
 * Returns the triples (class name, ident, value) of this symbolic
 * table.
 *
 * @return the triples for this table.
 */
public Vector getTriples() {
	return triplas;
}
	/**
	 * Returns a hash code for this table. Used for storing in hashtables.
	 *
	 * @return a hash code for this table.
	 */
	public int hashCode() {
		if (triplas.size() == 0) {
			return 0;
		} else {
			Triple t = (Triple) triplas.elementAt(0);
			return (t.className.hashCode() + t.ident.hashCode() +
					t.object.hashCode());
		}
	}
	/**
	 * Returns the index of a given identifier in the triples vector.
	 *
	 * @param ident the identifier of the needed variable.
	 * @return the index of a given identifier in the triples vector;
	 *          -1 if the identifier is not found in this table.
	 */
	private int indexOf(String ident) {
		for (int i = 0; i < triplas.size(); i++) {
			Triple t = (Triple) triplas.elementAt(i);
			if (t.ident.equals(ident))
				return i;
		}
		return -1;
	}
	/**
	 * Inserts a new pair (class name, identifier) in this table. If the
	 * identifier already exists, its value is replaced by null.
	 *
	 * @param className the class name of the variable.
	 * @param ident the identifier of the variable.
	 */
	public void insert(String className, String ident) {
		int pos = indexOf(ident);
		if (pos == -1) {
			triplas.addElement(new Triple(className, ident, null));
		} else {
			Triple t = (Triple) triplas.elementAt(pos);
			t.className = className;
		}
	}
	/**
	 * Inserts a new triple (class name, identifier, ,object) in this
	 * table.  If the identifier already exists, its value is replaced
	 * by the given one.
	 *
	 * @param className the class name of the variable.
	 * @param ident the identifier of the variable.
	 * @param value the value of the variable
	 */
	private void insert(String className, String ident, Object value) {
		int pos = indexOf(ident);
		if (pos == -1) {
			triplas.addElement(new Triple(className, ident, value));
		} else {
			Triple t = (Triple) triplas.elementAt(pos);
			t.className = className;
			t.object = value;
		}
	}
	/**
	 * Removes a given identifier from this table. If the identifier
	 * doesn't belong to this table, nothing happens.
	 *
	 * @param ident the identifier of the entry being removed.
	 */
	public void remove(String ident) {
		for (int i = 0; i < triplas.size(); i++) {
			Triple t = (Triple) triplas.elementAt(i);
			if (t.ident.equals(ident)) {
				triplas.removeElementAt(i);
				break;
			}
		}
	}
	/**
	 * Defines the value of a variable, given its identifier
	 * and the new value.
	 *
	 * @param ident the identifier of the variable
	 * @param value the value of the variable.
	 */
	public void setReference(String ident, Object value) {
		int pos = indexOf(ident);
		if (pos != -1) {
			Triple t = (Triple) triplas.elementAt(pos);
			t.object = value;
		}
	}
}