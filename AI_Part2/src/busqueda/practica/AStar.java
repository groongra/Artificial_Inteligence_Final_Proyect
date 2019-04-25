          package busqueda.practica;

/**
 * Clase creada como ejemplo de la parte 2 de la práctica 2018-2019 de IA, UC3M Colmenarejo
 *
 * 
 * @author Daniel Amigo Herrero
 * @author David Sánchez Pedroche
 */

import java.util.ArrayList;
import java.util.List;

import busqueda.examples.ejercicio4.*;;

public class AStar {
	private OpenList openList = new OpenList();						// Lista de nodos por explorar
	private ArrayList<Node> closedList = new ArrayList<Node>();		// Lista de nodos explorados
	private Node initialNode;										// Nodo inicial del problema
	private Node goalNode;											// Nodo meta del problema
	private boolean findGoal;
	private int[][] movementCosts =  { 
			{-1,10,10,-1,-1,-1,-1,-1,-1,5},
			{10,-1,5,20,20,20,15,15,15,10},
			{10,5,-1,20,20,20,15,15,15,10},
			{-1,20,20,-1,10,5,10,10,10,10},
			{-1,20,20,10,-1,10,10,10,10,10},
			{-1,20,20,5,10,-1,10,10,10,10},
			{-1,15,15,10,10,10,-1,5,5,30},
			{-1,15,15,10,10,10,5,-1,10,30},
			{-1,15,15,10,10,10,5,10,-1,30},
			{5,10,10,10,10,10,30,30,30,-1}
		 };
	
	/*	costs
	 * Where you are | where you can go = cost
	 *
	 * 			|	INF 	| 	ADE	 	|	 TUR	|___
	 *			|ART|VOL|COL|ART|VOL|COL|ART|VOL|COL|PIC|
	 * |INF-ART	|-1	|10	|10	|-1	|-1	|-1	|-1	|-1	|-1	|5	|
	 * |INF-VOL	|10	|-1	|5	|20	|20	|20	|15	|15	|15	|10	|
	 * |INF-COL	|10	|5	|-1	|20	|20	|20	|15	|15	|15	|10	|
	 * |ADE-ART	|-1	|20	|20	|-1	|10	|5	|10	|10	|10	|10	|
	 * |ADE-VOL	|-1	|20	|20	|10	|-1	|10	|10	|10	|10	|10	|
	 * |ADE-COL	|-1	|20	|20	|5	|10	|-1	|10	|10	|10	|10	|
	 * |TUR-ART	|-1	|15	|15	|10	|10	|10	|-1	|5	|5	|30	|
	 * |TUR-VOL	|-1	|15	|15	|10	|10	|10	|5	|-1	|10	|30	|
	 * |TUR-COL	|-1	|15	|15	|10	|10	|10	|5	|10	|-1	|30	|
	 * |  PIC	|5	|10	|10	|10	|10	|10	|30	|30	|30	|-1	|
	 
	 */
	
