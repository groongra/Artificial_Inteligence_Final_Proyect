package busqueda.practica;

/**
 * Clase creada como ejemplo de la parte 2 de la práctica 2018-2019 de IA, UC3M Colmenarejo
 *
 * 
 * @author Daniel Amigo Herrero
 * @author David Sánchez Pedroche
 */

public class OpenList {

	protected Node rootNode = null; // Nodo Raiz de la lista

	// Constructor de la lista
	/**
	 * MODIFICAR para ajustarse a las necesidades del problema
	 * 
	 */
	public OpenList() {
		this.rootNode = new Node();	// Genera el nodo raiz vacio. Despues se modifica
	}
	
	/*** Metodos especificos para el funcionamiento de A estrella */
	
	//Introduce un nodo en funcion de su valor de evaluacion (de menor a mayor). Indispensable para mantener el orden de la lista
	public void insertAtEvaluation(Node newElem) {
		Node newNodo = new Node(newElem);
		Node posNode = null;
		Node nodeIt = rootNode.getNextNode();
		if(nodeIt == null) rootNode.setNextNode(newNodo);
		else if(nodeIt.getEvaluacion()>newNodo.getEvaluacion()) {
			newNodo.setNextNode(nodeIt);
			rootNode.setNextNode(newNodo);
		}
		else {
			while (nodeIt != null) {
				posNode = nodeIt;
				nodeIt= nodeIt.getNextNode();
				if(nodeIt == null) {
					posNode.setNextNode(newNodo);
					break;
				}
				else if(nodeIt.getEvaluacion()>newNodo.getEvaluacion()) {
					newNodo.setNextNode(nodeIt);
					posNode.setNextNode(newNodo);
					break;
				}
			}
			
		}
	}
	
	// Extraer el primer nodo de la lista
	public Node pullFirst() {
		Node nodo = rootNode.getNextNode();		// Se modifica el segundo nodo para que sea el siguiente del nodo raiz (primer nodo de la lista)
		rootNode.setNextNode(nodo.getNextNode());
		return nodo;
	}
	
	
	/*** Metodos de utilidad dentro de una lista **/
	
	// Introduce al comienzo un nuevo nodo
	public void addFirst(Node newElem) {
		Node newNodo = new Node(newElem);
		Node oldNode = this.rootNode.getNextNode();
		rootNode.setNextNode(newNodo);
		newNodo.setNextNode(oldNode);
	}
	
	// Introduce al final un nuevo nodo
	public void addLast(Node newElem) {
		Node newNodo= new Node(newElem);

		Node lastNode = null;
		Node nodeIt = rootNode.getNextNode();
		while (nodeIt != null) {
			lastNode = nodeIt;
			nodeIt = nodeIt.getNextNode();
		}
		if (lastNode == null) {
			rootNode.setNextNode(newNodo);
		} else {
			lastNode.setNextNode(newNodo);
		}
	}	

	// Comprobar si la lista esta vacia
	public boolean isEmpty() {				
		return (rootNode.getNextNode() == null);
	}
	
	
	// Obtener el tamaño de la lista
	public int getSize() {
		int size = 0;
		Node nodeIt = rootNode.getNextNode();
		while (nodeIt != null) {
			size++;
			nodeIt = nodeIt.getNextNode();
		}
		return size;
	}

}