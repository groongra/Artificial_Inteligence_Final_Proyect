package busqueda.practica;

/**
 * Clase creada como ejemplo de la parte 2 de la práctica 2018-2019 de IA, UC3M Colmenarejo
 *
 * 
 * @author Daniel Amigo Herrero
 * @author David Sánchez Pedroche
 */

public class Node {
	private double coste;							// Valor del coste de llegada al nodo
	private double heuristica;						// Valor de la heurística del nodo
	private double evaluacion;						// Valor de la evaluación
	private Node parent;							// Nodo padre del arbol A*
	private Node nextNodeList = null;				// Para la gestión de la lista Slist
	private int position = 9;						// represents the default initial position of the robot as a "bookshelf" column from matrix
	private int matrix [][]; //= 
		
		
		/*{ 					// represents the library
			
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
	
	
	private int X; //= matrix.length;
	private int Y; //= matrix[0].length;
	public String action = "NULL";
	
	// Variables específicas al problema
	/**
	 * MODIFICAR para ajustarse a las necesidades del problema
	 * atributos del noso
	 * 
	 */

	//Constructor para introducir un nuevo nodo en el algoritmo A estrella
	/**
	 * MODIFICAR para ajustarse a las necesidades del problema
	 */
	public Node(Node parentNode, boolean goal, boolean initial, int current_matrix[][], int currentPosition /* ACCION REALIZADA */) {
		
		//la accion a añadir podria ser el numero de libros que modifica el robot en una estanteria
		super();
		this.X = current_matrix.length;
		this.Y = current_matrix[0].length;
		this.parent = parentNode;// padre en el arbol A*
		
		if(initial) {	//Create initial matrix
			 this.matrix = current_matrix;
			 this.position = 9;
			
		} else if (goal) { //Create final matrix
			
			int rowSum = 0;
			int diagonal = 0;
			this.matrix = new int[current_matrix.length][current_matrix[0].length];
			for (int i = 0; i < current_matrix.length; i++) {
				for (int j = 0; j < current_matrix[i].length; j++) {
					rowSum = rowSum + current_matrix[i][j];
					this.matrix[i][j]= 0;
				}
				this.matrix[i][diagonal] = rowSum;
				rowSum=0;
				diagonal++;
				this.position = currentPosition;	
			}
			//Print goal;
			/*
			for (int i = 0; i < current_matrix.length; i++) {
				for (int j = 0; j < current_matrix[i].length; j++) {
					System.out.print(this.matrix[i][j]+", ");
				}
				System.out.println();
			}		
			*/
			 
		} else {
			//Copies the parentNode matrix and modifies the corresponding info
			/*for (int i = 0; i <current_matrix.length; i++) {
				for (int j = 0; j < current_matrix[0].length; j++) {
					this.matrix[i][j] = current_matrix[i][j];
				}
			}*/
			this.matrix = current_matrix;
			this.position = currentPosition;
		}
	}

	// Constructor auxiliar para la implementacion del algoritmo. Genera una copia de un nodo para introducirla en la OpenList 
	public Node(Node original) {

		// Incluir todas las variables del nodo
		this.coste = original.coste;
		this.heuristica = original.heuristica;
		this.evaluacion = original.evaluacion;
		this.parent = original.parent;
		this.nextNodeList = original.nextNodeList;
		this.action = original.action;
		this.position = original.position;
		this.matrix = original.matrix;
		this.X= original.X;
		this.Y= original.Y;
		/**
		 * MODIFICAR para ajustarse a las necesidades del problema
		 */
		/* relativo al problema */
	}
	
	// Constructor auxiliar para generar el primer nodo de la lista abierta
	public Node() {	}

	// Calcula el valor de la heuristica del problema para el nodo 
	/**
	 * MODIFICAR para ajustarse a las necesidades del problema
	 */
	public void calculaHeuristica() {/* relativo al problema */
		
		//desarrollar una heuristica que premie el menor coste
		//Sumamos la columna de la seccion
		
		int aux = 0;
		for (int i = 0; i < this.X; i++) {
			for(int j = 0; j <this.Y-2; j++) {
				if(i != j) 	aux  = aux + this.matrix[i][j];
			}
		}
		for (int i = 0; i < this.X; i++) aux  = aux + this.matrix[i][this.getY()-2];
		this.heuristica = aux; 
	}