	public static void main(String[] args) {
		// Crear el nodo inicial y el nodo meta.
		/**
		 * MODIFICAR el constructor en funcion del problema
		 */
		//final int X = 11;	//Selects Bookshelf_area + PIC + ROB (9 + 1 + 1)
		//final int Y = 9; //Selects book shelf type
		
		 /* Estado final biblioteca:
		 *	    	 ___________________________________
		 * 	goal	|	INF 	| 	ADE	 	|	 TUR	|_______
		 * 			|ART|VOL|COL|ART|VOL|COL|ART|VOL|COL|PIC|ROB|
		 * |INF-ART	|19	|0	|0	|0	|0	|0	|0	|0	|0	|0	|0 	|
		 * |INF-COL	|0	|5	|0	|0	|0	|0	|0	|0	|0	|0	|0 	|
		 * |INF-VOL	|0	|0	|8	|0	|0	|0	|0	|0	|0	|0	|0 	|
		 * |ADE-ART	|0	|0	|0	|8	|0	|0	|0	|0	|0	|0	|0 	|
		 * |ADE-COL	|0	|0	|0	|0	|8	|0	|0	|0	|0	|0	|0 	|
		 * |ADE-VOL	|0	|0	|0	|0	|0	|6	|0	|0	|0	|0	|0 	|
		 * |TUR-ART	|0	|0	|0	|0	|0	|0	|4	|0	|0	|0	|0 	|
		 * |TUR-COL	|0	|0	|0	|0	|0	|0	|0	|6	|0	|0	|0	|
		 * |TUR-VOL	|0	|0	|0	|0	|0	|0	|0	|0	|6	|0	|0 	|
		 */
		 int[][] finish =  { 
			 {19,0,0,0,0,0,0,0,0,0,0},
			 {0,5,0,0,0,0,0,0,0,0,0},
			 {0,0,8,0,0,0,0,0,0,0,0},
			 {0,0,0,8,0,0,0,0,0,0,0},
			 {0,0,0,0,8,0,0,0,0,0,0},
			 {0,0,0,0,0,6,0,0,0,0,0},
			 {0,0,0,0,0,0,4,0,0,0,0},
			 {0,0,0,0,0,0,0,6,0,0,0},
			 {0,0,0,0,0,0,0,0,6,0,0}
		 };
		
		/*	Estado inicial biblioteca:
		 * 	    	 ___________________________________
		 * 	initial	|	INF 	| 	ADE	 	|	 TUR	|_______
		 * 			|ART|VOL|COL|ART|VOL|COL|ART|VOL|COL|PIC|ROB|
		 * |INF-ART	|10	|0	|1	|0	|1	|0	|0	|2	|0	|5	|0 	|
		 * |INF-VOL	|0	|0	|0	|0	|0	|0	|1	|0	|1	|3	|0 	|
		 * |INF-COL	|1	|0	|4	|0	|0	|0	|0	|0	|0	|3	|0 	|
		 * |ADE-ART	|0	|0	|0	|5	|0	|1	|0	|0	|0	|2	|0 	|
		 * |ADE-VOL	|0	|0	|1	|0	|6	|0	|0	|0	|1	|0	|0 	|
		 * |ADE-COL	|1	|0	|0	|0	|0	|4	|0	|0	|0	|1	|0 	|
		 * |TUR-ART	|0	|1	|0	|0	|0	|0	|2	|0	|0	|1	|0 	|
		 * |TUR-VOL	|0	|0	|0	|1	|0	|0	|0	|5	|0	|0	|0	|
		 * |TUR-COL	|0	|1	|0	|0	|0	|0	|0	|0	|4	|1	|0 	|
		 */
		 /*int[][] start =  { 
			 {10,0,1,0,1,0,0,2,0,5,0},
			 {0,0,0,0,0,0,1,0,1,3,0},
			 {1,0,4,0,0,0,0,0,0,3,0},
			 {0,0,0,5,0,1,0,0,0,2,0},
			 {0,0,1,0,6,0,0,0,1,0,0},
			 {1,0,0,0,0,4,0,0,0,1,0},
			 {0,1,0,0,0,0,2,0,0,1,0},
			 {0,0,0,1,0,0,0,5,0,0,0},
			 {0,1,0,0,0,0,0,0,4,1,0}
		 };*/
		 
		 int[][] start =  { /*PRUEBA*/
				 {0,0,0,0,0,0,0,0,0,0,0},
				 {0,0,0,0,0,0,0,0,0,0,0},
				 {0,0,0,0,0,0,0,0,0,0,0},
				 {0,0,0,0,0,0,0,0,0,0,0},
				 {0,0,0,0,0,0,0,0,0,0,0},
				 {0,0,0,0,0,0,0,0,0,0,0},
				 {0,0,0,0,0,0,0,0,0,0,0},
				 {0,0,0,0,0,0,0,0,0,0,0},
				 {1,1,1,1,1,1,1,1,1,0,0}
			 };
		 			//Node(parent,goal,initial,initial_matrix,position)
		Node initialNode = new Node(null,false,true,start,9/*AÑADIR ACCION*/);
		Node goalNode = new Node(null,true,false,start,9/*AÑADIR ACCION*/);
		
		// Introducir heuristicas y costes para el nodo inicial. El nodo meta solo tiene heuristica
		initialNode.calculaHeuristica();
		initialNode.setCoste(0);
		initialNode.calculaEvaluacion();
		goalNode.calculaHeuristica();
		
		// Generamos un AStar que funcione con nuestros nodos inicial y meta
		AStar aStar = new AStar(initialNode, goalNode);		
		// Comienzo del algoritmo
		aStar.Algorithm();
		
		//Extraccion de informacion 
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
		this.setFindGoal(false);
		// Genera la lista de nodos explorados y sin explorar
		this.closedList = new ArrayList<Node>();
		this.openList = new OpenList();
		this.openList.insertAtEvaluation(initialNode); // Añadimos a la lista de nodos sin explorar el nodo inicial
	}
	

