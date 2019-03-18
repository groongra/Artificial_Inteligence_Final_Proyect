package jeops.expressions;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * This class os a <i>wrapper</i> for the <code>java.lang.Class</code>
 * class. It's used so we can redefine the
 *    <code>getMethod(String,Class[])</code> method - what can't be
 *    done directly because Class is <b><code>final</code></b>.
 *
 * @version 0.02  29 Jun 1999  First version with comments in English.
 * @author Carlos Figueira Filho (<a href="mailto:csff@di.ufpe.br">csff@di.ufpe.br</a>)
 * @history 0.01  03 Mai 1998   Esta classe é uma <i>wrapper class</i> para
 *    a classe <code>java.lang.Class</code> da API padrão de java. A função
 *    desta classe-envoltória é sobre-escrever o método
 *    <code>getMethod(String,Class[])</code> da classe Class - o que não
 *    pode ser feito diretamente pelo fato da classe Class ser
 *    <b><code>final</code></b>.
 */
public class Classe {

	/**
	 * A classe sendo encapsulada.
	 */
	private Class classe;

	/**
	 * Class constructor.
	 *
	 * @param classe the class being wrapped.
	 */
	public Classe (Class classe) {
		this.classe = classe;
	}
	/**
	 * Searches, among the constructors of the wrapped class, one that
	 * is compatible to the given parameters.
	 *
	 * @param parameters the clases of the parameters of the needed
	 *          constructor.
	 * @exception NoSuchMethodException if there is no constructor in
	 *          the class that is compatible to the given parameters.
	 */
	public Constructor getConstructor(Class[] parameters) 
										 throws NoSuchMethodException {
		NoSuchMethodException exception = null; // a ser levantada se não
									  // houver nenhum método compatível.
		Constructor result = null;
		try {
			result = classe.getConstructor(parameters);
		} catch (NoSuchMethodException e) {
			result = null;
			exception = e;
		}
		if (result != null)
			return result;
		Constructor[] construtores = classe.getConstructors();
		int i = 0;
		while (i < construtores.length && result == null) {
			Class[] consParam = construtores[i].getParameterTypes();
			if (consParam.length != parameters.length) {
				i++;
				continue;
			}
			int j = 0;
			boolean compativel = true;
			while (compativel && j < parameters.length) {
				if (!consParam[j].isAssignableFrom(parameters[j]))
					compativel = false;
				j++;
			}
			if (compativel)
				result = construtores[i];
			i++;
		}
		if (result == null)
			throw exception;
		return result;
	}
	/**
	 * Searches, among the fields of the wrapped class and its
	 * superclasses, one with the given name.
	 *
	 * @param ident the name of the needed field.
	 * @exception NoSuchFieldException if there's no field in the wrapped
	 *          class with the given name.
	 */
	public Field getField(String ident) throws NoSuchFieldException {
		return classe.getField(ident);
	}
	/**
	 * Searches, among the methods of the wrapped class, one that
	 * is compatible to the given name and parameters.
	 *
	 * @param ident the name of the needed method.
	 * @param parameters the clases of the parameters of the needed
	 *          constructor.
	 * @exception NoSuchMethodException if there is no method in the
	 *          class that is compatible to the given name and parameters.
	 */
	public Method getMethod(String ident, Class[] parametros) 
										  throws NoSuchMethodException {
		NoSuchMethodException exception = null; // a ser levantada se não
									  // houver nenhum método compatível.
		Method result = null;
		try {
			result = classe.getMethod(ident, parametros);
		} catch (NoSuchMethodException e) {
			result = null;
			exception = e;
		}
		if (result != null)
			return result;
		Method[] metodos = classe.getMethods();
		int i = 0;
		while (i < metodos.length && result == null) {
			if (!metodos[i].getName().equals(ident)) {
				i++;
				continue;
			}
			Class[] metParam = metodos[i].getParameterTypes();
			if (metParam.length != parametros.length) {
				i++;
				continue;
			}
			int j = 0;
			boolean compativel = true;
			while (compativel && j < parametros.length) {
				if (!metParam[j].isAssignableFrom(parametros[j]))
					compativel = false;
				j++;
			}
			if (compativel)
				result = metodos[i];
			i++;
		}
		if (result == null)
			throw exception;
		return result;
	}
	/**
	 * Returns a String representation of this object. Mirror of the
	 * method with the same name in the wrapped class. Useful for
	 * debugging.
	 *
	 * @return a String representation of this object.
	 */
	public String toString() {
		return classe.toString();
	}
}