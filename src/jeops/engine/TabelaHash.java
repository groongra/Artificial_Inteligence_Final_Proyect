package jeops.engine;

import java.util.Vector;
import java.util.Enumeration;

/**
 * This class models a hash table used by the object base. It's used to
 * improve the search for the objects of a certain class.
 *
 * @version 0.03  29 Jun 1999   First version with comments in English.
 * @author Carlos Figueira Filho (<a href="mailto:csff@di.ufpe.br">csff@di.ufpe.br</a>)
 * @history 0.01      Esta classe modela uma tabela hash (<i>HashTable</i>).
 *    Ela será usada pela base de objetos, para melhorar a eficiência da
 *    busca pelos objetos de determinada classe.
 * @history 0.02  21 Jan 1998    Nesta versão, também são armazenadas
 *    as informações sobre as superclasses das classes dos objetos
 *    armazenados, de forma a permitir a utilização de herança.
 */
class TabelaHash {

	/**
	 * The superclasses vector.
	 */
	private Vector superclasses;

	/**
	 * The subclasses vector.
	 */
	private Vector subclasses;

	/**
	 * The vector of the keys (class names).
	 */
	private Vector chaves;

	/**
	 * The elements vector. It will be a vector of vectors, because each
	 * key (class name) can be linked to more than one object.
	 */
	private Vector elementos;

	/**
	 * Cria uma nova tabela hash.
	 */
	public TabelaHash() {
		chaves = new Vector();
		elementos = new Vector();
		superclasses = new Vector();
		subclasses = new Vector();
	}
	/**
	 * Retorna um vetor contendo as chaves desta tabela.
	 */
	public Vector chaves() {
		return chaves;
	}
	/**
	 * Testa se um elemento pertence à tabela. Esta operação é mais cara que a
	 *    operação <code>contemChave</code>.
	 *
	 * @return   <B>true</B> se o objeto está na tabela; <B>false</B>
	 *               caso contrário.
	 */
	public boolean contem(Object valor) {
		Vector vec;
		for (Enumeration e = elementos.elements(); e.hasMoreElements(); ) {
			vec = (Vector) e.nextElement();
			if (vec.contains(valor))
				return true;
		}
		return false;
	}
	/**
	 * Testa se o objeto é uma chave nesta tabela hash.
	 *
	 * @return   <B>true</B> se o objeto é uma chave; <B>false</B>
	 *               caso contrário.
	 */
	public boolean contemChave(Object chave) {
		return chaves.contains(chave);
	}
	/**
	 * Retorna um vetor com todos os objetos desta tabela.
	 */
	public Vector elementos() {
		Vector todos;
		Enumeration aux;
		todos = new Vector();
		for (int i = 0; i < elementos.size(); i++) {
			aux = ((Vector)elementos.elementAt(i)).elements();
			while (aux.hasMoreElements()) {
				Object obj = aux.nextElement();
				if (!todos.contains(obj)) {
					todos.addElement(obj);
				}
			}
		}
		return todos;
	}
	/**
	 * Retorna um vetor com os objetos de determinada classe.
	 *
	 * @param nomeClasse  a classe dos objetos retornados.
	 */
	public Vector elementos(String nomeClasse) {
		int i = chaves.indexOf(nomeClasse);
		Vector v;
		if (i == -1) // Nenhum objeto desta classe encontrado...
			v = new Vector();
		else
			v = (Vector) elementos.elementAt(i);
		Vector subs = subclasses(nomeClasse);
		for (i = 0; i < subs.size(); i++) {
			String str = (String) subs.elementAt(i);
			int j = chaves.indexOf(str);
			if (j != -1) {
				Vector aux = (Vector) elementos.elementAt(j);
				for (int k = 0; k < aux.size(); k++)
					v.addElement(aux.elementAt(k));
			}
		}
		return v;
	}
	/**
	 * Insere um novo elemento na tabela.
	 */
	public void insere(String nomeClasse, Object objeto) {
		int i = chaves.indexOf(nomeClasse);
		Vector aux;
		if (i == -1) {
			chaves.addElement(nomeClasse);
			aux = new Vector();
			aux.addElement(objeto);
			elementos.addElement(aux);
		} else {
			aux = (Vector) elementos.elementAt(i);
			aux.addElement(objeto);
		}
		Class classe = null;
		try {
			classe = Class.forName(nomeClasse);
		} catch (Exception e) {}
		while (classe != null) {
			classe = classe.getSuperclass();
			if (classe != null)
				inserePar(classe.getName(), nomeClasse);
		}
	}
	/**
	 * Insere um novo par <superclasse, subclasse>.
	 */
	private void inserePar(String superc, String subc) {
		int i = superclasses.indexOf(superc);
		if (i != -1)
			((Vector)subclasses.elementAt(i)).addElement(subc);
		else {
			superclasses.addElement(superc);
			Vector v = new Vector();
			v.addElement(subc);
			subclasses.addElement(v);
		}
	}
	/**
	 * Limpa a tabela hash, de modo que ela não possua nenhum elemento.
	 */
	public void limpa() {
		chaves.removeAllElements();
		elementos.removeAllElements();
		superclasses.removeAllElements();
		subclasses.removeAllElements();
	}
	/**
	 * Remove um elemento da tabela. Esta chamada é menos eficiente que
	 *    a chamada ao método <code>remove(String, Object)</code>, mas
	 *    também funciona. Se o objeto não pertencia à tabela, nada
	 *    ocorre.
	 */
	public void remove(Object objeto) {
		Vector vec;
		for (Enumeration e = elementos.elements(); e.hasMoreElements(); ) {
			vec = (Vector) e.nextElement();
			if (vec.contains(objeto)) {
				vec.removeElement(objeto);
				break;
			}
		}
	}
	/**
	 * Remove um elemento da tabela, dada sua classe. Se o objeto não
	 *    pertencer à lista de instâncias da classe, nada ocorre.
	 */
	public void remove(String nomeClasse, Object objeto) {
		int i = chaves.indexOf(nomeClasse);
		if (i != -1) {
			Vector vec = (Vector) elementos.elementAt(i);
			vec.removeElement(objeto);
		}
		Vector v = subclasses(nomeClasse);
		for (i = 0; i < v.size(); i++) {
			int j = chaves.indexOf((String)subclasses.elementAt(i));
			if (j != -1)
				((Vector) elementos.elementAt(j)).removeElement(objeto);
		}
	}
	/**
	 * Retorna um vetor com as subclasses de determinada classe. Se a
	 *    classe não tiver nenhuma subclasse representada nesta tabela,
	 *    o método retorna <code>null</code>.
	 */
	private Vector subclasses(String classe) {
		int i = superclasses.indexOf(classe);
		if (i != -1)
			return ((Vector) subclasses.elementAt(i));
		else
			return (new Vector());
	}
}