	// Implementación de A estrella
	private void Algorithm() {
		Node currentNode=null;
		
		while(!this.openList.isEmpty()) { 				// Recorremos la lista de nodos sin explorar
			currentNode = this.openList.pullFirst(); 	// Extraemos el primero (la lista esta ordenada segun la funcion de evaluación)
			/*
			System.out.println("...........Selected Node............");
			System.out.println("---------------"+currentNode.action+"----------------");
			System.out.println("--------------- POS "+currentNode.getPosition()+"----------------");
			System.out.println("--------------- Her"+currentNode.getHeuristica()+"----------------");
			currentNode.printNodeData();
			System.out.println("....................................");
			*/
			//System.out.print("#");
			if(checkNode(currentNode)) {				// Si el nodo ya se ha visitado con un coste menor (esta en la lista de explorados) lo ignoramos
				closedList.add(currentNode); 			// Añadimos dicho nodo a la lista de explorados
				
				if(this.goalNode.isGoal(currentNode)) {	// Si es el nodo meta hemos acabado y no hace falta continuar
					this.goalNode=currentNode;
					this.setFindGoal(true);
					break;
				}
				this.addAdjacentNodes(currentNode); 	// Expandimos el nodo segun las acciones posibles    	
			}
		}
	}
	
	
	//Comprobacion de si el nodo ya se ha explorado
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
		/* relativo al problema */
		
		int movementCost = 0;
		
		//UNLOAD
		addAdjacentNodeUnload(currentNode, movementCost);
		
		//LOAD
		addAdjacentNodeLoad(currentNode, movementCost);
	
