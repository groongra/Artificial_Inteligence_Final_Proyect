package jeops.parser;

import java.util.Vector;
import java.util.Enumeration;

/**
 * This class models a set of String pairs, used to store the list
 * of declarations for a rule.
 *
 * @version 0.03  28 Jun 1999    First version with comments in English.
 * @author Carlos Figueira Filho (<a href="mailto:csff@di.ufpe.br">csff@di.ufpe.br</a>)
 * @history 0.01  20 Mai 1998  Esta classe modela um conjunto de pares
 *   de Strings, usada para armazenar a lista das declarações de uma regra.
 * @history 0.02  03 Mar 1999  Métodos para retornar um tipo/identificador dado
 *								seu índice.
 */
public class Declaracoes {

	/**
	 * The list of the variable types
	 */
	private Vector types;

	/*
	 * The list of the variable identifiers
	 *
	 * @invariant tipos.size() == idents.size()
	 */
	private Vector idents;

	/**
	 * Class constructor. Creates an empty instance of this class.
	 */
	public Declaracoes() {
		idents = new Vector();
		types = new Vector();
	}
	/**
	 * Returns an enumeration with the types of these declarations.
	 *
	 * @return an enumeration with the types of these declarations.
	 */
	public Enumeration elements() {
		return types.elements();
	}
	/**
	 * Recovers the type of a previously declared identifier.
	 *
	 * @param ident the identifier of the needed identifier
	 * @return the class name of this identifier.
	 */
	public String get(String ident) {
		int pos = idents.indexOf(ident);
		if (pos != -1)
			return ((String) types.elementAt(pos));
		return null;
	}
	/**
	 * Returns the identifier for the i-th declaration of this set.
	 *
	 * @param i the index of the needed element.
	 * @return the identifier for the i-th declaration of this set.
	 * @since 0.02
	 */
	public String getIdent(int i) {
		return (String) idents.elementAt(i);
	}
	/**
	 * Returns the number of declarations in this set.
	 *
	 * @return the number of declarations in this set.
	 */
	public int getNumDeclarations() {
		return idents.size();
	}
	/**
	 * Returns the type for the i-th declaration of this set.
	 *
	 * @param i the index of the needed element.
	 * @return the type for the i-th declaration of this set.
	 * @since 0.02
	 */
	public String getType(int i) {
		return (String) types.elementAt(i);
	}
	/**
	 *  Retorna uma enumeração com os identificadores das declarações.
	 */
	public Enumeration keys() {
		return idents.elements();
	}
	/**
	 * Adds a new declaration to this set.
	 *
	 * @param ident the identifier of the declaration
	 * @param type the type of the declaration
	 */
	public void put(String ident, String type) {
		int pos = idents.indexOf(ident);
		if (pos == -1) {
			idents.addElement(ident);
			types.addElement(type);
		} else
			types.setElementAt(type, pos);
	}
}