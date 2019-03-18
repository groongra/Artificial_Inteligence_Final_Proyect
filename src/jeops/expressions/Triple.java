package jeops.expressions;

/**
 * Auxiliar class that stores triples with (class name, variable
 * identifier and references to the actual object), to be used
 * in the symbolic table.
 *
 * @version 0.03  22 May 1999    First version with comments in English.
 * @author Carlos Figueira Filho (<a href="mailto:csff@di.ufpe.br">csff@di.ufpe.br</a>)
 * @history 0.02  17 Dez 1997
 */
public class Triple {

	/**
	 * The class name.
	 */
	protected String className;

	/**
	 * The variable identifier.
	 */
	protected String ident;

	/** 
	 * The reference to the object.
	 */
	protected Object object;

	/**
	 * Class constructor.
	 *
	 * @param className the class name.
	 * @param ident the variable identifier.
	 * @param reference a reference to the object.
	 */
	public Triple(String className, String ident, Object reference) {
		this.className = className;
		this.ident = ident;
		this.object = reference;
	}
	/**
	 * Compares this triple with the given object.
	 * We call two triples equal if and only if their ident field
	 * are equal.
	 *
	 * @param obj the object to be compared to this triple
	 * @return <code>true</code> if this triple is equal to the
	 *          given object; <code>false</code> otherwise.
	 */
	public boolean equals(Object obj) {
		if (obj != null && obj instanceof Triple) {
			Triple aux = (Triple) obj;
			return (aux.ident.equals(this.ident));
		} else if (obj != null && obj instanceof String) {
			return (ident.equals(this.ident));
		} else
			return false;
	}
/**
 * Returns the object reference stored in this triple.
 *
 * @return the object reference stored in this triple.
 */
public Object getObject() {
	return object;
}
	/**
	 * Compares all fields of this triple with the given object.
	 * We call two triples totally equal if and only if all their
	 * field are equal.
	 *
	 * @param obj the object to be compared to this triple
	 * @return <code>true</code> if this triple is totally equal
	 *          to the given object; <code>false</code> otherwise.
	 */
	public boolean totallyEquals(Triple t) {
		return (this.className.equals(t.className)  &&
				this.ident.equals(t.ident) &&
				this.object == t.object);
	}
}