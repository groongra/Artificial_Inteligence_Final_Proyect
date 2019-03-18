package jeops.engine;

import java.util.Vector;

/**
 * This class models the facts over which the inferenc engine will act.
 * By facts we mean any object that is stored in this base - there's no
 * notion of truth or falseness. A fact simply exists or doesn't.
 *
 * @version 0.03  28 Jun 1999  First version with comments in English.
 * @author Carlos Figueira Filho (<a href="mailto:csff@di.ufpe.br">csff@di.ufpe.br</a>)
 * @history 0.02  26 Ago 1998  Mudan�a de nome de BaseDeConhecimento para
 *                              BaseDeObjetos. A partir desta nova vers�o,
 *                              a Base de Conhecimento � a uni�o da base
 *                              de regras com a base de objetos (ou de
 *                              fatos).
 */
public class ObjectBase {

	/**
	 * The hash table used to store the objects.
	 */
	private TabelaHash objects;

	/**
	 * Class constructor. Creates a new object base, initially empty.
	 */
	public ObjectBase() {
		this.objects = new TabelaHash();
	}
	/**
	 * Inserts a new object into this object base.
	 *
	 * @param obj the object to be inserted.
	 */
	public void join(Object obj) {
		objects.insere(obj.getClass().getName(), obj);
	}
	/**
	 * Removes all objects of this base.
	 */
	public void flush() {
		objects.limpa();
	}
	/**
	 * Returns the objects of the given class.
	 *
	 * @param classe the name of the class whose objects are
	 *          being removed from this base.
	 * @return all objects that are instances of the given
	 *          class.
	 */
	public Vector objects(String classe) {
		return objects.elementos(classe);
	}
	/**
	 * Removes an object from this object base.
	 *
	 * @param obj the object to be removed from this base.
	 */
	public void remove(Object obj) {
		objects.remove(obj.getClass().getName(), obj);
	}
}