	// Comprobacion de que la informacion de un nodo es equivalente a la de otro nodo
	/**
	 * MODIFICAR la condicion para ajustarse a las necesidades del problema
	 */
	public boolean equals(Object arg0) {
		Node other = (Node) arg0;
		/* relativo al problema */
		boolean checkMatrix=true;
		for (int i = 0; i < this.matrix.length && checkMatrix; i++) {
			for (int j = 0; j < this.matrix[0].length && checkMatrix; j++) {
				if(this.matrix[i][j]!=other.matrix[i][j])checkMatrix=false;
			}
		}
		boolean checkPosition = true;
		if(this.position!=other.position) checkPosition = false;
		
		return checkMatrix && checkPosition;
	}
	
	public boolean isGoal(Object arg0) {
		Node other = (Node) arg0;
		/* relativo al problema */
		boolean checkMatrix=true;
		for (int i = 0; i < this.matrix.length && checkMatrix; i++) {
			for (int j = 0; j < this.matrix[0].length && checkMatrix; j++) {
				if(this.matrix[i][j]!=other.matrix[i][j])checkMatrix=false;
			}
		}

		return checkMatrix;
	}


	// Impresón de la información del nodo
	/**
	 * MODIFICAR para imprimir las comprobaciones que se consideren para explicar los resultados
	 */
	public void printNodeData() {
		System.out.println("Accion____________________________________: "+this.action);
		System.out.println("Evaluation: "+this.getEvaluacion());
		System.out.println("Heuristic: " +this.heuristica);
		System.out.println("Cost:	"+this.coste);
		System.out.println("Position: " +this.position);
		System.out.println("WeightLoadedBooks:"+this.weightLoadedBooks());
		

		
		for (int i = 0; i < this.X; i++) {
			for (int j = 0; j < this.Y; j++) {
				System.out.print(this.matrix[i][j]+", ");
			}
			System.out.println();
		}
		/* relativo al problema */
	}

	// Actualiza el coste del nodo. Introducir el coste
	public void calculaCoste( double costeAccion ) {
		this.coste = costeAccion; 
	}

	// Ejecuta la funcion de evaluacion del problema para el nodo. IMPORTANTE: ejecutar antes el calculo del coste y heuristica
	public void calculaEvaluacion() {
		this.evaluacion = this.coste+this.heuristica; 
	}

	public int weightLoadedBooks() {
		int booksLoadedWeight = 0;
		for(int i=0; i<matrix.length;i++) {
			booksLoadedWeight = booksLoadedWeight + matrix[i][this.Y-1]*bookWeight(i);	
		}
		return booksLoadedWeight;
	}
	
	public int bookWeight(int bookshelfType) {
		
		if(bookshelfType%3==0)		return 1;	//ART
		else if(bookshelfType%3==1)	return 3;	//COL
		else						return 7;	//VOL
	}
	
	public int numberBooksAtBookShelf() {
		int sum = 0;
		for (int i = 0; i <this.X; i++) {						
			if(i!=this.position)sum += matrix[i][this.position];
		}
		return sum;
	}

	/**** Getters y Setters ****/
	/**
	 * MODIFICAR si se considera necesario. No es imprescindible, solo si consideras que puede ayudar a tu implementación
	 */
	public double getEvaluacion() {
		return evaluacion;
	}

	public void setEvaluacion(double evaluacion) {
		this.evaluacion = evaluacion;
	}
	public double getCoste() {
		return coste;
	}

	public void setCoste(double coste) {
		this.coste = coste;
	}

	public double getHeuristica() {
		return heuristica;
	}

	public void setHeuristica(double heuristica) {
		this.heuristica = heuristica;
	}

	public Node getParent() {
		return parent;
	}

	public void setParent(Node parent) {
		this.parent = parent;
	}

	public Node getNextNode() {
		return nextNodeList;
	}

	public void setNextNode(Node nextNode) {
		this.nextNodeList = nextNode;
	}

	public int[][] getMatrix() {
		return matrix;
	}

	public void setMatrix(int[][] matrix) {
		this.matrix = matrix;
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}

	public int getX() {
		return X;
	}

	public int getY() {
		return Y;
	}

	

}
