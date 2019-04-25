package busqueda.examples.ejercicio4;

/**
 * Clase creada como ejemplo de la parte 2 de la práctica 2018-2019 de IA, UC3M Colmenarejo
 *
 * 
 * @author Daniel Amigo Herrero
 * @author David Sánchez Pedroche
 */

import java.util.ArrayList;
import java.util.List;

public class AStar {
	private OpenList openList = new OpenList();						// Lista de nodos por explorar
	private ArrayList<Node> closedList = new ArrayList<Node>();		// Lista de nodos explorados
	private Node initialNode;										// Nodo inicial del problema
	private Node goalNode;											// Nodo meta del problema
	private boolean findGoal;
	
	public static void main(String[] args) {
		// Crear el nodo inicial y el nodo meta.
		/**
		 * MODIFICAR el constructor en funcion del problema
		 */
		Node initialNode = new Node(null, true, false, null);
		Node goalNode = new Node(null, false, true, null);
		
		// Introducir heurísticas y costes para el nodo inicial. El nodo meta solo tiene heurística
		initialNode.calculaHeuristica(goalNode);
		initialNode.setCoste(0);
		initialNode.calculaEvaluacion();
		goalNode.calculaHeuristica(goalNode);
		
		// Generamos un AStar que funcione con nuestros nodos inicial y meta
		AStar aStar = new AStar(initialNode, goalNode);		
		// Comienzo del algoritmo
		aStar.Algorithm();
		
		//Extracción de información
		/**
		 * MODIFICAR para imprimir las comprobaciones que se consideren para explicar los resultados
		 */
		if(aStar.findGoal)System.out.println("Camino encontrado");
		else System.out.println("Camino no encontrado");
		List<Node> path = aStar.getPath(aStar.goalNode);	// Genera el camino para llegar a la meta desde el nodo inicial
		for (Node node:path) {
			node.printNodeData();
		}
	}
	
	// Constructor del algoritmo, obtiene el nodo de inicio y el nodo meta
	public AStar(Node initialNode, Node goal) { 
		this.setInitialNode(initialNode);
		this.setgoal(goal);
		this.findGoal=false;
		// Genera la lista de nodos explorados y sin explorar
		this.closedList = new ArrayList<Node>();
		this.openList = new OpenList();
		this.openList.insertAtEvaluation(initialNode); // Añadimos a la lista de nodos sin explorar el nodo inicial
	}
	

	// Implementación de A estrella
	private void Algorithm() {
		Node currentNode=null;
		
		while(!this.openList.isEmpty()) { 				// Recorremos la lista de nodos sin explorar
			currentNode = this.openList.pullFirst(); 	// Extraemos el primero (la lista esta ordenada según la funcion de evaluación)
//			System.out.println("saco");
//			currentNode.printNodeData();
			if(checkNode(currentNode)) {				// Si el nodo ya se ha visitado con un coste menor (esta en la lista de explorados) lo ignoramos
				closedList.add(currentNode); 			// Añadimos dicho nodo a la lista de explorados
				
				if(this.goalNode.equals(currentNode)) {	// Si es el nodo meta hemos acabado y no hace falta continuar
					this.goalNode=currentNode;
					this.findGoal=true;
					break;
				}
				this.addAdjacentNodes(currentNode); 	// Expandimos el nodo segun las acciones posibles    	
			}
		}
	}
	
	
	//Comprobación de si el nodo ya se ha explorado
	private boolean checkNode(Node currentNode) {
		boolean expandirNodo=true;
		for (Node node : this.closedList) {
			if(currentNode.equals(node)) {			// Comprueba si la información del nodo es igual
				expandirNodo=false;
				break;
			}
		}
		return expandirNodo;						// false en el caso de que el nodo se haya visitado, indicando que no hay que expandirlo
	}
	
	
	// Insertamos los nodos segun las acciones a realizar 
	/**
	 * MODIFICAR para insertar las acciones especificas del problema
	 */
	private void addAdjacentNodes(Node currentNode) {
		// Movimiento hacia Arriba
		for (int i = 0; i < currentNode.getLuces()[0].length; i++) {
			for (int j = 0; j < currentNode.getLuces()[0].length; j++) {
				int a[]={i,j};
				Node newNode = new Node(currentNode,false,false,a);
				newNode.calculaHeuristica(this.goalNode);
				newNode.calculaHeuristica(this.goalNode);								// genera su heurística
				newNode.calculaCoste(currentNode.getCoste()+this.getCosteAccion());		// calcula el coste de la acción y se lo suma al coste del padre
				newNode.calculaEvaluacion();											// genera la evaluación
				this.openList.insertAtEvaluation(newNode);
//				System.out.println("meto");
//				newNode.printNodeData();
			}
		}
		
	}
	
	
	// Devuelve el costes de la accion que realice
	/**
	 * MODIFICAR el contenido y los parametros segun las necesidades del problema
	 */
	private int getCosteAccion() {
		
		return 1;
	}

	
	// Método para calcular el camino desde el nodo Inicial hasta el nodo actual
	private List<Node> getPath(Node currentNode) {
		List<Node> path = new ArrayList<Node>();	
		path.add(currentNode);	
		Node parent;
		while ((parent = currentNode.getParent()) != null) {	// Desde el nodo actual, se busca el nodo padre y se insertan 
			path.add(0, parent);								//  dentro de la lista de manera inversa
			currentNode = parent;
		}
		return path;
	}
	
	
	/**** Getters y Setters ****/
	/**
	 * MODIFICAR si se considera necesario. No es imprescindible, solo si consideras que puede ayudar a tu implementación
	 */
	public Node getInitialNode() {
		return initialNode;
	}
	public void setInitialNode(Node initialNode) {
		this.initialNode = initialNode;
	}
	public Node getgoal() {
		return goalNode;
	}
	public void setgoal(Node goal) {
		this.goalNode = goal;
	}

}