		//MOVEMENT
		addAdjacentNodeMovement(currentNode, movementCost);
		
	}
	
	private void addAdjacentNodeUnload(Node currentNode, int movementCost) {

		//Unloads a book at the bookshelf located in the current position
		//	update values -> if : currentNode.getMatrix()[CurrentPos][ROB column] is not empty
		
		if(currentNode.getPosition()!=9 /*Not allowed at PIC*/ && 
				currentNode.getMatrix()[currentNode.getPosition()][currentNode.getY()-1]!=0) {
			
				int newMatrix [][] = new int[currentNode.getX()][currentNode.getY()];
				
				//copy currentNode Matrix
				for (int i = 0; i < currentNode.getX(); i++) {
					for (int j = 0; j < currentNode.getY();j++) {
						newMatrix[i][j]= currentNode.getMatrix()[i][j];
					}	
				}
				//substracts from robot
				newMatrix[currentNode.getPosition()][currentNode.getY()-1]--;
					//newMatrix[currentNode.getPosition()][currentNode.getY()-1]-1;
				
				//adds to correspoding bookshelf
				newMatrix[currentNode.getPosition()][currentNode.getPosition()] =
					newMatrix[currentNode.getPosition()][currentNode.getPosition()]+1;
				
				Node newNode = new Node(currentNode,false,false,newMatrix,currentNode.getPosition());
				newNode.calculaHeuristica();	
				movementCost = 0;													// genera su heurística
				newNode.calculaCoste(currentNode.getCoste() + movementCost);		// calcula el coste de la acción y se lo suma al coste del padre
				newNode.calculaEvaluacion();										// genera la evaluación
				newNode.action = "Unload";
//				System.out.println("_____________[UNLOAD]_____________");
//				newNode.printNodeData();
//				System.out.println("____________________________________");
				this.openList.insertAtEvaluation(newNode);
			}
	}
	
	private void addAdjacentNodeLoad(Node currentNode, int movementCost) {
		//loads a book from the bookshelf located at the current position
		//update values -> if : currentNode.getMatrix()[][CurrentPos] is not empty
				
			//int substractedBookshelf = decideNextBookShelf(calculateMaxCost(),currentNode);
				
			//All possible books
			for(int i = 0; i < currentNode.getMatrix().length; i++) {
				if( i!=currentNode.getPosition() && currentNode.getMatrix()[i][currentNode.getPosition()]>0 && (currentNode.weightLoadedBooks()+currentNode.bookWeight(i)<10)) {
						
					int newMatrix [][] = new int[currentNode.getX()][currentNode.getY()];
						
					//copy currentNode Matrix
					for (int k = 0; k < currentNode.getX(); k++) {						
						for (int j = 0; j < currentNode.getY();j++) {
							newMatrix[k][j] = currentNode.getMatrix()[k][j];
						}	
					}
						
					//substracts from correspoding bookshelf
					newMatrix[i][currentNode.getPosition()]--;
						
					//adds books to robot
					newMatrix[i][currentNode.getY()-1]++;						
					Node newNode = new Node(currentNode,false,false,newMatrix,currentNode.getPosition());
					newNode.calculaHeuristica();	// genera su heurística						
					movementCost = 0;
					newNode.calculaCoste(currentNode.getCoste() + movementCost);		// calcula el coste de la acción y se lo suma al coste del padre									
					newNode.calculaEvaluacion();										// genera la evaluación
					newNode.action = "Load";
//					System.out.println("_____________[LOAD]_____________");
//					System.out.println("SubstractedBookshelf: ["+i+","+newNode.getPosition()+"]");
//					newNode.printNodeData();
//					System.out.println("____________________________________");
					this.openList.insertAtEvaluation(newNode);		
				}
			}
	}
	
	private void addAdjacentNodeMovement(Node currentNode, int movementCost) {
		
		//Check all posible movements. We use the basic matrix as a tool to anilize each cost asociated to a movemnt to a certain bookshelf
		// FROM "bookShelf"(currentNode.getPosition())  TO:	|ART|VOL|COL|ART|VOL|COL|ART|VOL|COL|PIC|ROB| "ROB column is never used (.getMatrix().length-1)"
				
		for(int i=0;i<currentNode.getY()-1;i++) {
			movementCost = this.getCosteAccion(currentNode.getPosition(),i);
			if(movementCost>=0) { //It means this is a posible path
				Node newNode = new Node(currentNode,false,false,currentNode.getMatrix(),i);
				newNode.setPosition(i);
				newNode.calculaHeuristica();									// genera su heurística
				newNode.calculaCoste(currentNode.getCoste() + movementCost);	// calcula el coste de la acción y se lo suma al coste del padre
				newNode.calculaEvaluacion();									// genera la evaluación
				newNode.action = "Move";
//				System.out.println("_____________[MOVEMENT]_____________");
//				newNode.printNodeData();
//				System.out.println("____________________________________");
				this.openList.insertAtEvaluation(newNode);
			}	
		}		
	}
	
	public int decideNextBookShelf (int minCost, Node currentNode) {
		
		int bookShelfDestination = -1;
		
		for(int j=0;j<currentNode.getY()-1/*PIC NOT ALLOWED*/;j++) {
			if(														//menor coste de entre las estanterias colindantes con libros
				movementCosts[currentNode.getPosition()][j]>0 &&				//a posible path
				movementCosts[currentNode.getPosition()][j]<minCost 			//mincost possible
				&& currentNode.getMatrix()[j][currentNode.getPosition()]>0) {	//having books
				
				minCost = movementCosts[currentNode.getPosition()][j];
				bookShelfDestination = j;
			}
		}
		if (bookShelfDestination!=-1) return bookShelfDestination;	//estanteria con libros
		for(int j=0;j<currentNode.getY()-2/*PIC NOT ALLOW*/;j++) {	//Selects the best bookshelf from all possible	
			if( currentNode.getMatrix()[currentNode.getPosition()][j]>0) { //having books
				bookShelfDestination = j;
				break;
			}
		}
		return bookShelfDestination;
	}
	
	private int calculateMaxCost() {
		
		 int[][] movementCosts =  { 
			{-1,10,10,-1,-1,-1,-1,-1,-1,5},
			{10,-1,5,20,20,20,15,15,15,10},
			{10,5,-1,20,20,20,15,15,15,10},
			{-1,20,20,-1,10,5,10,10,10,10},
			{-1,20,20,10,-1,10,10,10,10,10},
			{-1,20,20,5,10,-1,10,10,10,10},
			{-1,15,15,10,10,10,-1,5,5,30},
			{-1,15,15,10,10,10,5,-1,10,30},
			{-1,15,15,10,10,10,5,10,-1,30},
			{5,10,10,10,10,10,30,30,30,-1}
		 };
		 int number = -1;
		 for(int i=0; i<movementCosts.length; i++) {
			 for(int j=0; j<movementCosts[i].length; j++) {
				 if(movementCosts[i][j]>number) number = movementCosts[i][j];
			 }
		 }
		 return number;
	}
	// Devuelve el costes de la accion que realice
	/**
	 * MODIFICAR el contenido y los parametros segun las necesidades del problema
	 */
	private int getCosteAccion(int currentPos, int destination) {

		/* relativo al problema */
		int cost = -1;
		 if((currentPos>movementCosts.length)||(destination > movementCosts[currentPos].length)) {
			 System.out.println("ERROR: Wrong costs coordenates");
			 return -1;
		 } else {
			 cost = movementCosts[currentPos][destination];
			 return cost;	
		 }
			 
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
	 * MODIFICAR si se considera necesario. No es imprescindible, solo si consideras que puede ayudar a tu implementacion
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

	public boolean isFindGoal() {
		return findGoal;
	}

	public void setFindGoal(boolean findGoal) {
		this.findGoal = findGoal;
	}

}
